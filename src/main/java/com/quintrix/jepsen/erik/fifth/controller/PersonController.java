package com.quintrix.jepsen.erik.fifth.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.quintrix.jepsen.erik.fifth.model.Department;
import com.quintrix.jepsen.erik.fifth.model.Person;
import com.quintrix.jepsen.erik.fifth.service.DepartmentService;
import com.quintrix.jepsen.erik.fifth.service.PersonService;

@RestController
public class PersonController {
  @Autowired
  private PersonService personService;
  @Autowired
  DepartmentService deptService;
  @Value("${fifth.baseUri}")
  private String baseUri;



  @GetMapping("/person")
  public ResponseEntity<Person[]> person() {
    Person[] results = personService.getAllPersons();
    if (results.length == 0)
      return new ResponseEntity<>(results, null, HttpStatus.NOT_FOUND);
    return new ResponseEntity<>(results, null, HttpStatus.OK);
  }

  @PostMapping("/person/new")
  public ResponseEntity<Person> personNew(@RequestParam Person person) {
    Person personOut = personService.createPerson(person);
    if (person != null && personOut != null)
      return new ResponseEntity<>(personOut, HttpStatus.CREATED);
    return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
  }

  @GetMapping("person/last/{lName}")
  public ResponseEntity<Person[]> getPersonLast(@PathVariable String lName) {
    Person[] results = personService.getPersonsByLastName(lName);
    if (results.length == 0)
      return new ResponseEntity<>(results, HttpStatus.NOT_FOUND);
    return new ResponseEntity<>(results, HttpStatus.OK);
  }

  @GetMapping("person/first/{fName}")
  public ResponseEntity<Person[]> getPersonFirst(@PathVariable String fName) {
    Person[] results = personService.getPersonsByFirstName(fName);
    if (results.length == 0)
      return new ResponseEntity<>(results, HttpStatus.NOT_FOUND);
    return new ResponseEntity<>(results, HttpStatus.OK);
  }

  @GetMapping("person/dept/{deptId}")
  public ResponseEntity<Person[]> getPersonDept(@PathVariable int deptId) {
    Department dept = deptService.findById(deptId);
    if (dept == null)
      return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    Person[] results = personService.getPersonsByDeptId(dept);
    if (results.length == 0)
      return new ResponseEntity<>(results, HttpStatus.NOT_FOUND);
    return new ResponseEntity<>(results, HttpStatus.OK);
  }

  @GetMapping("person/{personId}")
  public ResponseEntity<Person> getPersonId(@PathVariable int personId) {
    Person result = personService.getPersonById(personId);
    if (result == null)
      return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    return new ResponseEntity<>(result, HttpStatus.OK);
  }

  @PutMapping("person/{personId}")
  public ResponseEntity<Person> putPersonId(@PathVariable int personId,
      @RequestParam Person person) {
    person.setPersonId(personId);
    if (personService.getPersonById(personId) == null)
      return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    if (personService.updatePerson(person) != null)
      return new ResponseEntity<>(personService.getPersonById(personId), null, HttpStatus.OK);
    return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
  }

  @DeleteMapping("person/{personId}")
  public ResponseEntity<Person> deletePersonId(@PathVariable int personId) {
    if (personService.getPersonById(personId) == null)
      return new ResponseEntity<>(null, null, HttpStatus.NOT_FOUND);
    personService.deletePersonById(personId);
    return new ResponseEntity<>(null, null, HttpStatus.NO_CONTENT);
  }

  @PatchMapping("person/{id}/firstName")
  public ResponseEntity<Person> patchPersonIdfName(@PathVariable int id,
      @RequestParam String fName) {
    if (personService.getPersonById(id) == null)
      return new ResponseEntity<>(null, null, HttpStatus.NOT_FOUND);
    if (personService.UpdatePersonFName(id, fName) == 1)
      return new ResponseEntity<>(personService.getPersonById(id), null, HttpStatus.OK);
    return new ResponseEntity<>(null, null, HttpStatus.INTERNAL_SERVER_ERROR);
  }

  @PatchMapping("person/{id}/lastName")
  public ResponseEntity<Person> patchPersonIdlName(@PathVariable int id,
      @RequestParam String lName) {
    if (personService.getPersonById(id) == null)
      return new ResponseEntity<>(null, null, HttpStatus.NOT_FOUND);
    if (personService.UpdatePersonLName(id, lName) == 1)
      return new ResponseEntity<>(personService.getPersonById(id), null, HttpStatus.OK);
    return new ResponseEntity<>(null, null, HttpStatus.INTERNAL_SERVER_ERROR);
  }

  @PatchMapping("person/{personId}/deptId")
  public ResponseEntity<Person> patchPersonIdDeptId(@PathVariable int personId,
      @RequestParam int deptId) {
    if (personService.getPersonById(personId) == null)
      return new ResponseEntity<>(null, null, HttpStatus.NOT_FOUND);
    if (personService.updatePersonDept(personId, deptId) != null)
      return new ResponseEntity<>(personService.getPersonById(personId), HttpStatus.OK);
    return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
  }
}

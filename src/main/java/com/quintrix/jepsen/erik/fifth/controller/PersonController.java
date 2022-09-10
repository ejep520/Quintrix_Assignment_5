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
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.quintrix.jepsen.erik.fifth.dao.PersonDao;
import com.quintrix.jepsen.erik.fifth.model.Person;

@RestController
public class PersonController {
  @Autowired
  private PersonDao personDao;
  @Value("${fifth.baseUri}")
  private String baseUri;

  @GetMapping("/person")
  public ResponseEntity<Person[]> person() {
    Person[] results = personDao.GetPersons();
    if (results.length == 0)
      return new ResponseEntity<>(results, null, HttpStatus.NOT_FOUND);
    return new ResponseEntity<>(results, null, HttpStatus.OK);
  }

  /*
   * @PostMapping("/person/new") public ResponseEntity<Person> personNew(@RequestParam String
   * fName, @RequestParam String lName,
   * 
   * @RequestParam int deptId) { if (personDao.personNew(new Person(fName, lName, deptId)) == 1)
   * return new ResponseEntity<>(personDao.getLastPerson(), null, HttpStatus.CREATED); return new
   * ResponseEntity<>(null, null, HttpStatus.BAD_REQUEST); }
   */

  @PostMapping("/person/new")
  public ResponseEntity<Person> personNew(@RequestParam String person) {
    Person personObj = null;
    ObjectMapper objectMapper = new ObjectMapper();
    try {
      personObj = objectMapper.readValue(person, Person.class);
    } catch (JsonMappingException e) {
      e.printStackTrace();
    } catch (JsonProcessingException e) {
      e.printStackTrace();
    }
    if (personObj != null && personDao.personNew(personObj) == 1)
      return new ResponseEntity<>(personDao.getLastPerson(), null, HttpStatus.CREATED);
    return new ResponseEntity<>(null, null, HttpStatus.BAD_REQUEST);
  }

  @GetMapping("person/last/{lName}")
  public ResponseEntity<Person[]> getPersonLast(@PathVariable String lName) {
    Person[] results = personDao.getPersonsByLastName(lName);
    if (results.length == 0)
      return new ResponseEntity<>(results, null, HttpStatus.NOT_FOUND);
    return new ResponseEntity<>(results, null, HttpStatus.OK);
  }

  @GetMapping("person/first/{fName}")
  public ResponseEntity<Person[]> getPersonFirst(@PathVariable String fName) {
    Person[] results = personDao.getPersonsByFirstName(fName);
    if (results.length == 0)
      return new ResponseEntity<>(results, null, HttpStatus.NOT_FOUND);
    return new ResponseEntity<>(results, null, HttpStatus.OK);
  }

  @GetMapping("person/dept/{deptId}")
  public ResponseEntity<Person[]> getPersonDept(@PathVariable int deptId) {
    Person[] results = personDao.getPersonsByDeptId(deptId);
    if (results.length == 0)
      return new ResponseEntity<>(results, null, HttpStatus.NOT_FOUND);
    return new ResponseEntity<>(results, null, HttpStatus.OK);
  }

  @GetMapping("person/{personId}")
  public ResponseEntity<Person> getPersonId(@PathVariable int personId) {
    Person result = personDao.getPersonById(personId);
    if (result == null)
      return new ResponseEntity<>(null, null, HttpStatus.NOT_FOUND);
    return new ResponseEntity<>(result, null, HttpStatus.OK);
  }

  @PutMapping("person/{personId}")
  public ResponseEntity<Person> putPersonId(@PathVariable int personId,
      @RequestParam Person person) {
    person.setPersonId(personId);
    if (personDao.getPersonById(personId) == null)
      return new ResponseEntity<>(null, null, HttpStatus.NOT_FOUND);
    if (personDao.UpdatePerson(person) == 1)
      return new ResponseEntity<>(personDao.getPersonById(personId), null, HttpStatus.OK);
    return new ResponseEntity<>(null, null, HttpStatus.INTERNAL_SERVER_ERROR);
  }

  @DeleteMapping("person/{personId}")
  public ResponseEntity<Person> deletePersonId(@PathVariable int personId) {
    if (personDao.getPersonById(personId) == null)
      return new ResponseEntity<>(null, null, HttpStatus.NOT_FOUND);
    if (personDao.deletePerson(personId) == 1)
      return new ResponseEntity<>(null, null, HttpStatus.NO_CONTENT);
    return new ResponseEntity<>(null, null, HttpStatus.INTERNAL_SERVER_ERROR);
  }

  @PatchMapping("person/{id}/firstName")
  public ResponseEntity<Person> patchPersonIdfName(@PathVariable int id,
      @RequestParam String fName) {
    if (personDao.getPersonById(id) == null)
      return new ResponseEntity<>(null, null, HttpStatus.NOT_FOUND);
    if (personDao.UpdatePersonFName(id, fName) == 1)
      return new ResponseEntity<>(personDao.getPersonById(id), null, HttpStatus.OK);
    return new ResponseEntity<>(null, null, HttpStatus.INTERNAL_SERVER_ERROR);
  }

  @PatchMapping("person/{id}/lastName")
  public ResponseEntity<Person> patchPersonIdlName(@PathVariable int id,
      @RequestParam String lName) {
    if (personDao.getPersonById(id) == null)
      return new ResponseEntity<>(null, null, HttpStatus.NOT_FOUND);
    if (personDao.UpdatePersonLName(id, lName) == 1)
      return new ResponseEntity<>(personDao.getPersonById(id), null, HttpStatus.OK);
    return new ResponseEntity<>(null, null, HttpStatus.INTERNAL_SERVER_ERROR);
  }

  @PatchMapping("person/{personId}/deptId")
  public ResponseEntity<Person> patchPersonIdDeptId(@PathVariable int personId,
      @RequestParam int deptId) {
    if (personDao.getPersonById(personId) == null)
      return new ResponseEntity<>(null, null, HttpStatus.NOT_FOUND);
    if (personDao.updatePersonDept(personId, deptId) == 1)
      return new ResponseEntity<>(personDao.getPersonById(personId), null, HttpStatus.OK);
    return new ResponseEntity<>(null, null, HttpStatus.INTERNAL_SERVER_ERROR);
  }
}

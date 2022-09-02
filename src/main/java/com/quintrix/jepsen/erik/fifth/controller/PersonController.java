package com.quintrix.jepsen.erik.fifth.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import com.quintrix.jepsen.erik.fifth.dao.PersonDao;
import com.quintrix.jepsen.erik.fifth.model.Person;

@RestController
public class PersonController {
  @Autowired
  PersonDao personDao;

  @GetMapping("/person")
  @ResponseBody
  public Person[] person() {
    return personDao.GetPersons();
  }

  @GetMapping("/config/new/table/persons")
  public void configNewTablePersons() {
    personDao.CreatePersonsTable();
  }

  @PostMapping("/person/new")
  @ResponseBody
  public String personNew(@RequestParam String fName, @RequestParam String lName,
      @RequestParam Integer deptId) {
    Person newPerson = new Person(fName, lName, deptId);
    if (personDao.PersonNew(newPerson) == 1)
      return "Success";
    return "Fail";
  }

  @GetMapping("person/last/{lName}")
  @ResponseBody
  public Person[] getPersonLast(@PathVariable String lName) {
    return personDao.GetPersonsByLastName(lName);
  }

  @GetMapping("person/first/{fName}")
  @ResponseBody
  public Person[] getPersonFirst(@PathVariable String fName) {
    return personDao.GetPersonsByFirstName(fName);
  }

  @GetMapping("person/dept/{deptId}")
  @ResponseBody
  public Person[] getPersonDept(@PathVariable int deptId) {
    return personDao.GetPersonsByDeptId(deptId);
  }

  @GetMapping("person/{personId}")
  @ResponseBody
  public Person getPersonId(@PathVariable int personId) {
    return personDao.GetPersonById(personId);
  }

  @PutMapping("person/{personId}")
  @ResponseBody
  public String putPersonId(@PathVariable int personId, @RequestParam Person person) {
    person.setPersonId(personId);
    if (personDao.UpdatePerson(person) == 1)
      return "Success";
    return "Fail";
  }

  @DeleteMapping("person/{personId}")
  @ResponseBody
  public String deletePersonId(@PathVariable int personId) {
    if (personDao.DeletePerson(personId) == 1)
      return "Success";
    return "Fail";
  }

  @PatchMapping("person/{id}/firstName")
  @ResponseBody
  public String patchPersonIdfName(@PathVariable int id, @RequestParam String fName) {
    if (personDao.UpdatePersonFName(id, fName) == 1)
      return "Success";
    return "Fail";
  }

  @PatchMapping("person/{id}/lastName")
  @ResponseBody
  public String patchPersonIdlName(@PathVariable int id, @RequestParam String lName) {
    if (personDao.UpdatePersonLName(id, lName) == 1)
      return "Success";
    return "Fail";
  }

  @PatchMapping("person/{personId}/deptId")
  @ResponseBody
  public String patchPersonIdDeptId(@PathVariable int personId, @RequestParam int deptId) {
    if (personDao.UpdatePersonDept(personId, deptId) == 1)
      return "Success";
    return "Fail";
  }
}

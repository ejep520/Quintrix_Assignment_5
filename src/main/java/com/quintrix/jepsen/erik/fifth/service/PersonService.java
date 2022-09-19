package com.quintrix.jepsen.erik.fifth.service;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.quintrix.jepsen.erik.fifth.dao.DepartmentDao;
import com.quintrix.jepsen.erik.fifth.dao.PersonDao;
import com.quintrix.jepsen.erik.fifth.model.Department;
import com.quintrix.jepsen.erik.fifth.model.Person;

@Service
public class PersonService {
  @Autowired
  private PersonDao repo;
  @Autowired
  private DepartmentDao deptDao;

  public PersonService(EntityManager entityManager) {}

  public Person[] getAllPersons() {
    Iterable<Person> people = repo.findAll();
    List<Person> peopleList = new ArrayList<>();
    for (Person person : people)
      peopleList.add(person);
    return peopleList.toArray(new Person[peopleList.size()]);
  }

  public Person getPersonById(int personId) {
    return repo.findById(personId).orElse(null);
  }

  public Person[] getPersonsByLastName(String lName) {
    List<Person> results = repo.findBylName(lName);
    return results.toArray(new Person[results.size()]);
  }

  public Person[] getPersonsByFirstName(String fName) {
    List<Person> results = repo.findPersonsByfName(fName);
    return results.toArray(new Person[results.size()]);
  }

  public Person createPerson(Person person) {
    return repo.save(person);
  }

  public int UpdatePersonFName(int personId, String fName) {
    Person updatee = repo.findById(personId).orElse(null);
    if (updatee == null)
      return 0;
    updatee.setFName(fName);
    repo.save(updatee);
    return 1;
  }

  public int UpdatePersonLName(int personId, String lName) {
    Person updatee = repo.findById(personId).orElse(null);
    if (updatee == null)
      return 0;
    updatee.setLName(lName);
    repo.save(updatee);
    return 1;
  }

  public Person[] getPersonsByDeptId(Department dept) {
    List<Person> results = repo.findPersonsByDept(dept);
    return results.toArray(new Person[results.size()]);
  }

  public Person getLastPerson() {
    Iterable<Person> results = repo.findAll();
    Person result = null;
    for (Person person : results) {
      if (result == null || result.getPersonId() < person.getPersonId())
        result = person;
    }
    return result;
  }

  public Person updatePerson(Person person) {
    Person updatee = repo.findById(person.getPersonId()).orElse(null);
    if (updatee == null)
      return null;
    updatee.setFName(person.getFName());
    updatee.setLName(person.getLName());
    updatee.setDept(person.getDept());
    return repo.save(updatee);
  }

  public void deletePerson(Person person) {
    repo.delete(person);
  }

  public void deletePersonById(int personId) {
    Person doomedPerson = repo.findById(personId).orElse(null);
    if (doomedPerson == null)
      return;
    deletePerson(doomedPerson);
  }

  public Person updatePersonDept(int personId, int deptId) {
    Person updatee = repo.findById(personId).orElse(null);
    if (updatee == null)
      return null;
    updatee.setDept(deptDao.findById(deptId).orElse(null));
    return repo.save(updatee);
  }
}

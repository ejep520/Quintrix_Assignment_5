package com.quintrix.jepsen.erik.fifth.dao;

import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import com.quintrix.jepsen.erik.fifth.model.Person;

public interface PersonDao extends CrudRepository<Person, Integer> {
  @Query("select c from Person c where c.lName=?1")
  public List<Person> findBylName(String lName);

  @Query("select c from Person c where c.fName = ?1")
  List<Person> findPersonsByfName(String fName);

  // Person[] findPersonsByDept(Department dept);
}

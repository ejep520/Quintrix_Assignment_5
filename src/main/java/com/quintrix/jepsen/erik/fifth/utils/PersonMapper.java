package com.quintrix.jepsen.erik.fifth.utils;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import com.quintrix.jepsen.erik.fifth.model.Person;

public class PersonMapper implements ResultSetExtractor<List<Person>> {

  @Override
  public List<Person> extractData(ResultSet rs) throws SQLException, DataAccessException {
    Person newPerson;
    String fName, lName;
    int personId, deptId;
    List<Person> result = new ArrayList<>();
    while (rs.next()) {
      fName = rs.getString("fName");
      lName = rs.getString("lName");
      personId = rs.getInt("personId");
      deptId = rs.getInt("deptId");
      if (deptId < 1) {
        newPerson = new Person(fName, lName, -1);
      } else {
        newPerson = new Person(fName, lName, deptId);
      }
      if (personId < 1)
        newPerson.setPersonId(-1);
      else
        newPerson.setPersonId(personId);
      result.add(newPerson);
    }
    return result;
  }
}

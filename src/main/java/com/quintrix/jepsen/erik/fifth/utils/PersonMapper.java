package com.quintrix.jepsen.erik.fifth.utils;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import com.quintrix.jepsen.erik.fifth.model.Department;
import com.quintrix.jepsen.erik.fifth.model.Person;

public class PersonMapper implements ResultSetExtractor<List<Person>> {

  @Override
  public List<Person> extractData(ResultSet rs) throws SQLException, DataAccessException {
    Person newPerson;
    String fName, lName;
    int personId;
    Department dept;
    List<Person> result = new ArrayList<>();
    while (rs.next()) {
      fName = rs.getString("fName");
      lName = rs.getString("lName");
      personId = rs.getInt("personId");
      dept = rs.getObject("deptId", Department.class);
      newPerson = new Person(fName, lName, personId, dept);
      result.add(newPerson);
    }
    return result;
  }
}

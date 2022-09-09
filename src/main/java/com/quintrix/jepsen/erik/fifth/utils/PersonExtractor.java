package com.quintrix.jepsen.erik.fifth.utils;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import com.quintrix.jepsen.erik.fifth.model.Person;

public class PersonExtractor implements ResultSetExtractor<Person> {

  @Override
  public Person extractData(ResultSet rs) throws SQLException, DataAccessException {
    Person person;
    if (rs == null || !rs.next())
      return null;
    person = new Person(rs.getString("fName"), rs.getString("lName"), rs.getInt("deptId"));
    if (person.getDeptId() < 1)
      person.setDeptId(-1);
    person.setPersonId(rs.getInt("personId"));
    return person;
  }

}

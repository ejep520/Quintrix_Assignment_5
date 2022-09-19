package com.quintrix.jepsen.erik.fifth.utils;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import com.quintrix.jepsen.erik.fifth.dao.DepartmentDao;
import com.quintrix.jepsen.erik.fifth.model.Person;

public class PersonExtractor implements ResultSetExtractor<Person> {
  @Autowired
  private DepartmentDao deptDao;

  @Override
  public Person extractData(ResultSet rs) throws SQLException, DataAccessException {
    Person person;
    if (rs == null || !rs.next())
      return null;
    person = new Person(rs.getString("fName"), rs.getString("lName"), rs.getInt("personId"),
        deptDao.findById(rs.getInt("deptId")).orElse(null));
    if (person.getDept() == null)
      person.setDept(null);
    person.setPersonId(rs.getInt("personId"));
    return person;
  }

}

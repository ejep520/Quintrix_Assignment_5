package com.quintrix.jepsen.erik.fifth.utils;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import com.quintrix.jepsen.erik.fifth.model.Department;

public class DepartmentExtractor implements ResultSetExtractor<Department> {
  public Department extractData(ResultSet rs) throws SQLException, DataAccessException {
    Department department;
    if (rs == null || !rs.next())
      return null;
    department = new Department(rs.getString("deptName"), rs.getInt("deptId"));
    return department;
  }
}

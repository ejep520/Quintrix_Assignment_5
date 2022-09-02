package com.quintrix.jepsen.erik.fifth.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import com.quintrix.jepsen.erik.fifth.model.Department;

class DepartmentExtractor implements ResultSetExtractor<Department> {
  public Department extractData(ResultSet rs) throws SQLException, DataAccessException {
    Department department;
    if (rs == null || !rs.next())
      return null;
    department = new Department(rs.getString("deptName"));
    department.setDeptNumber(rs.getInt("deptId"));
    return department;
  }
}

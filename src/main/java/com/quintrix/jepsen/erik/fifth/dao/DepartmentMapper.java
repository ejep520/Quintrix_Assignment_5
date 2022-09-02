package com.quintrix.jepsen.erik.fifth.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import com.quintrix.jepsen.erik.fifth.model.Department;

class DepartmentMapper implements ResultSetExtractor<List<Department>> {

  @Override
  public List<Department> extractData(ResultSet rs) throws SQLException, DataAccessException {
    Department newDepartment;
    List<Department> result = new ArrayList<>();
    while (rs.next()) {
      newDepartment = new Department(rs.getString("deptName"));
      newDepartment.setDeptNumber(rs.getInt("deptId"));
      result.add(newDepartment);
    }
    return result;
  }

}

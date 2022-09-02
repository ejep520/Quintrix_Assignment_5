package com.quintrix.jepsen.erik.fifth.dao;

import java.sql.Types;
import java.util.List;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import com.quintrix.jepsen.erik.fifth.model.Department;

@Repository
public class DepartmentDaoImpl implements DepartmentDao {
  private JdbcTemplate template;
  private final String CREATE_DEPT_TABLE_SAFE =
      "CREATE TABLE IF NOT EXISTS departments " + "(deptId INTEGER AUTO_INCREMENT NOT NULL, "
          + "deptName TINYTEXT, " + "CONSTRAINT PK_departments PRIMARY KEY (deptId));";
  private final String CREATE_DEPT_TABLE_UNSAFE = "DROP TABLE IF EXISTS departments; "
      + "CREATE TABLE departments " + "(deptId INTEGER AUTO_INCREMENT NOT NULL, "
      + "deptName TINYTEXT, " + "CONSTRAINT PK_departments PRIMARY KEY (deptId));";
  private final String GET_ALL_DEPTS = "SELECT * FROM departments;";
  private final String GET_SOME_DEPTS_LIKE = "SELECT * FROM departments WHERE deptName LIKE ?;";
  private final String GET_SOME_DEPTS_EQ = "SELECT * FROM departments WHERE deptId = ?;";
  private final String ADD_DEPARTMENT = "INSERT INTO departments (deptName) VALUES (?);";
  private final String UPDATE_DEPARTMENT = "UPDATE departments SET deptName = ? WHERE deptId = ?;";
  private final String DELETE_DEPARTMENT = "DELETE FROM departments WHERE deptId = ?;";

  @Autowired
  public DepartmentDaoImpl(DataSource ds) {
    template = new JdbcTemplate(ds);
  }

  @Override
  public Department[] GetAllDepartments() {
    List<Department> allDepartments = template.query(GET_ALL_DEPTS, new DepartmentMapper());
    return allDepartments.toArray(new Department[allDepartments.size()]);
  }

  @Override
  public Department GetDeptByName(String deptName) {
    final int[] types = {Types.VARCHAR};
    Object[] args = {deptName};
    return template.query(GET_SOME_DEPTS_LIKE, args, types, new DepartmentExtractor());
  }

  @Override
  public Department GetDeptByNumber(int deptId) {
    final int[] types = {Types.INTEGER};
    Object[] args = {deptId};
    return template.query(GET_SOME_DEPTS_EQ, args, types, new DepartmentExtractor());
  }

  @Override
  public void CreateDeptTable(boolean forceRecreation) {
    try {
      if (forceRecreation)
        template.execute(CREATE_DEPT_TABLE_UNSAFE);
      else
        template.execute(CREATE_DEPT_TABLE_SAFE);
    } catch (DataAccessException e) {
      e.printStackTrace();
    }
  }

  @Override
  public void CreateDeptTable() {
    CreateDeptTable(false);
  }

  @Override
  public int DepartmentAdd(Department department) {
    Object[] args = {department.getDeptName()};
    int[] types = {Types.VARCHAR};
    return template.update(ADD_DEPARTMENT, args, types);
  }

  @Override
  public int DepartmentUpdate(int deptId, String deptName) {
    Object[] args = {deptName, deptId};
    int[] types = {Types.VARCHAR, Types.INTEGER};
    return template.update(UPDATE_DEPARTMENT, args, types);
  }

  @Override
  public int DepartmentDelete(int deptId) {
    Object[] args = {deptId};
    int[] types = {Types.INTEGER};
    return template.update(DELETE_DEPARTMENT, args, types);
  }

}

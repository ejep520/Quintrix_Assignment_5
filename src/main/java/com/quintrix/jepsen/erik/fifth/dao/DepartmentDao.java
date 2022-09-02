package com.quintrix.jepsen.erik.fifth.dao;

import com.quintrix.jepsen.erik.fifth.model.Department;

public interface DepartmentDao {
  public Department[] GetAllDepartments();

  public Department GetDeptByName(String deptName);

  public Department GetDeptByNumber(int deptNumber);

  public void CreateDeptTable(boolean forceRecreation);

  public void CreateDeptTable();

  public int DepartmentAdd(Department department);

  public int DepartmentUpdate(int deptId, String deptName);

  public int DepartmentDelete(int deptId);
}

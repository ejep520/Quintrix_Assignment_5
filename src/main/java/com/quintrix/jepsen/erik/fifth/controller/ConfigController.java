package com.quintrix.jepsen.erik.fifth.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import com.quintrix.jepsen.erik.fifth.dao.DepartmentDao;
import com.quintrix.jepsen.erik.fifth.dao.PersonDao;

@RestController
public class ConfigController {
  @Autowired
  private PersonDao personDao;
  @Autowired
  private DepartmentDao departmentDao;

  @GetMapping("/config/new/table/departments")
  public void configTableDepartmentsNew() {
    departmentDao.CreateDeptTable();
  }

  @GetMapping("/config/new/table/persons")
  public void configNewTablePersons() {
    personDao.createPersonsTable();
  }
}

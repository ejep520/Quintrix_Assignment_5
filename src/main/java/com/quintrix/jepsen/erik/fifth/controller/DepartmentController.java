package com.quintrix.jepsen.erik.fifth.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import com.quintrix.jepsen.erik.fifth.dao.DepartmentDao;
import com.quintrix.jepsen.erik.fifth.model.Department;

@RestController
public class DepartmentController {
  @Autowired
  DepartmentDao deptDao;

  @GetMapping("/departments")
  @ResponseBody
  public Department[] departments() {
    return deptDao.GetAllDepartments();
  }

  @GetMapping("/departments/{id}")
  @ResponseBody
  public Department GetDepartments(@PathVariable int id) {
    return deptDao.GetDeptByNumber(id);
  }

  @PutMapping("/departments/{id}")
  @ResponseBody
  public String PutDepartment(@PathVariable int id, @RequestParam String deptName) {
    if (deptDao.DepartmentUpdate(id, deptName) == 1)
      return "Success";
    return "Fail";
  }

  @DeleteMapping("/departments/{id}")
  @ResponseBody
  public String DeleteDepartment(@PathVariable int id) {
    if (deptDao.DepartmentDelete(id) == 1)
      return "Success";
    return "Fail";
  }

  @PostMapping("/departments/new")
  @ResponseBody
  public String departmentsNew(@RequestParam Department department) {
    if (deptDao.DepartmentAdd(department) == 1)
      return "Success";
    return "Fail";
  }
}

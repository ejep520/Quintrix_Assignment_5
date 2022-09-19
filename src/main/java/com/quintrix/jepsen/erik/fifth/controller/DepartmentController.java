package com.quintrix.jepsen.erik.fifth.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.quintrix.jepsen.erik.fifth.model.Department;
import com.quintrix.jepsen.erik.fifth.service.DepartmentService;

@RestController
public class DepartmentController {
  @Autowired
  private DepartmentService deptService;

  @GetMapping("/departments")
  public Department[] departments() {
    return deptService.findAll();
  }

  @GetMapping("/departments/{id}")
  public ResponseEntity<Department> GetDepartments(@PathVariable int id) {
    Department result = deptService.findById(id);
    if (result == null)
      return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    return new ResponseEntity<>(result, HttpStatus.OK);
  }

  @PutMapping("/departments/{id}")
  public ResponseEntity<Department> PutDepartment(@PathVariable int id,
      @RequestParam String deptName) {
    Department updatee = deptService.findById(id);
    if (updatee == null)
      return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    updatee.setDeptName(deptName);
    updatee = deptService.save(updatee);
    return new ResponseEntity<>(updatee, HttpStatus.OK);
  }

  @DeleteMapping("/departments/{id}")
  public ResponseEntity<String> DeleteDepartment(@PathVariable int id) {
    Department doomedDept = deptService.findById(id);
    if (doomedDept == null)
      return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    deptService.delete(doomedDept);
    return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
  }

  @PostMapping("/departments/new")
  public String departmentsNew(@RequestParam Department department) {
    if (deptService.save(department) != null)
      return "Success";
    return "Fail";
  }
}

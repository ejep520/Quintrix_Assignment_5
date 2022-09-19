package com.quintrix.jepsen.erik.fifth.service;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.quintrix.jepsen.erik.fifth.dao.DepartmentDao;
import com.quintrix.jepsen.erik.fifth.model.Department;

@Service
public class DepartmentService {
  @Autowired
  private DepartmentDao deptDao;

  public Department findById(int deptId) {
    return deptDao.findById(deptId).orElse(null);
  }

  public Department[] findAll() {
    Iterable<Department> results = deptDao.findAll();
    List<Department> resultsList = new ArrayList<>();
    for (Department dept : results)
      resultsList.add(dept);
    return resultsList.toArray(new Department[resultsList.size()]);
  }

  public Department save(Department dept) {
    return deptDao.save(dept);
  }

  public void delete(Department dept) {
    deptDao.delete(dept);
  }

}

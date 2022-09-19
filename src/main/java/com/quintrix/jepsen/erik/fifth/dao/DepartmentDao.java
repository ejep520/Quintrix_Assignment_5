package com.quintrix.jepsen.erik.fifth.dao;

import org.springframework.data.repository.CrudRepository;
import com.quintrix.jepsen.erik.fifth.model.Department;

public interface DepartmentDao extends CrudRepository<Department, Integer> {
}

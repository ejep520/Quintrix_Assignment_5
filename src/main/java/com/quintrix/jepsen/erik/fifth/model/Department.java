package com.quintrix.jepsen.erik.fifth.model;

public class Department {
  private String deptName;
  private Integer deptNumber;

  public Department(String deptName) {
    this.deptName = deptName;
    deptNumber = -1;
  }

  public String getDeptName() {
    return deptName;
  }

  public void setDeptName(String deptName) {
    this.deptName = deptName;
  }

  public Integer getDeptNumber() {
    return deptNumber;
  }

  public void setDeptNumber(Integer deptNumber) {
    this.deptNumber = deptNumber;
  }

  @Override
  public boolean equals(Object b) {
    if (b == null)
      return false;
    if (this.getClass() != b.getClass())
      return false;
    return (deptName.equals(((Department) b).getDeptName()))
        && (deptNumber.equals(((Department) b).getDeptNumber()));
  }
}

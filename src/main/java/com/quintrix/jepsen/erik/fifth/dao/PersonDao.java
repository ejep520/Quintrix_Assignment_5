package com.quintrix.jepsen.erik.fifth.dao;

import javax.sql.DataSource;
import com.quintrix.jepsen.erik.fifth.model.Person;

public interface PersonDao {
  public void setDataSource(DataSource ds);

  /**
   * Get all persons from the database.
   * 
   * @return an array of all persons in the database
   */
  public Person[] GetPersons();

  /**
   * Get all persons from the database with the provided last name.
   * 
   * @param lName The last name of the persons being sought.
   * @return An array of the persons with the provided last name.
   */
  public Person[] GetPersonsByLastName(String lName);

  /**
   * Get all persons from the database with the provided first name.
   * 
   * @param fName The first (or given) name of the persons being sought.
   * @return An array of persons matching the search.
   */
  public Person[] GetPersonsByFirstName(String fName);

  public Person GetPersonById(int personId);

  public Person[] GetPersonsByDeptId(int deptId);

  /**
   * Creates the "persons" table; forcibly replacing it if necessary.
   * 
   * @param forceReplacement Indicates whether the table should be forcibly recreated if it already
   *        exists.
   */
  public void CreatePersonsTable(boolean forceReplacement);

  /**
   * Creates the "persons" table if it doesn't already exist.
   */
  public void CreatePersonsTable();

  /**
   * Creates a new person in the DB.
   * 
   * @param person The data to be written into the DB.
   * @return The number of entries affected. != 1 indicates failure.
   */
  public int PersonNew(Person person);

  /**
   * Replaces the existing data for a specified person, overwriting all previous info regardless of
   * differences.
   * 
   * @param person The data to be written into the DB.
   * @return The number of entries affected. != 1 indicates failure.
   */
  public int UpdatePerson(Person person);

  /**
   * Replaces the first name of a person.
   * 
   * @param personId The ID of the person to be renamed.
   * @param fName The new first (or given) name to be written to the DB.
   * @return The number of entries affected. != 1 indicates failure.
   */
  public int UpdatePersonFName(int personId, String fName);

  /**
   * Replaces the last name of a person.
   * 
   * @param personId The ID of the person to be renamed.
   * @param lName The new last (or family) name to be written to the DB.
   * @return The number of entries affected. != 1 indicates failure.
   */
  public int UpdatePersonLName(int personId, String lName);

  /**
   * Replaces the department ID assigned to a person.
   * 
   * @param personId The ID of the person being reassigned.
   * @param deptId The ID of the department the person is being reassigned to.
   * @return The number of entries affected. != 1 indicates failure.
   */
  public int UpdatePersonDept(int personId, int deptId);

  public int DeletePerson(int personId);
}

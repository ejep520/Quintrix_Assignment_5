package com.quintrix.jepsen.erik.fifth.dao;

import java.sql.Types;
import java.util.List;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import com.quintrix.jepsen.erik.fifth.model.Person;

@Repository
public class PersonDaoImpl implements PersonDao {
  private JdbcTemplate template;
  private final String CreateTableSafe = "CREATE TABLE IF NOT EXISTS persons "
      + "(personId INTEGER NOT NULL AUTO_INCREMENT, " + "fName TINYTEXT, " + "lname TINYTEXT, "
      + "deptId INTEGER, " + "CONSTRAINT PK_Person PRIMARY KEY (personId), "
      + "CONSTRAINT FK_Dept FOREIGN KEY (deptId) REFERENCES departments(deptId));";
  private final String CreateTableUnsafe = "DROP TABLE IF EXISTS persons; "
      + "CREATE TABLE persons " + "(personId INTEGER NOT NULL AUTO_INCREMENT, " + "fName TINYTEXT, "
      + "lname TINYTEXT, " + "deptId INTEGER, " + "CONSTRAINT PK_Person PRIMARY KEY (personId), "
      + "CONSTRAINT FK_Dept FOREIGN KEY (deptId) REFERENCES departments(deptId));";
  private final String GET_ALL = "SELECT * FROM persons;";
  private final String GET_SOME_LIKE = "SELECT * FROM persons WHERE persons.%s LIKE ?;";
  private final String GET_SOME_EQ = "SELECT * FROM persons WHERE %s = ?;";
  private final String GET_ONE = "SELECT * FROM persons WHERE personId = ?;";
  private final String ADD_PERSON = "INSERT INTO persons (fName, lName, deptId) VALUES (?, ?, ?);";
  private final String UPDATE_PERSON =
      "UPDATE persons SET fName = ?, lName = ?, deptId = ? WHERE personId = ?;";
  private final String UPDATE_PERSON_FORMAT = "UPDATE persons SET %s = ? WHERE personId = ?;";
  private final String DELETE_PERSON = "DELETE FROM persons WHERE personId = ?;";

  @Autowired
  public PersonDaoImpl(DataSource ds) {
    template = new JdbcTemplate(ds);
  }

  @Override
  public Person[] GetPersons() {
    List<Person> allPersons = template.query(GET_ALL, new PersonMapper());
    return allPersons.toArray(new Person[allPersons.size()]);
  }

  @Override
  public Person[] GetPersonsByLastName(String lName) {
    return GetSomePersons(lName, "lName");
  }

  @Override
  public Person[] GetPersonsByFirstName(String fName) {
    return GetSomePersons(fName, "fName");
  }

  private Person[] GetSomePersons(String searchKey, String field) {
    Object[] args = {searchKey};
    int[] types = {Types.VARCHAR};
    List<Person> somePersons =
        template.query(String.format(GET_SOME_LIKE, field), args, types, new PersonMapper());
    return somePersons.toArray(new Person[somePersons.size()]);
  }

  @Override
  public void CreatePersonsTable(boolean forceReplacement) {
    if (forceReplacement)
      template.execute(CreateTableUnsafe);
    else
      template.execute(CreateTableSafe);
  }

  @Override
  public void CreatePersonsTable() {
    CreatePersonsTable(false);
  }

  @Override
  public void setDataSource(DataSource ds) {
    template = new JdbcTemplate(ds);
  }

  @Override
  public int PersonNew(Person person) {
    Object[] args = {person.getfName(), person.getlName(), person.getDeptId()};
    int[] types = {Types.VARCHAR, Types.VARCHAR, Types.INTEGER};
    return template.update(ADD_PERSON, args, types);
  }

  @Override
  public int UpdatePerson(Person person) {
    Object[] args =
        {person.getfName(), person.getlName(), person.getDeptId(), person.getPersonId()};
    int[] types = {Types.VARCHAR, Types.VARCHAR, Types.INTEGER, Types.INTEGER};
    return template.update(UPDATE_PERSON, args, types);
  }

  @Override
  public int UpdatePersonFName(int personId, String fName) {
    Object[] args = {fName, personId};
    int[] types = {Types.VARCHAR, Types.INTEGER};
    return template.update(String.format(UPDATE_PERSON_FORMAT, "fName"), args, types);
  }

  @Override
  public int UpdatePersonLName(int personId, String lName) {
    Object[] args = {lName, personId};
    int[] types = {Types.VARCHAR, Types.INTEGER};
    return template.update(String.format(UPDATE_PERSON_FORMAT, "lName"), args, types);
  }

  @Override
  public int UpdatePersonDept(int personId, int deptId) {
    Object[] args = {deptId, personId};
    int[] types = {Types.INTEGER, Types.INTEGER};
    return template.update(String.format(UPDATE_PERSON_FORMAT, "deptId"), args, types);
  }

  @Override
  public Person GetPersonById(int personId) {
    Object[] args = {personId};
    int[] types = {Types.INTEGER};
    return template.query(GET_ONE, args, types, new PersonExtractor());
  }

  private Person[] GetSomeEq(String field, int searchKey) {
    Object[] args = {searchKey};
    int[] types = {Types.INTEGER};
    List<Person> somePersons =
        template.query(String.format(GET_SOME_EQ, field), args, types, new PersonMapper());
    return somePersons.toArray(new Person[somePersons.size()]);
  }

  @Override
  public Person[] GetPersonsByDeptId(int deptId) {
    return GetSomeEq("deptId", deptId);
  }

  @Override
  public int DeletePerson(int personId) {
    Object[] args = {personId};
    int[] types = {Types.INTEGER};
    return template.update(DELETE_PERSON, args, types);
  }
}

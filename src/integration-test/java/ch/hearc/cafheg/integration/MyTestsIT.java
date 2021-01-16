package ch.hearc.cafheg.integration;


import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import ch.hearc.cafheg.business.allocations.Allocataire;
import ch.hearc.cafheg.business.allocations.AllocationService;
import ch.hearc.cafheg.infrastructure.persistance.AllocataireMapper;
import ch.hearc.cafheg.infrastructure.persistance.AllocationMapper;
import ch.hearc.cafheg.infrastructure.persistance.Database;
import ch.hearc.cafheg.infrastructure.persistance.Migrations;
import ch.hearc.cafheg.infrastructure.persistance.VersementMapper;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import org.dbunit.DatabaseUnitException;
import org.dbunit.database.DatabaseConnection;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.dbunit.operation.DatabaseOperation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class MyTestsIT {

  private AllocataireMapper allocataireMapper;
  private AllocationMapper allocationMapper;
  private VersementMapper versementMapper;
  private AllocationService allocationService;


  @BeforeEach
  void setUp() {
    allocataireMapper = new AllocataireMapper();
    allocationMapper = new AllocationMapper();
    versementMapper = new VersementMapper();
    allocationService = new AllocationService(allocataireMapper, allocationMapper, versementMapper);

  }

  @Test
  void testSimple_Given1_ShouldBe1() {
    assertTrue(1 == 1);
  }

  @Test
  void deleteAllocataire_Given5Employees_ShouldHave4EmployeesAfterRemoval()
      throws SQLException, DatabaseUnitException {
    Database database = new Database();
    database.start();
    Migrations migrations = new Migrations(database, "classpath:db/ddl");
    migrations.start();

    try (Connection connection = database.getDataSource().getConnection()) {
      DatabaseConnection conn = new DatabaseConnection(connection);
      IDataSet dataSet = new FlatXmlDataSetBuilder()
          .build(getClass().getClassLoader().getResourceAsStream("mydataset.xml"));
      DatabaseOperation.CLEAN_INSERT.execute(conn, dataSet);
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }

    Database.inTransaction(
        () -> allocationService.deleteAllocataire(1)
    );

    List<Allocataire> allocataires = Database.inTransaction(
        () -> allocationService.findAllAllocataires("Geiser")
    );

    assertThat(allocataires.size()).isEqualTo(4);
  }


  @Test
  void updateAllocataire_ShouldReturnUpdatedAllocataire()
      throws SQLException, DatabaseUnitException {
    Database database = new Database();
    database.start();
    Migrations migrations = new Migrations(database, "classpath:db/ddl");
    migrations.start();

    try (Connection connection = database.getDataSource().getConnection()) {
      DatabaseConnection conn = new DatabaseConnection(connection);
      IDataSet dataSet = new FlatXmlDataSetBuilder()
          .build(getClass().getClassLoader().getResourceAsStream("mydataset.xml"));
      DatabaseOperation.CLEAN_INSERT.execute(conn, dataSet);
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }

    Allocataire allocataireBeforeChange = Database.inTransaction(
        () -> allocataireMapper.findById(1)
    );
    Allocataire allocataireToChange = Database.inTransaction(
        () -> allocataireMapper.findById(1)
    );

    Database.inTransaction(
        () -> allocationService.updateAllocataire(allocataireToChange, "Geyser", "Kendrick")
    );

    Allocataire allocataireAfterChange = Database.inTransaction(
        () -> allocataireMapper.findById(1)
    );

    assertNotEquals(allocataireBeforeChange, allocataireAfterChange);

  }
}
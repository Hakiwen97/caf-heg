package ch.hearc.cafheg.integration;


import ch.hearc.cafheg.business.allocations.Allocataire;
import ch.hearc.cafheg.business.allocations.AllocationService;

import ch.hearc.cafheg.infrastructure.persistance.AllocataireMapper;
import ch.hearc.cafheg.infrastructure.persistance.AllocationMapper;
import ch.hearc.cafheg.infrastructure.persistance.Database;

import ch.hearc.cafheg.infrastructure.persistance.Migrations;
import ch.hearc.cafheg.infrastructure.persistance.VersementMapper;

import com.fasterxml.jackson.annotation.JsonTypeInfo.As;
import java.sql.Connection;
import java.util.List;
import org.dbunit.DatabaseUnitException;

import org.dbunit.database.DatabaseConnection;

import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.dbunit.operation.*;
import org.junit.Assert;


import java.sql.SQLException;

import org.mockito.Mockito;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class MyTestsIT {

    private AllocataireMapper allocataireMapper;
    private AllocationMapper allocationMapper;
    private VersementMapper versementMapper;
    private AllocationService allocationService;


    @BeforeEach
    void setUp() {
       allocataireMapper = Mockito.mock(AllocataireMapper.class);
       allocationMapper = Mockito.mock(AllocationMapper.class);
        versementMapper = Mockito.mock(VersementMapper.class);

        allocationService = new AllocationService(allocataireMapper, allocationMapper, versementMapper);

    }
    @Test
    void testSimple_Given1_ShouldBe1(){
        assertTrue(1==1);
    }

    @Test
    void deleteAllocataire_Given5Employees_ShouldHave4EmployeesAfterRemoval() throws SQLException,DatabaseUnitException{
        Database database = new Database();
        database.start();
        Migrations migrations = new Migrations(database, "classpath:db/ddl");
        migrations.start();


        try (Connection connection = database.getDataSource().getConnection()){
          DatabaseConnection conn = new DatabaseConnection(connection);
          IDataSet dataSet = new FlatXmlDataSetBuilder().build(getClass().getClassLoader().getResourceAsStream("mydataset.xml"));
          DatabaseOperation.CLEAN_INSERT.execute(conn,dataSet);
        }catch (SQLException e){
          throw new RuntimeException(e);
        }
        //Code métier

        AllocationService service = new AllocationService(allocataireMapper,allocationMapper,versementMapper);
        List<Allocataire> allocataires = service.findAllAllocataires("Geiser");

        assertThat(allocataires.size()).isEqualTo(5);

    }




}

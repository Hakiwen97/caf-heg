package ch.hearc.cafheg.integration;

import ch.hearc.cafheg.infrastructure.persistance.Database;
import ch.hearc.cafheg.infrastructure.persistance.Mapper;
import org.dbunit.DatabaseUnitException;
import org.dbunit.IDatabaseTester;
import org.dbunit.database.DatabaseConnection;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class MyTestsIT {
    @BeforeEach
    void setUp() throws SQLException, DatabaseUnitException {

        var connection = new DatabaseConnection(new Database().getDataSource().getConnection());
        IDataSet dataSet = new FlatXmlDataSetBuilder().build(getClass().getClassLoader().getResourceAsStream("mydataset.xml"));
    }
    @Test
    void testSimple_Given1_ShouldBe1(){
        assertTrue(1==1);
    }

    @Test
    void deleteAllocataire_Given4Employees_ShouldHave3EmployeesAfterRemoval(){
        Database db=new Database();
        var connection = new DatabaseConnection(db.getDataSource().getConnection());
       ;

    }




}

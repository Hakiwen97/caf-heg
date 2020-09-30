package ch.hearc.cafheg.infrastructure.persistance;

import javax.sql.DataSource;
import org.flywaydb.core.Flyway;

public class Migrations {

  private final Database database;

  public Migrations(Database database) {
    this.database = database;
  }

  public void start() {
    System.out.println("Doing migrations");
    Flyway flyway = Flyway.configure().dataSource(database.getDataSource()).locations("classpath:db").load();
    flyway.migrate();
    System.out.println("Migrations done");
  }

}

package ch.hearc.cafheg.infrastructure.persistance;

import org.flywaydb.core.Flyway;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Migrations {

  private static final Logger logger = LoggerFactory.getLogger(Migrations.class);

  private final Database database;
  // private final boolean forTest;
  private final String location;


  public Migrations(Database database, String location) {
    this.database = database;
    // this.forTest = false;
    this.location = location;
  }
//  public Migrations(Database database, Boolean forTest) {
//    this.database = database;
//    this.forTest = forTest;
//  }


  public void start() {
    logger.debug("Doing migrations");

//    String location;
//    if (forTest) {
//      location = "classpath:db/ddl";
//    } else {
//      location = "classpath:db";
//    }

    Flyway flyway = Flyway.configure()
        .dataSource(database.getDataSource())
        .locations(location)
        .load();

    flyway.migrate();
    logger.debug("Migrations done");
  }

}

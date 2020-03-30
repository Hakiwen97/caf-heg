package ch.hearc.cafheg.infrastructure.application;

import ch.hearc.cafheg.infrastructure.persistance.Database;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import java.util.function.Supplier;
import org.flywaydb.core.Flyway;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "ch.hearc.cafheg")
public class Application {

  public static void main(String[] args) {
    Database database = Database.initialize();
    SpringApplication.run(Application.class, args);
  }
}

package ch.hearc.cafheg.infrastructure.application;

import ch.hearc.cafheg.infrastructure.persistance.Database;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import java.util.function.Supplier;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import org.flywaydb.core.Flyway;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "ch.hearc.cafheg")
public class Application extends SpringBootServletInitializer {

  public static void main(String[] args) {
    init();
    SpringApplication.run(Application.class, args);
  }

  private static void init() {
    Database.initialize();
  }

  @Override
  public void onStartup(ServletContext servletContext) throws ServletException {
    super.onStartup(servletContext);
    init();
  }
}

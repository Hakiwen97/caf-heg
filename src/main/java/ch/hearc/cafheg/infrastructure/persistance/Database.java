package ch.hearc.cafheg.infrastructure.persistance;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.function.Supplier;
import javax.sql.DataSource;
import org.flywaydb.core.Flyway;

public class Database {
  private static DataSource dataSource;
  private static ThreadLocal<Connection> connection = new ThreadLocal<>();

  private Database() {}

  public static Database initialize() {
    Database database = new Database();
    database.init();
    return database;
  }

  public static Connection getConnection() {
    return connection.get();
  }

  public static <T> T inTransaction(Supplier<T> inTransaction) {
    try {
      connection.set(dataSource.getConnection());
      return inTransaction.get();
    } catch (Exception e) {
      throw new RuntimeException(e);
    } finally {
      try {
        connection.get().close();
      } catch (SQLException e) {
        throw new RuntimeException(e);
      }
      connection.remove();
    }
  }

  private void init() {
    System.out.println("Initializing datasource");
    HikariConfig config = new HikariConfig();
    config.setJdbcUrl("jdbc:h2:mem:sample");
    config.setMaximumPoolSize(20);
    config.setDriverClassName("org.h2.Driver");
    dataSource = new HikariDataSource(config);
    System.out.println("Datasource initialized");

    System.out.println("Doing migrations");
    Flyway flyway = Flyway.configure().dataSource(dataSource).locations("classpath:db").load();
    flyway.migrate();
    System.out.println("Migrations done");
  }
}

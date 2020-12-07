package ch.hearc.cafheg.infrastructure.persistance;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.function.Supplier;
import javax.sql.DataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Database {

  private static final Logger logger = LoggerFactory.getLogger(Database.class);
  private static DataSource dataSource;
  private static ThreadLocal<Connection> connection = new ThreadLocal<>();

  public Database() {
  }

  static Connection getConnection() {
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

  public DataSource getDataSource() {
    return dataSource;
  }

  /**
   * public void start() { logger.info("Initializing datasource"); HikariConfig config = new
   * HikariConfig(); config.setJdbcUrl("jdbc:h2:mem:sample"); config.setMaximumPoolSize(20);
   * config.setDriverClassName("org.h2.Driver"); dataSource = new HikariDataSource(config);
   * logger.info("Datasource initialized");}
   **/

  public void start() {
    logger.info("Initializing datasource");
    HikariConfig config = new HikariConfig();
    config.setJdbcUrl("jdbc:h2:~/testt");
    config.setMaximumPoolSize(20);
    config.setDriverClassName("org.h2.Driver");
    dataSource = new HikariDataSource(config);
    logger.info("Datasource initialized");
  }
}

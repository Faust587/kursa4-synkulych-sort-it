package ua.synkulych.sort_it.database;

import com.mysql.cj.jdbc.MysqlDataSource;
import ua.synkulych.sort_it.entity.Response;

import java.sql.Connection;
import java.sql.SQLException;

public class DatabaseSQL {
  private String user, password, serverName, databaseName;
  private int port;
  private Connection connection = null;

  /**
   * DatabaseSQL constructor which can do actions to connected MySQL database
   * @param user
   * @param password
   * @param serverName
   * @param databaseName
   * @param port
   */
  public DatabaseSQL(String user, String password, String serverName, String databaseName, int port) {
    this.user = user;
    this.password = password;
    this.serverName = serverName;
    this.databaseName = databaseName;
    this.port = port;
  }

  public Response<Boolean> connect(String user, String password, String serverName, String databaseName, int port) {
    Response<Boolean> response = new Response<>();
    /*
     * This is constants for connection to database
     * TODO ask question to teacher about constant for connection to database;
     */

    MysqlDataSource dataSource = new MysqlDataSource();
    dataSource.setUser(user);
    dataSource.setPassword(password);
    dataSource.setServerName(serverName);
    dataSource.setDatabaseName(databaseName);
    dataSource.setPort(port);

    try {
      dataSource.setServerTimezone("UTC");
    } catch (SQLException ex) {
      response.setOK(false);
      response.setTitle("Connection error");
      response.setDescription(ex+"");
    }
    try {
      dataSource.setCharacterEncoding("utf8");
    } catch (SQLException ex) {
      response.setOK(false);
      response.setTitle("Connection error");
      response.setDescription(ex+"");
    }
    try {
      connection=dataSource.getConnection();
      response.setOK(true);
    } catch (SQLException ex) {
      connection=null;
      response.setOK(false);
      response.setTitle("Connection error");
      response.setDescription(ex+"");
    }

    return response;
  }
}

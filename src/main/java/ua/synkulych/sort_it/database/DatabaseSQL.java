package ua.synkulych.sort_it.database;

import com.mysql.cj.jdbc.MysqlDataSource;
import ua.synkulych.sort_it.entity.Response;
import ua.synkulych.sort_it.entity.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DatabaseSQL implements DatabaseService {
  private String user, password, serverName, databaseName;
  private int port;
  private Connection connection = null;

  /**
   * DatabaseSQL constructor which can do actions to connected MySQL database
   * @param user this is a string variable, admin username
   * @param password this is a string variable, password for the admin account
   * @param serverName this is a string variable, web path to the server
   * @param databaseName this is a string variable, name of the database
   * @param port this is an integer variable, port of the server
   */
  public DatabaseSQL(String user, String password, String serverName, String databaseName, int port) {
    this.user = user;
    this.password = password;
    this.serverName = serverName;
    this.databaseName = databaseName;
    this.port = port;
  }

  /**
   * This method connect application to MySQL database
   * @return Response<Boolean> with all errors if they are
   */
  public Response<Boolean> connect() {
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

  /**
   * This method add password and unique username to the MySQL database
   * @param username this is a string variable, must be unique
   * @param password this is a string variable
   * @return Response<Boolean>
   */
  public Response<Boolean> addNewUserToDatabase(String username, String password) {
    Response<Boolean> response = new Response<>();

    if (!userNameIsValid(username)) {
      response.setOK(false);
      response.setTitle("Validation error");
      response.setDescription("Username should be more then 3 symbols and less then 12 symbols");
      return response;
    }

    if (!userPasswordIsValid(password)) {
      response.setOK(false);
      response.setTitle("Validation error");
      response.setDescription("Password should be more then 4 symbols and less then 8 symbols");
      return response;
    }

    String SQL= "INSERT INTO `users` (`id`, `username`, `password`, `points`) VALUES (NULL,?,?,DEFAULT)";
    PreparedStatement statement;
    try {
      statement = connection.prepareStatement(SQL);
      statement.setString(1, username);
      statement.setString(2, password);
      statement.executeUpdate();
      User.setUsername(username);
      response.setOK(true);
    } catch (SQLException ex) {
      response.setOK(false);
      if (ex.toString().contains("Duplicate entry")) {
        response.setTitle("Duplicate name");
        response.setDescription("This name has already taken, choose another name");
      } else {
        response.setTitle("ERROR");
        response.setDescription(ex + "");
      }
    }
    return response;
  }
}

package ua.synkulych.sort_it.database;

import com.mysql.cj.jdbc.MysqlDataSource;

import ua.synkulych.sort_it.entity.Rating;
import ua.synkulych.sort_it.entity.Response;
import ua.synkulych.sort_it.entity.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class DatabaseSQL implements DatabaseService {
  private String user, password, serverName, databaseName;
  private int port;
  public static Connection connection = null;

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

    if (connection != null && checkConnection()) {
      response.setOK(true);
      return response;
    }

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
      response.setDescription(parseError(ex + ""));
    }
    try {
      dataSource.setCharacterEncoding("utf8");
    } catch (SQLException ex) {
      response.setOK(false);
      response.setTitle("Connection error");
      response.setDescription(parseError(ex + ""));
    }
    try {
      connection=dataSource.getConnection();
      response.setOK(true);
    } catch (SQLException ex) {
      connection=null;
      response.setOK(false);
      response.setTitle("Connection error");
      response.setDescription(parseError(ex + ""));
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
        response.setDescription(parseError(ex + ""));
      }
    }
    return response;
  }

  /**
   * This method create a rating list of users
   * @return all info about users in rating list
   */
  public Response<ArrayList<Rating>> getRatingList() {
    Response<ArrayList<Rating>> response = new Response<>();

    String SQL= "SELECT username, points FROM users ORDER BY points DESC";
    PreparedStatement statement;
    ArrayList<Rating> ratingArrayList = new ArrayList<>();

    try {
      statement = connection.prepareStatement(SQL);
      ResultSet result = statement.executeQuery();

      int rateCounter = 1;

      while (result.next()) {
        String username = result.getString(1);
        int points = result.getInt(2);
        ratingArrayList.add(new Rating(username, rateCounter, points));
        rateCounter++;
      }
    } catch (SQLException ex) {
      System.out.println(ex  + "");
    }
    return response;
  }

  /**
   * This method updates info about user's points
   * @param points this is integer variable, which is added to all user's points
   * @return response info about database updating
   */
  public Response<Boolean> addPointsToUserRating(int points) {
    Response<Boolean> response = new Response<>();

    String SQL= "UPDATE `users` SET points = points + ? WHERE username=?";
    PreparedStatement statement;
    try {
      statement = connection.prepareStatement(SQL);
      statement.setInt(1, points);
      statement.setString(2, User.getUsername());
      int result = statement.executeUpdate();
      response.setOK(result == 1);
      response.setTitle("Unknown database error");
      response.setDescription("Unknown database error, please try again");
    } catch (SQLException ex) {
      response.setOK(false);
      response.setTitle("SERVER ERROR");
      response.setDescription(parseError(ex + ""));
    }
    return response;
  }

  /**
   * This method check that user data is correct
   * @param username this is a string variable
   * @param password this is a string variable
   * @return response about login operation
   */
  public Response<Boolean> userLogIn(String username, String password) {
    Response<Boolean> response = new Response<>();

    String SQL= "SELECT username, password FROM users WHERE username=?";
    PreparedStatement statement;
    try {
      statement = connection.prepareStatement(SQL);
      statement.setString(1, username);
      ResultSet result = statement.executeQuery();
      if (result.next()) {
        if (result.getString(2).equals(password)) {
          response.setOK(true);
        } else {
          response.setOK(false);
          response.setTitle("Incorrect password");
          response.setDescription("Please try again with another password");
        }
      } else {
        response.setOK(false);
        response.setTitle("This username is not exists");
        response.setDescription("Check that your username is correct");
      }
    } catch (SQLException ex) {
      response.setOK(false);
      response.setTitle("SERVER ERROR");
      response.setDescription(parseError(ex + ""));
    }
    return response;
  }
}

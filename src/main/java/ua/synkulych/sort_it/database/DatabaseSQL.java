package ua.synkulych.sort_it.database;

import com.google.gson.Gson;
import com.mysql.cj.jdbc.MysqlDataSource;

import ua.synkulych.sort_it.constants.PathConstants;
import ua.synkulych.sort_it.entity.AlertWindow;
import ua.synkulych.sort_it.entity.Rating;
import ua.synkulych.sort_it.entity.Response;
import ua.synkulych.sort_it.entity.User;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class DatabaseSQL implements DatabaseUtils {
  private String user;
  private String password;
  private String serverName;
  private String databaseName;
  private int port;
  public Connection connection = null;

  public DatabaseSQL() {
    DatabaseConfig config = getConfig();
    this.user = config.getUser();
    this.password = config.getPassword();
    this.serverName = config.getServerName();
    this.databaseName = config.getDatabaseName();
    this.port = config.getPort();
  }

  public DatabaseConfig getConfig() {
    URL jsonPath = getClass().getResource(PathConstants.DATABASE_CONFIG);
    DatabaseConfig config;
    try {
      assert jsonPath != null;
      BufferedReader read = new BufferedReader(new InputStreamReader(jsonPath.openStream()));
      StringBuilder jsonValue = new StringBuilder();
      String i;
      while ((i = read.readLine()) != null)
        jsonValue.append(i);
      read.close();
      config = new Gson().fromJson(jsonValue.toString(), DatabaseConfig.class);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    return config;
  }

  /**
   * This method connect application to MySQL database
   * @return Response<Boolean> with all errors if they are
   */
  public boolean connect() {
    MysqlDataSource dataSource = new MysqlDataSource();
    dataSource.setUser(user);
    dataSource.setPassword(password);
    dataSource.setServerName(serverName);
    dataSource.setDatabaseName(databaseName);
    dataSource.setPort(port);

    try {
      dataSource.setServerTimezone("UTC");
      dataSource.setCharacterEncoding("utf8");
      connection=dataSource.getConnection();
      return true;
    } catch (SQLException ex) {
      new AlertWindow("Connection error", parseError(ex + ""));
      return false;
    }
  }

  /**
   * This method add password and unique username to the MySQL database
   * @param username this is a string variable, must be unique
   * @param password this is a string variable
   * @return Response<Boolean>
   */
  public boolean addNewUserToDatabase(String username, String password) {
    boolean connect = connect();
    if (!connect) return false;

    if (!userNameIsValid(username)) {
      new AlertWindow("Validation error", "Username should be more then 3 symbols and less then 12 symbols");
      return false;
    }

    if (!userPasswordIsValid(password)) {
      new AlertWindow("Validation error", "Password should be more then 4 symbols and less then 8 symbols");
      return false;
    }

    boolean sqlResult = false;
    String SQL= "INSERT INTO `users` (`id`, `username`, `password`, `points`) VALUES (NULL,?,?,DEFAULT)";
    PreparedStatement statement = null;
    try {
      statement = connection.prepareStatement(SQL);
      statement.setString(1, username);
      statement.setString(2, password);
      statement.executeUpdate();
      User.setUsername(username);
      sqlResult = true;
    } catch (SQLException ex) {
      if (ex.toString().contains("Duplicate entry")) {
        new AlertWindow("Duplicate name", "This name has already taken, choose another name");
      } else {
        new AlertWindow("ERROR", parseError(ex + ""));
      }
    } finally {
      closeQuietly(connection);
      closeQuietly(statement);
    }
    return sqlResult;
  }

  /**
   * This method create a rating list of users
   * @return all info about users in rating list
   */
  public Response<ArrayList<Rating>> getRatingList() {
    Response<ArrayList<Rating>> response = new Response<>();

    boolean connect = connect();
    if (!connect) {
      response.setOK(false);
      return response;
    }

    String SQL= "SELECT username, points FROM users ORDER BY points DESC";
    PreparedStatement statement = null;
    ArrayList<Rating> ratingArrayList = new ArrayList<>();
    ResultSet result = null;
    try {
      statement = connection.prepareStatement(SQL);
      result = statement.executeQuery();

      int rateCounter = 1;

      while (result.next()) {
        String username = result.getString(1);
        int points = result.getInt(2);
        ratingArrayList.add(new Rating(username, rateCounter, points));
        rateCounter++;
      }
    } catch (SQLException ex) {
      new AlertWindow("Database error", parseError(ex + ""));
      response.setOK(false);
    } finally {
      closeQuietly(connection);
      closeQuietly(statement);
      closeQuietly(result);
    }
    response.setValue(ratingArrayList);
    return response;
  }

  /**
   * This method updates info about user's points
   * @param points this is integer variable, which is added to all user's points
   */
  public void addPointsToUserRating(int points) {
    boolean connect = connect();
    if (!connect) return;
    Response<Boolean> response = new Response<>();

    String SQL= "UPDATE `users` SET points = points + ? WHERE username=?";
    PreparedStatement statement = null;
    try {
      statement = connection.prepareStatement(SQL);
      statement.setInt(1, points);
      statement.setString(2, User.getUsername());
      int result = statement.executeUpdate();
      if (result != 1) {
        new AlertWindow("Unknown database error", "Unknown database error, please try again");
      }
    } catch (SQLException ex) {
      new AlertWindow("SERVER ERROR", parseError(ex + ""));
    } finally {
      closeQuietly(connection);
      closeQuietly(statement);
    }
  }

  /**
   * This method check that user data is correct
   * @param username this is a string variable
   * @param password this is a string variable
   * @return response about login operation
   */
  public boolean userLogIn(String username, String password) {
    boolean connect = connect();
    if (!connect) return false;

    boolean sqlResult = false;
    String SQL= "SELECT username, password FROM users WHERE username=?";
    PreparedStatement statement = null;
    ResultSet result = null;
    try {
      statement = connection.prepareStatement(SQL);
      statement.setString(1, username);
      result = statement.executeQuery();
      if (result.next()) {
        if (result.getString(2).equals(password)) {
          sqlResult = true;
        } else {
          new AlertWindow("Incorrect password", "Please try again with another password");
        }
      } else {
        new AlertWindow("This username is not exists", "Check that your username is correct");
      }
    } catch (SQLException ex) {
      new AlertWindow("SERVER ERROR", parseError(ex + ""));
    } finally {
      closeQuietly(connection);
      closeQuietly(statement);
      closeQuietly(result);
    }
    return sqlResult;
  }
}

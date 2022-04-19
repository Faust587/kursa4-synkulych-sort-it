package ua.synkulych.sort_it.database;

import java.sql.Connection;
import java.sql.SQLException;

public interface DatabaseService {
  /**
   * Validate username;
   * @param username this is a string variable
   * @return boolean;
   */
  default boolean userNameIsValid(String username) {
    int usernameLength = username.length();
    return usernameLength >= 3 && usernameLength <= 12;
  }

  /**
   * Validate password
   * @param password this is a string variable
   * @return boolean;
   */
  default boolean userPasswordIsValid(String password) {
    int usernameLength = password.length();
    return usernameLength >= 4 && usernameLength <= 8;
  }

  default void closeConnection(Connection connection) {
    try {
      connection.close();
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }
}

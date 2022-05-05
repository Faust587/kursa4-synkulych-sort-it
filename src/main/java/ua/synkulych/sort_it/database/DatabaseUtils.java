package ua.synkulych.sort_it.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public interface DatabaseUtils {
  /**
   * Validate username;
   * @param username this is a string variable
   * @return boolean;
   */

  default boolean userNameIsValid(String username) {
    int usernameLength = username.length();
    return usernameLength >= 3 && usernameLength <= 12;
  }

  default void closeQuietly(Connection connection) {
    try {connection.close();} catch (SQLException ignored) {}
  }

  default void closeQuietly(PreparedStatement statement) {
    try {statement.close();} catch (SQLException ignored) {}
  }

  default void closeQuietly(ResultSet resultSet) {
    try {resultSet.close();} catch (SQLException ignored) {}
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

  default String parseError(String error) {
    StringBuilder parsedError = new StringBuilder();
    String[] arraySrt = error.split(" ");
    for (int i = 1; i < arraySrt.length; i++) {
      parsedError.append(arraySrt[i]).append(" ");
    }
    return parsedError.toString();
  }
}

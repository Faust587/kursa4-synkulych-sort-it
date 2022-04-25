package ua.synkulych.sort_it.database;

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

  default String parseError(String error) {
    StringBuilder parsedError = new StringBuilder();
    String[] arraySrt = error.split(" ");
    for (int i = 1; i < arraySrt.length; i++) {
      parsedError.append(arraySrt[i]).append(" ");
    }
    return parsedError.toString();
  }

  /**
   * This method check connection to database
   * @return boolean
   */
  default boolean checkConnection() {
    boolean result;
    try {
      result = DatabaseSQL.connection.isValid(3);
    } catch (SQLException e) {
      result = false;
    }
    return result;
  }
}

package ua.synkulych.sort_it.database;

public class DatabaseConfig {
  private String user;
  private String password;
  private String serverName;
  private String databaseName;
  private int port;

  public void setPassword(String password) {
    this.password = password;
  }

  public void setUser(String user) {
    this.user = user;
  }

  public void setDatabaseName(String databaseName) {
    this.databaseName = databaseName;
  }

  public void setServerName(String serverName) {
    this.serverName = serverName;
  }

  public void setPort(int port) {
    this.port = port;
  }

  public String getPassword() {
    return password;
  }

  public int getPort() {
    return port;
  }

  public String getServerName() {
    return serverName;
  }

  public String getUser() {
    return user;
  }
  public String getDatabaseName() {
    return databaseName;
  }
}

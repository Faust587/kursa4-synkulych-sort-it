package ua.synkulych.sort_it.entity;

import com.google.gson.Gson;
import ua.synkulych.sort_it.constants.PathConstants;
import ua.synkulych.sort_it.database.DatabaseConfig;

import java.io.*;
import java.net.URL;

public class UserConfig {
  private String language;
  private String theme;

  public String getLanguage() {
    return language;
  }

  public String getTheme() {
    return theme;
  }

  public void setLanguage(String language) {
    this.language = language;
  }

  public void setTheme(String theme) {
    this.theme = theme;
  }

  public static UserConfig getUserConfig() {
    URL jsonPath = UserConfig.class.getResource(PathConstants.USER_CONFIG);
    UserConfig config;
    try {
      assert jsonPath != null;
      BufferedReader read = new BufferedReader(new InputStreamReader(jsonPath.openStream()));
      StringBuilder jsonValue = new StringBuilder();
      String i;
      while ((i = read.readLine()) != null)
        jsonValue.append(i);
      read.close();
      config = new Gson().fromJson(jsonValue.toString(),UserConfig.class);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    return config;
  }

  public static void changeUserConfig(String parameter, String value) {
    UserConfig config = UserConfig.getUserConfig();
    switch (parameter) {
      case "language" -> config.setLanguage(value);
      case "theme" -> config.setTheme(value);
    }
    Gson gson = new Gson();

    URL jsonPath = UserConfig.class.getResource(PathConstants.USER_CONFIG);
    File jsonFile = new File(jsonPath.getFile());
    try {
      OutputStream outputStream = new FileOutputStream(jsonFile);
      outputStream.write(gson.toJson(config).getBytes());
      outputStream.flush();
    } catch (IOException e) {
      System.out.println(e);
      new AlertWindow("CHANGE ERROR", "SOMETHING WENT WRONG");
    }
  }
}

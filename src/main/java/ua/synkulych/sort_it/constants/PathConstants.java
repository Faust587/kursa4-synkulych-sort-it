package ua.synkulych.sort_it.constants;

import java.util.Objects;

public class PathConstants {
  public static String AlertView = "/views/AlertView.fxml";
  public static String GameView = "/views/GameView.fxml";
  public static String MenuView = "/views/MenuView.fxml";
  public static String RatingView = "/views/RatingView.fxml";
  public static String SETTINGS_VIEW = "/views/SettingsView.fxml";
  public static String SetDifficultView = "/views/SetDifficultView.fxml";
  public static String SignInView = "/views/SignInView.fxml";
  public static String SignUpView = "/views/SignUpView.fxml";
  public static String ErrorIcon = "/icons/error_icon.png";
  public static String Icon = "/icons/icon.png";
  public static String OopsIcon = "/icons/oops.png";
  public static String DATABASE_CONFIG = "/configs/database_config.json";
  public static String USER_CONFIG = "/configs/user_config.json";

  public static String smallStyleLight = Objects.requireNonNull(PathConstants.class.getResource("/styles/light/SmallStyles.css")).toExternalForm();
  public static String normalStyleLight = Objects.requireNonNull(PathConstants.class.getResource("/styles/light/MiddleStyles.css")).toExternalForm();
  public static String BigStyleLight = Objects.requireNonNull(PathConstants.class.getResource("/styles/light/BigStyles.css")).toExternalForm();
  public static String smallStyleDark = Objects.requireNonNull(PathConstants.class.getResource("/styles/dark/SmallStyles.css")).toExternalForm();
  public static String normalStyleDark = Objects.requireNonNull(PathConstants.class.getResource("/styles/dark/MiddleStyles.css")).toExternalForm();
  public static String BigStyleDark = Objects.requireNonNull(PathConstants.class.getResource("/styles/dark/BigStyles.css")).toExternalForm();
}

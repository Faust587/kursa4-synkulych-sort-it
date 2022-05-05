package ua.synkulych.sort_it.controllers;

import javafx.beans.Observable;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import ua.synkulych.sort_it.constants.PathConstants;
import ua.synkulych.sort_it.entity.AlertWindow;
import ua.synkulych.sort_it.entity.UserConfig;

import java.io.IOException;
import java.net.URL;
import java.util.Locale;
import java.util.Objects;
import java.util.ResourceBundle;

public interface WindowsServices {
  default void openNewWindow(Stage stage, String fxmlPath, String title, String difficult) {
    stage.hide();
    double width = stage.getWidth();
    double height = stage.getHeight();
    double posX = stage.getX();
    double posY = stage.getY();
    String lang = UserConfig.getUserConfig().getLanguage();
    Locale locale = new Locale(lang);
    ResourceBundle bundle = ResourceBundle.getBundle("language", locale);
    FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
    String stylePath = (Objects.equals(UserConfig.getUserConfig().getTheme(), "light")) ? PathConstants.normalStyleLight : PathConstants.normalStyleDark;
    Scene scene;
    try {
      loader.setResources(bundle);
      scene = new Scene(loader.load());
      scene.getStylesheets().add(stylePath);
      stage.setScene(scene);
    } catch (IOException e) {
      new AlertWindow("Load error", e + "");
    }
    stage.widthProperty().addListener((Observable e) -> styleHandler(e, stage));
    stage.setTitle(title);

    switch (title) {
      case "Sort it" -> ((GameController) loader.getController()).init(stage, difficult);
      case "Rating" -> ((RatingController) loader.getController()).init(stage);
      case "Sign In", "Sign Up" -> ((AuthController) loader.getController()).init(stage);
      case "Difficult" -> ((SetDifficultController) loader.getController()).init(stage);
      case "Settings" -> ((SettingsController) loader.getController()).init(stage);
      default -> ((MenuController) loader.getController()).init(stage);
    }
    stage.setHeight(height);
    stage.setWidth(width);
    stage.setX(posX);
    stage.setY(posY);
    stage.show();
  }

  default void styleHandler(Observable e, Stage stage) {
    Double widthValue = stage.widthProperty().getValue();
    String getStyleSheet = stage.getScene().getStylesheets().toString();
    if (widthValue > 1000 && !getStyleSheet.contains("BigStyles.css")) {
      String stylePath = (Objects.equals(UserConfig.getUserConfig().getTheme(), "light")) ? PathConstants.BigStyleLight : PathConstants.BigStyleDark;
      changeStyleSheet(stage, stylePath, stage.getHeight());
    } else if (widthValue > 800 && widthValue <= 1000 && !getStyleSheet.contains("MiddleStyles.css")) {
      String stylePath = (Objects.equals(UserConfig.getUserConfig().getTheme(), "light")) ? PathConstants.normalStyleLight : PathConstants.normalStyleDark;
      changeStyleSheet(stage, stylePath, stage.getHeight());
    } else if (widthValue <= 800 && !getStyleSheet.contains("SmallStyles.css")) {
      String stylePath = (Objects.equals(UserConfig.getUserConfig().getTheme(), "light")) ? PathConstants.smallStyleLight : PathConstants.smallStyleDark;
      changeStyleSheet(stage, stylePath, stage.getHeight());
    }
  }

  default void changeStyleSheet(Stage stage, String styleSheet, double height) {
    Scene newScene = stage.getScene();
    for (int x = 0; x < newScene.getStylesheets().size(); x++) {
      newScene.getStylesheets().remove(x);
    }
    newScene.getStylesheets().add(styleSheet);
    stage.setScene(newScene);
    stage.setHeight(height);
    stage.show();
  }
}

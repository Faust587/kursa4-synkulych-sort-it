package ua.synkulych.sort_it.controllers;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public interface WindowsServices {
  default void openNewWindow(Stage stage, String fxmlPath, String title, String difficult) {
    stage.hide();
    FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
    Scene authScene;
    try {
      authScene = new Scene(loader.load());
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    stage.setTitle(title);

    switch (title) {
      case "Sort it":
        ((GameController) loader.getController()).init(stage, difficult);
        break;
      case "Rating":
        ((RatingController) loader.getController()).init(stage);
        break;
      case "Difficult": {
        ((SetDifficultController)loader.getController()).init(stage);
        break;
      }
      case "Settings":
        //((SettingsController)loader.getController()).init(stage);
        break;
      default:
        ((MenuController) loader.getController()).init(stage);
        break;
    }
    stage.setScene(authScene);
    stage.show();
  }
}

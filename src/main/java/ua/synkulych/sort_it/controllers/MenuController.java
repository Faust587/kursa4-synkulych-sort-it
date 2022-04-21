package ua.synkulych.sort_it.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import ua.synkulych.sort_it.ProgramRunner;

import java.io.IOException;

public class MenuController {
  @FXML Stage stage;
  public void init(Stage stage) {
    this.stage = stage;
  }

  public void CloseApplication(MouseEvent mouseEvent) {
    System.exit(0);
  }

  public void OpenRating(MouseEvent mouseEvent) {
    stage.hide();
    FXMLLoader loader = new FXMLLoader(getClass().getResource("/RatingView.fxml"));
    Scene ratingScene;
    try {
      ratingScene = new Scene(loader.load());
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    stage.setTitle("Rating");
    ((RatingController)loader.getController()).init(stage);
    stage.setScene(ratingScene);
    stage.show();
  }
}

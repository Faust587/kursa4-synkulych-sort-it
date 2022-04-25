package ua.synkulych.sort_it.entity;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import ua.synkulych.sort_it.ProgramRunner;
import ua.synkulych.sort_it.controllers.AlertController;
import ua.synkulych.sort_it.controllers.AuthController;

import java.io.IOException;

public class AlertWindow {

  private String title, description;

  public AlertWindow(String title, String description) {
    this.title = title;
    this.description = description;
  }

  public void displayDialog() {
    Stage stage = new Stage();
    stage.setResizable(false);
    stage.setTitle(title);
    stage.initModality(Modality.APPLICATION_MODAL);
    FXMLLoader loader = new FXMLLoader(getClass().getResource("/AlertView.fxml"));
    stage.getIcons().add(new Image(AlertWindow.class.getResourceAsStream( "/error_icon.png" )));
    try {
      stage.setScene(new Scene(loader.load()));
      ((AlertController)loader.getController()).init(description);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    stage.show();
  }
}

package ua.synkulych.sort_it.entity;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;
import ua.synkulych.sort_it.constants.PathConstants;
import ua.synkulych.sort_it.controllers.AlertController;

import java.io.IOException;

public class AlertWindow {

  private String title, description;

  public AlertWindow(String title, String description) {
    this.title = title;
    this.description = description;
    displayDialog();
  }

  private void displayDialog() {
    Stage stage = new Stage();
    stage.setResizable(false);
    stage.setTitle(title);
    stage.initModality(Modality.WINDOW_MODAL);
    FXMLLoader loader = new FXMLLoader(getClass().getResource(PathConstants.AlertView));
    stage.getIcons().add(new Image(getClass().getResourceAsStream( PathConstants.ErrorIcon)));
    try {
      stage.setScene(new Scene(loader.load()));
      ((AlertController)loader.getController()).init(description);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    stage.show();
  }
}

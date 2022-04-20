package ua.synkulych.sort_it.controllers;

import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import ua.synkulych.sort_it.entity.AlertWindow;

public class AlertController {
  public Label AlertDescription;
  public ImageView ErrorImage;

  public void init(Stage stage, String description) {
    AlertDescription.setText(description);
    ErrorImage.setImage(new Image(AlertWindow.class.getResourceAsStream( "/oops.png" )));
  }
}

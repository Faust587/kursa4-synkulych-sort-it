package ua.synkulych.sort_it.controllers;

import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import ua.synkulych.sort_it.entity.AlertWindow;

import java.util.Objects;

public class AlertController {
  public Label AlertDescription;
  public ImageView ErrorImage;

  /**
   * This is initial method for updating description information about error
   * @param description this is a string variable, which describe error
   */
  public void init(String description) {
    AlertDescription.setText(description);
    ErrorImage.setImage(new Image(Objects.requireNonNull(AlertWindow.class.getResourceAsStream("/oops.png"))));
  }
}

package ua.synkulych.sort_it.controllers;

import javafx.fxml.FXML;
import javafx.stage.Stage;

public class MenuController implements WindowsServices {
  @FXML Stage stage;

  public void init(Stage stage) {
    this.stage = stage;
  }

  public void CloseApplication() {
    System.exit(0);
  }

  public void OpenRating() {
    openNewWindow(stage, "/RatingView.fxml", "Rating", null);
  }

  public void StartGame() {
    openNewWindow(stage, "/SetDifficultView.fxml", "Difficult", null);
  }
}

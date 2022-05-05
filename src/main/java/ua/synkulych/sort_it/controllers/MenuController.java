package ua.synkulych.sort_it.controllers;

import javafx.fxml.FXML;
import javafx.stage.Stage;
import ua.synkulych.sort_it.constants.PathConstants;
import ua.synkulych.sort_it.constants.TitleConstants;

public class MenuController implements WindowsServices {
  @FXML Stage stage;

  public void init(Stage stage) {
    this.stage = stage;
  }

  public void CloseApplication() {
    System.exit(0);
  }

  public void OpenRating() {
    openNewWindow(stage, PathConstants.RatingView, TitleConstants.Rating, null);
  }

  public void StartGame() {
    openNewWindow(stage, PathConstants.SetDifficultView, TitleConstants.Difficult, null);
  }

  public void OpenSettings() {
    openNewWindow(stage, PathConstants.SETTINGS_VIEW, TitleConstants.Settings, null);
  }
}

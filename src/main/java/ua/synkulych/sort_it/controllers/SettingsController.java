package ua.synkulych.sort_it.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import ua.synkulych.sort_it.constants.PathConstants;
import ua.synkulych.sort_it.constants.TitleConstants;
import ua.synkulych.sort_it.entity.UserConfig;

public class SettingsController implements WindowsServices {
  @FXML
  private Stage stage;
  public void init(Stage stage) {
    this.stage = stage;
  }

  public void OpenMenu() {
    openNewWindow(stage, PathConstants.MenuView, TitleConstants.MainMenu, null);
  }

  public void changeSettings(MouseEvent mouseEvent) {
    String text = ((Button)(mouseEvent.getSource())).getId();
    switch (text) {
      case "ua", "ru", "en" -> UserConfig.changeUserConfig("language", text);
      case "light", "dark" -> UserConfig.changeUserConfig("theme", text);
    }
    openNewWindow(stage, PathConstants.SETTINGS_VIEW, TitleConstants.Settings, null);
  }
}

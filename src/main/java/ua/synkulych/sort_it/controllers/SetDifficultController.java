package ua.synkulych.sort_it.controllers;

import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import ua.synkulych.sort_it.constants.PathConstants;
import ua.synkulych.sort_it.constants.TitleConstants;

public class SetDifficultController implements WindowsServices {
  private Stage stage;
  public void init(Stage stage) {
    this.stage = stage;
  }

  public void OpenMainMenu(MouseEvent mouseEvent) {
    openNewWindow(stage, PathConstants.MenuView, TitleConstants.MainMenu, null);
  }

  public void OpenGame(MouseEvent mouseEvent) {
    String difficult =((Button) mouseEvent.getSource()).getText();
    openNewWindow(stage, PathConstants.GameView, TitleConstants.SortIt, difficult);
  }
}

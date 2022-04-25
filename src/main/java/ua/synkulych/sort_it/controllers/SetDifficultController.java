package ua.synkulych.sort_it.controllers;

import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class SetDifficultController implements WindowsServices {
  private Stage stage;
  public void init(Stage stage) {
    this.stage = stage;
  }

  public void OpenMainMenu(MouseEvent mouseEvent) {
    openNewWindow(stage, "/MenuView.fxml", "Main menu", null);
  }

  public void OpenGame(MouseEvent mouseEvent) {
    String difficult =((Button) mouseEvent.getSource()).getText();
    openNewWindow(stage, "/GameView.fxml", "Sort it", difficult);
  }
}

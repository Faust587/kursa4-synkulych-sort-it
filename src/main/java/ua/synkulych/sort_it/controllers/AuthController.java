package ua.synkulych.sort_it.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import ua.synkulych.sort_it.database.DatabaseSQL;
import ua.synkulych.sort_it.entity.AlertWindow;
import ua.synkulych.sort_it.entity.Response;
import ua.synkulych.sort_it.entity.User;

public class AuthController implements WindowsServices {

  @FXML public HBox AuthView;
  @FXML public TextField SignInUsername;
  @FXML public PasswordField SignInPassword;
  @FXML public TextField SignUpUsername;
  @FXML public PasswordField SignUpPassword;
  @FXML public PasswordField SignUpRepeatPassword;
  @FXML private Stage stage;

  /**
   * Initial method for AuthView which connecting to database
   * @param stage current stage for this window
   */
  public void init(Stage stage) {
    this.stage = stage;
    DatabaseSQL databaseSQL = new DatabaseSQL("root", "", "127.0.0.1", "sort_it_database", 3306);
    Response<Boolean> response = databaseSQL.connect();
    if (!response.isOK()) {
      AlertWindow alertWindow = new AlertWindow(response.getTitle(), response.getDescription());
      alertWindow.displayDialog();
    }
  }

  /**
   * This is action method for "Sign Up" button
   * Which make authorization for user
   */
  public void SignInAction() {
    DatabaseSQL databaseSQL = new DatabaseSQL("root", "", "127.0.0.1", "sort_it_database", 3306);
    String username = SignInUsername.getText();
    String password = SignInPassword.getText();
    Response<Boolean> response = databaseSQL.userLogIn(username, password);
    if (!response.isOK()) {
      AlertWindow alertWindow = new AlertWindow(response.getTitle(), response.getDescription());
      alertWindow.displayDialog();
    } else {
      User.setUsername(username);
      User.setPassword(password);
      openMenu();
    }
  }

  /**
   * This is action method for "Sign In" button
   * Which make registration for user
   */
  public void SignUpAction() {
    if (!SignUpPassword.getText().equals(SignUpRepeatPassword.getText())) {
      AlertWindow alertWindow = new AlertWindow("Incorrect password", "Your passwords are not the same");
      alertWindow.displayDialog();
      return;
    }

    DatabaseSQL databaseSQL = new DatabaseSQL("root", "", "127.0.0.1", "sort_it_database", 3306);
    String username = SignUpUsername.getText();
    String password = SignUpPassword.getText();
    Response<Boolean> response = databaseSQL.addNewUserToDatabase(username, password);
    if (!response.isOK()) {
      AlertWindow alertWindow = new AlertWindow(response.getTitle(), response.getDescription());
      alertWindow.displayDialog();
    } else {
      User.setUsername(username);
      User.setPassword(password);
      openMenu();
    }
  }

  /**
   * This method open the main menu
   */
  public void openMenu() {
    openNewWindow(stage, "/MenuView.fxml", "Main menu", null);
  }
}

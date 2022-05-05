package ua.synkulych.sort_it.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import ua.synkulych.sort_it.constants.PathConstants;
import ua.synkulych.sort_it.constants.TitleConstants;
import ua.synkulych.sort_it.database.DatabaseSQL;
import ua.synkulych.sort_it.entity.AlertWindow;
import ua.synkulych.sort_it.entity.User;

public class AuthController implements WindowsServices {
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
  }

  /**
   * This is action method for "Sign Up" button
   * Which make authorization for user
   */
  public void SignInAction() {
    DatabaseSQL databaseSQL = new DatabaseSQL();
    String username = SignInUsername.getText();
    String password = SignInPassword.getText();
    boolean response = databaseSQL.userLogIn(username, password);
    if (response) {
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
      new AlertWindow("Incorrect password", "Your passwords are not the same");
      return;
    }

    String username = SignUpUsername.getText();
    String password = SignUpPassword.getText();

    DatabaseSQL databaseSQL = new DatabaseSQL();

    boolean response = databaseSQL.addNewUserToDatabase(username, password);
    if (response) {
      User.setUsername(username);
      User.setPassword(password);
      openMenu();
    }
  }

  /**
   * This method open the main menu
   */
  public void openMenu() {
    openNewWindow(stage, PathConstants.MenuView, TitleConstants.MainMenu, null);
  }

  public void OpenSignUpMenu() {
    openNewWindow(stage, PathConstants.SignUpView, TitleConstants.SignUp, null);
  }

  public void OpenSignInMenu() {
    openNewWindow(stage, PathConstants.SignInView, TitleConstants.SignIn, null);
  }
}

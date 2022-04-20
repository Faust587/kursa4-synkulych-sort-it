package ua.synkulych.sort_it.controllers;

import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import ua.synkulych.sort_it.database.DatabaseSQL;
import ua.synkulych.sort_it.entity.AlertWindow;
import ua.synkulych.sort_it.entity.Response;

public class AuthController {
  public HBox AuthView;
  public TextField SignInUsername;
  public PasswordField SignInPassword;
  public TextField SignUpUsername;
  public PasswordField SignUpPassword;
  public PasswordField SignUpRepeatPassword;

  public void init(Stage stage) {
    DatabaseSQL databaseSQL = new DatabaseSQL("root", "", "127.0.0.1", "sort_it_database", 3306);
    Response<Boolean> response = databaseSQL.connect();
    if (!response.isOK()) {
      AlertWindow alertWindow = new AlertWindow(response.getTitle(), response.getDescription());
      alertWindow.displayDialog();
    }
  }

  public void SignInAction(MouseEvent mouseEvent) {
    DatabaseSQL databaseSQL = new DatabaseSQL("root", "", "127.0.0.1", "sort_it_database", 3306);
    Response<Boolean> response = databaseSQL.userLogIn(SignInUsername.getText(), SignInPassword.getText());
    if (!response.isOK()) {
      AlertWindow alertWindow = new AlertWindow(response.getTitle(), response.getDescription());
      alertWindow.displayDialog();
    } else {
      System.out.println("Yeah");
    }
  }

  public void SignUpAction(MouseEvent mouseEvent) {
    if (!SignUpPassword.getText().equals(SignUpRepeatPassword.getText())) {
      AlertWindow alertWindow = new AlertWindow("Incorrect password", "Your passwords are not the same");
      alertWindow.displayDialog();
      return;
    }

    DatabaseSQL databaseSQL = new DatabaseSQL("root", "", "127.0.0.1", "sort_it_database", 3306);
    Response<Boolean> response = databaseSQL.addNewUserToDatabase(SignUpUsername.getText(), SignUpPassword.getText());
    if (!response.isOK()) {
      AlertWindow alertWindow = new AlertWindow(response.getTitle(), response.getDescription());
      alertWindow.displayDialog();
    } else {
      System.out.println("Yeah");
    }
  }
}

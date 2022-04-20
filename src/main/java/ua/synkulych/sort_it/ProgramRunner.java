package ua.synkulych.sort_it;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import ua.synkulych.sort_it.controllers.AuthController;
import ua.synkulych.sort_it.database.DatabaseSQL;
import ua.synkulych.sort_it.entity.Response;
import ua.synkulych.sort_it.entity.User;

import java.util.Objects;

public class ProgramRunner extends Application {

  public static void main(String[] args) {
    launch(args);
  }

  @Override
  public void start(Stage stage) throws Exception {
    FXMLLoader loader = new FXMLLoader(getClass().getResource("/AuthView.fxml"));
    Scene authScene = new Scene(loader.load());
    stage.getIcons().add(new Image(ProgramRunner.class.getResourceAsStream( "/icon.png" )));
    ((AuthController)loader.getController()).init(stage);
    stage.setScene(authScene);
    stage.show();
  }
}

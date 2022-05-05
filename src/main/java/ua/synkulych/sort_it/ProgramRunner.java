package ua.synkulych.sort_it;

import javafx.application.Application;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import ua.synkulych.sort_it.constants.PathConstants;
import ua.synkulych.sort_it.constants.TitleConstants;
import ua.synkulych.sort_it.controllers.WindowsServices;

public class ProgramRunner extends Application implements WindowsServices {

  public static void main(String[] args) {
    launch(args);
  }

  @Override
  public void start(Stage stage) throws Exception {
    stage.getIcons().add(new Image(ProgramRunner.class.getResourceAsStream(PathConstants.Icon)));
    openNewWindow(stage, PathConstants.SignInView, TitleConstants.SignIn, null);
  }
}

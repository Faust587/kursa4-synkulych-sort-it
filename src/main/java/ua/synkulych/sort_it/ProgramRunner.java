package ua.synkulych.sort_it;

import javafx.application.Application;
import javafx.stage.Stage;
import ua.synkulych.sort_it.database.DatabaseSQL;
import ua.synkulych.sort_it.entity.Response;

public class ProgramRunner extends Application {

  public static void main(String[] args) {
    launch(args);
  }

  @Override
  public void start(Stage stage) throws Exception {
    DatabaseSQL databaseSQL = new DatabaseSQL("root", "", "127.0.0.1", "sort_it_database", 3306);
    databaseSQL.connect();
    Response<Boolean> result = databaseSQL.addNewUserToDatabase("Faust2", "Passwor2");
    System.out.println(result.isOK());
    System.out.println(result.getDescription());
  }
}

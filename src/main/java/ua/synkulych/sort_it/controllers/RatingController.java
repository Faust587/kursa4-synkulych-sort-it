package ua.synkulych.sort_it.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import ua.synkulych.sort_it.database.DatabaseSQL;
import ua.synkulych.sort_it.entity.AlertWindow;
import ua.synkulych.sort_it.entity.Rating;
import ua.synkulych.sort_it.entity.Response;

import java.io.IOException;
import java.util.ArrayList;

public class RatingController {
  private final String placeStyle = "-fx-font-size: 20px; -fx-text-fill: #EC4E20; -fx-font-family: 'Segoe UI Black'";
  private final String usernameStyle = "-fx-font-size: 20px; -fx-text-fill: #3590F3; -fx-font-family: 'Segoe UI Black'";
  private final String pointsStyle = "-fx-font-size: 20px; -fx-text-fill: #E56399; -fx-font-family: 'Segoe UI Black'";
  @FXML private ScrollPane RatingPane;
  @FXML private VBox RatingList;
  @FXML private Stage stage;
  public void init(Stage stage) {
    this.stage = stage;
    DatabaseSQL databaseSQL = new DatabaseSQL("root", "", "127.0.0.1", "sort_it_database", 3306);
    Response<Boolean> response = databaseSQL.connect();
    if (!response.isOK()) {
      AlertWindow alertWindow = new AlertWindow(response.getTitle(), response.getDescription());
      alertWindow.displayDialog();
    }
    Response<ArrayList<Rating>> userRatingList = databaseSQL.getRatingList();
    int HBoxHeight = 0;
    for (Rating user : userRatingList.getValue()) {
      HBoxHeight += 100;
      Label place = new Label(user.getPlace() + "");
      place.setStyle(placeStyle);
      Label username = new Label(user.getUsername());
      username.setStyle(usernameStyle);
      Label points = new Label(user.getPoints() + "");
      points.setStyle(pointsStyle);
      HBox ratingBlock = new HBox(place, username, points);
      ratingBlock.setAlignment(Pos.BASELINE_LEFT);
      ratingBlock.setStyle("-fx-padding: 20px;");
      ratingBlock.setSpacing(20);
      RatingList.getChildren().add(ratingBlock);
    }
    RatingList.setPrefHeight(HBoxHeight);
    RatingPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
  }

  public void OpenMenu(MouseEvent mouseEvent) {
    stage.hide();
    FXMLLoader loader = new FXMLLoader(getClass().getResource("/MenuView.fxml"));
    Scene authScene;
    try {
      authScene = new Scene(loader.load());
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    stage.setTitle("Main menu");
    ((MenuController)loader.getController()).init(stage);
    stage.setScene(authScene);
    stage.show();
  }
}

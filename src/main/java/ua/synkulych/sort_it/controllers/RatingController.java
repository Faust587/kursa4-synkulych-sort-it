package ua.synkulych.sort_it.controllers;

import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import ua.synkulych.sort_it.database.DatabaseSQL;
import ua.synkulych.sort_it.entity.AlertWindow;
import ua.synkulych.sort_it.entity.Rating;
import ua.synkulych.sort_it.entity.Response;

import java.util.ArrayList;

public class RatingController implements WindowsServices {
  @FXML private ScrollPane RatingPane;
  @FXML private VBox RatingList;
  @FXML private Stage stage;

  public void init(Stage stage) {
    this.stage = stage;
    DatabaseSQL databaseSQL = new DatabaseSQL();
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
      String placeStyle = "-fx-font-size: 20px; -fx-text-fill: #EC4E20; -fx-font-family: 'Segoe UI Black'";
      place.setStyle(placeStyle);
      Label username = new Label(user.getUsername());
      String usernameStyle = "-fx-font-size: 20px; -fx-text-fill: #3590F3; -fx-font-family: 'Segoe UI Black'";
      username.setStyle(usernameStyle);
      Label points = new Label(user.getPoints() + "");
      String pointsStyle = "-fx-font-size: 20px; -fx-text-fill: #E56399; -fx-font-family: 'Segoe UI Black'";
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

  public void OpenMenu() {
    openNewWindow(stage, "/MenuView.fxml", "Main menu", null);
  }
}

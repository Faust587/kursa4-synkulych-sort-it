package ua.synkulych.sort_it.controllers;

import javafx.fxml.FXML;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import ua.synkulych.sort_it.constants.PathConstants;
import ua.synkulych.sort_it.constants.TitleConstants;
import ua.synkulych.sort_it.database.DatabaseSQL;
import ua.synkulych.sort_it.entity.AlertWindow;
import ua.synkulych.sort_it.entity.Rating;
import ua.synkulych.sort_it.entity.Response;

import java.util.ArrayList;

public class RatingController implements WindowsServices {
  @FXML private ScrollPane RatingPane;
  @FXML private GridPane RatingList;
  @FXML private Stage stage;

  public void init(Stage stage) {
    this.stage = stage;
    DatabaseSQL databaseSQL = new DatabaseSQL();
    Response<ArrayList<Rating>> userRatingList = databaseSQL.getRatingList();
    ArrayList<Rating> rating = userRatingList.getValue();
    for (int x = 0; x < userRatingList.getValue().size(); x++) {
      String placeText = rating.get(x).getPlace() + "";
      String usernameText = rating.get(x).getUsername();
      String pointsText = rating.get(x).getPoints() + "";

      Label placeLabel = new Label(placeText);
      Label usernameLabel = new Label(usernameText);
      Label pointsLabel = new Label(pointsText);

      placeLabel.getStyleClass().add("RatingLabel");
      usernameLabel.getStyleClass().add("RatingLabel");
      pointsLabel.getStyleClass().add("RatingLabel");

      RatingList.add(placeLabel, 0, x);
      RatingList.add(usernameLabel, 1, x);
      RatingList.add(pointsLabel, 2, x);
    }
    generateGrid(rating.size());
    RatingPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
    RatingPane.setFitToWidth(true);
    RatingPane.fitToWidthProperty().set(true);
    RatingPane.fitToHeightProperty().set(true);
  }

  private void generateGrid(int rows) {
    for (int x = 0; x < 3; x++) {
      ColumnConstraints column = new ColumnConstraints();
      column.setMinWidth(100);
      column.setPrefWidth(375);
      RatingList.getColumnConstraints().add(column);
    }
    for (int y = 0; y < rows; y++) {
      RowConstraints row = new RowConstraints();
      row.setMinHeight(100);
      row.setPrefHeight(150);
      row.setMaxHeight(175);
      RatingList.getRowConstraints().add(row);
    }
  }
  public void OpenMenu() {
    openNewWindow(stage, PathConstants.MenuView, TitleConstants.MainMenu, null);
  }
}

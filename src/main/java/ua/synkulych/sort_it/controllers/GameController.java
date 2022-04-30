package ua.synkulych.sort_it.controllers;

import javafx.event.ActionEvent;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.RowConstraints;
import javafx.stage.Stage;
import ua.synkulych.sort_it.database.DatabaseSQL;
import ua.synkulych.sort_it.entity.BoardSizes;
import ua.synkulych.sort_it.entity.User;

import javax.xml.crypto.Data;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.Stack;

public class GameController implements WindowsServices {
  public Button ExitButton;
  public GridPane GameGrid;
  public Label GameTitle;
  private Stage stage;
  private boolean blockGame = false;
  private int points;
  private BoardSizes sizes;
  private int[] colorCounter;
  private int[][] gameBoard;
  private int activeButton = -1;

  /**
   * This is initial method which create all important variables
   * @param stage the main stage of javafx project
   * @param difficult type of difficult which user have chosen
   */
  public void init(Stage stage, String difficult) {
    ExitButton.setText("");
    this.stage = stage;
    sizes = getSizes(difficult);
    points = getPoints(difficult);
    gameBoard = new int[sizes.getX()][sizes.getY()];
    colorCounter = new int[sizes.getX() - 1];
    generateGrid();
    drawGrid();
    refactorGameBoard();
    fillGrid();
  }

  /**
   * This method fill grid as it was written in important variables
   * There are creating all buttons and their properties
   */
  private void fillGrid() {
    mirrorGameBoard();
    for (int x = 0; x < sizes.getX(); x++) {
      for (int y = 0; y < sizes.getY(); y++) {
        Button button = new Button();
        button.setId(x + " " + y);
        button.setOnAction(this::click);
        GridPane.setHalignment(button, HPos.CENTER);
        GridPane.setValignment(button, VPos.CENTER);
        String color;
        if (gameBoard[x][y] == 1) {
          color = "#EC4E20";
        } else if (gameBoard[x][y] == 2) {
          color = "#F9C22E";
        } else if (gameBoard[x][y] == 3) {
          color = "#3590F3";
        } else if (gameBoard[x][y] == 4) {
          color = "#16E0BD";
        } else {
          color = "#A39B8B";
        }
        button.setStyle("-fx-pref-width: 86px; -fx-pref-height: 86px; -fx-background-radius: 43px; -fx-background-color:" + color + ";");
        GameGrid.add(button, x, y);
      }
    }
    mirrorGameBoard();
  }

  /**
   * This method reflects "gameBoard" array
   */
  private void mirrorGameBoard() {
    int temp = 0;
    for(int i = 0; i < sizes.getX(); i++){
      for(int j = 0; j < sizes.getY() / 2; j++){
        temp = gameBoard[i][j];
        gameBoard[i][j] = gameBoard[i][sizes.getY() - j - 1];
        gameBoard[i][sizes.getY() - j - 1] = temp;
      }
    }
  }

  /**
   * This method do some manipulations with "gameBoard" array
   * This method called when some game buttons was clicked
   * @param actionEvent variable which contains all info about buttons which was cliked
   */
  private void click(ActionEvent actionEvent) {
    if (blockGame) return;
    Button clickedButton =(Button) actionEvent.getSource();
    String id = clickedButton.getId();
    int posX = Integer.parseInt(id.split(" ")[0]);
    if (activeButton == -1) {
      if (!columnIsEmpty(posX)) {
        activeButton = posX;
      }
    } else if (activeButton == posX) {
      activeButton = -1;
    } else {
      if (!columnIsFull(posX)) {
        int deletedValue = deleteValueFromColumn(activeButton);
        addValueToColumn(posX, deletedValue);
        activeButton = -1;
        updateGrid();
        boolean win = checkWin();
        if (win) {
          GameTitle.setText("WIN");
          blockGame = true;
          new DatabaseSQL().addPointsToUserRating(points);
        }
      }
    }
  }

  /**
   * This method check "gameBoard" for winning requirements
   * @return boolean which indicate about win
   */
  private boolean checkWin() {
    for (int x = 0; x < sizes.getX(); x++) {
      int mainValue = gameBoard[x][0];
      for (int y = 1; y < sizes.getY(); y++) {
        if (gameBoard[x][y] != mainValue) return false;
      }
    }
    return true;
  }

  /**
   * Clear game grid and build this again
   */
  private void updateGrid() {
    GameGrid.getChildren().clear();
    generateGrid();
    drawGrid();
    fillGrid();
  }

  /**
   * Create start position of balls in game board
   */
  private void refactorGameBoard() {
    int ballsCounter = (sizes.getX() - 1) * sizes.getY();

    for (int i = 0; i < ballsCounter; i++) {
      int color = generateColorValue();
      int column = generateColumnIndex();
      addValueToColumn(column, color + 1);
    }
  }

  /**
   * generate color which is free
   * @return integer value which indicate some color
   */
  private int generateColorValue() {
    while (true) {
      int color = getRandomInteger(0, sizes.getX() - 2);
      if (colorCounter[color] < sizes.getY()) {
        colorCounter[color]++;
        return color;
      }
    }
  }

  /**
   * Generate column index which is not full
   * @return integer value of free column
   */
  private int generateColumnIndex() {
    while (true) {
      int index = getRandomInteger(0, sizes.getX() - 1);
      if (!columnIsFull(index)) {
        return index;
      }
    }
  }

  /**
   * This method check column for emptiness
   * @param column index which must be checked
   * @return boolean variable
   */
  private boolean columnIsEmpty(int column) {
    for (int i = 0; i < gameBoard[column].length; i++) {
      if (gameBoard[column][i] != 0) return false;
    }
    return true;
  }

  /**
   * This method check that column is full
   * @param column index which must be checked
   * @return boolean variable
   */
  private boolean columnIsFull(int column) {
    for (int i = 0; i < gameBoard[column].length; i++) {
      if (gameBoard[column][i] == 0) return false;
    }
    return true;
  }

  /**
   * This method delete last NOT ZERO value from column
   * @param column index of column which should be checked
   * @return deleted value
   */
  private int deleteValueFromColumn(int column) {
    for (int i = gameBoard[column].length - 1; i >= 0; i--) {
      if (gameBoard[column][i] != 0) {
        int deletedValue = gameBoard[column][i];
        gameBoard[column][i] = 0;
        return deletedValue;
      }
    }
    return -1;
  }

  /**
   * Add value to column
   * @param column index of column
   * @param value which must be added
   */
  private void addValueToColumn(int column, int value) {
    for (int i = 0; i < gameBoard[column].length; i++) {
      if (gameBoard[column][i] == 0) {
        gameBoard[column][i] = value;
        return;
      }
    }
  }

  /**
   * Generate rows and columns
   */
  private void generateGrid() {
    GameGrid.getColumnConstraints().clear();
    GameGrid.getRowConstraints().clear();

    GameGrid.setAlignment(Pos.CENTER);
    //GameGrid.setGridLinesVisible(true);
    for (int x = 0; x < sizes.getX(); x++) {
      ColumnConstraints column = new ColumnConstraints();
      column.setPrefWidth(150);
      GameGrid.getColumnConstraints().add(column);
    }
    for (int y = 0; y < sizes.getY(); y++) {
      RowConstraints row = new RowConstraints();
      row.setPrefHeight(100);
      GameGrid.getRowConstraints().add(row);
    }
  }

  /**
   * Draw tubes in grid
   */
  private void drawGrid() {
    for (int x = 0; x < sizes.getX(); x++) {
      for (int y = 0; y < sizes.getY(); y++) {
        Region rectangle = new Region();
        rectangle.setMaxSize(110, 100);
        rectangle.setMinSize(110, 100);
        if (y == sizes.getY() - 1) {
          rectangle.setStyle("-fx-background-radius: 0px 0px 10px 10px; -fx-background-color: #A39B8B;");
        } else {
          rectangle.setStyle("-fx-background-color: #A39B8B;");
        }
        GridPane.setHalignment(rectangle, HPos.CENTER);
        GridPane.setValignment(rectangle, VPos.CENTER);
        GameGrid.add(rectangle, x, y);
      }
    }
  }

  /**
   * Depending on the difficult created sizes of the game board
   * @param difficult string value
   * @return "BoardSizes" type variable
   */
  private BoardSizes getSizes(String difficult) {
    switch (difficult) {
      case "Easy": return new BoardSizes(4, 3);
      case "Medium": return new BoardSizes(4, 4);
      case "Hard": return new BoardSizes(5, 4);
      default: return new BoardSizes(5, 5);
    }
  }

  /**
   * Generate award points which depends on chosen difficult
   * @param difficult string value
   * @return integer variable
   */
  private int getPoints(String difficult) {
    switch (difficult) {
      case "Easy": return 5;
      case "Medium": return 10;
      case "Hard": return 20;
      default: return 25;
    }
  }

  /**
   * This method generate random number between MIN and MAX params
   * @param min integer variable
   * @param max integer variable
   * @return random integer
   */
  private int getRandomInteger(int min, int max) {
    return new Random().nextInt(max - min + 1) + min;
  }

  /**
   * This method open menu window and close game window
   * Called when buttons was clicked
   */
  public void OpenMenu() {
    openNewWindow(stage, "/MenuView.fxml", "Main menu", null);
  }
}

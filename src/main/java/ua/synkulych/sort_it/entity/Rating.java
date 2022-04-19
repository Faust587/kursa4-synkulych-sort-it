package ua.synkulych.sort_it.entity;

public class Rating {
  private String username;
  private int place, points;

  public Rating(String username, int place, int points) {
    this.username = username;
    this.place = place;
    this.points = points;
  }

  public String getUsername() {
    return username;
  }

  public int getPlace() {
    return place;
  }

  public int getPoints() {
    return points;
  }
}

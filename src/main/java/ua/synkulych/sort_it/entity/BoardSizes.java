package ua.synkulych.sort_it.entity;

public class BoardSizes {
  private int x, y;

  /**
   * This method set sizes
   * @param x integer value
   * @param y integer value
   */
  public BoardSizes(int x, int y) {
    this.x = x;
    this.y = y;
  }

  public int getX() {
    return x;
  }

  public int getY() {
    return y;
  }
}

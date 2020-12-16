package cs3500.animator.provider.util;

import java.awt.Color;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;
import java.util.Objects;
import java.util.HashMap;
import java.util.List;

/**
 * Generates an animation of the building of a FloodFill.
 * Represents the depth-first-search algorithm used in flood fill.
 */
public class FloodFillGenerator implements AnimationGenerator {

  /**
   * Represents the coordinate of a square.
   */
  public static class Coord {
    private int x;
    private int y;
    private String name;

    /**
     * Constructor that makes a coordinate with given x-y coordinates.
     * @param x the x-coordinate
     * @param y the y-coordinate
     */
    public Coord(int x, int y) {
      this.x = x;
      this.y = y;
      this.name = x + ":" + y;
    }

    /**
     * Returns a unique name for each coordinate.
     * @return a name of the coordinate.
     */
    public String getName() {
      return this.name;
    }

    /**
     * Returns the x-coordinate.
     * @return the x-coordinate.
     */
    public int getX() {
      return this.x;
    }

    /**
     * Returns the y-coordinate.
     * @return the y-coordinate
     */
    public int getY() {
      return this.y;
    }

    @Override
    public boolean equals(Object other) {
      if (!(other instanceof Coord)) {
        return false;
      }
      Coord that = (Coord) other;
      return this.x == that.x && this.y == that.y;
    }

    @Override
    public int hashCode() {
      return Objects.hash(x, y);
    }
  }

  private static int squareSize = 40;
  private static int padding = 5;
  private List<List<Integer>> matrix;
  private int targetX;
  private int targetY;
  private int newInt;
  private int oldInt;
  private int tick = 1;
  private static int delay = 1;
  private StringBuilder result = new StringBuilder();
  private Map<Coord, StringBuilder> squares = new HashMap<>();

  /**
   * Constructor that creates a FloodFillGenerator using given params.
   * @param matrix the entire matrix
   * @param x the x-coordinate of the target source
   * @param y the y-coordinate of the target source
   * @param newInt the new integer representing the new color to change to
   */
  public FloodFillGenerator(List<List<Integer>> matrix, int x, int y, int newInt) {
    if (invalidMatrix(matrix)) {
      throw new IllegalArgumentException("Invalid matrix");
    }
    if (x >= matrix.size() || y >= matrix.get(0).size()) {
      throw new IllegalArgumentException("Target out of bound");
    }
    if (newInt < 0) {
      throw new IllegalArgumentException("Invalid color");
    }
    this.matrix = matrix;
    this.targetX = x;
    this.targetY = y;
    this.newInt = newInt;
    this.oldInt = matrix.get(targetX).get(targetY);
  }

  private boolean invalidMatrix(List<List<Integer>> matrix) {
    if (matrix == null || matrix.size() <= 0) {
      return true;
    }
    for (List<Integer> row : matrix) {
      if (row.size() != matrix.size()) {
        return true;
      }
    }
    return false;
  }

  @Override
  public void generateAnimation(String file) throws IllegalStateException {
    if (file == null) {
      throw new IllegalStateException("No file defined");
    } try {
      FileWriter writer = new FileWriter(file);
      this.setStartString();
      this.floodFillAlgo();
      this.getEndString();
      writer.append(this.result);
      writer.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  private void getEndString() {
    tick += delay;
    for (Map.Entry<Coord, StringBuilder> square : squares.entrySet()) {
      int x = square.getKey().getX();
      int y = square.getKey().getY();
      StringBuilder s = square.getValue();
      s.append(getCurrentString(x, y, matrix.get(x).get(y)));
      result.append(s.toString()).append("\n");
    }
  }

  private void setStartString() {
    int size = squareSize * matrix.size() + padding * (matrix.size() - 1);
    result.append("canvas 0 0 ").append(size).append(" ").append(size).append("\n");
    for (int i = 0; i < matrix.size(); i ++) {
      for (int j = 0; j < matrix.get(0).size(); j ++) {
        Coord coord = new Coord(i, j);
        result.append("shape ").append(coord.getName()).append(" rectangle\n");
        StringBuilder s = new StringBuilder();
        s.append("motion ").append(coord.getName()).append(" ");
        s.append(getCurrentString(i, j, matrix.get(i).get(j))).append("  ");
        squares.put(coord, s);
      }
    }
  }

  private void floodFillAlgo() {
    if (oldInt != newInt) {
      dfs(targetX, targetY);
    }
  }

  private void dfs(int x, int y) {
    if (matrix.get(x).get(y) == oldInt) {
      matrix.get(x).set(y, newInt);
      this.getAnimationString(x, y);
      if (x >= 1) {
        dfs(x - 1, y);
      }
      if (y >= 1) {
        dfs(x, y - 1);
      }
      if (x + 1 < matrix.size()) {
        dfs(x + 1, y);
      }
      if (y + 1 < matrix.get(0).size()) {
        dfs(x, y + 1);
      }
    }
  }

  private Color getColor(int intColor) {
    if (intColor == 0) {
      return new Color(0, 0, 0);
    } else if (intColor == 1) {
      return new Color(255, 0, 0);
    } else if (intColor == 2) {
      return new Color(0, 255, 0);
    } else if (intColor == 3) {
      return new Color(0, 0, 255);
    } else {
      return new Color(255, 0, 255);
    }
  }

  private void getAnimationString(int x, int y) {
    Coord coord = new Coord(x, y);
    StringBuilder s = squares.get(coord);
    StringBuilder currString = getCurrentString(x, y, oldInt);
    s.append(currString);
    s.append("\n");
    s.append("motion ").append(coord.getName()).append(" ");
    s.append(currString);
    s.append("  ");
    tick += delay;
    currString = getCurrentString(x, y, matrix.get(x).get(y));
    s.append(currString);
    s.append("\n");
    s.append("motion ").append(coord.getName()).append(" ");
    s.append(currString);
    s.append("  ");
  }

  private StringBuilder getCurrentString(int x, int y, int intColor) {
    Color color = getColor(intColor);
    StringBuilder currString = new StringBuilder();
    currString.append(tick).append(" ");
    currString.append(x * squareSize + x * padding).append(" ");
    currString.append(y * squareSize + y * padding).append(" ");
    currString.append(squareSize).append(" ").append(squareSize).append(" ");
    currString.append(color.getRed()).append(" ").append(color.getGreen());
    currString.append(" ").append(color.getBlue());
    return currString;
  }

  /**
   * Generates an example FloodFillGenerator animation file.
   * @param args arguments passed into the main
   */
  public static void main(String[] args) {
    List<List<Integer>> matrix = new ArrayList<>();
    List<Integer> row1 = new ArrayList<>();
    row1.add(1);
    row1.add(1);
    row1.add(1);
    List<Integer> row2 = new ArrayList<>();
    row2.add(1);
    row2.add(1);
    row2.add(0);
    List<Integer> row3 = new ArrayList<>();
    row3.add(1);
    row3.add(0);
    row3.add(1);
    matrix.add(row1);
    matrix.add(row2);
    matrix.add(row3);
    AnimationGenerator animation = new FloodFillGenerator(matrix, 1, 1, 2);
    animation.generateAnimation("Hw7/resources/FloodFill.txt");
  }

  @Override
  public String toString() {
    return result.toString();
  }
}

package cs3500.animator.model.shape;

import java.util.Objects;

/**
 * Represents a rectangle with a 2 dimensional position, a height and a width, and a color.
 */
public class Rectangle extends AShape {

  /**
   * Represents the features of the rectangle shape.
   * @param pos represents the position of the rectangle
   * @param size represents the size (width and height) of rectangle
   * @param color represents the color of the rectangle
   */
  public Rectangle(Position2D pos, Size2D size, Rgb color) {
    super(pos, size, color);
  }

  @Override
  public String toString() {
    String str = "Rectangle with Position: "
        + this.getPos().toString()
        + ", Size: " + this.getSize().toString()
        + ", Color: " + this.getRgb().toString();
    return str;
  }

  @Override
  public boolean equals(Object a) {
    if (this == a) {
      return true;
    }
    if (!(a instanceof Rectangle)) {
      return false;
    }

    Rectangle that = (Rectangle) a;

    return this.getPos().equals(that.getPos())
        && this.getSize().equals(that.getSize())
        && this.getRgb().equals(that.getRgb());
  }

  @Override
  public int hashCode() {
    return Objects.hash(this.getPos(), this.getSize(), this.getRgb());
  }

  @Override
  public IShape2D copyIShape2D() {
    return new Rectangle(this.getPos(), this.getSize(), this.getRgb());
  }

  @Override
  public String shapeName() {
    return "rectangle";
  }
}

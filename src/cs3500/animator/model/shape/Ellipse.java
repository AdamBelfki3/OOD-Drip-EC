package cs3500.animator.model.shape;

import java.util.Objects;

/**
 * Represents an ellipse with a 2 dimensional position,
 * size (height and width) and particular colors.
 */
public class Ellipse extends AShape {

  /**
   * Features of the ellipse shape.
   * @param pos the position of the ellipse
   * @param size the size of the ellipse
   * @param colors the color of the ellipse
   */
  public Ellipse(Position2D pos, Size2D size, Rgb colors) {
    super(pos, size, colors);
  }

  @Override
  public String toString() {
    String str = "Ellipse with Position: "
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
    if (!(a instanceof Ellipse)) {
      return false;
    }

    Ellipse that = (Ellipse) a;

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
    return new Ellipse(this.getPos(),
        this.getSize(), this.getRgb());
  }

  @Override
  public String shapeName() {
    return "ellipse";
  }
}

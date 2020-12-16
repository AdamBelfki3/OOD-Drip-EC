package cs3500.animator.model.shape;

import java.util.Objects;

/**
 *
 *
 */
public class Plus extends AShape {

  public Plus(Position2D pos, Size2D size, Rgb color) {
    super(Objects.requireNonNull(pos), Objects.requireNonNull(size), Objects.requireNonNull(color));
  }

  @Override
  public IShape2D copyIShape2D() {
    return new Plus(this.getPos(), this.getSize(), this.getRgb());
  }

  @Override
  public String shapeName() {
    return "plus";
  }

  @Override
  public boolean equals(Object a) {
    if (this == a) {
      return true;
    }
    if (!(a instanceof Plus)) {
      return false;
    }

    Plus that = (Plus) a;

    return this.getPos().equals(that.getPos())
        && this.getSize().equals(that.getSize())
        && this.getRgb().equals(that.getRgb());
  }

  @Override
  public int hashCode() {
    return Objects.hash(this.getPos(), this.getSize(), this.getRgb());
  }

  @Override
  public String toString() {
    String str = "Plus with Position: "
        + this.getPos().toString()
        + ", Size: " + this.getSize().toString()
        + ", Color: " + this.getRgb().toString();
    return str;
  }
}

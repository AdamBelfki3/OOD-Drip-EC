package cs3500.animator.model.shape;

import java.util.Objects;

/**
 * Abstract class which represents the default features of a shape.
 * The class contains a 2 dimensional position,
 * a size (width and height) as well as methods that can be used to update
 * each of these three features.
 */
public abstract class AShape implements IShape2D {
  private Position2D pos;
  private Size2D size;
  private Rgb color;

  /**
   * Represents the abstract constructor that takes in these three features
   * about a shape.
   * @param pos 2 dimensional position of the shape
   * @param size height and width of the shape
   * @param color color of the shape
   */
  AShape(Position2D pos, Size2D size, Rgb color) {
    this.pos = Objects.requireNonNull(pos);
    this.size = Objects.requireNonNull(size);
    this.color = Objects.requireNonNull(color);
  }

  @Override
  public Rgb getRgb() {
    return new Rgb(this.color.getR(), this.color.getG(), this.color.getB());
  }

  @Override
  public Size2D getSize() {
    return new Size2D(size.getWidth(), size.getHeight());
  }

  @Override
  public Position2D getPos() {
    return new Position2D(pos.getX(), pos.getY());
  }

  @Override
  public void move(Position2D newPos) {
    this.pos = newPos;
  }

  @Override
  public void resize(Size2D newSize) {
    this.size = newSize;
  }

  @Override
  public void colorize(Rgb newColor) {
    this.color = newColor;
  }

  @Override
  abstract public IShape2D copyIShape2D();

  @Override
  abstract public String shapeName();
}


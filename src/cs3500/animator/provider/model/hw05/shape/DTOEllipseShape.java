package cs3500.animator.provider.model.hw05.shape;

/**
 * Represents an oval shape.
 * An oval consists of name, color, x-coordinate, y-coordinate, width, and height.
 */
public class DTOEllipseShape extends DTOAbstractShape {

  /**
   * Constructor for the oval shape.
   * @param x x location of the shape
   * @param y y location of the shape
   * @param w width of the shape
   * @param h height of the shape
   * @param r red value of the color of the shape
   * @param g green value of the color of the shape
   * @param b blue value of the color of the shape
   */
  public DTOEllipseShape(double x, double y, double w, double h,
                         double r, double g, double b) {
    super(x, y, w, h, r, g, b);
  }

  /**
   * Constructor for the oval shape with the name.
   * @param name the name of the shape
   * @param x x location of the shape
   * @param y y location of the shape
   * @param w width of the shape
   * @param h height of the shape
   * @param r red value of the color of the shape
   * @param g green value of the color of the shape
   * @param b blue value of the color of the shape
   */
  public DTOEllipseShape(String name, double x, double y, double w, double h,
                         double r, double g, double b) {
    super(name, x, y, w, h, r, g, b);
  }

  public String getShapeType() {
    return "ellipse";
  }
}

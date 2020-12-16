package cs3500.animator.provider.model.hw05.shape;

/**
 * An abstract class that represents a shape.
 * A shape consists of name, color, x-coordinate, y-coordinate, width, and height.
 */
public abstract class DTOAbstractShape {
  private String name = "";
  private double x;
  private double y;
  private double w;
  private double h;
  private double r;
  private double g;
  private double b;
  protected double originalX;
  protected double originalY;
  protected double originalW;
  protected double originalH;
  protected double originalR;
  protected double originalG;
  protected double originalB;

  /**
   * Constructs a shape using color, x-y coordinate, width, and height.
   * @param x     x-coordinate of the shape
   * @param y     y-coordinate of the shape
   * @param w     width of the shape
   * @param h     height of the shape
   */
  public DTOAbstractShape(double x, double y, double w, double h,
                          double r, double g, double b) {
    this.x = x;
    this.y = y;
    this.w = w;
    this.h = h;
    this.r = r;
    this.g = g;
    this.b = b;
    this.originalX = x;
    this.originalY = y;
    this.originalW = w;
    this.originalH = h;
    this.originalR = r;
    this.originalG = g;
    this.originalB = b;
  }

  /**
   * Constructs a shape using name, color, x-y coordinate, width, and height.
   * @param name  name of the shape
   * @param x     x-coordinate of the shape
   * @param y     y-coordinate of the shape
   * @param w     width of the shape
   * @param h     height of the shape
   */
  public DTOAbstractShape(String name, double x, double y, double w, double h,
                          double r, double g, double b) {
    this.name = name;
    this.x = x;
    this.y = y;
    this.w = w;
    this.h = h;
    this.r = r;
    this.g = g;
    this.b = b;
    this.originalX = x;
    this.originalY = y;
    this.originalW = w;
    this.originalH = h;
    this.originalR = r;
    this.originalG = g;
    this.originalB = b;
  }

  public String getName() {
    return name;
  }

  public double getX() {
    return x;
  }

  public double getY() {
    return y;
  }

  public double getW() {
    return w;
  }

  public double getH() {
    return h;
  }

  public double getR() {
    return r;
  }

  public double getG() {
    return g;
  }

  public double getB() {
    return b;
  }

  public abstract String getShapeType();
}

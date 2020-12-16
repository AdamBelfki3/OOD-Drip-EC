
import cs3500.animator.model.shape.Rectangle;
import cs3500.animator.model.shape.Ellipse;
import cs3500.animator.model.shape.Position2D;
import cs3500.animator.model.shape.Rgb;
import cs3500.animator.model.shape.Size2D;
import org.junit.Test;


import static org.junit.Assert.assertEquals;

/**
 * Test class that checks all the methods implemented in the shape class.
 */
public class ShapeTest {

  // create rectangle
  Position2D recPos = new Position2D(0,0);
  Size2D recSize = new Size2D(10,10);
  Rgb recRgb = new Rgb(255,0,0);
  Rectangle rectangle = new Rectangle(recPos, recSize, recRgb);


  // create ellipse
  Position2D ellipsePos = new Position2D(0,0);
  Size2D ellipseSize = new Size2D(10,10);
  Rgb ellipseRgb = new Rgb(0,0,255);
  Ellipse ellipse = new Ellipse(ellipsePos, ellipseSize, ellipseRgb);


  // test rectangle and ellipse to string
  @Test
  public void shapeToString() {
    assertEquals("Rectangle with Position: (0.000000, 0.000000), " +
            "Size: Width = 10, Height = 10, Color: R: 255, G: 0, B: 0", rectangle.toString());
    assertEquals("Ellipse with Position: (0.000000, 0.000000), " +
            "Size: Width = 10, Height = 10, Color: R: 0, G: 0, B: 255", ellipse.toString());
  }


  // TEST ASHAPE METHODS
  // test get rgb
  @Test
  public void testGetRgb() {
    // test rectangle
    assertEquals(new Rgb(255,0,0), rectangle.getRgb());

    // test ellipse
    assertEquals(new Rgb(0,0,255), ellipse.getRgb());
  }

  // test get size
  @Test
  public void testGetSize() {
    // test rectangle
    assertEquals(new Size2D(10,10), rectangle.getSize());

    // test ellipse
    assertEquals(new Size2D(10,10), ellipse.getSize());
  }

  // test get pos
  @Test
  public void testGetPos() {
    // test rectangle
    assertEquals(new Position2D(0,0), rectangle.getPos());

    // test ellipse
    assertEquals(new Position2D(0,0), ellipse.getPos());
  }

  // test move
  @Test
  public void testMove() {
    Position2D newPos = new Position2D(10,10);

    // test rectangle
    assertEquals(new Position2D(0,0), rectangle.getPos());
    rectangle.move(newPos);
    assertEquals(new Position2D(10,10), rectangle.getPos());

    // test ellipse
    assertEquals(new Position2D(0,0), ellipse.getPos());
    ellipse.move(newPos);
    assertEquals(new Position2D(10,10), ellipse.getPos());
  }

  // test resize
  @Test
  public void testResize() {
    Size2D newSize = new Size2D(30,30);

    // test rectangle
    assertEquals(new Size2D(10,10), rectangle.getSize());
    rectangle.resize(newSize);
    assertEquals(new Size2D(30,30), rectangle.getSize());

    // test ellipse
    assertEquals(new Size2D(10,10), ellipse.getSize());
    ellipse.resize(newSize);
    assertEquals(new Size2D(30,30), ellipse.getSize());
  }

  // test resize exception neg width
  @Test (expected = IllegalArgumentException.class)
  public void testResizeNegWidth() {
    Size2D newSize = new Size2D(-10,0);

    // test rectangle exception
    assertEquals(new Size2D(10,10), rectangle.getSize());
    rectangle.resize(newSize);

    // test ellipse exception
    assertEquals(new Size2D(10,10), ellipse.getSize());
    ellipse.resize(newSize);
  }

  // test resize exception negative height
  @Test (expected = IllegalArgumentException.class)
  public void testResizeNegHeight() {
    Size2D newSize = new Size2D(0,-10);

    // test rectangle exception
    assertEquals(new Size2D(10,10), rectangle.getSize());
    rectangle.resize(newSize);

    // test ellipse exception
    assertEquals(new Size2D(10,10), ellipse.getSize());
    ellipse.resize(newSize);
  }

  // test colorize
  @Test
  public void testColorize() {
    Rgb newColor = new Rgb(0,255,0);

    // test rectangle
    assertEquals(new Rgb(255,0,0), rectangle.getRgb());
    rectangle.colorize(newColor);
    assertEquals(new Rgb(0,255,0), rectangle.getRgb());

    // test ellipse
    assertEquals(new Rgb(0,0,255), ellipse.getRgb());
    ellipse.colorize(newColor);
    assertEquals(new Rgb(0,255,0), ellipse.getRgb());
  }

  // test colorize exception invalid R
  @Test (expected = IllegalArgumentException.class)
  public void testColorizeInvalidR() {
    Rgb newColorNeg = new Rgb(-1,0,0);
    Rgb newColorBig = new Rgb(256,0,0);

    // test rectangle exception
    assertEquals(new Rgb(255,0,0), rectangle.getRgb());
    rectangle.colorize(newColorNeg);

    // test ellipse exception
    assertEquals(new Rgb(0,0,255), ellipse.getRgb());
    ellipse.colorize(newColorBig);
  }

  // test colorize exception invalid G
  @Test (expected = IllegalArgumentException.class)
  public void testColorizeInvalidG() {
    Rgb newColorNeg = new Rgb(0,-1,0);
    Rgb newColorBig = new Rgb(0,300,0);

    // test rectangle exception
    assertEquals(new Rgb(255,0,0), rectangle.getRgb());
    rectangle.colorize(newColorNeg);

    // test ellipse exception
    assertEquals(new Rgb(0,0,255), ellipse.getRgb());
    ellipse.colorize(newColorBig);
  }

  // test colorize exception invalid B
  @Test (expected = IllegalArgumentException.class)
  public void testColorizeInvalidB() {
    Rgb newColorNeg = new Rgb(0,0,-1);
    Rgb newColorBig = new Rgb(0,0,257);

    // test rectangle exception
    assertEquals(new Rgb(255,0,0), rectangle.getRgb());
    rectangle.colorize(newColorNeg);

    // test ellipse exception
    assertEquals(new Rgb(0,0,255), ellipse.getRgb());
    ellipse.colorize(newColorBig);
  }

  // test copyIShape2D
  @Test
  public void testCopyOfShape() {
    Rectangle copyRec = new Rectangle(new Position2D(0,0),
            new Size2D(10,10), new Rgb(255,0,0));

    Ellipse copyEllipse = new Ellipse(new Position2D(0,0),
            new Size2D(10,10), new Rgb(0,0,255));

    assertEquals(copyRec, rectangle.copyIShape2D());
    assertEquals(copyEllipse, copyEllipse.copyIShape2D());

  }

  // test shapeName
  @Test
  public void testShapeName() {
    assertEquals("rectangle", rectangle.shapeName());
    assertEquals("ellipse", ellipse.shapeName());
  }
}

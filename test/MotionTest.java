import cs3500.animator.model.Motion;
import cs3500.animator.model.shape.Rectangle;
import cs3500.animator.model.shape.Ellipse;
import cs3500.animator.model.shape.Position2D;
import cs3500.animator.model.shape.Rgb;
import cs3500.animator.model.shape.Size2D;
import org.junit.Test;

import static org.junit.Assert.assertEquals;


/**
 * Test class that checks all the methods implemented in the motion class.
 */
public class MotionTest {
  // create initial rectangle
  Position2D recStartPos = new Position2D(200,200);
  Size2D recStartSize = new Size2D(50,100);
  Rgb recStartRgb = new Rgb(255,0,0);
  Rectangle rectangleInitial = new Rectangle(recStartPos, recStartSize, recStartRgb);

  // create rectangle for motion 1
  Position2D recPosMotion1 = new Position2D(200,200);
  Size2D recSizeMotion1 = new Size2D(50,100);
  Rgb recRgbMotion1 = new Rgb(0,255,0);
  Rectangle rectangleMotion1 = new Rectangle(recPosMotion1, recSizeMotion1, recRgbMotion1);

  // create initial ellipse
  Position2D ellipseStartPos = new Position2D(200,200);
  Size2D ellipseStartSize = new Size2D(50,100);
  Rgb ellipseStartRgb = new Rgb(255,0,0);
  Ellipse ellipseInitial = new Ellipse(ellipseStartPos, ellipseStartSize, ellipseStartRgb);
  
  // create ellipse for motion 
  Position2D ellipsePosMotion = new Position2D(300,300);
  Size2D ellipseSizeMotion = new Size2D(50,100);
  Rgb ellipseRgbMotion = new Rgb(0,0,255);
  Ellipse ellipseMotion1 = new Ellipse(ellipsePosMotion, ellipseSizeMotion, ellipseRgbMotion);
  
  
  // motion 1 for rectangle
  Motion motionRec =
          new Motion("rectangle", 1,
                  recStartPos, recStartSize, recStartRgb, 10,
                  recPosMotion1, recSizeMotion1, recRgbMotion1, false, false);

  // motion 1 for ellipse
  Motion motionEllipse =
          new Motion("ellipse", 10,
                  ellipseStartPos, ellipseStartSize, ellipseStartRgb,
                  50, ellipsePosMotion, ellipseSizeMotion, ellipseRgbMotion, false,
              false);

  // getStartTime
  @Test
  public void testGetStartTime() {
    assertEquals(1, motionRec.getStartTime());
    assertEquals(10, motionEllipse.getStartTime());
  }

  // getEndTime
  @Test
  public void testGetEndTime() {
    assertEquals(10, motionRec.getEndTime());
    assertEquals(50, motionEllipse.getEndTime());
  }

  // getStartPos
  @Test
  public void testGetStartPos() {
    assertEquals( new Position2D(200,200), motionRec.getStartPos());
    assertEquals( new Position2D(200,200), motionEllipse.getStartPos());
  }

  // getEndPos
  @Test
  public void testGetEndPos() {
    assertEquals( new Position2D(200,200), motionRec.getEndPos());
    assertEquals( new Position2D(300,300), motionEllipse.getEndPos());
  }


  // getStartSize
  @Test
  public void testGetStartSize() {
    assertEquals(new Size2D(50,100), motionRec.getStartSize());
    assertEquals(new Size2D(50,100), motionEllipse.getStartSize());
  }

  // getEndSize
  @Test
  public void testGetEndSize() {
    assertEquals(new Size2D(50,100), motionRec.getEndSize());
    assertEquals(new Size2D(50,100), motionEllipse.getEndSize());
  }


  // getStartColor
  @Test
  public void testGetStartColor() {
    assertEquals(new Rgb(255,0,0), motionRec.getStartColor());
    assertEquals(new Rgb(255,0,0), motionEllipse.getStartColor());
  }

  // getEndColor
  @Test
  public void testGetEndColor() {
    assertEquals(new Rgb(0,255,0), motionRec.getEndColor());
    assertEquals(new Rgb(0,0,255), motionEllipse.getEndColor());
  }

  // copyMotion
  @Test
  public void testCopyMotion() {
    Motion copyMotion1 = new Motion("rectangle", 1,
            new Position2D(200,200), new Size2D(50,100),
            new Rgb(255,0,0), 10,
            new Position2D(200,200), new Size2D(50,100),
            new Rgb(0,255,0), false, false);


    assertEquals(copyMotion1, motionRec.copyMotion());
  }

  // getShapeName
  @Test
  public void testShapeName() {
    assertEquals("rectangle", motionRec.getShapeName());
    assertEquals("ellipse", motionEllipse.getShapeName());
  }

  // test textual string
  @Test
  public void testTextualString() {
    assertEquals("motion rectangle 1 200.0 200.0 50 100 255 0 0  " +
        " 10 200.0 200.0 50 100 0 255 0", motionRec.toTextualString());
    assertEquals("motion ellipse 10 200.0 200.0 50 100 255 0 0  " +
        " 50 300.0 300.0 50 100 0 0 255", motionEllipse.toTextualString());
  }


  // test to string
  @Test
  public void testToString() {
    assertEquals("motion rectangle 1 200.0 200.0 50 100 255 0 0  " +
        " 10 200.0 200.0 50 100 0 255 0 false false", motionRec.toString());
    assertEquals("motion ellipse 10 200.0 200.0 50 100 255 0 0  " +
        " 50 300.0 300.0 50 100 0 0 255 false false", motionEllipse.toString());
  }
}

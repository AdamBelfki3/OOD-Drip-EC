
import cs3500.animator.model.IMotion;
import cs3500.animator.model.IShapeAndMotion;
import cs3500.animator.model.Motion;
import cs3500.animator.model.ShapeAndMotion;
import cs3500.animator.model.AnimationModel;
import cs3500.animator.model.SimpleAnimation;

import java.util.LinkedHashMap;

import cs3500.animator.model.shape.Position2D;
import cs3500.animator.model.shape.Rectangle;
import cs3500.animator.model.shape.Rgb;
import cs3500.animator.model.shape.Size2D;
import org.junit.Test;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;

import static org.junit.Assert.assertEquals;

/**
 * Test class that checks all the methods implemented in the simple animation class.
 */
public class SimpleAnimationTest {
  // create rectangle
  Position2D recStartPos = new Position2D(0, 0);
  Size2D recStartSize = new Size2D(10, 10);
  Rgb recStartRgb = new Rgb(255, 0, 0);
  Rectangle rectangle = new Rectangle(recStartPos, recStartSize, recStartRgb);

  // create rectangle for motion 1
  Position2D recPosMotion1 = new Position2D(200, 200);
  Size2D recSizeMotion1 = new Size2D(50, 100);
  Rgb recRgbMotion1 = new Rgb(0, 255, 0);

  // create rectangle for motion 2
  Position2D recPosMotion2 = new Position2D(300, 300);
  Size2D recSizeMotion2 = new Size2D(50, 100);
  Rgb recRgbMotion2 = new Rgb(0, 255, 0);

  // motion 1 for rectangle
  Motion motionRec1 =
          new Motion("rectangle", 1,
                  recPosMotion1, recSizeMotion1, recRgbMotion1, 10,
                  recPosMotion1, recSizeMotion1, recRgbMotion1, false, false);

  Motion motionRec2 =
          new Motion("rectangle", 1,
                  recPosMotion1, recSizeMotion1, recRgbMotion1, 50,
                  recPosMotion2, recSizeMotion2, recRgbMotion2, false, false);


  ArrayList oneMotion = new ArrayList<IMotion>(Arrays.asList(motionRec1));
  ArrayList twoMotion = new ArrayList<IMotion>(Arrays.asList(motionRec1, motionRec2));

  // create shape and motion
  // empty motion
  ShapeAndMotion shapeAndMotionEmpty = new ShapeAndMotion(rectangle, new ArrayList<>(), 0,
          0);
  // one motion
  ShapeAndMotion shapeAndMotion1 = new ShapeAndMotion(rectangle, oneMotion, 1, 50);
  // two motions
  ShapeAndMotion shapeAndMotion2 = new ShapeAndMotion(rectangle, twoMotion, 1, 50);

  // create default drawings

  // create simple animation
  AnimationModel simpleAnimation = new SimpleAnimation();
  Map<String, IShapeAndMotion> drawings = simpleAnimation.getDrawings();


  // createShape
  @Test
  public void testCreateShape() {
    // creates shape with default values
    ShapeAndMotion defaultSm = new ShapeAndMotion(new Rectangle(new Position2D(0, 0),
            new Size2D(10, 10), new Rgb(255, 0, 0)));

    Map<String, IShapeAndMotion> testDrawings = new LinkedHashMap<>();
    testDrawings.put("R", defaultSm);

    simpleAnimation.createShape("rectangle", "R");

    assertEquals(testDrawings.keySet(), drawings.keySet());
    assertEquals(testDrawings.get("R"), drawings.get("R"));
  }

  // resetAnimation
  @Test
  public void testReset() {
    ShapeAndMotion defaultSm = new ShapeAndMotion(new Rectangle(new Position2D(0, 0),
            new Size2D(10, 10), new Rgb(255, 0, 0)));

    Map<String, IShapeAndMotion> testDrawings = new LinkedHashMap<>();
    testDrawings.put("R", defaultSm);

    assertEquals(new LinkedHashMap<>(), simpleAnimation.getDrawings());

    simpleAnimation.createShape("rectangle", "R");

    assertEquals(testDrawings, simpleAnimation.getDrawings());
    simpleAnimation.resetAnimation();
    assertEquals(new LinkedHashMap<>(), simpleAnimation.getDrawings());
  }



  // getImageAt
  @Test
  public void testGetImageAt() {
    simpleAnimation.startAnimation();
    simpleAnimation.createShape("rectangle", "R");
    assertEquals(shapeAndMotionEmpty, simpleAnimation.getImageAt("R"));
  }


  // addOperation1
  @Test
  public void testGetOperation() {
    simpleAnimation.startAnimation();
    simpleAnimation.createShape("rectangle", "R");
    assertEquals(new ArrayList<>(), simpleAnimation.getDrawings().get("R").getMotions());
    simpleAnimation.addOperation("R", 10, 20);

    // new motion
    Motion doNothingMotion = new Motion("R", 10, 20);
    ArrayList doNothingList = new ArrayList<IMotion>(Arrays.asList(doNothingMotion));

    assertEquals(doNothingList, simpleAnimation.getDrawings().get("R").getMotions());

  }

  @Test
  public void testGetInstructions() {
    ShapeAndMotion defaultSm = new ShapeAndMotion(new Rectangle(new Position2D(0, 0),
            new Size2D(10, 10), new Rgb(255, 0, 0)));

    Map<String, IShapeAndMotion> testDrawings = new LinkedHashMap<>();
    testDrawings.put("R", defaultSm);

    simpleAnimation.startAnimation();

    assertEquals(new LinkedHashMap<>(), simpleAnimation.getInstructions());
    simpleAnimation.createShape("rectangle", "R");

    assertEquals(testDrawings, simpleAnimation.getInstructions());
  }

  /////////////////////////////////////////////////////////////////////////////////////////////////
  // tests for the method getFrame in the SimpleAnimation model implementation

  // testing for exception if the animation has not started yet
  @Test(expected = IllegalStateException.class)
  public void testGetFrameAnimationNotStarted() {
    AnimationModel model = new SimpleAnimation();
    model.createShape("rectangle", "R");
    model.addOperation("R", 1, 5, 0, 0, 10, 10, 255, 0, 0);
    model.addOperation("R", 5, 10);
    model.addOperation("R", 10, 20, 10, 10, 5, 5, 0, 255, 0);

    model.getFrame("R", 6);
  }

  // testing for exception if the animation does not have the time asked for
  @Test(expected = IllegalArgumentException.class)
  public void testGetFrameAnimationBadTime() {
    AnimationModel model = new SimpleAnimation();
    model.createShape("rectangle", "R");
    model.addOperation("R", 1, 5, 0, 0, 10, 10, 255, 0, 0);
    model.addOperation("R", 5, 10);
    model.addOperation("R", 10, 20, 10, 10, 5, 5, 0, 255, 0);

    model.startAnimation();

    model.getFrame("R", 35);
  }

  // testing for exception if the animation does not have shape asked for
  @Test(expected = IllegalArgumentException.class)
  public void testGetFrameAnimationNoShape() {
    AnimationModel model = new SimpleAnimation();
    model.createShape("rectangle", "R");
    model.addOperation("R", 1, 5, 0, 0, 10, 10, 255, 0, 0);
    model.addOperation("R", 5, 10);
    model.addOperation("R", 10, 20, 10, 10, 5, 5, 0, 255, 0);

    model.startAnimation();

    model.getFrame("W", 6);
  }

  // testing for a frame with a valid shape and name and time
  @Test
  public void testGetFrame() {
    AnimationModel model = new SimpleAnimation();
    model.createShape("rectangle", "R");
    model.addOperation("R", 1, 5, 0, 0, 10, 10, 255, 0, 0);
    model.addOperation("R", 5, 10);
    model.addOperation("R", 10, 20, 10, 10, 5, 5, 175, 0, 0);

    model.startAnimation();

    assertEquals(new Rectangle(new Position2D(0, 0), new Size2D(10, 10),
            new Rgb(0, 0, 0)), model.getFrame("R", 1));
    assertEquals(new Rectangle(new Position2D(0, 0), new Size2D(10, 10),
            new Rgb(255, 0, 0)), model.getFrame("R", 5));
    assertEquals(new Rectangle(new Position2D(0, 0), new Size2D(10, 10),
            new Rgb(255, 0, 0)), model.getFrame("R", 8));
    assertEquals(new Rectangle(new Position2D(4, 4), new Size2D(8, 8),
            new Rgb(223, 0, 0)), model.getFrame("R", 14));
    assertEquals(new Rectangle(new Position2D(10, 10), new Size2D(5, 5),
            new Rgb(175, 0, 0)), model.getFrame("R", 20));
  }

  /////////////////////////////////////////////////////////////////////////////////////////////////
  // tests for the method addMotion in the SimpleAnimation model implementation

  // invalid inputs to construct a motion
  @Test(expected = IllegalArgumentException.class)
  public void testAddOperation2BadInputs() {
    AnimationModel model = new SimpleAnimation();

    model.createShape("rectangle", "R");

    model.addOperation("R", 1, 15, 10, 10, -9, 5,
            255, 0, 0);
  }

  // adding a motion to a shape that was not created yet
  @Test(expected = IllegalArgumentException.class)
  public void testAddOperation2NoShape() {
    AnimationModel model = new SimpleAnimation();

    model.addOperation("L", 1, 15, 10, 10, 9, 5, 255, 0, 0);
  }

  @Test
  public void testAddOperation2() {
    AnimationModel model = new SimpleAnimation();

    model.createShape("rectangle", "R");

    model.addOperation("R", 1, 15, 10, 10, 9, 5,
            255, 0, 0);

    Map<String, IShapeAndMotion> newInstructions = new LinkedHashMap<String, IShapeAndMotion>();
    newInstructions.put("R", new ShapeAndMotion(new Rectangle(new Position2D(0, 0),
            new Size2D(10, 10), new Rgb(255, 0, 0)),
            new ArrayList<IMotion>(Arrays.asList(
                    new Motion("R", 1, 15, new Position2D(10, 10),
                            new Size2D(9, 5), new Rgb(255, 0, 0)))), 1,
            15));

    assertEquals(newInstructions, model.getInstructions());
  }

  /////////////////////////////////////////////////////////////////////////////////////////////////
  // tests for the method startAnimation in the SimpleAnimation model implementation

  @Test(expected = IllegalStateException.class)
  public void testStartAnimationIncoherence1() {
    AnimationModel model = new SimpleAnimation();
    model.createShape("rectangle", "R");
    model.addOperation("R", 1, 10, 4, 5, 45, 56, 90, 0, 0);
    model.addOperation("R", 3, 13, 15, 4, 43, 4, 2, 1, 2);

    model.startAnimation();
  }

  @Test(expected = IllegalStateException.class)
  public void testStartAnimationIncoherence2() {
    AnimationModel model = new SimpleAnimation();
    model.createShape("rectangle", "R");
    model.addOperation("R", 1, 10, 4, 5, 45, 56, 90, 0, 0);
    model.addOperation("R", 12, 13, 15, 4, 43, 4, 2, 1, 2);

    model.startAnimation();
  }

  @Test
  public void testStartAnimationIncoherence() {
    AnimationModel model = new SimpleAnimation();
    model.createShape("rectangle", "R");
    model.addOperation("R", 1, 2, 4, 30, 45, 255, 0, 0,
            10, 4, 5, 45, 56, 90, 0, 0);
    model.addOperation("R", 12, 13, 15, 4, 43, 4, 2, 1, 2);

    try {
      model.startAnimation();
    } catch (IllegalStateException e) {
      // throws an exception otherwise
    }

    assertEquals(new LinkedHashMap<>(), model.getInstructions());


    AnimationModel model2 = new SimpleAnimation();
    model2.createShape("rectangle", "R");
    model2.addOperation("R", 1, 10, 4, 5, 45, 56, 90, 0, 0);
    model2.addOperation("R", 10, 20, 20, 35, 25, 255, 0, 0,
            13, 15, 4, 43, 4, 2, 1, 2);

    // adding operations to a test tree
    Map<String, IShapeAndMotion> testInstructions = new LinkedHashMap<>();
    Motion motion1 = new Motion("R", 1, 10,
            new Position2D(4, 5), new Size2D(45, 56),
            new Rgb(90, 0, 0));

    Motion motion2 = new Motion("R", 10, new Position2D(20, 20),
            new Size2D(35, 25), new Rgb(255, 0, 0), 13,
            new Position2D(15, 4), new Size2D(43, 4),
            new Rgb(2, 1, 2));


    ShapeAndMotion shapeAndMotion = new ShapeAndMotion(rectangle,
            new ArrayList<IMotion>(Arrays.asList(motion1, motion2)), 0, 0);

    shapeAndMotion.linkMotions();

    testInstructions.put("R", shapeAndMotion);

    try {
      model2.startAnimation();
    } catch (IllegalStateException e) {
      // throws an exception otherwise
    }

    // testing the instructions so far
    assertEquals(testInstructions, model2.getInstructions());

    model2.addOperation("R", 20, 30, 20, 20, 20, 20, 0, 255, 0);

    try {
      model2.startAnimation();
    } catch (IllegalStateException e) {
      // do nothing
    }

    assertEquals(testInstructions, model2.getInstructions());
  }

  @Test
  public void testStartAnimation() {
    AnimationModel model = new SimpleAnimation();
    Motion motionRec1 =
            new Motion("R", 1,
                    new Position2D(0, 0),
                    new Size2D(10, 10),
                    new Rgb(0, 0, 0), 10,
                    new Position2D(4, 5),
                    new Size2D(45, 56),
                    new Rgb(90, 0, 0), true, false);

    ArrayList oneMotion = new ArrayList<IMotion>(Arrays.asList(motionRec1));
    ShapeAndMotion shapeAndMotion = new ShapeAndMotion(rectangle, oneMotion, 0, 0);
    shapeAndMotion.linkMotions();

    model.createShape("rectangle", "R");
    model.addOperation("R", 1, 10, 4, 5, 45, 56, 90, 0, 0);

    model.startAnimation();
    Map<String, IShapeAndMotion> testInstructions = new LinkedHashMap<>();
    testInstructions.put("R", shapeAndMotion);

    assertEquals(testInstructions, model.getInstructions());
  }

  @Test(expected = IllegalStateException.class)
  public void testRetrieve() {
    AnimationModel model = new SimpleAnimation();
    Motion motionRec1 =
            new Motion("R", 1,
                    new Position2D(0, 0),
                    new Size2D(10, 10),
                    new Rgb(0, 0, 0), 10,
                    new Position2D(4, 5),
                    new Size2D(45, 56),
                    new Rgb(90, 0, 0), true, false);
    Motion motionRec2 =
            new Motion("R", 10,
                    new Position2D(4, 5),
                    new Size2D(45, 56),
                    new Rgb(90, 0, 0), 20,
                    new Position2D(4, 5),
                    new Size2D(45, 56),
                    new Rgb(90, 0, 0), true, false);

    ArrayList twoMotion = new ArrayList<IMotion>(Arrays.asList(motionRec1, motionRec2));
    ShapeAndMotion shapeAndMotion = new ShapeAndMotion(rectangle, twoMotion, 0, 0);
    shapeAndMotion.linkMotions();

    model.createShape("rectangle", "R");
    model.addOperation("R", 1, 10, 4, 5, 45, 56, 90, 0, 0);
    model.addOperation("R", 10, 20, 4, 5, 45, 56, 90, 0, 0);

    model.startAnimation();
    Map<String, IShapeAndMotion> testInstructions = new LinkedHashMap<>();
    testInstructions.put("R", shapeAndMotion);

    assertEquals(testInstructions, model.getInstructions());
    model.retrieve();
    model.retrieve();
    assertEquals(testInstructions, model.getInstructions());

  }

  // test set bounds
  @Test
  public void testSetBounds() {
    AnimationModel model = new SimpleAnimation();
    model.startAnimation();
    model.setBounds(1, 2, 3, 4);
    assertEquals(1, model.getXCanvas());
    assertEquals(2, model.getYCanvas());
    assertEquals(3, model.getWidthCanvas());
    assertEquals(4, model.getHeightCanvas());
  }

  // set bounds exception neg width
  @Test(expected = IllegalArgumentException.class)
  public void setBoundExceptionNegWidth() {
    AnimationModel model = new SimpleAnimation();
    model.startAnimation();
    model.setBounds(1, 2, -3, 4);
  }

  // set bounds exception neg height
  @Test(expected = IllegalArgumentException.class)
  public void setBoundExceptionNegHeight() {
    AnimationModel model = new SimpleAnimation();
    model.startAnimation();
    model.setBounds(1, 2, 3, -4);
  }

  // test update shape
  @Test
  public void testUpdateShape() {
    AnimationModel model = new SimpleAnimation();
    model.startAnimation();
    model.createShape("rectangle", "R");
    assertEquals(new Position2D(0, 0), model.getDrawings().get("R").getShape().getPos());
    assertEquals(new Size2D(10, 10),
            model.getDrawings().get("R").getShape().getSize());
    assertEquals(new Rgb(255, 0, 0), model.getDrawings().get("R").getShape().getRgb());
    model.updateShape("R", new Position2D(10, 10), new Size2D(40, 40),
            new Rgb(0, 0, 255));
    assertEquals(new Position2D(10, 10), model.getDrawings().get("R").getShape().getPos());
    assertEquals(new Size2D(40, 40),
            model.getDrawings().get("R").getShape().getSize());
    assertEquals(new Rgb(0, 0, 255), model.getDrawings().get("R").getShape().getRgb());

  }

  // test update shape exception
  @Test(expected = IllegalArgumentException.class)
  public void testUpdateShapeException() {
    AnimationModel model = new SimpleAnimation();
    model.startAnimation();
    model.createShape("triangle", "R");
  }

  // test add operation 3
  @Test
  public void testAddOperation3() {
    AnimationModel model = new SimpleAnimation();

    model.createShape("rectangle", "R");

    model.addOperation("R", 1, 10, 10,
            9, 5, 255, 0, 0, 20, 15, 15, 20, 20, 0, 0, 0);

    Map<String, IShapeAndMotion> newInstructions = new LinkedHashMap<String, IShapeAndMotion>();
    Rectangle rect = new Rectangle(new Position2D(0, 0),
            new Size2D(10, 10), new Rgb(255, 0, 0));
    Motion motion1 = new Motion("R", 1, new Position2D(10, 10),
            new Size2D(9, 5), new Rgb(255, 0, 0),
            20, new Position2D(15, 15), new Size2D(20, 20),
            new Rgb(0, 0, 0));
    ShapeAndMotion sm = new ShapeAndMotion(rect,
            new ArrayList<>(Arrays.asList(motion1)), 1, 20);
    newInstructions.put("R", sm);

    assertEquals(newInstructions, model.getInstructions());
  }
}
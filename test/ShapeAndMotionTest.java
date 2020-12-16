import cs3500.animator.model.IMotion;
import cs3500.animator.model.IShapeAndMotion;
import cs3500.animator.model.Motion;
import cs3500.animator.model.ShapeAndMotion;
import cs3500.animator.model.shape.Rectangle;

import cs3500.animator.model.shape.IShape2D;
import cs3500.animator.model.shape.Position2D;
import cs3500.animator.model.shape.Rgb;
import cs3500.animator.model.shape.Size2D;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import static org.junit.Assert.assertEquals;


/**
 * Test class that checks all the methods implemented in the shape and motion class.
 */
public class ShapeAndMotionTest {
  // create rectangle
  Position2D recStartPos = new Position2D(0,0);
  Size2D recStartSize = new Size2D(10,10);
  Rgb recStartRgb = new Rgb(255,0,0);
  Rectangle rectangle = new Rectangle(recStartPos, recStartSize, recStartRgb);

  // create rectangle for motion 1
  Position2D recPosMotion1 = new Position2D(200,200);
  Size2D recSizeMotion1 = new Size2D(50,100);
  Rgb recRgbMotion1 = new Rgb(0,255,0);
  Rectangle rectangleMotion1 = new Rectangle(recPosMotion1, recSizeMotion1, recRgbMotion1);

  // create rectangle for motion 2
  Position2D recPosMotion2 = new Position2D(300,300);
  Size2D recSizeMotion2 = new Size2D(50,100);
  Rgb recRgbMotion2 = new Rgb(0,255,0);
  Rectangle rectangleMotion2 = new Rectangle(recPosMotion2, recSizeMotion2, recRgbMotion2);

  // motion 1 for rectangle
  Motion motionRec1 =
          new Motion("rectangle", 1,
                  recPosMotion1, recSizeMotion1, recRgbMotion1, 10,
                  recPosMotion1, recSizeMotion1, recRgbMotion1,false,false);

  // motion 2 for rectangle
  Motion motionRec2 =
          new Motion("rectangle", 10,
                  recPosMotion1, recSizeMotion1, recRgbMotion1, 50,
                  recPosMotion2, recSizeMotion2, recRgbMotion2,false, false);

  ArrayList oneMotion = new ArrayList<IMotion>(Arrays.asList(motionRec1));
  ArrayList motions = new ArrayList<IMotion>(Arrays.asList(motionRec1, motionRec2));

  // create shape and motion
  ShapeAndMotion shapeAndMotion1 = new ShapeAndMotion(rectangle, oneMotion,1,50);
  ShapeAndMotion shapeAndMotion2 = new ShapeAndMotion(rectangle, motions,1,50);


  // getEndTime
  @Test
  public void testEndTime() {
    assertEquals(50, shapeAndMotion2.getEndTime());
  }

  // getShape
  @Test
  public void testGetShape() {
    // returns IShape2D
    assertEquals(rectangle, shapeAndMotion2.getShape());
    shapeAndMotion2.applyMotion(1);
    assertEquals(rectangleMotion1, shapeAndMotion2.getShape());
  }

  // getMotions
  @Test
  public void testGetMotions() {
    Motion motionRec1Copy =
            new Motion("rectangle", 1,
                    recPosMotion1, recSizeMotion1, recRgbMotion1, 10,
                    recPosMotion1, recSizeMotion1, recRgbMotion1, false,false);
    Motion motionRec2Copy =
            new Motion("rectangle", 10,
                    recPosMotion1, recSizeMotion1, recRgbMotion1, 50,
                    recPosMotion2, recSizeMotion2, recRgbMotion2, false, false);

    ArrayList motionsCopy = new ArrayList<IMotion>(Arrays.asList(motionRec1Copy, motionRec2Copy));
    assertEquals(motionsCopy, shapeAndMotion2.getMotions());

  }

  // addMotion
  @Test
  public void testAddMotion() {
    // motion new for rectangle
    Position2D recPosMotion3 = new Position2D(200,200);
    Size2D recSizeMotion3 = new Size2D(25,100);
    Rgb recRgbMotion3 = new Rgb(0,255,0);

    // new motion
    Motion newMotion =
            new Motion("rectangle", 50,
                    recPosMotion2, recSizeMotion2, recRgbMotion2, 70,
                    recPosMotion3, recSizeMotion3, recRgbMotion3, false, false);

    ArrayList motionBef = new ArrayList<IMotion>(Arrays.asList(motionRec1,motionRec2));
    ArrayList motionAft = new ArrayList<IMotion>(Arrays.asList(motionRec1,motionRec2,newMotion));

    assertEquals(2, shapeAndMotion2.getMotions().size());
    assertEquals(motionBef.toString(), shapeAndMotion2.getMotions().toString());
    shapeAndMotion2.addMotion(newMotion);

    assertEquals(3, shapeAndMotion2.getMotions().size());
    assertEquals(motionAft.toString(), shapeAndMotion2.getMotions().toString());
  }

  // sortMotionsOnStartTime
  @Test
  public void testSortMotionsOnStartTime() {
    ArrayList motionsNotSorted = new ArrayList<IMotion>(Arrays.asList(motionRec2, motionRec1));
    ShapeAndMotion shapeAndMotion = new ShapeAndMotion(rectangle, motionsNotSorted,1,
        50);

    // before sort
    assertEquals(10, shapeAndMotion.getMotions().get(0).getStartTime());
    assertEquals(1, shapeAndMotion.getMotions().get(1).getStartTime());

    // sort
    shapeAndMotion.sortMotionsOnStartTime();

    // after sort
    assertEquals(1, shapeAndMotion.getMotions().get(0).getStartTime());
    assertEquals(10, shapeAndMotion.getMotions().get(1).getStartTime());

  }

  // copyShapeAndMotion
  @Test
  public void testCopyShapeAndMotion() {
    // create rectangle
    IShape2D copyRec = rectangle.copyIShape2D();

    Position2D recPosMotionCopy = new Position2D(200,200);
    Size2D recSizeMotionCopy = new Size2D(50,100);
    Rgb recRgbMotionCopy = new Rgb(0,255,0);

    // motion 1 for rectangle
    Motion motionRecCopy =
            new Motion("rectangle", 1,
                    recPosMotionCopy, recSizeMotionCopy, recRgbMotionCopy, 10,
                    recPosMotionCopy, recSizeMotionCopy, recRgbMotionCopy, false,
                false);


    ArrayList oneMotion = new ArrayList<IMotion>(Arrays.asList(motionRecCopy));

    // create shape and motion
    ShapeAndMotion shapeAndMotionCopy = new ShapeAndMotion(copyRec, oneMotion,1,50);

    assertEquals(shapeAndMotionCopy.toString(), shapeAndMotion1.copyShapeAndMotion().toString());
  }

  // updateDoNothingMotion
  @Test
  public void testUpdateDoNothing() {
    // testing if a ShapeAndMotion with a 'do nothing' motion
    // has its starting and ending characteristic updated according to the motion before it
    IShapeAndMotion sm1 = new ShapeAndMotion(rectangle.copyIShape2D(),
        new ArrayList<>(Arrays.asList(
            new Motion("R", 1, 5, recStartPos, recStartSize, recStartRgb),
            new Motion("R", 5, 15),
            new Motion("R", 15, 20,
                recPosMotion1, recSizeMotion1, recRgbMotion1))), 1, 20);

    IShapeAndMotion smDoNothingUpdated = new ShapeAndMotion(rectangle.copyIShape2D(),
        new ArrayList<>(Arrays.asList(
            new Motion("R", 1, 5, recStartPos, recStartSize, recStartRgb),
            new Motion("R", 5, recStartPos, recStartSize, recStartRgb,
                15, recStartPos, recStartSize, recStartRgb, true, true),
            new Motion("R", 15, 20,
                recPosMotion1, recSizeMotion1, recRgbMotion1))), 1, 20);

    smDoNothingUpdated.sortMotionsOnStartTime();

    sm1.updateDoNothingMotion();

    assertEquals(smDoNothingUpdated, sm1);

    // testing for ShapeAndMotion with no do nothing motions
    IShapeAndMotion smNoDoNothing = new ShapeAndMotion(rectangle.copyIShape2D(),
        new ArrayList<>(Arrays.asList(
            new Motion("R", 1, 5, recStartPos, recStartSize, recStartRgb),
            new Motion("R", 5, 15, new Position2D(20, 20),
                new Size2D(5, 4), new Rgb(175, 0, 0)),
            new Motion("R", 15, 20,
                recPosMotion1, recSizeMotion1, recRgbMotion1))), 1, 20);

    // updating the 'do nothing' motions
    smNoDoNothing.updateDoNothingMotion();

    assertEquals(smNoDoNothing, smNoDoNothing);

    // testing for a ShapeAndMotion with more than one 'do nothing' motion
    // ShapeAndMotion with 2 'do nothing' motions
    IShapeAndMotion sm2 = new ShapeAndMotion(rectangle.copyIShape2D(),
        new ArrayList<>(Arrays.asList(
            new Motion("R", 1, 5, recStartPos, recStartSize, recStartRgb),
            new Motion("R", 5, 15),
            new Motion("R", 15, 20),
            new Motion("R", 20, 26,
                recPosMotion1, recSizeMotion1, recRgbMotion1))), 1, 26);

    IShapeAndMotion smDoNothingUpdated2 = new ShapeAndMotion(rectangle.copyIShape2D(),
        new ArrayList<>(Arrays.asList(
            new Motion("R", 1, 5, recStartPos, recStartSize, recStartRgb),
            new Motion("R", 5, recStartPos, recStartSize, recStartRgb,
                15, recStartPos, recStartSize, recStartRgb, true, true),
            new Motion("R", 15, recStartPos, recStartSize, recStartRgb,
                20, recStartPos, recStartSize, recStartRgb, true, true),
            new Motion("R", 20, 26,
                recPosMotion1, recSizeMotion1, recRgbMotion1))), 1, 26);

    // updating the 'do nothing' motions
    sm2.updateDoNothingMotion();

    smDoNothingUpdated2.sortMotionsOnStartTime();

    assertEquals(smDoNothingUpdated2, sm2);

    // testing for a ShapeAndMotion with a 'do nothing' motion at the beginning
    IShapeAndMotion sm3 = new ShapeAndMotion(rectangle.copyIShape2D(),
        new ArrayList<>(Arrays.asList(
            new Motion("R", 1, 5),
            new Motion("R", 5, 15, recStartPos, recStartSize, recStartRgb),
            new Motion("R", 15, 20,
                recPosMotion1, recSizeMotion1, recRgbMotion1))), 1, 20);

    // updating the 'do nothing' motions
    sm3.updateDoNothingMotion();

    assertEquals(sm3, sm3);
  }

  // tests for the method checkMotionCoherence
  @Test
  public void testCheckMotionCoherence() {
    // testing for a ShapeAndMotion with coherent motions
    IShapeAndMotion sm1 = new ShapeAndMotion(rectangle.copyIShape2D(),
        new ArrayList<>(Arrays.asList(
            new Motion("R", 1, 5, recStartPos, recStartSize, recStartRgb),
            new Motion("R", 5, 15,
                recPosMotion1, recSizeMotion1, recRgbMotion1),
            new Motion("R", 15, 20))), 1, 20);

    assertEquals(true, sm1.checkMotionCoherence());

    // testing for a ShapeAndMotion with motions creating gaps
    IShapeAndMotion smGap = new ShapeAndMotion(rectangle.copyIShape2D(),
        new ArrayList<>(Arrays.asList(
            new Motion("R", 1, 5, recStartPos, recStartSize, recStartRgb),
            new Motion("R", 10, 15,
                recPosMotion1, recSizeMotion1, recRgbMotion1),
            new Motion("R", 15, 20))), 1, 20);

    assertEquals(false, smGap.checkMotionCoherence());

    // testing for a ShapeAndMotion with motions creating overlaps
    IShapeAndMotion smOverlap = new ShapeAndMotion(rectangle.copyIShape2D(),
        new ArrayList<>(Arrays.asList(
            new Motion("R", 1, 5, recStartPos, recStartSize, recStartRgb),
            new Motion("R", 3, 15,
                recPosMotion1, recSizeMotion1, recRgbMotion1),
            new Motion("R", 15, 20))), 1, 20);

    assertEquals(false, smGap.checkMotionCoherence());
  }

  // test link motion
  @Test
  public void testLinkMotions() {
    // first in motion list
    Motion outOfSync1 =
        new Motion("rectangle", 1,
            recPosMotion1, recSizeMotion1, recRgbMotion1, 10,
            recPosMotion1, recSizeMotion1, recRgbMotion1,false,false);
    Motion inSync1 =
        new Motion("rectangle", 1,
            new Position2D(0, 0), new Size2D(10, 10),
            new Rgb(0, 0, 0), 10,
            recPosMotion1, recSizeMotion1, recRgbMotion1,true,false);


    // second in motion list
    Motion outOfSync2 =
        new Motion("rectangle", 15,
            new Position2D(0, 0), new Size2D(10, 10),
            new Rgb(0, 0, 0), 50,
            recPosMotion2, recSizeMotion2, recRgbMotion2,false, false);

    // second in motion list
    Motion inSync2 =
        new Motion("rectangle", 15,
            recPosMotion1, recSizeMotion1, recRgbMotion1, 50,
            recPosMotion2, recSizeMotion2, recRgbMotion2,true, false);

    ArrayList oneMotionOutSync = new ArrayList<IMotion>(Arrays.asList(outOfSync1));
    ArrayList motionsOutSync = new ArrayList<IMotion>(Arrays.asList(outOfSync1, outOfSync2));

    // create shape and motion
    ShapeAndMotion syncMotionOne = new ShapeAndMotion(rectangle, oneMotionOutSync,
        1,25);
    ShapeAndMotion syncMotions = new ShapeAndMotion(rectangle, motionsOutSync,1,25);

    // has one motion
    assertEquals(false, syncMotionOne.getMotions().get(0).getSynchro());
    assertEquals(outOfSync1, syncMotionOne.getMotions().get(0));
    syncMotionOne.linkMotions();
    assertEquals(true, syncMotionOne.getMotions().get(0).getSynchro());
    assertEquals(inSync1, syncMotionOne.getMotions().get(0));

    // linking two motions
    assertEquals(false, syncMotions.getMotions().get(1).getSynchro());
    assertEquals(outOfSync2, syncMotions.getMotions().get(1));
    syncMotions.linkMotions();
    // synced motions by getting details from the precedent motion and applying it
    assertEquals(true, syncMotions.getMotions().get(1).getSynchro());
    assertEquals(inSync2, syncMotions.getMotions().get(1));

  }

  // apply motion when current tick = t final
  @Test
  public void testApplyMotionPartOne() {
    // first in motion list
    Motion motion1 =
        new Motion("rectangle", 1,
            recPosMotion1, recSizeMotion1, recRgbMotion1, 10,
            recPosMotion1, recSizeMotion1, recRgbMotion1,false,false);
    // second in motion list
    Motion motion2 =
        new Motion("rectangle", 15,
            new Position2D(0, 0), new Size2D(10, 10),
            new Rgb(0, 0, 0), 50,
            recPosMotion2, recSizeMotion2, recRgbMotion2,false, false);

    // second in motion list
    Motion changeFeatures =
        new Motion("rectangle", 1,
            new Position2D(10, 10), new Size2D(50, 50),
            new Rgb(255, 255, 255), 25,
            new Position2D(10, 10), new Size2D(50, 50),
            new Rgb(255, 255, 255),false, false);

    ArrayList oneMotion = new ArrayList<IMotion>(Arrays.asList(changeFeatures));
    ArrayList motions = new ArrayList<IMotion>(Arrays.asList(motion1, motion2));

    // create shape and motion
    ShapeAndMotion shapeAndMotionOne = new ShapeAndMotion(rectangle, oneMotion,1,25);
    ShapeAndMotion shapeAndMotion = new ShapeAndMotion(rectangle, motions,1,25);

    // if new tick == final tick
    assertEquals(new Position2D(0.000000, 0.000000), rectangle.getPos());
    assertEquals(new Size2D(10,10), rectangle.getSize());
    assertEquals(new Rgb(255,0,0), rectangle.getRgb());
    // apply at t final
    shapeAndMotionOne.applyMotion(25);
    // check each fields again
    assertEquals(new Position2D(10.000000, 10.000000), rectangle.getPos());
    assertEquals(new Size2D(50,50), rectangle.getSize());
    assertEquals(new Rgb(255,255,255), rectangle.getRgb());

  }

  // apply motion when current tick != t final
  @Test
  public void testApplyMotionPartTwo() {
    // second in motion list
    Motion changeFeatures =
        new Motion("rectangle", 3,
            new Position2D(0, 0), new Size2D(50, 50),
            new Rgb(255, 255, 255), 15,
            new Position2D(15, 20), new Size2D(20, 10),
            new Rgb(50, 50, 50),false, false);

    ArrayList oneMotion = new ArrayList<IMotion>(Arrays.asList(changeFeatures));

    // create shape and motion
    ShapeAndMotion shapeAndMotionOne = new ShapeAndMotion(rectangle, oneMotion,3,15);

    // if new tick != final tick
    assertEquals(new Position2D(0.000000, 0.000000), rectangle.getPos());
    assertEquals(new Size2D(10,10), rectangle.getSize());
    assertEquals(new Rgb(255,0,0), rectangle.getRgb());
    // apply at t final
    shapeAndMotionOne.applyMotion(13);
    // check each fields again
    assertEquals(new Position2D(12.5, 16.666667), shapeAndMotionOne.getShape().getPos());
    assertEquals(new Size2D(25,16), shapeAndMotionOne.getShape().getSize());
    assertEquals(new Rgb(84,84,84), shapeAndMotionOne.getShape().getRgb());

  }

  // apply motion exceptions
  @Test (expected = IllegalArgumentException.class)
  public void testApplyMotionException() {
    // first in motion list
    Motion motion1 =
        new Motion("rectangle", 1,
            recPosMotion1, recSizeMotion1, recRgbMotion1, 10,
            recPosMotion1, recSizeMotion1, recRgbMotion1,false,false);
    // second in motion list
    Motion motion2 =
        new Motion("rectangle", 15,
            new Position2D(0, 0), new Size2D(10, 10),
            new Rgb(0, 0, 0), 50,
            recPosMotion2, recSizeMotion2, recRgbMotion2,false, false);


    ArrayList motions = new ArrayList<IMotion>(Arrays.asList(motion1, motion2));

    // create shape and motion
    ShapeAndMotion shapeAndMotion = new ShapeAndMotion(rectangle, motions,1,50);

    // motions only from 1-25 but no motion for range greater than that
    shapeAndMotion.applyMotion(60);
  }

  // test get motion at time
  @Test
  public void testGetMotionAtTime() {
    // first in motion list
    Motion motion1 =
            new Motion("rectangle", 1,
                    recPosMotion1, recSizeMotion1, recRgbMotion1, 10,
                    recPosMotion1, recSizeMotion1, recRgbMotion1,false,false);
    // second in motion list
    Motion motion2 =
            new Motion("rectangle", 15,
                    new Position2D(0, 0), new Size2D(10, 10),
                    new Rgb(0, 0, 0), 50,
                    recPosMotion2, recSizeMotion2, recRgbMotion2,false, false);

    ArrayList motions = new ArrayList<IMotion>(Arrays.asList(motion1, motion2));

    // create shape and motion
    ShapeAndMotion shapeAndMotionOne = new ShapeAndMotion(rectangle, motions,1,25);
    assertEquals(motion1, shapeAndMotionOne.getMotionAtTime(5));
    assertEquals(motion2, shapeAndMotionOne.getMotionAtTime(18));
  }

  // test get motion at time exception motion not found
  @Test (expected = IllegalArgumentException.class)
  public void testGetMotionAtTimeException() {
    // first in motion list
    Motion motion1 =
            new Motion("rectangle", 1,
                    recPosMotion1, recSizeMotion1, recRgbMotion1, 10,
                    recPosMotion1, recSizeMotion1, recRgbMotion1,false,false);
    // second in motion list
    Motion motion2 =
            new Motion("rectangle", 15,
                    new Position2D(0, 0), new Size2D(10, 10),
                    new Rgb(0, 0, 0), 50,
                    recPosMotion2, recSizeMotion2, recRgbMotion2,false, false);

    ArrayList motions = new ArrayList<IMotion>(Arrays.asList(motion1, motion2));

    // create shape and motion
    ShapeAndMotion shapeAndMotionOne = new ShapeAndMotion(rectangle, motions,1,25);
    assertEquals(motion1, shapeAndMotionOne.getMotionAtTime(100));
  }



}

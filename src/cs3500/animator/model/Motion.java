package cs3500.animator.model;

import cs3500.animator.model.shape.Position2D;
import cs3500.animator.model.shape.Rgb;
import cs3500.animator.model.shape.Size2D;

import java.util.Objects;

/**
 * Represents the motions that will be applied to shapes and the methods required for it.
 * The class contains a shape name, all the details about the initial shape as well as
 * end time and detailed featured about the final shape.
 *
 */
public class Motion implements IMotion {
  private final String shapeName;
  private final int start;
  private Position2D startPos;
  private Size2D startSize;
  private Rgb startColor;

  private final int end;
  private Position2D newPos;
  private Size2D newSize;
  private Rgb newColor;

  private boolean synchro = false;
  private final boolean doNothing;

  /**
   * The features required to represent a motion which takes in the initial features
   * and also the new features for each motion over a particular period of time.
   * @param shapeName of the shape we want to animate
   * @param start start time of animation of shape
   * @param startPos start position of animation of shape
   * @param startSize start size of animation of shape
   * @param startColor start color of animation of shape
   * @param end end time of animation of shape
   * @param newPos new position of animation of shape
   * @param newSize new size of animation of shape
   * @param newColor new color of animation of shape
   */
  public Motion(String shapeName, int start, Position2D startPos, Size2D startSize, Rgb startColor,
      int end, Position2D newPos, Size2D newSize, Rgb newColor, boolean synchro,
      boolean doNothing) {

    if (start < 0 || end < 0 || end < start) {
      throw new IllegalArgumentException("start or end value is invalid.");
    }

    this.shapeName = shapeName;

    this.start = start;
    this.startPos = startPos;
    this.startSize = startSize;
    this.startColor = startColor;

    this.end = end;
    this.newPos = newPos;
    this.newSize = newSize;
    this.newColor = newColor;

    this.synchro = synchro;
    this.doNothing = doNothing;
  }

  /**
   * The features required to represent a motion which takes in all of the starting features
   * and also the new features for each motion over a particular period of time.
   * @param shapeName name of shape
   * @param start start time of motion
   * @param startPos starting position of motion
   * @param startSize starting size of motion
   * @param startColor starting color of motion
   * @param end end time of motion
   * @param newPos new position of motion
   * @param newSize new size of motion
   * @param newColor new color of motion
   */
  public Motion(String shapeName, int start, Position2D startPos, Size2D startSize, Rgb startColor,
      int end, Position2D newPos, Size2D newSize,
      Rgb newColor) {

    this(shapeName, start, startPos,
        startSize, startColor,
        end, newPos, newSize, newColor, true, false);
  }

  /**
   * The features required to represent a motion which takes in the new features for each motion
   * over a particular period of time.
   * @param start start time of animation of shape
   * @param end end time of animation of shape
   * @param newPos new postion of animation of shape
   * @param newSize new size of animation of shape
   * @param newColor new color of animation of shape
   */

  public Motion(String shapeName, int start, int end, Position2D newPos, Size2D newSize,
      Rgb newColor) {

    this(shapeName, start, new Position2D(0, 0),
        new Size2D(10, 10), new Rgb(0, 0, 0),
        end, newPos, newSize, newColor, false, false);
  }

  /**
   * If the motion is not being changed, this constructor is used which simply takes in a
   * start and end time so that only those features are changed.
   * @param shapeName of the shape that we want to animate
   * @param start start time of animation of shape
   * @param end end time of animation of shape
   */
  public Motion(String shapeName, int start, int end) {
    this(shapeName, start, new Position2D(0, 0),
        new Size2D(10, 10), new Rgb(0, 0, 0), end,
        new Position2D(0, 0),
        new Size2D(10, 10), new Rgb(0, 0, 0), false, true);
  }

  @Override
  public String getShapeName() {
    return this.shapeName;
  }

  @Override
  public int getStartTime() {
    return this.start;
  }

  @Override
  public int getEndTime() {
    return this.end;
  }

  @Override
  public Position2D getStartPos() {
    return new Position2D(this.startPos.getX(), this.startPos.getY());
  }

  @Override
  public Position2D getEndPos() {
    return new Position2D(this.newPos.getX(), this.newPos.getY());
  }


  @Override
  public Size2D getStartSize() {
    return new Size2D(this.startSize.getWidth(), this.startSize.getHeight());
  }

  @Override
  public Size2D getEndSize() {
    return new Size2D(this.newSize.getWidth(), this.newSize.getHeight());
  }

  @Override
  public Rgb getStartColor() {
    return new Rgb(this.startColor.getR(), this.startColor.getG(), this.startColor.getB());

  }

  @Override
  public Rgb getEndColor() {
    return new Rgb(this.newColor.getR(), this.newColor.getG(), this.newColor.getB());
  }

  @Override
  public boolean getSynchro() {
    return this.synchro;
  }

  @Override
  public boolean getDoNothing() {
    return this.doNothing;
  }

  @Override
  public boolean equals(Object a) {
    if (this == a) {
      return true;
    }

    if (!(a instanceof Motion)) {
      return false;
    }

    Motion that = (Motion) a;

    return this.start == that.start && this.end == that.end
        && this.getStartPos().equals(that.getStartPos())
        && this.getStartSize().equals(that.getStartSize())
        && this.getStartColor().equals(that.getStartColor())
        && this.newPos.equals(that.newPos) && this.newSize.equals(that.newSize)
        && this.newColor.equals(that.newColor) && this.synchro == that.getSynchro()
        && this.doNothing == that.getDoNothing();
  }

  @Override
  public int hashCode() {
    return Objects.hash(this.shapeName, this.start, this.startPos, this.startSize, this.startColor,
        this.end, this.newPos, this.newSize, this.newColor);
  }

  @Override
  public Motion copyMotion() {
    return new Motion(this.shapeName, this.start, this.getStartPos(), this.getStartSize(),
        this.getStartColor(), this.end, this.getEndPos(), this.getEndSize(), this.getEndColor(),
        this.synchro, this.doNothing);
  }

  @Override
  public String toString() {
    String finalResult = String.format("motion %s %d %s %s %s %s %s %s %s   "
            + "%s %s %s %s %s %s %s %s %s %s", this.shapeName, this.start,
        this.getStartPos().getX(), this.getStartPos().getY(),
        this.getStartSize().getWidth(), this.getStartSize().getHeight(),
        this.getStartColor().getR(), this.getStartColor().getG(), this.getStartColor().getB(),
        this.end,
        this.getEndPos().getX(), this.getEndPos().getY(),
        this.getEndSize().getWidth(), this.getEndSize().getHeight(),
        this.getEndColor().getR(), this.getEndColor().getG(), this.getEndColor().getB(),
        Boolean.toString(this.getSynchro()), Boolean.toString(this.getDoNothing()));

    return finalResult;
  }

  @Override
  public String toTextualString() {
    String finalResult = String.format("motion %s %d %s %s %s %s %s %s %s   "
            + "%s %s %s %s %s %s %s %s", this.shapeName, this.start,
        this.getStartPos().getX(), this.getStartPos().getY(),
        this.getStartSize().getWidth(), this.getStartSize().getHeight(),
        this.getStartColor().getR(), this.getStartColor().getG(), this.getStartColor().getB(),
        this.end,
        this.getEndPos().getX(), this.getEndPos().getY(),
        this.getEndSize().getWidth(), this.getEndSize().getHeight(),
        this.getEndColor().getR(), this.getEndColor().getG(), this.getEndColor().getB());

    return finalResult;
  }
}

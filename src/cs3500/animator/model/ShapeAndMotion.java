package cs3500.animator.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import cs3500.animator.model.shape.IShape2D;
import cs3500.animator.model.shape.Position2D;
import cs3500.animator.model.shape.Rgb;
import cs3500.animator.model.shape.Size2D;

/**
 * Represents a class that combines the shape and motion features so that we can use
 * both of them together.
 */
public class ShapeAndMotion implements IShapeAndMotion {
  private IShape2D shape;
  private List<IMotion> motions;
  int tStart;
  int tFinal;
  boolean sorted;

  /**
   * Combines shape and motion so that we can use them together.
   * @param shape that we want to store with motion
   * @param motions that we want to store in shape
   * @param tStart start time of the shape and motion
   * @param tFinal end time of the shape and motion
   * @param sorted whether the motions are sorted or not
   */
  public ShapeAndMotion(IShape2D shape, List<IMotion> motions, int tStart, int tFinal,
      boolean sorted) {
    this.shape = shape;
    this.motions = motions;
    this.tStart = this.earliestTime(motions);
    this.tFinal = this.latestTime(motions);
    this.sorted = sorted;
  }

  /**
   * Combines shape and motion so that we can use them together and doesn't have the sorted
   * field.
   * @param shape that we want to store in motion
   * @param motions that we want to store in shape
   * @param tStart start time of the shape and motion
   * @param tFinal end time of the shape and motion
   */
  public ShapeAndMotion(IShape2D shape, List<IMotion> motions, int tStart, int tFinal) {
    this(shape, motions, tStart, tFinal, false);
  }

  /**
   * Shape in empty motion.
   * @param shape that we want to store in empty motion
   */
  public ShapeAndMotion(IShape2D shape) {
    this(shape, new ArrayList<>(), 0, 0, false);
  }

  @Override
  public int getStartTime() {
    return this.tStart;
  }

  @Override
  public int getEndTime() {
    return this.tFinal;
  }

  @Override
  public IShape2D getShape() {
    return this.shape.copyIShape2D();
  }

  @Override
  public List<IMotion> getMotions() {
    List<IMotion> copyMotions = new ArrayList<>();
    for (IMotion m : this.motions) {
      copyMotions.add(m.copyMotion());
    }
    return copyMotions;
  }


  @Override
  public void addMotion(IMotion motion) throws IllegalArgumentException {
    // checking if the given motion is null
    if (motion == null) {
      throw new IllegalArgumentException("motion cannot be null");
    }

    if (this.tStart > motion.getStartTime()) {
      this.tStart = motion.getStartTime();
    }

    if (this.tFinal < motion.getEndTime()) {
      this.tFinal = motion.getEndTime();
    }

    if (!this.motions.contains(motion)) {
      this.motions.add(motion);
    }
  }

  @Override
  public void sortMotionsOnStartTime() {
    List<IMotion> copyMotions = new ArrayList<>();

    for (IMotion m : this.motions) {
      copyMotions.add(m.copyMotion());
    }

    List<IMotion> result = new ArrayList<>();

    while (copyMotions.size() > 0) {
      // the condition of the while guarantees that there is at least one element in lMotions
      IMotion firstMotion = copyMotions.get(0);
      for (IMotion m : copyMotions) {
        if (firstMotion.getStartTime() > m.getStartTime()) {
          firstMotion = m;
        }
      }

      // adding earliest motion to the result
      result.add(firstMotion);
      // remove the motion from the given list
      copyMotions.remove(firstMotion);
    }
    // setting the motions to the new chronologically sorted list of motions
    this.motions = result;
    this.sorted = true;
  }

  @Override
  public boolean checkMotionCoherence() {
    // sort the motions if not sorted
    if (!this.sorted) {
      this.sortMotionsOnStartTime();
    }

    for (int ii = 0; ii < this.motions.size(); ii++) {
      // if the motion is not the first in the list of motions
      if (ii != 0) {
        IMotion currentMotion = this.motions.get(ii);
        IMotion precedentMotion = this.motions.get(ii - 1);

        // create a gap if the start is greater than its predecessor's end time
        // create an overlap if the start is smaller than its predecessor's end time
        if (currentMotion.getStartTime() != precedentMotion.getEndTime()) {
          // in such case return false
          return false;
        }
      }
    }
    // no incoherence spotted
    return true;
  }

  @Override
  public void updateDoNothingMotion() {
    // sort the motions if not sorted
    if (!this.sorted) {
      this.sortMotionsOnStartTime();
    }

    // checks if the stats fields of teh motion object are nulls
    // if yes then if the motion is the first in the list then, set it to default values
    // else set both the start and end stats to the end stats of the object before it
    for (int ii = 0; ii < this.motions.size(); ii++) {
      IMotion m = this.motions.get(ii);
      if (m.getDoNothing()) {
        if (ii == 0) {
          this.motions.set(0, new Motion(m.getShapeName(), m.getStartTime(),
              new Position2D(0, 0), new Size2D(10, 10),
              new Rgb(0, 0, 0), m.getEndTime(), new Position2D(0, 0),
              new Size2D(10, 10), new Rgb(0, 0, 0),
              true, true));
        }
        else {
          IMotion precedentMotion = this.motions.get(ii - 1);
          this.motions.set(ii, new Motion(m.getShapeName(), m.getStartTime(),
              precedentMotion.getEndPos(), precedentMotion.getEndSize(),
              precedentMotion.getEndColor(), m.getEndTime(), precedentMotion.getEndPos(),
              precedentMotion.getEndSize(), precedentMotion.getEndColor(), true,
              true));
        }
      }
    }
  }

  @Override
  public void linkMotions() {
    // sort the motions if not sorted
    if (!this.sorted) {
      this.sortMotionsOnStartTime();
    }

    for (int ii = 0; ii < this.motions.size(); ii++) {
      IMotion m = this.motions.get(ii);
      if (!m.getSynchro() && !m.getDoNothing()) {
        if (ii == 0) {
          this.motions.set(0, new Motion(m.getShapeName(), m.getStartTime(),
              new Position2D(0, 0), new Size2D(10, 10),
              new Rgb(0, 0, 0), m.getEndTime(), m.getEndPos(),
              m.getEndSize(), m.getEndColor(), true, false));
        }
        else {
          IMotion precedentMotion = this.motions.get(ii - 1);
          this.motions.set(ii, new Motion(m.getShapeName(), m.getStartTime(),
              precedentMotion.getEndPos(), precedentMotion.getEndSize(),
              precedentMotion.getEndColor(), m.getEndTime(), m.getEndPos(),
              m.getEndSize(), m.getEndColor(), true, false));
        }
      }
    }
  }

  @Override
  public void applyMotion(int newTick) throws IllegalArgumentException {
    if ((newTick < this.tStart) || (newTick > this.tFinal)) {
      throw new IllegalArgumentException("This shape has no motion for the given time");
    }

    // sort the motions if not sorted
    if (!this.sorted) {
      this.sortMotionsOnStartTime();
    }

    int newTick2 = newTick;

    if (newTick == this.tFinal) {
      IMotion m = this.motions.get(this.motions.size() - 1);
      this.shape.move(m.getEndPos());

      this.shape.resize(m.getEndSize());

      this.shape.colorize(m.getEndColor());
    }
    else {
      for (IMotion m : this.motions) {
        if (m.getStartTime() <= newTick2 && m.getEndTime() > newTick2) {
          double motionProgressionPercentage =
              ((double) newTick2 - (double) m.getStartTime())
                  / ((double) m.getEndTime() - (double) m.getStartTime());

          double xProgression = m.getStartPos().getX()
              + (m.getEndPos().getX() - m.getStartPos().getX()) * motionProgressionPercentage;
          double yProgression = m.getStartPos().getY() +
              (m.getEndPos().getY() - m.getStartPos().getY()) * motionProgressionPercentage;
          int widthProgression = (int) (m.getStartSize().getWidth() +
              (m.getEndSize().getWidth() - m.getStartSize().getWidth())
                  * motionProgressionPercentage);
          int heightProgression = (int) (m.getStartSize().getHeight() +
              (m.getEndSize().getHeight() - m.getStartSize().getHeight())
                  * motionProgressionPercentage);
          int rProgression = (int) (m.getStartColor().getR()
              + (m.getEndColor().getR() - m.getStartColor().getR()) * motionProgressionPercentage);
          int gProgression = (int) (m.getStartColor().getG() +
              (m.getEndColor().getG() - m.getStartColor().getG()) * motionProgressionPercentage);
          int bProgression = (int) (m.getStartColor().getB() +
              (m.getEndColor().getB() - m.getStartColor().getB()) * motionProgressionPercentage);

          // move the shape
          this.shape.move(new Position2D(xProgression, yProgression));

          // resize the shape
          this.shape.resize(new Size2D(widthProgression, heightProgression));

          // colorize the shape
          this.shape.colorize(new Rgb(rProgression, gProgression, bProgression));
        }
      }
    }
  }


  @Override
  public IShapeAndMotion copyShapeAndMotion() {
    List<IMotion> copyMotions = new ArrayList<>();

    for (IMotion m : this.motions) {
      copyMotions.add(m.copyMotion());
    }

    return new ShapeAndMotion(this.shape.copyIShape2D(),
            copyMotions, this.tStart, this.tFinal, this.sorted);
  }

  @Override
  public String toString() {
    String finalResult = String.format("%s, Motion: %s, %d, %d, %s", shape.toString(),
            motions.toString(), this.tStart, this.tFinal, Boolean.toString(this.sorted));

    return finalResult;
  }

  @Override
  public boolean equals(Object a) {
    if (this == a) {
      return true;
    }

    if (!(a instanceof ShapeAndMotion)) {
      return false;
    }

    ShapeAndMotion that = (ShapeAndMotion) a;

    boolean motionsEquals = (this.motions.size() == that.motions.size());
    for (int ii = 0 ; ii < this.motions.size(); ii++) {
      motionsEquals = motionsEquals && (this.motions.get(ii).equals(that.motions.get(ii)));
    }
    return this.getShape().equals(that.getShape())
            && motionsEquals
            && this.tStart == that.tStart && this.tFinal == that.tFinal
            && this.sorted == that.sorted;
  }

  @Override
  public int hashCode() {
    return Objects.hash(this.shape, this.motions, this.tStart, tFinal, sorted);
  }

  /**
   * Checks the earliest time in the list of motions inputted.
   * @param motions the list of motions for which we want the earliest time
   * @return the time that is earliest in the list
   */
  private static int earliestTime(List<IMotion> motions) {
    int earliestTime = 0;
    for (int ii = 0; ii < motions.size(); ii++) {
      if (ii == 0) {
        earliestTime = motions.get(ii).getStartTime();
      }
      else {
        if (motions.get(ii).getStartTime() < earliestTime) {
          earliestTime = motions.get(ii).getStartTime();
        }
      }
    }
    return earliestTime;
  }


  /**
   * The latest time in the list of motions inputted.
   * @param motions the motions for which we want the latest time for
   * @return the latest time in int
   */
  private static int latestTime(List<IMotion> motions) {
    int latestTime = 0;
    for (int ii = 0; ii < motions.size(); ii++) {
      if (ii == 0) {
        latestTime = motions.get(ii).getEndTime();
      }
      else {
        if (motions.get(ii).getEndTime() > latestTime) {
          latestTime = motions.get(ii).getEndTime();
        }
      }
    }
    return latestTime;
  }

  @Override
  public IMotion getMotionAtTime(int time) throws IllegalArgumentException {
    // sort the motions if not sorted
    if (!this.sorted) {
      this.sortMotionsOnStartTime();
    }

    if ((time < this.getStartTime()) || (time > this.getEndTime())) {
      throw new IllegalArgumentException("Motion for the desired time is not available");
    }

    IMotion result = null;

    for (IMotion m : this.motions) {
      if (time < this.tFinal) {
        if ((m.getStartTime() <= time) && (time < m.getEndTime())) {
          result = m;
        }
      }
      else {
        if ((m.getStartTime() <= time) && (time <= m.getEndTime())) {
          result = m;
        }
      }
    }
    return result;
  }

  @Override
  public void updateShape(Position2D pos, Size2D size, Rgb color) {
    this.shape.move(pos);
    this.shape.resize(size);
    this.shape.colorize(color);
  }
}







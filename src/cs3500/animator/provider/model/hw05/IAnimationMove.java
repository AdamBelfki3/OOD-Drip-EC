package cs3500.animator.provider.model.hw05;

import cs3500.animator.model.shape.Position2D;
import cs3500.animator.model.shape.Rgb;
import cs3500.animator.model.shape.Size2D;

/**
 * An interface describing how a shape will move over a given time.
 */
public interface IAnimationMove extends Comparable<IAnimationMove> {

  /**
   * Gets the startTick of the move.
   * @return start tick
   */
  int getStartTick();

  /**
   * Gets the endTick of the move.
   * @return end tick
   */
  int getEndTick();

  /**
   * Gets the X value of the movement.
   * @return the Y value
   */
  int getX();

  /**
   * Gets the Y value of the movement.
   * @return the Y value
   */
  int getY();

  /**
   * Gets the Width value of the movement.
   * @return the W value
   */
  int getW();

  /**
   * Gets the Height value of the movement.
   * @return the H value
   */

  int getH();

  /**
   * Gets the Red value to update the shape's color.
   * @return the red value
   */
  int getR();

  /**
   * Gets the Green value to update the shape's color.
   * @return the green value
   */
  int getG();

  /**
   * Gets the Blue value to update the shape's color.
   * @return the blue value
   */
  int getB();

  /**
   * The start position of the motion.
   * @return the position in 2d.
   */
  public Position2D getStartPos();

  /**
   * The new position of the motion.
   * @return the position in 2d.
   */
  public Position2D getEndPos();

  /**
   * The start size of the motion.
   * @return the size in 2d
   */
  public Size2D getStartSize();

  /**
   * The new size of the motion.
   * @return the size in 2d
   */
  public Size2D getEndSize();

  /** The start color of the motion.
   * @return the motion in rgb.
   */
  public Rgb getStartColor();

  /** The end color of the motion.
   * @return the motion in rgb.
   */
  public Rgb getEndColor();

}

package cs3500.animator.provider.model.hw05;

import cs3500.animator.provider.model.hw05.shape.DTOAbstractShape;

import java.util.List;
import java.util.Map;

/**
 * An interface representing a AnimationModel with shapes and their animations.
 */
public interface IAnimationModel {
  /**
   * Updates the shape attributes.
   * @param name the name of the shape
   * @param x the x position of the shape
   * @param y the y position of the shape
   * @param w the width value of the shape
   * @param h the height value of the shape
   * @param r red value of the color of the shape
   * @param g green value of the color of the shape
   * @param b blue value of the color of the shape
   */
  void setStartShape(String name, int x, int y, int w, int h, int r, int g, int b);

  /**
   * Adds the shape to the model.
   * @param shape the shape type
   * @param name the name of the shape
   */
  void addShape(DTOAbstractShape shape, String name);

  /**
   * Adds a move for an animation of a shape.
   * @param shapeName the name of the shape
   * @param move the moves to add to the shape
   */
  void addAnimationMoveToShape(String shapeName, IAnimationMove move);

  /**
   * Gets the length of the animation which is the largest end tick of all shape's AnimationMove.
   * @return the length of the animation
   */
  int getAnimationLength();

  List<DTOAbstractShape> getShapesAtTick(int tick);

  /**
   * Gets a shape.
   * @param name name of the shape
   * @return the shape
   */
  DTOShapeAnimation getShape(String name);

  /**
   * Gets the width of the canvas.
   * @return the canvas width
   */
  int getBoundingWidth();

  /**
   * Gets the height of the canvas.
   * @return the canvas height
   */
  int getBoundingHeight();

  /**
   * Get the bounding X of the canvas.
   * @return the bounding X
   */
  int getBoundingX();

  /**
   * Get the bounding Y of the canvas.
   * @return the bounding Y
   */
  int getBoundingY();

  /**
   * Sets the bounds of the canvas.
   * @param x the x bound of the canvas
   * @param y the y bound of the canvas
   * @param width the width of the canvas
   * @param height the height of the canvas
   * @throws IllegalArgumentException if any arguments are invalid
   */
  void setBounds(int x, int y, int width, int height) throws IllegalArgumentException;

  Map<String, DTOShapeAnimation> getAllShapeAnimations();
}

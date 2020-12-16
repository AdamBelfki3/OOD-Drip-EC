package cs3500.animator.provider.model.hw05;

import cs3500.animator.provider.model.hw05.shape.DTOAbstractShape;

import java.util.ArrayList;
import java.util.List;

/**
 * Describes a shape and its movements in an animation.
 */
public class DTOShapeAnimation {
  private DTOAbstractShape shape;
  private List<IAnimationMove> animationMoveList = new ArrayList<>(); // ordered by startTick

  /**
   * Constructor providing a shape and its movements.
   * @param shape the shape of the animation
   * @param animationMoveList the lists of moves associated with the shape
   */
  public DTOShapeAnimation(DTOAbstractShape shape, List<IAnimationMove> animationMoveList) {
    this.shape = shape;
    this.animationMoveList = animationMoveList;
  }

  /**
   * Gets the shape of the ShapeAnimation.
   * @return shape of the animation
   */
  public DTOAbstractShape getShape() {
    return shape;
  }

  /**
   * Gets all the AnimationMoves for the animation move list.
   * @return the list of moves
   */
  public List<IAnimationMove> getAnimationMoveList() {
    return animationMoveList;
  }
}

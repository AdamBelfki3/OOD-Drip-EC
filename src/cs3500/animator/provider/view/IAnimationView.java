package cs3500.animator.provider.view;

import java.io.IOException;

/**
 * Provides a interface for the view of the animation.
 */
public interface IAnimationView {

  /**
   * Renders this Animation View.
   * @param out the output type
   * @param speed the speed of animation
   * @throws IOException throws an exception if view fails to render
   */
  void render(Appendable out, int speed) throws IOException;

  /**
   * Renders this Animation View.
   * @param speed the new speed of animation
   */
  void updateSpeed(int speed);

  /**
   * Renders this Animation View.
   * @param features the {@code Features} to be added
   */
  void addFeatures(Features features);

  /**
   * Renders this Animation View.
   * @param tick updates the visible shapes at given tick
   */
  void updateShapesAtTick(int tick);
}

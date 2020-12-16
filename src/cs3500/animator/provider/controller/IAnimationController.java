package cs3500.animator.provider.controller;

import cs3500.animator.provider.view.IAnimationView;

/**
 * Provides a interface for controlling and animation. This could be for example playing back a
 * recorded animation or a user-directing animation.
 */
public interface IAnimationController {

  /**
   * Starts the animation.
   * @param speed the ticks per second for the animation to run
   * @param output name of the file to write the output to
   */
  void start(int speed, String output);

  /**
   * Returns the view supplied to this controller.
   */
  IAnimationView getView();
}

package cs3500.animator.provider.view;

/**
 * Represents available features for an animation.
 */
public interface Features {

  /**
   * Start the animation from the beginning.
   */
  void play();

  /**
   * Pause the animation.
   */
  void pause();

  /**
   * Resume the animation from the previously paused time.
   */
  void resume();

  /**
   * Rewind to the start of the animation and replay.
   */
  void restart();

  /**
   * Allow loops in animation.
   */
  void loop();

  /**
   * Speed up the animation.
   */
  void speedUp();

  /**
   * Slow down the animation until 1.
   */
  void slowDown();
}

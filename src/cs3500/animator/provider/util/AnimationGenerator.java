package cs3500.animator.provider.util;

/**
 * An interface for dynamically generating animation files representing algorithms.
 */
public interface AnimationGenerator {

  /**
   * Generate a animation file representing the algorithms.
   * @param file the file to write the animation specifications
   * @throws IllegalStateException throws an exception if the file is not defined
   */
  void generateAnimation(String file) throws IllegalStateException;
}

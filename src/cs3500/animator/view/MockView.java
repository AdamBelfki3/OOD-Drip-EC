package cs3500.animator.view;

import java.io.IOException;
import java.util.Objects;

/**
 * A mock view that contains all the methods from the interactive view and then implements
 * each of the methods by just putting a log append. By doing so, we are able to test if
 * the correct method is being called in the controller when a listener is invoked.
 */
public class MockView extends InteractiveVisualAnimationView {
  Appendable append;

  /**
   * Takes in the appendable where the output for each of the method is stored in.
   *
   * @param append the appendable where the output of each method is added to
   */
  public MockView(Appendable append) {
    this.append = Objects.requireNonNull(append);
  }

  @Override
  public void startAnimation() {
    try {
      append.append("start animation\n");
    } catch (IOException e) {
      // if an exception just catch
    }
  }

  @Override
  public void resumeAnimation() {
    try {
      append.append("resume animation\n");
    } catch (IOException e) {
      // if an exception just catch
    }
  }

  @Override
  public void pauseAnimation() {
    try {
      append.append("pause animation\n");
    } catch (IOException e) {
      // if an exception just catch
    }
  }


  @Override
  public void restartAnimation() {
    try {
      append.append("restart animation\n");
    } catch (IOException e) {
      // if an exception just catch
    }
  }

  @Override
  public void fastForward(int speedUp) {
    try {
      append.append("fast forward animation\n");
    } catch (IOException e) {
      // if an exception just catch
    }
  }

  @Override
  public void fastBackward(int slowDown) {
    try {
      append.append("fast backward animation\n");
    } catch (IOException e) {
      // if an exception just catch
    }
  }

  @Override
  public void loop() {
    try {
      append.append("loop animation\n");
    } catch (IOException e) {
      // if an exception just catch
    }
  }

}

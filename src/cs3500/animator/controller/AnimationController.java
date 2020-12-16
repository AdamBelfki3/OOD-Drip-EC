package cs3500.animator.controller;

import cs3500.animator.view.IVisualAnimationView;
import java.io.IOException;
import java.util.Objects;

/**
 * A controller for the interactive animation view in order to render the interactive view.
 * Additionally,
 * this interface also implements the features interface which contains a list of all the
 * features/abilities of this interactive view and the features methods are implemented
 * by calling the corresponding method from the view.
 */
public class AnimationController implements IController, Features {
  protected IVisualAnimationView view;

  /**
   * The controller constructor takes in the interactive view and then the go method
   * is called on this view which is what allows the view to render as well as add the
   * features to this view. The features are the interactive features that enables the view
   * to become interactive by having the ability to start, resume, pause etc the animation.
   * @param view the interactive view is being passed in because we want this view to have
   *             the various interactive features
   */
  public AnimationController(IVisualAnimationView view) {
    this.view = Objects.requireNonNull(view);
  }

  @Override
  public void execute() {
    try {
      this.view.render();

      // add the various features/abilities to the view
      try {
        this.view.addFeatures(this);
      } catch (UnsupportedOperationException e) {
        // catch unsupported
      }
    }
    catch (IOException e) {
      //catch
    }

  }


  @Override
  public void beginAnimation() {
    view.startAnimation();
  }

  @Override
  public void resumeAnimation() {
    view.resumeAnimation();
  }

  @Override
  public void restartAnimation() {
    view.restartAnimation();
  }

  @Override
  public void pauseAnimation() {
    view.pauseAnimation();
  }

  @Override
  public void fastForward(int timeSpeedUp) {
    this.view.fastForward(timeSpeedUp);
  }

  @Override
  public void fastBackward(int timeSlowDown) {
    this.view.fastBackward(timeSlowDown);
  }

  @Override
  public void loopAnimation() {
    this.view.loop();
  }

  @Override
  public void outlineShapes() {
    throw new UnsupportedOperationException("Outlining shapes not supported in this view!");
  }


  @Override
  public void discreteFrame() {
    throw new UnsupportedOperationException("Outlining shapes not supported in this view!");
  }
}
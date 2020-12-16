package cs3500.animator.provider.view.interactive;

import cs3500.animator.controller.Features;
import cs3500.animator.provider.controller.ProviderInteractiveControllerAdapter;
import cs3500.animator.view.IVisualAnimationView;
import java.io.IOException;

/**
 * We had to create an adaptor that implements our interface methods but uses their code
 * because our implementation had ticks and timer in the view but their implementation has
 * ticks and timer in the controller. So instead, we had to pass in features (implemented
 * by controller) to their view which is what actually contains the implemented methods of
 * each feature since the timer is stored in the controller so the methods here are not
 * being used.
 */
public class InteractiveViewProviderViewAdapter implements IVisualAnimationView {
  private int speed;
  private AnimationInteractiveView view;
  private ProviderInteractiveControllerAdapter pController;

  /**
   * constructor takes in the provider's view so that we are able to add features to it by
   * passing in the controller adapter since that is where the time is being stored.
   * @param view the provider view and using their code to implement this adaptor
   * @param speed the speed to render the animation at
   * @param pController contains the features that is added for the view to be interactive
   */
  public InteractiveViewProviderViewAdapter(AnimationInteractiveView view, int speed,
      ProviderInteractiveControllerAdapter pController) {
    this.view = view;
    this.speed = speed;
    this.pController = pController;
  }

  @Override
  public void startAnimation() {
    // these were the methods for the features methods in our implementation but we are
    // directly implementing these methods in controller since the time is being stored
    // there in provider's code unlike for us since we stored time in the view
  }

  @Override
  public void resumeAnimation() {
    // these were the methods for the features methods in our implementation but we are
    // directly implementing these methods in controller since the time is being stored
    // there in provider's code unlike for us since we stored time in the view
  }

  @Override
  public void pauseAnimation() {
    // these were the methods for the features methods in our implementation but we are
    // directly implementing these methods in controller since the time is being stored
    // there in provider's code unlike for us since we stored time in the view
  }

  @Override
  public void restartAnimation() {
    // these were the methods for the features methods in our implementation but we are
    // directly implementing these methods in controller since the time is being stored
    // there in provider's code unlike for us since we stored time in the view
  }

  @Override
  public void fastForward(int speedUp) {
    // these were the methods for the features methods in our implementation but we are
    // directly implementing these methods in controller since the time is being stored
    // there in provider's code unlike for us since we stored time in the view
  }

  @Override
  public void fastBackward(int slowDown) {
    // these were the methods for the features methods in our implementation but we are
    // directly implementing these methods in controller since the time is being stored
    // there in provider's code unlike for us since we stored time in the view
  }

  @Override
  public void addFeatures(Features feat) { // our features
    this.view.addFeatures(/* Features */ this.pController);// their features
  }

  @Override
  public void loop() {
    // these were the methods for the features methods in our implementation but we are
    // directly implementing these methods in controller since the time is being stored
    // there in provider's code unlike for us since we stored time in the view
  }

  @Override
  public void outlineShapesView() {
    throw new UnsupportedOperationException("operation currently unsupported in this view");
  }

  @Override
  public void render() throws IOException {
    this.view.render(System.out, this.speed);
  }

  @Override
  public void discreteFrameView() {
    throw new UnsupportedOperationException("operation not supported");
  }
}

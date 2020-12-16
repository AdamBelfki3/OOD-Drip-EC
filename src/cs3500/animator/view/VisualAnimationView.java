package cs3500.animator.view;

import cs3500.animator.controller.Features;
import cs3500.animator.model.IViewModel;
import java.io.IOException;
import java.util.Objects;


/**
 * this view draws and play the animation inside a window, effectively reproducing the sample
 * animations shown in the previous assignments. This view is implemented using java swing
 * and extends JComponent which allows us to use the jFrame and jPanel in our view.
 * Additionally, In order to produce an animation, we used a timer to tell the window to keep
 * refreshing itself automatically. The fields are view model, speed in ticks per second and
 * information about the canvas. We pass in a view model so that we can't directly mutate the
 * model using this class, and we use the canvas features to build the canvas accordingly.
 */
public class VisualAnimationView implements IVisualAnimationView {
  private IViewModel viewModel;
  private VisualPanel panel;
  private VisualFrame frame;


  /**
   * Renders the visual view accordingly using the model and the given speed. Displays
   * the animation in a frame.
   * @param viewModel the model that we would like to draw and render
   * @param speed the speed at which we want the visual view to render
   */
  public VisualAnimationView(IViewModel viewModel, int speed) {
    this.viewModel = Objects.requireNonNull(viewModel);
    this.panel = new VisualPanel(speed, viewModel);
    this.frame = new VisualFrame(this.panel, this.viewModel.getWidthCanvas(),
        this.viewModel.getHeightCanvas());
  }

  /**
   * Renders the visual view accordingly using the model and the given speed. Displays
   * the animation in a frame.
   * @param panel the panel that will display and play the animation
   * @param frame the frame to be displayed and manage the entire animation
   * @param viewModel the model that we would like to draw and render
   */
  public VisualAnimationView(VisualPanel panel, VisualFrame frame, IViewModel viewModel) {
    this.panel = Objects.requireNonNull(panel);
    this.frame = Objects.requireNonNull(frame);
    this.viewModel = Objects.requireNonNull(viewModel);
  }

  @Override
  public void render() throws IOException {
    this.frame.createFrame();
    this.frame.makeVisible();
    try {
      this.panel.startTimerRender();
    }
    catch (UnsupportedOperationException e) {
      // catch if there are any unsupported exceptions
    }
  }

  @Override
  public void startAnimation() {
    throw new UnsupportedOperationException("Operation not available for this view, "
        + "come again later :)");
  }

  @Override
  public void resumeAnimation() {
    throw new UnsupportedOperationException("Operation not available for this view, "
        + "come again later :)");
  }

  @Override
  public void pauseAnimation() {
    throw new UnsupportedOperationException("Operation not available for this view, "
        + "come again later :)");
  }

  @Override
  public void restartAnimation() {
    throw new UnsupportedOperationException("Operation not available for this view, "
        + "come again later :)");
  }

  @Override
  public void fastForward(int speedUp) {
    throw new UnsupportedOperationException("Operation not available for this view, "
        + "come again later :)");
  }

  @Override
  public void fastBackward(int slowDown) {
    throw new UnsupportedOperationException("Operation not available for this view, "
      + "come again later :)");
  }

  @Override
  public void addFeatures(Features feat) {
    throw new UnsupportedOperationException("Operation not available for this view, "
        + "come again later :)");
  }

  @Override
  public void loop() {
    throw new UnsupportedOperationException("Operation not available for this view, "
        + "come again later :)");
  }

  @Override
  public void outlineShapesView() {
    throw new UnsupportedOperationException("Operation not available for this view, "
        + "come again later :)");
  }

  @Override
  public void discreteFrameView() {
    throw new UnsupportedOperationException("operation not supported for this view");
  }
}
package cs3500.animator.view;

import cs3500.animator.model.IViewModel;

import java.util.Objects;

/**
 * This panel contains all the features of the visual panel except that now it has some more
 * interactive features such as the ability to start, rewind, stop, fast forward etc. This
 * allows us to control the panel accordingly and give the buttons the ability to control
 * the animation using these methods.
 */
public class InteractivePanel extends VisualPanel implements IInteractivePanel {

  /**
   * Since the panel extends the visual panel, the visual panel fields are stored as a super.
   * Takes in the speed at which we want the panel to render at and the view model for which
   * we want to run the panel for.
   * @param speed at which we want the panel to render at
   * @param viewModel the model for which we want it to be interactive for
   */
  public InteractivePanel(int speed, IViewModel viewModel) {
    super(speed, Objects.requireNonNull(viewModel));
  }

  @Override
  public void startTimerRender() {
    throw new UnsupportedOperationException();
  }

  @Override
  public void startTimer() {
    if (!this.timer.isRunning()) {
      this.timer.start();
    }
  }

  @Override
  public void rewind() {
    this.currentTick = 1;
    timer.restart();
  }

  @Override
  public void stopPanel() {
    this.timer.stop();
  }

  @Override
  public void fastForward(int speedUp) {
//    System.out.println(String.format("speed: %d", this.speed));
    this.speed = this.speed * 2;
    this.updateTimer();
    //this.timer.restart();
  }

  @Override
  public void fastBackward(int slowDown) {
    //System.out.println(String.format("speed: %d", this.speed));
    this.speed = this.speed / 2;
    this.updateTimer();
    //this.timer.restart();
  }

  @Override
  public void looping() {
    this.looping = !this.looping;
  }

  @Override
  public void outlineShapesPanel() {
    throw new UnsupportedOperationException("Operation currently unsupported for this panel");
  }


  @Override
  public void discreteFramePanel() {
    throw new UnsupportedOperationException("Operation currently unsupported for this panel");
  }


}

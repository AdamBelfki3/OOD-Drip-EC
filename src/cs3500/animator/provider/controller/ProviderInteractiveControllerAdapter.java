package cs3500.animator.provider.controller;

import cs3500.animator.controller.AnimationController;
import cs3500.animator.provider.view.Features;
import cs3500.animator.provider.view.IAnimationView;
import cs3500.animator.provider.view.interactive.AnimationInteractiveView;
import cs3500.animator.provider.view.interactive.InteractiveViewProviderViewAdapter;
import cs3500.animator.view.IVisualAnimationView;

import java.awt.event.ActionEvent;
import javax.swing.Timer;

/**
 * In order for their controller to work with our code, we created an adaptor that implements
 * their controller methods but these methods are implemented by using our methods by having
 * the delegate field. In other words, this adaptor looks like the provider's code but it's
 * actually our code. The provider's implementation stored ticks in the controller so we
 * had to store those in fields, the timer to implement each of the features methods and
 * passing in the provider's view to get their code to work with our code.
 *
 */
public class ProviderInteractiveControllerAdapter implements IAnimationController, Features {
  private AnimationInteractiveView view;
  private Timer timer;
  private int currentTick;
  private int endTime;
  private boolean looping;
  private int speed;

  /**
   * constructor that takes in the provider's view and provides an end time which represents the
   * length of the animation.
   * @param view the provider's view that we want to work with our code
   * @param endTime the animation length because that is how the provider's code is implemented
   */
  public ProviderInteractiveControllerAdapter(AnimationInteractiveView view, int endTime) {
    this.view = view;
    this.currentTick = 1;
    this.endTime = endTime;
    this.looping = false;
  }

  @Override
  public void start(int speed, String output) {
    IVisualAnimationView adapterView =
        new InteractiveViewProviderViewAdapter(/*Their view*/ this.view, speed, this);
    AnimationController controller = new AnimationController(adapterView);
    this.timer = new Timer((int) ((double)1000 / (double) speed),
        (ActionEvent action) ->  this.timerStart());
    controller.execute();
    this.speed = speed;
  }

  private void timerStart() {
    if (this.currentTick >= 0 && this.currentTick < this.endTime) {
      this.view.updateShapesAtTick(this.currentTick);
      this.currentTick ++;
    } else {
      if (this.looping) {
        this.currentTick = 1;
      }
      else {
        System.exit(0);
      }
    }
  }

  @Override
  public IAnimationView getView() {
    return this.view;
  }

  @Override
  public void play() {
    timer.start();
  }

  @Override
  public void pause() {
    timer.stop();
  }

  @Override
  public void resume() {
    if (!this.timer.isRunning()) {
      this.timer.start();
    }
  }

  @Override
  public void restart() {
    currentTick = 1;
    timer.restart();
  }

  @Override
  public void loop() {
    this.looping = !this.looping;
  }

  @Override
  public void speedUp() {
    this.speed = this.speed * 2;
    this.view.updateSpeed(this.speed);
    this.timer.setDelay((int) ((double)1000 / (double) this.speed));
  }

  @Override
  public void slowDown() {
    this.speed = this.speed / 2;
    this.view.updateSpeed(this.speed);
    this.timer.setDelay((int) ((double)1000 / (double) this.speed));
  }
}

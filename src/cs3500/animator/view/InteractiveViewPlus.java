package cs3500.animator.view;

import cs3500.animator.controller.Features;
import cs3500.animator.model.IViewModel;
import java.awt.event.KeyEvent;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;


/**
 *
 */
public class InteractiveViewPlus extends InteractiveVisualAnimationView implements IVisualAnimationView {

  /**
   *
   * @param viewModel
   * @param speed
   */
  public InteractiveViewPlus(IViewModel viewModel, int speed) {
    super(Objects.requireNonNull(viewModel), speed);
    this.panel = new InteractivePanelPlus(viewModel, speed);
    this.frame = new InteractiveFramePlus((InteractivePanelPlus) this.panel, viewModel.getWidthCanvas(),
        viewModel.getHeightCanvas());
    this.delegate = new VisualAnimationView(this.panel, this.frame, viewModel);
  }

  public void outlineShapesView() {
    this.panel.outlineShapesPanel();
  }

  public void discreteFrameView() {
    this.panel.discreteFramePanel();
  }

  /**
   *
   * @param feat
   */
  @Override
  protected void configureButtonListener(Features feat) {
    if (feat == null) {
      throw new IllegalArgumentException("features object cannot be null");
    }

    Map<String,Runnable> buttonClickedMap = new HashMap<String,Runnable>();
    ButtonListener buttonListener = new ButtonListener();

    buttonClickedMap.put("restart", () -> feat.restartAnimation());
    buttonClickedMap.put("pause", () -> feat.pauseAnimation());
    buttonClickedMap.put("start", () -> feat.beginAnimation());
    buttonClickedMap.put("resume", () -> feat.resumeAnimation());
    buttonClickedMap.put("fast forward", () -> feat.fastForward(2));
    buttonClickedMap.put("fast backward", () -> feat.fastBackward(2));
    buttonClickedMap.put("loop", () -> feat.loopAnimation());
    // outline shape feature
    buttonClickedMap.put("outline", () -> feat.outlineShapes());

    // discrete to continuous button feature
    buttonClickedMap.put("discrete", () -> feat.discreteFrame());

    buttonListener.setButtonClickedActionMap(buttonClickedMap);
    this.frame.addActions(buttonListener);
  }

  /**
   *
   * @param feat
   */
  @Override
  protected void configureKeyBoardListener(Features feat) {
    if (feat == null) {
      throw new IllegalArgumentException("features object cannot be null");
    }

    Map<Integer, Runnable> keyPresses = new HashMap<>();

    // key features
    // start
    keyPresses.put(KeyEvent.VK_S, () -> feat.beginAnimation());
    // pause
    keyPresses.put(KeyEvent.VK_P, () -> feat.pauseAnimation());
    // resume
    keyPresses.put(KeyEvent.VK_R, () -> feat.resumeAnimation());
    // restart
    keyPresses.put(KeyEvent.VK_E, () -> feat.restartAnimation());
    //quit
    keyPresses.put(KeyEvent.VK_Q, () -> System.exit(0));
    // arrow left
    keyPresses.put(KeyEvent.VK_N, () -> feat.fastBackward(2));

    // arrow right
    keyPresses.put(KeyEvent.VK_M, () -> feat.fastForward(2));

    // arrow right
    keyPresses.put(KeyEvent.VK_L, () -> feat.loopAnimation());

    // press o for outline
    keyPresses.put(KeyEvent.VK_O, () -> feat.outlineShapes());

    // press d for discrete
    keyPresses.put(KeyEvent.VK_D, () -> feat.discreteFrame());

    KeyListeners kbd = new KeyListeners();
    kbd.setKeyPressedMap(keyPresses);

    this.panel.addKeyListener(kbd);

    // keys won't work without this
    this.panel.setFocusable(true);
  }

}

import cs3500.animator.controller.AnimationController;
import cs3500.animator.controller.Features;
import cs3500.animator.view.IVisualAnimationView;
import cs3500.animator.view.InteractiveVisualAnimationView;
import cs3500.animator.view.MockView;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Tests all the listener methods that is being called on by the controller by using a mock view.
 */
public class ListenerInteractiveTest {

  // log where output is stored
  StringBuilder log = new StringBuilder();

  // mock view
  IVisualAnimationView mockView = new MockView(log);

  // passing in the mock view in controller to test the different features
  Features controller = new AnimationController((InteractiveVisualAnimationView) mockView);


  @Test
  public void testBegin() {
    controller.beginAnimation();
    assertTrue(log.toString().contains("start animation\n"));
  }

  @Test
  public void testResume() {
    controller.resumeAnimation();
    assertTrue(log.toString().contains("resume animation\n"));
  }

  @Test
  public void testRestart() {
    controller.restartAnimation();
    assertTrue(log.toString().contains("restart animation\n"));
  }

  @Test
  public void testPause() {
    controller.pauseAnimation();
    assertTrue(log.toString().contains("pause animation\n"));
  }

  @Test
  public void testFastForward() {
    controller.fastForward(2);
    assertTrue(log.toString().contains("fast forward animation\n"));
  }

  @Test
  public void testFastBackward() {
    controller.fastBackward(2);
    assertTrue(log.toString().contains("fast backward animation\n"));
  }

  @Test
  public void testLoop() {
    controller.loopAnimation();
    assertTrue(log.toString().contains("loop animation\n"));
  }

  // combination of features
  @Test
  public void combinationOfFeatures() {
    controller.beginAnimation();
    controller.loopAnimation();
    controller.pauseAnimation();
    assertEquals("start animation\nloop animation\npause animation\n", log.toString());
  }

  // all features
  @Test
  public void allFeatures() {
    controller.beginAnimation();
    controller.loopAnimation();
    controller.pauseAnimation();
    controller.fastForward(2);
    controller.fastBackward(2);
    controller.resumeAnimation();
    controller.restartAnimation();
    assertEquals("start animation\nloop animation\npause animation\n" +
        "fast forward animation\nfast backward animation\nresume animation\n" +
        "restart animation\n", log.toString());
  }

  // same features multiple times
  @Test
  public void sameFeatures() {
    controller.beginAnimation();
    controller.pauseAnimation();
    controller.resumeAnimation();

    controller.pauseAnimation();
    controller.resumeAnimation();
    controller.restartAnimation();
    controller.loopAnimation();

    assertEquals("start animation\npause animation\nresume animation\n" +
        "pause animation\nresume animation\nrestart animation\n" +
        "loop animation\n", log.toString());
  }

  // no features
  @Test
  public void noFeatures() {
    assertEquals("", log.toString());
  }



}
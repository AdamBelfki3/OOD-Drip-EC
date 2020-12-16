package cs3500.animator.controller;

import cs3500.animator.view.InteractiveViewPlus;

/**
 *
 */
public class AnimationControllerInteractivePlus extends AnimationController implements IController, Features {

  /**
   *
   */
  public AnimationControllerInteractivePlus(InteractiveViewPlus view) {
    super(view);
  }

  @Override
  public void outlineShapes() {
    this.view.outlineShapesView();
  }

  @Override
  public void discreteFrame() {
    this.view.discreteFrameView();
  }
}

package cs3500.animator.controller;

import cs3500.animator.view.svg.ISVGAnimationView;

import java.io.IOException;
import java.util.Objects;

/**
 * Takes in the ISVG animation interface which contains the classes that implement this interface
 * for SVG. Also contains the execute method which renders the view. If we want this view to
 * be interactive, we could make this controller implement the features interface and
 * add the features in the execute method, like we did in the animation controller.
 */
public class SVGController implements IController {
  ISVGAnimationView view;

  /**
   * Takes in the interface for the animation view so that we are able to call the execute
   * method in the main method in order to render the view.
   * @param view the interface for the svg and we pass in all the classes that implement
   *             that svg interface
   */
  public SVGController(ISVGAnimationView view) {
    this.view = Objects.requireNonNull(view);
  }

  @Override
  public void execute() {
    try {
      view.render();
    } catch (IOException e) {
      e.getMessage();
    }
  }
}

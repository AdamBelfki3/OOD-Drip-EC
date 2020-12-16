package cs3500.animator.controller;

import cs3500.animator.view.textual.ITextualAnimationView;

import java.io.IOException;
import java.util.Objects;

/**
 * A controller that takes in the corresponding view and then executes it. By having a
 * special controller just for this view, we can easily add features to it by making
 * this interface implement the features. But for now, it only renders the view
 * which is called in the main method.
 */
public class TextualController implements IController {
  ITextualAnimationView view;

  /**
   * Takes in the interface of ITextual animation view and then performs the execute method on
   * it which renders the view.
   * @param view takes in the view that implements the ITextualAnimation interface
   */
  public TextualController(ITextualAnimationView view) {
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

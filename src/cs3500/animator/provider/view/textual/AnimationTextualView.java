package cs3500.animator.provider.view.textual;

import cs3500.animator.provider.view.Features;
import cs3500.animator.provider.model.hw05.IAnimationModel;
import cs3500.animator.provider.view.IAnimationView;

import java.io.IOException;

/**
 * Represents the textual view of the animation.
 * Receives a model and translates model's shapes and motions into textual description.
 */
public class AnimationTextualView implements IAnimationView {
  private final IAnimationModel model;
  private Appendable ap = new StringBuilder();

  public AnimationTextualView(IAnimationModel model) {
    this.model = model;
  }

  /**
   * Constructs the view using {@code AnimationModel}.
   * @param model  the AnimationModel used for the view
   * @param output  the appendable output
   */
  public AnimationTextualView(IAnimationModel model, Appendable output) {
    this.model = model;
    this.ap = output;
  }

  private String getText() {
    if (model == null) {
      throw new IllegalStateException();
    }
    String result = String.format("canvas %d %d %d %d\n",
            model.getBoundingX(), model.getBoundingY(),
            model.getBoundingWidth() - model.getBoundingX(),
            model.getBoundingHeight() - model.getBoundingY());
    return result + model.toString();
  }

  @Override
  public void render(Appendable out, int speed) throws IOException {
    String text = this.getText();
    out.append(text);
    this.ap.append(text);
  }

  @Override
  public void updateSpeed(int speed) {
    throw new UnsupportedOperationException("TextualView does not support update speed");
  }

  @Override
  public void addFeatures(Features features) {
    // no featues to add
  }

  @Override
  public void updateShapesAtTick(int tick) {
    throw new UnsupportedOperationException("TextualView does not support update shapes");
  }

  @Override
  public String toString() {
    return this.ap.toString();
  }
}

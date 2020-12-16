package cs3500.animator.model;

import cs3500.animator.model.shape.IShape2D;
import cs3500.animator.model.shape.Position2D;
import cs3500.animator.model.shape.Rgb;
import cs3500.animator.model.shape.Size2D;

import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * This class is acting as an "adapter" which essentially allows us to make sure that the
 * model can not be directly mutated through the view. In other words, this class only
 * implements the methods that are needed in the view and throws unsupported operation exception
 * in the places where it's possible to mutate the model. This ensures that the view
 * can not mutate the model in any way possible and fixes that pitfall.
 */
public class ViewModel extends SimpleAnimation implements IViewModel {

  private final AnimationModel model;

  /**
   * This model contains all the methods needed to implement the animation accordingly and then it
   * will be passed into the view model. The view model then can't mutate anything further since we
   * throw unsupported exception if that happens.
   *
   * @param model takes in our model that is used to implement the animation
   */
  public ViewModel(AnimationModel model) {
    this.model = Objects.requireNonNull(model);
  }


  @Override
  public Map<String, IShapeAndMotion> getDrawings() {
    return null;
  }

  @Override
  public void startAnimation() {
    throw new UnsupportedOperationException();
  }

  @Override
  public void createShape(String obj, String name) throws IllegalArgumentException {
    throw new UnsupportedOperationException();
  }

  @Override
  public void addOperation(String name, int start, int end) throws IllegalArgumentException {
    throw new UnsupportedOperationException();
  }

  @Override
  public void addOperation(String name, int start, int end, int x, int y, int w,
      int h, int r, int g, int b) throws IllegalArgumentException {
    throw new UnsupportedOperationException();
  }

  @Override
  public void addOperation(String name, int t1, int x1, int y1, int w1, int h1,
      int r1, int g1, int b1, int t2, int x2, int y2, int w2,
      int h2, int r2, int g2, int b2) {
    throw new UnsupportedOperationException();
  }

  @Override
  public boolean isAnimationOver() throws IllegalStateException {
    return false;
  }

  @Override
  public Map<String, IShapeAndMotion> getInstructions() {
    return this.model.getInstructions();
  }

  @Override
  public IShapeAndMotion getImageAt(String name) {
    return this.model.getImageAt(name);
  }

  @Override
  public void resetAnimation() {
    throw new UnsupportedOperationException();
  }

  @Override
  public IShape2D getFrame(String name, int time) throws IllegalStateException {
    return this.model.getFrame(name, time);
  }

  @Override
  public void updateShape(String name, Position2D pos, Size2D size, Rgb color) {
    this.model.updateShape(name, pos, size, color);
  }

  @Override
  public void retrieve() {
    throw new UnsupportedOperationException();
  }

  @Override
  public boolean animationStarted() {
    return this.model.animationStarted();
  }

  @Override
  public void setBounds(int x, int y, int width, int height) {
    throw new UnsupportedOperationException();
  }

  @Override
  public int getXCanvas() {
    return this.model.getXCanvas();
  }

  @Override
  public int getYCanvas() {
    return this.model.getYCanvas();
  }

  @Override
  public int getWidthCanvas() {
    return this.model.getWidthCanvas();
  }

  @Override
  public int getHeightCanvas() {
    return this.model.getHeightCanvas();
  }

  @Override
  public int getEndTime() {
    return this.model.getEndTime();
  }

  @Override
  public int isSlowMoTick(int tick) {
    return this.model.isSlowMoTick(tick);
  }

  @Override
  public Integer[] frameTicks() {
    return this.model.frameTicks();
  }
}

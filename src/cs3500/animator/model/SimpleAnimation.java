package cs3500.animator.model;

import cs3500.animator.util.AnimationBuilder;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import cs3500.animator.model.shape.Ellipse;
import cs3500.animator.model.shape.IShape2D;
import cs3500.animator.model.shape.Position2D;
import cs3500.animator.model.shape.Rectangle;
import cs3500.animator.model.shape.Rgb;
import cs3500.animator.model.shape.Size2D;
import java.util.Stack;

/**
 * Represents the animation class that contains the methods required to build the animation.
 * The drawings field allows us to connect a shape and motion to the string that is currently
 * supported by our implementation to the string that is inputted.
 * The current tick field stores the tick at which the animation is at,
 * the maxtFinal is the final tick for the
 * animation, and stack motions stores all the drawings about the inputted shape
 * which are first validated in start animation.
 */
public class SimpleAnimation implements AnimationModel {
  protected Map<String, IShapeAndMotion> drawings;
  protected int maxTFinal;
  protected boolean isStarted;
  protected Stack<Map<String, IShapeAndMotion>> stackMotions;

  // canvas window
  protected int corner_x;
  protected int corner_y;
  protected int canvasWidth;
  protected int canvasHeight;


  /**
   * Animation constructor that initializes all the fields to default.
   */
  public SimpleAnimation() {
    this.drawings = new LinkedHashMap<>();
    this.maxTFinal = 0;
    this.isStarted = false;
    this.stackMotions = new Stack<>();
  }

  // invariants
  // INVARIANT: currentTick > 0
  // INVARIANT: maxTFinal > 0
  // INVARIANT: motions are in chronological order
  // INVARIANT: motions don't have gaps
  // INVARIANT: motions don't have overlaps
  // INVARIANT: shape feature is modified based on current tick depending on motion


  @Override
  public Map<String, IShapeAndMotion> getDrawings() {
    return drawings;
  }


  @Override
  public int getEndTime() {
    return maxTFinal;
  }

  @Override
  public void startAnimation() {
    // chronologically sort the motions of each ShapeAndMotion

    // check within each ShapeAndMotion if the motions are coherent
    // coherent means that no gaps or overlaps are produced by the motions
    for (String name : drawings.keySet()) {
      IShapeAndMotion sm = drawings.get(name);
      sm.sortMotionsOnStartTime();
      // if any incoherence is spotted then we set the drawing as the peek of the stack
      // and throw in an exception
      if (!sm.checkMotionCoherence()) {
        // if stack is empty then simply resets the animation
        if (stackMotions.isEmpty()) {
          resetAnimation();
        }
        else {
          drawings = stackMotions.peek();
        }
        throw new IllegalStateException("There is coherence so we can't push "
                + "all of the new inputs.");
      }
    }

    // complete do nothing motions with their ending motion stats
    for (String name : this.drawings.keySet()) {
      IShapeAndMotion sm = this.drawings.get(name);
      sm.updateDoNothingMotion();
    }

    // complete motions with their starting motions stars
    for (String name : this.drawings.keySet()) {
      IShapeAndMotion sm = this.drawings.get(name);
      sm.linkMotions();
    }

    // push the changes into the stack
    Map<String, IShapeAndMotion> map = new LinkedHashMap<>();
    for (String name : drawings.keySet()) {
      map.put(name, drawings.get(name).copyShapeAndMotion());
    }

    stackMotions.push(map);


    // set maxTFinal to the highest ending time
    int tempMax = 0;
    for (String name : this.drawings.keySet()) {
      if (this.drawings.get(name).getEndTime() > tempMax) {
        tempMax = this.drawings.get(name).getEndTime();
      }
    }
    this.maxTFinal = tempMax;

    // game can be started
    this.isStarted = true;
  }


  @Override
  public void createShape(String shape, String name) {
    IShapeAndMotion sm;

    switch (shape) {
      case "rectangle":
        sm = new ShapeAndMotion(new Rectangle(new Position2D(0, 0),
                new Size2D(10, 10), new Rgb(255, 0, 0)));
        break;
      case "ellipse":
        sm = new ShapeAndMotion(new Ellipse(new Position2D(0, 0),
                new Size2D(10, 10),
                new Rgb(255, 0, 0)));
        break;
      default:
        throw new IllegalArgumentException("shape not currently supported");
    }

    // add the new shape with an empty list of motions to the drawings map
    this.drawings.put(name, sm);
  }

  @Override
  public void addOperation(String name, int start, int end) throws IllegalArgumentException {
    IMotion newMotion;

    // checking if the motions values do not throw an exception
    try {
      newMotion = new Motion(name, start, end);
    } catch (IllegalArgumentException e) {
      throw new IllegalArgumentException(e.getMessage());
    }

    // checking if the targeted shape already exists in the map of motions
    if (!this.drawings.keySet().contains(name)) {
      throw new IllegalArgumentException("shape not created yet");
    }
    else {
      // adding the motion to its corresponding shape
      IShapeAndMotion sm = this.drawings.get(name);
      sm.addMotion(newMotion);
    }
  }

  @Override
  public void addOperation(String name, int start, int end, int x, int y, int w, int h, int r,
                           int g, int b) throws IllegalArgumentException {

    IMotion newMotion;

    // checking if the motions values do not throw an exception
    try {
      newMotion = new Motion(name, start, end, new Position2D(x, y),
              new Size2D(w, h), new Rgb(r, g, b));
    } catch (IllegalArgumentException e) {
      throw new IllegalArgumentException(e.getMessage());
    }

    // checking if the targeted shape already exists in the map of motions
    if (!this.drawings.keySet().contains(name)) {
      throw new IllegalArgumentException("shape not created yet");
    }
    else {
      // adding the motion to its corresponding shape
      IShapeAndMotion sm = this.drawings.get(name);
      sm.addMotion(newMotion);
    }
  }

  @Override
  public void addOperation(String name, int t1, int x1, int y1, int w1, int h1, int r1, int g1,
                           int b1, int t2, int x2, int y2,
                           int w2, int h2, int r2, int g2, int b2) {

    IMotion newMotion;

    try {
      newMotion = new Motion(name, t1, new Position2D(x1, y1), new Size2D(w1, h1),
              new Rgb(r1, g1, b1), t2, new Position2D(x2, y2), new Size2D(w2, h2),
              new Rgb(r2, g2, b2));
    } catch (IllegalArgumentException e) {
      throw new IllegalArgumentException(e.getMessage());
    }

    // checking if the targeted shape already exists in the map of motions
    if (!this.drawings.keySet().contains(name)) {
      throw new IllegalArgumentException("shape not created yet");
    }
    else {
      // adding the motion to its corresponding shape
      IShapeAndMotion sm = this.drawings.get(name);
      sm.addMotion(newMotion);
    }
  }

  @Override
  public boolean isAnimationOver() throws IllegalArgumentException {
    // check if the animation has started
    this.checkAnimationStarted();

    return (!isStarted);
  }

  /**
   * Checks if the game has started.
   *
   * @throws IllegalStateException if the game has not started yet
   */
  protected void checkAnimationStarted() throws IllegalStateException {
    if (!this.isStarted) {
      throw new IllegalStateException("Animation has not started yet");
    }
  }

  @Override
  public Map<String, IShapeAndMotion> getInstructions() {
    Map<String, IShapeAndMotion> instructions = new LinkedHashMap<>();

    for (String name : this.drawings.keySet()) {
      IShapeAndMotion drawing = this.drawings.get(name);
      instructions.put(name, drawing.copyShapeAndMotion());
    }

    return instructions;
  }

  @Override
  public IShapeAndMotion getImageAt(String name) {
    IShapeAndMotion sm = this.drawings.get(name);
    return sm.copyShapeAndMotion();
  }

  @Override
  public void resetAnimation() {
    this.drawings = new LinkedHashMap<>();
    this.stackMotions = new Stack();
    this.maxTFinal = 0;
    this.isStarted = false;
  }

  @Override
  public IShape2D getFrame(String name, int time)
          throws IllegalStateException, IllegalArgumentException {
    // check if the animation has started
    this.checkAnimationStarted();

    if (!this.drawings.keySet().contains(name)) {
      throw new IllegalArgumentException("the shape looking for has never been created");
    }

    // copying the IShapeAndMotion object
    IShapeAndMotion sm = this.drawings.get(name).copyShapeAndMotion();

    // applying the motion of the given time to the chosen shape
    sm.applyMotion(time);

    // returns the shape state as the frame
    return sm.getShape();
  }

  @Override
  public void retrieve() {
    if (stackMotions.isEmpty()) {
      throw new IllegalStateException("No state to retrieve");
    }

    drawings = stackMotions.pop();
  }

  @Override
  public boolean equals(Object a) {
    if (this == a) {
      return true;
    }

    if (!(a instanceof SimpleAnimation)) {
      return false;
    }

    SimpleAnimation that = (SimpleAnimation) a;

    return this.getDrawings().equals(that.getDrawings())
            && this.maxTFinal == that.maxTFinal
            && this.isStarted == that.isStarted
            && this.stackMotions.equals(that.stackMotions);
  }

  @Override
  public int hashCode() {
    return Objects.hash(this.drawings, this.maxTFinal, isStarted, stackMotions);
  }

  @Override
  public boolean animationStarted() {
    return this.isStarted;
  }

  @Override
  public void setBounds(int x, int y, int width, int height) {
    if ((width <= 0) || (height <= 0)) {
      throw new IllegalArgumentException("width or height can't be 0 or negative");
    }

    this.corner_x = x;
    this.corner_y = y;
    this.canvasWidth = width;
    this.canvasHeight = height;
  }

  @Override
  public int getXCanvas() {
    return this.corner_x;
  }

  @Override
  public int getYCanvas() {
    return this.corner_y;
  }

  @Override
  public int getWidthCanvas() {
    return this.canvasWidth;
  }

  @Override
  public int getHeightCanvas() {
    return this.canvasHeight;
  }

  @Override
  public void updateShape(String name, Position2D pos, Size2D size, Rgb color) {
    this.checkAnimationStarted();

    if (!this.drawings.keySet().contains(name)) {
      throw new IllegalArgumentException("Shape does not exist");
    }

    this.drawings.get(name).updateShape(pos, size, color);
  }

  @Override
  public void addTempo(int t1, int t2) {
    throw new UnsupportedOperationException("operation currently unsupported in this model");
  }

  public Integer[] frameTicks() {
    throw new UnsupportedOperationException("operation currently unsupported in this model");
  }

  @Override
  public int isSlowMoTick(int tick) {
    throw new UnsupportedOperationException("not available");
  }

  /**
   * Builder class that has the model as its field and implements the four methods
   * using the models methods so that we are able to build and construct the model accordingly.
   * It
   */
  public static final class Builder implements AnimationBuilder<AnimationModel> {
    AnimationModel model;

    /**
     * The builder constructor has the model but does not take in anything. The model is used
     * to implement the methods in this class.
     */
    public Builder(String animationType) {
      switch (animationType) {
        case "Simple":
          this.model = new SimpleAnimation();
          break;
        case "Plus":
          this.model = new SimpleAnimationPlus();
          break;
        default:
          throw new IllegalArgumentException("Animation type desired nor supported by this builder");
      }
    }

    @Override
    public AnimationModel build() {
      return this.model;
    }

    @Override
    public AnimationBuilder<AnimationModel> setBounds(int x, int y, int width, int height) {
      this.model.setBounds(x, y, width, height);
      return this;
    }

    @Override
    public AnimationBuilder<AnimationModel> declareShape(String name, String type) {
      model.createShape(type, name);
      return this;
    }

    @Override
    public AnimationBuilder<AnimationModel> addMotion(String name, int t1, int x1, int y1, int w1,
              int h1, int r1, int g1, int b1, int t2, int x2, int y2,
              int w2, int h2, int r2, int g2, int b2) {

      model.addOperation(name, t1, x1, y1, w1, h1, r1, g1, b1, t2, x2, y2, w2, h2, r2, g2, b2);

      return this;
    }

    @Override
    public AnimationBuilder<AnimationModel> addTempoInterval(int t1, int t2) {
      this.model.addTempo(t1, t2);

      return this;
    }
  }
}

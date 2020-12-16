package cs3500.animator.provider.model.hw05;

import cs3500.animator.model.AnimationModel;
import cs3500.animator.model.IMotion;
import cs3500.animator.model.IShapeAndMotion;
import cs3500.animator.model.SimpleAnimation;
import cs3500.animator.model.ViewModel;
import cs3500.animator.model.shape.IShape2D;
import cs3500.animator.model.shape.Position2D;
import cs3500.animator.model.shape.Rgb;
import cs3500.animator.model.shape.Size2D;
import cs3500.animator.provider.model.hw05.shape.DTOAbstractShape;
import cs3500.animator.provider.model.hw05.shape.DTOEllipseShape;
import cs3500.animator.provider.model.hw05.shape.DTORectangleShape;
import cs3500.animator.provider.util.AnimationBuilder;
import cs3500.animator.view.IAnimationView;
import cs3500.animator.view.IntTextualAnimationView;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 * In order to make the provider's code work with our code, we had to create an adaptor
 * that implements their interface but all of their methods are implemented using our
 * model which is why we pass in our model interface as a delegate. Each of the methods in this
 * adaptor are simply implemented using our model methods. In other words, we have created a class
 * that looks like our provider's but is actually our code. This way, we were able to make their
 * views work with our code.
 */
public class ProviderModelAdapter implements IAnimationModel {
  AnimationModel model;


  /**
   * While using this constructor, we just have to pass in our model and then make sure
   * that the model is not null.
   * @param model our model is passed in to constructor so that we can use our implementation
   *              to implement their model methods
   */
  public ProviderModelAdapter(AnimationModel model) {
    this.model = Objects.requireNonNull(model);
  }

  @Override
  public void setStartShape(String name, int x, int y, int w, int h, int r, int g, int b) {
    model.updateShape(name, new Position2D(x,y), new Size2D(w,h), new Rgb(r,g,b));
  }

  @Override
  public void addShape(DTOAbstractShape shape, String name) {
    this.model.createShape(shape.getShapeType(), name);
  }

  @Override
  public void addAnimationMoveToShape(String shapeName, IAnimationMove move) {
    model.addOperation(shapeName, move.getStartTick(), (int)move.getStartPos().getX(),
        (int)move.getStartPos().getY(), move.getStartSize().getWidth(),
        move.getStartSize().getHeight(),
        move.getStartColor().getR(), move.getStartColor().getG(), move.getStartColor().getB(),
        move.getEndTick(), (int)move.getEndPos().getX(),
        (int)move.getEndPos().getY(), move.getEndSize().getWidth(), move.getEndSize().getHeight(),
        move.getEndColor().getR(), move.getEndColor().getG(), move.getEndColor().getB());
  }

  @Override
  public int getAnimationLength() {
    /*
    Not sure about this.
    > For now just making it return the maximum end time of the animation

    > To make it return the length, we will have to implement a getEarliestStartTime
    method in our model implementation
     */
    return this.model.getEndTime();
  }

  @Override
  public List<DTOAbstractShape> getShapesAtTick(int tick) {
    // first the names of all the shapes
    Set<String> allNames = this.model.getInstructions().keySet();

    //Map<String, IShapeAndMotion> sm = this.model.getInstructions();

    // then make an empty list of shapes
    List<DTOAbstractShape> shapesAtGivenTick = new ArrayList<>();

    // then add every shape at the specified time to the list
    for (String name : allNames) {
      // getting the shape from our model
      IShape2D s = null;

      try {
        s = this.model.getFrame(name, tick);

      }
      catch (IllegalArgumentException e) {
        // catch illegal argument if there are any
      }

      if (s != null) {
        DTOAbstractShape shape = null;

        if (s.shapeName().equals("rectangle")) {
          shape = new DTORectangleShape(name, s.getPos().getX(), s.getPos().getY(),
              s.getSize().getWidth(), s.getSize().getHeight(), s.getRgb().getR(),
              s.getRgb().getG(), s.getRgb().getB());
        } else {
          shape = new DTOEllipseShape(name, s.getPos().getX(), s.getPos().getY(),
              s.getSize().getWidth(), s.getSize().getHeight(), s.getRgb().getR(),
              s.getRgb().getG(), s.getRgb().getB());
        }

        // adding shape to the list
        shapesAtGivenTick.add(shape);
      }
    }

    // finally return the list
    return shapesAtGivenTick;
  }

  @Override
  public DTOShapeAnimation getShape(String name) {
    Map<String, IShapeAndMotion> allSM = this.model.getInstructions();
    IShapeAndMotion sm = allSM.get(name);

    IShape2D s = sm.getShape();

    DTOAbstractShape shape = null;

    if (sm.getShape().shapeName().equals("rectangle")) {
      shape = new DTORectangleShape(name, s.getPos().getX(), s.getPos().getY(),
          s.getSize().getWidth(), s.getSize().getHeight(), s.getRgb().getR(),
          s.getRgb().getG(), s.getRgb().getB());
    }
    else {
      shape = new DTOEllipseShape(name, s.getPos().getX(), s.getPos().getY(),
          s.getSize().getWidth(), s.getSize().getHeight(), s.getRgb().getR(),
          s.getRgb().getG(), s.getRgb().getB());
    }

    List<IAnimationMove> moves = new ArrayList<>();

    for (IMotion motion : sm.getMotions()) {
      // make new IAnimationMove
      IAnimationMove move = new ProviderMotionAdapter(name, motion);

      moves.add(move);
    }

    DTOShapeAnimation terribleResult = new DTOShapeAnimation(shape, moves);

    return terribleResult;
  }

  @Override
  public int getBoundingWidth() {
    return model.getWidthCanvas();
  }

  @Override
  public int getBoundingHeight() {
    return model.getHeightCanvas();
  }

  @Override
  public int getBoundingX() {
    return this.model.getXCanvas();
  }

  @Override
  public int getBoundingY() {
    return model.getYCanvas();
  }

  @Override
  public void setBounds(int x, int y, int width, int height) throws IllegalArgumentException {
    this.model.setBounds(x, y, width, height);
  }

  @Override
  public Map<String, DTOShapeAnimation> getAllShapeAnimations() {
    Map<String, DTOShapeAnimation> allShapeAnimations = new LinkedHashMap<>();

    Set<String> names = this.model.getInstructions().keySet();

    for (String name : names) {
      DTOShapeAnimation sa = this.getShape(name);
      allShapeAnimations.put(name, sa);
    }

    return allShapeAnimations;
  }

  @Override
  public String toString() {
    this.model.startAnimation();
    ViewModel viewModel = new ViewModel(this.model);
    StringBuilder builder = new StringBuilder();
    IAnimationView textualView = new IntTextualAnimationView(viewModel,builder, 1);
    try {
      textualView.render();
    }
    catch (IOException e) {
      // cry if you want
    }

    String result = builder.toString();

    return result.substring(result.indexOf('\n') + 1);
  }

  /**
   * Starts the animation and catches any illegal arguments if there are any.
   */
  public void startInnerModel() {
    try {
      this.model.startAnimation();
    }
    catch (IllegalArgumentException e) {
      // cry if you can
    }
  }

  /**
   * Builder class that has the model as its field and implements the four methods
   * using the models methods so that we are able to build and construct the model accordingly.
   * It
   */
  public static final class ProviderBuilder implements
      cs3500.animator.provider.util.AnimationBuilder<IAnimationModel> {
    IAnimationModel model = new ProviderModelAdapter(new SimpleAnimation());

    /**
     * The builder constructor has the model but does not take in anything. The model is used
     * to implement the methods in this class.
     */
    public ProviderBuilder() {
      //this.model = model;
    }

    @Override
    public IAnimationModel build() {
      return this.model;
    }

    @Override
    public cs3500.animator.provider.util.AnimationBuilder<IAnimationModel>
        setBounds(int x, int y, int width, int height) {
      this.model.setBounds(x, y, width, height);
      return this;
    }

    @Override
    public cs3500.animator.provider.util.AnimationBuilder<IAnimationModel>
        declareShape(String name, String type) {
      DTOAbstractShape shape = null;

      if (type.equals("rectangle")) {
        shape = new DTORectangleShape(name, 0, 0,
            10, 10, 255, 0, 0);
      }
      else {
        shape = new DTOEllipseShape(name, 0, 0,
            10, 10, 255, 0, 0);
      }

      this.model.addShape(shape, name);
      return this;
    }

    @Override
    public AnimationBuilder<IAnimationModel> addMotion(String name, int t1, int x1, int y1, int w1,
        int h1, int r1, int g1, int b1, int t2, int x2, int y2,
        int w2, int h2, int r2, int g2, int b2) {
      IAnimationMove move = new ProviderMotionAdapter(name, t1, (x1 - this.model.getBoundingX()),
          (y1 - this.model.getBoundingY()), w1,
          h1,  r1,  g1,  b1,  t2,  (x2 - this.model.getBoundingX()),
          (y2 - this.model.getBoundingY()), w2,  h2,  r2,  g2,  b2);

      model.addAnimationMoveToShape(name, move);

      return this;
    }
  }


}

package cs3500.animator.model;


import cs3500.animator.model.shape.Ellipse;
import cs3500.animator.model.shape.Plus;
import cs3500.animator.model.shape.Position2D;
import cs3500.animator.model.shape.Rectangle;
import cs3500.animator.model.shape.Rgb;
import cs3500.animator.model.shape.Size2D;
import cs3500.animator.provider.model.hw05.IAnimationModel;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 *
 */
public class SimpleAnimationPlus extends SimpleAnimation implements AnimationModel {
  List<Integer[]> temposIntervals;


  /**
   *
   */
  public SimpleAnimationPlus() {
    this.temposIntervals = new ArrayList<>();
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
        } else {
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

    if (this.temposIntervals.size() > 0) {

      // check if tempos are legit
      // do not overlap, and if none of them are outside of the running time of the animation
      this.temposIntervals = this.orderTemposList(this.temposIntervals);

      // check if the no intervals overlap
      boolean tempoOverlap = this.tempoOverlap();

      if (tempoOverlap) {
        throw new IllegalArgumentException("tempos interval cannot overlap");
      }

      // check if all intervals are within the running time of the animation
      if ((temposIntervals.get(temposIntervals.size() - 1)[1]) > this.maxTFinal) {
        throw new IllegalArgumentException("tempo outside of the running time of the animation");
      }

      if ((temposIntervals.get(0)[0]) < 0) {
        throw new IllegalArgumentException("tempo outside of the running time of the animation");
      }
    }

    // game can be started
    this.isStarted = true;


    Integer[] someList = this.frameTicks();

    System.out.println("We are printing the discrete ticks");
    for (int ii = 0; ii < someList.length; ii++) {
      System.out.println(String.format("Discrete tick %d -> %d", ii, someList[ii]));
    }
  }

  private boolean tempoOverlap() {
    boolean overlap = false;
    for (int ii = 0; ii < this.temposIntervals.size(); ii++) {
      if (ii != (this.temposIntervals.size() - 1)) {
        overlap = overlap || (this.temposIntervals.get(ii)[0] > this.temposIntervals.get(ii + 1)[0]);
      }
    }

    return overlap;
  }

  private static List<Integer[]> orderTemposList(List<Integer[]> tempos) {
    List<Integer[]> result = new ArrayList<>();

    int size = tempos.size();

    for(int ii = 0; ii < size; ii++) {
      /*for (Integer[] tempo : tempos) {
        System.out.println(String.format("start time: %d, end time %d", tempo[0], tempo[1]));
      }*/

      Integer smallestStartTimeIdx = 0;
      for (Integer[] tempo : tempos) {
        if (tempos.get(smallestStartTimeIdx)[0] > tempo[0]) {
          smallestStartTimeIdx = tempos.indexOf(tempo);
        }
      }
      Integer[] smallestTempo = tempos.get(smallestStartTimeIdx);
      tempos.remove(smallestTempo);
      result.add(smallestTempo);
    }

    return result;
  }


  @Override
  public void createShape(String shape, String name) {
    IShapeAndMotion sm;

    switch (shape) {
      case "rectangle":
        sm = new ShapeAndMotion(new Rectangle(new Position2D(-200, -200),
            new Size2D(10, 10), new Rgb(255, 0, 0)));
        break;
      case "ellipse":
        sm = new ShapeAndMotion(new Ellipse(new Position2D(-200, -200),
            new Size2D(10, 10),
            new Rgb(255, 0, 0)));
        break;
      case "plus":
        sm = new ShapeAndMotion(new Plus(new Position2D(-200, -200),
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
  public void addTempo(int t1, int t2) {
    if (t1 >= t2) {
      throw new IllegalArgumentException("illegal interval");
    }

    if ((t1 <= 0) || (t2 <= 0)) {
      throw new IllegalArgumentException("Negative times are not supported duhhhh");
    }

    Integer[] tempo = new Integer[2];
    tempo[0] = t1;
    tempo[1] = t2;

    this.temposIntervals.add(tempo);
  }


  @Override
  public int isSlowMoTick(int tick) {
    for (Integer[] tempo : this.temposIntervals) {
      if ((tempo[0] <= tick) && (tempo[1] >= tick)) {
        if (tick == tempo[0]) {
          return 0;
        }
        else {
          return 1;
        }
      }
    }
    return -1;
  }

  @Override
  public Integer [] frameTicks() {
    this.checkAnimationStarted();

    Set<Integer> set = new HashSet<Integer>();

    Map<String, IShapeAndMotion> allSM = this.getInstructions();

    for (String name : allSM.keySet()) {
      IShapeAndMotion sm = allSM.get(name);
      for (IMotion motion : sm.getMotions()) {
        set.add(motion.getStartTime());
        set.add(motion.getEndTime());
      }
    }

    int size = set.size();

    Integer[] result = new Integer[size];

    int idx = 0;

    for (Integer ii : set) {
      result[idx] = ii;
      idx += 1;
    }

    Arrays.sort(result);

    return result;
  }
}

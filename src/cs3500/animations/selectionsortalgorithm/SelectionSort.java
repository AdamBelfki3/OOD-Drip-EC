package cs3500.animations.selectionsortalgorithm;

import cs3500.animator.controller.IController;
import cs3500.animator.controller.TextualController;
import cs3500.animator.model.AnimationModel;
import cs3500.animator.model.SimpleAnimation;
import cs3500.animator.model.ViewModel;
import cs3500.animator.view.IntTextualAnimationView;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 * This class represents the selection sort algorithm as an animation. All of the bars are
 * displayed in blue and the current minimum bar will be displayed in orange and the ongoing search
 * of the bar will be displayed in yellow. As time progresses, you will be able to see how the
 * colors of the bars change depending on what is happening next. The user can specify the amount
 * of bars you want to sort and then firstly the bars are shuffled and then it will be sorted
 * using this algorithm.
 */
public class SelectionSort {

  /**
   * This is the entry point of the sort. You are able to put in a specific type of length and
   * a place where you want to output the animation. The length of the bar represents the amount of
   * bars in the array the user can specify this value and so we'll generate the specified amount of
   * values. The bars will be shuffled and then we will be sorting it using this algorithm.
   * @param args the inputs that are provided by the user. They can specify a valid output
   *             place and also the length of the array
   */
  public static void main(String[] args) {
    boolean outYes = false;
    boolean lengthYes = false;
    String outFile = "";
    int arrayLength = 0;

    for (int ii = 0; ii < 4; ii++) {
      switch (args[ii]) {
        case "-out":
          ii = ii + 1;
          if (!outYes) {
            outFile = args[ii];
            outYes = true;
          }
          else {
            JFrame frame = new JFrame();
            JOptionPane.showMessageDialog(frame,
                String.format("%s, is an Invalid Input Argument!", args[ii]),
                "Error Message", JOptionPane.ERROR_MESSAGE);
            System.exit(0);
          }
          break;
        case "-length":
          ii = ii + 1;
          if (!lengthYes) {
            arrayLength = Integer.parseInt(args[ii]);
            lengthYes = true;
          }
          else {
            JFrame frame = new JFrame();
            JOptionPane.showMessageDialog(frame,
                String.format("%s, is an Invalid Input Argument!", args[ii]),
                "Error Message", JOptionPane.ERROR_MESSAGE);
            System.exit(0);
          }
          break;
        default:
          JFrame frame = new JFrame();
          JOptionPane.showMessageDialog(frame,
              String.format("%s, is an Invalid Input Argument!", args[ii]),
              "Error Message", JOptionPane.ERROR_MESSAGE);
          System.exit(0);
      }
    }

    BufferedWriter output = null;
    try {
      output = new BufferedWriter(new FileWriter(outFile));
    }
    catch (IOException e) {
      JFrame frame = new JFrame();
      JOptionPane.showMessageDialog(frame,
          String.format("Error!"),
          "Error Message", JOptionPane.ERROR_MESSAGE);
      System.exit(0);
    }

    // creating array of random numbers with the specified length
    RandomizeBars rb = new RandomizeBars();
    int[] arr = rb.randomizeBars(arrayLength);

    // creating model
    AnimationModel model = new SimpleAnimation();

    // const: bars length
    int barsHeightFactor = 20;

    // initializing canvas
    int canvasWidth = 30 * arrayLength + 50 * 2 + 15 * 29;
    int canvasHeight = 50 * 2 + arrayLength * barsHeightFactor;
    model.setBounds(0, 0, canvasWidth, canvasHeight);

    // initializing shapes
    CreateBars cs = new CreateBars();
    cs.createBars(model, arr);

    // sorting bars
    SortBars sb = new SortBars();
    sb.sort(model, arr);

    // generating animation text
    try {
      model.startAnimation();
    }
    catch (IllegalArgumentException e) {
      JFrame frame = new JFrame();
      JOptionPane.showMessageDialog(frame,
          String.format(e.getMessage()),
          "Error Message", JOptionPane.ERROR_MESSAGE);
      System.exit(0);
    }
    ViewModel viewModel = new ViewModel(model);
    IntTextualAnimationView view = new IntTextualAnimationView(viewModel, output, 1);
    IController controller = new TextualController(view);

    controller.execute();

    try {
      output.close();
    }
    catch (IOException e) {
      JFrame frame = new JFrame();
      JOptionPane.showMessageDialog(frame,
          String.format("Error!"),
          "Error Message", JOptionPane.ERROR_MESSAGE);
      System.exit(0);
    }
  }
}












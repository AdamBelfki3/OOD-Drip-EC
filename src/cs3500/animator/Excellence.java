package cs3500.animator;

import cs3500.animator.controller.AnimationController;
import cs3500.animator.controller.AnimationControllerInteractivePlus;
import cs3500.animator.controller.SVGController;
import cs3500.animator.controller.TextualController;
import cs3500.animator.model.AnimationModel;
import cs3500.animator.model.IViewModel;
import cs3500.animator.model.SimpleAnimation.Builder;
import cs3500.animator.model.ViewModel;
import cs3500.animator.provider.controller.ProviderInteractiveControllerAdapter;
import cs3500.animator.provider.model.hw05.IAnimationModel;
import cs3500.animator.provider.model.hw05.ProviderModelAdapter;
import cs3500.animator.provider.model.hw05.ProviderModelAdapter.ProviderBuilder;
import cs3500.animator.provider.view.interactive.AnimationInteractiveView;
import cs3500.animator.provider.view.textual.AnimationTextualView;
import cs3500.animator.util.AnimationBuilder;
import cs3500.animator.util.AnimationReader;
import cs3500.animator.view.IAnimationView;
import cs3500.animator.view.InteractiveViewPlus;
import cs3500.animator.view.svg.SVGAnimationPlus;
import cs3500.animator.view.textual.TextualAnimationView;
import cs3500.animator.view.VisualAnimationView;
import cs3500.animator.view.InteractiveVisualAnimationView;
import cs3500.animator.view.svg.SVGAnimationView;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 * This class contains the main method which is the entry point of my program where you can
 * run my program using command lines.
 */
public final class Excellence {
  /**
   * This method takes in inputs as command lines in the form of -in "name-of-animation-file"
   * -view "type-of-view" -out "where-output-show-go" -speed "integer-ticks-per-second". It will
   * then find the corresponding view and take in the input and output accordingly with the
   * given speed.
   * @param args takes in the command line arguments as strings
   */
  public static void main(String[] args) {
    int speed = 1;
    String outputFile = "";
    String inputFile = "";
    String viewType = "";
    boolean inYes = false;
    boolean viewYes = false;

    for (int ii = 0; ii < args.length; ii++) {
      switch (args[ii]) {
        case "-in":
          ii += 1;
          inputFile = args[ii];
          inYes = true;
          break;
        case "-view":
          ii += 1;
          viewType = args[ii];
          viewYes = true;
          break;
        case "-out":
          ii += 1;
          outputFile = args[ii];
          break;
        case "-speed":
          ii += 1;
          speed = Integer.parseInt(args[ii]);
          break;

        default:
          JFrame frame = new JFrame();
          JOptionPane.showMessageDialog(frame,
              String.format("%s, is an Invalid Input Argument!", args[ii]),
              "Error Message", JOptionPane.ERROR_MESSAGE);
          System.exit(0);
      }
    }

    if (!viewType.equals("provider") && !viewType.equals("hybrid")) {
      Readable file = null;

      AnimationBuilder builder = new Builder("Simple");
      if (viewType.equals("interactive plus") || viewType.equals("svg plus")) {
        builder = new Builder("Plus");
      }
       // this already has the model created

      try {
        file = new FileReader(inputFile);
      } catch (FileNotFoundException e) {
        JFrame frame = new JFrame();
        JOptionPane.showMessageDialog(frame,
            String.format("The file named %s, was not found!", inputFile),
            "Error Message", JOptionPane.ERROR_MESSAGE);
        System.exit(0);
      }

      System.out.println("before parsing");

      AnimationModel model = (AnimationModel) new AnimationReader().parseFile(file, builder);

      // start the animation
      try {
        model.startAnimation();
      } catch (IllegalArgumentException e) {
        // catch if there are any errors while starting the animation
        e.getMessage();
      }

      IViewModel viewModel = new ViewModel(model);

      BufferedWriter outFile = null;

      outFile = missingOutputFileCheck(outputFile, inputFile, null);

      IAnimationView view = null;

      // view type
      switch (viewType) {
        case "text":
          if (outputFile.equals("")) {
            view = new TextualAnimationView(viewModel, System.out, speed);
          } else {
            view = new TextualAnimationView(viewModel, outFile, speed);
          }
          new TextualController((TextualAnimationView) view).execute();
          break;
        case "visual":
          view = new VisualAnimationView(viewModel, speed);
          new AnimationController((VisualAnimationView) view).execute();
          break;
        case "svg":
          if (outputFile.equals("")) {
            view = new SVGAnimationView(viewModel, System.out, speed);
          } else {
            view = new SVGAnimationView(viewModel, outFile, speed);
          }
          new SVGController((SVGAnimationView) view).execute();
          break;
        case "svg plus":
          if (outputFile.equals("")) {
            view = new SVGAnimationPlus(viewModel, System.out, speed);
          } else {
            view = new SVGAnimationPlus(viewModel, outFile, speed);
          }
          new SVGController((SVGAnimationPlus) view).execute();
          break;
        case "interactive":
          view = new InteractiveVisualAnimationView(viewModel, speed);
          new AnimationController((InteractiveVisualAnimationView) view).execute();
          break;
        case "interactive plus":
          view = new InteractiveViewPlus(viewModel, speed);
          new AnimationControllerInteractivePlus((InteractiveViewPlus) view).execute();
          break;
        default:
          JFrame frame = new JFrame();
          JOptionPane.showMessageDialog(frame,
              String.format("%s, is an Invalid View Type Requested!", viewType),
              "Error Message", JOptionPane.ERROR_MESSAGE);
          System.exit(0);
      }

      inAndViewYes(outputFile, inYes, viewYes, outFile);
    }


    else if (viewType.equals("hybrid")) {
      cs3500.animator.provider.util.AnimationBuilder builder = new ProviderBuilder();

      Readable file = null;

      try {
        file = new FileReader(inputFile);
      } catch (FileNotFoundException e) {
        JFrame frame = new JFrame();
        JOptionPane.showMessageDialog(frame,
            String.format("The file named %s, was not found!", inputFile),
            "Error Message", JOptionPane.ERROR_MESSAGE);
        System.exit(0);
      }

      ProviderModelAdapter model = (ProviderModelAdapter)
          new cs3500.animator.provider.util.AnimationReader().parseFile(file, builder);

      cs3500.animator.provider.view.IAnimationView view = new AnimationInteractiveView(model);

      model.startInnerModel();

      int endTime = model.getAnimationLength();

      ProviderInteractiveControllerAdapter controller =
          new ProviderInteractiveControllerAdapter(new AnimationInteractiveView(model), endTime);

      controller.start(speed, "none");

      if (!(inYes && viewYes)) {
        JFrame frame = new JFrame();
        JOptionPane.showMessageDialog(frame,
            String.format("-view and -in must be specified to run this program!"),
            "Error Message", JOptionPane.ERROR_MESSAGE);
        System.exit(0);
      }
    }

    else {
      // this already creates model
      cs3500.animator.provider.util.AnimationBuilder builder = new ProviderBuilder();

      Readable file = null;
      BufferedWriter outFile = null;

      try {
        file = new FileReader(inputFile);
      } catch (FileNotFoundException e) {
        JFrame frame = new JFrame();
        JOptionPane.showMessageDialog(frame,
            String.format("The file named %s, was not found!", inputFile),
            "Error Message", JOptionPane.ERROR_MESSAGE);
        System.exit(0);
      }

      IAnimationModel model = (IAnimationModel)
          new cs3500.animator.provider.util.AnimationReader().parseFile(file, builder);


      outFile = missingOutputFileCheck(outputFile, inputFile, null);

      cs3500.animator.provider.view.IAnimationView view = null;

      try {
        if (outputFile.equals("")) {
          view = new AnimationTextualView(model, System.out);
          view.render(System.out,speed);
        } else {
          view = new AnimationTextualView(model, outFile);
          view.render(outFile, speed);
        }
      }
      catch (IOException e) {
        // cry if you want
      }

      inAndViewYes(outputFile, inYes, viewYes, outFile);
    }
  }

  private static BufferedWriter missingOutputFileCheck(String outputFile, String inputFile,
                                                       BufferedWriter outFile) {
    if (!outputFile.equals("")) {
      try {
        outFile = new BufferedWriter(new FileWriter(outputFile));
      } catch (IOException e) {
        JFrame frame = new JFrame();
        JOptionPane.showMessageDialog(frame,
            String.format("The file named %s, was not found!", inputFile),
            "Error Message", JOptionPane.ERROR_MESSAGE);
        System.exit(0);
      }
    }
    return outFile;
  }

  private static void inAndViewYes(String outputFile, boolean inYes, boolean viewYes,
                                   BufferedWriter outFile) {
    if (!(inYes && viewYes)) {
      JFrame frame = new JFrame();
      JOptionPane.showMessageDialog(frame,
          String.format("-view and -in must be specified to run this program!"),
          "Error Message", JOptionPane.ERROR_MESSAGE);
      System.exit(0);
    }

    if (!outputFile.equals("")) {
      try {
        outFile.close();
      } catch (IOException e) {
        e.getMessage();
      }
    }
  }
}



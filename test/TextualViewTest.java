import cs3500.animator.model.ViewModel;
import cs3500.animator.view.textual.TextualAnimationView;
import java.io.IOException;
import cs3500.animator.model.AnimationModel;
import cs3500.animator.model.SimpleAnimation;
import org.junit.Test;
import cs3500.animator.view.IAnimationView;

import static org.junit.Assert.assertEquals;

/**
 * Tests in order to check the textual view method with ticks substituted as seconds as well as
 * the test for textual view with ticks. This ensures that the "textual" view is
 * outputting the animation correctly.
 */
public class TextualViewTest {
  // test to string with seconds
  @Test
  public void renderViewSeconds() {
    AnimationModel model = new SimpleAnimation();
    model.setBounds(200, 70, 360, 360);
    model.createShape("rectangle", "R");
    model.createShape("ellipse", "C");
    model.addOperation("R", 1, 10, 10, 200, 50, 100, 255, 0,
            0);
    model.addOperation("R", 10, 50, 300, 300, 50, 100, 255, 0,
            0);
    model.addOperation("R", 50, 51);
    model.addOperation("R", 51, 70, 300, 300, 50, 100, 255, 0,
            0);
    model.addOperation("R", 70, 100, 200, 200, 25, 100, 255, 0,
            0);
    model.addOperation("C", 6, 20, 400, 70, 120, 60, 0, 0,
            255);
    model.addOperation("C", 20, 50, 440, 250, 120, 60, 0, 0,
            255);
    model.addOperation("C", 50, 70, 440, 370, 120, 60, 0, 170,
            85);
    model.addOperation("C", 70, 80, 440, 370, 120, 60, 0, 255,
            0);
    model.addOperation("C", 80, 100, 440, 370, 120, 60, 0, 255,
            0);

    //assertEquals(0, model.getInstructions());
    model.startAnimation();

    //assertEquals(0, model.getInstructions());

    ViewModel viewModel = new ViewModel(model);
    StringBuilder out1 = new StringBuilder();

    IAnimationView view = new TextualAnimationView(viewModel, out1, 5);

    try {
      view.render();
    }
    catch (IOException e) {
      e.getMessage();
    }

    assertEquals("canvas 200 70 360 360\n"
                    + "shape R rectangle\n"
                    + "motion R 0.20 0 0 10 10 0 0 0   2.00 10 200 50 100 255 0 0\n"
                    + "motion R 2.00 10 200 50 100 255 0 0   10.00 300 300 50 100 255 0 0\n"
                    + "motion R 10.00 300 300 50 100 255 0 0   10.20 300 300 50 100 255 0 0\n"
                    + "motion R 10.20 300 300 50 100 255 0 0   14.00 300 300 50 100 255 0 0\n"
                    + "motion R 14.00 300 300 50 100 255 0 0   20.00 200 200 25 100 255 0 0\n"
                    + "shape C ellipse\n"
                    + "motion C 1.20 0 0 10 10 0 0 0   4.00 400 70 120 60 0 0 255\n"
                    + "motion C 4.00 400 70 120 60 0 0 255   10.00 440 250 120 60 0 0 255\n"
                    + "motion C 10.00 440 250 120 60 0 0 255   14.00 440 370 120 60 0 170 85\n"
                    + "motion C 14.00 440 370 120 60 0 170 85   16.00 440 370 120 60 0 255 0\n"
                    + "motion C 16.00 440 370 120 60 0 255 0   20.00 440 370 120 60 0 255 0\n",
            out1.toString());


  }

  // shape not found exception
  @Test (expected = IllegalArgumentException.class)
  public void textException() {
    AnimationModel model = new SimpleAnimation();

    model.setBounds(200, 70, 360, 360);
    model.createShape("triangle", "R");
    model.startAnimation();

    ViewModel viewModel = new ViewModel(model);


    StringBuilder out1 = new StringBuilder();
    IAnimationView view = new TextualAnimationView(viewModel, out1, 5);
    try {
      view.render();
    }
    catch (IOException e) {
      // do nothing
    }
    out1.toString();
  }

  //test null model
  @Test (expected = NullPointerException.class)
  public void nullModel() {
    AnimationModel model = new SimpleAnimation();
    model.startAnimation();
    ViewModel viewModel = new ViewModel(model);

    StringBuilder out1 = new StringBuilder();
    IAnimationView view = new TextualAnimationView(null, out1, 5);
    try {
      view.render();
    }
    catch (IOException e) {
      // do nothing
    }
  }

  // gap operations
  @Test (expected = IllegalStateException.class)
  public void gapRenderView() {
    AnimationModel model = new SimpleAnimation();
    model.setBounds(200, 70, 360, 360);
    model.createShape("rectangle", "R");
    model.createShape("ellipse", "C");
    model.addOperation("R", 1, 10, 10, 200, 50, 100, 255, 0,
            0);
    model.addOperation("R", 14, 50, 300, 300, 50, 100, 255, 0,
            0);

    model.startAnimation();

    ViewModel viewModel = new ViewModel(model);

    StringBuilder out1 = new StringBuilder();

    IAnimationView view = new TextualAnimationView(viewModel, out1, 5);

    try {
      view.render();
    }
    catch (IOException e) {
      // do nothing
    }

    assertEquals("canvas 200 70 360 360\n"
                    + "shape R rectangle\n"
                    + "motion R 0.20 0 0 10 10 0 0 0   2.00 10 200 50 100 255 0 0\n"
                    + "motion R 2.00 10 200 50 100 255 0 0   10.00 300 300 50 100 255 0 0\n"
                    + "motion R 10.00 300 300 50 100 255 0 0   10.20 300 300 50 100 255 0 0\n"
                    + "motion R 10.20 300 300 50 100 255 0 0   14.00 300 300 50 100 255 0 0\n"
                    + "motion R 14.00 300 300 50 100 255 0 0   20.00 200 200 25 100 255 0 0\n"
                    + "shape C ellipse\n"
                    + "motion C 1.20 0 0 10 10 0 0 0   4.00 400 70 120 60 0 0 255\n"
                    + "motion C 4.00 400 70 120 60 0 0 255   10.00 440 250 120 60 0 0 255\n"
                    + "motion C 10.00 440 250 120 60 0 0 255   14.00 440 370 120 60 0 170 85\n"
                    + "motion C 14.00 440 370 120 60 0 170 85   16.00 440 370 120 60 0 255 0\n"
                    + "motion C 16.00 440 370 120 60 0 255 0   20.00 440 370 120 60 0 255 0\n",
            out1.toString());


  }

  // alternate motion test should have same output
  @Test
  public void renderViewAlternateMotion() {
    AnimationModel model = new SimpleAnimation();

    model.setBounds(200, 70, 360, 360);
    model.createShape("rectangle", "R");
    model.createShape("ellipse", "C");
    model.addOperation("R", 10, 50, 300, 300, 50, 100, 255, 0,
            0);
    model.addOperation("R", 51, 70, 300, 300, 50, 100, 255, 0,
            0);
    model.addOperation("R", 1, 10, 10, 200, 50, 100, 255, 0,
            0);
    model.addOperation("C", 70, 80, 440, 370, 120, 60, 0, 255,
            0);
    model.addOperation("R", 50, 51);
    model.addOperation("C", 80, 100, 440, 370, 120, 60, 0, 255,
            0);
    model.addOperation("R", 70, 100, 200, 200, 25, 100, 255, 0,
            0);
    model.addOperation("C", 6, 20, 400, 70, 120, 60, 0, 0,
            255);
    model.addOperation("C", 20, 50, 440, 250, 120, 60, 0, 0,
            255);
    model.addOperation("C", 50, 70, 440, 370, 120, 60, 0, 170,
            85);


    model.startAnimation();

    ViewModel viewModel = new ViewModel(model);

    StringBuilder out1 = new StringBuilder();

    IAnimationView view = new TextualAnimationView(viewModel, out1, 5);

    try {
      view.render();
    } catch (IOException e) {
      // do nothing
    }

    assertEquals("canvas 200 70 360 360\n"
                    + "shape R rectangle\n"
                    + "motion R 0.20 0 0 10 10 0 0 0   2.00 10 200 50 100 255 0 0\n"
                    + "motion R 2.00 10 200 50 100 255 0 0   10.00 300 300 50 100 255 0 0\n"
                    + "motion R 10.00 300 300 50 100 255 0 0   10.20 300 300 50 100 255 0 0\n"
                    + "motion R 10.20 300 300 50 100 255 0 0   14.00 300 300 50 100 255 0 0\n"
                    + "motion R 14.00 300 300 50 100 255 0 0   20.00 200 200 25 100 255 0 0\n"
                    + "shape C ellipse\n"
                    + "motion C 1.20 0 0 10 10 0 0 0   4.00 400 70 120 60 0 0 255\n"
                    + "motion C 4.00 400 70 120 60 0 0 255   10.00 440 250 120 60 0 0 255\n"
                    + "motion C 10.00 440 250 120 60 0 0 255   14.00 440 370 120 60 0 170 85\n"
                    + "motion C 14.00 440 370 120 60 0 170 85   16.00 440 370 120 60 0 255 0\n"
                    + "motion C 16.00 440 370 120 60 0 255 0   20.00 440 370 120 60 0 255 0\n",
            out1.toString());
  }

}

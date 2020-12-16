import cs3500.animator.model.AnimationModel;
import cs3500.animator.model.SimpleAnimation;
import cs3500.animator.model.ViewModel;
import cs3500.animator.view.IAnimationView;
import cs3500.animator.view.SVGAnimationView;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertEquals;


/**
 * This class contains tests of the text output of the SVG to ensure that it has
 * the expected text output. It also tests for exceptions of what should happen if the
 * input is not correct.
 */
public class SVGAnimationTest {
  // gap in motion
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

    IAnimationView view = new SVGAnimationView(viewModel, out1, 5);

    try {
      view.render();
    }
    catch (IOException e) {
      // do nothing
    }

  }

  // test null model
  @Test (expected = NullPointerException.class)
  public void nullModel() {
    AnimationModel model = new SimpleAnimation();
    model.startAnimation();
    ViewModel viewModel = new ViewModel(model);
    StringBuilder out1 = new StringBuilder();
    IAnimationView view = new SVGAnimationView(null, out1, 5);
    try {
      view.render();
    }
    catch (IOException e) {
      // do nothing
    }
  }

  @Test
  public void emptyAnimation() {
    AnimationModel model = new SimpleAnimation();

    model.startAnimation();
    ViewModel viewModel = new ViewModel(model);
    StringBuilder out1 = new StringBuilder();
    IAnimationView view = new SVGAnimationView(viewModel, out1, 5);
    try {
      view.render();
    }
    catch (IOException e) {
      // do nothing
    }

    assertEquals("<svg width=\"0\" height=\"0\" version=\"1.1\"\n" +
            "     xmlns=\"http://www.w3.org/2000/svg\">\n" +
            "</svg>", out1.toString());

  }

  // shape not found exception
  @Test (expected = IllegalArgumentException.class)
  public void svgException() {
    AnimationModel model = new SimpleAnimation();

    model.setBounds(200, 70, 360, 360);
    model.createShape("triangle", "R");
    model.startAnimation();

    ViewModel viewModel = new ViewModel(model);

    StringBuilder out1 = new StringBuilder();
    IAnimationView view = new SVGAnimationView(viewModel, out1, 5);
    try {
      view.render();
    }
    catch (IOException e) {
      // do nothing
    }
    out1.toString();
  }

  @Test
  public void renderViewTest() {
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

    model.startAnimation();

    ViewModel viewModel = new ViewModel(model);


    StringBuilder out1 = new StringBuilder();

    IAnimationView view = new SVGAnimationView(viewModel, out1, 5);

    try {
      view.render();
    }
    catch (IOException e) {
      // do nothing
    }

    assertEquals(
            "<svg width=\"360\" height=\"360\" version=\"1.1\"\n" +
                    "     xmlns=\"http://www.w3.org/2000/svg\">\n" +
                    "<rect id=\"R\" x=\"0\" y=\"0\" width=\"10\" height=\"10\" " +
                    "fill=\"rgb(255,0,0)\"" +
                    " visibility=\"visible\" >\n" +
                    "<animate attributeType=\"xml\" begin=\"200ms\" dur=\"19800ms\" " +
                    "attributeName=\"visibility\" from=\"visible\" to=\"visible\" " +
                    "fill=\"freeze\" />\n" +
                    "<animate attributeType=\"xml\" begin=\"200ms\" dur=\"1800ms\" " +
                    "attributeName=\"x\" from=\"-200\" to=\"-190\" fill=\"freeze\" />\n" +
                    "<animate attributeType=\"xml\" begin=\"200ms\" dur=\"1800ms\" " +
                    "attributeName=\"y\" from=\"-70\" to=\"130\" fill=\"freeze\" />\n" +
                    "<animate attributeType=\"xml\" begin=\"200ms\" dur=\"1800ms\" " +
                    "attributeName=\"width\" from=\"10\" to=\"50\" fill=\"freeze\" />\n" +
                    "<animate attributeType=\"xml\" begin=\"200ms\" dur=\"1800ms\" " +
                    "attributeName=\"height\" from=\"10\" to=\"100\" fill=\"freeze\" />\n" +
                    "<animate attributeType=\"xml\" begin=\"200ms\" dur=\"1800ms\" " +
                    "attributeName=\"fill\" from=\"rgb(0, 0, 0)\" to=\"rgb(255, 0, 0)\" " +
                    "fill=\"freeze\" />\n" +
                    "<animate attributeType=\"xml\" begin=\"2000ms\" dur=\"8000ms\" " +
                    "attributeName=\"x\" from=\"-190\" to=\"100\" fill=\"freeze\" />\n" +
                    "<animate attributeType=\"xml\" begin=\"2000ms\" dur=\"8000ms\" " +
                    "attributeName=\"y\" from=\"130\" to=\"230\" fill=\"freeze\" />\n" +
                    "<animate attributeType=\"xml\" begin=\"2000ms\" dur=\"8000ms\" " +
                    "attributeName=\"width\" from=\"50\" to=\"50\" fill=\"freeze\" />\n" +
                    "<animate attributeType=\"xml\" begin=\"2000ms\" dur=\"8000ms\" " +
                    "attributeName=\"height\" from=\"100\" to=\"100\" fill=\"freeze\" />\n" +
                    "<animate attributeType=\"xml\" begin=\"2000ms\" dur=\"8000ms\" " +
                    "attributeName=\"fill\" from=\"rgb(255, 0, 0)\" to=\"rgb(255, 0, 0)\" " +
                    "fill=\"freeze\" />\n" +
                    "<animate attributeType=\"xml\" begin=\"10000ms\" dur=\"200ms\" " +
                    "attributeName=\"x\" from=\"100\" to=\"100\" fill=\"freeze\" />\n" +
                    "<animate attributeType=\"xml\" begin=\"10000ms\" dur=\"200ms\" " +
                    "attributeName=\"y\" from=\"230\" to=\"230\" fill=\"freeze\" />\n" +
                    "<animate attributeType=\"xml\" begin=\"10000ms\" dur=\"200ms\" " +
                    "attributeName=\"width\" from=\"50\" to=\"50\" fill=\"freeze\" />\n" +
                    "<animate attributeType=\"xml\" begin=\"10000ms\" dur=\"200ms\" " +
                    "attributeName=\"height\" from=\"100\" to=\"100\" fill=\"freeze\" />\n" +
                    "<animate attributeType=\"xml\" begin=\"10000ms\" dur=\"200ms\" " +
                    "attributeName=\"fill\" from=\"rgb(255, 0, 0)\" to=\"rgb(255, 0, 0)\" " +
                    "fill=\"freeze\" />\n" +
                    "<animate attributeType=\"xml\" begin=\"10200ms\" dur=\"3800ms\" " +
                    "attributeName=\"x\" from=\"100\" to=\"100\" fill=\"freeze\" />\n" +
                    "<animate attributeType=\"xml\" begin=\"10200ms\" dur=\"3800ms\" " +
                    "attributeName=\"y\" from=\"230\" to=\"230\" fill=\"freeze\" />\n" +
                    "<animate attributeType=\"xml\" begin=\"10200ms\" dur=\"3800ms\"" +
                    " attributeName=\"width\" from=\"50\" to=\"50\" fill=\"freeze\" />\n" +
                    "<animate attributeType=\"xml\" begin=\"10200ms\" dur=\"3800ms\" " +
                    "attributeName=\"height\" from=\"100\" to=\"100\" fill=\"freeze\" />\n" +
                    "<animate attributeType=\"xml\" begin=\"10200ms\" dur=\"3800ms\" " +
                    "attributeName=\"fill\" from=\"rgb(255, 0, 0)\" to=\"rgb(255, 0, 0)\" " +
                    "fill=\"freeze\" />\n" +
                    "<animate attributeType=\"xml\" begin=\"14000ms\" dur=\"6000ms\" " +
                    "attributeName=\"x\" from=\"100\" to=\"0\" fill=\"freeze\" />\n" +
                    "<animate attributeType=\"xml\" begin=\"14000ms\" dur=\"6000ms\" " +
                    "attributeName=\"y\" from=\"230\" to=\"130\" fill=\"freeze\" />\n" +
                    "<animate attributeType=\"xml\" begin=\"14000ms\" dur=\"6000ms\" " +
                    "attributeName=\"width\" from=\"50\" to=\"25\" fill=\"freeze\" />\n" +
                    "<animate attributeType=\"xml\" begin=\"14000ms\" dur=\"6000ms\" " +
                    "attributeName=\"height\" from=\"100\" to=\"100\" fill=\"freeze\" />\n" +
                    "<animate attributeType=\"xml\" begin=\"14000ms\" dur=\"6000ms\" " +
                    "attributeName=\"fill\" from=\"rgb(255, 0, 0)\" to=\"rgb(255, 0, 0)\" " +
                    "fill=\"freeze\" />\n" +
                    "</rect>\n" +
                    "\n" +
                    "<ellipse id=\"C\" cx=\"0\" cy=\"0\" rx=\"10\" ry=\"10\" " +
                    "fill=\"rgb(255,0,0)\" " +
                    "visibility=\"hidden\" >\n" +
                    "<animate attributeType=\"xml\" begin=\"1200ms\" dur=\"18800ms\" " +
                    "attributeName=\"visibility\" from=\"hidden\" to=\"visible\" " +
                    "fill=\"freeze\" />\n" +
                    "<animate attributeType=\"xml\" begin=\"1200ms\" dur=\"2800ms\" " +
                    "attributeName=\"cx\" from=\"-200\" to=\"200\" fill=\"freeze\" />\n" +
                    "<animate attributeType=\"xml\" begin=\"1200ms\" dur=\"2800ms\" " +
                    "attributeName=\"cy\" from=\"-70\" to=\"0\" fill=\"freeze\" />\n" +
                    "<animate attributeType=\"xml\" begin=\"1200ms\" dur=\"2800ms\" " +
                    "attributeName=\"rx\" from=\"5\" to=\"60\" fill=\"freeze\" />\n" +
                    "<animate attributeType=\"xml\" begin=\"1200ms\" dur=\"2800ms\" " +
                    "attributeName=\"ry\" from=\"5\" to=\"30\" fill=\"freeze\" />\n" +
                    "<animate attributeType=\"xml\" begin=\"1200ms\" dur=\"2800ms\" " +
                    "attributeName=\"fill\" from=\"rgb(0, 0, 0)\" to=\"rgb(0, 0, 255)\" " +
                    "fill=\"freeze\" />\n" +
                    "<animate attributeType=\"xml\" begin=\"4000ms\" dur=\"6000ms\" " +
                    "attributeName=\"cx\" from=\"200\" to=\"240\" fill=\"freeze\" />\n" +
                    "<animate attributeType=\"xml\" begin=\"4000ms\" dur=\"6000ms\" " +
                    "attributeName=\"cy\" from=\"0\" to=\"180\" fill=\"freeze\" />\n" +
                    "<animate attributeType=\"xml\" begin=\"4000ms\" dur=\"6000ms\" " +
                    "attributeName=\"rx\" from=\"60\" to=\"60\" fill=\"freeze\" />\n" +
                    "<animate attributeType=\"xml\" begin=\"4000ms\" dur=\"6000ms\" " +
                    "attributeName=\"ry\" from=\"30\" to=\"30\" fill=\"freeze\" />\n" +
                    "<animate attributeType=\"xml\" begin=\"4000ms\" dur=\"6000ms\" " +
                    "attributeName=\"fill\" from=\"rgb(0, 0, 255)\" to=\"rgb(0, 0, 255)\" " +
                    "fill=\"freeze\" />\n" +
                    "<animate attributeType=\"xml\" begin=\"10000ms\" dur=\"4000ms\" " +
                    "attributeName=\"cx\" from=\"240\" to=\"240\" fill=\"freeze\" />\n" +
                    "<animate attributeType=\"xml\" begin=\"10000ms\" dur=\"4000ms\" " +
                    "attributeName=\"cy\" from=\"180\" to=\"300\" fill=\"freeze\" />\n" +
                    "<animate attributeType=\"xml\" begin=\"10000ms\" dur=\"4000ms\" " +
                    "attributeName=\"rx\" from=\"60\" to=\"60\" fill=\"freeze\" />\n" +
                    "<animate attributeType=\"xml\" begin=\"10000ms\" dur=\"4000ms\" " +
                    "attributeName=\"ry\" from=\"30\" to=\"30\" fill=\"freeze\" />\n" +
                    "<animate attributeType=\"xml\" begin=\"10000ms\" dur=\"4000ms\" " +
                    "attributeName=\"fill\" from=\"rgb(0, 0, 255)\" to=\"rgb(0, 170, 85)\"" +
                    " fill=\"freeze\" />\n" +
                    "<animate attributeType=\"xml\" begin=\"14000ms\" dur=\"2000ms\" " +
                    "attributeName=\"cx\" from=\"240\" to=\"240\" fill=\"freeze\" />\n" +
                    "<animate attributeType=\"xml\" begin=\"14000ms\" dur=\"2000ms\" " +
                    "attributeName=\"cy\" from=\"300\" to=\"300\" fill=\"freeze\" />\n" +
                    "<animate attributeType=\"xml\" begin=\"14000ms\" dur=\"2000ms\" " +
                    "attributeName=\"rx\" from=\"60\" to=\"60\" fill=\"freeze\" />\n" +
                    "<animate attributeType=\"xml\" begin=\"14000ms\" dur=\"2000ms\" " +
                    "attributeName=\"ry\" from=\"30\" to=\"30\" fill=\"freeze\" />\n" +
                    "<animate attributeType=\"xml\" begin=\"14000ms\" dur=\"2000ms\" " +
                    "attributeName=\"fill\" from=\"rgb(0, 170, 85)\" to=\"rgb(0, 255, 0)\"" +
                    " fill=\"freeze\" />\n" +
                    "<animate attributeType=\"xml\" begin=\"16000ms\" dur=\"4000ms\" " +
                    "attributeName=\"cx\" from=\"240\" to=\"240\" fill=\"freeze\" />\n" +
                    "<animate attributeType=\"xml\" begin=\"16000ms\" dur=\"4000ms\" " +
                    "attributeName=\"cy\" from=\"300\" to=\"300\" fill=\"freeze\" />\n" +
                    "<animate attributeType=\"xml\" begin=\"16000ms\" dur=\"4000ms\" " +
                    "attributeName=\"rx\" from=\"60\" to=\"60\" fill=\"freeze\" />\n" +
                    "<animate attributeType=\"xml\" begin=\"16000ms\" dur=\"4000ms\"" +
                    " attributeName=\"ry\" from=\"30\" to=\"30\" fill=\"freeze\" />\n" +
                    "<animate attributeType=\"xml\" begin=\"16000ms\" dur=\"4000ms\" " +
                    "attributeName=\"fill\" from=\"rgb(0, 255, 0)\" to=\"rgb(0, 255, 0)\" " +
                    "fill=\"freeze\" />\n" +
                    "</ellipse>\n" +
                    "\n" +
                    "</svg>", out1.toString());
  }

  // alternate motions should render same output
  @Test
  public void renderViewTestAlternate() {
    AnimationModel model = new SimpleAnimation();

    model.setBounds(200, 70, 360, 360);
    model.createShape("rectangle", "R");
    model.createShape("ellipse", "C");
    model.addOperation("R", 10, 50, 300, 300, 50, 100, 255, 0,
            0);
    model.addOperation("R", 1, 10, 10, 200, 50, 100, 255, 0,
            0);
    model.addOperation("R", 50, 51);
    model.addOperation("R", 51, 70, 300, 300, 50, 100, 255, 0,
            0);
    model.addOperation("C", 6, 20, 400, 70, 120, 60, 0, 0,
            255);
    model.addOperation("C", 70, 80, 440, 370, 120, 60, 0, 255,
            0);
    model.addOperation("C", 50, 70, 440, 370, 120, 60, 0, 170,
            85);
    model.addOperation("C", 20, 50, 440, 250, 120, 60, 0, 0,
            255);
    model.addOperation("C", 80, 100, 440, 370, 120, 60, 0, 255,
            0);
    model.addOperation("R", 70, 100, 200, 200, 25, 100, 255, 0,
            0);

    model.startAnimation();

    ViewModel viewModel = new ViewModel(model);


    StringBuilder out1 = new StringBuilder();

    IAnimationView view = new SVGAnimationView(viewModel, out1, 5);

    try {
      view.render();
    }
    catch (IOException e) {
      // do nothing
    }

    assertEquals(
            "<svg width=\"360\" height=\"360\" version=\"1.1\"\n" +
                    "     xmlns=\"http://www.w3.org/2000/svg\">\n" +
                    "<rect id=\"R\" x=\"0\" y=\"0\" width=\"10\" height=\"10\" " +
                    "fill=\"rgb(255,0,0)\"" +
                    " visibility=\"visible\" >\n" +
                    "<animate attributeType=\"xml\" begin=\"200ms\" dur=\"19800ms\" " +
                    "attributeName=\"visibility\" from=\"visible\" to=\"visible\" " +
                    "fill=\"freeze\" />\n" +
                    "<animate attributeType=\"xml\" begin=\"200ms\" dur=\"1800ms\" " +
                    "attributeName=\"x\" from=\"-200\" to=\"-190\" fill=\"freeze\" />\n" +
                    "<animate attributeType=\"xml\" begin=\"200ms\" dur=\"1800ms\" " +
                    "attributeName=\"y\" from=\"-70\" to=\"130\" fill=\"freeze\" />\n" +
                    "<animate attributeType=\"xml\" begin=\"200ms\" dur=\"1800ms\" " +
                    "attributeName=\"width\" from=\"10\" to=\"50\" fill=\"freeze\" />\n" +
                    "<animate attributeType=\"xml\" begin=\"200ms\" dur=\"1800ms\" " +
                    "attributeName=\"height\" from=\"10\" to=\"100\" fill=\"freeze\" />\n" +
                    "<animate attributeType=\"xml\" begin=\"200ms\" dur=\"1800ms\" " +
                    "attributeName=\"fill\" from=\"rgb(0, 0, 0)\" to=\"rgb(255, 0, 0)\" " +
                    "fill=\"freeze\" />\n" +
                    "<animate attributeType=\"xml\" begin=\"2000ms\" dur=\"8000ms\" " +
                    "attributeName=\"x\" from=\"-190\" to=\"100\" fill=\"freeze\" />\n" +
                    "<animate attributeType=\"xml\" begin=\"2000ms\" dur=\"8000ms\" " +
                    "attributeName=\"y\" from=\"130\" to=\"230\" fill=\"freeze\" />\n" +
                    "<animate attributeType=\"xml\" begin=\"2000ms\" dur=\"8000ms\" " +
                    "attributeName=\"width\" from=\"50\" to=\"50\" fill=\"freeze\" />\n" +
                    "<animate attributeType=\"xml\" begin=\"2000ms\" dur=\"8000ms\" " +
                    "attributeName=\"height\" from=\"100\" to=\"100\" fill=\"freeze\" />\n" +
                    "<animate attributeType=\"xml\" begin=\"2000ms\" dur=\"8000ms\" " +
                    "attributeName=\"fill\" from=\"rgb(255, 0, 0)\" to=\"rgb(255, 0, 0)\" " +
                    "fill=\"freeze\" />\n" +
                    "<animate attributeType=\"xml\" begin=\"10000ms\" dur=\"200ms\" " +
                    "attributeName=\"x\" from=\"100\" to=\"100\" fill=\"freeze\" />\n" +
                    "<animate attributeType=\"xml\" begin=\"10000ms\" dur=\"200ms\" " +
                    "attributeName=\"y\" from=\"230\" to=\"230\" fill=\"freeze\" />\n" +
                    "<animate attributeType=\"xml\" begin=\"10000ms\" dur=\"200ms\" " +
                    "attributeName=\"width\" from=\"50\" to=\"50\" fill=\"freeze\" />\n" +
                    "<animate attributeType=\"xml\" begin=\"10000ms\" dur=\"200ms\" " +
                    "attributeName=\"height\" from=\"100\" to=\"100\" fill=\"freeze\" />\n" +
                    "<animate attributeType=\"xml\" begin=\"10000ms\" dur=\"200ms\" " +
                    "attributeName=\"fill\" from=\"rgb(255, 0, 0)\" to=\"rgb(255, 0, 0)\" " +
                    "fill=\"freeze\" />\n" +
                    "<animate attributeType=\"xml\" begin=\"10200ms\" dur=\"3800ms\" " +
                    "attributeName=\"x\" from=\"100\" to=\"100\" fill=\"freeze\" />\n" +
                    "<animate attributeType=\"xml\" begin=\"10200ms\" dur=\"3800ms\" " +
                    "attributeName=\"y\" from=\"230\" to=\"230\" fill=\"freeze\" />\n" +
                    "<animate attributeType=\"xml\" begin=\"10200ms\" dur=\"3800ms\"" +
                    " attributeName=\"width\" from=\"50\" to=\"50\" fill=\"freeze\" />\n" +
                    "<animate attributeType=\"xml\" begin=\"10200ms\" dur=\"3800ms\" " +
                    "attributeName=\"height\" from=\"100\" to=\"100\" fill=\"freeze\" />\n" +
                    "<animate attributeType=\"xml\" begin=\"10200ms\" dur=\"3800ms\" " +
                    "attributeName=\"fill\" from=\"rgb(255, 0, 0)\" to=\"rgb(255, 0, 0)\" " +
                    "fill=\"freeze\" />\n" +
                    "<animate attributeType=\"xml\" begin=\"14000ms\" dur=\"6000ms\" " +
                    "attributeName=\"x\" from=\"100\" to=\"0\" fill=\"freeze\" />\n" +
                    "<animate attributeType=\"xml\" begin=\"14000ms\" dur=\"6000ms\" " +
                    "attributeName=\"y\" from=\"230\" to=\"130\" fill=\"freeze\" />\n" +
                    "<animate attributeType=\"xml\" begin=\"14000ms\" dur=\"6000ms\" " +
                    "attributeName=\"width\" from=\"50\" to=\"25\" fill=\"freeze\" />\n" +
                    "<animate attributeType=\"xml\" begin=\"14000ms\" dur=\"6000ms\" " +
                    "attributeName=\"height\" from=\"100\" to=\"100\" fill=\"freeze\" />\n" +
                    "<animate attributeType=\"xml\" begin=\"14000ms\" dur=\"6000ms\" " +
                    "attributeName=\"fill\" from=\"rgb(255, 0, 0)\" to=\"rgb(255, 0, 0)\" " +
                    "fill=\"freeze\" />\n" +
                    "</rect>\n" +
                    "\n" +
                    "<ellipse id=\"C\" cx=\"0\" cy=\"0\" rx=\"10\" ry=\"10\" " +
                    "fill=\"rgb(255,0,0)\" " +
                    "visibility=\"hidden\" >\n" +
                    "<animate attributeType=\"xml\" begin=\"1200ms\" dur=\"18800ms\" " +
                    "attributeName=\"visibility\" from=\"hidden\" to=\"visible\" " +
                    "fill=\"freeze\" />\n" +
                    "<animate attributeType=\"xml\" begin=\"1200ms\" dur=\"2800ms\" " +
                    "attributeName=\"cx\" from=\"-200\" to=\"200\" fill=\"freeze\" />\n" +
                    "<animate attributeType=\"xml\" begin=\"1200ms\" dur=\"2800ms\" " +
                    "attributeName=\"cy\" from=\"-70\" to=\"0\" fill=\"freeze\" />\n" +
                    "<animate attributeType=\"xml\" begin=\"1200ms\" dur=\"2800ms\" " +
                    "attributeName=\"rx\" from=\"5\" to=\"60\" fill=\"freeze\" />\n" +
                    "<animate attributeType=\"xml\" begin=\"1200ms\" dur=\"2800ms\" " +
                    "attributeName=\"ry\" from=\"5\" to=\"30\" fill=\"freeze\" />\n" +
                    "<animate attributeType=\"xml\" begin=\"1200ms\" dur=\"2800ms\" " +
                    "attributeName=\"fill\" from=\"rgb(0, 0, 0)\" to=\"rgb(0, 0, 255)\" " +
                    "fill=\"freeze\" />\n" +
                    "<animate attributeType=\"xml\" begin=\"4000ms\" dur=\"6000ms\" " +
                    "attributeName=\"cx\" from=\"200\" to=\"240\" fill=\"freeze\" />\n" +
                    "<animate attributeType=\"xml\" begin=\"4000ms\" dur=\"6000ms\" " +
                    "attributeName=\"cy\" from=\"0\" to=\"180\" fill=\"freeze\" />\n" +
                    "<animate attributeType=\"xml\" begin=\"4000ms\" dur=\"6000ms\" " +
                    "attributeName=\"rx\" from=\"60\" to=\"60\" fill=\"freeze\" />\n" +
                    "<animate attributeType=\"xml\" begin=\"4000ms\" dur=\"6000ms\" " +
                    "attributeName=\"ry\" from=\"30\" to=\"30\" fill=\"freeze\" />\n" +
                    "<animate attributeType=\"xml\" begin=\"4000ms\" dur=\"6000ms\" " +
                    "attributeName=\"fill\" from=\"rgb(0, 0, 255)\" to=\"rgb(0, 0, 255)\" " +
                    "fill=\"freeze\" />\n" +
                    "<animate attributeType=\"xml\" begin=\"10000ms\" dur=\"4000ms\" " +
                    "attributeName=\"cx\" from=\"240\" to=\"240\" fill=\"freeze\" />\n" +
                    "<animate attributeType=\"xml\" begin=\"10000ms\" dur=\"4000ms\" " +
                    "attributeName=\"cy\" from=\"180\" to=\"300\" fill=\"freeze\" />\n" +
                    "<animate attributeType=\"xml\" begin=\"10000ms\" dur=\"4000ms\" " +
                    "attributeName=\"rx\" from=\"60\" to=\"60\" fill=\"freeze\" />\n" +
                    "<animate attributeType=\"xml\" begin=\"10000ms\" dur=\"4000ms\" " +
                    "attributeName=\"ry\" from=\"30\" to=\"30\" fill=\"freeze\" />\n" +
                    "<animate attributeType=\"xml\" begin=\"10000ms\" dur=\"4000ms\" " +
                    "attributeName=\"fill\" from=\"rgb(0, 0, 255)\" to=\"rgb(0, 170, 85)\"" +
                    " fill=\"freeze\" />\n" +
                    "<animate attributeType=\"xml\" begin=\"14000ms\" dur=\"2000ms\" " +
                    "attributeName=\"cx\" from=\"240\" to=\"240\" fill=\"freeze\" />\n" +
                    "<animate attributeType=\"xml\" begin=\"14000ms\" dur=\"2000ms\" " +
                    "attributeName=\"cy\" from=\"300\" to=\"300\" fill=\"freeze\" />\n" +
                    "<animate attributeType=\"xml\" begin=\"14000ms\" dur=\"2000ms\" " +
                    "attributeName=\"rx\" from=\"60\" to=\"60\" fill=\"freeze\" />\n" +
                    "<animate attributeType=\"xml\" begin=\"14000ms\" dur=\"2000ms\" " +
                    "attributeName=\"ry\" from=\"30\" to=\"30\" fill=\"freeze\" />\n" +
                    "<animate attributeType=\"xml\" begin=\"14000ms\" dur=\"2000ms\" " +
                    "attributeName=\"fill\" from=\"rgb(0, 170, 85)\" to=\"rgb(0, 255, 0)\"" +
                    " fill=\"freeze\" />\n" +
                    "<animate attributeType=\"xml\" begin=\"16000ms\" dur=\"4000ms\" " +
                    "attributeName=\"cx\" from=\"240\" to=\"240\" fill=\"freeze\" />\n" +
                    "<animate attributeType=\"xml\" begin=\"16000ms\" dur=\"4000ms\" " +
                    "attributeName=\"cy\" from=\"300\" to=\"300\" fill=\"freeze\" />\n" +
                    "<animate attributeType=\"xml\" begin=\"16000ms\" dur=\"4000ms\" " +
                    "attributeName=\"rx\" from=\"60\" to=\"60\" fill=\"freeze\" />\n" +
                    "<animate attributeType=\"xml\" begin=\"16000ms\" dur=\"4000ms\"" +
                    " attributeName=\"ry\" from=\"30\" to=\"30\" fill=\"freeze\" />\n" +
                    "<animate attributeType=\"xml\" begin=\"16000ms\" dur=\"4000ms\" " +
                    "attributeName=\"fill\" from=\"rgb(0, 255, 0)\" to=\"rgb(0, 255, 0)\" " +
                    "fill=\"freeze\" />\n" +
                    "</ellipse>\n" +
                    "\n" +
                    "</svg>", out1.toString());
  }
}

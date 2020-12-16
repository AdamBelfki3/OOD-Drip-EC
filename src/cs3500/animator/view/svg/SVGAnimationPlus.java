package cs3500.animator.view.svg;

import cs3500.animator.model.IMotion;
import cs3500.animator.model.IShapeAndMotion;
import cs3500.animator.model.IViewModel;
import java.util.Map;

public class SVGAnimationPlus extends SVGAnimationView implements ISVGAnimationView {

  /**
   * Displays the model in the svg file format but ouputs in text format.
   *
   * @param viewModel takes in the model that we want to render in text
   * @param out       the out where we append our result to
   * @param speed     the speed of the animation
   */
  public SVGAnimationPlus(IViewModel viewModel, Appendable out, int speed) {
    super(viewModel, out, speed);
  }


  @Override
  public String toString() {
    StringBuilder result = new StringBuilder();
    Map<String, IShapeAndMotion> allSM = this.viewModel.getInstructions();

    for (String name : allSM.keySet()) {
      IShapeAndMotion sm = allSM.get(name);

      switch (sm.getShape().shapeName()) {
        case "plus":
          // first rectangle is vertical rectangle
          // format open tag
          String openTagPlus1 = String.format("<rect id=\"%sV\" x=\"%.0f\" y=\"%.0f\" " +
                  "width=\"%d\" height=\"%d\" "
                  + "fill=\"rgb(%d,%d,%d)\" visibility=\"visible\" >\n", name,
              sm.getShape().getPos().getX() +
                  ((double)(sm.getShape().getSize().getWidth()  * 1 / 3)),
              sm.getShape().getPos().getY(),
              (int)((double)(sm.getShape().getSize().getWidth()) * 1 / 3),
              sm.getShape().getSize().getHeight(),
              sm.getShape().getRgb().getR(), sm.getShape().getRgb().getG(),
              sm.getShape().getRgb().getB());
          result.append(openTagPlus1);

          String visibilityAttributePlus1 =
              String.format("<animate attributeType=\"xml\" begin=\"%dms\" dur=\"%dms\" "
                      + "attributeName=\"visibility\" from=\"visible\" to=\"visible\" " +
                      "fill=\"freeze\" />\n",
                  (int)((double)(sm.getStartTime() * 1000) / (double)speed),
                  (int)((double)(sm.getEndTime() * 1000) / (double)speed)
                      - (int)((double)(sm.getStartTime() * 1000) / (double)speed));

          result.append(visibilityAttributePlus1);

          // format 5 animate tags for each motion (1 for each attribute name) + visibility tag
          for (IMotion m : sm.getMotions()) {
            String xAttribute =
                String.format("<animate attributeType=\"xml\" begin=\"%dms\" dur=\"%dms\" "
                        + "attributeName=\"x\" from=\"%.0f\" to=\"%.0f\" fill=\"freeze\" />\n",
                    (int)((double)(m.getStartTime() * 1000) / (double)speed),
                    (int)((double)(m.getEndTime() * 1000) / (double)speed)
                        - (int)((double)(m.getStartTime() * 1000) / (double)speed),
                    m.getStartPos().getX() + ((double)(m.getStartSize().getWidth()) * 1 / 3)
                        - this.viewModel.getXCanvas(),
                    m.getEndPos().getX() + ((double) (m.getEndSize().getWidth() * 1 / 3))
                        - this.viewModel.getXCanvas());
            result.append(xAttribute);

            String yAttribute =
                String.format("<animate attributeType=\"xml\" begin=\"%dms\" dur=\"%dms\" "
                        + "attributeName=\"y\" from=\"%.0f\" to=\"%.0f\" fill=\"freeze\" />\n",
                    (int)((double)(m.getStartTime() * 1000) / (double)speed),
                    (int)((double)(m.getEndTime() * 1000) / (double)speed)
                        - (int)((double)(m.getStartTime() * 1000) / (double)speed),
                    m.getStartPos().getY() - this.viewModel.getYCanvas(),
                    m.getEndPos().getY() - this.viewModel.getYCanvas());
            result.append(yAttribute);

            String widthAttribute =
                String.format("<animate attributeType=\"xml\" begin=\"%dms\" dur=\"%dms\" "
                        + "attributeName=\"width\" from=\"%d\" to=\"%d\" fill=\"freeze\" />\n",
                    (int)((double)(m.getStartTime() * 1000) / (double)speed),
                    (int)((double)(m.getEndTime() * 1000) / (double)speed)
                        - (int)((double)(m.getStartTime() * 1000) / (double)speed),
                    (int)((double)(m.getStartSize().getWidth()) * 1 / 3),
                    (int)((double) m.getEndSize().getWidth() * 1 / 3));
            result.append(widthAttribute);

            String heightAttribute =
                String.format("<animate attributeType=\"xml\" begin=\"%dms\" dur=\"%dms\" "
                        + "attributeName=\"height\" from=\"%d\" to=\"%d\" fill=\"freeze\" />\n",
                    (int)((double)(m.getStartTime() * 1000) / (double)speed),
                    (int)((double)(m.getEndTime() * 1000) / (double)speed)
                        - (int)((double)(m.getStartTime() * 1000) / (double)speed),
                    m.getStartSize().getHeight(), m.getEndSize().getHeight());
            result.append(heightAttribute);

            String rgbAttribute =
                String.format("<animate attributeType=\"xml\" begin=\"%dms\" dur=\"%dms\" "
                        + "attributeName=\"fill\" from=\"rgb(%d, %d, %d)\" "
                        + "to=\"rgb(%d, %d, %d)\" fill=\"freeze\" />\n",
                    (int)((double)(m.getStartTime() * 1000) / (double)speed),
                    (int)((double)(m.getEndTime() * 1000) / (double)speed)
                        - (int)((double)(m.getStartTime() * 1000) / (double)speed),
                    m.getStartColor().getR(), m.getStartColor().getG(), m.getStartColor().getB(),
                    m.getEndColor().getR(), m.getEndColor().getG(), m.getEndColor().getB());
            result.append(rgbAttribute);

          }

          String closeTagPlus1 = "</rect>";
          result.append(closeTagPlus1);
          result.append("\n\n");

          // second rectangle
          // format open tag
          String openTagPlus2 = String.format("<rect id=\"%sHG\" x=\"%.0f\" y=\"%.0f\" " +
                  "width=\"%d\" height=\"%d\" "
                  + "fill=\"rgb(%d,%d,%d)\" visibility=\"visible\" >\n", name,
              sm.getShape().getPos().getX(),
              sm.getShape().getPos().getY() + ((double)(sm.getShape().getSize().getHeight()) * 1 / 3),
              sm.getShape().getSize().getWidth(), (int)((double)(sm.getShape().getSize().getHeight()) * 1 / 3),
              sm.getShape().getRgb().getR(), sm.getShape().getRgb().getG(),
              sm.getShape().getRgb().getB());
          result.append(openTagPlus2);

          String visibilityAttributePlus2 =
              String.format("<animate attributeType=\"xml\" begin=\"%dms\" dur=\"%dms\" "
                      + "attributeName=\"visibility\" from=\"visible\" to=\"visible\" " +
                      "fill=\"freeze\" />\n",
                  (int)((double)(sm.getStartTime() * 1000) / (double)speed),
                  (int)((double)(sm.getEndTime() * 1000) / (double)speed)
                      - (int)((double)(sm.getStartTime() * 1000) / (double)speed));

          result.append(visibilityAttributePlus2);


          // format 5 animate tags for each motion (1 for each attribute name) + visibility tag
          for (IMotion m : sm.getMotions()) {
            String xAttribute =
                String.format("<animate attributeType=\"xml\" begin=\"%dms\" dur=\"%dms\" "
                        + "attributeName=\"x\" from=\"%.0f\" to=\"%.0f\" fill=\"freeze\" />\n",
                    (int)((double)(m.getStartTime() * 1000) / (double)speed),
                    (int)((double)(m.getEndTime() * 1000) / (double)speed)
                        - (int)((double)(m.getStartTime() * 1000) / (double)speed),
                    m.getStartPos().getX() - this.viewModel.getXCanvas(),
                    m.getEndPos().getX() - this.viewModel.getXCanvas());
            result.append(xAttribute);

            String yAttribute =
                String.format("<animate attributeType=\"xml\" begin=\"%dms\" dur=\"%dms\" "
                        + "attributeName=\"y\" from=\"%.0f\" to=\"%.0f\" fill=\"freeze\" />\n",
                    (int)((double)(m.getStartTime() * 1000) / (double)speed),
                    (int)((double)(m.getEndTime() * 1000) / (double)speed)
                        - (int)((double)(m.getStartTime() * 1000) / (double)speed),
                    m.getStartPos().getY() + ((double)(m.getStartSize().getHeight()) * 1 / 3)
                        - this.viewModel.getYCanvas(),
                    m.getEndPos().getY() + ((double) (m.getEndSize().getHeight()) * 1/3) - this.viewModel.getYCanvas());
            result.append(yAttribute);

            String widthAttribute =
                String.format("<animate attributeType=\"xml\" begin=\"%dms\" dur=\"%dms\" "
                        + "attributeName=\"width\" from=\"%d\" to=\"%d\" fill=\"freeze\" />\n",
                    (int)((double)(m.getStartTime() * 1000) / (double)speed),
                    (int)((double)(m.getEndTime() * 1000) / (double)speed)
                        - (int)((double)(m.getStartTime() * 1000) / (double)speed),
                    m.getStartSize().getWidth(), m.getEndSize().getWidth());
            result.append(widthAttribute);

            String heightAttribute =
                String.format("<animate attributeType=\"xml\" begin=\"%dms\" dur=\"%dms\" "
                        + "attributeName=\"height\" from=\"%d\" to=\"%d\" fill=\"freeze\" />\n",
                    (int)((double)(m.getStartTime() * 1000) / (double)speed),
                    (int)((double)(m.getEndTime() * 1000) / (double)speed)
                        - (int)((double)(m.getStartTime() * 1000) / (double)speed),
                    (int)((double)(m.getStartSize().getHeight()) * 1 / 3),
                    (int)((double)(m.getEndSize().getHeight()) * 1 / 3));
            result.append(heightAttribute);

            String rgbAttribute =
                String.format("<animate attributeType=\"xml\" begin=\"%dms\" dur=\"%dms\" "
                        + "attributeName=\"fill\" from=\"rgb(%d, %d, %d)\" "
                        + "to=\"rgb(%d, %d, %d)\" fill=\"freeze\" />\n",
                    (int)((double)(m.getStartTime() * 1000) / (double)speed),
                    (int)((double)(m.getEndTime() * 1000) / (double)speed)
                        - (int)((double)(m.getStartTime() * 1000) / (double)speed),
                    m.getStartColor().getR(), m.getStartColor().getG(), m.getStartColor().getB(),
                    m.getEndColor().getR(), m.getEndColor().getG(), m.getEndColor().getB());
            result.append(rgbAttribute);

          }

          String closeTagPlus2 = "</rect>";
          result.append(closeTagPlus2);
          result.append("\n\n");
          break;
        case "rectangle" :
          // format open tag
          String openTag = String.format("<rect id=\"%s\" x=\"%.0f\" y=\"%.0f\" " +
                  "width=\"%d\" height=\"%d\" "
                  + "fill=\"rgb(%d,%d,%d)\" visibility=\"visible\" >\n", name,
              sm.getShape().getPos().getX(), sm.getShape().getPos().getY(),
              sm.getShape().getSize().getWidth(), sm.getShape().getSize().getHeight(),
              sm.getShape().getRgb().getR(), sm.getShape().getRgb().getG(),
              sm.getShape().getRgb().getB());
          result.append(openTag);

          String visibilityAttribute =
              String.format("<animate attributeType=\"xml\" begin=\"%dms\" dur=\"%dms\" "
                      + "attributeName=\"visibility\" from=\"visible\" to=\"visible\" " +
                      "fill=\"freeze\" />\n",
                  (int)((double)(sm.getStartTime() * 1000) / (double)speed),
                  (int)((double)(sm.getEndTime() * 1000) / (double)speed)
                      - (int)((double)(sm.getStartTime() * 1000) / (double)speed));

          result.append(visibilityAttribute);


          // format 5 animate tags for each motion (1 for each attribute name) + visibility tag
          for (IMotion m : sm.getMotions()) {
            String xAttribute =
                String.format("<animate attributeType=\"xml\" begin=\"%dms\" dur=\"%dms\" "
                        + "attributeName=\"x\" from=\"%.0f\" to=\"%.0f\" fill=\"freeze\" />\n",
                    (int)((double)(m.getStartTime() * 1000) / (double)speed),
                    (int)((double)(m.getEndTime() * 1000) / (double)speed)
                        - (int)((double)(m.getStartTime() * 1000) / (double)speed),
                    m.getStartPos().getX() - this.viewModel.getXCanvas(),
                    m.getEndPos().getX() - this.viewModel.getXCanvas());
            result.append(xAttribute);

            String yAttribute =
                String.format("<animate attributeType=\"xml\" begin=\"%dms\" dur=\"%dms\" "
                        + "attributeName=\"y\" from=\"%.0f\" to=\"%.0f\" fill=\"freeze\" />\n",
                    (int)((double)(m.getStartTime() * 1000) / (double)speed),
                    (int)((double)(m.getEndTime() * 1000) / (double)speed)
                        - (int)((double)(m.getStartTime() * 1000) / (double)speed),
                    m.getStartPos().getY() - this.viewModel.getYCanvas(),
                    m.getEndPos().getY() - this.viewModel.getYCanvas());
            result.append(yAttribute);

            String widthAttribute =
                String.format("<animate attributeType=\"xml\" begin=\"%dms\" dur=\"%dms\" "
                        + "attributeName=\"width\" from=\"%d\" to=\"%d\" fill=\"freeze\" />\n",
                    (int)((double)(m.getStartTime() * 1000) / (double)speed),
                    (int)((double)(m.getEndTime() * 1000) / (double)speed)
                        - (int)((double)(m.getStartTime() * 1000) / (double)speed),
                    m.getStartSize().getWidth(), m.getEndSize().getWidth());
            result.append(widthAttribute);

            String heightAttribute =
                String.format("<animate attributeType=\"xml\" begin=\"%dms\" dur=\"%dms\" "
                        + "attributeName=\"height\" from=\"%d\" to=\"%d\" fill=\"freeze\" />\n",
                    (int)((double)(m.getStartTime() * 1000) / (double)speed),
                    (int)((double)(m.getEndTime() * 1000) / (double)speed)
                        - (int)((double)(m.getStartTime() * 1000) / (double)speed),
                    m.getStartSize().getHeight(), m.getEndSize().getHeight());
            result.append(heightAttribute);

            String rgbAttribute =
                String.format("<animate attributeType=\"xml\" begin=\"%dms\" dur=\"%dms\" "
                        + "attributeName=\"fill\" from=\"rgb(%d, %d, %d)\" "
                        + "to=\"rgb(%d, %d, %d)\" fill=\"freeze\" />\n",
                    (int)((double)(m.getStartTime() * 1000) / (double)speed),
                    (int)((double)(m.getEndTime() * 1000) / (double)speed)
                        - (int)((double)(m.getStartTime() * 1000) / (double)speed),
                    m.getStartColor().getR(), m.getStartColor().getG(), m.getStartColor().getB(),
                    m.getEndColor().getR(), m.getEndColor().getG(), m.getEndColor().getB());
            result.append(rgbAttribute);

          }

          String closeTag = "</rect>";
          result.append(closeTag);
          result.append("\n\n");
          break;
        case "ellipse":
          String openTagElli = String.format("<ellipse id=\"%s\" cx=\"%.0f\" " +
                  "cy=\"%.0f\" rx=\"%d\" ry=\"%d\" "
                  + "fill=\"rgb(%d,%d,%d)\" visibility=\"visible\" >\n", name,
              sm.getShape().getPos().getX(), sm.getShape().getPos().getY(),
              sm.getShape().getSize().getWidth(), sm.getShape().getSize().getHeight(),
              sm.getShape().getRgb().getR(), sm.getShape().getRgb().getG(),
              sm.getShape().getRgb().getB());
          result.append(openTagElli);

          // visibility tag
          String visibilityTagElli = String.format("<animate attributeType=\"xml\" " +
                  "begin=\"%dms\" dur=\"%dms\" "
                  + "attributeName=\"visibility\" from=\"visible\" to=\"visible\" " +
                  "fill=\"freeze\" />\n",
              (int)((double)(sm.getStartTime() * 1000) / (double)speed),
              (int)((double)(sm.getEndTime() * 1000) / (double)speed)
                  - (int)((double)(sm.getStartTime() * 1000) / (double)speed));
          // append visibility tag to result
          result.append(visibilityTagElli);

          for (IMotion m : sm.getMotions()) {
            // x-coordinate
            String cxAttribute =
                String.format("<animate attributeType=\"xml\" begin=\"%dms\" dur=\"%dms\" "
                        + "attributeName=\"cx\" from=\"%.0f\" to=\"%.0f\" fill=\"freeze\" />\n",
                    (int)((double)(m.getStartTime() * 1000) / (double)speed),
                    (int)((double)(m.getEndTime() * 1000) / (double)speed)
                        - (int)((double)(m.getStartTime() * 1000) / (double)speed),
                    m.getStartPos().getX() - this.viewModel.getXCanvas(),
                    m.getEndPos().getX() - this.viewModel.getXCanvas());
            // append x-coordinate attribution
            result.append(cxAttribute);

            // y-coordinate
            String cyAttribute =
                String.format("<animate attributeType=\"xml\" begin=\"%dms\" dur=\"%dms\" "
                        + "attributeName=\"cy\" from=\"%.0f\" to=\"%.0f\" fill=\"freeze\" />\n",
                    (int)((double)(m.getStartTime() * 1000) / (double)speed),
                    (int)((double)(m.getEndTime() * 1000) / (double)speed)
                        - (int)((double)(m.getStartTime() * 1000) / (double)speed),
                    m.getStartPos().getY() - this.viewModel.getYCanvas(),
                    m.getEndPos().getY() - this.viewModel.getYCanvas());
            // append y-coordinate attribution
            result.append(cyAttribute);

            // append width coordinate
            String rxAttribute =
                String.format("<animate attributeType=\"xml\" begin=\"%dms\" dur=\"%dms\" "
                        + "attributeName=\"rx\" from=\"%d\" to=\"%d\" fill=\"freeze\" />\n",
                    (int)((double)(m.getStartTime() * 1000) / (double)speed),
                    (int)((double)(m.getEndTime() * 1000) / (double)speed)
                        - (int)((double)(m.getStartTime() * 1000) / (double)speed),
                    (int)((double) m.getStartSize().getWidth() / (double) 2),
                    (int)((double) m.getEndSize().getWidth() / (double) 2));
            result.append(rxAttribute);

            // height value
            String ryAttribute =
                String.format("<animate attributeType=\"xml\" begin=\"%dms\" dur=\"%dms\" "
                        + "attributeName=\"ry\" from=\"%d\" to=\"%d\" fill=\"freeze\" />\n",
                    (int)((double)(m.getStartTime() * 1000) / (double)speed),
                    (int)((double)(m.getEndTime() * 1000) / (double)speed)
                        - (int)((double)(m.getStartTime() * 1000) / (double)speed),
                    (int)((double)(m.getStartSize().getHeight()) / (double)2),
                    (int)((double)(m.getEndSize().getHeight()) / (double)2));
            result.append(ryAttribute);

            String rgbAttributeElli =
                String.format("<animate attributeType=\"xml\" begin=\"%dms\" dur=\"%dms\" "
                        + "attributeName=\"fill\" from=\"rgb(%d, %d, %d)\" "
                        + "to=\"rgb(%d, %d, %d)\" fill=\"freeze\" />\n",
                    (int)((double)(m.getStartTime() * 1000) / (double)speed),
                    (int)((double)(m.getEndTime() * 1000) / (double)speed)
                        - (int)((double)(m.getStartTime() * 1000) / (double)speed),
                    m.getStartColor().getR(), m.getStartColor().getG(), m.getStartColor().getB(),
                    m.getEndColor().getR(), m.getEndColor().getG(), m.getEndColor().getB());
            result.append(rgbAttributeElli);
          }

          String closeTagElli = "</ellipse>";
          result.append(closeTagElli);
          result.append("\n\n");
          break;
        default:
          throw new IllegalArgumentException("Shape not found");
      }
    }
    return result.toString();
  }
}

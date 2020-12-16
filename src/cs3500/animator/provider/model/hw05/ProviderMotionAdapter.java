package cs3500.animator.provider.model.hw05;

import cs3500.animator.model.IMotion;
import cs3500.animator.model.Motion;
import cs3500.animator.model.shape.Position2D;
import cs3500.animator.model.shape.Rgb;
import cs3500.animator.model.shape.Size2D;

/**
 * We needed to create an adaptor for our code to work with our provider's code so that
 * we could get their views to work with our code. To do so, we made this adaptor class
 * implement their motion interface but created a delegate of our IMotion interface so that
 * we were able to implement all of their methods by calling our methods. In other words, we
 * created a class that looks like their code but is actually our code.
 */
public class ProviderMotionAdapter implements IAnimationMove {
  IMotion motion;
  String name;

  /**
   * Since the animation builder contains an add motion method that takes in all of the
   * motion features, we had to create this constructor to pass in and initialize a new Motion
   * using all of these details.
   * @param name name of shape
   * @param start start time of animation
   * @param x1 initial x coordinate of animation
   * @param y1 initial y coordinate of animation
   * @param width1 initial width of animation
   * @param height1 initial height of animation
   * @param r1 initial r of RGB color of animation
   * @param g1 initial g of RGB color of animation
   * @param b1 initial  of RGB color of animation
   * @param end end time of animation
   * @param x2 end x coordinate of animation
   * @param y2 end y coordinate of animation
   * @param width2 end width of animation
   * @param height2 end height of animation
   * @param r2 end r of RGB color of animation
   * @param g2 end g of RGB color of animation
   * @param b2 end b of RGB color of animation
   */
  public ProviderMotionAdapter(String name, int start, int x1, int y1, int width1,
      int height1, int r1, int g1, int b1, int end, int x2, int y2, int width2,
                               int height2, int r2, int g2, int b2) {
    this.motion = new Motion(name, start, new Position2D(x1, y1),
      new Size2D(width1, height1), new Rgb(r1, g1, b1),
        end, new Position2D(x2, y2), new Size2D(width2, height2), new Rgb(r2,g2,b2));
    this.name = name;
  }

  public ProviderMotionAdapter(String name, IMotion motion) {
    this.motion = motion;
    this.name = name;
  }


  @Override
  public int getStartTick() {
    return motion.getStartTime();
  }

  @Override
  public int getEndTick() {
    return motion.getEndTime();
  }

  @Override
  public int getX() {
    return (int)this.motion.getEndPos().getX() - (int)this.motion.getStartPos().getX();
  }

  @Override
  public int getY() {
    return (int)this.motion.getEndPos().getY() - (int)this.motion.getStartPos().getY();
  }

  @Override
  public int getW() {
    return motion.getEndSize().getWidth() - motion.getStartSize().getWidth();
  }

  @Override
  public int getH() {
    return motion.getEndSize().getHeight() - motion.getStartSize().getHeight();
  }

  @Override
  public int getR() {
    return motion.getEndColor().getR() - motion.getStartColor().getR();
  }

  @Override
  public int getG() {
    return motion.getEndColor().getG() - motion.getStartColor().getG();
  }

  @Override
  public int getB() {
    return motion.getEndColor().getB() - motion.getStartColor().getB();
  }

  @Override
  public int compareTo(IAnimationMove o) {
    return 0;
  }


  @Override
  public Position2D getStartPos() {
    return this.motion.getStartPos();
  }

  @Override
  public Position2D getEndPos() {
    return this.motion.getEndPos();
  }

  @Override
  public Size2D getStartSize() {
    return this.motion.getStartSize();
  }

  @Override
  public Size2D getEndSize() {
    return this.motion.getEndSize();
  }

  @Override
  public Rgb getStartColor() {
    return this.motion.getStartColor();
  }

  @Override
  public Rgb getEndColor() {
    return this.motion.getEndColor();
  }
}

package cs3500.animator.view;

import cs3500.animator.model.IShapeAndMotion;
import cs3500.animator.model.IViewModel;
import cs3500.animator.model.shape.IShape2D;
import cs3500.animator.model.shape.Plus;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Stroke;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import java.util.List;
import java.util.Map;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class InteractivePanelPlus extends InteractivePanel implements IInteractivePanel {
  protected boolean isOutline;
  protected int savedSpeed;
  protected boolean isDiscrete;
  protected Integer[] discreteTicks;
  protected int idx;


  public InteractivePanelPlus(IViewModel viewModel, int speed) {
    super(speed, viewModel);
    this.savedSpeed = this.speed;
    this.isOutline = false;
    this.isDiscrete = false;
    this.discreteTicks = this.viewModel.frameTicks();
    this.idx = 0;
  }

  @Override
  protected void updateTick() {
    if (this.currentTick <= this.discreteTicks[0] && (this.idx == this.discreteTicks.length)) {
      this.idx = 0;
    }

    if (isDiscrete) {
      System.out.println(String.format("tick was %d, when idx was %d", this.currentTick, this.idx));

      if (idx < this.discreteTicks.length) {
        while (this.currentTick > this.discreteTicks[idx]) {
          this.idx += 1;
        }

        this.currentTick = this.discreteTicks[idx];
        this.idx += 1;
      }
      else {
        this.currentTick += 1;
      }
    }
    else {
      this.currentTick += 1;
    }
  }

  @Override
  public void discreteFramePanel() {
    this.isDiscrete = !this.isDiscrete;
  }


  @Override
  public void outlineShapesPanel() {
    this.isOutline = !this.isOutline;
  }

  @Override
  public void paintComponent(Graphics g) {
    Graphics2D g2 = (Graphics2D) g;
    Map<String, IShapeAndMotion> allSM = this.viewModel.getInstructions();
    for (String name : allSM.keySet()) {
      IShapeAndMotion sm = allSM.get(name);

      IShape2D shape = sm.getShape();

      switch (shape.shapeName()) {
        case "rectangle":
          if ((sm.getStartTime() <= this.currentTick) && (sm.getEndTime() >= this.currentTick)) {
            Rectangle2D rect = new Rectangle2D.Double((shape.getPos().getX()
                - (double)this.cornerX),
                ((shape.getPos().getY() - (double)this.cornerY)),
                shape.getSize().getWidth(), shape.getSize().getHeight());
            g2.setColor(new Color(shape.getRgb().getR(), shape.getRgb().getG(),
                shape.getRgb().getB()));
            if (this.isOutline) {
              g2.setStroke(new BasicStroke(3));
              g2.draw(rect);
            }
            else {
              g2.fill(rect);
            }
          }
          break;
        case "ellipse":
          if ((sm.getStartTime() <= this.currentTick) && (sm.getEndTime() >= this.currentTick)) {
            Ellipse2D elli = new Ellipse2D.Double(shape.getPos().getX() - (double)this.cornerX,
                ((shape.getPos().getY() - (double)this.cornerY)), shape.getSize().getWidth(),
                shape.getSize().getHeight());
            g2.setColor(new Color(shape.getRgb().getR(), shape.getRgb().getG(),
                shape.getRgb().getB()));
            if (this.isOutline) {
              g2.setStroke(new BasicStroke(3));
              g2.draw(elli);
            } else {
              g2.fill(elli);
            }
          }
          break;
        case "plus":
          if ((sm.getStartTime() <= this.currentTick) && (sm.getEndTime() >= this.currentTick)) {
            if (!this.isOutline) {
                Rectangle2D rectV = new Rectangle2D.Double(shape.getPos().getX() - (double) this.cornerX
                  + ((double) (shape.getSize().getWidth()) / 3),
                  ((shape.getPos().getY() - (double) this.cornerY)),
                  (int) ((double) (shape.getSize().getWidth()) / 3),
                  shape.getSize().getHeight());
                Rectangle2D rectH = new Rectangle2D.Double(
                  shape.getPos().getX() - (double) this.cornerX,
                  ((shape.getPos().getY() - (double) this.cornerY) + (
                      (double) (shape.getSize().getHeight()) / 3)),
                  shape.getSize().getWidth(),
                  (int) ((double) (shape.getSize().getHeight()) / 3));
                g2.setColor(new Color(shape.getRgb().getR(), shape.getRgb().getG(),
                  shape.getRgb().getB()));
                g2.fill(rectV);
                g2.fill(rectH);
            }
            else {
              Line2D line1 = new Line2D.Double(shape.getPos().getX() - (double) this.cornerX
                  + ((double) (shape.getSize().getWidth()) / 3),
                  ((shape.getPos().getY() - (double) this.cornerY)),
                  shape.getPos().getX() - (double) this.cornerX
                      + ((double) (shape.getSize().getWidth()) * 2 / 3),
                  ((shape.getPos().getY() - (double) this.cornerY)));
              Line2D line2 = new Line2D.Double(shape.getPos().getX() - (double) this.cornerX
                  + ((double) (shape.getSize().getWidth()) * 2 / 3),
                  ((shape.getPos().getY() - (double) this.cornerY)),
                  shape.getPos().getX() - (double) this.cornerX
                      + ((double) (shape.getSize().getWidth()) * 2 / 3),
                  ((shape.getPos().getY() - (double) this.cornerY))
                      + ((double) (shape.getSize().getHeight()) * 1 / 3));
              Line2D line3 = new Line2D.Double(shape.getPos().getX() - (double) this.cornerX
                  + ((double) (shape.getSize().getWidth()) * 2 / 3),
                  ((shape.getPos().getY() - (double) this.cornerY)
                      + ((double) (shape.getSize().getHeight()) * 1 / 3)),
                  shape.getPos().getX() - (double) this.cornerX
                      + ((double) (shape.getSize().getWidth())),
                  ((shape.getPos().getY() - (double) this.cornerY))
                      + ((double) (shape.getSize().getHeight()) * 1 / 3));
              Line2D line4 = new Line2D.Double(shape.getPos().getX() - (double) this.cornerX
                  + ((double) (shape.getSize().getWidth()) * 3 / 3),
                  ((shape.getPos().getY() - (double) this.cornerY)
                      + ((double) (shape.getSize().getHeight()) * 1 / 3)),
                  shape.getPos().getX() - (double) this.cornerX
                      + ((double) (shape.getSize().getWidth()) * 3 / 3),
                  ((shape.getPos().getY() - (double) this.cornerY))
                      + ((double) (shape.getSize().getHeight()) * 2 / 3));
              Line2D line5 = new Line2D.Double(shape.getPos().getX() - (double) this.cornerX
                  + ((double) (shape.getSize().getWidth()) * 2 / 3),
                  ((shape.getPos().getY() - (double) this.cornerY)
                      + ((double) (shape.getSize().getHeight()) * 2 / 3)),
                  shape.getPos().getX() - (double) this.cornerX
                      + ((double) (shape.getSize().getWidth()) * 3 / 3),
                  ((shape.getPos().getY() - (double) this.cornerY))
                      + ((double) (shape.getSize().getHeight()) * 2 / 3));
              Line2D line6 = new Line2D.Double(shape.getPos().getX() - (double) this.cornerX
                  + ((double) (shape.getSize().getWidth()) * 2 / 3),
                  ((shape.getPos().getY() - (double) this.cornerY)
                      + ((double) (shape.getSize().getHeight()) * 2 / 3)),
                  shape.getPos().getX() - (double) this.cornerX
                      + ((double) (shape.getSize().getWidth()) * 2 / 3),
                  ((shape.getPos().getY() - (double) this.cornerY))
                      + ((double) (shape.getSize().getHeight()) * 3 / 3));
              Line2D line7 = new Line2D.Double(shape.getPos().getX() - (double) this.cornerX
                  + ((double) (shape.getSize().getWidth()) * 1 / 3),
                  ((shape.getPos().getY() - (double) this.cornerY)
                      + ((double) (shape.getSize().getHeight()) * 3 / 3)),
                  shape.getPos().getX() - (double) this.cornerX
                      + ((double) (shape.getSize().getWidth()) * 2 / 3),
                  ((shape.getPos().getY() - (double) this.cornerY))
                      + ((double) (shape.getSize().getHeight()) * 3 / 3));
              Line2D line8 = new Line2D.Double(shape.getPos().getX() - (double) this.cornerX
                  + ((double) (shape.getSize().getWidth()) * 1 / 3),
                  ((shape.getPos().getY() - (double) this.cornerY)
                      + ((double) (shape.getSize().getHeight()) * 2 / 3)),
                  shape.getPos().getX() - (double) this.cornerX
                      + ((double) (shape.getSize().getWidth()) * 1 / 3),
                  ((shape.getPos().getY() - (double) this.cornerY))
                      + ((double) (shape.getSize().getHeight()) * 3 / 3));
              Line2D line9 = new Line2D.Double(shape.getPos().getX() - (double) this.cornerX
                  + ((double) (shape.getSize().getWidth()) * 0 / 3),
                  ((shape.getPos().getY() - (double) this.cornerY)
                      + ((double) (shape.getSize().getHeight()) * 2 / 3)),
                  shape.getPos().getX() - (double) this.cornerX
                      + ((double) (shape.getSize().getWidth()) * 1 / 3),
                  ((shape.getPos().getY() - (double) this.cornerY))
                      + ((double) (shape.getSize().getHeight()) * 2 / 3));
              Line2D line10 = new Line2D.Double(shape.getPos().getX() - (double) this.cornerX
                  + ((double) (shape.getSize().getWidth()) * 0 / 3),
                  ((shape.getPos().getY() - (double) this.cornerY)
                      + ((double) (shape.getSize().getHeight()) * 1 / 3)),
                  shape.getPos().getX() - (double) this.cornerX
                      + ((double) (shape.getSize().getWidth()) * 0 / 3),
                  ((shape.getPos().getY() - (double) this.cornerY))
                      + ((double) (shape.getSize().getHeight()) * 2 / 3));
              Line2D line11 = new Line2D.Double(shape.getPos().getX() - (double) this.cornerX
                  + ((double) (shape.getSize().getWidth()) * 0 / 3),
                  ((shape.getPos().getY() - (double) this.cornerY)
                      + ((double) (shape.getSize().getHeight()) * 1 / 3)),
                  shape.getPos().getX() - (double) this.cornerX
                      + ((double) (shape.getSize().getWidth()) * 1 / 3),
                  ((shape.getPos().getY() - (double) this.cornerY))
                      + ((double) (shape.getSize().getHeight()) * 1 / 3));
              Line2D line12 = new Line2D.Double(shape.getPos().getX() - (double) this.cornerX
                  + ((double) (shape.getSize().getWidth()) * 1 / 3),
                  ((shape.getPos().getY() - (double) this.cornerY)
                      + ((double) (shape.getSize().getHeight()) * 0 / 3)),
                  shape.getPos().getX() - (double) this.cornerX
                      + ((double) (shape.getSize().getWidth()) * 1 / 3),
                  ((shape.getPos().getY() - (double) this.cornerY))
                      + ((double) (shape.getSize().getHeight()) * 1 / 3));
              g2.setColor(new Color(shape.getRgb().getR(), shape.getRgb().getG(),
                  shape.getRgb().getB()));

              g2.setStroke(new BasicStroke(3));

              g2.draw(line1);
              g2.draw(line2);
              g2.draw(line3);
              g2.draw(line4);
              g2.draw(line5);
              g2.draw(line6);
              g2.draw(line7);
              g2.draw(line8);
              g2.draw(line9);
              g2.draw(line10);
              g2.draw(line11);
              g2.draw(line12);

            }
          }
          break;
        default:
          JFrame frame = new JFrame();
          JOptionPane.showInternalMessageDialog(frame,
              String.format("%s is not a supported shape type!", shape.shapeName()),
              "Error Message", JOptionPane.ERROR_MESSAGE);
          System.exit(0);
      }
    }
  }

  @Override
  protected void updateSlowMoSpeed() {

    if (this.viewModel.isSlowMoTick(this.currentTick) == 0) {
      this.savedSpeed = this.speed;
      this.speed = 1;
    }
    else if (this.viewModel.isSlowMoTick(this.currentTick) == 1) {
      this.speed = 1;
    }
    else {
      if (this.viewModel.isSlowMoTick(this.currentTick - 1) == 1) {
        this.speed = this.savedSpeed;
      }
    }

    this.updateTimer();
  }

  @Override
  public void rewind() {
    super.rewind();
    this.idx = 0;
  }
}





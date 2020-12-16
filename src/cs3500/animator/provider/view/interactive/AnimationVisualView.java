package cs3500.animator.provider.view.interactive;

import cs3500.animator.provider.model.hw05.IAnimationModel;
import cs3500.animator.provider.view.Features;
import cs3500.animator.provider.view.IAnimationView;

import javax.swing.JScrollPane;
import javax.swing.Timer;
import javax.swing.JFrame;
import java.awt.Dimension;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;

/**
 * Represents the visual view of the animation.
 * Receives a model and display model's shapes and motions.
 */
public class AnimationVisualView extends JFrame implements IAnimationView {
  private final IAnimationModel model;
  protected ShapesPanel shapesPanel;
  protected JScrollPane animationScrollPane;
  private int tick = 0;

  /**
   * Construct the visual view using the given model.
   */
  public AnimationVisualView(IAnimationModel animationModel) {
    super();
    this.model = animationModel;
    setSize(animationModel.getBoundingWidth(), animationModel.getBoundingHeight());
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setLocationRelativeTo(null);
    this.setLayout(new BorderLayout(5, 5));

    this.shapesPanel = new ShapesPanel();
    this.shapesPanel.setSize(this.model.getBoundingWidth(), this.model.getBoundingHeight());
    this.shapesPanel.setBackground(Color.WHITE);

    this.animationScrollPane = new JScrollPane(shapesPanel);
    this.animationScrollPane.setSize(this.model.getBoundingWidth(), this.model.getBoundingHeight());
    this.add(animationScrollPane, BorderLayout.CENTER);
    this.setVisible(true);
  }

  @Override
  public void updateSpeed(int speed) {
    throw new UnsupportedOperationException("VisualView does not support update speed");
  }

  @Override
  public void updateShapesAtTick(int tick) {
    this.shapesPanel.updateShapes(this.model.getShapesAtTick(tick));
    this.repaint();
  }

  @Override
  public void render(Appendable out, int speed) {
    Timer timer = new Timer((int) (1000.0 / speed), (ActionEvent action) -> this.timerStart());
    timer.start();
  }

  private void timerStart() {
    if (tick >= 0 && tick < model.getAnimationLength()) {
      this.updateShapesAtTick(tick);
      tick ++;
    } else {
      System.exit(0);
    }
  }

  @Override
  public Dimension getPreferredSize() {
    return new Dimension(this.model.getBoundingWidth(),
            this.model.getBoundingHeight());
  }

  @Override
  public void addFeatures(Features features) {
    // no features to add
  }
}



package cs3500.animator.provider.view.interactive;

import cs3500.animator.provider.model.hw05.IAnimationModel;
import cs3500.animator.provider.view.Features;
import cs3500.animator.provider.view.IAnimationView;
import cs3500.animator.provider.view.interactive.AnimationVisualView;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.BoxLayout;
import java.awt.Dimension;
import java.awt.BorderLayout;

/**
 * Represents the interactive view of the animation.
 * Extends AnimationVisualView and adds additional features and visual elements
 * to allow user interactions.
 */
public class AnimationInteractiveView extends AnimationVisualView implements IAnimationView {
  private JButton playButton;
  private JButton pauseButton;
  private JButton resumeButton;
  private JButton restartButton;
  private JButton speedUpButton;
  private JButton slowDownButton;
  private JCheckBox loopCheckbox;
  private JLabel speedLable;
  private boolean isPaused = true;

  /**
   * Construct the interactive view using the given model.
   */
  public AnimationInteractiveView(IAnimationModel model) {
    super(model);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setLocationRelativeTo(null);
    this.setLayout(new BorderLayout(5, 5));

    JPanel buttonPanel = new JPanel();
    buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.X_AXIS));
    playButton = new JButton("Play");
    buttonPanel.add(playButton);
    pauseButton = new JButton("Pause");
    buttonPanel.add(pauseButton);
    pauseButton.setEnabled(false);
    resumeButton = new JButton("Resume");
    buttonPanel.add(resumeButton);
    resumeButton.setEnabled(false);
    restartButton = new JButton("Restart");
    buttonPanel.add(restartButton);
    restartButton.setEnabled(false);
    loopCheckbox = new JCheckBox("Loop");
    buttonPanel.add(loopCheckbox);
    speedUpButton = new JButton("Speed up");
    buttonPanel.add(speedUpButton);
    slowDownButton = new JButton("Slow down");
    buttonPanel.add(slowDownButton);
    speedLable = new JLabel("Speed");
    buttonPanel.add(speedLable);
    this.add(buttonPanel, BorderLayout.NORTH);

    setPreferredSize(new Dimension(
            model.getBoundingWidth() + buttonPanel.getWidth(),
            model.getBoundingHeight() + buttonPanel.getHeight()));
    setSize(new Dimension(
            model.getBoundingWidth() + buttonPanel.getWidth() + 125,
            model.getBoundingHeight() + buttonPanel.getHeight() + 75));

    this.remove(this.animationScrollPane);
    this.animationScrollPane.setLocation(0, 50);
    this.add(this.animationScrollPane);
    this.setVisible(true);
  }

  @Override
  public void updateSpeed(int speed) {
    speedLable.setText("Speed: " + speed);
  }

  @Override
  public void render(Appendable out, int speed) {
    updateSpeed(speed);
    updateShapesAtTick(1);
  }

  @Override
  public void addFeatures(Features features) {
    playButton.addActionListener(evt -> {
      features.play();
      playButton.setEnabled(false);
      pauseButton.setEnabled(true);
      resumeButton.setEnabled(true);
      restartButton.setEnabled(true);
      isPaused = false;
    });
    pauseButton.addActionListener(evt -> {
      if (!isPaused) {
        features.pause();
        pauseButton.setEnabled(false);
        resumeButton.setEnabled(true);
        restartButton.setEnabled(true);
      }
    });
    resumeButton.addActionListener(evt -> {
      if (!isPaused) {
        features.resume();
        resumeButton.setEnabled(false);
        restartButton.setEnabled(true);
        pauseButton.setEnabled(true);
      }
    });
    restartButton.addActionListener(evt -> {
      features.restart();
      playButton.setEnabled(false);
      pauseButton.setEnabled(true);
      resumeButton.setEnabled(false);
      restartButton.setEnabled(true);
    });
    loopCheckbox.addActionListener(evt -> features.loop());
    speedUpButton.addActionListener(evt -> features.speedUp());
    slowDownButton.addActionListener(evt -> features.slowDown());
  }
}

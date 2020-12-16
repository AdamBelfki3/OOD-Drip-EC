package cs3500.animator.view;

import cs3500.animator.controller.Features;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class InteractiveFramePlus extends InteractiveFrame {
  private JButton outline = new JButton("Outline");
  private JButton discrete = new JButton("Discrete");

  public InteractiveFramePlus(InteractivePanelPlus panel, int width, int height)  {
    super(Objects.requireNonNull(panel), width, height);
  }


  @Override
  public void createFrame() {
    JScrollPane scroll = new JScrollPane(this.panel,
        JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);

    this.add(scroll, BorderLayout.CENTER);
    start.setUI(new PlayButtonUI());
    JPanel panel = new JPanel();

    // set action command
    this.start.setActionCommand("start");
    panel.add(start);

    // set action command
    this.fastBackward.setActionCommand("fast backward");
    panel.add(this.fastBackward);

    // set action command
    this.resume.setActionCommand("resume");
    panel.add(resume);

    // set action command
    this.pause.setActionCommand("pause");
    panel.add(pause);

    // set action command
    this.fastForward.setActionCommand("fast forward");
    panel.add(this.fastForward);

    // set action command
    this.restart.setActionCommand("restart");
    panel.add(restart);

    // set action command
    this.looper.setActionCommand("loop");
    panel.add(this.looper);

    // set action command
    this.outline.setActionCommand("outline");
    panel.add(this.outline);

    // set action command
    discrete.setActionCommand("discrete");
    panel.add(this.discrete);

    Dimension d = panel.getPreferredSize();

    this.add(panel, BorderLayout.SOUTH);

    // title
    this.setTitle("Adam & Mirana's Animation");
    // frame size

    int ww = Math.max(this.width, (int)d.getWidth());
    if (ww == (int)d.getWidth()) {
      ww += 50;
    }

    this.setSize(ww, this.height + (int)d.getHeight());
    // frame close
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    // so that the keys are able to work
    start.setFocusable(false);
    restart.setFocusable(false);
    resume.setFocusable(false);
    pause.setFocusable(false);
    fastForward.setFocusable(false);
    fastBackward.setFocusable(false);
    this.looper.setFocusable(false);
    this.outline.setFocusable(false);
    this.discrete.setFocusable(false);
  }

  @Override
  public void addActions(ActionListener actionListener) {
    super.addActions(actionListener);
    this.outline.addActionListener(actionListener);
    this.discrete.addActionListener(actionListener);
  }
}

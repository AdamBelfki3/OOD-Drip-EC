package cs3500.animator.provider.view.interactive;

import cs3500.animator.provider.model.hw05.shape.DTOAbstractShape;
import cs3500.animator.provider.model.hw05.shape.DTOEllipseShape;
import cs3500.animator.provider.model.hw05.shape.DTORectangleShape;

import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Shape;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Dimension;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Represents the Shapes Panel that extends from JPanel.
 */
public class ShapesPanel extends JPanel {

  /**
   * A static class that represents a colored shape.
   */
  public static class ColoredShape {
    private Shape shape;
    private Color color;

    public ColoredShape(Shape shape, Color color) {
      this.shape = shape;
      this.color = color;
    }

    @Override
    public boolean equals(Object obj) {
      if (!(obj instanceof  ColoredShape)) {
        return false;
      }
      ColoredShape other = (ColoredShape) obj;
      return this.shape.equals(other.shape) && this.color.equals(other.color);
    }

    @Override
    public int hashCode() {
      return this.shape.hashCode() + this.color.hashCode();
    }
  }

  private Map<String, ColoredShape> shapeHashMap = new LinkedHashMap<>();

  public ShapesPanel() {
    super();
  }

  /**
   * Get the color of the given Shape.
   * @param aShape  the given shape.
   */
  public ShapesPanel.ColoredShape getColoreredShape(DTOAbstractShape aShape) {
    Shape shape = null;
    if (aShape instanceof DTORectangleShape) {
      shape = new Rectangle2D.Double(aShape.getX(), aShape.getY(), aShape.getW(), aShape.getH());
    }
    else if (aShape instanceof DTOEllipseShape) {
      shape = new Ellipse2D.Double(aShape.getX(), aShape.getY(), aShape.getW(), aShape.getH());
    }
    ShapesPanel.ColoredShape cShape = new ShapesPanel.ColoredShape(shape,
            new Color((int)aShape.getR(), (int)aShape.getG(), (int)aShape.getB()));
    return cShape;
  }

  /**
   * Replaces {@code shapeHashMap}.
   * @param shapes  the given shapes to replace the current ones.
   */
  public void updateShapes(List<DTOAbstractShape> shapes) {
    this.shapeHashMap.clear();
    for (DTOAbstractShape shape : shapes) {
      this.shapeHashMap.put(shape.getName(), getColoreredShape(shape));
    }
  }

  @Override
  public void paintComponent(Graphics g) {
    super.paintComponent(g);
    Graphics2D g2d = (Graphics2D)g;
    for (String shapeName : this.shapeHashMap.keySet()) {
      ColoredShape cShape = this.shapeHashMap.get(shapeName);
      g2d.setColor(cShape.color);
      g2d.fill(cShape.shape);
    }
  }

  @Override
  public Dimension getPreferredSize() {
    return new Dimension(this.getWidth(), this.getHeight());
  }
}

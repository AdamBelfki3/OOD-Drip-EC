package cs3500.animator.provider.util;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Generates an animation of the building of a BinarySearchTree.
 * Represents the algorithm used to add new nodes to a BST
 * The nodes of the BinarySearchTree are passed in as integers to the constructor.
 */
public class BinarySearchTreeGenerator implements AnimationGenerator {

  /**
   * Represents a node in the BinarySearchTree.
   * It includes some extra information necessary for drawing.
   */
  public static class Node {
    protected int value;
    protected Node left = null;
    protected Node right = null;
    protected int level = 0;
    protected int rowPosition = 0;
    protected int xLocation = 0;
    protected int yLocation = 0;

    /**
     * Constructor that makes a node out of a value.
     * @param value the value of the node.
     */
    public Node(int value) {
      this.value = value;
    }

    /**
     * Returns a unique name for each node.
     * @return a name of the node.
     */
    public String getShapeName() {
      return this.level + ":" + this.rowPosition;
    }
  }

  protected Node root = null;
  private int height = 20;
  private int width = 20;
  private List<Node> nodeList = new ArrayList<>();
  private int largestNode = 0;
  private int numLevels = 0;
  private int canvasWidth = 0;
  private int canvasHeight = 0;
  private int newShapeDelay = 50;
  private int shapeMoveSpeed = 50;

  /**
   * Constructor that creates a BinarySearchTree out of the given list of integers.
   * @param values the values to be added to the tree.
   */
  public BinarySearchTreeGenerator(List<Integer> values) {
    for (Integer node : values) {
      this.addNode(node);
    }
  }

  @Override
  public void generateAnimation(String file) throws IllegalStateException {
    if (file == null) {
      throw new IllegalArgumentException("No file defined.");
    }
    try {
      FileWriter writer = new FileWriter(file);
      writer.append(this.getAnimationSpecificationString());
      writer.close();
    } catch (IOException e) {
      throw new IllegalArgumentException("Was unable to write to file.");
    }
  }

  /**
   * Generates the String that represents the animation for building the BinarySearchTree.
   * @return the animation specification
   */
  public String getAnimationSpecificationString() {
    this.calculateWidth();
    this.calculateHeight();
    this.assignXY(this.root);

    StringBuilder stringBuilder = new StringBuilder();

    String canvasString = getCanvasString();
    stringBuilder.append(canvasString).append("\n");
    int tick = 0;
    int totalLength = this.nodeList.size() * (this.shapeMoveSpeed + this.newShapeDelay);
    for (Node node : this.nodeList) {
      String moveStr = getNodeMovementString(node, tick, tick + this.shapeMoveSpeed, totalLength);
      stringBuilder.append(moveStr).append("\n");
      tick = tick + this.shapeMoveSpeed + this.newShapeDelay;
    }
    return stringBuilder.toString();
  }

  /**
   * Gets the canvas specification string.
   * @return the canvas representation string
   */
  private String getCanvasString() {
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("canvas 0 0 ");
    stringBuilder.append(this.canvasWidth).append(" ");
    stringBuilder.append(this.canvasHeight).append(" ");
    return stringBuilder.toString();
  }

  /**
   * Gets a String representing an animation for a node.
   * @param node the node to generate the animation file
   * @param start the start tick
   * @param end the end tick
   * @param totalLength the total length of the animation
   * @return String representing the animation for the node
   */
  private String getNodeMovementString(Node node, int start, int end, int totalLength) {
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("shape ").append(node.getShapeName()).append(" ellipse").append("\n");

    stringBuilder.append("motion ").append(node.getShapeName()).append(" ");
    stringBuilder.append(start).append(" ");
    stringBuilder.append(0).append(" ").append(0).append(" ");
    stringBuilder.append(node.value).append(" ").append(node.value).append(" ");
    stringBuilder.append("100 100 100 ");

    stringBuilder.append(end).append(" ");
    stringBuilder.append(node.xLocation).append(" ").append(node.yLocation).append(" ");
    stringBuilder.append(node.value).append(" ").append(node.value).append(" ");
    stringBuilder.append("100 100 100").append("\n");

    stringBuilder.append("motion ").append(node.getShapeName()).append(" ");
    stringBuilder.append(end).append(" ");
    stringBuilder.append(node.xLocation).append(" ").append(node.yLocation).append(" ");
    stringBuilder.append(node.value).append(" ").append(node.value).append(" ");
    stringBuilder.append("100 100 100 ");

    stringBuilder.append(totalLength).append(" ");
    stringBuilder.append(node.xLocation).append(" ").append(node.yLocation).append(" ");
    stringBuilder.append(node.value).append(" ").append(node.value).append(" ");
    stringBuilder.append("100 100 100");
    return stringBuilder.toString();
  }

  /**
   * Adds a node to the BinarySearchTree recursively.
   * @param value the value to be added to the tree
   * @throws IllegalArgumentException thrown if the value is less than 1
   */
  private void addNode(int value) throws IllegalArgumentException {
    if (value < 1) {
      throw new IllegalArgumentException("Values must be 1 or greater.");

    }
    if (value > this.largestNode) {
      this.largestNode = value;
    }
    this.root = this.addNode(this.root, value, 1, 1);
  }

  /**
   * Helper function that creates the node in the proper place in the BinarySearchTree.
   * @param parentNode the parent node where this node should be added to
   * @param value the value of the new node
   * @param level the current level where the node is to be inserted
   * @param position the position of the parent node
   * @return returns the top node where the new node was added.
   */
  private Node addNode(Node parentNode, int value, int level, int position) {
    if (parentNode == null) {
      if (level > this.numLevels) {
        this.numLevels = level;
      }
      Node node = new Node(value);
      node.level = level;
      node.rowPosition = position;
      nodeList.add(node);

      return node;
    }
    if (value >= parentNode.value) {
      parentNode.right = addNode(parentNode.right, value, level + 1, position * 2);
    } else if (value < parentNode.value) {
      parentNode.left = addNode(parentNode.left, value, level + 1, position * 2 - 1);
    }
    return parentNode;
  }

  /**
   * Calculates the width of the entire BinarySearchTree.
   * @return the width of the BinarySearchTree
   */
  protected int calculateWidth() {
    int spotsNeeded = (int) (Math.pow(2, this.numLevels - 1) * 2 - 1);
    this.canvasWidth = spotsNeeded * this.largestNode;
    return this.canvasWidth;
  }

  /**
   *  Calculates the height of the entire BinarySearchTree.
   *  @return the height of the BinarySearchTree
   */
  private int calculateHeight() {
    this.canvasHeight = this.numLevels * this.largestNode;
    return this.canvasHeight;
  }

  /**
   * Recursively assigns a calculated x and y location for each node in the tree.
   * @param node the node where to start assigning x and y location.
   */
  protected void assignXY(Node node) {
    if (node == null) {
      return;
    }
    node.xLocation = (int) ((node.rowPosition / (Math.pow(2, node.level - 1) + 1))
            * this.canvasWidth);
    node.yLocation = node.level * this.largestNode;
    assignXY(node.left);
    assignXY(node.right);
  }

  /**
   * Generates an example BinarySearchTree animation file.
   * @param args arguments passed into the main
   */
  public static void main(String[] args) {
    List<Integer> params = new ArrayList<>();
    params.add(30);
    params.add(40);
    params.add(20);
    params.add(50);
    params.add(55);
    params.add(45);
    params.add(15);
    params.add(10);
    AnimationGenerator animation = new BinarySearchTreeGenerator(params);
    animation.generateAnimation("Hw7/resources/BinarySearchTree.txt");
  }
}

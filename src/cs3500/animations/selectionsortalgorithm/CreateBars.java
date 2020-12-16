package cs3500.animations.selectionsortalgorithm;

import cs3500.animator.model.AnimationModel;

/**
 * Creates bar rectangles to represent the numbers in the array to be displayed.
 * The numbers in the array for this sake of this animation are represented
 * with vertical rectangles, and each shape gets the name "R"
 * and its corresponding number in the array.
 */
public class CreateBars {

  /**
   *  Creates a shape for all the values in the array to be sorted. Each value is represented by
   *  a vertical bar in the with the height a factor of the value of the element associated with it.
   * @param model model object of the animation
   * @param arr arr of integers to be sorted and specified by the user
   */
  public void createBars(AnimationModel model, int[] arr) {
    for (int ii = 0; ii < arr.length; ii++) {
      model.createShape("rectangle", String.format("R%d", arr[ii]));
    }
  }
}

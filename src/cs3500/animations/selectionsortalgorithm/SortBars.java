package cs3500.animations.selectionsortalgorithm;

import cs3500.animator.model.AnimationModel;

/**
 * Sorts the bars by performing the selection sort algorithm after taking in the randomized
 * array of integers. Once it has been sorted, this bar will then be initialized as the init bars
 * which will be used in the animation.
 */
public class SortBars {

  /**
   * Uses the selection sort algorithm to sort the bars one by one which is what will be displayed
   * in our animation.
   * @param model object model of the animation
   * @param arr array of integers to be sorted
   */
  public void sort(AnimationModel model, int[] arr) {
    int n = arr.length;
    int currentStart = 1;
    int currentEnd = 15;

    // initialize bars in the animation
    this.initBars(model, arr, currentStart, currentEnd);
    currentStart = currentEnd;

    // One by one move boundary of unsorted subarray
    for (int i = 0; i < n - 1; i++) {
      // turn ith bar orange for 5 seconds
      currentEnd += 5;
      this.ithBar(model, arr, currentStart, currentEnd, i);
      currentStart = currentEnd;

      // Find the minimum element in unsorted array
      int min_idx = i;
      for (int j = i + 1; j < n; j++) {
        // turn jth bar yellow for 5 seconds and keep min_idx orange
        currentEnd += 5;
        this.jthBarCompare(model, arr, currentStart, currentEnd, min_idx, j, i);
        currentStart = currentEnd;

        if (arr[j] < arr[min_idx]) {
          min_idx = j;


        }
      }

      // Swap the found minimum element with the first
      // element
      int temp = arr[min_idx];
      arr[min_idx] = arr[i];
      arr[i] = temp;
    }

    currentEnd += 5;
    this.initBars(model, arr, currentStart, currentEnd);
  }

  /**
   * Displays all the values in the array as bars of length associated with the value of
   * each element. The bars are displayed as vertical rectangle in blue.
   * @param model model object of the animation
   * @param arr arr of integers to be sorted
   * @param start start time of this display
   * @param end end time of this display
   */
  private static void initBars(AnimationModel model, int[] arr, int start, int end) {
    int widthBoundary = 50;
    int heightBoundary = 50;
    int barsWidth = 30;
    int spacing = 15;
    int barsHeightFactor = 20;
    int initColorR = 2;
    int initColorG = 105;
    int initColorB = 164;
    int canvasHeight = heightBoundary * 2 + arr.length * barsHeightFactor;

    int currentPos = widthBoundary;

    for (int ii = 0; ii < arr.length; ii++) {
      int y = canvasHeight - heightBoundary - (arr[ii] * barsHeightFactor);
      model.addOperation(String.format("R%d", arr[ii]), start, currentPos, y, barsWidth,
          arr[ii] * barsHeightFactor,
          initColorR, initColorG, initColorB, end, currentPos, y, barsWidth,
          arr[ii] * barsHeightFactor, initColorR,
          initColorG, initColorB);
      currentPos += barsWidth + spacing;
    }
  }

  /**
   * Displays the bar, corresponding to ith value in the array to be sorted, to be in orange.
   * @param model model object of the animation
   * @param arr arr of integers to be sorted
   * @param start start time of this display
   * @param end end time of this display
   * @param ithBar bar in the ith position to be sorted
   */
  private static void ithBar(AnimationModel model, int[] arr, int start, int end, int ithBar) {
    int widthBoundary = 50;
    int heightBoundary = 50;
    int barsWidth = 30;
    int spacing = 15;
    int barsHeightFactor = 20;
    int initColorR = 2;
    int initColorG = 105;
    int initColorB = 164;
    int markedR = 217;
    int markedG = 117;
    int markedB = 130;

    int canvasHeight = heightBoundary * 2 + arr.length * barsHeightFactor;

    int currentPos = widthBoundary;

    for (int ii = 0; ii < arr.length; ii++) {
      int y = canvasHeight - heightBoundary - (arr[ii] * barsHeightFactor);
      if (ii == ithBar) {
        model.addOperation(String.format("R%d", arr[ii]), start, currentPos, y, barsWidth,
            arr[ii] * barsHeightFactor,
            markedR, markedG, markedB, end, currentPos, y, barsWidth,
            arr[ii] * barsHeightFactor, markedR,
            markedG, markedB);
      }
      else {
        model.addOperation(String.format("R%d", arr[ii]), start, currentPos, y, barsWidth,
            arr[ii] * barsHeightFactor,
            initColorR, initColorG, initColorB, end, currentPos, y, barsWidth,
            arr[ii] * barsHeightFactor, initColorR,
            initColorG, initColorB);
      }
      currentPos += barsWidth + spacing;
    }
  }

  /**
   * Displays the bar corresponding to the minimum value in the unsorted section of the array
   * in orange and displays the jth bar corresponding to the element in the array to the right
   * of the minimum value so far, in yellow.
   * @param model model object of the animation
   * @param arr arr of integers to be sorted
   * @param start start time of this display
   * @param end end time of this display
   * @param minBar bar of minimum value in the unsorted portion of the array
   * @param jthBar jth bar to the left of the minimum bar
   */
  private static void jthBarCompare(AnimationModel model, int[] arr, int start,
      int end, int minBar, int jthBar, int ithBar) {
    int widthBoundary = 50;
    int heightBoundary = 50;
    int barsWidth = 30;
    int spacing = 15;
    int barsHeightFactor = 20;
    int initColorR = 2;
    int initColorG = 105;
    int initColorB = 164;
    int markedR = 255;
    int markedG = 216;
    int markedB = 50;

    int canvasHeight = heightBoundary * 2 + arr.length * barsHeightFactor;

    int currentPos = widthBoundary;

    for (int ii = 0; ii < arr.length; ii++) {
      int y = canvasHeight - heightBoundary - (arr[ii] * barsHeightFactor);
      if (ii == minBar) {
        model.addOperation(String.format("R%d", arr[ii]), start, currentPos, y, barsWidth,
            arr[ii] * barsHeightFactor,
            254, 124, 0, end, currentPos, y, barsWidth, arr[ii] * barsHeightFactor,
            254,
            124, 0);
      }
      else if (ii == jthBar) {
        model.addOperation(String.format("R%d", arr[ii]), start, currentPos, y, barsWidth,
            arr[ii] * barsHeightFactor,
            markedR, markedG, markedB, end, currentPos, y, barsWidth,
            arr[ii] * barsHeightFactor, markedR,
            markedG, markedB);
      }
      else if (ii == ithBar) {
        model.addOperation(String.format("R%d", arr[ii]), start, currentPos, y, barsWidth,
            arr[ii] * barsHeightFactor,
            217, 117, 130, end, currentPos, y, barsWidth,
            arr[ii] * barsHeightFactor, 217,
            117, 130);
      }
      else {
        model.addOperation(String.format("R%d", arr[ii]), start, currentPos, y, barsWidth,
            arr[ii] * barsHeightFactor,
            initColorR, initColorG, initColorB, end, currentPos, y, barsWidth,
            arr[ii] * barsHeightFactor, initColorR,
            initColorG, initColorB);
      }
      currentPos += barsWidth + spacing;
    }
  }
}

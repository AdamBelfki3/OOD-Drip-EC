package cs3500.animations.selectionsortalgorithm;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Shuffle an array of integers with element from 1 to n, with n the specified length. This class
 * will take in the length and then shuffle first. After it's shuffled, the algorithm will then
 * begin sorting which is displayed by our animation.
 */
public class RandomizeBars {

  /**
   * This method will shuffle the bars based on the length that is passed in by the user. It will
   * generate a random index and then put it in the array then remove it from the array as shown in
   * the code below.
   * @param length the length passed in by the user for which the values will be randomized
   * @return an array of randomized integers which is what we will use to sort
   */
  public int[] randomizeBars(int length) {
    int[] arr = new int[length];

    List<Integer> quickList = new ArrayList<>();
    for (int ii = 0; ii < length; ii++) {
      quickList.add(ii + 1);
    }

    Random r = new Random();
    for (int ii = length; ii > 0; ii--) {
      // generate random index
      int idx = r.nextInt(ii);

      // take element from arrayList
      int element = quickList.get(idx);

      // put it in arr
      arr[length - ii] = element;

      // remove from arrayList
      quickList.remove(idx);
    }

    return arr;
  }
}

package ds.algo.dcp;

import java.util.Arrays;

public class MinValRotatedSortedArray {

  public static void main(String[] args) {
    System.out.println(findMinimumValue(new int[] {3, 4, 5, 7, 10}));
    System.out.println(findMinimumValue(new int[] {10, 3, 4, 5, 7}));
    System.out.println(findMinimumValue(new int[] {7, 10, 3, 4, 5}));
    System.out.println(findMinimumValue(new int[] {5, 7, 10, 3, 4}));
    System.out.println(findMinimumValue(new int[] {4, 5, 7, 10, 3}));
  }

  public static int findMinimumValue(int[] arr) {
    int s = 0;
    int e = arr.length - 1;
    int mid;
    int min = arr[s];

    if (arr[s] < arr[e]) {
      System.out.println(Arrays.toString(arr) + " - No Rotation!");
      return min;
    }

    while (s < e) {
      mid = (s + e) / 2;
      System.out.println(Arrays.toString(arr) + "\t" + s + " " + mid + " " + e);
      if (mid > s && arr[mid - 1] > arr[mid]) {
        min = arr[mid];
      } else if (mid < e && arr[mid] > arr[mid + 1]) {
        min = arr[mid + 1];
      }
      if (arr[s] > arr[mid]) {
        e = mid - 1;
      } else {
        s = mid + 1;
      }
    }
    return min;
  }
}

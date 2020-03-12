package ds.algo.dcp;

import java.util.ArrayList;
import java.util.List;

public class SparseNumber {

  public static void main(String[] args) {
    for (int i = 0; i <= 16; i++) {
      System.out.println(i + " -> " + nextSparseNumber(i));
    }
  }

  private static int nextSparseNumber(int n) {
    List<Integer> binArr = new ArrayList<>();
    while (n != 0) {
      binArr.add(n & 1);
      n >>= 1;
    }
    //Since number can be larger, adding an extra bit.
    binArr.add(0);
    int last_marked_bit = 0;

    for (int i = 1; i < binArr.size() - 1; i++) {
      if (binArr.get(i - 1) + binArr.get(i) == 2 && binArr.get(i + 1) == 0) {
        for (int j = i; j >= last_marked_bit; j--) {
          binArr.set(j, 0);
        }
        binArr.set(i + 1, 1);
        last_marked_bit = i + 1;
      }
    }

    for (int i = 0; i < binArr.size(); i++) {
      n += binArr.get(i) * (1 << i);
    }

    return n;
  }
}

package ds.algo.dcp;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BinaryUtil {

  public static void main(String[] args) {
    int x = 16;
    int[] binArr = toBinary(x);
    System.out.println(Arrays.toString(binArr));

    int copy = fromBinary(binArr);
    System.out.println(x == copy);
  }

  private static int[] toBinary(int x) {
    List<Integer> binArr = new ArrayList<>();
    while (x != 0) {
      binArr.add(x & 1);
      x >>= 1;
    }

    return binArr.stream().mapToInt(v -> v).toArray();
  }

  private static int fromBinary(int[] binArr) {
    int v = 0;
    for (int i = 0; i < binArr.length; i++) {
      v += binArr[i] * (1 << i);
      //System.out.println(i + " - " + (1 << i) + " @ " + Integer.toBinaryString(1 << i));
    }

    return v;
  }
}

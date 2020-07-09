package ds.algo.dcp;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public class BinaryArraySort {
  /*
  TODO:
    https://www.geeksforgeeks.org/sort-binary-array-using-one-traversal/
    https://www.geeksforgeeks.org/given-an-array-of-numbers-arrange-the-numbers-to-form-the-biggest-number/
    https://www.geeksforgeeks.org/arrange-given-numbers-form-biggest-number-set-2/
    https://www.geeksforgeeks.org/find-smallest-value-represented-sum-subset-given-array/
    longestSubArray which sums to N
    sortedSquareArray
   */


  public static void main(String[] args) throws Exception {
    Function<String, List<Long>> toList = (s -> Arrays.stream(s.split(" "))
        .map(Long::valueOf).sorted(Collections.reverseOrder())
        .collect(Collectors.toList()));
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    int T = Integer.parseInt(br.readLine());
    List<Long> output = new ArrayList<>(T);
    while (T > 0) {
      //assuming number of elements in the list would always be same as number of players,
      // hence ignoring the `number of players` iput
      br.readLine();
      output.add(solution(toList.apply(br.readLine()), toList.apply(br.readLine())));
      T--;
    }

    output.forEach(System.out::println);
  }

  private static long solution(List<Long> gRev, List<Long> allStarz) {
    long count = 0;
    for (int i = 0, j = 0; j < allStarz.size();) {
      if (gRev.get(i) > allStarz.get(j)) {
        i++;
        count++;
      }
      j++;
    }

    return count;
  }

}

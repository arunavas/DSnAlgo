package ds.algo.hashcode.google;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Training {

  public static void main(String[] args) throws Exception {
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    int T = Integer.parseInt(br.readLine());
    List<Integer> res = new ArrayList<>(T);
    while (T > 0) {
      int P = Integer.parseInt(br.readLine().split(" ")[1]);
      res.add(solution(P, parseStudentStrengths(br.readLine())));
      T--;
    }

    for (int i = 0; i < res.size(); i++) {
      System.out.println("Case #" + (i + 1) + ": " + res.get(i));
    }
  }

  private static int[] parseStudentStrengths(String input) {
    String[] arr = input.split(" ");
    int[] res = new int[arr.length];
    for (int i = 0; i < arr.length; i++) {
      res[i] = Integer.parseInt(arr[i]);
    }

    return res;
  }

  private static int solution(int P, int[] strengths) {
    int hours;
    int temp;
    P--;
    int e = strengths.length - 1;
    int s = e - P;
    Arrays.sort(strengths);
    temp = hours = calculateHours(strengths, s, e, strengths[e]);
    s--;
    e--;
    while (s >= 0) {
      temp -= (strengths[e + 1] - strengths[e]) * (P);
      temp += strengths[e] - strengths[s];
      if (temp < hours) {
        hours = temp;
      }
      s--;
      e--;
    }

    return hours;
  }

  private static int calculateHours(int[] arr, int s, int e, int max) {
    int hours = 0;
    while (s <= e) {
      hours += max - arr[s];
      s++;
    }

    return hours;
  }
}

package ds.algo;

import java.util.ArrayList;
import java.util.List;

/*
Given an int array find the array’s balancing index.
a[] = {-7, 1, 5, 2, -4, 3, 0}
a[0] + a[1] + a[2] = a[4] + a[5] + a[6]
Hence, index 3 is balancing the array..
So we have to find out these balancing factor within an array..
More precisely,
a[0] + a[1] + a[2] + a[3] = a[3] + a[4] + a[5] + a[6]
So, index 3
 */
public class ArrayValueBalancer {
    public static void main(String[] args) {
        int[] arr = new int[] {-7, 1, 5, 2, -4, 3, 0};
        //int[] arr = new int[] {1, 0, 0, 0, 0, 0, 1};
        System.out.println(recursive(arr, false));
        System.out.println(solution(arr));
    }

    //Time Complexity: O(n) | Space Complexity: O(1)
    /*
    two pointers: s from 0 to n - 1, e from n to 1
      if e > s then keep adding fromLeft and fromRight
      if e == s
        update fromLeft and fromRight
        if both are same then s or e is one balancing index
      else
        fromLeft += value @ s - value @ s + 1 (cause e went summing the values from right)
        if fromLeft == value @ s then s is one balancing index
        fromRight += value @ e - value @ e - 1 (cause s went summing the values from left)
        if frmRight == value @ e then e is one balancing index
     */
    private static List<Integer> solution(int[] arr) {
        List<Integer> res = new ArrayList<>();
        if (arr.length > 1) {
            int fromRight = arr[arr.length - 1];
            int fromLeft = arr[0];
            for (int s = 1, e = arr.length - 2; s < arr.length - 1; s++, e--) {
                if (e > s) {
                    arr[s] += fromLeft;
                    fromLeft = arr[s];
                    arr[e] += fromRight;
                    fromRight = arr[e];
                } else if (s == e) {
                    fromLeft += arr[s];
                    fromRight += arr[e];
                    if (fromLeft == fromRight) {
                        res.add(s);
                    }
                } else {
                    fromLeft += (arr[s] - arr[s + 1]);
                    if (fromLeft == arr[s]) {
                        res.add(s);
                    }
                    fromRight += arr[e] - arr[e - 1];
                    if (fromRight == arr[e]) {
                        res.add(e);
                    }
                }
            }
        }
        return res;
    }

    ////Time Complexity: O(n) | Space Complexity: O(n) - Method Stack Frames
    private static List<Integer> recursive(int[] arr, boolean verbose) {
        List<Integer> res = new ArrayList<>();
        aux(arr, 0, 0, res, verbose);
        return res;
    }

    private static int aux(int[] arr, int idx, int acc, List<Integer> res, boolean verbose) {
        if (idx == arr.length) return 0;
        int p = acc;
        acc += arr[idx];
        String space = verbose ? spaces(idx) : ""; //just for verbose-ing purpose
        if (verbose) System.out.println(space + idx + ": " + p + " + " + arr[idx] + " = " + acc);
        int rightSum = aux(arr, idx + 1, acc, res, verbose);
        p = rightSum;
        rightSum += arr[idx];
        boolean flag = idx < arr.length - 1 && acc == rightSum;
        if (verbose) System.out.println(space + idx + ": " + p + " + " + arr[idx] + " = " + rightSum + " -> " + flag);
        if (flag) {
            res.add(idx);
        }
        return rightSum;
    }

    private static String spaces(int count) {
        StringBuilder sb = new StringBuilder();
        while(count > 0) {
            sb.append(" ");
            count--;
        }
        return sb.toString();
    }
}

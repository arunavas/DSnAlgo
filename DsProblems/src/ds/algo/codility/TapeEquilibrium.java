package ds.algo.codility;

import java.util.Arrays;

/*
Problem: Minimize the value |(A[0] + ... + A[P-1]) - (A[P] + ... + A[N-1])|
A non-empty array A consisting of N integers is given. Array A represents numbers on a tape.

Any integer P, such that 0 < P < N, splits this tape into two non-empty parts: A[0], A[1], ..., A[P − 1] and A[P], A[P + 1], ..., A[N − 1].

The difference between the two parts is the value of: |(A[0] + A[1] + ... + A[P − 1]) − (A[P] + A[P + 1] + ... + A[N − 1])|

In other words, it is the absolute difference between the sum of the first part and the sum of the second part.

For example, consider array A such that:

  A[0] = 3
  A[1] = 1
  A[2] = 2
  A[3] = 4
  A[4] = 3
We can split this tape in four places:

P = 1, difference = |3 − 10| = 7
P = 2, difference = |4 − 9| = 5
P = 3, difference = |6 − 7| = 1
P = 4, difference = |10 − 3| = 7
Write a function:

class Solution { public int solution(int[] A); }

that, given a non-empty array A of N integers, returns the minimal difference that can be achieved.

For example, given:

  A[0] = 3
  A[1] = 1
  A[2] = 2
  A[3] = 4
  A[4] = 3
the function should return 1, as explained above.

Write an efficient algorithm for the following assumptions:

N is an integer within the range [2..100,000];
each element of array A is an integer within the range [−1,000..1,000].
 */
public class TapeEquilibrium {
    public static void main(String[] args) {
        System.out.println(Arrays.toString(new int[] {3, 1, 2, 4, 3}));
        System.out.println(solution(new int[] {3, 1, 2, 4, 3}));
    }

    public static int solution(int[] A) {
        int min = Integer.MAX_VALUE;
        int P = 1;
        if (A == null || A.length < 2) return -1;
        else if (A.length == 2) return Math.abs(A[0] - A[1]);
        int sumS = 0, sumE = 0;
        for (int s = 0, e = A.length - 1; s < A.length; s++, e--) {
            if (s <= e) {
                sumS += A[s];
                sumE += A[e];
                if (s == e) {
                    int m = Math.abs(sumS - sumE - A[e]);
                    if (m < min) {
                        min = m;
                        P = s;
                    }
                    System.out.print("Middle: ");
                } else {
                    A[s] = sumS;
                    A[e] = sumE;
                }
            } else {
                int t1 = Math.abs(sumS - A[s]);
                int t2 = Math.abs(A[e] - sumE);
                int m = Math.min(t1, t2);
                if (m < min) {
                    min = m;
                    P = t1 < t2 ? s : e;
                }
                if (s + 1 < A.length) {
                    sumS = sumS + (A[s] - A[s + 1]);
                    sumE = sumE + (A[e] - A[e - 1]);
                }
            }
            System.out.println(s + " " + sumS + " | " + e + " " + sumE);
        }
        return min;
    }
}

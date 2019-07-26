package ds.algo.codility;

import java.util.Arrays;

/*
Problem:
An array A consisting of N integers is given. Rotation of the array means that each element is shifted right by one index, and the last element of the array is moved to the first place. For example, the rotation of array A = [3, 8, 9, 7, 6] is [6, 3, 8, 9, 7] (elements are shifted right by one index and 6 is moved to the first place).

The goal is to rotate array A K times; that is, each element of A will be shifted to the right K times.

Write a function:

class Solution { public int[] solution(int[] A, int K); }

that, given an array A consisting of N integers and an integer K, returns the array A rotated K times.

For example, given

    A = [3, 8, 9, 7, 6]
    K = 3
the function should return [9, 7, 6, 3, 8]. Three rotations were made:

    [3, 8, 9, 7, 6] -> [6, 3, 8, 9, 7]
    [6, 3, 8, 9, 7] -> [7, 6, 3, 8, 9]
    [7, 6, 3, 8, 9] -> [9, 7, 6, 3, 8]
For another example, given

    A = [0, 0, 0]
    K = 1
the function should return [0, 0, 0]

Given

    A = [1, 2, 3, 4]
    K = 4
the function should return [1, 2, 3, 4]

Assume that:

N and K are integers within the range [0..100];
each element of array A is an integer within the range [−1,000..1,000].
 */
public class CyclicRotation {
    public static void main(String[] args) {
        System.out.println(Arrays.toString(new int[] {3, 8, 9, 7, 6, 9}));
        /*System.out.println(Arrays.toString(solution(new int[] {3, 8, 9, 7, 6}, 1)));
        System.out.println(Arrays.toString(solution(new int[] {3, 8, 9, 7, 6}, 3)));*/
        System.out.println("@3");
        System.out.println(Arrays.toString(solution(new int[] {3, 8, 9, 7, 6, 9}, 9)));
        System.out.println(Arrays.toString(rotate(new int[] {3, 8, 9, 7, 6, 9}, 9)));
        System.out.println(Arrays.toString(rotateRight(new int[] {3, 8, 9, 7, 6, 9}, 9)));
        System.out.println("@4");
        System.out.println(Arrays.toString(solution(new int[] {3, 8, 9, 7, 6, 9}, 10)));
        System.out.println(Arrays.toString(rotate(new int[] {3, 8, 9, 7, 6, 9}, 10)));
        System.out.println(Arrays.toString(rotateRight(new int[] {3, 8, 9, 7, 6, 9}, 10)));
    }

    private static int gcd(int a, int b) {
        if (a % b == 0) return b;
        return gcd(b, a % b);
    }

    //O(n) with O(1) auxiliary space
    private static int[] solution(int[] A, int K) {
        if (A == null || A.length == 0) return A;
        int l = A.length;
        K = K % l;
        if (K == 0) return A;
        int g = gcd(K, l);
        for (int i = 0; i < g; i++) {
            boolean flag = false;
            int c = i;
            int cv = A[c];
            int temp;
            while (!flag) {
                c = (c + K) % l;
                temp = A[c];
                A[c] = cv;
                cv = temp;
                flag = c == i;
            }
        }
        return A;
    }

    //O(N) with O(N) auxiliary space
    private static int[] rotate(int[] A, int K) {
        int l = A.length;
        int[] res = new int[l];
        for (int i = 0; i < l; i++) {
            res[(i + K) % l] = A[i];
        }
        return res;
    }

    //O(K*N) with O(1) auxiliary space
    private static int[] rotateRight(int[] A, int K) {
        if (A == null || A.length == 0) return A;
        int l = A.length;
        K = K % l;
        if (K == 0) return A;
        while (K > 0) {
            K--;
            int cv = A[0];
            for (int i = 0; i < l; i++) {
                int t = A[(i + 1) % l];
                A[(i + 1) % l] = cv;
                cv = t;
            }
        }
        return A;
    }
}

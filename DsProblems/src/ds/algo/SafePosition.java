package ds.algo;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.*;

/*
Problem:
There are n people standing in a circle (numbered clockwise 1 to n) waiting to be executed. The counting begins at point 1 in the circle and proceeds around the circle in a fixed direction (clockwise). In each step, a certain number of people are skipped and the next person is executed. The elimination proceeds around the circle (which is becoming smaller and smaller as the executed people are removed), until only the last person remains, who is given freedom.
Given the total number of persons n and a number k which indicates that k-1 persons are skipped and kth person is killed in circle. The task is to choose the place in the initial circle so that you are the last one remaining and so survive.

Consider if n = 5 and k = 2, then the safe position is 3.
Firstly, the person at position 2 is killed, then person at position 4 is killed, then person at position 1 is killed. Finally, the person at position 5 is killed. So the person at position 3 survives.

Input:
The first line of input contains a single integer T denoting the number of test cases. Then T test cases follow. The first and only line of each test case consists of two space separated positive integers denoting n and k.

Output:
Corresponding to each test case, in a new line, print the safe position.

Constraints:
1 ≤ T ≤ 200
1 ≤ n, k ≤ 200

Example:
Input
3
2 1
4 2
50 10

Output
2
1
36

Explanation:
Testcase 1: here n = 2 and k = 1, then safe position is 2 as the person at 1st position will be killed.
 */
public class SafePosition {
    public static void main(String[] args) throws Exception {
        int n = 5;
        int k = 3;
        System.out.println(remaining(n, k) + 1);
        System.out.println(solution(n, k));
    }

    //http://blue.butler.edu/~phenders/InRoads/MathCounts8.pdf
    //https://stackoverflow.com/questions/3810789/removal-of-every-kth-person-from-a-circle-find-the-last-remaining-person
    /*
    This problem is based on Josephus problem.

    The problem has following recursive structure.
    josephus(n, k) = (josephus(n - 1, k) + k-1) % n + 1
    josephus(1, k) = 1
    After the first person (kth from begining) is killed, n-1 persons are left.
    So we call josephus(n – 1, k) to get the position with n-1 persons.
    But the position returned by josephus(n – 1, k) will consider the position starting from k%n + 1.
    So, we must make adjustments to the position returned by josephus(n – 1, k).
     */
    static int remaining(int n, int k) {
        int r = 0;
        for (int i = 2; i <= n; i++) {
            r = (r + k) % i;
            //System.out.println(" " + r);
        }

        return r;
    }

    private static int solution(int n, int k) {
        List<Integer> lst = new ArrayList<>(n);
        for (int i = 0; i < n; i++) {
            lst.add(i);
        }
        int s = k;
        int l = n;
        while (n > 1) {
            int r = lst.remove(s);
            System.out.println((l - n) + " " + s + " " + lst + " x" + r);
            s = (s + k) % n;
            n--;
        }

        return lst.get(0);
    }
}

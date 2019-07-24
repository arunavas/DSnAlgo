package ds.algo;

import java.util.*;

public class PossibleLeaders {
    public static void main(String[] args) {
        int[] a = new int[] {2, 1, 3, 1, 2, 2, 3};
        System.out.println(Arrays.toString(a));
        System.out.println(Arrays.toString(solution(3, 5, a)));
        /*int[] a = new int[] {1, 2, 2, 1, 2};
        System.out.println(Arrays.toString(a));
        System.out.println(Arrays.toString(solution(4, 2, a)));*/
        /*int[] a = new int[] {1, 2, 3};
        System.out.println(Arrays.toString(a));
        System.out.println(Arrays.toString(solution(3, 3, a)));*/
    }

    private static int[] solution(int K, int M, int[] A) {
        // write your code in Java SE 8
        Set<Integer> leaders = new HashSet<>();
        Map<Integer, Integer> m = new HashMap<>();
        int mv = 0;
        int mc = 0;
        boolean tieFlag = false;
        for (int i = 0; i < A.length; i++) {
            int v = i < K ? A[i] + 1 : A[i];
            m.put(v, m.getOrDefault(v, 0) + 1);
            if (m.get(v) > mc) {
                mc = m.get(v);
                mv = v;
                tieFlag = false;
            } else if (m.get(v) == mc) {
                tieFlag = true;
            }
        }
        if (!tieFlag) {
            leaders.add(mv);
        }
        System.out.println(m + " | " + mv + " @ " + mc);
        int e = K;
        while (e < A.length) {
            int p = A[e - K];
            int n = A[e];
            System.out.println("==================================");
            tieFlag = false;
            if (p != n) {
                m.put(p + 1, m.getOrDefault(p + 1, 0) - 1);
                m.put(n, m.getOrDefault(n, 0) - 1);
                if (p + 1 == mv) {
                    mc = m.get(p + 1);
                }
                if (n == mv) {
                    mc = m.get(n);
                }
                m.put(p, m.getOrDefault(p, 0) + 1);
                if (m.get(p) > mc) {
                    mc = m.get(p);
                    mv = p;
                    tieFlag = false;
                } else if (m.get(p) == mc) {
                    tieFlag = true;
                }
                m.put(n + 1, m.getOrDefault(n + 1, 0) + 1);
                if (m.get(n + 1) > mc) {
                    mc = m.get(n + 1);
                    mv = n + 1;
                    tieFlag = false;
                } else if (m.get(n + 1) == mc) {
                    tieFlag = true;
                }
                if (!tieFlag) {
                    leaders.add(mv);
                }
            }
            System.out.println(e + " " + p + " " + n + " " + m + " | " + mv + " @ " + mc);
            e++;
        }
        return leaders.stream().sorted().mapToInt(Number::intValue).toArray();
    }
}

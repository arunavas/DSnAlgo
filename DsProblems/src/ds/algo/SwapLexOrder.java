package ds.algo;

import java.util.*;
import java.util.stream.Collectors;

/*
Problem:
Given a string str and array of pairs that indicates which indices in the string can be swapped, return the lexicographically largest string that results from doing the allowed swaps. You can swap indices any number of times.

Example

For str = "abdc" and pairs = [[1, 4], [3, 4]], the output should be
swapLexOrder(str, pairs) = "dbca".

By swapping the given indices, you get the strings: "cbda", "cbad", "dbac", "dbca". The lexicographically largest string in this list is "dbca".
 */
public class SwapLexOrder {
    public static void main(String[] args) {
        /*int[][] ps = new int[4][];
        ps[0] = new int[] {0, 2};
        ps[1] = new int[] {5, 7};
        ps[2] = new int[] {7, 2};
        ps[3] = new int[] {1, 6};
        String str = "acxrabdz";
        System.out.println(str + " -> " + swapLexOrder(str, ps));*/
        int[][] ps = new int[][] {new int[] {16,26}, new int[] {2,25}, new int[] {25,27}, new int[] {19,20}, new int[] {13,20}, new int[] {4,26}, new int[] {19,27}, new int[] {18,26}, new int[] {13,23}, new int[] {1,4}, new int[] {11,19}, new int[] {16,19}, new int[] {25,28}, new int[] {19,30}, new int[] {19,25}, new int[] {1,11}, new int[] {2,20}, new int[] {10,22}, new int[] {6,19}, new int[] {7,26}, new int[] {3,30}, new int[] {15,23}, new int[] {12,26}, new int[] {1,3}, new int[] {3,12}, new int[] {3,26}, new int[] {16,30}, new int[] {2,16}, new int[] {4,13}};
        String str = "wdvvvuxuswroaxfybkikevtliryxyracjypxrygxxuehpowimunjjswhgvdhieybfgccscbubisbujbncdeaotxktfcfoanajloq";
        System.out.println(str + " -> " + swapLexOrder(str, ps));
    }

    private static String swapLexOrder(String str, int[][] pairs) {
        Set<List<Integer>> idxs = getConnectedIndexes(pairs);
        System.out.println(idxs);
        char[] srr = str.toCharArray();
        for (List<Integer> s : idxs) {
            char[] arr = new char[s.size()];
            int i = 0;
            for (Integer idx : s) {
                arr[i++] = srr[idx - 1];
            }
            System.out.println("=======================");
            System.out.println(s.stream().map(x -> srr[x - 1]).collect(Collectors.toList()));
            System.out.println(s + " -> " + Arrays.toString(arr));
            Arrays.sort(arr);
            System.out.println(s + " -> " + Arrays.toString(arr));
            i = arr.length - 1;
            for (Integer idx : s) {
                srr[idx - 1] = arr[i--];
            }
            System.out.println(s.stream().map(x -> srr[x - 1]).collect(Collectors.toList()));
        }
        return new String(srr);
    }

    private static Set<List<Integer>> getConnectedIndexes(int[][] pairs) {
        Set<List<Integer>> idxs = new HashSet<>();
        Map<Integer, Set<Integer>> idxMap = new LinkedHashMap<>();
        for (int[] p : pairs) {
            if (idxMap.containsKey(p[0]) && idxMap.containsKey(p[1])) {
                Set<Integer> s1 = idxMap.get(p[0]);
                Set<Integer> s2 = idxMap.get(p[1]);
                Set<Integer> temp = new HashSet<>(s1);
                s1.addAll(s2);
                s2.addAll(temp);
            } else if (idxMap.containsKey(p[0])) {
                Set<Integer> s = idxMap.get(p[0]);
                s.add(p[1]);
                idxMap.put(p[1], s);
            } else if (idxMap.containsKey(p[1])) {
                Set<Integer> s = idxMap.get(p[1]);
                s.add(p[0]);
                idxMap.put(p[0], s);
            } else {
                Set<Integer> s = new HashSet<>();
                s.add(p[0]);
                s.add(p[1]);
                idxMap.put(p[0], s);
                idxMap.put(p[1], s);
            }
        }
        Set<Integer> t = new HashSet<>(idxMap.keySet());
        for (Integer i : t) {
            Set<Integer> s = idxMap.get(i);
            if (s != null && !s.isEmpty()) {
                s.forEach(idxMap::remove);
                idxs.add(s.stream().sorted().collect(Collectors.toList()));
            }
        }
        return idxs;
    }
}

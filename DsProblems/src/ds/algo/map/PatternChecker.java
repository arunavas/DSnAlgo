package ds.algo.map;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/*
Problem:
Given an array strings, determine whether it follows the sequence given in the patterns array. In other words, there should be no i and j for which strings[i] = strings[j] and patterns[i] ≠ patterns[j] or for which strings[i] ≠ strings[j] and patterns[i] = patterns[j].

Example

For strings = ["cat", "dog", "dog"] and patterns = ["a", "b", "b"], the output should be
areFollowingPatterns(strings, patterns) = true;
For strings = ["cat", "dog", "doggy"] and patterns = ["a", "b", "b"], the output should be
areFollowingPatterns(strings, patterns) = false.
 */
public class PatternChecker {
    public static void main(String[] args) {
        String[] ss = new String[] {"cat", "dog", "dog"};
        String[] ps = new String[] {"a", "b", "b"};
        System.out.println(areFollowingPatterns(ss, ps));
        ss[2] = "doggy";
        System.out.println(areFollowingPatterns(ss, ps));
    }

    private static boolean areFollowingPatterns(String[] strings, String[] patterns) {
        if (strings.length != patterns.length) return false;
        Map<String, String> m = new HashMap<>();
        Set<String> ps = new HashSet<>();
        for (int i = 0; i < strings.length; i++) {
            if (m.containsKey(strings[i]) || ps.contains(patterns[i])) {
                if (!patterns[i].equals(m.getOrDefault(strings[i], ""))) {
                    return false;
                }
            } else {
                m.put(strings[i], patterns[i]);
                ps.add(patterns[i]);
            }
        }
        return true;
    }
}

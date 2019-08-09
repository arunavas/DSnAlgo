package ds.algo.leetcode;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/*
Problem:
Given a string, find the first non-repeating character in it and return it's index. If it doesn't exist, return -1.

Examples:

s = "leetcode"
return 0.

s = "loveleetcode",
return 2.
Note: You may assume the string contain only lowercase letters.
 */
public class FirstUniqueCharacter {
    public static void main(String[] args) {
        System.out.println(firstUniqChar("leetcode"));
        System.out.println(firstUniqChar("loveleetcode"));
    }

    private static int firstUniqChar(String s) {
        char[] arr = s.toCharArray();
        int[] chars = new int[26];
        for (char c : arr) {
            int idx = c - 97; //since ascii value of a is 97 and all the characters are assumed to be in lower case only;
            chars[idx] += 1;
        }
        int res = -1;
        for (int i = 0; i < arr.length; i++) {
            int idx = arr[i] - 97;
            if (chars[idx] == 1) {
                res = i;
                i = arr.length;
            }
        }
        return res;
    }
}

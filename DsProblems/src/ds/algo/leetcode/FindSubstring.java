package ds.algo.leetcode;

import java.util.Arrays;

/*
Problem:
Implement strStr().

Return the index of the first occurrence of needle in haystack, or -1 if needle is not part of haystack.

Example 1:

Input: haystack = "hello", needle = "ll"
Output: 2
Example 2:

Input: haystack = "aaaaa", needle = "bba"
Output: -1
Clarification: if needle is empty then return 0;
 */
//KMP Algorithm: https://www.youtube.com/watch?v=V5-7GzOfADQ&feature=youtu.be&t=461
public class FindSubstring {
    public static void main(String[] args) {
        System.out.println(strStr("hello", "ll"));
        System.out.println(strStr("aaaaa", "bba"));
        System.out.println(strStr("mississippi", "issip"));
        System.out.println(strStr("mississippi", "issippi"));
        System.out.println(Arrays.toString(lps("abcdabc")));
        System.out.println(Arrays.toString(lps("issip")));
    }
    //abcdabc
    private static int[] lps(String str) {
        if (str == null || str.isEmpty()) return new int[] {};
        int[] lps = new int[str.length()];
        lps[0] = 0;
        int i = 1;
        int j = 0;
        while (i < str.length()) {
            if (str.charAt(i) == str.charAt(j)) {
                j++;
                lps[i] = j;
                i++;
            } else {
                if (j == 0) {
                    lps[i] = 0;
                    i++;
                } else {
                    j = lps[j - 1];
                }
            }
        }
        return lps;
    }

    private static int strStr(String haystack, String needle) {
        if (needle == null || needle.isEmpty()) return 0;
        if (haystack == null || haystack.isEmpty()) return -1;
        int idx = -1;
        int[] lps = lps(needle);
        System.out.println(haystack + " " + needle + " -> " + Arrays.toString(lps));
        int i = 0;
        int j = 0;
        while (i < haystack.length()) {
            if (haystack.charAt(i) == needle.charAt(j)) {
                i++;
                j++;
                if (j == needle.length()) {
                    idx = i - j;
                    i = haystack.length();
                }
            } else {
                if (j == 0) {
                    i++;
                } else {
                    j = lps[j - 1];
                }
            }
        }
        return idx;
    }
}

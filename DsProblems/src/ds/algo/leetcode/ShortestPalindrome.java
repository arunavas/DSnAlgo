package ds.algo.leetcode;

/*
Problem:
Given a string s, you are allowed to convert it to a palindrome by adding characters in front of it. Find and return the shortest palindrome you can find by performing this transformation.

Example 1:

Input: "aacecaaa"
Output: "aaacecaaa"
Example 2:

Input: "abcd"
Output: "dcbabcd"
 */
public class ShortestPalindrome {
    public static void main(String[] args) {
        System.out.println("aacecaaa -> " + shortestPalindrome("aacecaaa"));
        System.out.println("baaacecaaa -> " + shortestPalindrome("baaacecaaa"));
        System.out.println("baaacecaaab -> " + shortestPalindrome("baaacecaaab"));
        System.out.println("abcd -> " + shortestPalindrome("abcd"));
        System.out.println("abcda -> " + shortestPalindrome("abcda"));
        System.out.println("abbacd -> " + shortestPalindrome("abbacd"));
        System.out.println("a -> " + shortestPalindrome("a"));
    }

    private static String shortestPalindrome(String s) {
        StringBuilder sb = new StringBuilder(s);
        int i = s.length() - 1;
        int lpi = 0;
        while (i >= 0) {
            if (s.charAt(0) == s.charAt(i)) {
                boolean isPal = true;
                for (int j = 0, k = i; j < k && isPal; j++, k--) {
                    if (s.charAt(j) != s.charAt(k)) {
                        isPal = false;
                    }
                }
                if (isPal) {
                    lpi = i;
                    i = 0;
                }
            }
            i--;
        }
        i = lpi + 1;
        while (i < s.length()) {
            sb.insert(0, s.charAt(i++));
        }
        return sb.toString();
    }
}

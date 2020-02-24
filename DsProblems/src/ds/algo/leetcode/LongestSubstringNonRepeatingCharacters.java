package ds.algo.leetcode;

import java.util.HashMap;
import java.util.Map;

public class LongestSubstringNonRepeatingCharacters {

  public static void main(String[] args) {

  }

  public int lengthOfLongestSubstring(String str) {
    int maxLen = 0;

    Map<Character, Integer> charMap = new HashMap<>();
    int s = 0;
    for (int i = 0; i < str.length(); i++) {
      char c = str.charAt(i);
      if (charMap.containsKey(c)) {
        
      }
    }

    return maxLen;
  }
}

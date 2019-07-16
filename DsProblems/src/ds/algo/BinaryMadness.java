package ds.algo;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/*
Problem:
Binary Madness
Given a non-negative integer, find the count of substrings with an odd number of 1s in its binary representation.
Input Format

First line of input t denotes the number of test cases. Next t lines follow each containing a non-negative integer n.
Output Format

For each test case print the count of substrings which satisfy the condition.
Sample Input

12
0
1
2
10
32
33
127
341
455
496
992
1000
Sample Output

0
1
2
6
6
10
16
25
24
21
24
28
Constraints

1 <= t <= 100
0 <= n <= 10200
Explanation

For the number 33, the binary representation is 100001. Of all the substrings possible, the following 10 substrings have odd number of 1s.
1
10
100
1000
10000
00001
0001
001
01
1
Hence answer is 10.
 */
public class BinaryMadness {

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int tests = Integer.parseInt(br.readLine());
        List<BigInteger> inputs = new ArrayList<>(tests);
        for (int i = 0; i < tests; i++) {
            BigInteger n = new BigInteger(br.readLine());
            inputs.add(n);
        }
        List<Integer> lst = inputs.stream().parallel().map(p -> subStrings(p.toString(2)).size()).collect(Collectors.toList());
        lst.forEach(System.out::println);
    }

    private static List<String> subStrings(String str) {
        List<String> res = new ArrayList<>();
        int n = str == null ? 0 : str.length();
        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j <= n; j++) {
                String s = str.substring(i, j);
                if (isOddOnePresent(s)) {
                    res.add(s);
                }
            }
        }
        return res;
    }

    private static boolean isOddOnePresent(String str) {
        int oneCount = 0;
        for (char c : str.toCharArray()) {
            if (c == '1') {
                oneCount++;
            }
        }
        return oneCount % 2 != 0;
    }

}

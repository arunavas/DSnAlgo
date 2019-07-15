package codechef;

import ds.common.ListUtil;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.math.MathContext;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/*
Problem:
Chef has invested his savings into N coins (numbered 1 through N). For each valid i, the i-th coin has value Ai. Chef does not want to know how much money he has, so he remembers the mean value of the coins instead of the sum of their values.

A waiter from Chef's restaurant plans to steal exactly one of Chef's coins, but he does not want Chef to know about this, so he can only steal a coin if the arithmetic mean of all remaining coins is the same as the original arithmetic mean of all coins. Since the waiter is not good at mathematics, can you help him complete his plan?

You have to determine whether it is possible to steal some coin and if it is possible, choose the coin the waiter should steal. If there are multiple coins that can be stolen, choose the one with the smallest number.

Input
The first line of the input contains a single integer T denoting the number of test cases. The description of T test cases follows.
The first line of each test case contains a single integer N.
The second line contains N space-separated integers A1,A2,…,AN.
Output
For each test case, print a single line. If the waiter cannot steal any coin, this line should contain the string "Impossible" (without quotes). Otherwise, it should contain the number of the coin he should steal.

Constraints
1≤T≤10
2≤N≤105
1≤Ai≤109 for each valid i

Example Input
3
5
1 2 3 4 5
4
5 4 3 6
10
1000000000 1000000000 1000000000 1000000000 1000000000 1000000000 1000000000 1000000000 1000000000 1000000000
Example Output
3
Impossible
1
Explanation
Example case 1: Stealing the third coin does not change the mean. Initially, it is (1+2+3+4+5)/5=3 and after stealing this coin, it is still (1+2+4+5)/4=3.

Example case 2: It is not possible to steal any coin without changing the mean.

Example case 3: The mean is always 109, both initially and after removing any coin, so each coin can be stolen. In that case, we should choose the first coin.
 */
public class ChefAndMean {
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int t = Integer.parseInt(br.readLine());
        List<List<Long>> input = new ArrayList<>(t);
        for (int i = 0; i < t; i++) {
            int l = Integer.parseInt(br.readLine());
            input.add(Arrays.stream(br.readLine().split(" ")).map(Long::parseLong).collect(Collectors.toList()));
        }
        ChefAndMean cm = new ChefAndMean();
        List<String> res = input.parallelStream().map(cm::solution).collect(Collectors.toList());
        res.forEach(System.out::println);
    }

    public String solution(List<Long> coins) {
        final BigDecimal sum = ListUtil.fold(coins, BigDecimal.ZERO, (i, bi) -> bi.add(BigDecimal.valueOf(i)));
        final BigDecimal mean = sum.divide(BigDecimal.valueOf(coins.size()), MathContext.DECIMAL64);
        final BigDecimal count = BigDecimal.valueOf(coins.size() - 1);
        long min = Long.MAX_VALUE;
        int idx = -1;
        for (int i = 0; i < coins.size(); i++) {
            long v = coins.get(i);
            BigDecimal newSum = sum.subtract(BigDecimal.valueOf(v));
            BigDecimal newMean = newSum.divide(count, MathContext.DECIMAL64);
            if (mean.equals(newMean) && min > v) {
                min = v;
                idx = i;
            }
        }
        if (idx >= 0) {
            return "" + (idx + 1);
        } else {
            return "Impossible";
        }
    }
}

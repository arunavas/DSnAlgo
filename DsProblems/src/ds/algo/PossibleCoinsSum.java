package ds.algo;

import java.util.*;

/*
Problem:
You have a collection of coins, and you know the values of the coins and the quantity of each type of coin in it. You want to know how many distinct sums you can make from non-empty groupings of these coins.

Example

For coins = [10, 50, 100] and quantity = [1, 2, 1], the output should be
possibleSums(coins, quantity) = 9.

Here are all the possible sums:

50 = 50;
10 + 50 = 60;
50 + 100 = 150;
10 + 50 + 100 = 160;
50 + 50 = 100;
10 + 50 + 50 = 110;
50 + 50 + 100 = 200;
10 + 50 + 50 + 100 = 210;
10 = 10;
100 = 100;
10 + 100 = 110.
 */
public class PossibleCoinsSum {
    public static void main(String[] args) {
        int[] coins = new int[] {1, 2};
        int[] qs = new int[] {50000, 2};
        /*int[] coins = new int[] {10, 50, 100};
        int[] qs = new int[] {1, 2, 1};*/
        System.out.println(possibleSums(coins, qs));
    }

    private static void doSum(int[] coins, int[] quantity, int idx, Set<Integer> sums, int acc) {
        if (idx >= quantity.length) return;
        int q = quantity[idx];
        int sum = acc;
        while (q > 0) {
            q--;
            sum += coins[idx];
            sums.add(sum);
            doSum(coins, quantity, idx + 1, sums, sum);
        }
    }

    /*
    For this example
    coins = [10, 50, 100] and quantity = [1, 2, 1],
    Initialize an empty HasSet.
    make a recursion tree like below:

    (10 * 1) = 10
    ...............|__ +(50 * 1) = 60
    ........................................|__ +(100 * 1) = 160
    ...............|__ +(50 * 2) = 110
    ........................................|__ +(100 * 1) = 210
    (50 * 1) = 50
    .................|__ +(100 * 1) = 150
    (50 * 2) = 100
    .................|__ +(100 * 1) = 200
    (100 * 1) = 100 //This value is already in the set.

    On each iteration add the sum to the hashset to avoid duplicates.
     */
    //TODO - Need to test further
    private static int possibleSums(int[] coins, int[] quantity) {
        Set<Integer> sums = new HashSet<>();
        for (int i = 0; i < coins.length; i++) {
            doSum(coins, quantity, i, sums, 0);
        }
        return sums.size();
    }
}

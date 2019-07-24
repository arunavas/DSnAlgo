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
        int[] coins = new int[] {10, 50, 100, 500};
        int[] qs = new int[] {5, 3, 2, 2};
        /*int[] coins = new int[] {10, 50, 100};
        int[] qs = new int[] {1, 2, 1};*/
        System.out.println(possibleSums(coins, qs));
        Set<Integer> sums = new HashSet<>();
        generateSums(coins, qs, 0, 0, sums);
        System.out.println(sums.size() - 1);
    }

    //TODO find bug in doSum with respect to generateSums

    private static void doSum(int[] coins, int[] quantity, int idx, Set<Integer> sums, int acc, String str) {
        if (idx >= quantity.length) return;
        int q = quantity[idx];
        int sum = acc;
        boolean flag = str.isEmpty();
        while (q > 0) {
            q--;
            sum += coins[idx];
            str += flag ? coins[idx] : " + " + coins[idx];
            flag = false;
            sums.add(sum);
            System.out.println(spaces(idx) + coins[idx] + "." + (quantity[idx] - q) + " -> " + str + " = " + sum);
            doSum(coins, quantity, idx + 1, sums, sum, str);
        }
    }

    public static void generateSums(int[] coins,int[] quantity,int i,int sum,Set<Integer> set){
        if(i==coins.length){
            set.add(sum);
            return;
        }
        if(quantity[i]==0){
            set.add(sum);
            generateSums(coins, quantity, i+1, sum,set);
        }else{
            set.add(sum);
            quantity[i]=quantity[i]-1;
            generateSums(coins, quantity, i, sum+coins[i],set);
            //backtrack. now increase the quantity since we havent used the ith coin and moving to next coin
            quantity[i]=quantity[i]+1;
            generateSums(coins, quantity, i+1, sum, set);
        }
    }

    private static String spaces(int c) {
        StringBuilder sb = new StringBuilder();
        while (c > 0) {
            sb.append(" ");
            c--;
        }
        return sb.toString();
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
            System.out.print(coins[i] + "x" + quantity[i]);
            System.out.print(" ");
        }
        System.out.println();
        for (int i = 0; i < coins.length; i++) {
            doSum(coins, quantity, i, sums, 0, "");
        }
        return sums.size();
    }
}

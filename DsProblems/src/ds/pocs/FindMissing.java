package com.relcache.core.pocs;

public class FindMissing {
    public static void main(String[] args) {
        System.out.println(findMissing(new int[] {3, 3, 2, 1, 2, 2, 3}));
    }

    private static int findMissing(int[] arr) {
        int sum = 0;
        int counter = 0;
        int total = 0;
        for (int i = 0; i < arr.length; i++) {
            total += arr[i];
            System.out.println(arr[i] + ": " + counter + " & " + (1 << arr[i]) + " = " + (counter & (1 << arr[i])));
            if ((counter & (1 << arr[i])) == 0) {
                sum += arr[i];
                counter |= 1 << arr[i];
            }
        }
        System.out.println(sum);
        sum *= 3;
        System.out.println(total);
        return (sum - total) / 2;
    }
}

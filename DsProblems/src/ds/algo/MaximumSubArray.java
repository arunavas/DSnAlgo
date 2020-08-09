package ds.algo;

public class MaximumSubArray {
    public static void main(String[] args) {
        System.out.println(maxSubArraySum(new int[] {1, -3, 2, 2, -1, 1}));
        System.out.println(maxSubArraySum(new int[] {1, -3, 2, 1, -1, 1}));
        System.out.println(maxSubArraySum(new int[] {7, -3, 2, 1, -1, 1}));
    }

    private static int maxSubArraySum(int[] arr) {
        int max = 0;
        int last = 0;
        for (int i = 0; i < arr.length; i++) {
            int curr = arr[i];
            last += curr;
            if (last > max) {
                max = last;
            } else if (curr > max) {
                max = curr;
                last = curr;
            }
        }
        return max;
    }
}

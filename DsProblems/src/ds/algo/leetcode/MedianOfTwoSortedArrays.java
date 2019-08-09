package ds.algo.leetcode;

import java.util.Arrays;

/*
Problem:
There are two sorted arrays nums1 and nums2 of size m and n respectively.

Find the median of the two sorted arrays. The overall run time complexity should be O(log (m+n)).

You may assume nums1 and nums2 cannot be both empty.

Example 1:

nums1 = [1, 3]
nums2 = [2]

The median is 2.0
Example 2:

nums1 = [1, 2]
nums2 = [3, 4]

The median is (2 + 3)/2 = 2.5
 */
//https://www.youtube.com/watch?v=LPFhl65R7ww
public class MedianOfTwoSortedArrays {
    public static void main(String[] args) {
        System.out.println(findMedianSortedArrays(new int[] {1, 3, 4, 6}, new int [] {2, 3, 5}));
        System.out.println(findMedianSortedArrays(new int[] {1, 3}, new int [] {2}));
        System.out.println(findMedianSortedArrays(new int[] {1, 2}, new int [] {3, 4}));
    }

    private static double findMedianSortedArrays(int[] nums1, int[] nums2) {
        if (nums1 == null) nums1 = new int[] {};
        if (nums2 == null) nums2 = new int[] {};
        int l1 = nums1.length, l2 = nums2.length;
        if (l2 < l1) return findMedianSortedArrays(nums2, nums1);
        int t = l1 + l2;
        int s = 0, e = l1;
        int p1, p2;
        double res = 0.0;
        System.out.println(Arrays.toString(nums1) + " " + Arrays.toString(nums2));
        while (s <= e) {
            p1 = (s + e) / 2;
            p2 = (t + 1) / 2 - p1;
            int maxL1 = p1 == 0 ? Integer.MIN_VALUE : nums1[p1 - 1];
            int minR1 = p1 == l1 ? Integer.MAX_VALUE : nums1[p1];
            int maxL2 = p2 == 0 ? Integer.MIN_VALUE : nums2[p2 - 1];
            int minR2 = p2 == l2 ? Integer.MAX_VALUE : nums2[p2];
            System.out.println(t + " (" + s + ", " + e + ") " + p1 + "." + p2 + " - (" + maxL1 + " < " + minR2 + ") (" + maxL2 + " < " + minR1 + ")");
            if (maxL1 <= minR2 && maxL2 <= minR1) {
                //found
                s = e + 1;
                if (t % 2 == 0) {
                    //even -- avg of middle two
                    res = ((double) Math.max(maxL1, maxL2) + (double) Math.min(minR1, minR2)) / 2;
                } else {
                    //odd -- middle element only
                    res = Math.max(maxL1, maxL2);
                }
            } else if (maxL1 > minR2) {
                e = p1 - 1;
                System.out.println(s + " " + e + " <--");
            } else {
                System.out.println("--> " + s + " " + e);
                s = p1 + 1;
            }
        }
        return res;
    }
}

package ds.algo;

import ds.algo.SingleLinkedListReversal.ListNode;

/*
Problem:
Given two singly linked lists sorted in non-decreasing order, your task is to merge them. In other words, return a singly linked list, also sorted in non-decreasing order, that contains the elements from both original lists.

Example

For l1 = [1, 2, 3] and l2 = [4, 5, 6], the output should be
mergeTwoLinkedLists(l1, l2) = [1, 2, 3, 4, 5, 6];
For l1 = [1, 1, 2, 4] and l2 = [0, 3, 5], the output should be
mergeTwoLinkedLists(l1, l2) = [0, 1, 1, 2, 3, 4, 5].
 */
public class MergeSortedSingleLinkedList {
    public static void main(String[] args) {
        //int[] a = new int[] {1, 2, 3};
        //int[] b = new int[] {4, 5, 6};
        int[] a = new int[] {1, 1, 2, 4};
        int[] b = new int[] {0, 3, 5};
        ListNode<Integer> l1 = new ListNode<>(a[0]);
        ListNode<Integer> l2 = new ListNode<>(b[0]);
        ListNode<Integer> c = l1;
        for (int i = 1; i < a.length; i++) {
            c.next = new ListNode<>(a[i]);
            c = c.next;
        }
        c = l2;
        for (int i = 1; i < b.length; i++) {
            c.next = new ListNode<>(b[i]);
            c = c.next;
        }
        System.out.println(l1 + "\n" + l2);
        System.out.println(mergeTwoLinkedLists(l1, l2));
    }

    private static ListNode<Integer> mergeTwoLinkedLists(ListNode<Integer> l1, ListNode<Integer> l2) {
        if (l1 == null) return l2;
        if (l2 == null) return l1;
        ListNode<Integer> r = null;
        ListNode<Integer> p = null;
        while (l1 != null || l2 != null) {
            if (l2 == null || (l1 != null && l1.value <= l2.value)) {
                if (p == null) {
                    p = l1;
                    r = p;
                } else {
                    p.next = new ListNode<>(l1.value);
                    p = p.next;
                }
                l1 = l1.next;
            }
            else {
                if (p == null) {
                    p = l2;
                    r = p;
                } else {
                    p.next = new ListNode<>(l2.value);
                    p = p.next;
                }
                l2 = l2.next;
            }
        }
        return r;
    }
}

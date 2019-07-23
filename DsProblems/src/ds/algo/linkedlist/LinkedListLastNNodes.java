package ds.algo.linkedlist;

import ds.algo.linkedlist.SingleLinkedListReversal.ListNode;

import java.util.HashSet;

/*
Problem:
Given a singly linked list of integers l and a non-negative integer n, move the last n list nodes to the beginning of the linked list.

Example

For l = [1, 2, 3, 4, 5] and n = 3, the output should be
rearrangeLastN(l, n) = [3, 4, 5, 1, 2];
For l = [1, 2, 3, 4, 5, 6, 7] and n = 1, the output should be
rearrangeLastN(l, n) = [7, 1, 2, 3, 4, 5, 6].
 */
public class LinkedListLastNNodes {
    public static void main(String[] args) {
        int[] a = new int[] {1, 2, 3, 4, 5};
        ListNode<Integer> l = new ListNode<>(a[0]);
        ListNode<Integer> c = l;
        for (int i = 1; i < a.length; i++) {
            c.next = new ListNode<>(a[i]);
            c = c.next;
        }
        System.out.println(rearrangeLastN(l, 3));
    }

    private static ListNode<Integer> rearrangeLastN(ListNode<Integer> l, int n) {
        ListNode<Integer> c = l;
        ListNode<Integer> last = null;
        int size = 0;
        while (c != null) {
            size++;
            last = c;
            c = c.next;
        }
        if (size > n) {
            last.next = l;
            c = l;
            n = size - n;
            while (n > 0) {
                n--;
                last = c;
                c = c.next;
            }
            l = c;
            last.next = null;
        }
        return l;
    }
}

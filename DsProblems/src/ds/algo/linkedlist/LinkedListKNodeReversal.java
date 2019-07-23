package ds.algo.linkedlist;

import ds.algo.linkedlist.SingleLinkedListReversal.ListNode;

public class LinkedListKNodeReversal {
    public static void main(String[] args) {
        int[] a = new int[] {1, 2, 3, 4};
        ListNode<Integer> l = new ListNode<>(a[0]);
        ListNode<Integer> c = l;
        for (int i = 1; i < a.length; i++) {
            c.next = new ListNode<>(a[i]);
            c = c.next;
        }
        System.out.println(reverseNodesInKGroups(l, 2));
    }

    private static ListNode<Integer> reverseNodesInKGroups(ListNode<Integer> l, int k) {
        int size = size(l);
        if (size < 2 || k < 2) return l;
        ListNode<Integer> pp = null;
        ListNode<Integer> n = null;
        ListNode<Integer> r = null;
        for (int i = k; i <= size; i += k) {
            ListNode<Integer> p = null;
            ListNode<Integer> cp = null;
            for (int j = 0; j < k; j++) {
                n = l.next;
                l.next = p;
                p = l;
                l = n;
                if (j == 0) {
                    cp = p;
                }
            }
            if (i == k) {
                r = p;
                pp = cp;
            } else {
                pp.next = p;
                pp = cp;
            }
            System.out.println(i + " " + pp + " " + cp + " " + p + " " + l + " " + n + " " + r);
        }
        pp.next = l;
        return r;
    }

    private static int size(ListNode<Integer> l) {
        int size = 0;
        ListNode<Integer> c = l;
        while (c != null) {
            size++;
            c = c.next;
        }
        return size;
    }
}

package ds.algo;

import ds.algo.SingleLinkedListReversal.ListNode;

/*
Problem:
Given a singly linked list of integers, determine whether or not it's a palindrome.

Note: in examples below and tests preview linked lists are presented as arrays just for simplicity of visualization: in real data you will be given a head node l of the linked list

Example

For l = [0, 1, 0], the output should be
isListPalindrome(l) = true;

For l = [1, 2, 2, 3], the output should be
isListPalindrome(l) = false.
 */
public class LinkedListIntPalidrome {
    public static void main(String[] args) {
        ListNode<Integer> l = new ListNode<>(0);
        l.next = new ListNode<>(1);
        l.next.next = new ListNode<>(0);
        /*System.out.println(isListPalindrome1(l));
        System.out.println(isListPalindrome2(l));*/
        System.out.println(l);
        System.out.println(isListPalindrome(l));
        int[] v = new int[] {1, 1000000000, -1000000000, -1000000000, 1000000000, 1};
        l = new ListNode<>(v[0]);
        ListNode<Integer> c = l;
        for (int i = 1; i < v.length; i++) {
            c.next = new ListNode<>(v[i]);
            c = c.next;
        }
        System.out.println(l);
        System.out.println(isListPalindrome(l));
    }

    private static boolean isListPalindrome(ListNode<Integer> l) {
        int size = 0;
        ListNode<Integer> c = l;
        //Getting Size - O(n)
        while (c != null) {
            size++;
            c = c.next;
        }
        if (size < 2) {
            return true;
        }
        int mid = size / 2;
        //System.out.println("Size: " + size + " | Mid: " + mid);
        ListNode<Integer> p = null;
        //Reversing till mid list - O(n/2)
        for (int i = 0; i < mid; i++) {
            c = l.next;
            l.next = p;
            p = l;
            l = c;
        }
        l = p;
        if (size % 2 != 0) {
            //for odd sized list, need to skip mid element.
            c = c.next;
        }
        //System.out.println(l + " " + c);
        //traversing head -> mid (i.e. mid -> head after reversal) && mid + 1 -> last - O(n/2)
        while (c != null) {
            if (!l.value.equals(c.value)) {
                return false;
            }
            c = c.next;
            l = l.next;
            //System.out.println((l == null ? "null" : l.value) + " " + (c == null ? "null" : c.value));
        }
        return true;
    }

    //for Integers only (not crossing long size)
    private static boolean isListPalindrome1(ListNode<Integer> l) {
        long front = 0;
        long back = 0;
        int c = 0;
        ListNode<Integer> curr = l;
        while (curr != null) {
            front = front * 10 + curr.value;
            back = c == 0 ? curr.value : curr.value * Double.valueOf(Math.pow(10, c)).longValue() + back;
            c++;
            curr = curr.next;
        }
        return front == back;
    }

    //for Integers only (not crossing long size) - same as isListPalindrome1 with a little betterment - summing to mid list instead of summing entire list
    private static boolean isListPalindrome2(ListNode<Integer> l) {
        int size = 0;
        ListNode<Integer> c = l;
        //Getting Size - O(n)
        while (c != null) {
            size++;
            c = c.next;
        }
        c = l;
        //for odd size list, mid element need to be discarded.
        boolean flag = size % 2 == 0;
        int mid = size / 2;
        long sum = 0;
        int idx = 0;
        while (c != null) {
            if (idx < mid) {
                sum += c.value;
            } else if ((flag && idx == mid) || idx > mid) {
                sum -= c.value;
            }
            idx++;
            c = c.next;
        }
        return sum == 0;
    }
}

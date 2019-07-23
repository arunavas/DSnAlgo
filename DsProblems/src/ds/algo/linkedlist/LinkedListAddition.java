package ds.algo.linkedlist;

import ds.algo.linkedlist.SingleLinkedListReversal.ListNode;

/*
Problem:
You're given 2 huge integers represented by linked lists. Each linked list element is a number from 0 to 9999 that represents a number with exactly 4 digits. The represented number might have leading zeros. Your task is to add up these huge integers and return the result in the same format.

Example

For a = [9876, 5432, 1999] and b = [1, 8001], the output should be
addTwoHugeNumbers(a, b) = [9876, 5434, 0].

Explanation: 987654321999 + 18001 = 987654340000.

For a = [123, 4, 5] and b = [100, 100, 100], the output should be
addTwoHugeNumbers(a, b) = [223, 104, 105].

Explanation: 12300040005 + 10001000100 = 22301040105.
 */
public class LinkedListAddition {
    public static void main(String[] args) {
        int[] a = new int[] {9876, 5432, 1999};
        int[] b = new int[] {1, 8001};
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
        System.out.println(addTwoHugeNumbers(l1, l2));
    }

    private static ListNode<Integer> addTwoHugeNumbers(ListNode<Integer> a, ListNode<Integer> b) {
        int sa = size(a);
        int sb = size(b);
        int carry;
        if (sb > sa) {
            carry = add(b, a, sb - sa);
            a = b;
        } else {
            carry = add(a, b, sa - sb);
        }
        while (carry != 0) {
            ListNode<Integer> n = new ListNode<>(carry % 10000);
            n.next = a;
            a = n;
            carry /= 10000;
        }
        return a;
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

    private static int add(ListNode<Integer> a, ListNode<Integer> b, int diff) {
        if (a == null && b == null) return 0;
        int carry;
        //System.out.println(diff + " " + a.value + " " + b.value);
        int sum;
        if (diff > 0) {
            carry = add(a.next, b, diff - 1);
            sum = a.value + carry;
        } else {
            carry = add(a.next, b.next, diff);
            sum = a.value + b.value + carry;
        }
        //System.out.println("-" + a.value + "+" + b.value + "+" + carry + "=" + sum);
        carry = sum / 10000;
        a.value = sum % 10000;
        //System.out.println("-" + carry + " " + a.value);
        return carry;
    }
}

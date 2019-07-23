package ds.algo.linkedlist;

public class SingleLinkedListReversal {
    public static void main(String[] args) {
        ListNode<Integer> l = new ListNode<>(0);
        ListNode<Integer> c = l;
        for (int i = 1; i < 10; i++) {
            c.next = new ListNode<>(i);
            c = c.next;
        }
        System.out.println(reverse(l));
    }

    public static ListNode<Integer> reverse(ListNode<Integer> l) {
        ListNode<Integer> p = null;
        ListNode<Integer> n = null;
        while (l != null) {
            n = l.next;
            l.next = p;
            p = l;
            l = n;
        }
        l = p;
        return l;
    }

    public static class ListNode<T> {
        ListNode(T x) {
            value = x;
        }
        T value;
        ListNode<T> next;

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();
            ListNode<T> c = this;
            while (c != null) {
                sb.append(c.value);
                if (c.next != null) {
                    sb.append(" -> ");
                }
                c = c.next;
            }
            return "[" + sb.toString() + "]";
        }
    }
}

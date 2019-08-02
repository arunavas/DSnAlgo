package ds.common;

import java.util.function.Function;

//TODO
public class CircularLinkedList<A> {
    private Node<A> head;
    private int size = 0;

    public static void main(String[] args) {
        CircularLinkedList<Integer> cl = new CircularLinkedList<>();
        System.out.println(cl);
        for (int i = 0; i < 10; i++) {
            cl.add(i);
            System.out.println(cl);
        }
        cl.add(0, -1);
        System.out.println(cl.head + " - " + cl + " - " + cl.size);
        cl.delete(x -> x == -1);
        System.out.println(cl.head + " - " + cl + " - " + cl.size);
        System.out.println(cl.get(x -> x == 9));
    }

    public void add(A value) {
        if (head == null) {
            head = new Node<>(value);
            head.setNext(head);
            head.setPrev(head);
        } else {
            Node<A> n = new Node<>(value);
            head.getPrev().setNext(n);
            n.setPrev(head.getPrev());
            head.setPrev(n);
            n.setNext(head);
        }
        size++;
    }

    public void add(int idx, A value) {
        if (idx > size) throw new IndexOutOfBoundsException("idx must be less than size!");
        Node<A> n = getNode(idx);
        Node<A> v = new Node<>(value);
        if (n == null) {
            n = head;
        }
        n.getPrev().setNext(v);
        v.setPrev(n.getPrev());
        n.setPrev(v);
        v.setNext(n);
        size++;
        if (idx == 0) {
            head = v;
        }
    }

    public void set(int idx, A value) {
        if (idx >= size) throw new IndexOutOfBoundsException("idx must be less than size!");
        Node<A> n = getNode(idx);
        n.setValue(value);
    }

    public A delete(int idx) {
        if (idx >= size) throw new IndexOutOfBoundsException("idx must be less than size!");
        Node<A> n = getNode(idx);
        n.getPrev().setNext(n.getNext());
        n.getNext().setPrev(n.getPrev());
        size--;
        if (idx == 0) {
            head = n.getNext();
        }
        return n.value;
    }

    public A delete(Function<A, Boolean> f) {
        Node<A> n = getNode(f);
        if (n == null) return null;
        n.getPrev().setNext(n.getNext());
        n.getNext().setPrev(n.getPrev());
        size--;
        if (n == head) {
            head = n.getNext();
        }
        return n.value;
    }

    public A get(int idx) {
        Node<A> n = getNode(idx);
        return n == null ? null : n.value;
    }

    public A get(Function<A, Boolean> f) {
        Node<A> n = getNode(f);
        return n == null ? null : n.value;
    }

    private Node<A> getNode(Function<A, Boolean> f) {
        Node<A> n = null;
        if (f != null) {
            n = head;
            int idx = 0;
            while (idx < size && !f.apply(n.value)) {
                n = n.next;
                idx++;
            }
        }
        return n;
    }

    private Node<A> getNode(int idx) {
        Node<A> n = null;
        if (idx < size) {
            n = head;
            while (idx > 0) {
                n = n.next;
                idx--;
            }
        }
        return n;
    }

    @Override
    public String toString() {
        Node<A> n = head;
        StringBuilder sb = new StringBuilder();
        if (n != null) {
            sb.append(n);
            n = n.next;
            while (n != head) {
                sb.append(" -> ").append(n);
                n = n.next;
            }
        }
        return sb.toString();
    }

    private static class Node<T> {
        private T value;
        private Node<T> next;
        private Node<T> prev;

        public Node(T value) {
            this.value = value;
            this.next = null;
        }

        public void setNext(Node<T> next) {
            this.next = next;
        }
        public Node<T> getNext() {
            return this.next;
        }

        public void setPrev(Node<T> prev) {
            this.prev = prev;
        }
        public Node<T> getPrev() {
            return this.prev;
        }

        public void setValue(T value) {
            this.value = value;
        }
        public T getValue() {
            return this.value;
        }

        @Override
        public String toString() {
            //return "[" + (prev == null ? "" : prev.value) + " <- " + value.toString()+ " -> " + (next == null ? "" : next.value) + "]";
            return value.toString();
        }
    }
}

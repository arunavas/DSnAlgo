package ds.common;

import java.util.*;

public class LRUCache<A, B> {
    public static void main(String[] args) {
        // Let’s say we have a LRU cache of capacity 2.
        LRUCache<Integer, Integer> cache = new LRUCache<Integer, Integer>(2);

        cache.set(1, 10); // it will store a key (1) with value 10 in the cache.
        System.out.println("-> 1: " + cache);
        cache.set(2, 20); // it will store a key (1) with value 10 in the cache.
        System.out.println("-> 2: " + cache);
        System.out.println("<- 1: " + cache.get(1)); // returns 10
        System.out.println("<- 1: " + cache);
        cache.set(3, 30); // evicts key 2 and store a key (3) with value 30 in the cache.
        System.out.println("-> 3: " + cache);
        System.out.println("<- 2: " + cache.get(2)); // returns -1 (not found)
        System.out.println("<- 2: " + cache);
        cache.set(4, 40); // evicts key 1 and store a key (4) with value 40 in the cache.
        System.out.println("-> 4: " + cache);
        System.out.println("<- 1: " + cache.get(1)); // returns -1 (not found)
        System.out.println("<- 1: " + cache);
        System.out.println("<- 3: " + cache.get(3)); // returns 30
        System.out.println("<- 3: " + cache);
        System.out.println("<- 4: " + cache.get(4)); // returns 40
        System.out.println("<- 4: " + cache);
    }

    private final int MAX_SIZE;
    private Node<A> head = null;
    private Node<A> last = null;
    private Map<A, Pair<B, Node<A>>> cache;
    private int size = 0;

    public LRUCache(int size) {
        this.MAX_SIZE = size;
        cache = new HashMap<>(MAX_SIZE);
    }

    public void set(A key, B value) {
        if (!cache.containsKey(key)) {
            Node<A> n = new Node<>(key);
            n.setNext(head);
            cache.put(key, new Pair<>(value, n));
            head = n;
            if (!n.hasNext()) {
                last = n;
            } else {
                n.getNext().setPrev(n);
            }
        } else {
            Node<A> n = cache.get(key).second();
            if (n != head) {
                if (n.hasPrev()) {
                    n.getPrev().setNext(n.getNext());
                }
                if (n.hasNext()) {
                    n.getNext().setPrev(n.getPrev());
                } else {
                    Node<A> t = last;
                    last = n.getPrev();
                }
                n.setNext(head);
                n.setPrev(null);
                head.setPrev(n);
                head = n;
            }
        }
        if ((size + 1) > MAX_SIZE) {
            cache.remove(last.value);
            if (last.hasPrev()) {
                last.getPrev().setNext(null);
                last = last.getPrev();
            } else {
                System.err.println("WARNING - No previous for Last!");
            }
        } else {
            size++;
        }
    }

    private Optional<B> get(A key) {
        Optional<B> res = Optional.empty();
        if (cache.containsKey(key)) {
            Pair<B, Node<A>> p = cache.get(key);
            Node<A> n = p.second();
            if (n != head) {
                if (n.hasPrev()) {
                    n.getPrev().setNext(n.getNext());
                }
                if (n.hasNext()) {
                    n.getNext().setPrev(n.getPrev());
                } else {
                    Node<A> t = last;
                    last = n.getPrev();
                }
                n.setNext(head);
                n.setPrev(null);
                head.setPrev(n);
                head = n;
            }
            res = Optional.of(p.first());
        }
        return res;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        if (head != null) {
            Node<A> n = head;
            sb.append("(").append(n.value).append(", ").append(cache.get(n.value).first()).append(")");
            while (n.hasNext()) {
                n = n.next;
                sb.append(" -> (").append(n.value).append(", ").append(cache.get(n.value).first()).append(")");
            }
        }
        return "[" + sb.toString() + "]";
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
        public T getValue() {
            return this.value;
        }

        public boolean hasNext() {
            return this.next != null;
        }
        public boolean hasPrev() {
            return this.prev != null;
        }

        @Override
        public String toString() {
            //return "[" + (prev == null ? "" : prev.value) + " <- " + value.toString()+ " -> " + (next == null ? "" : next.value) + "]";
            return value.toString();
        }
    }
}

package ds.common;

//TODO
public class CircularLinkedList<A> {
    private Node<A> head;

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

        @Override
        public String toString() {
            //return "[" + (prev == null ? "" : prev.value) + " <- " + value.toString()+ " -> " + (next == null ? "" : next.value) + "]";
            return value.toString();
        }
    }
}

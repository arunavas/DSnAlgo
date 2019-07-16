package ds.common;

public class Test {

    public static void main(String[] args) {
        Heap mh = new Heap(10, Heap.HeapType.MIN);
        mh.insert(9, false);
        mh.insert(7, false);
        mh.insert(8, false);
        mh.insert(2, false);
        mh.insert(3, false);
        mh.insert(4, false);
        mh.insert(5, false);
        mh.insert(1, false);
        mh.insert(0, false);
        mh.insert(6, false);
        mh.print();
        System.out.println("Min: " + mh.removeExtreme(false));
        mh.print();
        System.out.println("Min: " + mh.removeExtreme(false));
        mh.print();
    }
}

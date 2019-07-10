package test.common;

import java.util.List;

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
        mh.insert(6, true);
        mh.print();
        System.out.println("Max: " + mh.removeMax(true));
        mh.print();
    }
}

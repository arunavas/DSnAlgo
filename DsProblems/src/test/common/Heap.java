package test.common;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.BiFunction;
import java.util.stream.Collectors;

public class Heap {
    public enum HeapType {MIN, MAX}
    private final int[] elems;
    private final int MAX_CAPACITY;
    private int offset;
    private int EXCEPTIONAL_VALUE;

    private final BiFunction<Integer, Integer, Boolean> relationF;
    private final BiFunction<Integer, Integer, Integer> compareF;

    public Heap(int size, HeapType type) {
        this.elems = new int[size];
        this.MAX_CAPACITY = size;
        this.offset = -1;
        if (type == HeapType.MIN) {
            relationF = (parent, child) -> parent > child;
            compareF = Math::min;
            EXCEPTIONAL_VALUE = Integer.MAX_VALUE;
        } else {
            relationF = (parent, child) -> parent < child;
            compareF = Math::max;
            EXCEPTIONAL_VALUE = Integer.MIN_VALUE;
        }
    }

    public int getSize() {
        return this.offset + 1;
    }

    public int getValueOf(int idx) {
        return (idx >= 0 && idx <= offset) ? elems[idx] : EXCEPTIONAL_VALUE;
    }

    public int getParentOf(int idx) {
        return idx == 0 ? -1 : (idx - 1) / 2;
    }

    public int getLeftChildOf(int idx) {
        return idx * 2 + 1;
    }

    public int getRightChildOf(int idx) {
        return idx * 2 + 2;
    }

    public int getMax() {
        return (offset > 0) ? elems[0] : EXCEPTIONAL_VALUE;
    }

    public int removeMax(boolean verbose) {
        int max = EXCEPTIONAL_VALUE;
        if (offset > 0) {
            max = elems[0];
            elems[0] = elems[offset];
            elems[offset] = 0;
            offset--;
            heapify(0, verbose);
        }
        return max;
    }

    public void insert(int n, boolean verbose) {
        if ((offset + 1) < MAX_CAPACITY) {
            offset++;
            elems[offset] = n;
            int cv = n;
            int p = getParentOf(offset);
            int pv = getValueOf(p);
            int step = 1;
            if (verbose) System.out.println("Step " + (step++) + ": " + Arrays.toString(elems));
            while (relationF.apply(pv, cv) && p >= 0) {
                heapify(p, false);
                cv = pv;
                p = getParentOf(p);
                pv = getValueOf(p);
                if (verbose) System.out.println("Step " + (step++) + ": " + Arrays.toString(elems));
            }
        }
    }

    private void heapify(int idx, boolean verbose) {
        if (verbose) System.out.println("heapify: " + Arrays.toString(elems));
        int lc = getLeftChildOf(idx);
        int rc = getRightChildOf(idx);
        int lcv = getValueOf(lc);
        int rcv = getValueOf(rc);
        if (relationF.apply(getValueOf(idx), compareF.apply(lcv, rcv))) {
            if (relationF.apply(rcv, lcv)) {
                swap(idx, lc);
                idx = lc;
            } else {
                swap(idx, rc);
                idx = rc;
            }
            heapify(idx, verbose);
        }
    }

    private void swap(int a, int b) {
        int temp = elems[a];
        elems[a] = elems[b];
        elems[b] = temp;
    }

    /*
    elements @ dept d = (2^d - 1) -> (2^d - 1) + 2^d
    considering x = 2^d => x - 1 -> (x - 1) + x
     */
    public int[] getElemsAtDepth(int d) {
        int[] res = null;
        int x = Double.valueOf(Math.pow(2, d)).intValue();
        int start = x - 1;
        if (start > offset) {
            res = new int[] {};
        } else {
            int end = start + x;
            if (end > offset) {
                end = offset + 1;
            }
            res = new int[end - start];
            int idx = 0;
            //System.out.println("Depth: " + d + " | start: " + start + " | end: " + end + " | size: " + res.length);
            for (int i = start; i < end; i++) {
                res[idx++] = getValueOf(i);
            }
        }
        return res;
    }

    @Override
    public String toString() {
        return Arrays.toString(elems);
    }

    public void print() {
        int max = max(height());
        prettyPrint(0, max);
    }

    private void prettyPrint(int depth, int n) {
        int[] values = getElemsAtDepth(depth);
        if (values == null || values.length == 0) {
            return;
        }
        printSpaces(n / 2);
        System.out.print(values[0]);
        for (int i = 1; i < values.length; i++) {
            printSpaces(n);
            System.out.print(values[i]);
        }
        System.out.println();
        prettyPrint(depth + 1, n / 2);
    }

    private void printSpaces(int n) {
        for (int i = 0; i < n; i++) {
            System.out.print(" ");
        }
    }

    private int height() {
        return Double.valueOf(Math.ceil(Math.log(offset + 1)) + 1).intValue();
    }

    private int max(int height) {
        return Double.valueOf(Math.pow(2, height) - 1).intValue();
    }
}

package ds.algo;

/*
Problem:
Create a class with complete Binary Search Tree to add Node with Root , Left & Right child key (data value) to demonstrate insert operation in binary search tree.
Write a utility function to print the sum of all left child data.
 */
public class BinaryTreeLeftSum {
    public static void main(String[] args) {
        int[] arr = new int[] {25, 50, 75, 15, 35, 65, 85};
        IntNode n = populateBst(arr);
        System.out.println(n.sumLeftNodesOnly());
    }

    private static IntNode populateBst(int[] arr) {
        if (arr == null || arr.length == 0) return null;
        IntNode r = new IntNode(arr[0]);
        for (int i = 1; i < arr.length; i++) {
            r.add(arr[i]);
        }
        return r;
    }
}

class IntNode {
    private int value;
    private IntNode left;
    private IntNode right;

    public IntNode(int value) {
        this.value = value;
    }

    public int getValue() {
        return this.getValue();
    }
    public void setLeft(IntNode l) {
        this.left = l;
    }
    public IntNode getLeft() {
        return this.left;
    }
    public void setRight(IntNode r) {
        this.right = r;
    }
    public IntNode getRight() {
        return this.right;
    }

    public void add(int value) {
        if (this.value <= value) {
            if (right == null) {
                this.right = new IntNode(value);
            } else {
                right.add(value);
            }
        } else {
            if (left == null) {
                this.left = new IntNode(value);
            } else {
                left.add(value);
            }
        }
    }

    public int sumLeftNodesOnly() {
        int sum = 0;
        if (left != null) {
            sum = left.value;
            sum += left.sumLeftNodesOnly();
        }
        if (right != null) {
            sum += right.sumLeftNodesOnly();
        }
        return sum;
    }

    public void print() {
        if (left != null) {
            left.print();
        }
        System.out.print(" " + value);
        if (right != null) {
            right.print();
        }
    }
}
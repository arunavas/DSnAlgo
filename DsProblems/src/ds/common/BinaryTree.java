package ds.common;

import java.util.*;

public class BinaryTree<A> {
    private TreeNode<A> root;
    private final Comparator<A> comparator;
    private int size = 0;
    private int height = 0;
    public BinaryTree(Comparator<A> comparator) {
        this.comparator = comparator;
    }

    public void insert(A value) {
        TreeNode<A> n = new TreeNode<>(value);
        if (root == null) {
            root = n;
        } else {
            insert(root, n);
        }
        size++;
    }
    private void insert(TreeNode<A> root, TreeNode<A> n) {
        if (comparator.compare(root.getValue(), n.getValue()) > 0) {
            //Root is greater, move left
            if (root.getLeft() == null) {
                root.setLeft(n);
            } else {
                insert(root.getLeft(), n);
            }
        } else {
            //Root is smaller/equal, move right
            if (root.getRight() == null) {
                root.setRight(n);
            } else {
                insert(root.getRight(), n);
            }
        }
    }

    public A findValue(A value) {
        return Optional.ofNullable(findNode(value)).map(TreeNode::getValue).orElse(null);
    }
    public TreeNode<A> findNode(A value) {
        return find(root, value);
    }
    private TreeNode<A> find(TreeNode<A> root, A value) {
        if (root == null || comparator.compare(root.getValue(), value) == 0) return root;
        if (comparator.compare(root.getValue(), value) > 0) return find(root.getLeft(), value);
        return find(root.getRight(), value);
    }

    private List<TreeNode<A>> getNextElems(List<TreeNode<A>> input) {
        List<TreeNode<A>> res = new ArrayList<>();
        for (TreeNode<A> n : input) {
            if (n != null) {
                if (n.hasLeft()) {
                    res.add(n.getLeft());
                }
                if (n.hasRight()) {
                    res.add(n.getRight());
                }
            }
        }
        return res;
    }

    @Override
    public String toString() {
        return root == null ? "[]" : root.toString();
    }

    public void print() {
        int max = max(height());
        prettyPrint(Collections.singletonList(root), max);
    }

    private void prettyPrint(List<TreeNode<A>> input, int n) {
        if (input == null || input.isEmpty()) {
            return;
        }
        printSpaces(n / 2);
        System.out.print(input.get(0));
        for (int i = 1; i < input.size(); i++) {
            printSpaces(n);
            System.out.print(input.get(i));
        }
        System.out.println();
        prettyPrint(getNextElems(input), n / 2);
    }

    private void printSpaces(int n) {
        for (int i = 0; i < n; i++) {
            System.out.print(" ");
        }
    }

    private int height() {
        return Double.valueOf(Math.ceil(Math.log(size)) + 1).intValue();
    }

    private int max(int height) {
        return Double.valueOf(Math.pow(2, height) - 1).intValue();
    }

    public static class TreeNode<A> {
        private A value;
        private TreeNode<A> left;
        private TreeNode<A> right;

        public TreeNode(A value) {
            this.value = value;
        }

        public void setLeft(TreeNode<A> n) {
            this.left = n;
        }
        public void setRight(TreeNode<A> n) {
            this.right = n;
        }
        public TreeNode<A> getLeft() {
            return this.left;
        }
        public TreeNode<A> getRight() {
            return this.right;
        }
        public A getValue() {
            return this.value;
        }
        public boolean hasLeft() {
            return this.left != null;
        }
        public boolean hasRight() {
            return this.right != null;
        }

        @Override
        public String toString() {
            return this.value.toString();
        }
    }
}
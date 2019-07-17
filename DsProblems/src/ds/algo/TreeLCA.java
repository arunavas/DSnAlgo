package ds.algo;

import ds.common.BinaryTree;
import ds.common.BinaryTree.TreeNode;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/*
Problem:
Find LCA for list of nodes of a tree

Function defined as below -
TreeNode findLCA(TreeNode root, List<TreeNode> nodes)
 */
public class TreeLCA {
    public static void main(String[] args) {
        BinaryTree<Integer> bt = new BinaryTree<>(Integer::compareTo);
        bt.insert(5);
        bt.insert(3);
        bt.insert(7);
        bt.insert(2);
        bt.insert(4);
        bt.insert(6);
        bt.insert(8);
        bt.print();
        TreeNode<Integer> root = bt.findNode(5);
        System.out.println(findLCA(root, Arrays.asList(3, 5, 4)));
    }

    public static Integer findLCA(TreeNode<Integer> root, List<Integer> values) {
        int res = -1;
        Set<Integer> vals = new HashSet<>(values);
        TreeNode<Integer> result = findLCA(root, vals);
        if (result != null && vals.isEmpty()) {
            res = result.getValue();
        }
        return res;
    }

    private static TreeNode<Integer> findLCA(TreeNode<Integer> root, Set<Integer> values) {
        TreeNode<Integer> result = null;
        if (root != null) {
            if (values.contains(root.getValue())) {
                values.remove(root.getValue());
                result = root;
            }
            TreeNode<Integer> l = findLCA(root.getLeft(), values);
            TreeNode<Integer> r = findLCA(root.getRight(), values);
            //System.out.println(root + " -> " + values + " | " + l + " | " + r);
            if (l != null && r != null) {
                result = root;
            } else if (result == null) {
                if (l != null) {
                    result = l;
                } else if (r != null) {
                    result = r;
                }
            }
        }
        return result;
    }
}

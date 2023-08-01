package com.lw.tree.bst;

import javax.management.NotificationEmitter;
import java.util.Stack;

/*
二分搜索树这里是不包含重复元素的
 */
public class BSTree<E extends Comparable<E>> {
    private class Node {
        public E e;
        public Node left, right;
        public Node(E e) {
            this.e = e;
            left = null;
            right = null;
        }
    }
    private Node root;
    private int size;
    public BSTree() {
        root = null;
        size = 0;
    }

    public int size() {
        return this.size;
    }

    public boolean isEmpty() {
        return this.size == 0;
    }

    public void add(E e) {
        if (root == null) {
            root = new Node(e);
            size++;
        } else {
            add(root, e);
        }
    }


    private void add(Node node, E e) {
        // 递归的终止条件
        if (e.equals(node.e)) {
            return;
        } else if (e.compareTo(node.e) > 0 && node.right == null) {
            node.right = new Node(e);
            size++;
            return;
        } else if (e.compareTo(node.e) < 0 && node.left == null) {
            node.left = new Node(e);
            size++;
            return;
        }

        // 递归调用
        if (e.compareTo(node.e) > 0) {
            add(node.right, e);
        } else {
            add(node.left, e);
        }
    }


    // 插入元素的另一种写法
    public void add2(E e) {
        root = add2(root, e);
    }

    private Node add2(Node node, E e) {
        if (node == null) {
            size++;
            return new Node(e);
        }

        if (e.compareTo(node.e) > 0) {
            node.right = add2(node.right, e);
        } else if (e.compareTo(node.e) < 0) {
            node.left = add2(node.left, e);
        }

        return node;
    }

    public boolean contains(E e) {
        return contains(root, e);
    }

    public boolean contains(Node node, E e) {
        if (node == null) {
            return false;
        }
        if (e.equals(node.e)) {
            return true;
        } else if (e.compareTo(node.e) > 0) {
            return contains(node.right, e);
        } else {
            return contains(node.left, e);
        }
    }

    public void preOrder() {
        preOrder(root);
    }
    private void preOrder(Node node) {
        if (node == null) {
            return;
        }
        System.out.println(node.e);
        preOrder(node.left);
        preOrder(node.right);
    }

    public void preOrderNR() {
        Stack<Node> st = new Stack<>();
        if (root != null) {
            st.push(root);
        }
        while (!st.isEmpty()) {
            Node node = st.pop();
            System.out.println(node.e);
            if (node.right != null) {
                st.push(node.right);
            }
            if (node.left != null) {
                st.push(node.left);
            }
        }
    }

    public E minimum() throws Exception {
        if (root == null) {
            throw new Exception("tree is empty");
        }
        return minimum(root).e;
    }

    public E maximum() throws Exception {
        if (root == null) {
            throw new Exception("tree is empty");
        }
        return maximum(root).e;
    }
    private Node maximum(Node node) {
        if (node.right == null) {
            return node;
        }
        return maximum(node.right);
    }
    private Node minimum(Node node) {
        if (node.left == null) {
            return node;
        }
        return minimum(node.left);
    }

    // 二叉搜索树中删除最小节点, 最大响应修改即可
    public E removeMin() throws Exception {
        E ret = minimum();
        root = removeMin(root);
        return ret;
    }

    private Node removeMin(Node node) {
        // 删除最小值时要考虑右子树的保存问题
        if (node.left == null) {
            Node rightNode = node.right;
            node.right = null;
            size--;
            return rightNode;
        }
        node.left = removeMin(node.left);
        return node;
    }

    public E removeMax() throws Exception {
        E result = maximum();
        root = removeMax(root);
        return result;
    }

    private Node removeMax(Node node) {
        if (node.right == null) {
            Node left = node.left;
            node.left = null;
            size--;
            return left;
        }
        node.right = removeMax(node.right);
        return node;
    }

    // 删除二分搜索树的任意元素
    public void remove(E e) {
        root = remove(root, e);
    }
    private Node remove(Node node, E e) {
        if (node == null) {
            return null;
        }
        if (e.compareTo(node.e) > 0) {
            node.right = remove(node.right, e);
            return  node;
        } else if (e.compareTo(node.e) < 0) {
            node.left = remove(node.left, e);
            return node;
        } else { // 找到了待删除节点

            // 待删除节点左子树为空
            if (node.left == null) {
                Node rightNode = node.right;
                node.right = null;
                size --;
                return rightNode;
            }
            // 待删除节点右子树为空
            if (node.right == null) {
                Node leftNode = node.left;
                node.left = null;
                size --;
                return leftNode;
            }

            // 待删除节点均不为空
            // 找到比删除节点大的最小节点，即待删除节点右子树的最小节点
            // 用这个节点代替待删除节点的位置
            Node successor = minimum(node.right);
            successor.right = removeMin(node.right);
            successor.left = node.left;
            return successor;
        }
    }



}

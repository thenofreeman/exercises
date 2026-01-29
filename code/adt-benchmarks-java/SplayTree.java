import java.util.NoSuchElementException;

public class SplayTree<AnyType extends Comparable<? super AnyType>> implements ContainerType<AnyType> {
    public SplayTree() {
        this.root = null;
    }

    public boolean insert(AnyType value) {
        insertedFlag = true;
        this.root = insertIntoTree(value, this.root, null);

        return insertedFlag;
    }

    public boolean remove(AnyType value) {
        removedFlag = false;
        this.root = removeFromTree(value, this.root);

        return removedFlag;
    }

    public boolean contains(AnyType value) {
        return isContainedInTree(value, root);
    }

    public void print() {
        printTree(this.root);
    }

    private void printTree(TreeNode<AnyType> tree) {
        if (tree != null) {
            printTree(tree.left);
            System.out.print(tree.value + " ");
            printTree(tree.right);
        }
    }

    public void clear() {
        root = null;
    }

    public boolean isEmpty() {
        return root == null;
    }

    public AnyType getMin() {
        if (isEmpty()) {
            throw new NoSuchElementException("Tree is Empty.");
        }

        return getMinNodeInTree(root).value;
    }

    public AnyType getMax() {
        if (isEmpty()) {
            throw new NoSuchElementException("Tree is Empty.");
        }

        return getMaxNodeInTree(root).value;
    }

    private boolean insertedFlag = false; // Used by insert methods
    private boolean removedFlag = false; // Used by remove methods

    private TreeNode<AnyType> root;

    private TreeNode<AnyType> insertIntoTree(AnyType value, TreeNode<AnyType> tree, TreeNode<AnyType> parent) {
        if (tree == null) return new TreeNode<>(value, null, null);

        int comparisonResult = value.compareTo(tree.value);

        if (comparisonResult < 0) {
            tree.left = insertIntoTree(value, tree.left, tree);
        } else if (comparisonResult > 0) {
            tree.right = insertIntoTree(value, tree.right, tree);
        } else {
            // duplicate
            insertedFlag = false;
        }

        return tree;
    }

    private TreeNode<AnyType> removeFromTree(AnyType value, TreeNode<AnyType> tree) {
        if (tree == null) {
            removedFlag = false;
            return tree;
        }

        int comparisonResult = value.compareTo(tree.value);

        if (comparisonResult < 0) {
            tree.left = removeFromTree(value, tree.left);
        } else if (comparisonResult > 0) {
            tree.right = removeFromTree(value, tree.right);
        } else if (tree.left != null && tree.right != null) {
            tree.value = getMinNodeInTree(tree.right).value;
            tree.right = removeFromTree(tree.value, tree.right);
        } else {
            removedFlag = true;
            tree = (tree.left != null)
                ? tree.left
                : tree.right;
        }

        return tree;
    }

    private TreeNode<AnyType> getMinNodeInTree(TreeNode<AnyType> tree) {
        if (tree == null) return tree;

        while (tree.left != null) {
            tree = tree.left;
        }

        return tree;
    }

    private TreeNode<AnyType> getMaxNodeInTree(TreeNode<AnyType> tree) {
        if (tree == null) return tree;

        while (tree.right != null) {
            tree = tree.right;
        }

        return tree;
    }

    private boolean isContainedInTree(AnyType value, TreeNode<AnyType> tree) {
        if (tree == null) return false;

        TreeNode<AnyType> dummyNode = new TreeNode<>(null, null, null);
        TreeNode<AnyType> leftMax = dummyNode;
        TreeNode<AnyType> rightMin = dummyNode;

        while (true) {
            int comparisonResult = value.compareTo(tree.value);

            if (comparisonResult < 0) {
                if (tree.left == null) break;

                if (value.compareTo(tree.left.value) < 0) {
                    TreeNode<AnyType> newTree = tree.left;

                    tree.left = newTree.right;
                    newTree.right = tree;
                    tree = newTree;

                    if (tree.left == null) break;
                }

                rightMin.left = tree;
                rightMin = tree;

                tree = tree.left;
                rightMin.left = null;

            } else if (comparisonResult > 0) {
                if (tree.right == null) break;

                if (value.compareTo(tree.right.value) > 0) {
                    TreeNode<AnyType> newTree = tree.right;

                    tree.right = newTree.left;
                    newTree.left = tree;
                    tree = newTree;

                    if (tree.right == null) break;
                }

                leftMax.right = tree;
                leftMax = tree;

                tree = tree.right;
                leftMax.right = null;

            } else {
                break;
            }
        }

        leftMax.right = tree.left;
        rightMin.left = tree.right;
        tree.left = dummyNode.right;
        tree.right = dummyNode.left;

        this.root = tree;

        return value.compareTo(tree.value) == 0;
    }

    private static class TreeNode<AnyType> {
        TreeNode(
            AnyType x,
            TreeNode<AnyType> left,
            TreeNode<AnyType> right
        ) {
            this.value = x;
            this.left = left;
            this.right = right;
        }

        AnyType value;
        TreeNode<AnyType> left;
        TreeNode<AnyType> right;
    }

}

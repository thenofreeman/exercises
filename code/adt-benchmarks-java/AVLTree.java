import java.util.NoSuchElementException;

public class AVLTree<AnyType extends Comparable<? super AnyType>> implements ContainerType<AnyType> {
    public AVLTree() {
        this.root = null;
    }

    public boolean insert(AnyType value) {
        insertedFlag = true;
        this.root = insertIntoTree(value, this.root);

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

    private TreeNode<AnyType> balanceTree(TreeNode<AnyType> tree) {
        if (tree == null) return tree;

        if (heightOfTree(tree.left) - heightOfTree(tree.right) > MAXIMUM_IMBALANCE) {
            if (heightOfTree(tree.left.left) >= heightOfTree(tree.left.right)) {
                tree = rotateTreeOnLeftChild(tree);
            } else { // Double Rotation
                tree.left = rotateTreeOnRightChild(tree.left);
                tree = rotateTreeOnLeftChild(tree);
            }
        } else if (heightOfTree(tree.right) - heightOfTree(tree.left) > MAXIMUM_IMBALANCE) {
            if (heightOfTree(tree.right.right) >= heightOfTree(tree.right.left)) {
                tree = rotateTreeOnRightChild(tree);
            } else { // Double Rotation
                tree.right = rotateTreeOnLeftChild(tree.right);
                tree = rotateTreeOnRightChild(tree);
            }
        }

        tree.height = 1 + Math.max(
            heightOfTree(tree.left),
            heightOfTree(tree.right)
        );

        return tree;
    }

    private static final int MAXIMUM_IMBALANCE = 1;
    private boolean insertedFlag = false; // Used by insert methods
    private boolean removedFlag = false; // Used by remove methods

    private TreeNode<AnyType> root;

    private TreeNode<AnyType> insertIntoTree(AnyType value, TreeNode<AnyType> tree) {
        if (tree == null) return new TreeNode<>(value, null, null);

        int comparisonResult = value.compareTo(tree.value);

        if (comparisonResult < 0) {
            tree.left = insertIntoTree(value, tree.left);
        } else if (comparisonResult > 0) {
            tree.right = insertIntoTree(value, tree.right);
        } else {
            // duplicate
            insertedFlag = false;
        }

        return balanceTree(tree);
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

        return balanceTree(tree);
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
        while (tree != null) {
            int comparisonResult = value.compareTo(tree.value);

            if (comparisonResult < 0) {
                tree = tree.left;
            } else if (comparisonResult > 0) {
                tree = tree.right;
            } else {
                return true;
            }
        }

        return false;
    }

    private int heightOfTree(TreeNode<AnyType> tree) {
        return tree == null ? -1 : tree.height;
    }

    private TreeNode<AnyType> rotateTreeOnLeftChild(TreeNode<AnyType> tree) {
        TreeNode<AnyType> newParent = tree.left;
        tree.left = newParent.right;
        newParent.right = tree;

        tree.height = 1 + Math.max(
            heightOfTree(tree.left),
            heightOfTree(tree.right)
        );
        tree.height = 1 + Math.max(
            heightOfTree(newParent.left),
            tree.height
        );

        return newParent;
    }

    private TreeNode<AnyType> rotateTreeOnRightChild(TreeNode<AnyType> tree) {
        TreeNode<AnyType> newParent = tree.right;
        tree.right = newParent.left;
        newParent.left = tree;

        tree.height = 1 + Math.max(
            heightOfTree(tree.left),
            heightOfTree(tree.right)
        );
        tree.height = 1 + Math.max(
            heightOfTree(newParent.right),
            tree.height
        );

        return newParent;
    }

    private static class TreeNode<AnyType> {
        TreeNode(AnyType x, TreeNode<AnyType> left, TreeNode<AnyType> right) {
            this.value = x;
            this.left = left;
            this.right = right;
            this.height = 0;
        }

        AnyType value;
        TreeNode<AnyType> left;
        TreeNode<AnyType> right;
        int height;
    }

}

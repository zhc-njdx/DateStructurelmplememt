class BinaryNode extends Node{

    BinaryNode leftChild;
    BinaryNode rightChild;

    BinaryNode(int value) {
        super(value);
        leftChild = null;
        rightChild = null;
    }

    BinaryNode(int value, BinaryNode leftChild, BinaryNode rightChild){
        super(value);;
        this.leftChild = leftChild;
        this.rightChild = rightChild;
    }
}

public class Tree {

    BinaryNode root;

    Tree(int value){
        root = new BinaryNode(value);
    }

    /**
     * 先序遍历: 先树根、左子树、右子树
     * @return 遍历的字符串
     */
    public String PreOrderTraversal(BinaryNode node){
        StringBuilder traversal = new StringBuilder();
        traversal.append(node.value);
        if (node.leftChild != null)
            traversal.append(PreOrderTraversal(node.leftChild));
        if (node.rightChild != null)
            traversal.append(PreOrderTraversal(node.rightChild));
        return traversal.toString();
    }

    public String InorderTraversal(BinaryNode node){
        StringBuilder traversal = new StringBuilder();
        if (node.leftChild != null)
            traversal.append(InorderTraversal(node.leftChild));
        traversal.append(node.value);
        if (node.rightChild != null)
            traversal.append(InorderTraversal(node.rightChild));
        return traversal.toString();
    }

    public String PostOrderTraversal(BinaryNode node){
        StringBuilder traversal = new StringBuilder();
        if (node.leftChild != null)
            traversal.append(PostOrderTraversal(node.leftChild));
        if (node.rightChild != null)
            traversal.append(PostOrderTraversal(node.rightChild));
        traversal.append(node.value);
        return traversal.toString();
    }

    public String LevelOrderTraversal(){
        // 需要队列

        return null;
    }
}

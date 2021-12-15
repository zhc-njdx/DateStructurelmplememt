public class Node {
    int value;
    boolean isActive; // 为HashTable服务

    Node(int value){
        this.value = value;
        this.isActive = true;
    }

    Node(int value, boolean isActive){
        this.value = value;
        this.isActive = isActive;
    }

    public int compareTo(Node x){
        return value > x.value ? 1 : -1;
    }
}

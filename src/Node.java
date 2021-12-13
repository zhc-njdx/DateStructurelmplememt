public class Node {
    int value;

    Node(int value){
        this.value = value;
    }

    public int compareTo(Node x){
        return value > x.value ? 1 : -1;
    }
}

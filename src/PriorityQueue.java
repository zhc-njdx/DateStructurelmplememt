/**
 * minHeap
 */
class Heap{

    private Node[] heap;
    int currentSize; //堆当前的长度
    int maxSize; // 堆的最大空间
    int layers;  // 堆的层数

    Heap(int maxLen){
        maxSize = maxLen;
        this.heap = new Node[maxLen + 1];
        currentSize = 0;
    }

    /**
     * 生成堆算法 O(n)
     * @param arr 堆元素
     * @param maxLen 堆的最大长度
     */
    Heap(int[] arr,int maxLen){
        maxSize = maxLen;
        currentSize = arr.length;
        this.heap = new Node[maxLen+1];
        for (int i = 0; i < arr.length; ++i)
            heap[i+1] = new Node(arr[i]);
        for (int j = currentSize / 2; j >= 1; --j)
            percolateDown(j);
    }

    /**
     * 生成堆算法 O(nlogn)
     * @param arr 堆元素
     * @param maxLen 堆最大长度
     */
    public void createHeap(int[] arr, int maxLen)
    {
        this.heap = new Node[maxLen+1];
        for (int j : arr)
            insert(new Node(j));
    }

    public void upgradeLayers(){
        int layers = 0;
        while (Math.pow(2,layers) - 1 <= currentSize)
            layers++;
        this.layers = layers;
    }

    /**
     * 插入操作
     * @param x 待插入的节点
     * @return 插入后的堆，方便insert操作能够连续进行
     */
    public Heap insert(Node x){
        if (currentSize == maxSize)
            throw new ArrayIndexOutOfBoundsException("Heap is full!");
        heap[++currentSize] = x;
        upgradeLayers();
        percolateUp(currentSize);
        return this;
    }

    /**
     * 删除操作
     * @return 删除后的堆
     */
    public Node deleteMin(){
        if (currentSize == 0)
            throw new NullPointerException("Heap is empty!");
        Node min = heap[1];
        heap[1] = heap[currentSize--];
        upgradeLayers();
//        heap[currentSize] = null;
        percolateDown(1);
        return min;
    }

    /**
     * 下滤操作
     * @param index 做下滤操作的元素下标
     */
    private void percolateDown(int index){
        // 这里不能和上滤一样用while循环
        // 因为上滤是一定有父元素的
        // 下滤不一定有子元素，或不一定有右子节点
        // 应该采用for循环
        int child;
        for (; index * 2 <= currentSize; index = child)
        {
            child = 2 * index;
            // 如果有右子节点并且右子节点更小
            if (child != currentSize && heap[child+1].compareTo(heap[child]) < 0)
                child++;
            if (heap[child].compareTo(heap[index]) < 0)
            {
                Node tmp = heap[index];
                heap[index] = heap[child];
                heap[child] = tmp;
            }
            else
                break;
        }
    }

    /**
     * 上滤操作
     * @param index 做上滤操作的元素下标
     */
    private void percolateUp(int index){
        while (index != 1 && heap[index / 2].compareTo(heap[index]) > 0)
        {
            Node tmp = heap[index/2];
            heap[index/2] = heap[index];
            heap[index] = tmp;
            index /= 2;
        }
    }

    public void print(){
        int layer = 0;
        for (int i = 1; i <= currentSize; ++i){
            if (layer < layer(i))
            {
                System.out.println();
                layer = layer(i);
            }
            System.out.print(heap[i].value+" ");
        }
    }

    private int layer(int symbol){
        int layer = 0;
        while (Math.pow(2,layer) <= symbol)
            layer++;
        return layer - 1;
    }
}

/**
 * 优先队列建立在堆的基础上
 */
public class PriorityQueue {

    private final Heap priorityQueue;

    PriorityQueue(int maxLen){
        priorityQueue = new Heap(maxLen);
    }

    PriorityQueue(int[] arr, int maxLen){
        priorityQueue = new Heap(arr, maxLen);
    }

    public void insert(Node x){
        priorityQueue.insert(x);
    }

    public Node delete(){
        return priorityQueue.deleteMin();
    }

}

/**
 * 关于堆排序就是使用 O(n) 的生成堆算法 然后不断地调用 deleteMin 函数
 */

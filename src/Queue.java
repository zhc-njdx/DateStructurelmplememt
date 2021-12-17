/**
 * queue is a linear list where additions and deletions take place
 * at different ends
 * is called FIFO(first-in-first-out)list
 * the end where new elements are added is called the rear
 * the end where old elements are deleted is called the front
 */
public class Queue {

    private final LinkedQueue queue;

    Queue(){
        queue = new LinkedQueue();
    }

    public void enqueue(QueueNode x){
        queue.enqueue(x);
    }

    public QueueNode dequeue(){
        return queue.dequeue();
    }

    public void makeEmpty(){
        queue.makeEmpty();
    }
}

/**
 * 数组实现的队列
 */
class ArrayQueue {

    Data[] queue;
    int rear;
    int front;
    int currentSize;
    int maxSize;

    ArrayQueue(int maxSize){
        this.maxSize = maxSize;
        currentSize = 0;
        queue = new Data[maxSize];
        rear = -1;
        front = 0;
    }

    public void enqueue(Data x){
        if (currentSize == maxSize)
            throw new OutOfMemoryError("Queue is full!"); // 队列满了
        queue[rear] = x;
        rear = (rear + 1) % maxSize; // 形成一个循环数组
        currentSize++;
    }

    public Data dequeue(){
        if (currentSize == 0)
            throw new NullPointerException("Queue is empty!");
        Data tmp = queue[front];
        front = (front + 1) % maxSize; // 形成一个循环数组
        currentSize--;
        return tmp;
    }

    public void makeEmpty(){
        currentSize = 0;
        front = 0;
        rear = -1;
    }
}

class QueueNode {

    Data data;
    QueueNode next;

    QueueNode(int value){
        data = new Data(value);
        next = null;
    }
    QueueNode(char value){
        data = new Data(value);
        next = null;
    }

}

/**
 * 链表实现的队列
 */
class LinkedQueue {
    QueueNode front; // 队首 值无意义
    QueueNode rear; // 队尾 值无意义
    int currentSize;

    LinkedQueue(){
        front = rear = null;
        currentSize = 0;
    }

    public void enqueue(QueueNode x){
        if (currentSize == 0)
            front = x;
        else
            rear.next = x;
        rear = x;
        currentSize++;
    }

    public QueueNode dequeue(){
        if (currentSize == 0)
            throw new NullPointerException("Queue is empty!");
        QueueNode tmp = front;
        front = front.next;
        currentSize--;
        return tmp;
    }

    public void makeEmpty(){
        front = rear = null;
    }
}

/**
 * 打印杨辉三角
 * 程序很精妙(摘自PPT)
 */
class YangHui{
    static int n = 5;
    public static void main(String[] args){
        Queue queue = new Queue();
        queue.enqueue(new QueueNode(1));
        queue.enqueue(new QueueNode(1));
        int s = 0;
        for (int i = 1; i <= n; ++i){

            System.out.println();
            // 美观
            for (int k = 1; k <= 20-i; ++k)
                System.out.print(" ");
            // 加 s=0 以及 队列里加个0 是为了通过两两相加算出下一行
            queue.enqueue(new QueueNode(0));
            // 一次循环打印一行
            // 每次循环结束 s = 0  队列里有 下一行的数字和0
            for (int j = 1; j <= i + 2; ++j){
                int t = queue.dequeue().data.intValue;
                queue.enqueue(new QueueNode(s+t));
                s = t;
                if (j != i + 2) // j == i + 2 时 s = 0
                    System.out.print(s+" ");
            }
        }
    }
}
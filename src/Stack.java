/**
 * stack is a list where insertions and deletions take place
 * at the same end.
 * the end is called the top, while the other end of the list is
 * called the bottom
 * is also called a LIFO(last-in-first-out)list
 *
 * Two implements:
 * 1.Linked list
 * 2.Array
 */
public class Stack {
    private final ArrayStack arrayStack;

    Stack(int maxSize){
        arrayStack = new ArrayStack(maxSize);
    }

    public Data pop(){
        return arrayStack.pop();
    }

    public void push(Data x){
        arrayStack.push(x);
    }

    public Data top(){
        return arrayStack.top();
    }

    public int getCurrentSize(){return arrayStack.stackSize;}
}

/**
 * Array implement Stack
 */
class ArrayStack {
    Data[] stack;
    int stackTop;
    int stackMaxSize;
    int stackSize;

    ArrayStack(int maxSize){
        stackMaxSize = maxSize;
        stack = new Data[stackMaxSize];
        stackTop = -1;
        stackSize = 0;
    }

    public Data pop(){
        if (stackSize == 0)
            throw new NullPointerException("Stack is empty!");
        Data tmp = stack[stackTop--];
        stackSize--;
        return tmp;
    }

    public void push(Data x){
        if (stackSize == stackMaxSize)
            throw new StackOverflowError("Stack is full!");
        stack[++stackTop] = x;
        stackSize++;
    }

    public Data top(){
        if (stackSize == 0)
            throw new NullPointerException("Stack is empty!");
        return stack[stackTop];
    }

}

class StackNode {

    Data data;
    StackNode next;

    StackNode(int value){
        data = new Data(value);
        next = null;
    }

    StackNode(char value){
        data = new Data(value);
        next = null;
    }
}

/**
 * Linked List implement Stack
 */
class LinkedStack {

    StackNode StackTop; // 栈顶节点，不存放值
    int StackSize;
    int StackMaxSize;

    LinkedStack(int maxSize){
        StackTop = new StackNode(0);
        StackSize = 0;
        StackMaxSize = maxSize;
    }

    /**
     * 弹出栈顶元素
     * @return 栈顶元素
     */
    public StackNode pop(){
        if (StackTop.next == null)
            return null; // 栈为空
        StackNode tmp = StackTop.next;
        StackTop.next = StackTop.next.next;
        StackSize--;
        return tmp;
    }

    /**
     * 压栈
     * @param x 压栈元素
     */
    public void push(StackNode x){
        if (StackSize == StackMaxSize)
            throw new StackOverflowError("Stack is Full!"); // 栈满了
        x.next = StackTop.next;
        StackTop.next = x;
        StackSize++;
    }

    /**
     * 查看栈顶元素
     * @return 栈顶元素
     */
    public StackNode top(){
        return StackTop.next;
    }
}

/**
 * 匹配符号
 */
class MatchParenthesis{
    static String expr = "(1+2)+(2*3)-())";
    public static void main(String[] args){
        Stack stack = new Stack(100);
        for (int i = 0; i < expr.length(); ++i){
            if (expr.charAt(i) == '(')
                while (expr.charAt(i) != ')')
                    stack.push(new Data(expr.charAt(i++)));
            while (expr.charAt(i) == ')'){
                char c = ' ';
                try{
                    c = stack.pop().charValue;
                } catch (NullPointerException e){
                    System.out.println("The expr is illegal!");
                    return;
                }
                if (c == '(')
                    break;
            }
        }
        if (stack.getCurrentSize() == 0)
            System.out.println("The expr is valid!");
        else
            System.out.println("The expr is illegal!");
    }
}
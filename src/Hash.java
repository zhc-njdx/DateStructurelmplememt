import java.util.Arrays;

/**
 * Hash Method
 * 使得查找的时间复杂度降为O(1)
 * (顺序查找:O(n) 二分查找:O(logn))
 * 散列表的关键在于散列函数: address = hash(key)
 * 散列函数的关键在于如何解决collision: select a suitable A.
 * A = n / b
 * n: 散列表中元素的个数  b: 散列表中空位的个数
 * 如果 A > 1 碰撞概率大 A < 1 碰撞概率小
 *
 * 几种 Hash Function
 * 1、取余法: H(key) = key % M 其中 M 是 <= 表长的最大的质数
 * 2、平方取中法 H(key) = key^2 的中间几位
 * 3、乘法杂凑法 H(key) = =  M * ((  * Key ) % 1 )向下取整 其中 M 是表长 是一个常数
 * 4、to add up the ASCII(or Unicode) value of the characters in the String
 * 5、H(key) = k0 + 37k1 + 37^2k2 + .....
 *
 * 关键: how to solve a collision!!!
 * 此份代码主要使用Open Addressing
 * 1、Open Addressing 开放地址探测法
 *      1)linear probing 线性探测法
 *      如果散列函数算出来的位置已经被占了的话，就向后依次(d+1,d+2,...,d-1)检查直到空位
 *      2)Quadratic probing 平方探测法
 *      向后以d+1,d+2^2,d+3^2,...来找空位
 *      3)双重散列
 * 2、Separate Chaining 散列表加链表
 *      Separate Chaining 代码结构不一致  不需要 isEmpty  要用Node  冲突了直接向外用链表延伸即可
 */

public class Hash {

    int[] table;
    boolean[] isEmpty; // 为查找操作设立，删除元素不能直接删除，而是将isEmpty置true
    int D; // 取余法的除数
    int currentSize;
    int maxSize;

    Hash(int len)
    {
        initialize(len);
    }

    // 进行散列表的初始化
    private void initialize(int len){
        maxSize = len;
        setDivisor();
        table = new int[len];
        isEmpty = new boolean[len];
        Arrays.fill(isEmpty,true);
        currentSize = 0;
    }

    // 设置取余法的除数
    private void setDivisor(){
        int divisor = maxSize;
        while (!isPrime(divisor))
            divisor--;
        D = divisor;
    }

    // 判断一个数是否是质数
    private boolean isPrime(int x){
        if (x == 2) return true;
        for (int i = 2; i < x; ++i) {
            if (x % i == 0)
                return false;
        }
        return true;
    }


    /**
     * 重新散列
     * 当表项数>表的70%时，可再散列
     * 取比(2*原表长)大的质数再散列
     */
    public void reHash(){
        // 判断是否需要重新散列
        if (currentSize <= 0.7 * maxSize)
            return;
        // 确定新散列表的长度
        int new_maxSize = 2 * maxSize;
        while (!isPrime(new_maxSize))
            new_maxSize++;
        // 保存原散列表
        int[] old_table = table;
        boolean[] old_isEmpty = isEmpty;
        // 重新初始化
        initialize(new_maxSize);
        // 将原来散列表中的元素插入新表中
        for (int i = 0; i < old_table.length; ++i)
            if (!old_isEmpty[i])
                Insert(old_table[i]);
    }

    // 散列函数
    private int HashFunction(int x) {
        return x % D;
    }

    // 用于DoubleHash的第二个散列函数
    private int HashFunction2(int x){
        return 5 - x % 5;
    }

    /**
     * LinearProbing
     * examine successive d+1, d+2, d+3, ....
     */
    private int LinearProbing(int position, int x){
        int i = position;
        do {
            if (isEmpty[i] || table[i] == x)
                return i; // 如果找到空位返回 如果找到相同元素也返回
            i = (i + 1) % maxSize;
        } while (i != position);
        return position; // 表满了
    }

    /**
     * QuadraticProbing
     * examine successive d+1, d+2^2, d+3^2,....
     */
    private int QuadraticProbing(int position, int x){
        int factor = 1;
        int i = position;
        int times = 0;
        do {
            if (isEmpty[i] || table[i] == x)
                return i;
            i = (int) (position + Math.pow(factor,2)) % maxSize;
            factor++;
            times++;
        } while (times < maxSize); // 由于是跳跃式地遍历散列表，可能回不到position，甚至是一直循环遍历某几个位置
        return position; // 表满了
    }

    /**
     * DoubleHash
     * c = HashFUnction2(x)
     * position = d
     * examine successive d+c, d+2c, d+3c,....
     */
    private int DoubleHash(int position, int x){
        int c = HashFunction2(x);
        int i = position;
        do {
            if (isEmpty[i] || table[i] == x)
                return i;
            i = (i + c) % maxSize;
        } while (i != position);
        return position;
    }

    // 处理冲突的策略，封装在这个函数里，改变策略时在这里修改函数即可
    private int collisionDeal(int position, int x){
//        return LinearProbing(position,x);
        return QuadraticProbing(position,x);
//        return DoubleHash(position,x);
    }

    // 搜索函数，找到返回散列表中位置，反之返回-1
    public int Search(int x){
        int position = HashFunction(x);
        // 前提还得是不为空
        if (!isEmpty[position] && table[position] == x)
            return position;
        int new_position = collisionDeal(position,x);
        if (!isEmpty[new_position] && table[new_position] == x)
            return new_position;
        return -1; // 没找到
    }

    // 插入函数
    public void Insert(int x){
        int position = HashFunction(x);
        if (isEmpty[position]){
            table[position] = x;
            isEmpty[position] = false;
        }
        else {
            int new_position = collisionDeal(position, x);
            if (new_position == position)
                throw new OutOfMemoryError("HashTable is Full!"); // 表满
            if (table[new_position] == x)
                return; // 元素已在表中
            table[new_position] = x;
            isEmpty[new_position] = false;
        }
        currentSize++;
        reHash();
    }

    // 删除函数
    public void delete(int x){
        int position = Search(x);
        if (position != -1){
            isEmpty[position] = true;
            currentSize--;
        }
    }

    public void print(){
        for (int i = 0; i < maxSize; ++i){
            System.out.print(i + "  ");
            if (!isEmpty[i])
                System.out.print(table[i]);
            else
                System.out.print(" ");
            System.out.println();
        }
    }

}

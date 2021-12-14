import java.util.Arrays;

public class DisjointSet {

    /**
     * 增加一个root数组
     * 该数组用来判断是否是根节点
     * parent数组做修改
     * 如果是根节点，parent数组里放这棵树的节点数
     * 如果不是根节点，parent数组放其根节点
     */
    private final boolean[] root;
    private final int[] parent;

    DisjointSet(int numElements){
        root = new boolean[numElements];
        parent = new int[numElements];
        Arrays.fill(parent,-1);
        Arrays.fill(root,true);
    }

    public int Find(int x){
        while (!root[x])
            x = parent[x];
        return x;
    }

    /**
     * 另外一种改善策略是针对Find操作
     * 在Find的过程中，将路径上的所有节点全部连到根节点上，降低树的高度
     */
    public int Find1(int x){
        if (root[x])
            return x;
        return parent[x] = Find1(parent[x]);
    }

    /**
     * x 和 y 都是 根节点
     * 改善Union，防止Union函数使得树不平衡
     * 之前的Union默认将y连到x上面，就会可能导致N节点形成N-1高的树
     * 会使得Find操作的时间复杂度增加
     * 所以要进行改进，使得树尽可能平衡
     * 两个策略：
     * 1、Weight rule
     *      根据两边的节点数来决定谁连到谁上面
     *      此份代码选择的是Weight rule
     * 2、Height rule
     *      根据两边的高度来决定谁连到谁上面
     *      实现:对parent数组的不同修改策略，parent数组中根节点的值为负数，而且绝对值是其高度。
     */
    public void Union(int x,int y){
//        parent[y] = x;
        if (parent[x] > parent[y]){
            parent[x] += parent[y];
            parent[y] = x;
            root[y] = false;
        }
        else {
            parent[y] += parent[x];
            parent[x] = y;
            root[x] = false;
        }
    }
}

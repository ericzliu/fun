package spoj.gss1;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class SegmentTree {
    int N;
    int[] elements;
    int[] sum;
    int[] max_l_sum;
    int[] max_r_sum;
    int[] max_sum;
    int[] lo;
    int[] hi;

    public SegmentTree(int[] elements) {
        this.N = elements.length;
        this.elements = elements;
        this.sum = new int[2 * N];
        this.max_l_sum = new int [2 * N];
        this.max_r_sum = new int [2 * N];
        this.max_sum = new int[2 * N];
        this.lo = new int[2 * N];
        this.hi = new int[2 * N];
    }
    
    public int getLBound(int node) {
        return lo[node];
    }
    
    public int getUBound(int node) {
        return hi[node];
    }
    
    public void buildTree(int node, int l, int r) {
        if (r == l) {
            setLeaf(node, l);
        }
        else {
            int mid = l + (r - l) / 2;
            buildTree(node * 2, l, mid);
            buildTree(node * 2 + 1, mid + 1, r);
            calc(node, l, r);
        }
    }
    
    public void setLeaf(int node, int p) {
        sum[node] = elements[p];
        max_l_sum[node] = elements[p];
        max_r_sum[node] = elements[p];
        max_sum[node] = elements[p];
        lo[node] = p;
        hi[node] = p;
    }

    public void calc(int node, int l, int h) {
        sum[node] = sum[node * 2] + sum[node * 2 + 1];
        max_l_sum[node] = Math.max(max_l_sum[node * 2], sum[node * 2] + max_l_sum[node * 2 + 1]);
        max_r_sum[node] = Math.max(max_r_sum[node * 2 + 1], sum[node * 2 + 1] + max_r_sum[node * 2]);
        max_sum[node] = Math.max(max_sum[node * 2], max_sum[node * 2 + 1]);
        max_sum[node] = Math.max(max_sum[node], max_r_sum[node * 2] + max_l_sum[node * 2 + 1]);
        lo[node] = l;
        hi[node] = h;
    }
    
    public int query(int l, int r) {
        List<Integer> nodes = new ArrayList<>();
        query_rec(1, l, r, nodes);
        int t = 0;
        int result = Integer.MIN_VALUE;
        for (Integer node : nodes)
        {
            result = Math.max(result, max_sum[node]);
            result = Math.max(result, t + max_l_sum[node]);
            t = Math.max(t + sum[node], max_r_sum[node]);
        }
        return result;
    }

    public void query_rec(int node, int l, int r, List<Integer> nodes) {
        if (lo[node] >= l && hi[node] <= r) {
            nodes.add(node);
        }
        else {
            int mid = lo[node] + (hi[node] - lo[node]) / 2;
            if (mid >= l) {
                query_rec(node * 2, l, r, nodes);
            }
            if (mid < r) {
                query_rec(node * 2 + 1, l, r, nodes);
            }
        }
    }
    
}

public class Main {

    public static void main(String[] args) throws java.lang.Exception {
        Scanner scan = new Scanner(System.in);
        int N = scan.nextInt();
        int[] elements = new int[N];
        for (int i = 0; i < N; i++) {
            elements[i] = scan.nextInt();
        }
        SegmentTree instance = new SegmentTree(elements);
        instance.buildTree(1, 0, N - 1);
        int M = scan.nextInt();
        for (int i = 0; i < M; i++) {
            int x = scan.nextInt();
            int y = scan.nextInt();
            int result = instance.query(x - 1, y - 1);
            System.out.println(result);
        }
    }
}

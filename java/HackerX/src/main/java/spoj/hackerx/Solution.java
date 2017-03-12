package spoj.hackerx;

import java.io.*;
import java.util.*;

public class Solution {

    public static <T> int ceilIndex(T[] elements, int lo, int hi, T element, Comparator<T> comp) {
        while (lo < hi) {
            int mid = lo +( hi - lo) / 2;
            if (comp.compare(elements[mid], element) != -1) {
                hi = mid;
            }
            else {
                lo = mid + 1;
            }
        }
        if (comp.compare(elements[lo], element) != -1) {
            return lo;
        }
        throw new IllegalArgumentException();
    }

    static <T> int LIS(T[] a, Comparator<T> comp) {
        int size = a.length;
        if (size == 0)
            return 0;
        T[] tail = (T[])new Object[size];
        tail[0] = a[0];
        int len = 1;
        for (int i = 1; i < size; ++i) {
            if (comp.compare(a[i], tail[0]) == -1) {
                tail[0] = a[i];
            }
            else if (comp.compare(a[i], tail[len-1]) == 1) {
                tail[len++] = a[i];
            }
            else {
                tail[ceilIndex(tail, 0, len - 1, a[i], comp)] = a[i];
            }
        }
        return len;
    }
    
    static class V {

        private final int a;
        private final int b;

        public V(int t, int f) {
            this.a = t + f;
            this.b = t - f;
        }
        
        public int getA() {
            return a;
        }

        public int getB() {
            return b;
        }
    }
    
    public static int solve(InputStream in) {
        Scanner scan = new Scanner(in);
        int N = scan.nextInt();
        int[] T = new int[N];
        int[] F = new int[N];
        V[] vertices = new V[N];
        for (int i = 0; i < N; i++) {
            T[i] = scan.nextInt();
            F[i] = scan.nextInt();
            vertices[i] = new V(T[i], F[i]);
        }
        Arrays.sort(vertices, (V o1, V o2) -> {
            if (o1.getA() < o2.getA())
                return -1;
            if (o1.getA() > o2.getA())
                return 1;
            if (o1.getB() < o2.getB())
                return -1;
            if (o1.getB() > o2.getB())
                return 1;
            return 0;
        });
        return LIS(vertices, (V o1, V o2) -> {
            if (o1.getB() > o2.getB())
                return -1;
            if (o1.getB() == o2.getB())
                return 0;
            return 1;
        });
    }

    public static void main(String[] args) {
        System.out.println(solve(System.in));
    }
}

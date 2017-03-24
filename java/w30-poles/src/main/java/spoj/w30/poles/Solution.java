/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package spoj.w30.poles;
import java.util.*;

class StraightLine {
    long slope;
    long intercept;

    public static StraightLine create(long slope, long intercept) {
        return new StraightLine(slope, intercept);
    }

    public StraightLine(long slope, long intercept) {
        this.slope = slope;
        this.intercept = intercept;
    }

    public long getValue(long x) {
        return slope * x + intercept;
    }

    public double getIntersectX(StraightLine line) {
        return (double) (line.intercept - intercept) / (slope - line.slope);
    }
}

class LineSet {

    LinkedList<StraightLine> lines = new LinkedList<>();

    int size() {
        return lines.size();
    }

    // Lines are added in slope increasing order
    public void addLine(StraightLine line) {
        while (lines.size() >= 2) {
            StraightLine l2 = lines.removeLast();
            StraightLine l1 = lines.peekLast();
            double x3 = line.getIntersectX(l1);
            double x2 = l2.getIntersectX(l1);
            if (x3 > 0 && x2 > 0 && x3 < x2) {
                lines.add(l2);
                break;
            }
        }

        if (lines.size() > 1) {
            lines.add(line);
        }
        else if (lines.size() == 1) {
            double x3 = line.getIntersectX(lines.peekLast());
            if (x3 > 0) {
                lines.add(line);
            }
        }
        else {
            lines.add(line);
        }
    }

    // x are queried in decreasing order
    public long getMinVal(long xVal) {
        while (lines.size() > 1) {
            StraightLine l1 = lines.poll();
            StraightLine l2 = lines.peek();
            long y1 = l1.getValue(xVal);
            long y2 = l2.getValue(xVal);
            if (y1 < y2) {
                lines.addFirst(l1);
                break;
            }
        }
        return lines.peek().getValue(xVal);
    }
}

public class Solution {

    long[] pos;
    long[] weight;
    long[] A;
    long[] B;
    long[][] F;

    public long cost(long[] X, long[] W, int K) {
        int N = X.length - 1;
        pos = X;
        weight = W;
        A = new long[N + 1];
        B = new long[N + 1];
        F = new long[N + 1][2];

        for (int i = 1; i <= N; ++i) {
            B[i] = B[i - 1] + pos[i] * weight[i];
            A[i] = A[i - 1] + weight[i];
            F[i][1] = B[i] - pos[i] * A[i];
        }

        for (int k = 2; k <= K; ++k) {
            LineSet e = new LineSet();
            for (int n = 1; n <= N; ++n) {
                F[n][k & 1] = B[n] - pos[n] * A[n];
                if (n > 1)
                    e.getMinVal(pos[n]);
                e.addLine(new StraightLine(A[n - 1], F[n - 1][(k - 1) & 1] - B[n - 1]));
            }
        }
        return F[N][K & 1];
    }

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int N = in.nextInt();
        int K = in.nextInt();
        long[] pos = new long[N + 1];
        long[] weight = new long[N + 1];
        for(int a0 = 0; a0 < N; a0++){
            pos[N - a0] = in.nextInt();
            weight[N - a0] = in.nextInt();
        }

    }
}

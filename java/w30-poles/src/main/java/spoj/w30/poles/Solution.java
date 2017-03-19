/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package spoj.w30.poles;

import java.util.Arrays;
import java.util.Scanner;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

public class Solution {

    private static final Logger LOG = LogManager.getLogger(Solution.class);

    public static int cost(int[] X, int[] W, int K) {
        int N = X.length;
        int[][] P = new int[N][N];
        int[][] C = new int[N][K + 1];

        for (int i = 0; i < N; i++) {
            for (int j = i + 1; j < N; j++) {
                P[i][j] = P[i][j - 1] + W[j] * (X[j] - X[i]);
            }
        }

        for (int i = 0; i < N - 1; i++) {
            C[i][1] = P[i][N - 1];
        }

        for (int k = 2; k <= K; k++) {
            for (int i = 0; i < N - k; i++) {
                C[i][k] = Integer.MAX_VALUE;
                for (int j = i + 1; j < N; j++) {
                    int p = P[i][j - 1] + C[j][k - 1];
                    if (p < C[i][k]) {
                        C[i][k] = p;
                    }
                }
            }
        }
        LOG.info(Arrays.deepToString(P));
        LOG.info(Arrays.deepToString(C));
        return C[0][K];
    }

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int n = in.nextInt();
        int k = in.nextInt();
        int[] x = new int[n];
        int[] w = new int[n];

        for (int a0 = 0; a0 < n; a0++) {
            int x_i = in.nextInt();
            int w_i = in.nextInt();
            x[a0] = x_i;
            w[a0] = w_i;
        }
        System.out.println(cost(x, w, k));
    }
}

package spoj.xoringninja;

import java.util.Arrays;
import java.util.Scanner;
import java.util.stream.LongStream;

public class Solution {

    public static long xorSum(long... all) {
        long x = Arrays.stream(all).reduce(0L, (accumulator, _item) -> accumulator | _item);
        Long base = LongStream.range(0, 9).boxed().reduce(1L, (accumulator, _item) -> accumulator * 10L) + 7L;
        Long reduce = LongStream.range(0, all.length - 1).boxed().reduce(x, (accumulator, _item) -> (accumulator * 2L) % base);
        return reduce;
    }

    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        int T = scan.nextInt();
        for (int i = 0; i < T; ++i) {
            int N = scan.nextInt();
            long[] all = new long[N];
            for (int j = 0; j < N; j++) {
                all[j] = scan.nextLong();
            }
            System.out.println(xorSum(all));
        }
    }
}

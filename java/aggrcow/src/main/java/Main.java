
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {

    static class Space {

        private final int lo;
        private final int hi;

        public Space(int lo, int hi) {
            this.lo = lo;
            this.hi = hi;
        }

        public int getLo() {
            return lo;
        }

        public int getHi() {
            return hi;
        }

    }

    static class Farm {

        final int N;
        final int C;
        final Integer[] stalls;

        public Farm(int N, int C, Integer[] stalls) {
            this.N = N;
            this.C = C;
            this.stalls = stalls;
        }

        public int getLargestMinimumDistance() {
            Space space = getSearchSpace();
            return binarySearch(space.getLo(), space.getHi());
        }

        private Space getSearchSpace() {
            if (C > N || N <= 1 || C <= 1) {
                return new Space(0, 0);
            }
            int minDistance = Integer.MAX_VALUE;
            int maxDistance = (stalls[stalls.length - 1] - stalls[0]) / (C - 1);
            for (int i = 1; i < stalls.length; i += 1) {
                int dist = stalls[i] - stalls[i - 1];
                if (dist < minDistance) {
                    minDistance = dist;
                }
            }
            return new Space(minDistance, maxDistance);
        }

        private int getNextStall(int loc, int distance) {
            int location = stalls[loc];
            while (loc < stalls.length && stalls[loc] - location < distance) {
                loc += 1;
            }
            return loc;
        }

        private boolean hasEnoughCapacity(int distance) {
            int counter = 0;
            for (int i = 0; i < stalls.length; i = getNextStall(i, distance)) {
                counter += 1;
            }
            return counter >= C;
        }

        private int binarySearch(int lo, int hi) {
            while (lo < hi) {
                int mid = lo + (hi - lo + 1) / 2;
                if (hasEnoughCapacity(mid)) {
                    lo = mid;
                } else {
                    hi = mid - 1;
                }
            }

            if (!hasEnoughCapacity(lo)) {
                throw new IllegalArgumentException("No solution");
            }
            return lo;
        }
    }

    public static List<Farm> readFromStream(InputStream input) {
        ArrayList<Farm> farms = new ArrayList<Farm>();
        Scanner scanner = new Scanner(input);
        final int T = scanner.nextInt();
        for (int i = 0; i < T; i += 1) {
            final int N = scanner.nextInt();
            final int C = scanner.nextInt();
            List< Integer> stalls = new ArrayList<Integer>();
            for (int j = 0; j < N; j += 1) {
                stalls.add(scanner.nextInt());
            }
            stalls.sort((Integer o1, Integer o2) -> {
                return o1 < o2 ? -1 : o1 > o2 ? 1 : 0;
            });
            Farm farm = new Farm(N, C, stalls.toArray(new Integer[0]));
            farms.add(farm);
        }
        return farms;
    }

    public static void solve(InputStream in, OutputStream out) {
        try (PrintWriter writer = new PrintWriter(out)) {
            List<Farm> farms = readFromStream(in);
            for (Farm farm : farms) {
                int largestMinimumDistance = farm.getLargestMinimumDistance();
                writer.println(largestMinimumDistance);
            }
        }
    }

    public static void main(String args[]) throws Exception {
        solve(System.in, System.out);
    }
}

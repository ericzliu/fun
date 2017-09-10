import org.junit.Assert;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

class Job
{
    public int weight;
    public int length;

    public Job(int weight, int length) {
        this.weight = weight;
        this.length = length;
    }
}

public class Solution {

    static List<Job> readJob(String filename) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            List<Job> jobs = new ArrayList<>();
            reader.readLine();
            String line;
            while ((line = reader.readLine()) != null) {
                String[] strings = line.split("\\s+");
                int weight = Integer.parseInt(strings[0]);
                int length = Integer.parseInt(strings[1]);
                jobs.add(new Job(weight, length));
            }
            return jobs;
        }
    }

    static long differenceMethod(List<Job> originJobs) {
        List<Job> jobs = new ArrayList<>(originJobs);
        Collections.sort(jobs, new Comparator<Job>() {
                    @Override
                    public int compare(Job o1, Job o2) {
                        int score1 = o1.weight - o1.length;
                        int score2 = o2.weight - o2.length;
                        if (score1 < score2)
                            return 1;
                        if (score1 > score2)
                            return -1;
                        if (o1.weight > o2.weight)
                            return -1;
                        if (o1.weight < o2.weight)
                            return 1;
                        return 0;
                    }
                }
        );
        return WCT(jobs);
    }

    static long ratioMethod(List<Job> originJobs) {
        List<Job> jobs = new ArrayList<>(originJobs);
        Collections.sort(jobs, new Comparator<Job>() {
                    @Override
                    public int compare(Job o1, Job o2) {
                        double score1 = o1.weight / (double)o1.length;
                        double score2 = o2.weight / (double)o2.length;
                        if (score1 < score2)
                            return 1;
                        if (score1 > score2)
                            return -1;
                        if (o1.weight > o2.weight)
                            return -1;
                        if (o1.weight < o2.weight)
                            return 1;
                        return 0;
                    }
                }
        );
        return WCT(jobs);
    }

    static long WCT(List<Job> jobs) {
        long time = 0;
        long total = 0;
        int size = jobs.size();
        for (int i = 0; i < size; i++) {
            time += jobs.get(i).length;
            total += jobs.get(i).weight * time;
        }
        return total;
    }

    @Test
    public void tc1() throws IOException {
        List<Job> jobs = readJob("tc1.txt");
        long wct = differenceMethod(jobs);
        Assert.assertEquals(32780, wct);
        long wct2 = ratioMethod(jobs);
        Assert.assertEquals(32104, wct2);
    }

    public static void main(String[] args) throws IOException {
        List<Job> jobs = readJob("jobs.txt");
        long wct = differenceMethod(jobs);
        System.out.println("difference method : " + wct);
        long wct2 = ratioMethod(jobs);
        System.out.println("ratio method : " + wct2);
    }
}
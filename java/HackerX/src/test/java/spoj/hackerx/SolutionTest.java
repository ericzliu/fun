/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package spoj.hackerx;

import java.util.Comparator;
import org.junit.Test;
import org.junit.Assert;

/**
 *
 * @author DIAMIAO
 */
public class SolutionTest {
    
    public SolutionTest() {
    }

    @Test
    public void test_LIS() {
        Integer[] a =  {0, 8, 4, 12, 2, 10, 6, 14, 1, 9, 5, 13, 3, 11, 7, 15};
        int length = Solution.LIS(a, (Integer o1, Integer o2) -> {
            if (o1 < o2)
                return -1;
            if (o1 > o2)
                return 1;
            return 0;
        });
        Assert.assertEquals(6, length);
    }
    
    @Test
    public void test_case_4() {
        int count = Solution.solve(SolutionTest.class.getResourceAsStream("input04.txt"));
        Assert.assertEquals(16, count);
    }
    
    @Test
    public void test_case_0() {
        int count = Solution.solve(SolutionTest.class.getResourceAsStream("input00.txt"));
        Assert.assertEquals(1, count);
    }
}

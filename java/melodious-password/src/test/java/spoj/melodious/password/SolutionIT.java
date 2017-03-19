/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package spoj.melodious.password;

import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.Assert;

/**
 *
 * @author DIAMIAO
 */
public class SolutionIT {
    
    public SolutionIT() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of backtracking method, of class Solution.
     */
    @Test
    public void testBacktracking() {
        System.out.println("backtracking");
        int N = 0;
        int l = 0;
        char[] ps = new char[N];
        List<String> results = Solution.backtracking(N, l, ps);
        Assert.assertTrue(results.isEmpty());
    }

    @Test
    public void test_one_char() {
        System.out.println("backtracking");
        int N = 1;
        int l = 0;
        char[] ps = new char[N];
        List<String> results = Solution.backtracking(N, l, ps);
        Assert.assertEquals(25, results.size());
    }
    
    
    @Test
    public void test_two_chars() {
        System.out.println("backtracking");
        int N = 2;
        int l = 0;
        char[] ps = new char[N];
        List<String> results = Solution.backtracking(N, l, ps);
        Assert.assertEquals(200, results.size());
    }
    
    
    @Test
    public void test_six_chars() {
        System.out.println("backtracking");
        int N = 6;
        int l = 0;
        char[] ps = new char[N];
        List<String> results = Solution.backtracking(N, l, ps);
        Assert.assertEquals(2000000, results.size());
    }
    
}

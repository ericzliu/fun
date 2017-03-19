/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package spoj.w30.poles;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

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
     * Test of cost method, of class Solution.
     */
    @Test
    public void testCost() {
        System.out.println("cost");
        int[] X = {20, 30, 40};
        int[] W = {1, 1, 1};
        int K = 2;
        Solution instance = new Solution();
        int expResult = 10;
        int result = instance.cost(X, W, K);
        assertEquals(expResult, result);
    }
    
}

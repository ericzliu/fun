/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package spoj.xoringninja;

import java.util.Arrays;
import java.util.List;
import org.junit.Test;
import org.junit.Assert;

/**
 *
 * @author DIAMIAO
 */
public class SolutionTest {
    
    public SolutionTest() {
    }

    /**
     * Test of xorSum method, of class Solution.
     */
    @Test
    public void testXorSum() {
        System.out.println("xorSum");
        long expResult = 12L;
        long result = Solution.xorSum(1L, 2L, 3L);
        Assert.assertEquals(expResult, result);
    }
    
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package spoj.gss1;

import java.util.ArrayList;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class SegmentTreeIT {
    
    public SegmentTreeIT() {
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

    @Test
    public void test_find_single_leaf() {
        SegmentTree instance = new SegmentTree(new int[]{1, 2, 3, 4, 5, 6, 7});
        instance.buildTree(1, 0, 6);
        List<Integer> res = new ArrayList<>();
        instance.query_rec(1, 0, 0, res);
        assertEquals(1, res.size());
        assertEquals(0, instance.getLBound(res.get(0)));
        assertEquals(0, instance.getUBound(res.get(0)));
    }

    @Test
    public void test_find_two_consecutive_leaves() {
        SegmentTree instance = new SegmentTree(new int[]{1, 2, 3, 4, 5, 6, 7});
        instance.buildTree(1, 0, 6);
        List<Integer> res = new ArrayList<>();
        instance.query_rec(1, 5, 6, res);
        assertEquals(2, res.size());
        Integer first = res.get(0);
        Integer second = res.get(1);
        assertEquals(5, instance.getLBound(first));
        assertEquals(5, instance.getUBound(first));
        assertEquals(6, instance.getLBound(second));
        assertEquals(6, instance.getUBound(second));
    }
    
    @Test
    public void test_find_two_consecutive_internals() {
        SegmentTree instance = new SegmentTree(new int[]{1, 2, 3, 4, 5, 6, 7});
        instance.buildTree(1, 0, 6);
        List<Integer> res = new ArrayList<>();
        instance.query_rec(1, 2, 5, res);
        assertEquals(2, res.size());
        Integer first = res.get(0);
        Integer second = res.get(1);
        assertEquals(2, instance.getLBound(first));
        assertEquals(3, instance.getUBound(first));
        assertEquals(4, instance.getLBound(second));
        assertEquals(5, instance.getUBound(second));
    }
    
    @Test
    public void test_nominal_case_gss1() {
        SegmentTree instance = new SegmentTree(new int[]{-1, 2, 3});
        instance.buildTree(1, 0, 2);
        int result = instance.query(0, 1);
        assertEquals(2, result);
    }
    
    @Test
    public void test_nominal_case1_gss3() {
        SegmentTree instance = new SegmentTree(new int[]{1, 2, 3, 4});
        instance.buildTree(1, 0, 3);
        int result = instance.query(0, 2);
        assertEquals(6, result);
    }
    
    @Test
    public void test_nominal_case2_gss3() {
        SegmentTree instance = new SegmentTree(new int[]{1, 2, -3, 4});
        instance.buildTree(1, 0, 3);
        int result = instance.query(1, 3);
        assertEquals(4, result);
    }  
    
     
    @Test
    public void test_nominal_case3_gss3() {
        SegmentTree instance = new SegmentTree(new int[]{1, 2, -3, 4});
        instance.buildTree(1, 0, 3);
        int result = instance.query(2, 2);
        assertEquals(-3, result);
    }  
}

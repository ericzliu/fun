/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package spoj.w30.poles;

import org.junit.*;

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
        long[] X = {40L, 30L, 20L};
        long[] W = {1L, 1L, 1L};
        int K = 2;
        Solution instance = new Solution();
        int expResult = 10;
        long result = instance.cost(X, W, K);
        assertEquals(expResult, result);
    }

    @Test
    public void testLineSet() {
        LineSet e = new LineSet();
        e.addLine(StraightLine.create(1, -40));
        e.addLine(StraightLine.create(2, -60));
        Assert.assertEquals(2, e.size());
        long minVal = e.getMinVal(50);
        Assert.assertEquals(10, minVal);
        Assert.assertEquals(2, e.size());
        long minVal2 = e.getMinVal(20);
        Assert.assertEquals(-20, minVal2);
        Assert.assertEquals(1, e.size());
        long minVal3 = e.getMinVal(10);
        Assert.assertEquals(-40, minVal3);
        Assert.assertEquals(1, e.size());
    }


    @Test
    public void testAddLine() {
        LineSet e = new LineSet();
        e.addLine(StraightLine.create(1, -40));
        e.addLine(StraightLine.create(2, -60));
        e.addLine(StraightLine.create(3, -90));
        Assert.assertEquals(2, e.size());
        Assert.assertEquals(-30, e.getMinVal(20));
        Assert.assertEquals(1, e.size());
    }


    @Test
    public void testAddLine2() {
        LineSet e = new LineSet();
        e.addLine(StraightLine.create(1, -40));
        e.addLine(StraightLine.create(2, -60));
        e.addLine(StraightLine.create(5, -100));
        Assert.assertEquals(3, e.size());
    }

    
}

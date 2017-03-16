/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package spoj.connectedcomponent;

import java.util.List;
import java.util.Set;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author DIAMIAO
 */
public class UndirectedGraphTest {

    public UndirectedGraphTest() {
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
     * Test of addLink method, of class UndirectedGraph.
     */
//    @Test
//    public void testAddLink() {
//        System.out.println("addLink");
//        Node u = null;
//        Node v = null;
//        UndirectedGraph instance = new UndirectedGraph();
//        instance.addLink(u, v);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
    /**
     * Test of complete method, of class UndirectedGraph.
     */
//    @Test
//    public void testComplete() {
//        System.out.println("complete");
//        UndirectedGraph instance = new UndirectedGraph();
//        instance.complete();
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
    /**
     * Test of getOutgoingLinks method, of class UndirectedGraph.
     */
//    @Test
//    public void testGetOutgoingLinks() {
//        System.out.println("getOutgoingLinks");
//        Node u = null;
//        UndirectedGraph instance = new UndirectedGraph();
//        List<Link> expResult = null;
//        List<Link> result = instance.getOutgoingLinks(u);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
    /**
     * Test of getConnectedComponent method, of class UndirectedGraph.
     */
    @Test
    public void testGetConnectedComponent() {
        System.out.println("getConnectedComponent");
        NodeRepository repo = NodeRepository.getInstance();
        Node u0 = repo.get(0);
        Node u1 = repo.get(1);
        Node u2 = repo.get(2);
        Node u3 = repo.get(3);
        UndirectedGraph instance = new UndirectedGraph();
        instance.addLink(u0, u1);
        instance.addLink(u1, u2);
        instance.addLink(u2, u3);
        instance.addLink(u3, u1);
        instance.complete();
        List<Set<Node>> components = instance.getConnectedComponents();
        Assert.assertEquals(1, components.size());
        Set<Node> component = components.get(0);
        Assert.assertEquals(4, component.size());
    }

    /**
     * Test of getConnectedComponents method, of class UndirectedGraph.
     */
//    @Test
//    public void testGetConnectedComponents() {
//        System.out.println("getConnectedComponents");
//        UndirectedGraph instance = new UndirectedGraph();
//        List<Set<Node>> expResult = null;
//        List<Set<Node>> result = instance.getConnectedComponents();
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }

}

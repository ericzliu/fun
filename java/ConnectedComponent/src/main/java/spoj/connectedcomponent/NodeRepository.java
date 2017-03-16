/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package spoj.connectedcomponent;

import java.util.HashMap;
import java.util.Map;

public class NodeRepository {

    Map<Integer, Node> nodes = new HashMap<>();
    
    private static NodeRepository repo = new NodeRepository();
    
    public static NodeRepository getInstance() {
        return repo;
    }

    public Node get(int u) {
        Node n = nodes.getOrDefault(u, null);
        if (n == null) {
            n = new Node(u);
            nodes.put(u, n);
        }
        return n;
    }
}

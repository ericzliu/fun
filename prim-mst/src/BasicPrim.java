import common.Edge;
import common.Graph;
import common.Node;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class BasicPrim {
    private Graph graph;

    public BasicPrim(Graph graph) {
        this.graph = graph;
    }

    List<Edge> mst() {
        Set<Node> spanned = new HashSet<>();
        List<Node> nodes = graph.nodes;
        List<Edge> tree = new ArrayList<>();
        if (nodes.isEmpty())
            return tree;
        spanned.add(nodes.get(0));
        while (spanned.size() < nodes.size()) {
            Long minCost = Long.MAX_VALUE;
            Edge minCostEdge = null;
            for (Edge e : graph.edges) {
                if ((spanned.contains(e.node1) && !spanned.contains(e.node2)) || (spanned.contains(e.node2) && !spanned.contains(e.node1))) {
                    if (e.cost < minCost) {
                        minCost = e.cost;
                        minCostEdge = e;
                    }
                }
            }
            tree.add(minCostEdge);
            spanned.add(minCostEdge.node1);
            spanned.add(minCostEdge.node2);
        }
        return tree;
    }
}

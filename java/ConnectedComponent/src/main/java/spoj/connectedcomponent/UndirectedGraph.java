package spoj.connectedcomponent;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.Stack;
import java.util.function.Consumer;
import java.util.stream.Stream;

public class UndirectedGraph {

    List< Link> links = new ArrayList<>();

    public void addLink(Node u, Node v) {
        links.add(new Link(u, v));
        links.add(new Link(v, u));
    }

    public void complete() {
        links.sort((Link o1, Link o2) -> {
            Node u1 = o1.getU();
            Node u2 = o2.getU();
            if (u1.getID() < u2.getID()) {
                return -1;
            }
            if (u1.getID() > u2.getID()) {
                return 1;
            }
            return 0;
        });
    }

    public List<Link> getOutgoingLinks(Node u) {
        int lb = lowerBound(u);
        int ub = upperBound(u);
        if (lb == -1 || ub == -1) {
            return Collections.emptyList();
        }
        if (equal(lb, u)) {
            List<Link> outgoings = new ArrayList<>();
            for (int i = lb; i < ub; i++) {
                outgoings.add(links.get(i));
            }
            return outgoings;
        }
        return Collections.emptyList();
    }

    private boolean equalOrBigger(int mid, Node u) {
        return equal(mid, u) || bigger(mid, u);
    }

    private boolean equal(int mid, Node u) {
        return (links.get(mid).getU().getID() == u.getID());
    }

    private boolean bigger(int mid, Node u) {
        return (links.get(mid).getU().getID() > u.getID());
    }

    private int lowerBound(Node u) {
        int lo = 0;
        int hi = links.size() - 1;
        if (hi < lo) {
            return -1;
        }
        while (lo < hi) {
            int mid = lo + (hi - lo) / 2;
            boolean p = equalOrBigger(mid, u);
            if (p) {
                hi = mid;
            } else {
                lo = mid + 1;
            }
        }
        if (equalOrBigger(lo, u)) {
            return lo;
        }
        return -1;
    }

    private int upperBound(Node u) {
        int lo = 0;
        int hi = links.size() - 1;
        if (hi < lo) {
            return -1;
        }
        while (lo < hi) {
            int mid = lo + (hi - lo) / 2;
            boolean p = bigger(mid, u);
            if (p) {
                hi = mid;
            } else {
                lo = mid + 1;
            }
        }
        if (bigger(lo, u)) {
            return lo;
        }
        return -1;
    }

    Set<Node> getConnectedComponent(Node u) {
        Set<Node> visited = new HashSet<>();
        Stack<Node> stack = new Stack<>();
        stack.add(u);
        visited.add(u);
        while (!stack.isEmpty()) {
            Node pop = stack.pop();
            List<Link> outgoingLinks = getOutgoingLinks(pop);
            outgoingLinks.forEach((t) -> {
                Node v = t.getV();
                if (!visited.contains(v)) {
                    stack.add(v);
                    visited.add(v);
                }
            });
        }
        return visited;
    }
    
    private Optional<Node> getNextNode(Map<Node, Boolean> table) {
        for (Node n : table.keySet()) {
            if (!table.get(n))
                return Optional.of(n);
        }
        return Optional.empty();
    }
    
    List<Set<Node>> getConnectedComponents() {
        List<Set<Node>> components = new ArrayList<>();
        Stream<Node> nodes = links.stream().map((Link l) -> l.getU());
        Map<Node, Boolean> table = new HashMap<>();
        nodes.forEach((t) -> {
            table.put(t, Boolean.FALSE);
        });
        for (Optional<Node> n = getNextNode(table); n.isPresent(); n = getNextNode(table)) {
            Optional<Set<Node>> component = n.map(this::getConnectedComponent);
            component.ifPresent((t) -> {
                components.add(t);
                t.forEach((Node u) -> {
                    table.replace(u, Boolean.TRUE);
                });
            });
        }
        return components;
    }
}

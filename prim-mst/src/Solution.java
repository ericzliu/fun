
import common.Edge;
import common.Graph;
import common.Node;
import org.junit.Assert;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;


class Heap {
    Map<Node, Integer> loc = new HashMap<>();
    List<Entry> entries = new ArrayList<>();

    public static class Entry {
        public Node node;
        public Long cost;
        public Edge edge;

        Entry(Node node) {
            this.node = node;
            this.cost = Long.MAX_VALUE;
        }

        @Override
        public String toString() {
            return "Entry{" +
                    "node=" + node +
                    ", cost=" + cost +
                    ", edge=" + edge +
                    '}';
        }
    }

    void siftUp(Integer l) {
        Entry ent = entries.get(l);
        while (true) {
            Integer p = getParentLoc(l);
            if (p == null || entries.get(p).cost < ent.cost) {
                return;
            }
            swap(l, p);
            l = p;
            ent = entries.get(l);
        }
    }

    void siftDown(int loc) {
        while (true) {
            Integer ch1 = getFirstChild(loc);
            Integer ch2 = getSecondChild(loc);
            if (ch1 == null)
                return;
            Integer nloc = ch1;
            if (ch2 != null && entries.get(ch2).cost < entries.get(ch1).cost)
                nloc = ch2;
            swap(loc, nloc);
            loc = nloc;
        }
    }

    void swap(int loc1, int loc2) {
        Entry ent1 = entries.get(loc1);
        Entry ent2 = entries.get(loc2);
        entries.set(loc1, ent2);
        entries.set(loc2, ent1);
        loc.put(ent1.node, loc2);
        loc.put(ent2.node, loc1);
    }

    public void insert(Entry entry) {
        entries.add(entry);
        loc.put(entry.node, entries.size() - 1);
        siftUp(entries.size() - 1);
    }

    public Entry extract() {
        Entry minEl = entries.get(0);
        return delete(minEl.node);
    }

    public Entry delete(Node node) {
        if (!loc.containsKey(node)) {
            throw new RuntimeException("common.Node is not on the heap!");
        }
        Integer ind = loc.get(node);
        Entry ent = entries.get(ind);
        loc.remove(ent.node);
        Entry lastEnt = entries.get(entries.size() - 1);
        entries.remove(entries.size() - 1);
        if (lastEnt.node.equals(ent.node))
            return ent;
        entries.set(ind, lastEnt);
        loc.put(lastEnt.node, ind);
        Integer ploc = getParentLoc(ind);
        if (ploc != null && entries.get(ploc).cost > lastEnt.cost) {
            siftUp(ind);
        } else {
            siftDown(ind);
        }
        return ent;
    }

    Integer getParentLoc(Integer loc) {
        if (loc == 0)
            return null;
        return (loc - 1) / 2;
    }

    Integer getFirstChild(Integer loc) {
        int i = 2 * loc + 1;
        if (i >= entries.size()) {
            return null;
        }
        return i;
    }

    Integer getSecondChild(Integer loc) {
        int i = 2 * loc + 2;
        if (i >= entries.size()) {
            return null;
        }
        return i;
    }
}

class Prim {

    private Graph graph;

    public Prim(Graph graph) {
        this.graph = graph;
    }

    public List<Edge> run() {
        List<Edge> mst = new ArrayList<>();
        List<Node> nodes = graph.nodes;
        if (nodes.isEmpty()) {
            return mst;
        }
        Node s = nodes.get(0);
        Set<Node> spanned = new HashSet<>();
        spanned.add(s);
        Heap heap = new Heap();
        initiate(s, heap);
        while (spanned.size() < nodes.size()) {
            Heap.Entry entry = heap.extract();
            mst.add(entry.edge);
            spanned.add(entry.node);
            List<Edge> edges = entry.node.edges;
            for (Edge e : edges) {
                Node node = e.node1;
                if (node == entry.node)
                    node = e.node2;
                if (!spanned.contains(node)) {
                    Heap.Entry ent = heap.delete(node);
                    ent.cost = Math.min(ent.cost, e.cost);
                    if (ent.edge == null || !ent.cost.equals(ent.edge.cost)) {
                        ent.edge = e;
                    }
                    heap.insert(ent);
                }
            }
        }
        return mst;
    }

    void initiate(Node s, Heap heap) {
        Map<Node, Heap.Entry> entryMap = new HashMap<>();
        for (Edge edge : graph.edges) {
            if (edge.node1.equals(s) || edge.node2.equals(s)) {
                Node node = edge.node1;
                if (node.equals(s)) {
                    node = edge.node2;
                }
                updateEntry(entryMap, edge, node);
            }
            else {
                if (!entryMap.containsKey(edge.node1))
                    entryMap.put(edge.node1, new Heap.Entry(edge.node1));
                if (!entryMap.containsKey(edge.node2))
                    entryMap.put(edge.node2, new Heap.Entry(edge.node2));
            }
        }
        for (Map.Entry<Node, Heap.Entry> entry : entryMap.entrySet()) {
            Heap.Entry heapEntry = entry.getValue();
            heap.insert(heapEntry);
        }
    }

    private void updateEntry(Map<Node, Heap.Entry> entryMap, Edge edge, Node node) {
        if (!entryMap.containsKey(node)) {
            entryMap.put(node, new Heap.Entry(node));
        }
        Heap.Entry entry = entryMap.get(node);
        entry.cost = Math.min(entry.cost, edge.cost);
        if (entry.edge == null || !entry.edge.cost.equals(edge.cost)) {
            entry.edge = edge;
        }
    }
}

public class Solution {

    public static Graph readFile(String name) throws IOException {
        try (BufferedReader stream = new BufferedReader(new FileReader(name))) {
            String line;
            stream.readLine(); // Ignore the first line
            HashMap<Integer, Node> nodesMap = new HashMap<>();
            List<Edge> edges = new ArrayList<>();
            while ((line = stream.readLine()) != null) {
                String[] strings = line.split("\\s+");
                Integer nid1 = Integer.parseInt(strings[0]);
                Integer nid2 = Integer.parseInt(strings[1]);
                Long cost = Long.parseLong(strings[2]);
                Node node1 = nodesMap.get(nid1);
                if (node1 == null) {
                    nodesMap.put(nid1, new Node(nid1));
                    node1 = nodesMap.get(nid1);
                }
                Node node2 = nodesMap.get(nid2);
                if (node2 == null) {
                    nodesMap.put(nid2, new Node(nid2));
                    node2 = nodesMap.get(nid2);
                }
                Edge e = new Edge();
                e.node1 = node1;
                e.node2 = node2;
                e.cost = cost;
                node1.edges.add(e);
                node2.edges.add(e);
                edges.add(e);
            }
            Graph g = new Graph();
            g.nodes = new ArrayList<>(nodesMap.values());
            g.edges = edges;
            return g;
        }
    }

    public static long totalCost(List<Edge> mst) {
        return mst.stream().map(edge -> edge.cost).reduce(0L, (x, y) -> x + y);
    }

    @Test
    public void tc1() throws IOException {
        Graph graph = readFile("tc1.txt");
        Prim prim = new Prim(graph);
        List<Edge> mst = prim.run();
        Assert.assertEquals(7, totalCost(mst));

        BasicPrim bprim = new BasicPrim(graph);
        List<Edge> mst2 = bprim.mst();
        Assert.assertEquals(7, totalCost(mst2));
    }

    @Test
    public void tc2() throws IOException {
        Graph graph = readFile("tc2.txt");
        Prim prim = new Prim(graph);
        List<Edge> mst = prim.run();
        Assert.assertEquals(14, totalCost(mst));

        BasicPrim bprim = new BasicPrim(graph);
        List<Edge> mst2 = bprim.mst();
        Assert.assertEquals(14, totalCost(mst2));
    }

    @Test
    public void tc3() {
        Node node1 = new Node(1);
        Node node2 = new Node(2);
        Node node3 = new Node(3);
        Edge e1 = new Edge();
        e1.node1 = node1;
        e1.node2 = node2;
        e1.cost = Long.valueOf(1 << 30);
        Edge e2 = new Edge();
        e2.node1 = node2;
        e2.node2 = node3;
        e2.cost = Long.valueOf(1 << 30);
        node1.edges.add(e1);
        node2.edges.add(e1);
        node2.edges.add(e2);
        node3.edges.add(e2);
        Graph graph = new Graph();
        graph.nodes = Arrays.asList(node1, node2, node3);
        graph.edges = Arrays.asList(e1, e2);
        List<Edge> mst = new Prim(graph).run();
        Assert.assertEquals(Long.valueOf(1<<30) + Long.valueOf(1<<30), totalCost(mst));
        List<Edge> mst2 = new BasicPrim(graph).mst();
        Assert.assertEquals(Long.valueOf(1<<30) + Long.valueOf(1<<30), totalCost(mst2));
    }

    public static void main(String[] args) throws IOException {
        Graph graph = readFile("edges.txt");
        Prim prim = new Prim(graph);
        List<Edge> mst = prim.run();
        System.out.println(totalCost(mst)); // -3612829
//        printTo(mst, "wrong.txt", false);
//        printTo(mst, "wrong-sorted.txt", true);

        BasicPrim bprim = new BasicPrim(graph);
        List<Edge> mst1 = bprim.mst();
        System.out.println(totalCost(mst1)); // -3612829
//        printTo(mst1, "right.txt", false);
//        printTo(mst1, "right-sorted.txt", true);
    }

    public static void printTo(List<Edge> mst, String name, boolean sort) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(name))) {
            if (sort) {
                List<Edge> dup = new ArrayList<>(mst);
                Collections.sort(dup, (e1, e2) -> {
                    if (e1.node1.id < e2.node1.id)
                        return -1;
                    if (e1.node1.id > e2.node1.id)
                        return 1;
                    if (e1.node2.id < e2.node2.id)
                        return -1;
                    if (e1.node2.id > e2.node2.id)
                        return 1;
                    return 0;
                });
                mst = dup;
            }
            for (Edge e : mst) {
                writer.write(e.toString());
                writer.newLine();
            }
            writer.flush();
        }
    }
}


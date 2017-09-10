package common;

public class Edge {
    public Node node1;
    public Node node2;
    public Long cost;

    @Override
    public String toString() {
        return "Edge{" +
                "node1=" + node1 +
                ", node2=" + node2 +
                ", cost=" + cost +
                '}';
    }
}

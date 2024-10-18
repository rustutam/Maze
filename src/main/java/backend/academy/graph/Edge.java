package backend.academy.graph;

import backend.academy.Coordinate;
import lombok.Getter;
import java.util.Objects;

@Getter public class Edge {
    private final Vertex from;
    private final Vertex to;
    private final int weight;

    public Edge(Vertex from, Vertex to, int weight) {
        this.from = from;
        this.to = to;
        this.weight = weight;
    }

    @Override public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Edge edge = (Edge) o;
        return weight == edge.weight &&
            ((Objects.equals(from, edge.from) && Objects.equals(to, edge.to)) ||
                (Objects.equals(from, edge.to) && Objects.equals(to, edge.from)));
    }

    @Override
    public int hashCode() {
        return Objects.hash(from, to, weight);
    }

    public static void main(String[] args) {
        Coordinate c1 = new Coordinate(1, 2);
        Coordinate c2 = new Coordinate(2, 1);
        Edge e1 = new Edge(new Vertex(c1), new Vertex(c2), 3);
        Edge e2 = new Edge(new Vertex(c2), new Vertex(c1), 3);
        System.out.println(e1.equals(e2)); // true
    }
}

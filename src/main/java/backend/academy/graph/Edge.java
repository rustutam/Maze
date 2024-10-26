package backend.academy.graph;

import java.util.Objects;

public record Edge(Vertex from, Vertex to) {

    public Vertex getSecondVertex(Vertex vertex) {
        if (vertex.equals(from)) {
            return to;
        } else if (vertex.equals(to)) {
            return from;
        } else {
            throw new IllegalArgumentException("Vertex is not part of this edge");
        }
    }

    @Override public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Edge edge = (Edge) o;
        return
            ((Objects.equals(from, edge.from) && Objects.equals(to, edge.to))
                || (Objects.equals(from, edge.to) && Objects.equals(to, edge.from)));
    }

    @Override
    public int hashCode() {
        return Objects.hash(from, to) + Objects.hash(to, from);
    }

}

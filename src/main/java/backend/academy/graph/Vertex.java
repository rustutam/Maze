package backend.academy.graph;

import backend.academy.Coordinate;
import java.util.Objects;
import lombok.Getter;
import lombok.Setter;

@Getter public class Vertex {

    private final Coordinate coordinate;
    private final int weight;

    @Setter
    private VertexType type;

    public Vertex(Coordinate coordinate, VertexType type) {
        this.coordinate = coordinate;
        this.type = type;
        this.weight = getWeightByType(type);
    }

    public Vertex(Coordinate coordinate) {
        this(coordinate, VertexType.NORMAL); // Значение по умолчанию для weight
    }

    private int getWeightByType(VertexType type) {
        return switch (type) {
            case COIN -> 100;
            case NORMAL -> 1;
            case SAND -> 0;
        };
    }

    @Override public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Vertex vertex = (Vertex) o;
        return Objects.equals(coordinate, vertex.coordinate);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(coordinate);
    }
}

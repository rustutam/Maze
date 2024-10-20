package backend.academy.graph;

import backend.academy.Coordinate;
import lombok.Getter;
import java.util.Objects;

public class Vertex {

    private final Coordinate coordinate;
    @Getter private final int weight;
    public Vertex(Coordinate coordinate, int weight) {
        this.coordinate = coordinate;
        this.weight = weight;
    }

    public Vertex(Coordinate coordinate) {
        this(coordinate, 1); // Значение по умолчанию для weight
    }


    public Coordinate getCoordinate() {
        return coordinate;
    }

    @Override public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Vertex vertex = (Vertex) o;
        return Objects.equals(coordinate, vertex.coordinate);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(coordinate);
    }
}

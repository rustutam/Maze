package backend.academy.graph;

import java.util.Objects;
import java.util.UUID;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Vertex {

    private UUID uuid;
    private int weight;

    public Vertex(UUID uuid) {
        this.uuid = uuid;
        this.weight = 1;
    }

    public Vertex() {
        this.uuid = UUID.randomUUID();
        this.weight = 1;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Vertex vertex = (Vertex) o;
        return uuid == vertex.uuid;
    }

    @Override
    public int hashCode() {
        return Objects.hash(uuid);
    }

}

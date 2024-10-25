package backend.academy.graph;

import java.util.List;
import java.util.Objects;
import java.util.UUID;
import lombok.Getter;
import lombok.Setter;

public class Vertex {

    @Getter
    @Setter
    private UUID uuid;
    @Getter
    @Setter
    private int weight;

//    public Vertex(UUID uuid,Integer weight) {
//        this.uuid = uuid;
//        this.weight = weight;
//    }

    public Vertex(UUID uuid) {
        this.uuid = uuid;
        this.weight = 1;
    }

    public Vertex(){
        this.uuid = UUID.randomUUID();
        this.weight = 1;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Vertex vertex = (Vertex) o;
        return uuid == vertex.uuid;
    }

    @Override
    public int hashCode() {
        return Objects.hash(uuid);
    }

    public static void main(String[] args) {
//        List<Vertex> a = List.of(new Vertex(1), new Vertex(2));
//        Vertex vertex = new Vertex(1);
//        System.out.println(a.contains(vertex));
    }
}

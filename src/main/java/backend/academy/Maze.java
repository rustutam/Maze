package backend.academy;

import backend.academy.graph.Graph;
import backend.academy.graph.Vertex;
import backend.academy.graph.VertexType;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import lombok.Getter;

public final class Maze {
    @Getter
    private final int height;
    @Getter
    private final int width;
    @Getter
    private final Graph graph;

    private final Random random;

    public Maze(int height, int width, Graph graph, Random random) {
        this.height = height;
        this.width = width;
        this.graph = graph;
        this.random = random;

    }

    public void addNewSurfaces(VertexType type, int coinCount) {
        if (coinCount<=0){
            return;
        }
        List<Vertex> vertices = graph.getVertices();
        vertices = vertices.stream()
            .filter(vertex -> vertex.type() == VertexType.NORMAL)
            .collect(Collectors.toList());

        int vertexCount = vertices.size();

        if (coinCount > vertexCount) {
            coinCount = vertexCount;
        }

        while (coinCount > 0) {
            int randomIndex = random.nextInt(vertexCount);
            Vertex randomVertex = vertices.get(randomIndex);
            vertices.remove(randomIndex);
            vertexCount = vertices.size();
            randomVertex.type(type);
            coinCount--;
        }
    }

    public List<Coordinate> getCoordinates(){
        return graph().getVertices().stream().map(Vertex::coordinate).collect(Collectors.toList());
    }
}



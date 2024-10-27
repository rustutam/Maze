package backend.academy.generator;

import backend.academy.graph.Edge;
import backend.academy.graph.Graph;
import backend.academy.graph.Vertex;
import java.security.SecureRandom;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class KruskalMazeGeneratorTest {

    @Test
    void generate() {
        // Arrange
        UUID uuid1 = UUID.randomUUID();
        UUID uuid2 = UUID.randomUUID();
        UUID uuid3 = UUID.randomUUID();
        UUID uuid4 = UUID.randomUUID();

        Graph graph = new Graph();
        Vertex v1 = new Vertex(uuid1);
        Vertex v2 = new Vertex(uuid2);
        Vertex v3 = new Vertex(uuid3);
        Vertex v4 = new Vertex(uuid4);

        graph.addVertex(v1);
        graph.addVertex(v2);
        graph.addVertex(v3);
        graph.addVertex(v4);

        graph.addEdge(v1, v2);
        graph.addEdge(v1, v3);
        graph.addEdge(v3, v4);
        graph.addEdge(v2, v4);

        KruskalMazeGenerator generator = new KruskalMazeGenerator(new SecureRandom());

        // Act
        Graph mst = generator.generate(graph);

        // Assert
        assertNotNull(mst);
        List<Edge> edges = mst.getEdges();
        assertEquals(3, edges.size()); // MST для графа с 4 вершинами должен содержать 3 ребра

    }
}

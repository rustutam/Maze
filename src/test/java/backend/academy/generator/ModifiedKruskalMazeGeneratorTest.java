package backend.academy.generator;

import backend.academy.graph.Edge;
import backend.academy.graph.Graph;
import backend.academy.graph.Vertex;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.security.SecureRandom;
import java.util.List;
import java.util.UUID;
import static org.junit.jupiter.api.Assertions.*;

class ModifiedKruskalMazeGeneratorTest {
    Graph graph;
    SecureRandom random;

    @BeforeEach
    void setUp() {
        random = new SecureRandom();
        graph = new Graph();

        UUID uuid1 = UUID.randomUUID();
        UUID uuid2 = UUID.randomUUID();
        UUID uuid3 = UUID.randomUUID();
        UUID uuid4 = UUID.randomUUID();
        UUID uuid5 = UUID.randomUUID();
        UUID uuid6 = UUID.randomUUID();

        Vertex v1 = new Vertex(uuid1);
        Vertex v2 = new Vertex(uuid2);
        Vertex v3 = new Vertex(uuid3);
        Vertex v4 = new Vertex(uuid4);
        Vertex v5 = new Vertex(uuid5);
        Vertex v6 = new Vertex(uuid6);

        graph.addVertex(v1);
        graph.addVertex(v2);
        graph.addVertex(v3);
        graph.addVertex(v4);
        graph.addVertex(v5);
        graph.addVertex(v6);

        graph.addEdge(v1, v2);
        graph.addEdge(v1, v3);
        graph.addEdge(v3, v4);
        graph.addEdge(v2, v4);
        graph.addEdge(v3, v5);
        graph.addEdge(v4, v6);
        graph.addEdge(v5, v6);
    }

    @Test
    void generateTestWithZeroCoefficient() {
        // Arrange
        ModifiedKruskalMazeGenerator generator = new ModifiedKruskalMazeGenerator(random, 0);

        // Act
        Graph mst = generator.generate(graph);

        // Assert
        assertNotNull(mst);

        List<Edge> edges = mst.getEdges();
        assertEquals(5, edges.size());
    }

    @Test
    void generateTestWithCoefficient() {
        // Arrange
        ModifiedKruskalMazeGenerator generator = new ModifiedKruskalMazeGenerator(random, 1);

        // Act
        Graph mst = generator.generate(graph);

        // Assert
        assertNotNull(mst);

        List<Edge> edges = mst.getEdges();
        assertEquals(7, edges.size());
    }
}

package backend.academy.graph;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class GraphTest {

    @Test
    void addVertex() {
        // Arrange
        Graph graph = new Graph();
        Vertex vertex1 = new Vertex();
        Vertex vertex2 = new Vertex();
        Vertex vertex3 = new Vertex();

        // Act
        graph.addVertex(vertex1);
        graph.addVertex(vertex2);

        // Assert
        assertTrue(graph.getVertices().contains(vertex1));
        assertTrue(graph.getVertices().contains(vertex2));
        assertFalse(graph.getVertices().contains(vertex3));
    }

    @Test
    void addEdge() {
        // Arrange
        Graph graph = new Graph();
        Vertex vertex1 = new Vertex();
        Vertex vertex2 = new Vertex();
        graph.addVertex(vertex1);
        graph.addVertex(vertex2);

        // Act
        graph.addEdge(vertex1, vertex2);

        // Assert
        assertTrue(graph.getEdges().contains(new Edge(vertex1, vertex2)));
        assertTrue(graph.getNeighbours(vertex1).stream().anyMatch(edge -> edge.to().equals(vertex2)));
        assertTrue(graph.getNeighbours(vertex2).stream().anyMatch(edge -> edge.from().equals(vertex1)));
    }
}

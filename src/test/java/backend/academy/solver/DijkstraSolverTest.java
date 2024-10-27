package backend.academy.solver;

import backend.academy.graph.Graph;
import backend.academy.graph.Vertex;
import java.util.List;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;

class DijkstraSolverTest {
    @Test
    void testMaxValuePath() {
        // Arrange
        Graph graph = new Graph();

        Vertex vertex1 = new Vertex();
        Vertex vertex2 = new Vertex();
        Vertex vertex3 = new Vertex();
        Vertex vertex4 = new Vertex();

        graph.addVertex(vertex1);
        graph.addVertex(vertex2);
        graph.addVertex(vertex3);
        graph.addVertex(vertex4);

        graph.addEdge(vertex1, vertex2);
        graph.addEdge(vertex1, vertex3);

        graph.addEdge(vertex2, vertex1);
        graph.addEdge(vertex2, vertex4);

        graph.addEdge(vertex3, vertex1);
        graph.addEdge(vertex3, vertex4);

        graph.addEdge(vertex4, vertex2);
        graph.addEdge(vertex4, vertex3);

        vertex3.weight(10);

        List<Vertex> correctPath = List.of(vertex1, vertex3, vertex4);
        DijkstraSolver dijkstraSolver = new DijkstraSolver();

        // Act
        List<Vertex> path = dijkstraSolver.solve(graph, vertex1, vertex4);

        // Assert
        assertArrayEquals(correctPath.toArray(), path.toArray());

    }

}

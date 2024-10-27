package backend.academy.graph;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class EdgeTest {

    @Test
    void getSecondVertex() {
        // Arrange
        Vertex vertex1 = new Vertex();
        Vertex vertex2 = new Vertex();
        Edge edge = new Edge(vertex1, vertex2);

        //Act
        Vertex secondVertex1 = edge.getSecondVertex(vertex1);
        Vertex secondVertex2 = edge.getSecondVertex(vertex2);

        // Assert
        assertEquals(vertex2, secondVertex1);
        assertEquals(vertex1, secondVertex2);
        assertThrows(IllegalArgumentException.class, () -> edge.getSecondVertex(new Vertex()));
    }
}

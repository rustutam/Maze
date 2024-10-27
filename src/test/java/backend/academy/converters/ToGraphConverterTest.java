package backend.academy.converters;

import backend.academy.cell.Cell;
import backend.academy.cell.Passage;
import backend.academy.cell.Wall;
import backend.academy.graph.Graph;
import backend.academy.graph.Vertex;
import backend.academy.models.ConvertedMazeModel;
import backend.academy.models.Coordinate;
import backend.academy.models.MazeListModel;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

class ToGraphConverterTest {

    @Test
    void convertToGraph() {
        // Arrange
        int height = 3;
        int width = 3;
        Cell[][] mazeList = new Cell[height][width];
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                mazeList[i][j] = new Wall(i, j);
            }
        }
        mazeList[0][0] = new Passage(0, 0);
        mazeList[0][2] = new Passage(0, 2);
        mazeList[2][0] = new Passage(2, 0);
        mazeList[2][2] = new Passage(2, 2);

        Map<Coordinate, List<Coordinate>> coordinateNeighbours = new HashMap<>();
        coordinateNeighbours.put(new Coordinate(0, 0), List.of(new Coordinate(0, 2), new Coordinate(2, 0)));
        coordinateNeighbours.put(new Coordinate(0, 2), List.of(new Coordinate(0, 0), new Coordinate(2, 2)));
        coordinateNeighbours.put(new Coordinate(2, 0), List.of(new Coordinate(0, 0), new Coordinate(2, 2)));
        coordinateNeighbours.put(new Coordinate(2, 2), List.of(new Coordinate(2, 0), new Coordinate(0, 2)));

        MazeListModel mazeListModel = new MazeListModel(mazeList, coordinateNeighbours, height, width);
        ToGraphConverter toGraphConverter = new ToGraphConverter();

        Graph graph = new Graph();

        Vertex v1 = new Vertex();
        Vertex v2 = new Vertex();
        Vertex v3 = new Vertex();
        Vertex v4 = new Vertex();

        graph.addVertex(v1);
        graph.addVertex(v2);
        graph.addVertex(v3);
        graph.addVertex(v4);

        graph.addEdge(v1, v2);
        graph.addEdge(v1, v3);
        graph.addEdge(v2, v1);
        graph.addEdge(v2, v4);
        graph.addEdge(v3, v1);
        graph.addEdge(v3, v4);
        graph.addEdge(v4, v2);
        graph.addEdge(v4, v3);

        Map<Coordinate, Vertex> myCoordinateVertexMap = new HashMap<>();
        myCoordinateVertexMap.put(new Coordinate(0, 0), v1);
        myCoordinateVertexMap.put(new Coordinate(0, 2), v2);
        myCoordinateVertexMap.put(new Coordinate(2, 0), v3);
        myCoordinateVertexMap.put(new Coordinate(2, 2), v4);

        ConvertedMazeModel myConvertedMazeModel = new ConvertedMazeModel(graph, myCoordinateVertexMap, height, width);

        // Act
        ConvertedMazeModel convertedMazeModel = toGraphConverter.convertToGraph(mazeListModel);

        // Assert
        assertEquals(myConvertedMazeModel.graph().getVertices().size(),
            convertedMazeModel.graph().getVertices().size());
        assertEquals(myConvertedMazeModel.graph().getEdges().size(), convertedMazeModel.graph().getEdges().size());
        assertEquals(myConvertedMazeModel.coordinateVertexMap().keySet(),
            convertedMazeModel.coordinateVertexMap().keySet());
        assertEquals(myConvertedMazeModel.height(), convertedMazeModel.height());
        assertEquals(myConvertedMazeModel.width(), convertedMazeModel.width());
    }
}

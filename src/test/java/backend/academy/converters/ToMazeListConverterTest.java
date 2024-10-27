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
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

class ToMazeListConverterTest {

    @Test
    void convertToMazeList() {
        // Arrange
        int height = 5;
        int width = 5;

        Graph graph = new Graph();

        Vertex vertex1 = new Vertex();
        Vertex vertex2 = new Vertex();
        Vertex vertex3 = new Vertex();
        Vertex vertex4 = new Vertex();
        Vertex vertex5 = new Vertex();
        Vertex vertex6 = new Vertex();
        Vertex vertex7 = new Vertex();
        Vertex vertex8 = new Vertex();
        Vertex vertex9 = new Vertex();

        graph.addVertex(vertex1);
        graph.addVertex(vertex2);
        graph.addVertex(vertex3);
        graph.addVertex(vertex4);
        graph.addVertex(vertex5);
        graph.addVertex(vertex6);
        graph.addVertex(vertex7);
        graph.addVertex(vertex8);
        graph.addVertex(vertex9);

        graph.addEdge(vertex1, vertex2);
        graph.addEdge(vertex1, vertex4);

        graph.addEdge(vertex2, vertex1);
        graph.addEdge(vertex2, vertex3);
        graph.addEdge(vertex2, vertex5);

        graph.addEdge(vertex3, vertex2);
        graph.addEdge(vertex3, vertex6);

        graph.addEdge(vertex4, vertex1);
        graph.addEdge(vertex4, vertex7);

        graph.addEdge(vertex5, vertex2);
        graph.addEdge(vertex5, vertex8);

        graph.addEdge(vertex6, vertex3);
        graph.addEdge(vertex6, vertex9);

        graph.addEdge(vertex7, vertex4);
        graph.addEdge(vertex7, vertex8);

        graph.addEdge(vertex8, vertex5);
        graph.addEdge(vertex8, vertex7);

        graph.addEdge(vertex9, vertex6);

        Map<Coordinate, Vertex> coordinateVertexMap = Map.of(
            new Coordinate(0, 0), vertex1,
            new Coordinate(0, 2), vertex2,
            new Coordinate(0, 4), vertex3,
            new Coordinate(2, 0), vertex4,
            new Coordinate(2, 2), vertex5,
            new Coordinate(2, 4), vertex6,
            new Coordinate(4, 0), vertex7,
            new Coordinate(4, 2), vertex8,
            new Coordinate(4, 4), vertex9
        );
        ConvertedMazeModel convertedMazeModel = new ConvertedMazeModel(graph, coordinateVertexMap, height, width);

        Cell[][] mazeList = new Cell[height][width];
        for (int i = 0; i < height; i += 1) {
            for (int j = 0; j < width; j += 1) {
                mazeList[i][j] = new Passage(i, j);
            }
        }
        mazeList[1][1] = new Wall(1, 1);
        mazeList[2][1] = new Wall(2, 1);
        mazeList[3][1] = new Wall(3, 1);
        mazeList[1][3] = new Wall(1, 3);
        mazeList[2][3] = new Wall(2, 3);
        mazeList[3][3] = new Wall(3, 3);
        mazeList[4][3] = new Wall(4, 3);

        Map<Coordinate, List<Coordinate>> coordinateNeighbours = new HashMap<>();
        coordinateNeighbours.put(new Coordinate(0, 0), List.of(new Coordinate(0, 1), new Coordinate(1, 0)));
        coordinateNeighbours.put(new Coordinate(0, 1), List.of(new Coordinate(0, 0), new Coordinate(0, 2)));
        coordinateNeighbours.put(new Coordinate(0, 2),
            List.of(new Coordinate(0, 1), new Coordinate(0, 3), new Coordinate(1, 2)));
        coordinateNeighbours.put(new Coordinate(0, 3), List.of(new Coordinate(0, 2), new Coordinate(0, 4)));
        coordinateNeighbours.put(new Coordinate(0, 4), List.of(new Coordinate(0, 3), new Coordinate(1, 4)));

        coordinateNeighbours.put(new Coordinate(1, 0), List.of(new Coordinate(0, 0), new Coordinate(2, 0)));
        coordinateNeighbours.put(new Coordinate(1, 2), List.of(new Coordinate(0, 2), new Coordinate(2, 2)));
        coordinateNeighbours.put(new Coordinate(1, 4), List.of(new Coordinate(0, 4), new Coordinate(2, 4)));

        coordinateNeighbours.put(new Coordinate(2, 0), List.of(new Coordinate(1, 0), new Coordinate(3, 0)));
        coordinateNeighbours.put(new Coordinate(2, 2), List.of(new Coordinate(1, 2), new Coordinate(3, 2)));
        coordinateNeighbours.put(new Coordinate(2, 4), List.of(new Coordinate(1, 4), new Coordinate(3, 4)));

        coordinateNeighbours.put(new Coordinate(3, 0), List.of(new Coordinate(2, 0), new Coordinate(4, 0)));
        coordinateNeighbours.put(new Coordinate(3, 2), List.of(new Coordinate(4, 2), new Coordinate(2, 2)));
        coordinateNeighbours.put(new Coordinate(3, 4), List.of(new Coordinate(4, 4), new Coordinate(2, 4)));

        coordinateNeighbours.put(new Coordinate(4, 0), List.of(new Coordinate(4, 1), new Coordinate(3, 0)));
        coordinateNeighbours.put(new Coordinate(4, 1), List.of(new Coordinate(4, 0), new Coordinate(4, 2)));
        coordinateNeighbours.put(new Coordinate(4, 2), List.of(new Coordinate(4, 1), new Coordinate(3, 2)));
        coordinateNeighbours.put(new Coordinate(4, 4), List.of(new Coordinate(3, 4)));

        MazeListModel myMazeListModel = new MazeListModel(mazeList, coordinateNeighbours, height, width);

        ToMazeListConverter toMazeListConverter = new ToMazeListConverter();

        // Act
        MazeListModel mazeListModelConverted = toMazeListConverter.convertToMazeList(convertedMazeModel);

        // Assert
        assertArrayEquals(myMazeListModel.mazeList(), mazeListModelConverted.mazeList());

        Map<Coordinate, List<Coordinate>> map1 = myMazeListModel.coordinateNeighboursMap();
        Map<Coordinate, List<Coordinate>> map2 = mazeListModelConverted.coordinateNeighboursMap();
        assertEquals(map1.keySet(), map2.keySet());
        for (Coordinate key : map1.keySet()) {
            Set<Coordinate> set1 = new HashSet<>(map1.get(key));
            Set<Coordinate> set2 = new HashSet<>(map2.get(key));
            assertEquals(set1, set2, "Values for key " + key + " are different");

        }
        assertEquals(myMazeListModel.height(), mazeListModelConverted.height());
        assertEquals(myMazeListModel.width(), mazeListModelConverted.width());

    }

}

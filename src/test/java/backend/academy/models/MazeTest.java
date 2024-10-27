package backend.academy.models;

import backend.academy.cell.Cell;
import backend.academy.cell.Passage;
import backend.academy.cell.Wall;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertTrue;

class MazeTest {

    @Test
    void getAllPassage() {
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
        mazeList[0][1] = new Passage(0, 1);
        mazeList[0][2] = new Passage(0, 2);
        mazeList[1][0] = new Passage(1, 0);
        mazeList[2][0] = new Passage(2, 0);
        mazeList[1][2] = new Passage(1, 2);
        mazeList[2][2] = new Passage(2, 2);

        Map<Coordinate, List<Coordinate>> coordinateNeighbours = new HashMap<>();
        coordinateNeighbours.put(new Coordinate(0, 0), List.of(new Coordinate(0, 1), new Coordinate(1, 0)));
        coordinateNeighbours.put(new Coordinate(0, 1), List.of(new Coordinate(0, 0), new Coordinate(0, 2)));
        coordinateNeighbours.put(new Coordinate(0, 2), List.of(new Coordinate(0, 1), new Coordinate(1, 2)));
        coordinateNeighbours.put(new Coordinate(1, 0), List.of(new Coordinate(0, 0), new Coordinate(2, 0)));
        coordinateNeighbours.put(new Coordinate(2, 0), List.of(new Coordinate(1, 0)));
        coordinateNeighbours.put(new Coordinate(1, 2), List.of(new Coordinate(0, 2), new Coordinate(2, 2)));
        coordinateNeighbours.put(new Coordinate(2, 2), List.of(new Coordinate(1, 2)));

        MazeListModel mazeListModel = new MazeListModel(mazeList, coordinateNeighbours, height, width);
        Maze maze = new Maze(height, width, mazeListModel);

        List<Coordinate> correctPassages = List.of(
            new Coordinate(0, 0),
            new Coordinate(0, 1),
            new Coordinate(0, 2),
            new Coordinate(1, 0),
            new Coordinate(2, 0),
            new Coordinate(1, 2),
            new Coordinate(2, 2));

        // Act
        List<Coordinate> allPassages = maze.getAllPassage();

        // Assert
        for (Coordinate coordinate : allPassages) {
            assertTrue(correctPassages.contains(coordinate));
        }
    }
}

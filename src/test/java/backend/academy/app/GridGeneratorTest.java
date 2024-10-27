package backend.academy.app;

import backend.academy.cell.Cell;
import backend.academy.cell.Passage;
import backend.academy.cell.Wall;
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

class GridGeneratorTest {

    @Test
    void testGetMazeListModelWithFirstStartCoordinate() {
        //Arrange
        int height = 5;
        int width = 5;
        GridGenerator gridGenerator = new GridGenerator();
        Coordinate startCoordinate = new Coordinate(0, 0);
        Cell[][] mazeList = new Cell[height][width];
        for (int i = 0; i < height; i += 1) {
            for (int j = 0; j < width; j += 1) {
                mazeList[i][j] = new Wall(i, j);
            }
        }
        for (int i = 0; i < height; i += 2) {
            for (int j = 0; j < width; j += 2) {
                mazeList[i][j] = new Passage(i, j);
            }
        }

        Map<Coordinate, List<Coordinate>> coordinateNeighbours = new HashMap<>();
        coordinateNeighbours.put(new Coordinate(0, 0), List.of(new Coordinate(0, 2), new Coordinate(2, 0)));
        coordinateNeighbours.put(new Coordinate(0, 2),
            List.of(new Coordinate(0, 0), new Coordinate(0, 4), new Coordinate(2, 2)));
        coordinateNeighbours.put(new Coordinate(0, 4), List.of(new Coordinate(0, 2), new Coordinate(2, 4)));

        coordinateNeighbours.put(new Coordinate(2, 0),
            List.of(new Coordinate(0, 0), new Coordinate(4, 0), new Coordinate(2, 2)));
        coordinateNeighbours.put(new Coordinate(2, 2),
            List.of(new Coordinate(2, 0), new Coordinate(2, 4), new Coordinate(4, 2), new Coordinate(0, 2)));
        coordinateNeighbours.put(new Coordinate(2, 4),
            List.of(new Coordinate(0, 4), new Coordinate(4, 4), new Coordinate(2, 2)));

        coordinateNeighbours.put(new Coordinate(4, 0), List.of(new Coordinate(2, 0), new Coordinate(4, 2)));
        coordinateNeighbours.put(new Coordinate(4, 2),
            List.of(new Coordinate(4, 0), new Coordinate(4, 4), new Coordinate(2, 2)));
        coordinateNeighbours.put(new Coordinate(4, 4), List.of(new Coordinate(4, 2), new Coordinate(2, 4)));

        MazeListModel myMazeListModel = new MazeListModel(mazeList, coordinateNeighbours, height, width);

        //Act
        MazeListModel mazeListModelGenerated = gridGenerator.getMazeListModel(startCoordinate, height, width);

        //Assert
        assertArrayEquals(myMazeListModel.mazeList(), mazeListModelGenerated.mazeList());

        Map<Coordinate, List<Coordinate>> map1 = myMazeListModel.coordinateNeighboursMap();
        Map<Coordinate, List<Coordinate>> map2 = mazeListModelGenerated.coordinateNeighboursMap();
        assertEquals(map1.keySet(), map2.keySet());
        for (Coordinate key : map1.keySet()) {
            Set<Coordinate> set1 = new HashSet<>(map1.get(key));
            Set<Coordinate> set2 = new HashSet<>(map2.get(key));
            assertEquals(set1, set2, "Values for key " + key + " are different");

        }
        assertEquals(myMazeListModel.height(), mazeListModelGenerated.height());
        assertEquals(myMazeListModel.width(), mazeListModelGenerated.width());

    }

    @Test
    void testGetMazeListModelWithSecondStartCoordinate() {
        //Arrange
        int height = 5;
        int width = 5;
        GridGenerator gridGenerator = new GridGenerator();
        Coordinate startCoordinate = new Coordinate(1, 1);
        Cell[][] mazeList = new Cell[height][width];
        for (int i = 0; i < height; i += 1) {
            for (int j = 0; j < width; j += 1) {
                mazeList[i][j] = new Wall(i, j);
            }
        }
        mazeList[0][1] = new Passage(0, 1);
        mazeList[0][3] = new Passage(0, 3);

        mazeList[1][0] = new Passage(1, 0);
        mazeList[1][1] = new Passage(1, 1);
        mazeList[1][3] = new Passage(1, 3);
        mazeList[1][4] = new Passage(1, 4);

        mazeList[3][0] = new Passage(3, 0);
        mazeList[3][1] = new Passage(3, 1);
        mazeList[3][3] = new Passage(3, 3);
        mazeList[3][4] = new Passage(3, 4);

        mazeList[4][1] = new Passage(4, 1);
        mazeList[4][3] = new Passage(4, 3);

        Map<Coordinate, List<Coordinate>> coordinateNeighbours = new HashMap<>();
        coordinateNeighbours.put(new Coordinate(0, 1), List.of(new Coordinate(1, 1)));
        coordinateNeighbours.put(new Coordinate(0, 3), List.of(new Coordinate(1, 3)));

        coordinateNeighbours.put(new Coordinate(1, 0), List.of(new Coordinate(1, 1)));
        coordinateNeighbours.put(new Coordinate(1, 1),
            List.of(new Coordinate(1, 0), new Coordinate(0, 1), new Coordinate(1, 3), new Coordinate(3, 1)));
        coordinateNeighbours.put(new Coordinate(1, 3),
            List.of(new Coordinate(1, 1), new Coordinate(0, 3), new Coordinate(3, 3), new Coordinate(1, 4)));
        coordinateNeighbours.put(new Coordinate(1, 4), List.of(new Coordinate(1, 3)));

        coordinateNeighbours.put(new Coordinate(3, 0), List.of(new Coordinate(3, 1)));
        coordinateNeighbours.put(new Coordinate(3, 1),
            List.of(new Coordinate(3, 0), new Coordinate(1, 1), new Coordinate(3, 3), new Coordinate(4, 1)));
        coordinateNeighbours.put(new Coordinate(3, 3),
            List.of(new Coordinate(3, 1), new Coordinate(1, 3), new Coordinate(3, 4), new Coordinate(4, 3)));
        coordinateNeighbours.put(new Coordinate(3, 4), List.of(new Coordinate(3, 3)));

        coordinateNeighbours.put(new Coordinate(4, 1), List.of(new Coordinate(3, 1)));
        coordinateNeighbours.put(new Coordinate(4, 3), List.of(new Coordinate(3, 3)));

        MazeListModel myMazeListModel = new MazeListModel(mazeList, coordinateNeighbours, height, width);

        //Act
        MazeListModel mazeListModelGenerated = gridGenerator.getMazeListModel(startCoordinate, height, width);

        //Assert
        assertArrayEquals(myMazeListModel.mazeList(), mazeListModelGenerated.mazeList());

        Map<Coordinate, List<Coordinate>> map1 = myMazeListModel.coordinateNeighboursMap();
        Map<Coordinate, List<Coordinate>> map2 = mazeListModelGenerated.coordinateNeighboursMap();
        assertEquals(map1.keySet(), map2.keySet());
        for (Coordinate key : map1.keySet()) {
            Set<Coordinate> set1 = new HashSet<>(map1.get(key));
            Set<Coordinate> set2 = new HashSet<>(map2.get(key));
            assertEquals(set1, set2, "Values for key " + key + " are different");

        }
        assertEquals(myMazeListModel.height(), mazeListModelGenerated.height());
        assertEquals(myMazeListModel.width(), mazeListModelGenerated.width());

    }
}

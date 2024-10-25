package backend.academy;

import backend.academy.cell.Cell;
import backend.academy.cell.Passage;
import backend.academy.cell.Wall;
import backend.academy.models.MazeListModel;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GridGenerator {
    private final int height;
    private final int width;
    private Cell[][] mazeList;
    private final Map<Coordinate, List<Coordinate>> coordinateNeighbours = new HashMap<>();

    public GridGenerator(int height, int width) {
        this.height = height;
        this.width = width;
    }

    public MazeListModel getMazeListModel(Coordinate startCoordinate) {
        generateFullWallMaze();
        generateGrid(startCoordinate);
        updateCoordinateNeighbours();

        return new MazeListModel(mazeList, coordinateNeighbours, height, width);
    }

    private void generateFullWallMaze() {
        mazeList = new Cell[height][width];
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                mazeList[i][j] = new Wall(i, j);
            }
        }
    }

    private void generateGrid(Coordinate startCoordinate) {
        int x = adjustCoordinate(startCoordinate.col());
        int y = adjustCoordinate(startCoordinate.row());

        for (int i = y; i < height; i += 2) {
            for (int j = x; j < width; j += 2) {
                mazeList[i][j] = new Passage(i, j);
                addBoundaryCoordinates(new Coordinate(i, j));
            }
        }
    }

    private void addBoundaryCoordinates(Coordinate coordinate) {
        List<Coordinate> maybeNeighboursCoordinate = getMaybeNeighbours(coordinate, 2);

//        //добавляем соседей в мапу
//        neighboursCoordinate.stream()
//            .filter(this::isWithinBounds)
//            .forEach(cord -> addCoordinateNeighbour(coordinate, cord));

        //добавляем соседей, которые лежат вне лабиринта в мапу
        maybeNeighboursCoordinate.stream()
            .filter(cord -> !isWithinBounds(cord))
            .map(cord -> getMiddleCoordinate(coordinate, cord))
            .filter(this::isWithinBounds)
            .forEach(
                cord -> {
                    mazeList[cord.row()][cord.col()] = new Passage(cord.row(), cord.col());
                });
    }

    private void updateCoordinateNeighbours() {
        Arrays.stream(mazeList)
            .flatMap(Arrays::stream)
            .filter(cell -> cell.type() == Cell.CellType.PASSAGE)
            .map(cell -> new Coordinate(cell.row(), cell.col()))
            .forEach(cord -> {

                List<Coordinate> maybeNeighboursCoordinateByStep2 = getMaybeNeighbours(cord, 2);
                List<Coordinate> maybeNeighboursCoordinateByStep1 = getMaybeNeighbours(cord, 1);
                List<Coordinate> maybeNeighboursCoordinate = new ArrayList<>();
                maybeNeighboursCoordinate.addAll(maybeNeighboursCoordinateByStep2);
                maybeNeighboursCoordinate.addAll(maybeNeighboursCoordinateByStep1);
                maybeNeighboursCoordinate.stream()
                    .filter(this::isWithinBounds)
                    .filter(coordinate -> mazeList[coordinate.row()][coordinate.col()].type() == Cell.CellType.PASSAGE)
                    .forEach(c -> addCoordinateNeighbour(cord, c));
            });
    }

    private List<Coordinate> getMaybeNeighbours(Coordinate coordinate,int step) {
        List<Coordinate> neighboursCoordinate = new ArrayList<>();
        for (Direction direction : Direction.values()) {
            Coordinate newCoordinate = move(coordinate, direction, step);
            neighboursCoordinate.add(newCoordinate);
        }
        return neighboursCoordinate;
    }

    private Coordinate getMiddleCoordinate(Coordinate coordinate1, Coordinate coordinate2) {
        int row = (coordinate1.row() + coordinate2.row()) / 2;
        int col = (coordinate1.col() + coordinate2.col()) / 2;
        return new Coordinate(row, col);
    }

    private boolean isWithinBounds(Coordinate coordinate) {
        return coordinate.row() >= 0 && coordinate.row() < height &&
            coordinate.col() >= 0 && coordinate.col() < width;
    }

    private void addCoordinateNeighbour(Coordinate key, Coordinate value) {
        coordinateNeighbours.computeIfAbsent(key, _ -> new ArrayList<>()).add(value);
    }

    private int adjustCoordinate(int coordinate) {
        while (coordinate >= 2) {
            coordinate -= 2;
        }
        return coordinate;
    }

    private Coordinate move(Coordinate coordinate, Direction direction, Integer step) {
        int row = coordinate.row();
        int col = coordinate.col();

        return switch (direction) {
            case UP -> new Coordinate(row - step, col);
            case DOWN -> new Coordinate(row + step, col);
            case LEFT -> new Coordinate(row, col - step);
            case RIGHT -> new Coordinate(row, col + step);
        };
    }

    public static void main(String[] args) {
        GridGenerator gridGenerator = new GridGenerator(5, 5);
        MazeListModel mazeListModel = gridGenerator.getMazeListModel(new Coordinate(1, 1));
        Cell[][] cells = mazeListModel.mazeList();
        for (Cell[] row : cells) {
            for (Cell cell : row) {
                System.out.print(cell + " ");
                System.out.println();

            }
            System.out.println("-------------------------------------------------------------");
        }

        Map<Coordinate, List<Coordinate>> cordList = mazeListModel.coordinateNeighboursMap();
        cordList.forEach((k, v) -> {
            System.out.println(k);
            v.forEach(c -> System.out.print(c + " "));
            System.out.println();
            System.out.println("-----------------------------------------");
        });
    }

}

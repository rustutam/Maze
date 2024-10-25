package backend.academy;

import backend.academy.cell.Cell;
import backend.academy.cell.Passage;
import backend.academy.cell.Wall;
import backend.academy.models.MazeListModel;
import java.util.ArrayList;
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
        addLinks();
        updateMazeList();

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
                coordinateNeighbours.put(new Coordinate(i, j), new ArrayList<>());
            }
        }
    }

    private void addLinks() {
        List<Coordinate> allCoordinates = coordinateNeighbours.keySet().stream().toList();
        allCoordinates
            .forEach(cord -> {
                List<Coordinate> neighbours = getNeighbours(cord);
                for (Coordinate neighbour : neighbours) {
                    if (isWithinBounds(neighbour)) {
                        addCoordinateNeighbour(cord, neighbour);
                    } else {
                        Coordinate middleCoordinate = getMiddleCoordinate(cord, neighbour);
                        if (isWithinBounds(middleCoordinate)) {
                            addCoordinateNeighbour(cord, middleCoordinate);
                            addCoordinateNeighbour(middleCoordinate, cord);
                        }
                    }
                }
            });
    }

    private void updateMazeList() {
        coordinateNeighbours.keySet()
            .forEach(cord -> mazeList[cord.row()][cord.col()] = new Passage(cord.row(), cord.col()));
    }

    private List<Coordinate> getNeighbours(Coordinate coordinate) {
        List<Coordinate> neighboursCoordinate = new ArrayList<>();
        for (Direction direction : Direction.values()) {
            Coordinate newCoordinate = move(coordinate, direction);
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

    private void addCoordinateNeighbour(Coordinate c1, Coordinate c2) {
        coordinateNeighbours.computeIfAbsent(c1, _ -> new ArrayList<>()).add(c2);
    }

    private int adjustCoordinate(int coordinate) {
        while (coordinate >= 2) {
            coordinate -= 2;
        }
        return coordinate;
    }

    private Coordinate move(Coordinate coordinate, Direction direction) {
        int row = coordinate.row();
        int col = coordinate.col();

        return switch (direction) {
            case UP -> new Coordinate(row - 2, col);
            case DOWN -> new Coordinate(row + 2, col);
            case LEFT -> new Coordinate(row, col - 2);
            case RIGHT -> new Coordinate(row, col + 2);
        };
    }

    public static void main(String[] args) {
        GridGenerator gridGenerator = new GridGenerator(5, 5);
        MazeListModel mazeListModel = gridGenerator.getMazeListModel(new Coordinate(0, 1));
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

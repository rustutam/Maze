package backend.academy.app;

import backend.academy.cell.Cell;
import backend.academy.cell.Passage;
import backend.academy.cell.Wall;
import backend.academy.models.Coordinate;
import backend.academy.models.Direction;
import backend.academy.models.MazeListModel;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GridGenerator {

    private Cell[][] mazeList;
    private final Map<Coordinate, List<Coordinate>> coordinateNeighbours = new HashMap<>();

    public MazeListModel getMazeListModel(Coordinate startCoordinate, int height, int width) {
        /*
        Создает сетку лабиринта
        Сеткой будет являться каждая вторая клетка, чтобы оставить место для стен.
        Для граничных случаев добавляем в сетку новую ячейку,
        которая будет находиться между двумя соседними клетками.
        */

        generateFullWallMaze(height, width);
        generateGrid(startCoordinate, height, width);
        addLinks(height, width);
        updateMazeList();

        return new MazeListModel(mazeList, coordinateNeighbours, height, width);
    }

    private void generateFullWallMaze(int height, int width) {
        //заполняет лабиринт стенами
        mazeList = new Cell[height][width];
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                mazeList[i][j] = new Wall(i, j);
            }
        }
    }

    private void generateGrid(Coordinate startCoordinate, int height, int width) {
        //Создает сетку лабиринта. Делаем шаг 2, чтобы оставить места для отрисовки стен
        int x = adjustCoordinate(startCoordinate.col());
        int y = adjustCoordinate(startCoordinate.row());

        for (int i = y; i < height; i += 2) {
            for (int j = x; j < width; j += 2) {
                coordinateNeighbours.put(new Coordinate(i, j), new ArrayList<>());
            }
        }
    }

    private void addLinks(int height, int width) {

        // Получаем все координаты, которые являются проходами
        List<Coordinate> allCoordinates = coordinateNeighbours.keySet().stream().toList();
        allCoordinates
            .forEach(cord -> {
                // Получаем соседей для каждой координаты
                List<Coordinate> neighbours = getNeighbours(cord);
                for (Coordinate neighbour : neighbours) {
                    // Если соседняя координата находится в пределах лабиринта, добавляем её как соседа
                    if (isWithinBounds(neighbour, height, width)) {
                        addCoordinateNeighbour(cord, neighbour);
                    } else {
                        // Если соседняя координата вне пределов лабиринта, находим среднюю координату между ними
                        Coordinate middleCoordinate = getMiddleCoordinate(cord, neighbour);
                        // Если средняя координата находится в пределах лабиринта, добавляем её как соседа
                        if (isWithinBounds(middleCoordinate, height, width)) {
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
        List<Coordinate> neighboursCoordinate = new ArrayList<>(Direction.values().length);
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

    private boolean isWithinBounds(Coordinate coordinate, int height, int width) {
        return coordinate.row() >= 0 && coordinate.row() < height
            && coordinate.col() >= 0 && coordinate.col() < width;
    }

    private void addCoordinateNeighbour(Coordinate c1, Coordinate c2) {
        coordinateNeighbours.computeIfAbsent(c1, ignored -> new ArrayList<>()).add(c2);
    }

    private int adjustCoordinate(int coordinate) {
        // Чтобы удобно итерироваться по координатам, сдвигаем coordinate максимально в левый верхний угол
        int adjustedCoordinate = coordinate;
        while (adjustedCoordinate >= 2) {
            adjustedCoordinate -= 2;
        }
        return adjustedCoordinate;
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
}

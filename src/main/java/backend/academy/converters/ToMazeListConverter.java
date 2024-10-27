package backend.academy.converters;

import backend.academy.cell.Cell;
import backend.academy.cell.Passage;
import backend.academy.cell.Wall;
import backend.academy.graph.Graph;
import backend.academy.graph.Vertex;
import backend.academy.models.ConvertedMazeModel;
import backend.academy.models.Coordinate;
import backend.academy.models.MazeListModel;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ToMazeListConverter {

    private Cell[][] mazeList;
    private final Map<Coordinate, List<Coordinate>> coordinateNeighbours = new HashMap<>();

    public MazeListModel convertToMazeList(ConvertedMazeModel convertedMazeModel) {
        // Преобразуем модель графа в модель лабиринта
        generateFullWallMaze(convertedMazeModel.height(), convertedMazeModel.width());
        updateMazeList(convertedMazeModel);

        return new MazeListModel(mazeList, coordinateNeighbours, convertedMazeModel.height(),
            convertedMazeModel.width());
    }

    private void updateMazeList(ConvertedMazeModel convertedMazeModel) {
        // Обновляем лабиринт на основе модели графа
        Graph graph = convertedMazeModel.graph();
        List<Vertex> vertices = graph.getVertices();
        for (Vertex vertex : vertices) {
            List<Vertex> neighboursVertexList =
                graph.getNeighbours(vertex).stream().map(edge -> edge.getSecondVertex(vertex)).toList();
            Coordinate coordinate = getCoordinateByVertex(vertex, convertedMazeModel);
            int row = coordinate.row();
            int col = coordinate.col();
            mazeList[row][col] = new Passage(row, col);
            for (Vertex neighbour : neighboursVertexList) {
                Coordinate neighbourCoordinate = getCoordinateByVertex(neighbour, convertedMazeModel);
                int neighbourRow = neighbourCoordinate.row();
                int neighbourCol = neighbourCoordinate.col();
                mazeList[neighbourRow][neighbourCol] = new Passage(neighbourRow, neighbourCol);
                createIntermediatePassages(coordinate, neighbourCoordinate);
            }

        }
    }

    private void createIntermediatePassages(Coordinate start, Coordinate end) {
        /*
        При преобразовании графа в лабиринт могут возникнуть ситуации,
        что граф соединяет 2 вершины, которые в лабиринте находятся не на соседних ячейках.
        Поэтому при конвертировании в лабиринт нужно добавлять новые проходы.
        Соединяем координату, соответствующей 1 вершины с координатой, соответствующей 2 вершине
        */
        int rowDiff = end.row() - start.row();
        int colDiff = end.col() - start.col();

        if (rowDiff != 0) {
            int rowStep = rowDiff / Math.abs(rowDiff);
            for (int row = start.row() + rowStep; row != end.row() + rowStep; row += rowStep) {
                // Устанавливаем проход в лабиринте и добавляем соседние координаты
                mazeList[row][start.col()] = new Passage(row, start.col());
                addCoordinateNeighbour(new Coordinate(row - rowStep, start.col()), new Coordinate(row, start.col()));
            }
        } else if (colDiff != 0) {
            int colStep = colDiff / Math.abs(colDiff);
            for (int col = start.col() + colStep; col != end.col() + colStep; col += colStep) {
                // Устанавливаем проход в лабиринте и добавляем соседние координаты
                mazeList[start.row()][col] = new Passage(start.row(), col);
                addCoordinateNeighbour(new Coordinate(start.row(), col - colStep), new Coordinate(start.row(), col));
            }
        }
    }

    private void generateFullWallMaze(int height, int width) {
        mazeList = new Cell[height][width];
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                mazeList[i][j] = new Wall(i, j);
            }
        }
    }

    private void addCoordinateNeighbour(Coordinate key, Coordinate value) {
        coordinateNeighbours.computeIfAbsent(key, ignored -> new ArrayList<>()).add(value);
    }

    private Coordinate getCoordinateByVertex(Vertex vertex, ConvertedMazeModel convertedMazeModel) {
        return convertedMazeModel.coordinateVertexMap().entrySet().stream()
            .filter(entry -> entry.getValue().equals(vertex))
            .map(Map.Entry::getKey)
            .findFirst()
            .orElseThrow(() -> new IllegalArgumentException("Vertex not found in the map"));
    }

}

package backend.academy.converters;

import backend.academy.cell.Cell;
import backend.academy.cell.CellType;
import backend.academy.cell.Passage;
import backend.academy.graph.Graph;
import backend.academy.graph.Vertex;
import backend.academy.models.ConvertedMazeModel;
import backend.academy.models.Coordinate;
import backend.academy.models.MazeListModel;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ToGraphConverter {
    private static final int COIN_WEIGHT = 0;
    private static final int NORMAL_WEIGHT = 10;
    private static final int SAND_WEIGHT = 100;

    public ConvertedMazeModel convertToGraph(MazeListModel mazeListModel) {
        Graph graph = new Graph();
        Map<Coordinate, Vertex> coordinateVertexMap = new HashMap<>();
        // Преобразуем модель лабиринта в модель графа
        Cell[][] mazeList = mazeListModel.mazeList();
        List<Cell> passageList = getPassageList(mazeList);
        addVertexToGraph(passageList, graph, coordinateVertexMap);
        addEdgesToGraph(mazeListModel, graph, coordinateVertexMap);

        return new ConvertedMazeModel(graph, coordinateVertexMap, mazeListModel.height(), mazeListModel.width());

    }

    private List<Cell> getPassageList(Cell[][] mazeList) {
        // Получаем все проходы из лабиринта
        return Arrays.stream(mazeList)
            .flatMap(Arrays::stream)
            .filter(cell -> cell.type() == CellType.PASSAGE)
            .toList();
    }

    private void addVertexToGraph(List<Cell> passageList, Graph graph, Map<Coordinate, Vertex> coordinateVertexMap) {
        passageList.forEach(cell -> {
                if (cell instanceof Passage passage) {
                    Coordinate coordinate = new Coordinate(cell.row(), cell.col());
                    Vertex vertex = new Vertex();
                    // Устанавливаем вес вершины в зависимости от типа прохода
                    switch (passage.passageType()) {
                        case COIN -> vertex.weight(COIN_WEIGHT);
                        case SAND -> vertex.weight(SAND_WEIGHT);
                        default -> vertex.weight(NORMAL_WEIGHT);

                    }
                    coordinateVertexMap.put(coordinate, vertex);
                    graph.addVertex(vertex);

                }
            }
        );
    }

    private void addEdgesToGraph(
        MazeListModel mazeListModel,
        Graph graph,
        Map<Coordinate, Vertex> coordinateVertexMap
    ) {

        graph.getVertices().forEach(vertex -> {
            Coordinate coordinate = getCoordinateWithVertex(vertex, coordinateVertexMap);
            List<Coordinate> neighbours = mazeListModel.coordinateNeighboursMap().get(coordinate);
            if (neighbours != null) {
                neighbours.forEach(neighbourCoordinate -> {
                    Vertex neighbourVertex = coordinateVertexMap.get(neighbourCoordinate);
                    graph.addEdge(vertex, neighbourVertex);
                });
            }
        });

    }

    private Coordinate getCoordinateWithVertex(Vertex vertex, Map<Coordinate, Vertex> coordinateVertexMap) {
        return coordinateVertexMap.entrySet().stream()
            .filter(entry -> entry.getValue().equals(vertex))
            .map(Map.Entry::getKey)
            .findFirst()
            .orElseThrow(() -> new IllegalArgumentException("Vertex not found in the map"));
    }
}

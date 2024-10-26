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
    private static final int COIN_WEIGHT = 10;
    private static final int SAND_WEIGHT = 0;
    private static final int NORMAL_WEIGHT = 1;

    private final MazeListModel mazeListModel;
    private final Graph graph = new Graph();
    private final Map<Coordinate, Vertex> coordinateVertexMap = new HashMap<>();

    public ToGraphConverter(MazeListModel mazeListModel) {
        this.mazeListModel = mazeListModel;
    }

    public ConvertedMazeModel convertToGraph() {
        Cell[][] mazeList = mazeListModel.mazeList();
        List<Cell> passageList = getPassageList(mazeList);
        addVertexToGraph(passageList);
        addEdgesToGraph();

        return new ConvertedMazeModel(graph, coordinateVertexMap, mazeListModel.height(), mazeListModel.width());

    }

    private List<Cell> getPassageList(Cell[][] mazeList) {
        return Arrays.stream(mazeList)
            .flatMap(Arrays::stream)
            .filter(cell -> cell.type() == CellType.PASSAGE)
            .toList();
    }

    private void addVertexToGraph(List<Cell> passageList) {
        passageList.forEach(cell -> {
                if (cell instanceof Passage passage) {
                    Coordinate coordinate = new Coordinate(cell.row(), cell.col());
                    Vertex vertex = new Vertex();
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

    private void addEdgesToGraph() {
        graph.getVertices().forEach(vertex -> {
            Coordinate coordinate = getCoordinateWithVertex(vertex);
            List<Coordinate> neighbours = mazeListModel.coordinateNeighboursMap().get(coordinate);
            neighbours.forEach(neighbourCoordinate -> {
                Vertex neighbourVertex = coordinateVertexMap.get(neighbourCoordinate);
                graph.addEdge(vertex, neighbourVertex);
            });
        });

    }

    private Coordinate getCoordinateWithVertex(Vertex vertex) {
        return coordinateVertexMap.entrySet().stream()
            .filter(entry -> entry.getValue().equals(vertex))
            .map(Map.Entry::getKey)
            .findFirst()
            .orElseThrow(() -> new IllegalArgumentException("Vertex not found in the map"));
    }
}

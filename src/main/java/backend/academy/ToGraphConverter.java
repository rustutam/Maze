package backend.academy;

import backend.academy.cell.Cell;
import backend.academy.cell.Passage;
import backend.academy.cell.Wall;
import backend.academy.graph.Graph;
import backend.academy.graph.Vertex;
import backend.academy.models.ConvertedMazeModel;
import backend.academy.models.MazeListModel;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ToGraphConverter {
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
            .filter(cell -> cell.type() == Cell.CellType.PASSAGE)
            .toList();
    }

    private void addVertexToGraph(List<Cell> passageList) {
        passageList.forEach(cell -> {
                Coordinate coordinate = new Coordinate(cell.row(), cell.col());
                Vertex vertex = new Vertex();
                coordinateVertexMap.put(coordinate, vertex);
                graph.addVertex(vertex);
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

    public static void main(String[] args) {
        int height = 5;
        int width = 5;
        GridGenerator gridGenerator = new GridGenerator(height, width);
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
        coordinateNeighbours.put(new Coordinate(0, 1), List.of(new Coordinate(1, 1), new Coordinate(0, 3)));
        coordinateNeighbours.put(new Coordinate(0, 3), List.of(new Coordinate(0, 1), new Coordinate(1, 3)));

        coordinateNeighbours.put(new Coordinate(1, 0), List.of(new Coordinate(1, 1), new Coordinate(3, 0)));
        coordinateNeighbours.put(new Coordinate(1, 1), List.of(new Coordinate(1, 0),new Coordinate(0, 1), new Coordinate(1, 3),new Coordinate(3, 1)));
        coordinateNeighbours.put(new Coordinate(1, 3), List.of(new Coordinate(1, 1), new Coordinate(0, 3), new Coordinate(3, 3), new Coordinate(1, 4)));
        coordinateNeighbours.put(new Coordinate(1, 4), List.of(new Coordinate(1, 3), new Coordinate(3, 4)));

        coordinateNeighbours.put(new Coordinate(3, 0), List.of(new Coordinate(1, 0), new Coordinate(3, 1)));
        coordinateNeighbours.put(new Coordinate(3, 1), List.of(new Coordinate(3, 0), new Coordinate(1, 1), new Coordinate(3, 3), new Coordinate(4, 1)));
        coordinateNeighbours.put(new Coordinate(3, 3), List.of(new Coordinate(3, 1), new Coordinate(1, 3), new Coordinate(3, 4), new Coordinate(4, 3)));
        coordinateNeighbours.put(new Coordinate(3, 4), List.of(new Coordinate(3, 3), new Coordinate(1, 4)));

        coordinateNeighbours.put(new Coordinate(4, 1), List.of(new Coordinate(3, 1), new Coordinate(4, 3)));
        coordinateNeighbours.put(new Coordinate(4, 3), List.of(new Coordinate(4, 1), new Coordinate(3, 3)));



        MazeListModel myMazeListModel = new MazeListModel(mazeList, coordinateNeighbours, height, width);

        ConvertedMazeModel convertedMaze = new ToGraphConverter(myMazeListModel).convertToGraph();
    }

}

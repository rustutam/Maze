package backend.academy;

import backend.academy.generator.Generator;
import backend.academy.graph.Graph;
import backend.academy.graph.Vertex;
import backend.academy.models.ConvertedMazeModel;
import backend.academy.models.MazeListModel;
import backend.academy.solver.Solver;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class MazeService {
    Random random;

    public MazeService(Random random) {
        this.random = random;
    }

    public Maze generateMaze(int height, int width, Generator mazeGenerator) {
        //получаем сетку
        MazeListModel mazeListModel = getRandomGrid(height, width);

        //строим из этой сетки полный граф
        ConvertedMazeModel convertedMazeModel = new ToGraphConverter(mazeListModel).convertToGraph();

        //генератор возвращает остов
        Graph minGraph = mazeGenerator.generate(convertedMazeModel.graph());
        convertedMazeModel = new ConvertedMazeModel(minGraph, convertedMazeModel.coordinateVertexMap(), height, width);
        //преобразуем остов в лабиринт
        MazeListModel minMazeListModel = new ToMazeListConverter(convertedMazeModel).convertToMazeList();
        // выдаем Maze
        return new Maze(height, width, minMazeListModel);

    }

    public List<Coordinate> searchPathInMaze(
        Maze maze,
        Coordinate startCoordinate,
        Coordinate finishCoordinate,
        Solver solver
    ) {
        //получаем лабиринт
        MazeListModel mazeListModel = maze.mazeListModel();
        //получаем стартовую и конечную точку

        //преобразуем лабиринт в граф
        ConvertedMazeModel convertedMazeModel = new ToGraphConverter(mazeListModel).convertToGraph();
        //получаем стартовую и конечную вершины
        Vertex startVertex = convertedMazeModel.coordinateVertexMap().get(startCoordinate);
        Vertex finishVertex = convertedMazeModel.coordinateVertexMap().get(finishCoordinate);
        //находим путь
        List<Vertex> path = solver.solve(convertedMazeModel.graph(), startVertex, finishVertex);
        // преобразуем путь в координаты
        return path.stream()
            .map(vertex -> convertedMazeModel.coordinateVertexMap().entrySet().stream()
                .filter(entry -> entry.getValue().equals(vertex))
                .map(Map.Entry::getKey)
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("Vertex not found in coordinate map")))
            .toList();

    }

    private MazeListModel getRandomGrid(int height, int width) {
        GridGenerator gridGenerator = new GridGenerator(height, width);
        Coordinate startCoordinate = new Coordinate(random.nextInt(height), random.nextInt(width));
        return gridGenerator.getMazeListModel(startCoordinate);
    }

}


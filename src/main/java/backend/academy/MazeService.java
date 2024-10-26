package backend.academy;

import backend.academy.cell.CellType;
import backend.academy.cell.Passage;
import backend.academy.cell.PassageType;
import backend.academy.converters.ToGraphConverter;
import backend.academy.converters.ToMazeListConverter;
import backend.academy.generator.Generator;
import backend.academy.graph.Graph;
import backend.academy.graph.Vertex;
import backend.academy.models.ConvertedMazeModel;
import backend.academy.models.Coordinate;
import backend.academy.models.Maze;
import backend.academy.models.MazeListModel;
import backend.academy.solver.Solver;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class MazeService {
    SecureRandom random;

    public MazeService(SecureRandom random) {
        this.random = random;
    }

    public Maze generateMaze(int height, int width, Generator mazeGenerator, int coinCount, int sandCount) {
        //получаем сетку
        MazeListModel mazeListModel = getRandomGrid(height, width);

        //строим из этой сетки полный граф
        ConvertedMazeModel convertedMazeModel = new ToGraphConverter(mazeListModel).convertToGraph();

        //генератор возвращает остов
        Graph minGraph = mazeGenerator.generate(convertedMazeModel.graph());
        convertedMazeModel = new ConvertedMazeModel(minGraph, convertedMazeModel.coordinateVertexMap(), height, width);

        //преобразуем остов в лабиринт
        MazeListModel minMazeListModel = new ToMazeListConverter(convertedMazeModel).convertToMazeList();

        //добавляем монеты
        addNewSurfaces(minMazeListModel, PassageType.COIN, coinCount);
        //добавляем песок
        addNewSurfaces(minMazeListModel, PassageType.SAND, sandCount);
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

    public void addNewSurfaces(MazeListModel mazeListModel, PassageType passageType, int count) {
        List<Coordinate> allNormalTypePassages =
            Arrays.stream(mazeListModel.mazeList()).map(Arrays::asList).flatMap(List::stream)
                .filter(cell -> cell.type() == CellType.PASSAGE)
                .filter(cell -> ((Passage) cell).passageType() == PassageType.NORMAL)
                .map(cell -> new Coordinate(cell.row(), cell.col()))
                .collect(Collectors.toList());
        int passageCount = allNormalTypePassages.size();
        int remainingCount = Math.min(count, passageCount);
        while (remainingCount > 0) {
            int index = random.nextInt(passageCount);
            Coordinate coordinate = allNormalTypePassages.get(index);
            Passage passage = (Passage) mazeListModel.mazeList()[coordinate.row()][coordinate.col()];
            passage.passageType(passageType);
            allNormalTypePassages.remove(index);
            remainingCount -= 1;
            passageCount -= 1;

        }

    }

}


package backend.academy.app;

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
import backend.academy.models.MazeGenerationParamsModel;
import backend.academy.models.MazeListModel;
import backend.academy.solver.Solver;
import java.security.SecureRandom;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class MazeServiceTest {
    private MazeService mazeService;
    private SecureRandom random;
    private Solver solver;

    @BeforeEach
    void setUp() {
        random = mock(SecureRandom.class);
        solver = mock(Solver.class);
        mazeService = new MazeService(random);
    }

    @Test
    void generateMaze() {
        // Arrange
        int height = 5;
        int width = 5;
        int coinCount = 0;
        int sandCount = 0;

        MazeGenerationParamsModel mazeGenerationParamsModel = mock(MazeGenerationParamsModel.class);
        when(mazeGenerationParamsModel.height()).thenReturn(height);
        when(mazeGenerationParamsModel.width()).thenReturn(width);
        when(mazeGenerationParamsModel.coinCount()).thenReturn(coinCount);
        when(mazeGenerationParamsModel.sandCount()).thenReturn(sandCount);
        Generator mazeGenerator = mock(Generator.class);
        when(mazeGenerationParamsModel.generator()).thenReturn(mazeGenerator);
        ToGraphConverter toGraphConverter = mock(ToGraphConverter.class);
        when(mazeGenerationParamsModel.toGraphConverter()).thenReturn(toGraphConverter);
        ToMazeListConverter toMazeListConverter = mock(ToMazeListConverter.class);
        when(mazeGenerationParamsModel.toMazeListConverter()).thenReturn(toMazeListConverter);
        GridGenerator gridGenerator = mock(GridGenerator.class);
        when(mazeGenerationParamsModel.gridGenerator()).thenReturn(gridGenerator);

        MazeListModel initialMazeListModel = mock(MazeListModel.class);
        when(gridGenerator.getMazeListModel(any(Coordinate.class), eq(height), eq(width))).thenReturn(
            initialMazeListModel);

        ConvertedMazeModel convertedMazeModel = mock(ConvertedMazeModel.class);
        when(toGraphConverter.convertToGraph(initialMazeListModel)).thenReturn(convertedMazeModel);

        Graph graph = mock(Graph.class);
        when(convertedMazeModel.graph()).thenReturn(graph);

        Graph minGraph = mock(Graph.class);
        when(mazeGenerator.generate(graph)).thenReturn(minGraph);

        MazeListModel minMazeListModel = mock(MazeListModel.class);
        when(toMazeListConverter.convertToMazeList(any(ConvertedMazeModel.class))).thenReturn(minMazeListModel);

        // Act
        Maze result = mazeService.generateMaze(mazeGenerationParamsModel);

        // Assert
        assertNotNull(result);
        assertEquals(5, result.height());
        assertEquals(5, result.width());
        verify(gridGenerator).getMazeListModel(any(Coordinate.class), eq(5), eq(5));
        verify(toGraphConverter).convertToGraph(initialMazeListModel);
        verify(mazeGenerator).generate(graph);
        verify(toMazeListConverter).convertToMazeList(any(ConvertedMazeModel.class));
    }

    @Test
    void searchPathInMaze() {
        // Arrange
        Maze maze = mock(Maze.class);
        Coordinate startCoordinate = new Coordinate(0, 0);
        Coordinate finishCoordinate = new Coordinate(4, 4);
        MazeListModel mazeListModel = mock(MazeListModel.class);
        when(maze.mazeListModel()).thenReturn(mazeListModel);

        ConvertedMazeModel convertedMazeModel = mock(ConvertedMazeModel.class);
        ToGraphConverter toGraphConverter = mock(ToGraphConverter.class);
        when(toGraphConverter.convertToGraph(mazeListModel)).thenReturn(convertedMazeModel);

        Vertex startVertex = new Vertex();
        Vertex finishVertex = new Vertex();
        when(convertedMazeModel.coordinateVertexMap()).thenReturn(Map.of(
            startCoordinate, startVertex,
            finishCoordinate, finishVertex
        ));

        Graph graph = mock(Graph.class);
        when(convertedMazeModel.graph()).thenReturn(graph);

        List<Vertex> path = List.of(startVertex, finishVertex);
        when(solver.solve(eq(graph), eq(startVertex), eq(finishVertex))).thenReturn(path);

        // Act
        List<Coordinate> result =
            mazeService.searchPathInMaze(maze, startCoordinate, finishCoordinate, solver, toGraphConverter);

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(startCoordinate, result.get(0));
        assertEquals(finishCoordinate, result.get(1));
    }

    @Test
    void addNewSurfaces_addsCoins() {
        // Arrange
        MazeListModel mazeListModel = mock(MazeListModel.class);
        Passage passage = mock(Passage.class);
        when(passage.type()).thenReturn(CellType.PASSAGE);
        when(passage.passageType()).thenReturn(PassageType.NORMAL);
        Passage[][] mazeArray = {
            {passage, passage},
            {passage, passage}
        };
        when(mazeListModel.mazeList()).thenReturn(mazeArray);
        when(random.nextInt(anyInt())).thenReturn(0, 1, 2, 3);

        // Act
        mazeService.addNewSurfaces(mazeListModel, PassageType.COIN, 2);

        // Assert
        verify(passage, times(2)).passageType(PassageType.COIN);
    }

    @Test
    void addNewSurfaces_addsSand() {
        // Arrange
        MazeListModel mazeListModel = mock(MazeListModel.class);
        Passage passage = mock(Passage.class);
        when(passage.type()).thenReturn(CellType.PASSAGE);
        when(passage.passageType()).thenReturn(PassageType.NORMAL);
        Passage[][] mazeArray = {
            {passage, passage},
            {passage, passage}
        };
        when(mazeListModel.mazeList()).thenReturn(mazeArray);
        when(random.nextInt(anyInt())).thenReturn(3, 2, 1, 0);

        // Act
        mazeService.addNewSurfaces(mazeListModel, PassageType.SAND, 3);

        // Assert
        verify(passage, times(3)).passageType(PassageType.SAND);
    }

    @Test
    void addNewSurfaces_handlesMoreSurfacesThanPassages() {
        // Arrange
        MazeListModel mazeListModel = mock(MazeListModel.class);
        Passage passage = mock(Passage.class);
        when(passage.type()).thenReturn(CellType.PASSAGE);
        when(passage.passageType()).thenReturn(PassageType.NORMAL);
        Passage[][] mazeArray = {
            {passage, passage},
            {passage, passage}
        };
        when(mazeListModel.mazeList()).thenReturn(mazeArray);
        when(random.nextInt(anyInt())).thenReturn(3, 2, 1, 0);

        // Act
        mazeService.addNewSurfaces(mazeListModel, PassageType.COIN, 10);

        // Assert
        verify(passage, times(4)).passageType(PassageType.COIN);
    }
}

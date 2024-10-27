package backend.academy.app;

import backend.academy.converters.ToGraphConverter;
import backend.academy.converters.ToMazeListConverter;
import backend.academy.generator.Generator;
import backend.academy.models.Coordinate;
import backend.academy.models.Maze;
import backend.academy.models.MazeGenerationParamsModel;
import backend.academy.solver.Solver;
import backend.academy.visualization.Renderer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.PrintStream;
import java.util.List;

import static org.mockito.Mockito.*;

class AppTest {
    private PrintStream out;
    private Settings settings;
    private Renderer ui;
    private MazeService mazeService;
    private App app;
    private ToGraphConverter toGraphConverter;
    private ToMazeListConverter toMazeListConverter;
    private GridGenerator gridGenerator;

    @BeforeEach
    void setUp() {
        out = mock(PrintStream.class);
        settings = mock(Settings.class);
        ui = mock(Renderer.class);
        mazeService = mock(MazeService.class);
        toGraphConverter = mock(ToGraphConverter.class);
        toMazeListConverter = mock(ToMazeListConverter.class);
        gridGenerator = mock(GridGenerator.class);

        app = new App(out, settings, ui, mazeService, toGraphConverter, toMazeListConverter, gridGenerator);
    }

    @Test
    void start() {
        // Arrange
        int height = 5;
        int width = 5;
        int coinCount = 10;
        int sandCount = 5;
        Generator generator = mock(Generator.class);
        Solver solver = mock(Solver.class);
        Maze maze = mock(Maze.class);
        List<Coordinate> allowedCoordinates = List.of(new Coordinate(0, 0), new Coordinate(4, 4));
        Coordinate startCoordinate = new Coordinate(0, 0);
        Coordinate finishCoordinate = new Coordinate(4, 4);
        List<Coordinate> path = List.of(startCoordinate, finishCoordinate);
        MazeGenerationParamsModel mazeGenerationParamsModel = new MazeGenerationParamsModel(
                height,
                width,
                generator,
                coinCount,
                sandCount,
                toGraphConverter,
                toMazeListConverter,
                gridGenerator
        );

        when(settings.getMazeHeight()).thenReturn(height);
        when(settings.getMazeWeight()).thenReturn(width);
        when(settings.getGenerateAlgorithm()).thenReturn(generator);
        when(settings.getCoinCount()).thenReturn(coinCount);
        when(settings.getSandCount()).thenReturn(sandCount);
        when(settings.getSolverAlgorithm()).thenReturn(solver);
        when(maze.getAllPassage()).thenReturn(allowedCoordinates);
        when(settings.getStartCoordinate(allowedCoordinates)).thenReturn(startCoordinate);
        when(settings.getFinishCoordinate(allowedCoordinates)).thenReturn(finishCoordinate);
        when(mazeService.generateMaze(mazeGenerationParamsModel)).thenReturn(maze);
        when(mazeService.searchPathInMaze(maze, startCoordinate, finishCoordinate, solver, toGraphConverter)).thenReturn(path);

        when(ui.render(maze)).thenReturn("maze");
        when(ui.render(maze, path)).thenReturn("maze + path");
        // Act
        app.start();

        // Assert
        verify(ui).render(maze);
        verify(ui).render(maze, path);
        verify(out).println(ui.render(maze));
        verify(out).println(ui.render(maze, path));


    }
}

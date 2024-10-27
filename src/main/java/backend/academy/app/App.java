package backend.academy.app;

import backend.academy.converters.ToGraphConverter;
import backend.academy.converters.ToMazeListConverter;
import backend.academy.models.Coordinate;
import backend.academy.models.Maze;
import backend.academy.models.MazeGenerationParamsModel;
import backend.academy.solver.Solver;
import backend.academy.visualization.Renderer;
import java.io.PrintStream;
import java.util.List;

public class App {
    private final PrintStream out;
    private final Settings settings;
    private final Renderer ui;
    private final MazeService mazeService;
    private final ToGraphConverter toGraphConverter;
    private final ToMazeListConverter toMazeListConverter;
    private final GridGenerator gridGenerator;

    public App(
        PrintStream out,
        Settings settings,
        Renderer ui,
        MazeService mazeService,
        ToGraphConverter toGraphConverter,
        ToMazeListConverter toMazeListConverter,
        GridGenerator gridGenerator
    ) {
        this.out = out;
        this.settings = settings;
        this.ui = ui;
        this.mazeService = mazeService;
        this.toGraphConverter = toGraphConverter;
        this.toMazeListConverter = toMazeListConverter;
        this.gridGenerator = gridGenerator;
    }

    public void start() {
        MazeGenerationParamsModel mazeGenerationParamsModel = new MazeGenerationParamsModel(
            settings.getMazeHeight(),
            settings.getMazeWeight(),
            settings.getGenerateAlgorithm(),
            settings.getCoinCount(),
            settings.getSandCount(),
            toGraphConverter,
            toMazeListConverter,
            gridGenerator
        );
        Maze maze = mazeService.generateMaze(mazeGenerationParamsModel);
        out.println(ui.render(maze));

        Solver solver = settings.getSolverAlgorithm();

        List<Coordinate> allowedCoordinates = maze.getAllPassage();
        Coordinate startCoordinate = settings.getStartCoordinate(allowedCoordinates);
        Coordinate finishCoordinate = settings.getFinishCoordinate(allowedCoordinates);
        List<Coordinate> path =
            mazeService.searchPathInMaze(maze, startCoordinate, finishCoordinate, solver, toGraphConverter);
        out.println(ui.render(maze, path));
    }
}

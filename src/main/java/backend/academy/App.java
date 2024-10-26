package backend.academy;

import backend.academy.generator.Generator;
import backend.academy.models.Coordinate;
import backend.academy.models.Maze;
import backend.academy.solver.Solver;
import backend.academy.visualization.Renderer;
import java.io.PrintStream;
import java.util.List;
import java.util.Random;

public class App {
    private final PrintStream out;
    private final Settings settings;
    private final Renderer ui;
    private final Random random;

    public App(PrintStream out, Settings settings, Renderer ui, Random random) {
        this.out = out;
        this.settings = settings;
        this.ui = ui;
        this.random = random;

    }

    void start() {
        int height = settings.getMazeHeight();
        int weight = settings.getMazeWeight();
        Generator generator = settings.getGenerateAlgorithm();
        int coinCount = settings.getCoinCount();
        int sandCount = settings.getSandCount();

        MazeService mazeService = new MazeService(random);
        Maze maze = mazeService.generateMaze(height, weight, generator, coinCount, sandCount);
        out.println(ui.render(maze));

        Solver solver = settings.getSolverAlgorithm();

        List<Coordinate> allowedCoordinates = maze.getAllPassage();
        Coordinate startCoordinate = settings.getStartCoordinate(allowedCoordinates);
        allowedCoordinates.remove(startCoordinate);
        Coordinate finishCoordinate = settings.getFinishCoordinate(allowedCoordinates);
        List<Coordinate> path = mazeService.searchPathInMaze(maze, startCoordinate, finishCoordinate, solver);
        out.println(ui.render(maze, path));
    }
}

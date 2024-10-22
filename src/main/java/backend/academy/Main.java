package backend.academy;

import backend.academy.generator.Generator;
import backend.academy.generator.KruskalMazeGenerator;
import backend.academy.generator.PrimaMazeGenerator;
import backend.academy.graph.Vertex;
import backend.academy.graph.VertexType;
import backend.academy.input.ConsoleInputProvider;
import backend.academy.solver.BfsSolver;
import backend.academy.solver.DijkstraSolver;
import backend.academy.solver.Solver;
import backend.academy.visualization.ConsoleRenderer;
import backend.academy.visualization.Renderer;
import lombok.experimental.UtilityClass;
import java.util.List;
import java.util.Random;

@UtilityClass
public class Main {
    public static void main(String[] args) {
        var in = System.in;
        var out = System.out;
        Random random = new Random();
        Renderer visual= new ConsoleRenderer();

        ConsoleInputProvider consoleInputProvider = new ConsoleInputProvider(in, out);
        Settings settings = new Settings(consoleInputProvider, out, random);

        int height = settings.getMazeHeight();
        int weight = settings.getMazeWeight();
        Generator generator = settings.getGenerateAlgorithm();

        Maze maze = generator.generate(height, weight);
        out.println(visual.render(maze));

        List<Coordinate> allowedCoordinates = maze.getCoordinates();
        Coordinate startCoordinate = settings.getStartCoordinate(allowedCoordinates);
        allowedCoordinates.remove(startCoordinate);
        Coordinate finishCoordinate = settings.getFinishCoordinate(allowedCoordinates);
        Solver solverAlgorithm = settings.getSolverAlgorithm();
        List<Vertex> path = solverAlgorithm.solve(maze, startCoordinate, finishCoordinate);
        out.println(visual.render(maze, path));





//        Generator kruskalMazeGenerator = new KruskalMazeGenerator(new Random());
//        Maze maze = kruskalMazeGenerator.generate(10, 10);
//
//
//
//        maze.addNewSurfaces(VertexType.COIN, 10);
//        maze.addNewSurfaces(VertexType.SAND, 2);
//        Renderer visual= new ConsoleRenderer();
//        out.println(visual.render(maze));
//
//
//        Solver dSolver = new BfsSolver();
//        Coordinate startCoordinate = new Coordinate(0,0);
//        Coordinate endCoordinate = new Coordinate(9,9);
//        List<Vertex> path = dSolver.solve(maze, startCoordinate, endCoordinate);
//
//        System.out.println(visual.render(maze, path));
    }
}





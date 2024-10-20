package backend.academy;

import backend.academy.generator.Generator;
import backend.academy.generator.KruskalMazeGenerator;
import backend.academy.generator.PrimaMazeGenerator;
import backend.academy.graph.Vertex;
import backend.academy.graph.VertexType;
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
        Generator kruskalMazeGenerator = new KruskalMazeGenerator(new Random());
        Maze maze = kruskalMazeGenerator.generate(20, 20);


        maze.addNewSurfaces(VertexType.COIN, 1);
        maze.addNewSurfaces(VertexType.SAND, 2);
        Renderer visual= new ConsoleRenderer();
        System.out.println(visual.render(maze));

        Solver dSolver = new DijkstraSolver();
        Coordinate startCoordinate = new Coordinate(0,0);
        Coordinate endCoordinate = new Coordinate(19,19);
        List<Vertex> path = dSolver.solve(maze, startCoordinate, endCoordinate);

        System.out.println(visual.render(maze, path));
    }
}
//TODO:1)убрать лишние вершины в фулл графе
//TODO:2)добавить отрицательные веса и вес обычной клетки 0 (неправильно работает алгоритм дейкстры)
//TODO:2)добавить алгоритм




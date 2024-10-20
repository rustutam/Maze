package backend.academy.visualization;

import backend.academy.Coordinate;
import backend.academy.Maze;
import backend.academy.graph.Vertex;

import java.util.List;

public interface Renderer {
    String render(Maze maze);
    String render(Maze maze, List<Vertex> path);
}

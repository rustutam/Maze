package backend.academy.visualization;

import backend.academy.models.Coordinate;
import backend.academy.models.Maze;
import java.util.List;

public interface Renderer {
    String render(Maze maze);

    String render(Maze maze, List<Coordinate> path);
}

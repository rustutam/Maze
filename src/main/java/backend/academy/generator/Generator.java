package backend.academy.generator;

import backend.academy.Maze;
import backend.academy.graph.Graph;

public interface Generator {
    Graph generate(Graph graph);
}

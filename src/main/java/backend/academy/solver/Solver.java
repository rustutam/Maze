package backend.academy.solver;

import backend.academy.graph.Graph;
import backend.academy.graph.Vertex;
import java.util.List;

public interface Solver {

    List<Vertex> solve(Graph graph, Vertex start, Vertex end);

}

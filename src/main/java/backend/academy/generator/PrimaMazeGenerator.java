package backend.academy.generator;

import backend.academy.graph.Edge;
import backend.academy.graph.Graph;
import backend.academy.graph.Vertex;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class PrimaMazeGenerator implements Generator {

    private final Random random;

    public PrimaMazeGenerator(Random random) {
        this.random = random;
    }

    @Override
    public Graph generate(Graph graph) {
        List<Vertex> allVertex = graph.getVertices();
        Vertex startVertex = allVertex.get(random.nextInt(allVertex.size()));
        Set<Vertex> visitedVertex = new HashSet<>();

        List<Edge> neighboursEdges =
            new ArrayList<>(graph.getNeighbours(startVertex).stream().toList());
        visitedVertex.add(startVertex);

        Graph minGraph = new Graph();
        minGraph.addVertex(startVertex);

        while (visitedVertex.size() < allVertex.size()) {
            Edge edge = neighboursEdges.get(random.nextInt(neighboursEdges.size()));
            Vertex v1 = edge.from();
            Vertex v2 = edge.to();
            if (visitedVertex.contains(v1) && visitedVertex.contains(v2)) {
                neighboursEdges.remove(edge);
                continue;
            }

            Vertex nextVertex = visitedVertex.contains(v1) ? v2 : v1;
            minGraph.addVertex(nextVertex);
            minGraph.addEdge(v1, v2);
            neighboursEdges.remove(edge);
            neighboursEdges.addAll(graph.getNeighbours(nextVertex));

            visitedVertex.add(nextVertex);

        }

        return minGraph;
    }
}



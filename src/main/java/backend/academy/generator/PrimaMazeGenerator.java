package backend.academy.generator;

import backend.academy.graph.Edge;
import backend.academy.graph.Graph;
import backend.academy.graph.Vertex;
import com.google.common.collect.Sets;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class PrimaMazeGenerator implements Generator {

    private final SecureRandom random;

    public PrimaMazeGenerator(SecureRandom random) {
        this.random = random;
    }

    @Override
    public Graph generate(Graph graph) {
        List<Vertex> allVertex = graph.getVertices();
        Vertex startVertex = allVertex.get(random.nextInt(allVertex.size()));
        Set<Vertex> visitedVertex = Sets.newHashSetWithExpectedSize(allVertex.size());

        List<Edge> neighboursEdges = getNeighboursEdges(graph, startVertex);

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

    private List<Edge> getNeighboursEdges(Graph graph, Vertex startVertex) {
        return new ArrayList<>(graph.getNeighbours(startVertex).stream().toList());
    }
}



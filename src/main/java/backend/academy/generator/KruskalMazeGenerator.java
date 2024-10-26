package backend.academy.generator;

import backend.academy.graph.Edge;
import backend.academy.graph.Graph;
import backend.academy.graph.Vertex;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Random;

public class KruskalMazeGenerator implements Generator {
    private final Random random;

    public KruskalMazeGenerator(Random random) {
        this.random = random;
    }

    @Override
    public Graph generate(Graph graph) {
        Graph minGraph = new Graph();

        List<Edge> allEdges = graph.getEdges();

        Map<Vertex, Integer> connectiveComponent = getConnectiveComponent(graph.getVertices());
        while (!allEdges.isEmpty()) {
            Edge edge = allEdges.get(random.nextInt(allEdges.size()));
            Vertex v1 = edge.from();
            Vertex v2 = edge.to();
            if (!Objects.equals(connectiveComponent.get(v1), connectiveComponent.get(v2))) {

                mergeConnectiveComponent(connectiveComponent, v1, v2);
                minGraph.addVertex(v1);
                minGraph.addVertex(v2);
                minGraph.addEdge(v1, v2);
            }

            allEdges.remove(edge);

        }

        return minGraph;
    }

    private void mergeConnectiveComponent(
        Map<Vertex, Integer> connectiveComponent,
        Vertex v1,
        Vertex v2
    ) {
        int component1 = connectiveComponent.get(v1);
        int component2 = connectiveComponent.get(v2);
        for (Vertex vertex : connectiveComponent.keySet()) {
            if (Objects.equals(connectiveComponent.get(vertex), component2)) {
                connectiveComponent.put(vertex, component1);
            }
        }
    }

    private Map<Vertex, Integer> getConnectiveComponent(List<Vertex> allVertex) {
        int component = 0;
        Map<Vertex, Integer> connectiveComponent = new HashMap<>();
        for (Vertex vertex : allVertex) {
            connectiveComponent.put(vertex, component);
            component += 1;
        }
        return connectiveComponent;
    }
}

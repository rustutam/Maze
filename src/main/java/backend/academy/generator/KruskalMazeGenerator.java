package backend.academy.generator;

import backend.academy.Coordinate;
import backend.academy.Maze;
import backend.academy.graph.Edge;
import backend.academy.graph.FullEdgeGraph;
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
    public Maze generate(int height, int width) {
        Coordinate startCoordinate = new Coordinate(random.nextInt(height), random.nextInt(width));
        Vertex startVertex = new Vertex(startCoordinate);
        Graph fullGraph = new FullEdgeGraph(startCoordinate, height, width).getFullEdgeGraph();
        Graph graph = new Graph();

        List<Edge> allEdges = fullGraph.getEdges();
        Map<Vertex, Integer> connectiveComponent = getConnectiveComponent(fullGraph.getVertices());
        while (!allEdges.isEmpty()) {
            Edge edge = allEdges.get(random.nextInt(allEdges.size()));
            Vertex v1 = edge.from();
            Vertex v2 = edge.to();
            if (!Objects.equals(connectiveComponent.get(v1), connectiveComponent.get(v2))) {

                connectiveComponent = mergeConnectiveComponent(connectiveComponent, v1, v2);
                graph.addVertex(v1);
                graph.addVertex(v2);
                graph.addEdge(v1, v2, edge.weight());
            }

            allEdges.remove(edge);

        }

        return new Maze(height, width, graph);
    }

    private Map<Vertex, Integer> mergeConnectiveComponent(
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
        return connectiveComponent;
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

    public static void main(String[] args) {
        KruskalMazeGenerator kruskalMazeGenerator = new KruskalMazeGenerator(new Random());
        Maze maze = kruskalMazeGenerator.generate(5, 5);
        maze.graph().printGraph();
    }
}

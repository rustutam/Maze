package backend.academy.generator;

import backend.academy.Coordinate;
import backend.academy.Maze;
import backend.academy.graph.Edge;
import backend.academy.graph.FullEdgeGraph;
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
    public Maze generate(int height, int width) {
        Coordinate startCoordinate = new Coordinate(random.nextInt(height), random.nextInt(width));
        Vertex startVertex = new Vertex(startCoordinate);
        Graph fullGraph = new FullEdgeGraph(startCoordinate, height, width).getFullEdgeGraph();
        List<Vertex> allVertex = fullGraph.getVertices();
        Set<Vertex> visitedVertex = new HashSet<>();

        List<Edge> neighboursEdges =
            new ArrayList<>(fullGraph.getNeighbours(startVertex).stream().toList());
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
            minGraph.addEdge(v1, v2, edge.weight());
            neighboursEdges.remove(edge);
            neighboursEdges.addAll(fullGraph.getNeighbours(nextVertex));

            visitedVertex.add(nextVertex);

        }

        return new Maze(height, width, minGraph);
    }

    public static void main(String[] args) {
        PrimaMazeGenerator primaMazeGenerator = new PrimaMazeGenerator(new Random());
        Maze maze = primaMazeGenerator.generate(5, 5);
        maze.graph().printGraph();
    }
}



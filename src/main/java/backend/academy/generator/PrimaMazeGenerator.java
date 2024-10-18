package backend.academy.generator;

import backend.academy.Coordinate;
import backend.academy.Direction;
import backend.academy.Maze;
import backend.academy.graph.Edge;
import backend.academy.graph.Graph;
import backend.academy.graph.Vertex;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;

public class PrimaMazeGenerator implements Generator {
    private final Random random;

    public PrimaMazeGenerator(Random random) {
        this.random = random;
    }

    @Override
    public Maze generate(int height, int width) {
        Coordinate startCoordinate = new Coordinate(random.nextInt(height), random.nextInt(width));
        Vertex startVertex = new Vertex(startCoordinate);
        Graph graph = getFullEdgeGraph(startCoordinate, height, width);
        Set<Vertex> visitedVertex = new HashSet<>();
        Set<Vertex> unVisitedVertex = new HashSet<>(graph.getAdjacencyList().keySet());
        List<Edge> neighboursEdges =
            new ArrayList<>(graph.getNeighbours(startVertex).stream().toList());
        visitedVertex.add(startVertex);
        unVisitedVertex.remove(startVertex);
        Graph minGraph = new Graph();
        minGraph.addVertex(startVertex);
        while (!unVisitedVertex.isEmpty()) {
            Edge edge = neighboursEdges.get(random.nextInt(neighboursEdges.size()));
            Vertex nextVertex = edge.to();
            visitedVertex.add(nextVertex);
            unVisitedVertex.remove(nextVertex);

            neighboursEdges.remove(edge);
            neighboursEdges.addAll(
                graph.getNeighbours(nextVertex));

            neighboursEdges = neighboursEdges.stream().filter(e -> !visitedVertex.contains(e.to()))
                .collect(Collectors.toList());

            minGraph.addVertex(nextVertex);
            minGraph.addEdge(edge.from(), edge.to(), edge.weight());
            minGraph.addEdge(edge.to(), edge.from(), edge.weight());

        }

        return new Maze(height, width, minGraph);
    }

    private Graph getFullEdgeGraph(Coordinate startCoordinate, int height, int width) {
        int x = adjustCoordinate(startCoordinate.col());
        int y = adjustCoordinate(startCoordinate.row());
        Graph fullGraph = new Graph();
        addVertices(fullGraph, x, y, height, width);
        addEdges(fullGraph, x, y, height, width);
        return fullGraph;
    }

    private int adjustCoordinate(int coordinate) {
        while (coordinate >= 2) {
            coordinate -= 2;
        }
        return coordinate;
    }

    private void addVertices(Graph graph, int x, int y, int height, int width) {
        for (int i = y; i < height; i += 2) {
            for (int j = x; j < width; j += 2) {
                Vertex vertex = new Vertex(new Coordinate(i, j));
                graph.addVertex(vertex);
            }
        }
    }

    private void addEdges(Graph graph, int x, int y, int height, int width) {
        List<Vertex> vertices = graph.getAdjacencyList().keySet().stream().toList();
        for (Vertex vertex : vertices) {
            Coordinate coordinate = vertex.getCoordinate();
            List<Coordinate> neighbours = getNeighbours(coordinate, height, width);
            for (Coordinate neighbour : neighbours) {
                vertices.stream()
                    .filter(v -> v.getCoordinate().equals(neighbour))
                    .findFirst()
                    .ifPresent(neighbourVertex -> graph.addEdge(vertex, neighbourVertex, 2));
            }
        }
    }

    private Coordinate move(Coordinate coordinate, Direction direction) {
        int row = coordinate.row();
        int col = coordinate.col();

        return switch (direction) {
            case UP -> new Coordinate(row - 2, col);
            case DOWN -> new Coordinate(row + 2, col);
            case LEFT -> new Coordinate(row, col - 2);
            case RIGHT -> new Coordinate(row, col + 2);
        };
    }

    private List<Coordinate> getNeighbours(Coordinate coordinate, int height, int width) {
        List<Coordinate> neighbours = new ArrayList<>();
        for (Direction direction : Direction.values()) {
            Coordinate neighbour = move(coordinate, direction);
            if (neighbour.row() >= 0 && neighbour.row() < height && neighbour.col() >= 0 && neighbour.col() < width) {
                neighbours.add(neighbour);
            }
        }
        return neighbours;
    }

    public static void main(String[] args) {
        PrimaMazeGenerator primaMazeGenerator = new PrimaMazeGenerator(new Random());
        Maze maze = primaMazeGenerator.generate(5, 5);
        maze.graph().printGraph();
        maze.printMaze();
    }
}



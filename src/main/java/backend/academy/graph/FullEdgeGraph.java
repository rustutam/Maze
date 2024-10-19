package backend.academy.graph;

import backend.academy.Coordinate;
import backend.academy.Direction;
import java.util.ArrayList;
import java.util.List;

public class FullEdgeGraph {
    Coordinate startCoordinate;
    int height;
    int width;

    public FullEdgeGraph(Coordinate startCoordinate, int height, int width) {
        this.startCoordinate = startCoordinate;
        this.height = height;
        this.width = width;

    }
    public Graph getFullEdgeGraph() {
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
}

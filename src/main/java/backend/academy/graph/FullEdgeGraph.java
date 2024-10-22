package backend.academy.graph;

import backend.academy.Coordinate;
import backend.academy.Direction;
import backend.academy.Maze;
import backend.academy.visualization.ConsoleRenderer;
import backend.academy.visualization.Renderer;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

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
        fullGraph = deleteUnnecessaryVertices(fullGraph);
//        return deleteUnnecessaryVertices(fullGraph);
        return fullGraph;
    }

    private Graph deleteUnnecessaryVertices(Graph graph) {
        List<Vertex> vertices = graph.getVertices();
        vertices = vertices.stream()
            .filter(v -> !isWithinBounds(v)).toList();

        for (Vertex vertex : vertices) {
             Edge neighboursEdge =  graph.getNeighbours(vertex).stream().findFirst().orElse(null);
             if (neighboursEdge != null) {
                 Vertex middleVertex = neighboursEdge.getMiddleVertex();
                 Vertex neighbour = neighboursEdge.getSecondVertex(vertex);
                 if (isWithinBounds(middleVertex)) {
                     graph.addVertex(middleVertex);
                     graph.addEdge(neighbour, middleVertex, 2);

                 }
                 graph.deleteVertex(vertex);
             }
        }


        return graph;
    }
    private boolean isWithinBounds(Vertex vertex) {
        Coordinate coordinate = vertex.coordinate();
        return coordinate.row() >= 0 && coordinate.row() < height &&
            coordinate.col() >= 0 && coordinate.col() < width;
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

    //TODO: Нужно удалить ненужные ячейки.Пока хз как делать
    private void addEdges(Graph graph, int x, int y, int height, int width) {
        List<Vertex> vertices = graph.getVertices();
        for (Vertex vertex : vertices) {
            List<Vertex> neighbours = getNeighbours(vertex, height, width);
            for (Vertex neighbour : neighbours) {
                if (!vertices.contains(neighbour)) {
                    graph.addVertex(neighbour);
                }

                graph.addEdge(vertex, neighbour, 2);
//                vertices.stream()
//                    .filter(v -> v.getCoordinate().equals(neighbour))
//                    .findFirst()
//                    .ifPresent(neighbourVertex -> graph.addEdge(vertex, neighbourVertex, 2));
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

    private List<Vertex> getNeighbours(Vertex vertex, int height, int width) {
        List<Vertex> neighbours = new ArrayList<>();
        for (Direction direction : Direction.values()) {
            Coordinate coordinate = vertex.coordinate();
            Coordinate neighbour = move(coordinate, direction);
            neighbours.add(new Vertex(neighbour));
        }
        return neighbours;
    }

    public static void main(String[] args) {
//        FullEdgeGraph fullEdgeGraph = new FullEdgeGraph(new Coordinate(1, 1), 6, 6);
//        Graph graph = fullEdgeGraph.getFullEdgeGraph();
//        Set<Edge> f = graph.adjacencyList.values().stream().flatMap(Set::stream).collect(Collectors.toSet());
//        System.out.println(f.size());
//        graph.printGraph();
//        Maze maze = new Maze(5, 5, new FullEdgeGraph(new Coordinate(0, 1), 6, 5).getFullEdgeGraph());
//        Renderer visual= new ConsoleRenderer();
//        System.out.println(visual.render(maze));

    }

}

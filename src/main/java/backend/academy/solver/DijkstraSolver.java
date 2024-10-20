package backend.academy.solver;

import backend.academy.Coordinate;
import backend.academy.Maze;
import backend.academy.graph.Edge;
import backend.academy.graph.Graph;
import backend.academy.graph.Vertex;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class DijkstraSolver implements Solver {
    @Override
    public List<Vertex> solve(Maze maze, Coordinate start, Coordinate end) {
        Graph graph = maze.graph();
        Vertex startVertex = new Vertex(start);
        Vertex endVertex = new Vertex(end);

        List<Vertex> allVertex = graph.getVertices();

        Set<Vertex> visitedVertex = new HashSet<>();

        Map<Vertex, Integer> distance = new HashMap<>();
        Map<Vertex, Vertex> predecessors = new HashMap<>();

        if (!allVertex.contains(startVertex)){
            throw new IllegalArgumentException("Start vertex not in graph");
        }
        for (Vertex vertex : allVertex) {
            distance.put(vertex, Integer.MAX_VALUE);
        }
        distance.put(startVertex, 0);

        while (visitedVertex.size() != allVertex.size()) {
            Vertex currentVertex = getMinDistanceVertex(distance, visitedVertex);
            visitedVertex.add(currentVertex);
            Set<Edge> neighbours = graph.getNeighbours(currentVertex).stream()
                .filter(edge -> !visitedVertex.contains(edge.getSecondVertex(currentVertex)))
                .collect(Collectors.toSet());
            for (Edge edge : neighbours) {
                Vertex neighbour = edge.getSecondVertex(currentVertex);
                int newDistance = distance.get(currentVertex) + neighbour.weight();
                if (newDistance < distance.get(neighbour)) {
                    distance.put(neighbour, newDistance);
                    predecessors.put(neighbour, currentVertex);
                }
            }
        }
        List<Vertex> path = new ArrayList<>();
        for (Vertex at = endVertex; at != null; at = predecessors.get(at)) {
            path.add(at);
        }
        Collections.reverse(path);

//        Graph solveGraph = new Graph();
//        for (Vertex vertex: path)
//            solveGraph.addVertex(vertex);
//
//        for (int i = 0; i < path.size() - 1; i++) {
//            Vertex from = path.get(i);
//            Vertex to = path.get(i + 1);
//            int weight = graph.getNeighbours(from).stream()
//                .filter(edge -> edge.getSecondVertex(from).equals(to))
//                .findFirst()
//                .orElseThrow(() -> new IllegalArgumentException("No edge found between vertices"))
//                .weight();
//            solveGraph.addEdge(from, to, weight);
//        }

        return path;

    }

    Vertex getMinDistanceVertex(Map<Vertex, Integer> distance, Set<Vertex> visitedVertex) {
        Vertex minVertex = null;
        int minDistance = Integer.MAX_VALUE;
        for (Map.Entry<Vertex, Integer> entry : distance.entrySet()) {
            if (!visitedVertex.contains(entry.getKey()) && entry.getValue() < minDistance) {
                minDistance = entry.getValue();
                minVertex = entry.getKey();
            }
        }
        if (minVertex == null) {
            throw new IllegalArgumentException("No vertex found");
        }
        return minVertex;
    }
}

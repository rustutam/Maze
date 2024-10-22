package backend.academy.solver;

import backend.academy.Coordinate;
import backend.academy.Maze;
import backend.academy.graph.Graph;
import backend.academy.graph.Vertex;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

public class BfsSolver implements Solver {
    @Override
    public List<Vertex> solve(Maze maze, Coordinate start, Coordinate end) {
        Queue<Vertex> queue = new LinkedList<>();

        Graph graph = maze.graph();
        Vertex startVertex = new Vertex(start);
        Vertex endVertex = new Vertex(end);
        Map<Vertex, Vertex> predecessors = new HashMap<>();
        HashSet<Vertex> visited = new HashSet<>();
        queue.add(startVertex);

        while (!queue.isEmpty()) {
            Vertex currentVertex = queue.poll();
            visited.add(currentVertex);
            if (currentVertex.equals(endVertex)) {
                return reconstructPath(predecessors, endVertex);
            }
            for (Vertex neighbor : graph.getNeighbours(currentVertex).stream()
                .map(edge -> edge.getSecondVertex(currentVertex))
                .filter(neighbor -> !visited.contains(neighbor))
                .toList()) {
                queue.add(neighbor);
                visited.add(neighbor);
                predecessors.put(neighbor, currentVertex);
            }
        }

        return new ArrayList<>();
    }

    private List<Vertex> reconstructPath(Map<Vertex, Vertex> predecessors, Vertex endVertex) {
        List<Vertex> path = new ArrayList<>();
        for (Vertex at = endVertex; at != null; at = predecessors.get(at)) {
            path.add(at);
        }
        Collections.reverse(path);
        return path;
    }
}

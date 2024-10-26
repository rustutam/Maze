package backend.academy.solver;

import backend.academy.graph.Edge;
import backend.academy.graph.Graph;
import backend.academy.graph.Vertex;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class DijkstraSolver implements Solver {
    @Override
    public List<Vertex> solve(Graph graph, Vertex startVertex, Vertex endVertex) {
        List<Vertex> allVertex = graph.getVertices();

        Set<Vertex> visitedVertex = Sets.newHashSetWithExpectedSize(allVertex.size());

        Map<Vertex, Integer> distance = Maps.newHashMapWithExpectedSize(allVertex.size());
        Map<Vertex, Vertex> predecessors = Maps.newHashMapWithExpectedSize(allVertex.size());

        if (!allVertex.contains(startVertex)) {
            throw new IllegalArgumentException("Start vertex not in graph");
        }
        for (Vertex vertex : allVertex) {
            distance.put(vertex, Integer.MIN_VALUE);
        }
        distance.put(startVertex, 0);

        while (visitedVertex.size() != allVertex.size()) {
            Vertex currentVertex = getMaxDistanceVertex(distance, visitedVertex);
            visitedVertex.add(currentVertex);
            Set<Edge> neighbours = graph.getNeighbours(currentVertex).stream()
                .filter(edge -> !visitedVertex.contains(edge.getSecondVertex(currentVertex)))
                .collect(Collectors.toSet());
            for (Edge edge : neighbours) {
                Vertex neighbour = edge.getSecondVertex(currentVertex);
                int newDistance = distance.get(currentVertex) + neighbour.weight();
                if (newDistance > distance.get(neighbour)) {
                    distance.put(neighbour, newDistance);
                    predecessors.put(neighbour, currentVertex);
                }
            }
        }
        return reconstructPath(predecessors, endVertex);

    }

    private List<Vertex> reconstructPath(Map<Vertex, Vertex> predecessors, Vertex endVertex) {
        List<Vertex> path = new ArrayList<>();
        for (Vertex at = endVertex; at != null; at = predecessors.get(at)) {
            path.add(at);
        }
        Collections.reverse(path);
        if (path.size() == 1 && !path.getFirst().equals(endVertex)) {
            return Collections.emptyList();
        }
        return path;
    }

    Vertex getMaxDistanceVertex(Map<Vertex, Integer> distance, Set<Vertex> visitedVertex) {
        Vertex maxVertex = null;
        int minDistance = Integer.MIN_VALUE;
        for (Map.Entry<Vertex, Integer> entry : distance.entrySet()) {
            if (!visitedVertex.contains(entry.getKey()) && entry.getValue() > minDistance) {
                minDistance = entry.getValue();
                maxVertex = entry.getKey();
            }
        }
        if (maxVertex == null) {
            throw new IllegalArgumentException("No vertex found");
        }
        return maxVertex;
    }

}

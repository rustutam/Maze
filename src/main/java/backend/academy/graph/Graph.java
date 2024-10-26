package backend.academy.graph;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.Getter;

@Getter
public class Graph {
    Map<Vertex, HashSet<Edge>> adjacencyList;

    public Graph() {
        this.adjacencyList = new HashMap<>();
    }

    public List<Edge> getEdges() {
        return adjacencyList.values().stream()
            .flatMap(HashSet::stream)
            .distinct()
            .collect(Collectors.toList());
    }

    public List<Vertex> getVertices() {
        return adjacencyList.keySet().stream()
            .distinct()
            .collect(Collectors.toList());
    }

    public void addVertex(Vertex vertex) {
        adjacencyList.putIfAbsent(vertex, new HashSet<>());
    }

    public void addEdge(Vertex from, Vertex to) {
        Edge edge = new Edge(from, to);
        adjacencyList.get(from).add(edge);
        adjacencyList.get(to).add(edge);

    }

    public HashSet<Edge> getNeighbours(Vertex vertex) {
        return adjacencyList.get(vertex);
    }

}

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

    // Получить все рёбра графа

    public List<Edge> getEdges() {
        return adjacencyList.values().stream()
            .flatMap(HashSet::stream)
            .distinct()
            .collect(Collectors.toList());
    }

    // Получить все вершины графа

    public List<Vertex> getVertices() {
        return adjacencyList.keySet().stream()
            .distinct()
            .collect(Collectors.toList());
    }

    // Добавить вершину в граф

    public void addVertex(Vertex vertex) {
        adjacencyList.putIfAbsent(vertex, new HashSet<>());
    }

    // Добавить ребро в граф

    public void addEdge(Vertex from, Vertex to) {
        Edge edge = new Edge(from, to);
        adjacencyList.get(from).add(edge);
        adjacencyList.get(to).add(edge);

    }

    // Получить смежные вершины для заданной вершины

    public HashSet<Edge> getNeighbours(Vertex vertex) {
        return adjacencyList.get(vertex);
    }

}

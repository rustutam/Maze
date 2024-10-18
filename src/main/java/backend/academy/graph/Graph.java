package backend.academy.graph;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import backend.academy.Coordinate;
import lombok.Getter;

@Getter class Graph {
    Map<Vertex, HashSet<Edge>> adjacencyList;

    public Graph() {
        this.adjacencyList = new HashMap<>();
    }

    public void addVertex(Vertex vertex) {
        adjacencyList.putIfAbsent(vertex, new HashSet<>());
    }

    public void addEdge(Vertex from, Vertex to, int weight) {
        Edge edge = new Edge(from, to, weight);
        adjacencyList.get(from).add(edge);
        adjacencyList.get(to).add(edge);
    }

    public HashSet<Edge> getNeighbours(Vertex vertex) {
        return adjacencyList.get(vertex);
    }

    public void printGraph() {
        for (Map.Entry<Vertex, HashSet<Edge>> entry : adjacencyList.entrySet()) {
            Vertex vertex = entry.getKey();
            HashSet<Edge> edges = entry.getValue();
            System.out.print("Vertex " + vertex + " is connected to: ");
            for (Edge edge : edges) {
                System.out.print(edge.to() + " (weight: " + edge.weight() + "), ");
            }
            System.out.println();
        }
    }

    public static void main(String[] args) {
        Graph graph = new Graph();
        Vertex a    = new Vertex(new Coordinate(0, 0));
        Vertex b = new Vertex(new Coordinate(0, 1));
        Vertex c = new Vertex(new Coordinate(1, 0));
        Vertex d = new Vertex(new Coordinate(1, 1));
        graph.addVertex(a);
        graph.addVertex(b);
        graph.addVertex(c);
        graph.addVertex(d);
        graph.addEdge(a, b, 1);
        graph.addEdge(a, c, 2);
        graph.addEdge(b, d, 3);
        graph.addEdge(c, d, 4);
        graph.printGraph();
    }

}
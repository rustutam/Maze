package backend.academy.graph;

import backend.academy.Coordinate;
import lombok.Getter;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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

    public List<Vertex> getVertices(){
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

    public void deleteVertex(Vertex vertex) {
        adjacencyList.remove(vertex);
        for (Map.Entry<Vertex, HashSet<Edge>> entry : adjacencyList.entrySet()) {
            HashSet<Edge> edges = entry.getValue();
            edges.removeIf(edge -> edge.from().equals(vertex) || edge.to().equals(vertex));
        }
    }



    public Vertex getSecondVertex(Edge edge, Vertex vertex) throws Exception {
        if (edge.from().equals(vertex)) {
            return edge.to();
        }
        else if (edge.to().equals(vertex)){
            return edge.from();
        }
        else {
            throw new Exception();
        }
    }

//    public Graph addIntermediateVertices() {
//        Graph newGraph = new Graph();
//
//        for (Vertex vertex : this.getVertices()) {
//            newGraph.addVertex(vertex);
//        }
//
//        for (Edge edge : this.getEdges()) {
//            Vertex v1 = edge.from();
//            Vertex v2 = edge.to();
//            Coordinate midCoordinate = new Coordinate(
//                (v1.coordinate().row() + v2.coordinate().row()) / 2,
//                (v1.coordinate().col() + v2.coordinate().col()) / 2
//            );
//            Vertex midVertex = new Vertex(midCoordinate);
//            newGraph.addVertex(midVertex);
//            newGraph.addEdge(v1, midVertex, edge.weight());
//            newGraph.addEdge(midVertex, v2, edge.weight());
//        }
//
//        return newGraph;
//    }

    public static void main(String[] args) {
//        Graph graph = new Graph();
//        Vertex a = new Vertex(new Coordinate(0, 0));
//        Vertex b = new Vertex(new Coordinate(0, 1));
//        Vertex c = new Vertex(new Coordinate(1, 0));
//        Vertex d = new Vertex(new Coordinate(1, 1));
//        graph.addVertex(a);
//        graph.addVertex(b);
//        graph.addVertex(c);
//        graph.addVertex(d);
//        graph.addEdge(a, b, 1);
//        graph.addEdge(a, c, 2);
//        graph.addEdge(b, d, 3);
//        graph.addEdge(c, d, 4);
//        graph.printGraph();
    }

}

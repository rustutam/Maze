package backend.academy;
import lombok.Getter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

public class GraphTestNotUsing {
    @Getter private final Map<Coordinate, HashSet<Coordinate>> adjacencyList;

    public GraphTestNotUsing() {
        this.adjacencyList = new HashMap<>();
    }

    public void addVertex(Coordinate vertex) {
        adjacencyList.putIfAbsent(vertex, new HashSet<>());
    }

    public void addEdge(Coordinate from, Coordinate to) {
        adjacencyList.putIfAbsent(from, new HashSet<>());
        adjacencyList.putIfAbsent(to, new HashSet<>());
        adjacencyList.get(from).add(to);
        adjacencyList.get(to).add(from); // Удалите эту строку для ориентированного графа
    }

    public HashSet<Coordinate> getNeighbours(Coordinate vertex) {
        return adjacencyList.get(vertex);
    }

    public int[][] toAdjacencyMatrix() {
        int size = adjacencyList.size();
        int[][] matrix = new int[size][size];
        List<Coordinate> vertices = new ArrayList<>(adjacencyList.keySet());

        for (int i = 0; i < size; i++) {
            Coordinate vertex = vertices.get(i);
            for (Coordinate neighbour : adjacencyList.get(vertex)) {
                int j = vertices.indexOf(neighbour);
                matrix[i][j] = 1; // 1 указывает на наличие ребра
            }
        }

        return matrix;
    }

    @Override public String toString() {
        return "GraphTestNotUsing{" +
            "adjacencyList=" + adjacencyList +
            '}';
    }
    public void printGraph(){
        for (Map.Entry<Coordinate, HashSet<Coordinate>> entry : adjacencyList.entrySet()) {
            System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue());
        }
    }

    public static void main(String[] args) {
        GraphTestNotUsing graphTestNotUsing = new GraphTestNotUsing();
        Coordinate a = new Coordinate(0, 0);
        Coordinate b = new Coordinate(0, 1);
        Coordinate c = new Coordinate(1, 0);

        graphTestNotUsing.addVertex(a);
        graphTestNotUsing.addVertex(b);
        graphTestNotUsing.addVertex(c);

        graphTestNotUsing.addEdge(a, b);
        graphTestNotUsing.addEdge(a, c);

        System.out.println(graphTestNotUsing);
    }


}


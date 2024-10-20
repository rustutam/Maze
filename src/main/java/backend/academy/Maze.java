package backend.academy;

import backend.academy.graph.Edge;
import backend.academy.graph.Graph;
import backend.academy.graph.Vertex;
import backend.academy.graph.VertexType;
import lombok.Getter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

public final class Maze {
    @Getter
    private final int height;
    @Getter
    private final int width;
    @Getter
    private final Graph graph;

    private final Random random;

    public Maze(int height, int width, Graph graph, Random random) {
        this.height = height;
        this.width = width;
        this.graph = graph;
        this.random = random;

    }

    public void addNewSurfaces(VertexType type, int coinCount) {
        List<Vertex> vertices = graph.getVertices();
        int vertexCount = vertices.size();
        if (coinCount> vertexCount){
            coinCount = vertexCount;
        }

        while (coinCount > 0) {
            int randomIndex = random.nextInt(vertexCount);
            Vertex randomVertex = vertices.get(randomIndex);
            if (randomVertex.type() != VertexType.NORMAL) {
                continue;
            }
            randomVertex.type(type);
            coinCount --;
        }
    }
    public void printMaze() {
        char[][] grid = new char[height][width];

        // Initialize the grid with spaces
        for (int i = 0; i < grid.length; i++) {
            Arrays.fill(grid[i], '#');
        }

        // Mark vertices with '0'
        for (Vertex vertex : graph.getAdjacencyList().keySet()) {
            int y = vertex.coordinate().row();
            int x = vertex.coordinate().col();
            grid[x][y] = '0';
        }

        // Mark edges with '-'
        for (Map.Entry<Vertex, HashSet<Edge>> entry : graph.getAdjacencyList().entrySet()) {
            Vertex from = entry.getKey();
            int fromX = from.coordinate().row();
            int fromY = from.coordinate().col();
            for (Edge edge : entry.getValue()) {
                Vertex to = edge.to();
                int toX = to.coordinate().row();
                int toY = to.coordinate().col();
                if (fromX == toX) {
                    grid[fromX][(fromY + toY) / 2] = '-';
                } else if (fromY == toY) {
                    grid[(fromX + toX) / 2][fromY] = '-';
                }
            }
        }

        // Print the grid
        for (char[] row : grid) {
            for (char cell : row) {
                System.out.print(cell);
            }
            System.out.println();
        }
    }
//    public void updateCell(Coordinate coordinate, Cell.Type type){
//        int x = coordinate.row();
//        int y = coordinate.col();
//        grid[x][y] = new Cell(x, y, type);
//    }

}



package backend.academy;

import backend.academy.graph.Edge;
import backend.academy.graph.Graph;
import backend.academy.graph.Vertex;
import lombok.Getter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Getter public final class Maze {
    private final int height;
    private final int width;
    private final Graph graph;

    public Maze(int height, int width, Graph graph) {
        this.height = height;
        this.width = width;
        this.graph = graph;

    }
    public void printMaze() {
        char[][] grid = new char[height][width];

        // Initialize the grid with spaces
        for (int i = 0; i < grid.length; i++) {
            Arrays.fill(grid[i], '#');
        }

        // Mark vertices with '0'
        for (Vertex vertex : graph.getAdjacencyList().keySet()) {
            int y = vertex.getCoordinate().row();
            int x = vertex.getCoordinate().col();
            grid[x][y] = '0';
        }

        // Mark edges with '-'
        for (Map.Entry<Vertex, HashSet<Edge>> entry : graph.getAdjacencyList().entrySet()) {
            Vertex from = entry.getKey();
            int fromX = from.getCoordinate().row();
            int fromY = from.getCoordinate().col();
            for (Edge edge : entry.getValue()) {
                Vertex to = edge.to();
                int toX = to.getCoordinate().row();
                int toY = to.getCoordinate().col();
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



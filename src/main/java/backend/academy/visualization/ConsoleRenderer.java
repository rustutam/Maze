package backend.academy.visualization;

import backend.academy.Coordinate;
import backend.academy.Maze;
import backend.academy.graph.Graph;
import backend.academy.graph.Vertex;
import java.util.List;

public class ConsoleRenderer implements Renderer{
    @Override
    public String render(Maze maze) {
        int height = maze.height();
        int width = maze.width();
        Graph graph = maze.graph();
        StringBuilder sb = new StringBuilder();

        String wall = "█";
        String passage = " ";

        // Верхняя рамка
        sb.append(wall.repeat(width + 2)).append("\n");

        for (int row = 0;row <height;row++){
            sb.append(wall);
            for (int col = 0;col <width;col++){
                Coordinate coordinate = new Coordinate(row,col);
                if (graph.getVertices().contains(new Vertex(coordinate))) {
                    sb.append(passage);
                }
                else {
                    sb.append(wall);
                }
            }
            sb.append(wall); // Правая рамка
            sb.append("\n"); // Правая рамка
        }
        // Нижняя рамка
        sb.append(wall.repeat(width + 2)).append("\n");
        return sb.toString();
    }

    @Override
    public String render(Maze maze, List<Vertex> path) {
        int height = maze.height();
        int width = maze.width();
        Graph graph = maze.graph();
        StringBuilder sb = new StringBuilder();
        String wall = "█";
        String passage = " ";
        String findPath = "∙";
        // Верхняя рамка
        sb.append(wall.repeat(width + 2)).append("\n");

        for (int row = 0; row < height; row++) {
            sb.append(wall); // Левая рамка
            for (int col = 0; col < width; col++) {
                Coordinate coordinate = new Coordinate(row, col);
                Vertex vertex = new Vertex(coordinate);
                if (path.contains(vertex)) {
                    sb.append(findPath); // Путь
                } else if (graph.getVertices().contains(new Vertex(coordinate))) {
                    sb.append(passage); // Проход
                } else {
                    sb.append(wall); // Стена
                }
            }
            sb.append(wall); // Правая рамка
            sb.append("\n"); // Правая рамка
        }

        // Нижняя рамка
        sb.append(wall.repeat(width + 2)).append("\n");

        return sb.toString();
    }
}

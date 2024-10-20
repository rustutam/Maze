package backend.academy.visualization;

import backend.academy.Coordinate;
import backend.academy.Maze;
import backend.academy.graph.Graph;
import backend.academy.graph.Vertex;
import java.util.List;

public class ConsoleRenderer implements Renderer{
    private final String WALL  = "⬜️";
    private final String SPACE = "⬛️";
    private final String START = "🟩";
    private final String END   = "🟥";
    private final String PATH  = "🟨";
    private final String COIN  = "🪙";
    private final String SAND  = "🏖️";

    @Override
    public String render(Maze maze) {
        int height = maze.height();
        int width = maze.width();
        Graph graph = maze.graph();
        StringBuilder sb = new StringBuilder();

        // Верхняя рамка
        sb.append(WALL.repeat(width + 2)).append("\n");

        for (int row = 0;row <height;row++){
            sb.append(WALL);
            for (int col = 0;col <width;col++){
                Coordinate coordinate = new Coordinate(row,col);
                Vertex vertex = graph.getVertices().stream()
                    .filter(v -> v.coordinate().equals(coordinate))
                    .findFirst()
                    .orElse(null);
                if (vertex != null) {
                    switch (vertex.type()) {
                        case COIN:
                            sb.append(COIN);
                            break;
                        case SAND:
                            sb.append(SAND);
                            break;
                        case NORMAL:
                            sb.append(SPACE);
                            break;
                    }
                } else {
                    sb.append(WALL);
                }
            }
            sb.append(WALL); // Правая рамка
            sb.append("\n"); // Правая рамка
        }
        // Нижняя рамка
        sb.append(WALL.repeat(width + 2)).append("\n");
        return sb.toString();
    }

    @Override
    public String render(Maze maze, List<Vertex> path) {
        int height = maze.height();
        int width = maze.width();
        Graph graph = maze.graph();
        StringBuilder sb = new StringBuilder();

        // Верхняя рамка
        sb.append(WALL.repeat(width + 2)).append("\n");

        for (int row = 0; row < height; row++) {
            sb.append(WALL); // Левая рамка
            for (int col = 0; col < width; col++) {
                Coordinate coordinate = new Coordinate(row, col);
                Vertex vertex = graph.getVertices().stream()
                    .filter(v -> v.coordinate().equals(coordinate))
                    .findFirst()
                    .orElse(null);
                if (vertex != null) {
                    switch (vertex.type()) {
                        case COIN:
                            sb.append(COIN); // Монета
                            break;
                        case SAND:
                            sb.append(SAND); // Песок
                            break;
                        case NORMAL:
                            if (path.contains(vertex)) {
                                sb.append(PATH); // Путь
                            } else {
                                sb.append(SPACE); // Проход
                            }
                            break;
                    }
                } else {
                    sb.append(WALL); // Стена
                }
            }
            sb.append(WALL); // Правая рамка
            sb.append("\n"); // Правая рамка
        }

        // Нижняя рамка
        sb.append(WALL.repeat(width + 2)).append("\n");

        return sb.toString();
    }
}

package backend.academy.visualization;

import backend.academy.cell.Cell;
import backend.academy.cell.Passage;
import backend.academy.models.Coordinate;
import backend.academy.models.Maze;
import java.util.List;

public class ConsoleRenderer implements Renderer {
    private static final String WALL = "⬜️";
    private static final String SPACE = "⬛️";
    private static final String START = "🟩";
    private static final String END = "🟥";
    private static final String PATH = "🟨";
    private static final String COIN = "🪙";
    private static final String SAND = "🏖️";
    private static final String UNEXPECTED_VALUE_MESSAGE = "Unexpected value: ";

    @Override
    public String render(Maze maze) {
        return render(maze, List.of());
    }

    @Override
    public String render(Maze maze, List<Coordinate> path) {
        int height = maze.height();
        int width = maze.width();

        Cell[][] mazeList = maze.mazeListModel().mazeList();
        StringBuilder sb = new StringBuilder();

        // Верхняя рамка
        sb.append(WALL.repeat(width + 2)).append("\n");

        for (int row = 0; row < height; row++) {
            sb.append(WALL);
            for (int col = 0; col < width; col++) {
                Cell cell = mazeList[row][col];
                Coordinate coordinate = new Coordinate(row, col);
                if (path.contains(coordinate)) {

                    if (coordinate.equals(path.getFirst())) {
                        sb.append(START);
                        continue;
                    } else if (coordinate.equals(path.getLast())) {
                        sb.append(END);
                        continue;
                    } else {
                        sb.append(PATH);
                        continue;
                    }
                }
                switch (cell.type()) {
                    case WALL:
                        sb.append(WALL);
                        break;
                    case PASSAGE:
                        Passage passage = (Passage) cell;
                        switch (passage.passageType()) {
                            case NORMAL:
                                sb.append(SPACE);
                                break;
                            case SAND:
                                sb.append(SAND);
                                break;
                            case COIN:
                                sb.append(COIN);
                                break;
                            default:
                                throw new IllegalStateException(UNEXPECTED_VALUE_MESSAGE + passage.passageType());
                        }
                        break;
                    default:
                        throw new IllegalStateException(UNEXPECTED_VALUE_MESSAGE + cell.type());
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

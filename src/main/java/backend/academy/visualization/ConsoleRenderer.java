package backend.academy.visualization;

import backend.academy.cell.Cell;
import backend.academy.cell.Passage;
import backend.academy.models.Coordinate;
import backend.academy.models.Maze;
import java.util.List;

public class ConsoleRenderer implements Renderer {
    // Символы для визуализации различных элементов лабиринта
    private static final String WALL = "⬜️";
    private static final String SPACE = "⬛️";
    private static final String START = "🟩";
    private static final String END = "🟥";
    private static final String PATH = "🟨";
    private static final String COIN = "🪙";
    private static final String SAND = "🏖️";
    private static final String UNEXPECTED_VALUE_MESSAGE = "Unexpected value: ";

    /**
     * Рендерит лабиринт без пути.
     *
     * @param maze лабиринт для рендеринга
     * @return строковое представление лабиринта
     */
    @Override
    public String render(Maze maze) {
        return render(maze, List.of());
    }

    /**
     * Рендерит лабиринт с указанным путём.
     *
     * @param maze лабиринт для рендеринга
     * @param path путь, который нужно отобразить
     * @return строковое представление лабиринта с путём
     */
    @Override
    public String render(Maze maze, List<Coordinate> path) {
        int height = maze.height();
        int width = maze.width();

        Cell[][] mazeList = maze.mazeListModel().mazeList();
        StringBuilder sb = new StringBuilder();

        // Верхняя рамка
        sb.append(WALL.repeat(width + 2)).append('\n');

        for (int row = 0; row < height; row++) {
            sb.append(WALL); // Левая рамка
            for (int col = 0; col < width; col++) {
                Cell cell = mazeList[row][col];
                Coordinate coordinate = new Coordinate(row, col);
                if (path.contains(coordinate)) {
                    // Отображаем стартовую, конечную и промежуточные точки пути
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
                // Отображаем тип ячейки
                switch (cell.type()) {
                    case WALL:
                        sb.append(WALL);
                        break;
                    case PASSAGE:
                        Passage passage = (Passage) cell;
                        // Отображаем проходы
                        switch (passage.passageType()) {
                            case SAND:
                                sb.append(SAND);
                                break;
                            case COIN:
                                sb.append(COIN);
                                break;
                            case NORMAL:
                                sb.append(SPACE);
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
            sb.append('\n');
        }
        // Нижняя рамка
        sb.append(WALL.repeat(width + 2)).append('\n');
        return sb.toString();
    }
}

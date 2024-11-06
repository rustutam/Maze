package backend.academy.app;

import backend.academy.generator.Generator;
import backend.academy.generator.KruskalMazeGenerator;
import backend.academy.generator.ModifiedKruskalMazeGenerator;
import backend.academy.generator.PrimaMazeGenerator;
import backend.academy.input.InputProvider;
import backend.academy.models.Coordinate;
import backend.academy.solver.BfsSolver;
import backend.academy.solver.DijkstraSolver;
import backend.academy.solver.Solver;
import java.io.PrintStream;
import java.security.SecureRandom;
import java.util.List;

public class Settings {
    private final SecureRandom random;
    private final InputProvider inputProvider;
    private final PrintStream out;
    private static final String INVALID_INPUT_MESSAGE = "Некорректный ввод. Пожалуйста, введите числовое значение.";
    private static final double EXTRA_EDGES_COEFFICIENT = 0.1;

    public Settings(InputProvider inputProvider, PrintStream out, SecureRandom random) {

        this.inputProvider = inputProvider;
        this.out = out;
        this.random = random;

    }

    private Integer getMazeDimension(String prompt, String errorMessage) {
        while (true) {
            try {
                String input = inputProvider.getInput(prompt);
                int dimension = Integer.parseInt(input);
                if (dimension > 0) {
                    return dimension;
                } else {
                    out.println(errorMessage);
                }
            } catch (NumberFormatException e) {
                out.println(INVALID_INPUT_MESSAGE);
            }
        }
    }

    public Integer getMazeHeight() {
        return getMazeDimension("Введите желаемую высоту лабиринта:",
            "Высота должна быть положительным числом. Попробуйте снова.");
    }

    public Integer getMazeWeight() {
        return getMazeDimension("Введите желаемую ширину лабиринта:",
            "Ширина должна быть положительным числом. Попробуйте снова.");
    }

    public Generator getGenerateAlgorithm() {
        return getAlgorithm(
            "Выберите алгоритм генерации лабиринта:",
            List.of(
                "1 - Прима",
                "2 - Краскала",
                "3 - Модифицированный Краскала(будет иметь циклы)"),
            List.of(
                new PrimaMazeGenerator(random),
                new KruskalMazeGenerator(random),
                new ModifiedKruskalMazeGenerator(random, EXTRA_EDGES_COEFFICIENT))
        );
    }

    public Solver getSolverAlgorithm() {
        return getAlgorithm(
            "Выберите алгоритм решения лабиринта:",
            List.of("1 - Дейкстра", "2 - BFS"),
            List.of(new DijkstraSolver(), new BfsSolver())
        );
    }

    private <T> T getAlgorithm(String prompt, List<String> options, List<T> algorithms) {
        while (true) {
            try {
                out.println(prompt);
                for (String option : options) {
                    out.println(option);
                }
                String input = inputProvider.getInput("Введите номер алгоритма:");
                int algorithm = Integer.parseInt(input);
                if (algorithm > 0 && algorithm <= algorithms.size()) {
                    return algorithms.get(algorithm - 1);
                } else {
                    out.println("Некорректный выбор. Пожалуйста, выберите правильный номер.");
                }
            } catch (NumberFormatException e) {
                out.println(INVALID_INPUT_MESSAGE);
            }
        }
    }

    private Coordinate getCoordinate(String prompt, List<Coordinate> allowedCoordinates) {
        while (true) {
            try {
                String input = inputProvider.getInput(prompt);
                String[] parts = input.split(",");
                if (parts.length != 2) {
                    out.println("Некорректный ввод. Пожалуйста, введите координаты в формате x,y.");
                    continue;
                }
                int x = Integer.parseInt(parts[0].trim());
                int y = Integer.parseInt(parts[1].trim());
                Coordinate coordinate = new Coordinate(x, y);
                if (allowedCoordinates.contains(coordinate)) {
                    return coordinate;
                } else {
                    out.println("Вы вводите координату стены или координату,которая находится вне лабиринта.");
                }
            } catch (NumberFormatException e) {
                out.println("Некорректный ввод. Пожалуйста, введите числ��вые значения для координат.");
            }
        }
    }

    public Coordinate getStartCoordinate(List<Coordinate> allowedCoordinates) {
        return getCoordinate("Введите координаты начальной точки x,y:", allowedCoordinates);
    }

    public Coordinate getFinishCoordinate(List<Coordinate> allowedCoordinates) {
        return getCoordinate("Введите координаты конечной точки (x,y):", allowedCoordinates);
    }

    private int getCount(String prompt) {
        while (true) {
            try {
                String input = inputProvider.getInput(prompt);
                int count = Integer.parseInt(input);
                if (count >= 0) {
                    return count;
                } else {
                    out.println("Количество должно быть положительным числом или 0. Попробуйте снова.");
                }
            } catch (NumberFormatException e) {
                out.println(INVALID_INPUT_MESSAGE);
            }
        }
    }

    public int getCoinCount() {
        return getCount("Введите количество монет в лабиринте: ");
    }

    public int getSandCount() {
        return getCount("Введите количество песка в лабиринте: ");
    }

}

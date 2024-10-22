package backend.academy;

import backend.academy.generator.Generator;
import backend.academy.generator.KruskalMazeGenerator;
import backend.academy.generator.PrimaMazeGenerator;
import backend.academy.input.InputProvider;
import backend.academy.solver.BfsSolver;
import backend.academy.solver.DijkstraSolver;
import backend.academy.solver.Solver;
import java.io.PrintStream;
import java.util.List;
import java.util.Random;

public class Settings {
    private Random random;
    private InputProvider inputProvider;
    private final PrintStream out;

    public Settings(InputProvider inputProvider, PrintStream out, Random random) {

        this.inputProvider = inputProvider;
        this.out = out;
        this.random = random;

    }

    public Integer getMazeHeight() {
        while (true) {
            try {
                String input = inputProvider.getInput("Введите желаемую высоту лабиринта:");
                int height = Integer.parseInt(input);
                if (height > 0) {
                    return height;
                } else {
                    out.println("Высота должна быть положительным числом. Попробуйте снова.");
                }
            } catch (NumberFormatException e) {
                out.println("Некорректный ввод. Пожалуйста, введите числовое значение.");
            }
        }
    }

    public Integer getMazeWeight() {
        while (true) {
            try {
                String input = inputProvider.getInput("Введите желаемую ширину лабиринта:");
                int weight = Integer.parseInt(input);
                if (weight > 0) {
                    return weight;
                } else {
                    out.println("Ширина должна быть положительным числом. Попробуйте снова.");
                }
            } catch (NumberFormatException e) {
                out.println("Некорректный ввод. Пожалуйста, введите числовое значение.");
            }
        }
    }

    public Generator getGenerateAlgorithm() {
        while (true) {
            try {
                out.println("Выберите алгоритм генерации лабиринта:");
                out.println("1 - Прима");
                out.println("2 - Краскала");
                String input = inputProvider.getInput("Введите номер алгоритма:");
                int algorithm = Integer.parseInt(input);
                if (algorithm == 1) {
                    return new PrimaMazeGenerator(random);
                } else if (algorithm == 2) {
                    return new KruskalMazeGenerator(random);
                } else {
                    out.println("Некорректный выбор. Пожалуйста, выберите 1 или 2.");
                }
            } catch (NumberFormatException e) {
                out.println("Некорректный ввод. Пожалуйста, введите числовое значение.");
            }
        }
    }

    public Solver getSolverAlgorithm() {
        while (true) {
            try {
                out.println("Выберите алгоритм решения лабиринта:");
                out.println("1 - Дейкстра");
                out.println("2 - BFS");
                String input = inputProvider.getInput("Введите номер алгоритма:");
                int algorithm = Integer.parseInt(input);
                if (algorithm == 1) {
                    return new DijkstraSolver();
                } else if (algorithm == 2) {
                    return new BfsSolver();
                } else {
                    out.println("Некорректный выбор. Пожалуйста, выберите 1 или 2.");
                }
            } catch (NumberFormatException e) {
                out.println("Некорректный ввод. Пожалуйста, введите числовое значение.");
            }
        }
    }

    public Coordinate getStartCoordinate(List<Coordinate> allowedCoordinates) {
        while (true) {
            try {
                String input = inputProvider.getInput("Введите координаты начальной точки x, y:");
                String[] parts = input.split(",");
                if (parts.length != 2) {
                    out.println("Некорректный ввод. Пожалуйста, введите координаты в формате x,y.");
                    continue;
                }
                int x = Integer.parseInt(parts[0].trim());
                int y = Integer.parseInt(parts[1].trim());
                Coordinate startCoordinate = new Coordinate(x, y);
                if (allowedCoordinates.contains(startCoordinate)) {
                    return startCoordinate;
                } else {
                    out.println("Вы вводите координату стены или координату,которая находится вне лабиринта.");
                }
            } catch (NumberFormatException e) {
                out.println("Некорректный ввод. Пожалуйста, введите числовые значения для координат.");
            }
        }
    }

    public Coordinate getFinishCoordinate(List<Coordinate> allowedCoordinates) {
        while (true) {
            try {
                String input = inputProvider.getInput("Введите координаты конечной точки (x, y):");
                String[] parts = input.split(",");
                if (parts.length != 2) {
                    out.println("Некорректный ввод. Пожалуйста, введите координаты в формате x,y.");
                    continue;
                }
                int x = Integer.parseInt(parts[0].trim());
                int y = Integer.parseInt(parts[1].trim());
                Coordinate finishCoordinate = new Coordinate(x, y);
                if (allowedCoordinates.contains(finishCoordinate)) {
                    return finishCoordinate;
                } else {
                    out.println("Вы вводите координату стены или координату,которая находится вне лабиринта.");
                }
            } catch (NumberFormatException e) {
                out.println("Некорректный ввод. Пожалуйста, введите числовые значения для координат.");
            }
        }
    }

}

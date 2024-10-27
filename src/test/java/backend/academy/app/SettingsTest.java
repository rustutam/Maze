package backend.academy.app;

import backend.academy.generator.Generator;
import backend.academy.generator.KruskalMazeGenerator;
import backend.academy.generator.PrimaMazeGenerator;
import backend.academy.input.InputProvider;
import backend.academy.models.Coordinate;
import backend.academy.solver.BfsSolver;
import backend.academy.solver.DijkstraSolver;
import backend.academy.solver.Solver;
import java.io.PrintStream;
import java.security.SecureRandom;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class SettingsTest {
    private InputProvider inputProvider;
    private Settings settings;

    @BeforeEach
    void setUp() {
        inputProvider = mock(InputProvider.class);
        PrintStream out = mock(PrintStream.class);
        SecureRandom random = new SecureRandom();
        settings = new Settings(inputProvider, out, random);
    }

    @Test
    void getMazeHeight() {
        // Arrange
        when(inputProvider.getInput(anyString())).thenReturn("10");

        // Act
        int height = settings.getMazeHeight();

        // Assert
        assertEquals(10, height);
    }

    @Test
    void getMazeWeight() {
        // Arrange
        when(inputProvider.getInput(anyString())).thenReturn("15");

        // Act
        int weight = settings.getMazeWeight();
        assertEquals(15, weight);
    }

    @Test
    void getGenerateAlgorithm() {
        // Arrange
        when(inputProvider.getInput(anyString())).thenReturn("1");

        // Act
        Generator generator = settings.getGenerateAlgorithm();

        // Assert
        assertInstanceOf(PrimaMazeGenerator.class, generator);

        // Arrange
        when(inputProvider.getInput(anyString())).thenReturn("2");

        // Act
        generator = settings.getGenerateAlgorithm();

        // Assert
        assertInstanceOf(KruskalMazeGenerator.class, generator);
    }

    @Test
    void getSolverAlgorithm() {
        // Arrange
        when(inputProvider.getInput(anyString())).thenReturn("1");
        // Act
        Solver solver = settings.getSolverAlgorithm();
        // Assert
        assertInstanceOf(DijkstraSolver.class, solver);

        // Arrange
        when(inputProvider.getInput(anyString())).thenReturn("2");
        // Act
        solver = settings.getSolverAlgorithm();
        // Assert
        assertInstanceOf(BfsSolver.class, solver);
    }

    @Test
    void getStartCoordinate() {
        // Arrange
        List<Coordinate> allowedCoordinates = List.of(new Coordinate(0, 0), new Coordinate(1, 1));
        when(inputProvider.getInput(anyString())).thenReturn("0,0");

        // Act
        Coordinate coordinate = settings.getStartCoordinate(allowedCoordinates);

        // Assert
        assertEquals(new Coordinate(0, 0), coordinate);
    }

    @Test
    void getFinishCoordinate() {
        // Arrange
        List<Coordinate> allowedCoordinates = List.of(new Coordinate(0, 0), new Coordinate(1, 1));
        when(inputProvider.getInput(anyString())).thenReturn("1,1");

        // Act
        Coordinate coordinate = settings.getFinishCoordinate(allowedCoordinates);

        // Assert
        assertEquals(new Coordinate(1, 1), coordinate);
    }

    @Test
    void getCoinCount() {
        // Arrange
        when(inputProvider.getInput(anyString())).thenReturn("5");

        // Act
        int coinCount = settings.getCoinCount();

        // Assert
        assertEquals(5, coinCount);
    }

    @Test
    void getSandCount() {
        // Arrange
        when(inputProvider.getInput(anyString())).thenReturn("3");

        // Act
        int sandCount = settings.getSandCount();

        // Assert
        assertEquals(3, sandCount);
    }

}

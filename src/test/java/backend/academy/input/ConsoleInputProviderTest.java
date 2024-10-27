package backend.academy.input;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

class ConsoleInputProviderTest {
    private ByteArrayOutputStream out;
    private ConsoleInputProvider consoleInputProvider;

    @BeforeEach
    void setUp() {
        String inputSymbol = "Hello";
        ByteArrayInputStream in = new ByteArrayInputStream(inputSymbol.getBytes());
        out = new ByteArrayOutputStream();
        consoleInputProvider = new ConsoleInputProvider(in, new PrintStream(out));
    }

    @Test
    void testGetInput() {
        // Arrange
        String message = "Enter input:";

        // Act
        String result = consoleInputProvider.getInput(message);

        // Assert
        assertEquals("Hello", result);
        assertEquals(message + " ", out.toString());
    }
}

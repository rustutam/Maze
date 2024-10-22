package backend.academy.input;

import java.io.InputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class ConsoleInputProvider implements InputProvider{
    private final Scanner scanner;
    private final PrintStream out;

    public ConsoleInputProvider(InputStream in, PrintStream out){
        this.scanner = new Scanner(in, StandardCharsets.UTF_8);
        this.out = out;
    }
    @Override
    public String getInput(String message) {
        out.print(message + " ");
        return scanner.nextLine();
    }
}

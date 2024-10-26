package backend.academy;

import backend.academy.input.ConsoleInputProvider;
import backend.academy.visualization.ConsoleRenderer;
import backend.academy.visualization.Renderer;
import java.security.SecureRandom;
import lombok.experimental.UtilityClass;

@UtilityClass
public class Main {
    public static void main(String[] args) {
        var in = System.in;
        var out = System.out;
        SecureRandom random = new SecureRandom();
        Settings settings = new Settings(new ConsoleInputProvider(in, out), out, random);
        Renderer ui = new ConsoleRenderer();
        App app = new App(out, settings, ui, random);
        app.start();

    }
}





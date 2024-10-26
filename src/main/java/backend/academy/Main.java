package backend.academy;

import backend.academy.app.App;
import backend.academy.app.GridGenerator;
import backend.academy.app.MazeService;
import backend.academy.app.Settings;
import backend.academy.converters.ToGraphConverter;
import backend.academy.converters.ToMazeListConverter;
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
        MazeService mazeService = new MazeService(random);
        Renderer ui = new ConsoleRenderer();
        ToGraphConverter toGraphConverter = new ToGraphConverter();
        ToMazeListConverter toMazeListConverter = new ToMazeListConverter();
        GridGenerator gridGenerator = new GridGenerator();
        App app = new App(out, settings, ui, mazeService, toGraphConverter, toMazeListConverter, gridGenerator);
        app.start();

    }
}





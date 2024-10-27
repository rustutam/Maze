package backend.academy.models;

import java.util.ArrayList;
import java.util.List;

public record Maze(int height, int width, MazeListModel mazeListModel) {
    // получить все проходы лабиринта
    public List<Coordinate> getAllPassage() {
        return new ArrayList<>(mazeListModel.coordinateNeighboursMap().keySet());
    }

}



package backend.academy.models;

import backend.academy.cell.Cell;
import backend.academy.cell.CellType;
import java.util.Arrays;
import java.util.List;

public record Maze(int height, int width, MazeListModel mazeListModel) {

    public List<Coordinate> getAllPassage() {
        Cell[][] mazeList = mazeListModel.mazeList();
        return Arrays.stream(mazeList)
            .flatMap(Arrays::stream)
            .filter(cell -> cell.type() == CellType.PASSAGE)
            .map(cell -> new Coordinate(cell.row(), cell.col()))
            .toList();
    }

}



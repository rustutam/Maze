package backend.academy.models;

import backend.academy.cell.Cell;
import java.util.List;
import java.util.Map;

public record MazeListModel(
    Cell[][] mazeList,
    Map<Coordinate, List<Coordinate>> coordinateNeighboursMap,
    int height,
    int width) {
}

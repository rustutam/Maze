package backend.academy.cell;

import lombok.ToString;

@ToString(callSuper = true)
public class Wall extends Cell {
    public Wall(int row, int col) {
        super(row, col, CellType.WALL);
    }
}

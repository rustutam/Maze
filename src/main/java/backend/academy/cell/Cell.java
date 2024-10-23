package backend.academy.cell;

import lombok.Getter;

@Getter
public abstract class Cell {
    private final int row;
    private final int col;

    public Cell(int row, int col) {
        this.row = row;
        this.col = col;

    }

}

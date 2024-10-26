package backend.academy.cell;

import java.util.Objects;
import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
public abstract class Cell {
    private final int row;
    private final int col;
    private final CellType type;

    public Cell(int row, int col, CellType type) {
        this.row = row;
        this.col = col;
        this.type = type;

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Cell cell = (Cell) o;
        return row == cell.row && col == cell.col && type == cell.type;
    }

    @Override
    public int hashCode() {
        return Objects.hash(row, col, type);
    }

}

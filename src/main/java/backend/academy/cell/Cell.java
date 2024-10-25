package backend.academy.cell;

import lombok.ToString;
import lombok.Getter;
import java.util.Objects;

@ToString
@Getter
public abstract class Cell {
    private final int row;
    private final int col;
    private final CellType type;



    public Cell(int row, int col,CellType type) {
        this.row = row;
        this.col = col;
        this.type = type;

    }

    public enum CellType {
        WALL,
        PASSAGE
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Cell cell = (Cell) o;
        return row == cell.row && col == cell.col && type == cell.type;
    }

    @Override
    public int hashCode() {
        return Objects.hash(row, col, type);
    }

    public static void main(String[] args) {
        Cell cell = new Passage(1, 2);
        Cell cell1 = new Passage(1, 2);
        Cell cell2 = new Wall(2, 2);
        System.out.println(cell.equals(cell1));
        System.out.println(cell2.equals(cell1));
    }

}

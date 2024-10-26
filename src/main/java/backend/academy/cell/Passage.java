package backend.academy.cell;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString(callSuper = true)
public class Passage extends Cell {
    private PassageType passageType;

    public Passage(int row, int col) {
        super(row, col, CellType.PASSAGE);
        this.passageType = PassageType.NORMAL;
    }

}

package backend.academy.cell;

import lombok.Getter;
import lombok.Setter;


@Setter @Getter
public class Passage extends Cell {
    private PassageType type;

    public Passage(int row, int col, PassageType type) {
        super(row, col);
        this.type = type;
    }

    public enum PassageType {
        NORMAL,
        SAND,
        COIN
    }

    public static void main(String[] args) {
        Passage cell = new Passage(1, 2,PassageType.NORMAL);
        cell.type(PassageType.COIN);
    }

}

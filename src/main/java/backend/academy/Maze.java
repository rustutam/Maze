package backend.academy;

import backend.academy.models.MazeListModel;
import lombok.Getter;

@Getter public record Maze(int height, int width, MazeListModel mazeListModel) {
}



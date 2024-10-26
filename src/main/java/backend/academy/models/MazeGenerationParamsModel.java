package backend.academy.models;

import backend.academy.app.GridGenerator;
import backend.academy.converters.ToGraphConverter;
import backend.academy.converters.ToMazeListConverter;
import backend.academy.generator.Generator;

public record MazeGenerationParamsModel(int height, int width, Generator generator, int coinCount, int sandCount,
                                        ToGraphConverter toGraphConverter, ToMazeListConverter toMazeListConverter,
                                        GridGenerator gridGenerator) {
}

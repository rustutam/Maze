package backend.academy.models;

import backend.academy.graph.Graph;
import backend.academy.graph.Vertex;
import java.util.Map;

public record ConvertedMazeModel(Graph graph, Map<Coordinate, Vertex> coordinateVertexMap, int height, int width) {
}

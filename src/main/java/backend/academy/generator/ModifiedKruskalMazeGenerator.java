package backend.academy.generator;

import backend.academy.graph.Edge;
import backend.academy.graph.Graph;
import java.security.SecureRandom;
import java.util.List;

public class ModifiedKruskalMazeGenerator extends KruskalMazeGenerator {
    private final double extraEdgesCoefficient;
    private final SecureRandom random;

    public ModifiedKruskalMazeGenerator(SecureRandom random, double extraEdgesCoefficient) {
        super(random);
        this.extraEdgesCoefficient = extraEdgesCoefficient;
        this.random = random;
    }

    @Override
    public Graph generate(Graph graph) {
        Graph minGraph = super.generate(graph);

        // Получаем все рёбра графа
        List<Edge> allEdges = graph.getEdges();
        allEdges.removeAll(minGraph.getEdges());

        // Добавляем дополнительные рёбра
        int extraEdgesCount = (int) (graph.getEdges().size() * extraEdgesCoefficient);

        for (int i = 0; i < extraEdgesCount; i++) {
            if (allEdges.isEmpty()) {
                break;
            }
            Edge edge = allEdges.get(random.nextInt(allEdges.size()));
            minGraph.addEdge(edge.from(), edge.to());
            allEdges.remove(edge);
        }
        return minGraph;
    }
}

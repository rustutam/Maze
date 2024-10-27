package backend.academy.generator;

import backend.academy.graph.Edge;
import backend.academy.graph.Graph;
import backend.academy.graph.Vertex;
import com.google.common.collect.Sets;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class PrimaMazeGenerator implements Generator {

    private final SecureRandom random;

    public PrimaMazeGenerator(SecureRandom random) {
        this.random = random;
    }

    /**
     * Генерирует минимальный остовный граф (минимальное остовное дерево) из заданного графа.
     *
     * @param graph исходный граф
     * @return минимальный остовный граф
     */

    @Override
    public Graph generate(Graph graph) {
        // Получаем все вершины графа
        List<Vertex> allVertex = graph.getVertices();
        // Выбираем случайную стартовую вершину
        Vertex startVertex = allVertex.get(random.nextInt(allVertex.size()));
        Set<Vertex> visitedVertex = Sets.newHashSetWithExpectedSize(allVertex.size());

        // Получаем рёбра, смежные со стартовой вершиной
        List<Edge> neighboursEdges = getNeighboursEdges(graph, startVertex);

        visitedVertex.add(startVertex);

        Graph minGraph = new Graph();
        minGraph.addVertex(startVertex);

        // Пока не все вершины посещены
        while (visitedVertex.size() < allVertex.size()) {
            // Выбираем случайное ребро из списка смежных рёбер
            Edge edge = neighboursEdges.get(random.nextInt(neighboursEdges.size()));
            Vertex v1 = edge.from();
            Vertex v2 = edge.to();
            // Если обе вершины ребра уже посещены, удаляем ребро из списка и продолжаем
            if (visitedVertex.contains(v1) && visitedVertex.contains(v2)) {
                neighboursEdges.remove(edge);
                continue;
            }

            // Определяем следующую вершину для добавления в остов
            Vertex nextVertex = visitedVertex.contains(v1) ? v2 : v1;
            minGraph.addVertex(nextVertex);
            minGraph.addEdge(v1, v2);
            neighboursEdges.remove(edge);
            // Добавляем все рёбра, смежные с новой вершиной
            neighboursEdges.addAll(graph.getNeighbours(nextVertex));

            visitedVertex.add(nextVertex);

        }

        return minGraph;
    }

    /**
     * Получает список рёбер, смежных с заданной вершиной.
     *
     * @param graph  граф, в котором производится поиск
     * @param vertex вершина, соседей которой ищем
     * @return список рёбер, смежных с начальной вершиной
     */

    private List<Edge> getNeighboursEdges(Graph graph, Vertex vertex) {
        return new ArrayList<>(graph.getNeighbours(vertex).stream().toList());
    }
}



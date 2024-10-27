package backend.academy.generator;

import backend.academy.graph.Edge;
import backend.academy.graph.Graph;
import backend.academy.graph.Vertex;
import com.google.common.collect.Maps;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Random;

public class KruskalMazeGenerator implements Generator {
    private final Random random;

    public KruskalMazeGenerator(Random random) {
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
        Graph minGraph = new Graph();

        // Получаем все рёбра графа
        List<Edge> allEdges = graph.getEdges();

        // Инициализируем компоненты связности для каждой вершины
        Map<Vertex, Integer> connectiveComponent = getConnectiveComponent(graph.getVertices());
        while (!allEdges.isEmpty()) {
            // Выбираем случайное ребро
            Edge edge = allEdges.get(random.nextInt(allEdges.size()));
            Vertex v1 = edge.from();
            Vertex v2 = edge.to();
            // Проверяем, принадлежат ли вершины одного ребра разным компонентам связности
            if (!Objects.equals(connectiveComponent.get(v1), connectiveComponent.get(v2))) {
                // Объединяем компоненты связности
                mergeConnectiveComponent(connectiveComponent, v1, v2);
                minGraph.addVertex(v1);
                minGraph.addVertex(v2);
                minGraph.addEdge(v1, v2);
            }

            allEdges.remove(edge);

        }

        return minGraph;
    }

    /**
     * Объединяет две компоненты связности.
     *
     * @param connectiveComponent map компонент связности
     * @param v1                  первая вершина
     * @param v2                  вторая вершина
     */
    private void mergeConnectiveComponent(
        Map<Vertex, Integer> connectiveComponent,
        Vertex v1,
        Vertex v2
    ) {

        int component1 = connectiveComponent.get(v1);
        int component2 = connectiveComponent.get(v2);
        for (Map.Entry<Vertex, Integer> entry : connectiveComponent.entrySet()) {
            if (Objects.equals(entry.getValue(), component2)) {
                entry.setValue(component1);
            }
        }
    }

    /**
     * Инициализирует компоненты связности для всех вершин графа.
     *
     * @param allVertex список всех вершин графа
     * @return map компонент связности
     */
    private Map<Vertex, Integer> getConnectiveComponent(List<Vertex> allVertex) {
        int component = 0;
        Map<Vertex, Integer> connectiveComponent = Maps.newHashMapWithExpectedSize(allVertex.size());

        for (Vertex vertex : allVertex) {
            connectiveComponent.put(vertex, component);
            component += 1;
        }
        return connectiveComponent;
    }
}

package backend.academy.solver;

import backend.academy.graph.Edge;
import backend.academy.graph.Graph;
import backend.academy.graph.Vertex;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class DijkstraSolver implements Solver {
    /**
     * Решает задачу нахождения самого выгодного пути с использованием алгоритма Дейкстры.
     *
     * @param graph       граф, в котором производится поиск
     * @param startVertex начальная вершина
     * @param endVertex   конечная вершина
     * @return список вершин, представляющих самый длинный путь от startVertex до endVertex,
     *     или пустой список, если путь не найден
     */
    @Override
    public List<Vertex> solve(Graph graph, Vertex startVertex, Vertex endVertex) {
        List<Vertex> allVertex = graph.getVertices();

        Set<Vertex> visitedVertex = Sets.newHashSetWithExpectedSize(allVertex.size());
        // Инициализируем мапы расстояний и предшественников
        Map<Vertex, Integer> distance = Maps.newHashMapWithExpectedSize(allVertex.size());
        Map<Vertex, Vertex> predecessors = Maps.newHashMapWithExpectedSize(allVertex.size());

        // Проверяем, содержится ли начальная вершина в графе
        if (!allVertex.contains(startVertex)) {
            throw new IllegalArgumentException("Start vertex not in graph");
        }
        // Устанавливаем начальные расстояния до всех вершин равным MIN_VALUE
        for (Vertex vertex : allVertex) {
            distance.put(vertex, Integer.MAX_VALUE);
        }
        // Устанавливаем расстояние до начальной вершины равным 0
        distance.put(startVertex, 0);

        // Пока не все вершины посещены
        while (visitedVertex.size() != allVertex.size()) {
            // Находим вершину с минимальным расстоянием, которая ещё не посещена
            Vertex currentVertex = getMinDistanceVertex(distance, visitedVertex);
            visitedVertex.add(currentVertex);
            // Получаем соседей текущей вершины, которые ещё не посещены
            Set<Edge> neighbours = graph.getNeighbours(currentVertex).stream()
                .filter(edge -> !visitedVertex.contains(edge.getSecondVertex(currentVertex)))
                .collect(Collectors.toSet());
            // Обновляем расстояния до соседей
            for (Edge edge : neighbours) {
                Vertex neighbour = edge.getSecondVertex(currentVertex);
                int newDistance = distance.get(currentVertex) + neighbour.weight();
                if (newDistance < distance.get(neighbour)) {
                    distance.put(neighbour, newDistance);
                    predecessors.put(neighbour, currentVertex);
                }
            }
        }
        return reconstructPath(predecessors, endVertex);

    }

    /**
     * Восстанавливает путь от конечной вершины до начальной вершины с использованием мапы предшественников.
     *
     * @param predecessors мапа, где каждой вершине соответствует её предшественник на пути
     * @param endVertex    конечная вершина
     * @return список вершин, представляющих путь от начальной вершины до конечной вершины
     */
    private List<Vertex> reconstructPath(Map<Vertex, Vertex> predecessors, Vertex endVertex) {
        List<Vertex> path = new ArrayList<>();
        for (Vertex at = endVertex; at != null; at = predecessors.get(at)) {
            path.add(at);
        }
        // Переворачиваем путь, чтобы он шёл от начальной вершины к конечной
        Collections.reverse(path);
        // Если путь состоит из одной вершины и это не конечная вершина, возвращаем пустой список
        if (path.size() == 1 && !path.getFirst().equals(endVertex)) {
            return Collections.emptyList();
        }
        return path;
    }

    /**
     * Находит вершину с максимальным расстоянием, которая ещё не была посещена.
     *
     * @param distance      карта расстояний до каждой вершины
     * @param visitedVertex множество посещённых вершин
     * @return вершина с максимальным расстоянием
     * @throws IllegalArgumentException если вершина не найдена
     */
    Vertex getMinDistanceVertex(Map<Vertex, Integer> distance, Set<Vertex> visitedVertex) {
        Vertex minVertex = null;
        int minDistance = Integer.MAX_VALUE;
        // Проходим по всем вершинам и находим ту, которая имеет минимальное расстояние и не была посещена
        for (Map.Entry<Vertex, Integer> entry : distance.entrySet()) {
            if (!visitedVertex.contains(entry.getKey()) && entry.getValue() < minDistance) {
                minDistance = entry.getValue();
                minVertex = entry.getKey();
            }
        }
        // Если вершина не найдена, выбрасываем исключение
        if (minVertex == null) {
            throw new IllegalArgumentException("No vertex found");
        }
        return minVertex;
    }

}

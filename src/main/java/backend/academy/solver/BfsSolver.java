package backend.academy.solver;

import backend.academy.graph.Graph;
import backend.academy.graph.Vertex;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

public class BfsSolver implements Solver {
    /**
     * Решает задачу нахождения кратчайшего пути с использованием BFS.
     *
     * @param graph       граф, в котором производится поиск
     * @param startVertex начальная вершина
     * @param endVertex   конечная вершина
     * @return список вершин, представляющих кратчайший путь от startVertex до endVertex,
     *     или пустой список, если путь не найден
     */
    @Override
    public List<Vertex> solve(Graph graph, Vertex startVertex, Vertex endVertex) {
        // Очередь для хранения вершин, которые нужно посетить
        Queue<Vertex> queue = new LinkedList<>();
        // Мапа для хранения предшественников каждой вершины
        Map<Vertex, Vertex> predecessors = new HashMap<>();
        // Множество для хранения посещённых вершин
        HashSet<Vertex> visited = new HashSet<>();
        // Добавляем начальную вершину в очередь
        queue.add(startVertex);

        // Пока очередь не пуста
        while (!queue.isEmpty()) {
            Vertex currentVertex = queue.poll();
            visited.add(currentVertex);

            // Если текущая вершина является конечной, восстанавливаем путь
            if (currentVertex.equals(endVertex)) {
                return reconstructPath(predecessors, endVertex);
            }
            // Обрабатываем всех соседей текущей вершины
            for (Vertex neighbor : graph.getNeighbours(currentVertex).stream()
                .map(edge -> edge.getSecondVertex(currentVertex))
                .filter(neighbor -> !visited.contains(neighbor))
                .toList()) {
                queue.add(neighbor);
                visited.add(neighbor);
                // Устанавливаем текущую вершину как предшественника для соседа
                predecessors.put(neighbor, currentVertex);
            }
        }

        return new ArrayList<>();
    }

    /**
     * Восстанавливает путь от конечной вершины до начальной вершины с использованием мапы предшественников.
     *
     * @param predecessors карта, где каждой вершине соответствует её предшественник на пути
     * @param endVertex    конечная вершина
     * @return список вершин, представляющих путь от начальной вершины до конечной вершины
     */
    private List<Vertex> reconstructPath(Map<Vertex, Vertex> predecessors, Vertex endVertex) {
        List<Vertex> path = new ArrayList<>();
        for (Vertex at = endVertex; at != null; at = predecessors.get(at)) {
            path.add(at);
        }
        // Переворачиваем путь, чтобы он шёл от начальной вершины
        Collections.reverse(path);
        return path;
    }
}

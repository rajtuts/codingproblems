package graph;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class AllPathsSourceTargetLeetCode797 {
    public void bfs(int[][] graph, List<List<Integer>> list, int src, int tar) {
        Queue<List<Integer>> queue = new LinkedList<>();
        List<Integer> path = new ArrayList<>();
        path.add(src);
        queue.add(path);
        while (!queue.isEmpty()) {
            path = queue.poll();
            int last = path.get(path.size() - 1);
            if (last == tar) {
                list.add(path);
                continue;
            }
            for (Integer adj : graph[last]) {
                if (!path.contains(adj)) {
                    List<Integer> newPath = new ArrayList<>(path);
                    newPath.add(adj);
                    queue.add(newPath);
                }
            }
        }
    }

    public List<List<Integer>> allPathsSourceTarget(int[][] graph) {
        List<List<Integer>> list = new ArrayList<>();
        int target = graph.length - 1;
        bfs(graph, list, 0, target);
        return list;
    }
}

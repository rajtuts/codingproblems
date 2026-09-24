package graph;

public class BipartiteLeetCode797 {
    public boolean BFS(int[][] graph, int[] colors, int node) {
        Queue<Integer> queue = new LinkedList<>();
        queue.add(node);
        colors[node] = 1; // 1, -1
        while (!queue.isEmpty()) {
            int curr = queue.poll();
            for (int adj : graph[curr]) {
                if (colors[adj] == 0) {
                    colors[adj] = -1 * colors[curr];
                    queue.add(adj);
                } else if (colors[curr] == colors[adj]) {
                    return false;
                }
            }
        }
        return true;
    }

    public boolean isBipartite(int[][] graph) {
        int v = graph.length;
        int[] colors = new int[v];
        for (int node = 0; node < v; node++) {
            if (colors[node] == 0) {
                if (BFS(graph, colors, node) == false) {
                    return false;
                }
            }
        }
        return true;
    }
}

package graph;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class TopologicalSort {
    static int[] topoSort(int V, ArrayList<ArrayList<Integer>> adj) {
        // kahn's algorithm
        int[] indegree = new int[V];
        int[] top_sort = new int[V];
        for (ArrayList<Integer> list : adj) {
            for (int node : list) {
                indegree[node]++;
            }
        }
        Queue<Integer> queue = new LinkedList<>();
        for (int i = 0; i < indegree.length; i++) {
            if (indegree[i] == 0) {
                queue.add(i);
            }
        }
        int index = 0;
        while (!queue.isEmpty()) {
            int curr = queue.poll();
            for (Integer nbr : adj.get(curr)) {
                indegree[nbr]--;
                if (indegree[nbr] == 0) {
                    queue.add(nbr);
                }
            }
            top_sort[index] = curr;
            index++;
        }
        return top_sort;
    }
}

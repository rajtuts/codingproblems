# Task Scheduler
Given a characters array tasks, representing the tasks a CPU needs to do, where each letter represents a different task. Tasks could be done in any order. Each task is done in one unit of time. For each unit of time, the CPU could complete either one task or just be idle. However, there is a non-negative integer n that represents the cooldown period between two same tasks (the same letter in the array), that is that there must be at least n units of time between any two same tasks.

Example 1:
Input: {"tasks":["A","A","A","B","B","B"],"n":2}
Output: 8

Calculate minimum based on most frequent task. Either fill gaps with idle or tasks overflow.

Time: O(n)
Space: O(1)

```
class Solution {
    public int leastInterval(char[] tasks, int n) {
        int[] counter = new int[26];
        for (char task : tasks) counter[task - 'A']++;
        
        PriorityQueue<Integer> maxHeap = new PriorityQueue<>((a, b) -> b - a);
        for (int count : counter) {
            if (count > 0) maxHeap.add(count);
        }
        
        int time = 0;
        Queue<int[]> q = new LinkedList<>(); // {cnt, idleTime}
        
        while (!maxHeap.isEmpty() || !q.isEmpty()) {
            time++;
            
            if (!maxHeap.isEmpty()) {
                int val = maxHeap.poll();
                val--;
                if (val > 0) q.add(new int[]{val, time + n});
            }
            
            if (!q.isEmpty() && q.peek()[1] == time) {
                maxHeap.add(q.poll()[0]);
            }
        }
        return time;
    }
}
```

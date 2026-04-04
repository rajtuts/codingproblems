# Detect Squares
You are given a stream of points on the X-Y plane. Design an algorithm that adds new points from the stream into a data structure. Duplicate points are allowed and should be treated as different points. Given a query point, counts the number of ways to choose three points from the data structure such that the three points and the query point form an axis-aligned square with positive area.  

## Example 1:  
Input: {"operations":["add","add","add","count"],"values":[[[3,10]],[[11,2]],[[3,2]],[[11,10]]]}  
Output: [null,null,null,1]  

## Approach: Hash Map with Diagonal Enumeration  

Key Insight: For a query point P, enumerate all diagonal points D. If |x_d - x_p| == |y_d - y_p|, check if the other two corners exist.  

## Algorithm:  

1. Store points with counts in a hash map  
1. For count(P): iterate all points D that could be diagonal  
1. Check if (D.x, P.y) and (P.x, D.y) exist  
1. Multiply counts for all combinations  
1. Time Complexity: O(n) for count, O(1) for add Space Complexity: O(n)   

### Time: O(n) per count  
### Space: O(n)  

```
class DetectSquares {
    Map<String, Integer> points;

    public DetectSquares() {
        points = new HashMap<>();
    }

    public void add(int[] point) {
        String key = point[0] + "," + point[1];
        points.put(key, points.getOrDefault(key, 0) + 1);
    }

    public int count(int[] point) {
        int px = point[0], py = point[1];
        int result = 0;

        for (Map.Entry<String, Integer> entry : points.entrySet()) {
            String[] parts = entry.getKey().split(",");
            int x = Integer.parseInt(parts[0]);
            int y = Integer.parseInt(parts[1]);
            if (Math.abs(x - px) != Math.abs(y - py) || x == px) continue;
            int cnt = entry.getValue();
            int cnt1 = points.getOrDefault(x + "," + py, 0);
            int cnt2 = points.getOrDefault(px + "," + y, 0);
            result += cnt * cnt1 * cnt2;
        }

        return result;
    }
}
```

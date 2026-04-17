# Asteroid Collision
Given an array of asteroids, find out the state after all collisions. Positive means moving right, negative means moving left. The larger asteroid survives.

Example 1:
Input: [5,10,-5]
Output: [5,10]

**Approach: Stack Simulation**

The key insight is understanding when collisions occur:

-   Positive asteroids move right (→)
    
-   Negative asteroids move left (←)
    
-   Collision happens ONLY when: stack top is positive AND current asteroid is negative
    

**Algorithm:**

1.  Use a stack to store surviving asteroids.
    
2.  For each asteroid:
    
    -   If no collision possible (stack empty, or same direction, or both moving away), push to stack.
        
    -   If collision possible (stack top > 0 and current < 0):
        
        -   Compare sizes (absolute values)
            
        -   If stack top is smaller: pop it, continue checking (chain reaction)
            
        -   If equal: pop stack top, current also destroyed
            
        -   If stack top is larger: current is destroyed
            

**Collision Cases:**

-   `[5, -3]`: 5 > |-3|, so -3 explodes → `[5]`
    
-   `[3, -5]`: 3 < |-5|, so 3 explodes → `[-5]`
    
-   `[5, -5]`: 5 == |-5|, both explode → `[]`
    

**No Collision Cases:**

-   `[5, 10]`: Both positive (same direction) → No collision
    
-   `[-5, -10]`: Both negative (same direction) → No collision
    
-   `[-5, 10]`: -5 moves left, 10 moves right (moving apart) → No collision
    

**Example: \[10, 2, -5\]**

-   Push 10: stack = \[10\]
    
-   Push 2: stack = \[10, 2\]
    
-   Process -5: Collision with 2! |2| < |-5|, so 2 explodes
    
-   Process -5: Collision with 10! |10| > |-5|, so -5 explodes
    
-   Result: \[10\]
    

**Time Complexity:** O(n) - each asteroid is pushed/popped at most once **Space Complexity:** O(n) - for the stack

**Time: O(n)**

**Space: O(n)**

```
class Solution {
    public int[] asteroidCollision(int[] asteroids) {
        Stack<Integer> stack = new Stack<>();
        for (int asteroid : asteroids) {
            boolean alive = true;
            while (alive && !stack.isEmpty() && asteroid < 0 && stack.peek() > 0) {
                alive = stack.peek() < -asteroid;
                if (stack.peek() <= -asteroid) {
                    stack.pop();
                }
            }
            if (alive) {
                stack.push(asteroid);
            }
        }
        int[] result = new int[stack.size()];
        for (int i = result.length - 1; i >= 0; i--) {
            result[i] = stack.pop();
        }
        return result;
    }
}
````

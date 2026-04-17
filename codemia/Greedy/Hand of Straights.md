# Hand of Straights
Alice has some number of cards and she wants to rearrange the cards into groups so that each group is of size groupSize, and consists of groupSize consecutive cards. Given an integer array hand where hand[i] is the value written on the ith card and an integer groupSize, return true if she can rearrange the cards, or false otherwise.

Example 1:
Input: {"hand":[1,2,3,6,2,3,4,7,8],"groupSize":3}
Output: true

**Approach: Greedy with Counting**

**Key Insight:** Start from the smallest card and form consecutive groups. Each group starting at card x needs x, x+1, ..., x+groupSize-1.

**Algorithm:**

1.  If n % groupSize != 0: impossible
    
2.  Count card frequencies
    
3.  For each card in sorted order:
    
    -   If count > 0: need to start 'count' groups here
        
    -   For i in range(groupSize):
        
        -   If count\[card + i\] < need: return False
            
        -   Decrement count\[card + i\] by need
            
4.  Return True
    

**Example:** hand = \[1,2,3,6,2,3,4,7,8\], groupSize = 3

-   Counts: {1:1, 2:2, 3:2, 4:1, 6:1, 7:1, 8:1}
    
-   Card 1: form \[1,2,3\], decrement each
    
-   Card 2: form \[2,3,4\], decrement each
    
-   Card 6: form \[6,7,8\], decrement each
    
-   Result: True
    

**Time Complexity:** O(n log n) - sorting **Space Complexity:** O(n) - counter

**Time: O(n log n)**

**Space: O(n)**

```
class Solution {
    public boolean isNStraightHand(int[] hand, int groupSize) {
        if (hand.length % groupSize != 0) return false;
        
        TreeMap<Integer, Integer> count = new TreeMap<>();
        for (int n : hand) count.put(n, count.getOrDefault(n, 0) + 1);
        
        while (!count.isEmpty()) {
            int first = count.firstKey();
            for (int i = first; i < first + groupSize; i++) {
                if (!count.containsKey(i)) return false;
                int c = count.get(i);
                if (c == 1) count.remove(i);
                else count.put(i, c - 1);
            }
        }
        return true;
    }
}

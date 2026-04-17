# Selection Sort
Repeatedly finds the minimum element from the unsorted part and puts it at the beginning. Return the sorted array.

Example 1:
Input: [64,34,25,12,22,11,90]
Output: [11,12,22,25,34,64,90]

**Approach: Selection Sort**

**Key Insight:** Repeatedly find the minimum element from the unsorted portion and swap it with the first unsorted position.

**Algorithm:**

1.  For each position i (0 to n-1):
    
    -   Find index of minimum element in \[i, n-1\]
        
    -   Swap nums\[i\] with nums\[min\_idx\]
        

**Example:** \[64, 25, 12, 22, 11\]

-   Find min in \[0,4\]: 11 at idx 4, swap → \[11, 25, 12, 22, 64\]
    
-   Find min in \[1,4\]: 12 at idx 2, swap → \[11, 12, 25, 22, 64\]
    
-   Find min in \[2,4\]: 22 at idx 3, swap → \[11, 12, 22, 25, 64\]
    
-   Find min in \[3,4\]: 25 at idx 3, no swap → \[11, 12, 22, 25, 64\]
    

**Characteristics:**

-   NOT stable (swaps can change relative order)
    
-   Always O(n²) regardless of input
    
-   Minimizes swaps (exactly n-1 swaps)
    

**Time Complexity:** O(n²) - always **Space Complexity:** O(1) - in-place

**Time: O(n²)**

**Space: O(1)**

```
class Solution {
    public void selectionSort(int[] arr) {
        int n = arr.length;
        for (int i = 0; i < n - 1; i++) {
            int minIdx = i;
            for (int j = i + 1; j < n; j++) {
                if (arr[j] < arr[minIdx]) {
                    minIdx = j;
                }
            }
            if (minIdx != i) {
                int temp = arr[i];
                arr[i] = arr[minIdx];
                arr[minIdx] = temp;
            }
        }
    }
}
```

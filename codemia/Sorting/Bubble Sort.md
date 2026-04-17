# Bubble Sort
Repeatedly steps through the list, compares adjacent elements and swaps them if they are in the wrong order. Return the sorted array.

Example 1:
Input: [64,34,25,12,22,11,90]
Output: [11,12,22,25,34,64,90]

**Approach: Bubble Sort**

**Key Insight:** Repeatedly swap adjacent elements if out of order. Each pass "bubbles" the largest unsorted element to its final position.

**Algorithm:**

1.  For i in range(n):
    
    -   For j in range(n - i - 1):
        
        -   If nums\[j\] > nums\[j+1\]: swap them
            
    -   Optimization: if no swaps occurred, array is sorted
        

**Example:** \[5, 2, 8, 1\]

-   Pass 1: \[2,5,1,8\] (8 bubbles to end)
    
-   Pass 2: \[2,1,5,8\] (5 in position)
    
-   Pass 3: \[1,2,5,8\] (sorted!)
    

**Characteristics:**

-   Stable sort (preserves relative order of equal elements)
    
-   Adaptive (faster on nearly sorted data with optimization)
    
-   In-place (O(1) extra space)
    

**Time Complexity:** O(n²) worst/average, O(n) best (if optimized) **Space Complexity:** O(1) - only swap variable

**Time: O(n²)**

**Space: O(1)**

```
class Solution {
    public void bubbleSort(int[] arr) {
        int n = arr.length;
        for (int i = 0; i < n - 1; i++) {
            boolean swapped = false;
            for (int j = 0; j < n - 1 - i; j++) {
                if (arr[j] > arr[j + 1]) {
                    int temp = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = temp;
                    swapped = true;
                }
            }
            if (!swapped) break;
        }
    }
}
````

# Insertion Sort
Builds the final sorted array (or list) one item at a time by comparisons. Return the sorted array.

Example 1:
Input: [64,34,25,12,22,11,90]
Output: [11,12,22,25,34,64,90]

**Approach: Insertion Sort**

**Key Insight:** Build a sorted portion from left to right. For each new element, find its correct position in the sorted portion and insert it.

**Algorithm:**

1.  Start with first element as "sorted"
    
2.  For each remaining element (key):
    
    -   Shift larger sorted elements right
        
    -   Insert key in correct position
        

**Example:** \[12, 11, 13, 5, 6\]

-   \[12\] | 11, 13, 5, 6 → \[11, 12\] | 13, 5, 6
    
-   \[11, 12\] | 13, 5, 6 → \[11, 12, 13\] | 5, 6
    
-   \[11, 12, 13\] | 5, 6 → \[5, 11, 12, 13\] | 6
    
-   \[5, 11, 12, 13\] | 6 → \[5, 6, 11, 12, 13\]
    

**Characteristics:**

-   Stable sort
    
-   Adaptive (O(n) for nearly sorted)
    
-   Excellent for small arrays or online sorting
    

**Time Complexity:** O(n²) worst, O(n) best (nearly sorted) **Space Complexity:** O(1) - in-place

**Time: O(n²)**

**Space: O(1)**

```
class Solution {
    public void insertionSort(int[] arr) {
        int n = arr.length;
        for (int i = 1; i < n; ++i) {
            int key = arr[i];
            int j = i - 1;

            while (j >= 0 && arr[j] > key) {
                arr[j + 1] = arr[j];
                j = j - 1;
            }
            arr[j + 1] = key;
        }
    }
}

# Pascal's Triangle
Given an integer numRows, return the first numRows of Pascal's triangle. In Pascal's triangle, each number is the sum of the two numbers directly above it.

## Example 1:  
Input: 5  
Output: [[1],[1,1],[1,2,1],[1,3,3,1],[1,4,6,4,1]]  

**Approach: Row-by-Row Construction**

Pascal's Triangle has a simple recursive structure where each element (except edges) is the sum of the two elements above it.

**Algorithm:**  
1.  Create an empty triangle list     
2.  For each row i from 0 to numRows-1:      
    -   Create a row of (i + 1) ones        
    -   For middle elements (1 to i-1): row\[j\] = triangle\[i-1\]\[j-1\] + triangle\[i-1\]\[j\]        
    -   Append row to triangle        
3.  Return triangle      
**Visual Example (numRows = 5):**  
`Row 0: [1] (base) Row 1: [1,1] (base) Row 2: [1,2,1] (1+1=2) Row 3: [1,3,3,1] (1+2=3, 2+1=3) Row 4: [1,4,6,4,1] (1+3=4, 3+3=6, 3+1=4)`

**Key Insight:**  
-   Edge elements are always 1      
-   Interior element = left parent + right parent  
**Time Complexity:** O(numRows²) - building triangle **Space Complexity:** O(numRows²) - storing all elements    
**Time: O(numRows²)**  **Space: O(numRows²)**  
```
class Solution {
    public List<List<Integer>> generate(int numRows) {
        List<List<Integer>> triangle = new ArrayList<>();

        for (int i = 0; i < numRows; i++) {
            List<Integer> row = new ArrayList<>();
            for (int j = 0; j <= i; j++) {
                if (j == 0 || j == i) {
                    row.add(1);
                } else {
                    row.add(triangle.get(i-1).get(j-1) + triangle.get(i-1).get(j));
                }
            }
            triangle.add(row);
        }

        return triangle;
    }
}
```

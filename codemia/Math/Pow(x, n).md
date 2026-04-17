# Pow(x, n)
Implement pow(x, n), which calculates x raised to the power n (i.e., x^n). Use the fast exponentiation algorithm to achieve O(log n) time complexity.

Example 1:
Input: {"x":2,"n":10}
Output: 1024.0

**Approach: Fast Exponentiation (Binary Exponentiation)**

Instead of O(n) multiplications, we can compute x^n in O(log n) time.

**Key Insight:**

-   x^n = (x^2)^(n/2) when n is even
    
-   x^n = x × (x^2)^((n-1)/2) when n is odd
    

**Algorithm:**

1.  Handle negative exponent: x^(-n) = (1/x)^n
    
2.  Initialize result = 1
    
3.  While n > 0:
    
    -   If n is odd: multiply result by x
        
    -   Square x (x = x × x)
        
    -   Halve n (n = n // 2)
        

**Example: 2^10**

`n=10: n even, x=2→4, n=5 n=5: n odd, result=1*4=4, x=4→16, n=2 n=2: n even, x=16→256, n=1 n=1: n odd, result=4*256=1024, x=256→..., n=0 Result: 1024`

**Trace in binary:**

-   10 in binary = 1010
    
-   2^10 = 2^8 × 2^2 = 256 × 4 = 1024
    
-   We only multiply when bit is 1!
    

**Handling edge cases:**

-   n = 0: return 1
    
-   n < 0: compute (1/x)^|n|
    
-   Overflow: use long for n (n = -2^31 can overflow when negated)
    

**Time Complexity:** O(log n) - halving n each iteration **Space Complexity:** O(1) - iterative version

**Time: O(log n)**

**Space: O(1)**

```
class Solution {
    public double myPow(double x, int n) {
        if (n == 0) return 1;

        long N = n;
        if (N < 0) {
            x = 1 / x;
            N = -N;
        }

        double result = 1;
        while (N > 0) {
            if (N % 2 == 1) {
                result *= x;
            }
            x *= x;
            N /= 2;
        }
        return result;
    }
}
```

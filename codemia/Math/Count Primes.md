# Count Primes
Given an integer n, return the number of prime numbers that are strictly less than n.

Example 1:
Input: 10
Output: 4

**Approach: Sieve of Eratosthenes**

**Key Insight:** Instead of checking each number for primality (expensive), we can efficiently mark all composite numbers by iterating through primes and crossing out their multiples.

**Algorithm:**

1.  Create boolean array `is_prime[0..n-1]`, initialize all to True
    
2.  Mark 0 and 1 as not prime
    
3.  For each number i from 2 to √n:
    
    -   If i is still marked prime, mark all multiples of i as not prime
        
    -   Start from i² (smaller multiples already marked by smaller primes)
        
4.  Count remaining True values
    

**Why Start from i²?** For prime p, multiples p×2, p×3, ... p×(p-1) are already marked by smaller primes (2, 3, ..., p-1).

**Example:** n = 10

`Initial: [F,F,T,T,T,T,T,T,T,T] (0,1 marked False) i=2: [F,F,T,T,F,T,F,T,F,T] (4,6,8 marked) i=3: [F,F,T,T,F,T,F,T,F,F] (9 marked) Result: Primes are 2,3,5,7 → count = 4`

**Time Complexity:** O(n log log n) - each composite marked only once **Space Complexity:** O(n) - boolean array of size n

**Time: O(n log log n)**

**Space: O(n)**

```
class Solution {
    public int countPrimes(int n) {
        if (n < 2) return 0;

        boolean[] isPrime = new boolean[n];
        Arrays.fill(isPrime, true);
        isPrime[0] = isPrime[1] = false;

        for (int i = 2; i * i < n; i++) {
            if (isPrime[i]) {
                for (int j = i * i; j < n; j += i) {
                    isPrime[j] = false;
                }
            }
        }

        int count = 0;
        for (boolean p : isPrime) if (p) count++;
        return count;
    }
}

# Time Based Key-Value Store
Design a time-based key-value data structure that can store multiple values for the same key at different time stamps and retrieve the key's value at a certain timestamp.

Example 1:
Input: {"commands":["TimeMap","set","get","get","set","get","get"],"values":[[],["foo","bar",1],["foo",1],["foo",3],["foo","bar2",4],["foo",4],["foo",5]]}
Output: [null,null,"bar","bar",null,"bar2","bar2"]

**Approach: Hash Map + Binary Search**

**Key Insight:** Timestamps are strictly increasing, so store (timestamp, value) pairs in insertion order. Use binary search to find the largest timestamp ≤ query.

**Algorithm:** Set: Append (timestamp, value) to key's list Get:

1.  Get list for key
    
2.  Binary search for largest timestamp ≤ query
    
3.  Return corresponding value, or "" if not found
    

**Example:**

-   set("foo", "bar", 1)
    
-   set("foo", "bar2", 4)
    
-   store\["foo"\] = \[(1, "bar"), (4, "bar2")\]
    
-   get("foo", 3): binary search finds timestamp 1 → "bar"
    
-   get("foo", 5): binary search finds timestamp 4 → "bar2"
    

**Time Complexity:** O(1) set, O(log n) get **Space Complexity:** O(n) - all values stored

**Time: O(log n) get, O(1) set**

**Space: O(n)**

```
class TimeMap {
    Map<String, List<String[]>> store; // [value, timestamp]

    public TimeMap() {
        store = new HashMap<>();
    }
    
    public void set(String key, String value, int timestamp) {
        if (!store.containsKey(key)) {
            store.put(key, new ArrayList<>());
        }
        store.get(key).add(new String[]{value, String.valueOf(timestamp)});
    }
    
    public String get(String key, int timestamp) {
        if (!store.containsKey(key)) return "";
        
        List<String[]> list = store.get(key);
        return search(list, timestamp);
    }
    
    private String search(List<String[]> list, int timestamp) {
        int l = 0, r = list.size() - 1;
        String res = "";
        while (l <= r) {
            int m = l + (r - l) / 2;
            if (Integer.parseInt(list.get(m)[1]) <= timestamp) {
                res = list.get(m)[0];
                l = m + 1;
            } else {
                r = m - 1;
            }
        }
        return res;
    }
}
```

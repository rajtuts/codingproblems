# Design Twitter
Design a simplified version of Twitter where users can post tweets, follow/unfollow another user, and see the 10 most recent tweets in their news feed. Implement the Twitter class with postTweet, getNewsFeed, follow, and unfollow methods.

Example 1:
Input: {"commands":["Twitter","postTweet","getNewsFeed","follow","postTweet","getNewsFeed","unfollow","getNewsFeed"],"values":[[],[1,5],[1],[1,2],[2,6],[1],[1,2],[1]]}
Output: [null,null,[5],null,null,[6,5],null,[5]]

**Approach: Hash Maps + Max Heap**

**Key Insight:** Store tweets with timestamps for ordering. Use sets for follow relationships. For news feed, collect tweets from user and followees, use a max heap to get the 10 most recent.

**Data Structures:**

-   tweets: {userId → \[(timestamp, tweetId), ...\]}
    
-   follows: {userId → set of followeeIds}
    
-   time: global counter for ordering
    

**Operations:**

1.  postTweet: append (time, tweetId) to user's list, increment time
    
2.  follow: add followeeId to follower's set
    
3.  unfollow: remove followeeId from follower's set (discard if absent)
    
4.  getNewsFeed:
    
    -   Collect all tweets from user + followees
        
    -   Push to max heap with (-timestamp, tweetId)
        
    -   Pop top 10
        

**Example:**

-   postTweet(1, 5): tweets\[1\] = \[(0, 5)\]
    
-   follow(1, 2)
    
-   postTweet(2, 6): tweets\[2\] = \[(1, 6)\]
    
-   getNewsFeed(1): tweets from user 1 and followee 2
    
    -   heap: \[(−1, 6), (−0, 5)\]
        
    -   pop 2: \[6, 5\] ✓
        

**Time Complexity:**

-   postTweet: O(1)
    
-   follow/unfollow: O(1)
    
-   getNewsFeed: O(n log n) where n = total tweets from user + followees
    

**Space Complexity:** O(n) - storing all tweets and relationships

**Time: O(n log n) for getNewsFeed**

**Space: O(n)**

```
class Twitter {
    private int time = 0;
    private Map<Integer, List<int[]>> tweets = new HashMap<>();
    private Map<Integer, Set<Integer>> follows = new HashMap<>();

    public void postTweet(int userId, int tweetId) {
        tweets.computeIfAbsent(userId, k -> new ArrayList<>()).add(new int[]{time++, tweetId});
    }

    public List<Integer> getNewsFeed(int userId) {
        Set<Integer> users = new HashSet<>();
        users.add(userId);
        if (follows.containsKey(userId)) users.addAll(follows.get(userId));

        PriorityQueue<int[]> pq = new PriorityQueue<>((a, b) -> b[0] - a[0]);
        for (int uid : users) {
            if (tweets.containsKey(uid)) {
                for (int[] tweet : tweets.get(uid)) pq.offer(tweet);
            }
        }

        List<Integer> result = new ArrayList<>();
        while (!pq.isEmpty() && result.size() < 10) {
            result.add(pq.poll()[1]);
        }
        return result;
    }

    public void follow(int followerId, int followeeId) {
        follows.computeIfAbsent(followerId, k -> new HashSet<>()).add(followeeId);
    }

    public void unfollow(int followerId, int followeeId) {
        if (follows.containsKey(followerId)) follows.get(followerId).remove(followeeId);
    }
}
```

package com.jpa.relationshipsdemo;

import java.util.*;


public class Test {
    public static void main(String[] args) {

    }

    public static int[] maxSlidingWindow(int[] scores, int k) {
        int n = scores.length;
        int[] result = new int[n-k+1];
        Deque<Integer> deque = new ArrayDeque<>();
        for (int i=0;i<n;i++) {

        }
    }
}
/*Given your experience optimizing high-volume data processing, consider a stream of customer
feedback scores coming from a Kafka topic.
Write a Java method to find the maximum feedback score in every sliding window of size k
within a given array of scores. You must optimize this to run in time complexity.
Example:
scores = [1, 3, -1, -3, 5, 3, 6, 7], k = 3
Output: [3, 3, 5, 5, 6, 7]

 */
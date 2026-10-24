package com.jpa.relationshipsdemo;

import org.apache.tomcat.util.http.parser.Priority;

import java.util.Collections;
import java.util.PriorityQueue;


public class Practice2 {
    public static void main(String[] args) {
        System.out.println(minSum(new int[]{10, 20, 7},  4));
        System.out.println(minSum(new int[]{2, 3},  1));
    }
    public static int minSum(int[] nums, int k) {

        PriorityQueue<Double> maxHeap = new PriorityQueue<>(Collections.reverseOrder());

        for(int num: nums) {
            maxHeap.add((double)num);
        }

        for(int i=0;i<k;i++) {
            double maxVal = maxHeap.poll();
            double reduce = Math.ceil(maxVal/2);
            maxHeap.add(reduce);
        }
        int total = 0;
        while(!maxHeap.isEmpty()) {
            total+= maxHeap.poll();
        }
        return total;
    }
}

    /*
Given an array of integers nums, perform k operations to minimize the sum of
the elements. Each operation consists of:
Removing an element from the array
Dividing it by 2
Inserting the ceiling of the result back into the array
Return the minimum possible sum after k operations.
Example 1
Input: n = 3 elements, nums = [10, 20, 7], k = 4

Output: 14
Explanation:
The ceiling of 7/2 = 4 → [10, 20, 4]
The ceiling of 10/2 = 5 → [5, 20, 4]
The ceiling of 20/2 = 10 and 10/2 = 5→ [5, 5, 4]
The sum of the final array is 5 + 5 + 4 = 14.
Example 2
Input: n = 2 elements, nums = [2, 3], k = 1
Output: 4
Explanation: Whether we choose 2 or 3, the answer is the same.
The ceiling of 2/2 = 1, 1 + 3 = 4
The ceiling of 3/2 = 2, 2 + 2 = 4
*/
/**
 * public class Test {
 *     public static void main(String[] args) {
 *         System.out.println(subArray(new int[]{12, 5, 31, 9, 21, 8}, 45));
 *     }
 *
 *     public static List<Integer> subArray(int[] input, int target) {
 *         int sum = 0;
 *         List<Integer> resultlst = new ArrayList<>();
 *         for (int i = 0; i < input.length - 1; i++) {
 *             resultlst = new ArrayList<>();
 *             resultlst.add(input[i]);
 *             sum = input[i];
 *             if (sum == target) {
 *                 return resultlst;
 *             }
 *             for (int j = i+1; j < input.length - 1; j++) {
 *                 sum = sum + input[j];
 *                 System.out.println("result:"+resultlst);
 *                 resultlst.add(input[j]);
 *                 if (sum == target) {
 *                     return resultlst;
 *                 }
 *             }
 *         }
 *         return resultlst;
 *     }
 * }
 * /*Write a Java program to find continuous sub array of the given array whose sum is equal to a given number.
 *         For example, If {12, 5, 31, 9, 21, 8} is the given array and 45 is the given number,
 * then you have to find continuous sub array in this array such that whose elements add up to 45.
 * In this case, {5, 31, 9} is such sub array whose elements add up to 45*/

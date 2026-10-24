package com.jpa.relationshipsdemo;

import java.util.*;
import java.util.stream.Collectors;

public class Practice {

    public static void main(String[] args) {
/*
        lst.stream()
                .collect(Collectors.groupingBy(
                        Emp::getDept,
                        e -> { Collectors.avaragingDouble(e.getSalary) }
                ));
        ;*/
    }
   // "abcabcbb"
   /* private static String maxSubstring(String s) {
        int maxLen=0, left=0; StringBuilder sb = new StringBuilder();
        Map<Character, String> substring = new HashMap<>();
        for(int i=0;i<s.length();i++){
            char c = s.charAt(i);
            if(substring.containsKey(c)) {
                String substr = substring.get(c);

                substring.put(c,substring.put()  s.substring());
            }
            substring.put(c,i);
            maxLen= s.substring(substring.get(c),i).length();

        }

        return sb.toString();
    }*/



}
/*
Given a string s, find the length of the longest substring without duplicate characters.
find dept with highest avg sal of employee.?
 */
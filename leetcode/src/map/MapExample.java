package map;

import java.util.HashMap;
import java.util.Map;

public class MapExample {
    public static void main(String[] args) {
        // Create a Map of items and their stock counts
        Map<String, Integer> stock = new HashMap<>();
        stock.put("Apples", 50);
        stock.put("Bananas", 20);

        // 1. Key exists: returns the actual value (50)
        int appleStock = stock.getOrDefault("Apples", 0);
        System.out.println("Apple stock: " + appleStock);

        // 2. Key does not exist: returns the fallback default value (0)
        int orangeStock = stock.getOrDefault("Oranges", 0);
        System.out.println("Orange stock: " + orangeStock);
    }
}


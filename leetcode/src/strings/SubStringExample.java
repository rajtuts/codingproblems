package strings;

public class SubStringExample {
    public static void main(String[] args) {
        String text = "JavaProgramming";

        // Variant 1: From a specific index to the end of the string
        // Starts at index 4 ('P') and goes all the way to the end
        String sub1 = text.substring(4);
        System.out.println("Substring 1: " + sub1); // Output: Programming

        // Variant 2: Between a start and end index
        // Starts at index 0 ('J') and stops before index 4 ('P')
        String sub2 = text.substring(0, 4);
        System.out.println("Substring 2: " + sub2); // Output: Java
    }
}

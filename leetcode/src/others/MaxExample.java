package others;

public class MaxExample {
    public static void main(String[] args) {
        // 1. Displaying the built-in limits
        System.out.println("Java Int Max Limit: " + Integer.MAX_VALUE);
        System.out.println("Java Int Min Limit: " + Integer.MIN_VALUE);
        System.out.println("----------------------------------------");
        System.out.println(Math.min(Double.MIN_VALUE, 0.0d));

        System.out.println(Integer.MIN_VALUE - 1);

        System.out.println(Integer.MAX_VALUE + 1);

        int x = Integer.MIN_VALUE;
        System.out.println(Math.abs(x));

        System.out.println(add(1,Integer.MIN_VALUE));
        System.out.println("----------------------------------------");
        // 2. Finding the largest number in an array
        int[] numbers = {-15, -42, -3, -8, -22};

        // Initialize to the lowest possible integer value
        // This ensures that any number in our array will be larger than 'highest'
        int highest = Integer.MIN_VALUE;

        for (int num : numbers) {
            // Math.max compares the current 'highest' with the current array element
            // and returns the larger of the two
            highest = Math.max(highest, num);
        }

        System.out.println("The largest number in the array is: " + highest);
    }

    /*public static int add(int a, int b) {
        return a - (-b);
    }*/
    public static int add(int a, int b) {
        while (b != 0) {
            int sum = a ^ b;
            int carry = (a & b) << 1;

            a = sum;
            b = carry;
        }
        return a;
    }

}

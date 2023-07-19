class Fibonacci {

    public static int fibonacci(int n) {

        if (n <= 1) {
            return 0;
        }

        return fibonacci(n - 1) + fibonacci(n - 2);

    }

    public static void main(String[] args) {

        int length = 100;
        int[] fibonacci_numbers = new int[length];


        fibonacci_numbers[0] = 0;
        fibonacci_numbers[1] = 0;

        for (int i = 2; i < length; i++) {
            fibonacci_numbers[i] = fibonacci_numbers[i - 1] + fibonacci_numbers[i - 2];
        }

        for (int i = 0; i < length; i++) {
            System.out.println(fibonacci_numbers[i]);
        }

    }

}
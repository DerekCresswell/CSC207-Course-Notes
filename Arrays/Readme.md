# CSC207 Course Notes - Interfaces

The array is a key component in almost every programming langauge and Java is no exception!
Here, we will quickly discuss some of the basics of using arrays in Java.

## Arrays

Arrays allow us to store multiple values of the same type.
Unlike in Python, arrays in Java have a constant length.
That is, once we declare the length of an array, it cannot change.
Later, we will see how we can overcome this issue with ease.

Let's use a simple example of generating the front of a mathematical sequence to demonstrate arrays.
We will try to write some code that fills an array with the [Fibonacci Numbers](https://en.wikipedia.org/wiki/Fibonacci_sequence).

#### Fibonacci Numbers

The Fibonacci Numbers is a simple sequence of numbers defined with a recursive formula.
We denote the nth Fibonacci Number as `F(n)`.
Then, they are defined as:

* `F(0) = F(1) = 0`
* For `n > 1`, `F(n) = F(n - 1) + F(n - 2)`

##### Calculating Fibonacci Numbers

Of course, we need to be able to calculate these numbers.
We can do so with a simple Java function defined as follows:

```java
int fibonacci(int n) {

    if (n <= 1) {
        return 0;
    }

    return fibonacci(n - 1) + fibonacci(n - 2);

}
```

This requires more runtime the larger `n` becomes.
Because of this, we will actually use a different method to calculate our values that avoids recomputations.

### Array Creation

Now, we want to create our array of numbers.
We obviously need an array to start with!
For this, we need to declare an array of integers.
Since we must also have a constant length, we must choose a length.
Our code for this will look like:

```java
int length = 100;
int[] fibonacci_numbers = new int[length];
```

This will declare `fibonacci_numbers` to be an array of integers.
Further, the `new int[length]` specifies how long this array should actually be (`length` in our case).
The values contained within this array however have not been initialized (ie the array potentially contains garbage).

### Filling Our Array

Now, we may begin to compute the fibonacci numbers within this array.
To start, we will get our base-cases out of the way directly:

```java
fibonacci_numbers[0] = 0;
fibonacci_numbers[1] = 0;
```

These set the first and second values of our array to zero.
Now, we can begin to compute the rest of our array.
As said above, we wish to avoid recomputation, so we will leverage the previous array elements to calculate our new ones.
Like so:

```java
for (int i = 2; i < length; i++) {
    fibonacci_numbers[i] = fibonacci_numbers[i - 1] + fibonacci_numbers[i - 2];
}
```

Here, we are safe to access the `i - 1` and `i - 2` array elements because:

* `i` starts at a value of 2 and so we never end up with a negative index

> Note: Negative indexing is not a thing in Java; `x[-1]` will error.

* When we calculate the `i`th number, we have already calculated and stored all previous values in the sequence.

This is all we need in order to calculate `length` fibonacci numbers.

### Printing Our Array.

To verify this is correct, we can easily print our entire array:

```java
for (int i = 0; i < length; i++) {
    System.out.println(fibonacci_numbers[i]);
}
```

You can even use the explicit [fibonacci function](#calculating-fibonacci-numbers) shown above to verify that our array correctly contains each fibonacci number.

Try running this code in a [scratch file](../Scratch.java) and see the results.

> Note: Arrays also have a `.length` attribute (ie. `fibonacci_numbers.length`).

### Array Initialization

Sometimes, you might need to make a small array and will know the exact values it will hold when you create it.
For this, there is a simpler way to create an array.

Perhaps we want to have a simple mapping of exit codes to a text description of them.
Here, we could use an array like so:

```java
// Assumes exit_code is a valid code (ie. 0, 1, or 2).
void print_exit_code(int exit_code) {
    String[] exit_codes = {"OK", "WARNING", "ERROR"};

    System.out.println(exit_codes[exit_code]);
}
```

Here, Java is able to infer the length of our array when running our code.

### Nested Arrays

Java arrays may hold any type of values, including other arrays.
Using this, we may set up data structures like matrices with ease.

```java
// A 4 by 4 matrix.
float[][] square_matrix = new float[4][4];

// Set a few values within the matrix.
square_matrix[0][0] = 2.0; // The top-left element.
square_matrix[3][3] = 1.5; // The bottom-right element.
square_matrix[0][3] = 3.14; // The top-right element.
```

We can also do this with varying lengths per row, but you will need to be more careful when accessing values as to not go out of bounds.

```java
// A 2x? matrix.
float[][] odd_matrix = new float[2][];

odd_matrix[0] = new float[4];
odd_matrix[1] = new float[8];
```

## ArrayList

When using plain arrays in Java we do need explictly declare the lengths.
This can make it difficult to add new elements into our arrays.
If we wanted to expand our array, we would need to first create a new longer array and then copy all of our existing data into the new array.
After that, we could add our new element.
Of course, once we use up all the space within that array we would need to repeat this process.

Luckily for us, Java has a solution already built into its libraries.
This is the [`ArrayList`](https://docs.oracle.com/javase/8/docs/api/java/util/ArrayList.html).
The `ArrayList` is more akin to the `List` in Python than Java's arrays.
We will talk more about this when we discuss [interfaces](../interfaces).
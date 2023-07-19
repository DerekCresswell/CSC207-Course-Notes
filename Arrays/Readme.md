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

### Array Initialization

### Nested Arrays

## ArrayList
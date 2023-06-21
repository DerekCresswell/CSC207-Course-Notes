# CSC207 Course Notes - Fizz Buzz

Let's start off our exploration of Java with a classic programming challenge, Fizz Buzz!

## Fizz Buzz

If you have not heard of this, Fizz Buzz is a game where we must create different outputs based on a number we continually increment.
Considering the current number is `i`, we output the following:

* If `i` is divisible by 3, print 'Fizz'
* If `i` is divisible by 5, print 'Buzz'
* If `i` is divisibly by 3 and 5, print 'Fizz Buzz'
* Otherwise, print the value of `i`

So, starting with `i = 1` and counting until `i = 6` we would get:

```
1
2
Fizz
4
Buzz
Fizz
```

Now, we want to create a program to do this for us.
We could write it in Python (and encourage that you quickly try this if you have never before) but, we will present the program written in Java and dissect it.
Fizz Buzz, implemented in Java may look like:

```java
class FizzBuzz {

    public static void main(String[] args) {

        for (int i = 1; i < 100; i++) {

            // Find out which numbers divide i.
            boolean divisible_by_3 = i % 3 == 0;
            boolean divisible_by_5 = i % 5 == 0;

            // Print our appropriate result.
            if (divisible_by_3 && divisible_by_5) {

                System.out.println("Fizz Buzz");

            } else if (divisible_by_3) {

                System.out.println("Fizz");

            } else if (divisible_by_5) {

                System.out.println("Buzz");

            } else {

                System.out.println(i);

            }

        }

    }

}
```

This program (which you should run for yourself) prints out the first 100 numbers of Fizz Buzz!
Now, you may never have seen Java code before but still, just by reading over what is written here, you may be able to get a feel for how Java code works.
Take a minute and read this through and take guesses at what different pieces of the code do.
As well, if you run the code for yourself, perhaps try to modify this program and see what different results you can get.

### The Basic Blocks of This Program

Now, let's pick apart some of this code to get a bit of a better understanding of what is going on.
As said, most of this should feel similar to Python even if the syntax is different.

#### The Main Function

In Python, any code that you write in a file will get run when you execute the file.
This is not the case in Java.
We must instead explicitly tell java where to start running our code from.
This is what the main function is for!

```java
public static void main(String[] args)
```

This function denotes the entry point of your program and will be run when you try to run the containing Java file.
We will talk a bit more later about what `public` and `static` mean.
For now, we will talk about `void` and `String[] args`.

Just like in Python, Java functions can return values.
If a function does not return a value, it has it's return type set to `void`.
This return type is placed directly in front of the function's name.

`String[] args` is a parameter to the function whose name is `args`.
The `String[]` denotes the type that `args` must be when called which in this case is a list (the `[]` suffix) of strings!
Again, this is similar to Python except that the type of the parameter must always be stated (more on this later).

Lucky for us, Java takes care of calling this function with the correct arguments for us!

#### The FizzBuzz Class

...

#### Braces and Brackets

...

### Extending Fizz Buzz

...

## Other Simple Examples
...
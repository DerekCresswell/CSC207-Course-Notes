# CSC207 Course Notes - Strings

[Strings](https://docs.oracle.com/javase/8/docs/api/java/lang/String.html) are a fundamental data type for Java programming and are implemented in the standard library for us.
Here, we will learn some of the basic operations to let us work with strings.

## Strings

Just as you would expect from experience in Python, Java strings are a sequence of characters.
We can create strings using the `String` type like so:

```java
String myString = "Hello World";
```

Notice that we surround our string literal with double quotes (`"`).
Unlike Python, in Java single quotes (`'`) are only for characters and strings must be enclosed in double quotes.

There is also one other way to create a string which is slightly different and we will discuss shortly:

```java
String myString = new String("Hello World");
```

We will go over some common operations on strings within Java in order to do some basic data parsing.

### Reference and Primitive Types

First, we must discuss the difference between a reference type and a primitive type.

In Java, there are many built-in types which are called [primitives](https://docs.oracle.com/javase/tutorial/java/nutsandbolts/datatypes.html).
These primitives might be what you expect, for instance all of:

* `int`
* `boolean`
* `float`
* `char`

are primitive types.
In fact, all, and only, primitive types start with a lowercase letter.

To us, a primitive just means that the variable directly holds its value.
Consider the variable `int i = 0;`.
Since `int` is primitive, `i` represents a memory location that has the value `0`.

In contrast, a reference type is one where the value of the variable is held somewhere in memory and the variable itself holds the address of that memory.
Consider `myString` from above.
In Java, `String` is a reference type so the variable `myString` does __not__ hold the value `"Hello World"` but rather a memory address.
If you look at that address in memory, you would see the actual string data (`"Hello World"`).

This has some consequences when it comes to comparing string objects.

Consider the following code block (which you can run for yourself in a scratch file):

```java
String s1 = "hello";
String s2 = "hello";

System.out.println(s1 == s2);

String s3 = new String("hello");
String s4 = new String("hello");

System.out.println(s3 == s4);
```

Here, we can see that `s1` and `s2` are pointing to the same object (the same place in memory).
Whereas, `s3` and `s4` are not pointing to the same object (though both string objects have the same value).

If you want to compare the value of objects (it's internal data) and not the object's address you must use the `equals` method like so:

```java
s3.equals(s4);
```

### Immutability

Within Java, strings are immutable objects.
This just means that once we have created a string we are not able to edit it in memory.
Instead, when we do 'mutate' a string we are actually just creating a new string in memory and copying over the previous data.

We can see this occur in the following example:

```java
String s5 = s2;
s2 = "bye";
System.out.println(s2 == s5);
System.out.println(s2.equals(s5));
```

In the first line, `s5` points to the string object which `s2` points to.
In the second line we create a new string object and change `s2` to point to that object.
Thus, `s2` and `s5` no longer have the same value nor point to the same object.

### Common Operations

Now, we may go over a few basic operations on strings.
We will use these to create a simple data parser for comma separated values (csv).

That is, given a string like `"a,b,c,d"`, we can extract the values between commons.

#### Indexing

First, we may want to extract a single character of a string.
In Python, we would achieve this using indexing just like an array / list.
In Java, we have the same idea of indexing a string however we must use a method, [`charAt`](https://docs.oracle.com/javase/8/docs/api/java/lang/String.html#charAt-int-).

Try out the following code:

```java
String s = "hello";
System.out.println(s.charAt(0));
System.out.println(s.charAt(4));
```

Note that indices cannot be negative nor exceed the length of the string (zero-indexed).

#### Searching

We can search strings using the [`contains`](https://docs.oracle.com/javase/8/docs/api/java/lang/String.html#contains-java.lang.CharSequence-) method.

```java
System.out.println(s.contains("e"));
System.out.println(s.contains("ell"));
System.out.println(s.contains("boo"));
```

This will tell you whether a string contains a given string.
However, a string may contain a string multiple times.
In this case, it can be important to find the index at which the string occurs.
For this, we have the [`indexOf`](https://docs.oracle.com/javase/8/docs/api/java/lang/String.html#indexOf-java.lang.String-) method (and a related [`lastIndexOf`](https://docs.oracle.com/javase/8/docs/api/java/lang/String.html#lastIndexOf-java.lang.String-) method).
This method will return `-1` if the substring is not found.

```java
System.out.println(s.indexOf("e"));
System.out.println(s.indexOf("l"));
System.out.println(s.indexOf("b"));
```

#### Substrings

We can extract substrings from a string using the aptly named [`substring`](https://docs.oracle.com/javase/8/docs/api/java/lang/String.html#substring-int-int-) method.
We supply a starting and ending index from which to extract our string.
The end index may be ommitted, in which case we get the rest of the string after the start index returned.

```java
System.out.println(s.substring(2));
System.out.println(s.substring(1, 3));
```

#### Stripping

One more very useful string function is the [`trim`](https://docs.oracle.com/javase/8/docs/api/java/lang/String.html#trim--) method.
This removes leading and trailing whitespaces from a string.

```java
String whiteSpace = "    abc     ";
System.out.println(whiteSpace.trim());
```

### CSV Parsing

With this basic arsenal of methods, we can see about writing a method which will extract values from a string of comma separated values.
Consider our data to be the string `"a,b,c,d"`.

Below we have the code which will print each value in this (or any) comma separated string.

```java
String data = "a,b,c,d";

int lastComma = 0;
while (true) {

    // Find the position of the next comma (relative to the last).
    int nextComma = data.substring(lastComma).indexOf(",");

    if (nextComma == -1) {

        // There is one value left.
        String lastValue = data.substring(lastComma);
        System.out.println(lastValue);

        break; // There are no more values.

    }

    // Since nextComma is relative to lastComma, we must add them.
    String value = data.substring(lastComma, lastComma + nextComma);

    System.out.println(value);

    // We add one because there is one extra character (the comma we found).
    lastComma = nextComma + 1;

}

```

> Note: This is not the only way we can achieve this. Here we are illustrating the functions we've shown you.
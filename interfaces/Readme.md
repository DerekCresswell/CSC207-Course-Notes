# CSC207 Course Notes - Interfaces

A key part of Java's class design are interfaces.
Interfaces act as contracts between classes.
That is, an interface specifies a behaviour that a class must satisfy (in terms of function signatures) without revealing anything about the underlying class.
In this way, we are able to swap out different implementations of the same interface without needing to change any code that utilises the interface.

We will illustrate how this works by profiling on different implementations of a list and compare their performance.
After, we will have you write a basic extension on an existing list class.

## Interfaces

In Java, an interface looks much like a class except that we do not have any member variables nor do we define the bodies of methods.
This is because an interface is meant to describe a behaviour but not tell you _how_ that behaviour is achieved.
What this allows is for many classes to implement the same interface but use very different code behind the scenes.
The person using the interfaces doesn't know what the class is doing; only that is _has_ the methods defined in the interface.

Let's look at a simple example to get a feel for this.

### Notifier Example

Consider that we have a code base where we wish to send users notifications.
There are many different ways that we could send a user a notification such as through email or text message.
So, we will define an interface, called `Notifier`, which contains a `send` method.

```java
interface Notifier {

    /**
     * Forwards message to the specified user.
     *
     * Returns whether the message was sent successfully.
     */
    boolean send(User user, String message);

}
```

_Note_: We will not define the `User` class, or many of the details of these classes.
You should be able to understand what they do regardless.

That's all the interface is!
There is no details on how we send the data.
There is also no data stored in the interface.
It's just the set of methods we need.

Now, we want to write our text and email notifiers so that they inherit this interface.
Let's start with the text version of the class.
We may have something like this:

```java
// Sends messages via SMS text messages.
class TextNotifier implements Notifier {

    // ... Constructors, data, and other necessities here.

    @Override
    public boolean send(User user, String message) {

        // First, we check that our user has a phone number.
        if (!user.hasPhoneNumber()) {
            return false; // We cannot text this user.
        }

        int number = user.getPhoneNumber();

        // Now use our SMS service to send the message.
        if (!this.sms_service.isActive()) {
            return false; // Our SMS is offline.
        }

        this.sms_service.sendText(number, message);

        return true;

    }

}
```

Omitting many details about this class, we see that it implements the `Notifier` interface.
This means that it is required to define a `send` function with the same signature as the `Notifier` interface.
Here, we see that we must check whether users have a phone number.
We then check that our SMS service is actually able to send a text.
Finally, once these checks pass, we can send out our message.

Now, let's see how the email notifier might be implemented!

```java
// Sends messages via email.
class EmailNotifier implements Notifier {

    // ... Constructors, data, and other necessities here.

    @Override
    public boolean send(User user, String message) {

        String email = user.getEmail();
        String from_email = "abc@example.com";

        // We must format the email as HTML in order to be sent.
        formatted = this.formatMessage(message);

        this.email_service.sendEmail(email, from_email, formatted)

        return true;

    }

}
```

Again, this class implements the `Notifier` and it's `send` method.
The `EmailNotifier` and `TextNotifier` go about the actual details of sending an message differently.
Notice the `EmailNotifier` must format it's message (details omitted) and gather the necessary email addresses.
Whereas the `TextNotifier` had to do additional verification to our user before sending.

Now, why do we want this?
We can see why this interface is useful by looking at how you, a developer, might leverage this.
Consider the following usage of the `Notifier` interface where we try to send welcome messages to new users:

```java

class Welcomer {

    void sendWelcomeMessage(User user, Notifier notifier) {

        notifier.send(user, "Welcome to our site!");

    }

}

```

Albeit simple, this illustrates that now our `Welcomer` class (and any other class that must notify users) can do so using only the `Notifier` interface.
That is, we can do both of the following simultaneously:

```java
EmailNotifier emailer = EmailNotifier();
TextNotifier texter = TextNotifier();
Welcomer welcomer = Welcomer();

welcomer.sendWelcomeMessage(user_1, emailer);
welcomer.sendWelcomeMessage(user_2, texter);
```

The first user will receive an email welcome and the second will receive a text message.
In both cases, the `Welcomer` does not know which implementation of the `Notifier` it is using.
This means that the `Welcomer` does not _care_ about how someone is notified, just that they can be notified.

## The List Interface

Now we will take a look at a very common interface within Java projects.
In Python, you have the `List` class that you'd use for most of your data.
In Java, we instead have a `List` interface.
We can take a look at the [documentation for this interface](https://docs.oracle.com/javase/8/docs/api/java/util/List.html) to get a sense of what lists must be able to do.

From this page, we first see that the `List` interface itself extends another interface, the `Collection` interface.

```java
public interface List<E> extends Collection<E>
```

A `Collection` is just as it sounds, a collection of objects.
`Lists` differentiate themselves by having a specific order of elements.

### Generics

We also see the `<E>` specified after the interface's name.
This is a generic.
Since we have static types in Java, we must declare what type of data a list holds.
The generic allows us to write a single implementation of our list that we can later pass in different types to.
That is, `List<Integer>` is a list of integers and `List<String>` is a list of strings but both get handled by the same implementation of our list.
We will get a little more familiar with this later.
For now, we can just think of it as another argument to our methods but, rather than data, this argument is a type!

### Existing List Implementations

The standard Java libraries already define a few versions of lists that we can use.
Looking at the [docs](https://docs.oracle.com/javase/8/docs/api/java/util/List.html) again, we can see under 'All Known Implementing Classes' the classes which implement this interface.
These include:
* [ArrayList](https://docs.oracle.com/javase/8/docs/api/java/util/ArrayList.html)
* [LinkedList](https://docs.oracle.com/javase/8/docs/api/java/util/LinkedList.html)
* [Vector](https://docs.oracle.com/javase/8/docs/api/java/util/Vector.html)

Since each of these implement the `List` interface, we may use them interchangeably.
However, since each has different internals, we may be able to see different results from using each of them.
For this, we are going to set up a basic profiler to compare the speed of these lists against each other.

This profiler will time how long it takes to create, increment, search, and remove elements from lists of integers.
We will write it using the `List` interface so that we may pass in any implementation without modifying the profiler itself.

## A List Profiler

To start, we will make a profiler class and a method for timing a list:

```java
class Profiler {

    /**
     * Prints out the time it takes for a list to perform a given set of operations.
     */
    public static void time_list(List<Integer> list, String name, int length) {

        System.out.printf("Timing %s:\n", name);

    }

}
```

This function, `time_list`, will be our workhorse.
Firstly, the arguments.
We take in a `List<Integer>` that we will be profiling (note this is the interface, not a class. As well, we explicitly require that the list contains integers).
As well we take in a name (for printing out information) and a length that we wish to make the list.

Now, the first thing we need to do is fill our list with data.
To do this, we will append each integer from 0 to `length - 1` to our list, like so:

```java
for (int i = 0; i < length; i++) {
    list.add(i); // add will insert i at the end of the list.
}
```

However, we need to time this.
The easiest method for this is to use `System.currentTimeMillis()` which simply returns the current time as a `long` (a very big integer).
The documentation for this function can be found [here](https://docs.oracle.com/javase/8/docs/api/java/lang/System.html#currentTimeMillis--).
So, to time our function, all we must do is grab the current time before and after running our code.
We can then take the difference of these times to find the actual elapsed time.

```java
long start_time = System.currentTimeMillis();

for (int i = 0; i < length; i++) {
    list.add(i);
}

long end_time = System.currentTimeMillis();
System.out.printf("\tFilling List: %dms\n", end_time - start_time);
```

Now, we can go through our list again and try to increment every element.
This is done in much the same way as before, except we need to both `get` an element from our list and then `set` it back after incrementing.

```java
for (int i = 0; i < length; i++) {

    // Retrieve the value.
    int value = list.get(i);

    // Now set it back into the list.
    list.set(i, value + 1);

}
```

Just as above, we will time this part of the function by calling `currentTimeMillis` before and after our code runs.

Our next step is to see how long it takes to search these lists.
We will only look for a few items within the list to get a sense of the performance.
For this, we can use the `contains` method of the `List` interface which will return a `boolean` based on whether the list contains our item.
Let's look for a few elements near the start and the end of the list.

```java
boolean contains_1 = list.contains(1);
boolean contains_5 = list.contains(5);
boolean contains_length = list.contains(length);
boolean contains_length_add_1 = list.contains(length + 1);
```

Once again, we will time this code, but we will also print out the results of the search.
This big print statement will do just that.
It prints each of our above booleans and the element they related to.

```java
System.out.printf(
    "\t\tFound: 1=%b, 5=%b, %d=%b, %d=%b\n",
    contains_1,
    contains_5,
    length,
    contains_length,
    length + 1,
    contains_length_add_1
);
```

Our last step is to iteratively pop the front of the list off.
This is quite simple, in a loop like before we call `List.remove(0)` which will remove the first element of the list.

```java
for (int i = 0; i < length; i++) {

    list.remove(0);

}
```

Again, we time and print an appropriate message.

Now, looking at our `time_list` method in full we have the following (provided in the [code folder](./code/Profiler.java)):

```java
public static void time_list(List<Integer> list, String name, int length) {

    System.out.printf("Timing %s:\n", name);

    // First, we fill the list with integers.
    long start_time = System.currentTimeMillis();

    for (int i = 0; i < length; i++) {
        list.add(i);
    }

    long end_time = System.currentTimeMillis();
    System.out.printf("\tFilling List: %dms\n", end_time - start_time);

    // Now, we will iterate the list and increment every integer.
    start_time = System.currentTimeMillis();

    for (int i = 0; i < length; i++) {

        // Retrieve the value.
        int value = list.get(i);

        // Now set it back into the list.
        list.set(i, value + 1);

    }

    end_time = System.currentTimeMillis();
    System.out.printf("\tIncrementing List: %dms\n", end_time - start_time);

    // Then, we will check that this list contains certain elements.
    start_time = System.currentTimeMillis();

    boolean contains_1 = list.contains(1);
    boolean contains_5 = list.contains(5);
    boolean contains_length = list.contains(length);
    boolean contains_length_add_1 = list.contains(length + 1);

    end_time = System.currentTimeMillis();
    System.out.printf("\tSearching List: %dms\n", end_time - start_time);
    System.out.printf(
        "\t\tFound: 1=%b, 5=%b, %d=%b, %d=%b\n",
        contains_1,
        contains_5,
        length,
        contains_length,
        length + 1,
        contains_length_add_1
    );

    // Lastly, we will remove the first item until our list is empty.
    start_time = System.currentTimeMillis();

    for (int i = 0; i < length; i++) {

        list.remove(0);

    }

    end_time = System.currentTimeMillis();
    System.out.printf("\tPop Front of List: %dms\n", end_time - start_time);

}
```

Perhaps a bit lengthy, but it does just what we want it to!
Now all we need is a `main` function to drive this code.
We will add this method straight into our `Profiler` class.
We will need to call our `time_list` function with a couple of different list implementations.
Using `ArrayList`, `LinkedList`, and `Vector` we have the following:

```java
public static void main(String[] args) {

    int list_length = 100000;

    time_list(new ArrayList<>(), "ArrayList", list_length);
    time_list(new LinkedList<>(), "LinkedList", list_length);
    time_list(new Vector<>(), "Vector", list_length);

}
```

Go ahead and run this function.
We will get our results printed out for each list.
You might want to play around with the `list_length` variable to see how the results change.
Does anything stand out to you?

### Creating Our Own List

For practice, let's go through implementing our own list.
We won't do anything too fancy and, in fact, we will utilise an existing list implementation for actually dealing with data.
What we want to do is make a talkative list that prints out some details when we do certain things on the list.
To start, let's define our list and have it extend the `ArrayList`.

```java
class TalkativeList<E> extends ArrayList<E> {

}
```

As is, this is perfectly fun (but very boring) extension of the `ArrayList`.
Let's add some dialogue to the `contains` method so we know what we are searching for.
Remember from [above](#notifier-example), we must override the `contains` method to achieve this.
Setting up just the signature of the method we have:

```java
@Override
public boolean contains(Object o) {

}
```

Now, let's notify the user about what we are searching for.

```java
@Override
public boolean contains(Object o) {

    System.out.printf("Looking for %s...\n", o.toString());

}
```

Notice that, since this method takes the generic `Object` type, we must convert it to a `String` so we can safely print it.
We are not done with this method yet because we need to actually search for our object.
Since we are extending the `ArrayList` class, we can actually use their implementation.

#### Super

To do this, we must utilise the `super` keyword in Java.
This acts quite similarly to the Python `super()` method in that it allows us to access our parent's implementation of certain things.
Using this, we can call `contains` on our parent class like so:

```java
@Override
public boolean contains(Object o) {

    System.out.printf("Looking for %s...\n", o.toString());

    boolean contains = super.contains(o);

}
```

And now we should have our answer for whether our list contains `o`.
Before we return this result, let's do a bit of additional printing:

```java
@Override
public boolean contains(Object o) {

    System.out.printf("Looking for %s...\n", o.toString());

    boolean contains = super.contains(o);

    if (contains) {
        System.out.println("I found it!");
    } else {
        System.out.println("No where to be seen.");
    }

    return contains;

}
```

This looks good!
Now we may plug this into our `Profiler`'s main method and run the code again!

```java
time_list(new TalkativeList<>(), "TalkativeList", list_length);
```

We can see that when we run the benchmarks we get additional information printed for our `TalkativeList`.

For the full code, please see the [code folder](./code/Profiler.java).
Copy this into your local environment and run the profiler.
As well, play with different values or series of operations.
Do not hesitate to change and experiment with anything in the file.

### Going Further

Now that we have seen how interfaces work and how we can extend existing classes, we have a challenge for you.
We might like to know what sorts of operations are happening to our lists, perhaps for diagnostic reasons.
What we ask you to do here is to create another class that extends the `List` interface.
For each of the `add`, `get`, `set`, and `contains` method, provide an overridden version that keeps track of the number of calls made to each method.
You will need to have member variables on this list in order to store this information.
As well, you're welcome to extend an existing `List` class to deal with the actual data manipulation.

For instance, this might look something like this in the end:

```java
List<Integer> tracker = new TrackerList<>();

// Various list operations or profiler calls...

tracker.printStats();
// Prints: "We tracked 3 adds, 5 gets, 8 sets, and 13 contains"
```

Then, provide your class with a new method that prints out all this information you have kept track of.
We leave this up to you to attempt, but reference back to this document and code as needed.
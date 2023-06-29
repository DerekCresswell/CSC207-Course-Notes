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

...
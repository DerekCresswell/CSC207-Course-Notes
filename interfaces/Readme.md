# CSC207 Course Notes - Interfaces

A key part of Java's class design are interfaces.
Interfaces act as contracts between classes.
That is, an interface specifies a behaviour that a class must satisfy (in terms of function signatures) without revealing anything about the underlying class.
In this way, we are able to swap out different implementations of the same interface without needing to change any code that utilises the interface.

We will illustrate how this works by profiling on different implementations of a list and compare their performance.
After, we will have you write a basic extension on an existing list class.

TODO: Lists or collections? Or both!

## Interfaces

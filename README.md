# QuantLab tools and technology tutorium.

This project contains exercises to be worked on during the quantlab tutorium. They are checked
by running the tests in src/test/main (if available). While you are working on the exercises, you can of course
include a main method.


## 11/30 First assignment

Today we review git, github and github classroom. As well as how to accept and submit the graded assignments of the course.


## 11/23 Car - Build your own objects

Today we have a more free-style coding session. Please have a look at the interface Car and it's nested interfaces. (Nested interfaces for demonstration, is this a good idea in an actual application?) These are given to you by a client, who wants to buy a Car (i.e. the client wants to execute the main in the class CarShop).

**1)** Please provide at least two implementations of Car, which the shop can sell. For this:
 * You are given a clean slate. Create your package structure and all classes yourself.
 * Try to keep your code clean, choose reasonable levels of abstraction to avoid code duplication and keep your code open to extension.
 * Write unit tests to check your product is functioning correctly.

**2)** Once you are done with 1), you are not satisfied with just building cars. You want to know which one is best, in the sense of getting to a far away point fast. For this please complete the class CarTest, which is blank with the exception of a list of locations. The idea is that you want to get from the first point on the list to the last as quickly as possible, you don't need to visit all points in between. However, the car is only allowed to refuel at the given points. That means, a quicker car moves faster, but may need to take a more indirect route than a slower car with higher range. (The given locations were made with ranges of around 500 on a full tank.)


## 11/16 Value - Dependency injection and generics

(**Note**) Changes to the pom may require an update to your project configuration. Right-click the project in the explorer > Maven > Update Project.

We are looking at the AAD code from the lecture and consider the abstraction via the interfaces Value and ValueDifferentiable. 

The exercise consists of two parts.
 * First, modify the code to fully implement the dependency injection pattern.
 * Further create a ValueDifferentiable implementation that works for arbitrary Value using generics. 

As a **bonus**: Can you convert DifferentiationExperimentHypothenuse into a parametrized test that tests the auto differentiated value against the analytic solution? 


## 11/09 Review

We start off with a review of how to get started and run your code from the IDE, from a main method or as a JUnit test.
Further we take a look at the finmath library and maybe the main take-away of the preceding lecture.

For the exercise itself, you are given an incomplete class, named DerivativePricing, which evaluates calls and puts in the Black-Scholes model via Monte-Carlo simulation. As well as a skeleton PutOption class. Your job is to complete these and run DerivativePricing and DerivativePricingTest.
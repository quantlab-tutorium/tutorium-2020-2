# QuantLab tools and technology tutorium.

This project contains exercises to be worked on during the quantlab tutorium. They are checked
by running the tests in src/test/main (if available). While you are working on the exercises, you can of course
include a main method.



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
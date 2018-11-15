# Project 1 Part 2 - NFA

* Authors: Nathan Steele and Nathan Regner
* Class: CS361
* Semester: Fall 2018

## Overview

This program provides an complete implementation of the NFAInterface interface,
capable of converting itself to an equivalent DFA.

## Compiling and Using

To compile the project, run:

`javac fa/nfa/NFADriver.java`

You can then run the simulator with the following command:

`java fa.nfa.NFADriver ./tests/p2tc0.txt`

Output will be printed to stdout.

## Discussion

For the most part, this project was straightforward. We were quickly able
to develop the NFA class by copy-pasting most of our DFA implementation.
From here, we implemented a simple breadth-first search algorithm in the
toDFA() method.

After getting our initial implementation working, we realized that we were
not using the provided DFA class. Once we swapped our class out, we found
several bugs in our toDFA() implementation. These bugs were caused by the
following differences:

* The provided DFA requires that you add start states after final states. 
  Otherwise, the DFA ends up containing duplicates.

* The DFA class does not prevent you from adding duplicate states, unlike
  our original implementation. We relied on this behavior in our first
  implementation.

Once we tracked down these differences in the provided DFA implementation, 
we were able to update our algorithm to deal with them.

## Testing

For the majority of our testing we relied on the provided test cases, however
we did create several of our own.

To make testing easier, we setup a maven project with JUnit. This allowed us to 
run tests within our IDE and quickly get feedback.

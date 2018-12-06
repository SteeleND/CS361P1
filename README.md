# Project 1 Part 3 - Regular Expressions

* Authors: Nathan Steele and Nathan Regner
* Class: CS361
* Semester: Fall 2018

## Overview

This program constructs an NFA from a regular expression. It then converts
the NFA to a DFA and simulates it on a variety of string inputs.

## Compiling and Using

To compile the project, run:

`javac -cp ".:./CS361FA.jar" re/REDriver.java`

You can then run the simulator with the following command:

`java -cp ".:./CS361FA.jar" re.REDriver ./tests/p3tc1.txt`

Output will be printed to stdout.

## Discussion

We did did not encounter too many issues during our implementation of the RE
class. Mostly, we just had to figure out how to write a recursive descent
parser for the regex. Using the provided resources, we were able to develop 
an initial algorithm that did most of what we wanted. From here, we just had 
to figure out how the provided NFA implementation worked, as it was slightly 
different than our version from P2.


## Testing

For the majority of our testing we relied on the provided test cases, however
we did create several of our own.

To make testing easier, we setup a maven project with JUnit. This allowed us to 
run tests within our IDE and quickly get feedback.

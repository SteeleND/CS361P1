#Project 1: DFA

*Authors: Nathan Steele and Nathan Regner
*Class: CS361
*Semester: Fall 2018

## Overview

Our program uses the fa.dfa package to setup a DFA machine, using a given text file, supplied as a command line argument,
parsing strings from the file to verify they are part of the language, and moving along the machine. 

## Compiling and Using
-Open a console
-Make your way to the directory containing the file.
-Use the following java command to compile the file:
	javac fa/dfa/DFADriver.java
-Use the following java command to run the file, giving command line arguments for the textfile:
	java fa.dfa.DFADriver ./tests/testing.txt

This will then build the state machine, run through each string and then provide an output telling the users the outcome
of the string, including accepted or not.

## Discussion
Overall there was not a lot of challenges once we got a base of what needed to be done, and had the time to look over
the handout a bit. We used the HashMap to manage states, as it needed to, and we implemented this in the DFAState class,
fairly basic implementation of it, nothing really difficult on that part.  
The DFA.java was where the majority of our code was done, managing the functionality that we were going to need, and ensuring
that it would handle the strings that we were testing on, as well as planning for different situations was the most
difficult part. After some general logic building, we were able to move our code in a direction which ended up working out for us.

The majority of errors were ironed out during writing of the classes. We didnt have any real large errors that we ran into
as most of the setup was straight forward after we figured out what direction we wanted to take things. There were a few
simple errors, such as null pointers or small syntax fixs. I(Nathan Steele) dont work in Java regularly enough, and ended up causing
myself a few headaches, just based off syntax problems.

## Testing


For the majority of our testing we relied on the test files provided. Running from our personal computers, then running the final
tests from onyx to ensure cross platform errors didn't occur. We did original test cases from IDE, but as we were wrapping up
we tested everything on Onyx, as stated before, to ensure it was working. 

# General Search Algorithm

Implementation, in Java, of a general search algorithm that provides different strategies by changing the queueing function used to store nodes. 

## How to use it

To use it one needs only to define two classes that extend SearchProblem and Node and implement the abstract methods defined by them. To demonstrate how this is done we use the 8 puzzle problem where we have 8 numbered tiles in a 3x3 grid and want to get from an initial configuration to the target configuration by moving the tiles.

## About

This is a revisit of a project I'd done some years ago for a course called Intelligent Systems. One of the purposes of this assignment was to compare complexities of different strategies, this is why we keep track of statistics such as total and maximum number of nodes and show them in the test along with the time spent.

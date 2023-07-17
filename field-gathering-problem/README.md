# Field Gathering Problem
The Field Gathering Problem is a treasure hunting scenario where the goal is to efficiently gather as many truffles as possible within a limited timeframe. The field is divided into a grid, with each spot on the grid indicating the amount of treasure present.

#### Problem Description
Given a field divided into a grid and limited movement options, the goal is to create a path for the gatherer from the top to the bottom of the field, maximizing the amount of truffles collected along the way. The gatherer can only move in three directions: down, down-left, or down-right. They cannot skip any spots or move backward.

#### Solution Approach
To solve this problem, we can employ the Topological Sort algorithm. The idea is to assign a value to each spot in the grid, representing the maximum amount of truffles that can be collected if the gatherer reaches that spot. We start from the top of the field and iteratively calculate the maximum values for each spot by considering the values of the adjacent spots from the previous row. This process continues until reaching the bottom of the field.

The final result of the Topological Sort will be the best path for the gatherer to follow, maximizing the truffles collected along the way.

#### Usage
To run the demo of the Field Gathering Problem solution, follow these steps:

* Clone the repository to your local machine.
* Open the command-line interface and navigate to the project directory.
* Compile and run the Java program that implements the solution.
* Provide the field grid as input as a .txt file, indicating the amount of treasure in each spot.
* A single tab character should be present before each number (except the first column of numbers), and
  the next row in the field will be represented by a new line in the text file (enter key).
* The program will output the best path for the gatherer and the total amount of treasure collected.

#### Example
Here's an example of how the solution can be used:

```yaml
$ java TrufflePath.java field.txt

[0, 0] - 6
[1, 1] - 4
[2, 2] - 6
Total truffles: 16
```
where the text file (field.txt) stores the information of the field :

```
6  2  4
5  4  2
4  2  6
```

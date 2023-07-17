## Skyline Problem

This folder contains Java console applications related to the "Skyline Problem". The Skyline Problem involves finding the skyline of a city, which refers to the outline created by a collection of buildings when viewed from a distance. The skyline is represented as a list of key points along the horizon, sorted based on their x-coordinate.

#### Problem Description

A city's skyline is the outer contour of the silhouette formed by all the buildings in that city when viewed from a distance. Given the locations and heights of all the buildings, your task is to create a program that can efficiently calculate the skyline formed by these buildings collectively. The skyline should be represented as a list of key points in the form `[[x1, y1], [x2, y2], ...]`. Each key point represents the left endpoint of a horizontal segment in the skyline, except for the last point in the list, which always has a y-coordinate of 0 and marks the termination of the skyline where the rightmost building ends. The contour of the skyline includes any ground area situated between the leftmost and rightmost buildings.

#### Solution Overview

The provided Java code in this repository solves the Skyline Problem using a divide-and-conquer approach. It uses a modified form of the topological-sort algorithm to find the best path for constructing the skyline. The code efficiently merges the skylines of sub-regions to calculate the overall skyline of the city.

#### Demo

To see the Skyline Problem solution in action, follow these steps:

1. Compile the Java program by executing the following command in your terminal:

   ```bash
   javac Skyline.java
   ```

2. Run the program by executing the following command:

   ```bash
   java Skyline
   ```

3. Enter the input representing the buildings in the format `<l h r> <l h r> ...`. For example:

   ```plaintext
   Please enter a list of buildings in the form <l h r> <l h r> ...
   <1 10 3><2 5 5><3 7 8>
   ```

4. Press Enter to see the resulting skyline, represented as a list of left-most key points in the format `[[x1, y1], [x2, y2], ...]`. For example, the previous arguments output:

   ```plaintext
   <1 10><3 7><8 0>
   ```

   This output indicates that the skyline consists of the points (1, 10), (3, 7), and (8, 0).

Feel free to explore different inputs and observe how the program calculates the skyline based on the given buildings.

---

Note: The provided Java code assumes that the necessary dependencies are available and properly configured. Make sure you have Java Development Kit (JDK) installed on your machine.

---

By following these steps, you can run the Skyline Problem solution and observe its output.

Enjoy exploring the city skylines!

---

**Author**: [Jacob Snow](https://github.com/snowjacob)

---

Please customize the README as needed, adding any additional information or sections that are relevant to your project.

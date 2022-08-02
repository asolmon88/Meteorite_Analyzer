# Meteorite Analyzer

## Description

In this coursework, the images were given. This program is meant for a scenario where a person has a ship and said person, needs to load as much meteorites as possible into that ship. The program that we had to create, had to look at an image of the meteorites and calculate the weight and cost of each meteorite, to see how many the person can take on their ship.

For example, if you see any of the images, you will see dots inside of a figure. The outside figure represents the meteorite and the dots inside are the crystals. With this, we used the backpack algorithm to calculate optimal way to carry as much meteorites as possible.

The file ```Imagen.jar``` contains code from the professor to convert the images in matrixes so it was easier to work on.

## Compilation and Execution

### Windows

To compile in windows, open the folder where the source code is and type the following:

```
$ javac -cp .;Imagen.jar *.java
```

To run:

```
$ java -cp .;Imagen.jar *.java
```

### Linux
To compile in linux, open the folder where the source code is and type the following:

```
$ javac -cp .:Imagen.jar *.java
```

To run:

```
$ java -cp .:Imagen.jar *.java
```

After this the first thing the program asks for is the name of the photo that is going to be analyzed. After this, it's going to ask for the maximum capacity the ship can take. Lastly it asks where you want the results to be displayed, either on screen or on a file, after that press Ctrl+C to terminate the program.
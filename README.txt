The program checks wheteher a maze has a solution where the maze is a PNG file or a JPEG file
To run the program recieves the image as a file location from the command line arguments
You must make sure that the background color (the color of the path of the maze) is set according to the maze
Set it in the main function
The prorgram uses the Union Find algorithem to unite all similar pixels and thus checks wheter the path exists between the start and end points
The output of the program yields a result whether the maze has a solution or not and the number of different components
Each component is a path created by the pixels
In addition, it draws a new image to the screen where each component is of different color
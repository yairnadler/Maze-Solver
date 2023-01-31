The program checks wheteher a maze has a solution where the maze is a PNG file or a JPEG file.

To run, the program recieves the image as a file location from the command line arguments.

You must make sure that the background color (the color of the path of the maze) is set according to the maze (set it in the main function) and that in the given image the start and end point are marked as red pixels.

The prorgram uses the Union Find algorithm to unite all similar pixels and thus checks wheter the path exists between the start and end points. 

The output of the program yields a result whether the maze has a solution or not and the number of different components, where each component is a path created by the pixels.

In addition, it draws a new image to the screen where each component is of different color.

(There are example mazes to test the program and to play around with).

Enjoy!

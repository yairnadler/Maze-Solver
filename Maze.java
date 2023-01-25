

import java.util.ArrayList; 
import java.awt.Color; 
 
/** 
 * A class for decomposing images into connected components. 
 */ 
public class Maze { 
 
   private UnionFind uf; 
   private DisplayImage image; 
   private int startX;   // x-coordinate for the start pixel
   private int startY;	 // y-coordinate for the start pixel
   private int endX;	 // x-coordinate for the end pixel
   private int endY;	 // y-coordinate for the end pixel
  
  /** 
    * Constructor - reads image, and decomposes it into its connected components.
    * After calling the constructor, the mazeHasSolution() and areConnected() methods
	* are expect to return a correct solution.
    * 
    * @param fileName name of image file to process. 
    */ 
   public Maze (String fileName, Color c) { 
      image = new DisplayImage(fileName);
      uf = new UnionFind(image.height() * image.width());
      int count = 0;
      for (int x = 0; x < image.width(); x++){
         for (int y = 0; y < image.height(); y++){
            //Checks start and end points of maze
            if (image.isRed(x, y)){
               if (count == 0){
                  startX = x;
                  startY = y;
                  image.set(x, y, c);
               }
               else{
                  endX = x;
                  endY = y;
                  image.set(x, y, c);
               }
               count++;
            }
            //If we reached the n x n pixel it's already connected, therefore we can return
            if (y == image.height() - 1 && x == image.width() - 1) return;
            //Checks if the pixel below current is of same path
            if (x < image.width() - 1){
               if (image.isOn(x + 1, y) == image.isOn(x, y) && areConnected(x + 1, y, x, y) == false){
                  connect(x + 1, y, x, y);
               }
            }
            //If reached the last row, only check if pixel to the right is of same path
            if (x == image.width() - 1 && y < image.height() - 1){
               if (image.isOn(x, y) == image.isOn(x,y + 1) && areConnected(x, y, x, y + 1) == false){
                  connect(x, y, x, y + 1);
               }
            }
            //Checks if pixel to the right is of same path
            if (y < image.height() - 1){
               if (image.isOn(x, y) == image.isOn(x, y + 1) && areConnected(x, y + 1, x, y) == false){
                  connect(x, y + 1, x, y);
               }
            }
            //If reached last column, only check if pixel below is of same path
            if (y == image.height() - 1 && x < image.width() - 1){
               if (image.isOn(x, y) == image.isOn(x + 1, y) && areConnected(x, y, x + 1, y) == false){
                  connect(x, y, x + 1, y);
               }
            }
            
         }
      }
   } 
 
   /** 
    * Generates a unique integer id from (x, y) coordinates. 
    * It is suggested you implement this function, in order to transform
	* pixels into valid indices for the UnionFind data structure.
    *
	* @param x x-coordinate. 
    * @param y y-coordinate. 
    * @return unique id. 
    */ 
   private int pixelToId (int x, int y) { 
      return y * image.width() + x;
   } 
 
   /** 
    * Connects two pixels if they both belong to the same image 
    * area (background or foreground), and are not already connected. 
    * It is assumed that the pixels are neighbours.
    * 
    * @param x1 x-coordinate of first pixel. 
    * @param y1 y-coordinate of first pixel. 
    * @param x2 x-coordinate of second pixel. 
    * @param y2 y-coordinate of second pixel. 
    */ 
   public void connect (int x1, int y1, int x2, int y2) { 
      int x1y1 = pixelToId(x1, y1);
      int x2y2 = pixelToId(x2, y2);
		uf.union(uf.find(x1y1), uf.find(x2y2));
   } 
 

 
   /** 
    * Checks if two pixels are connected (belong to the same component). 
    * 
    * @param x1 x-coordinate of first pixel. 
    * @param y1 y-coordinate of first pixel. 
    * @param x2 x-coordinate of second pixel. 
    * @param y2 y-coordinate of second pixel. 
    * @return true if the pixels are connected, false otherwise. 
    */ 
   public boolean areConnected (int x1, int y1, int x2, int y2) { 
		int x1y1 = pixelToId(x1, y1);
      int leader1 = uf.find(x1y1);
      int x2y2 = pixelToId(x2, y2);
      int leader2 = uf.find(x2y2);
		return (leader1 == leader2);
   } 
 
   /** 
    * Finds the number of components in the image. 
    * 
    * @return the number of components in the image 
    */ 
   public int getNumComponents() { 
		return uf.getNumSets();
   } 
 
   /**
    * Returns true if and only if the maze has a solution.
    * In this case, the start and end pixel will belong to the same connected component.
    * 
    * @return true if and only if the maze has a solution
    */
   public boolean mazeHasSolution(){
		int startIndex = pixelToId(startX, startY);
      int endIndex = pixelToId(endX, endY);
      return (uf.find(startIndex) == uf.find(endIndex));
   }
      
   /** 
    * Creates a visual representation of the connected components. 
    * 
    * @return a new image with each component colored in a random color. 
    */ 
   public DisplayImage getComponentImage() { 
 
      DisplayImage compImg = new DisplayImage (image.width(), image.height()); 
      ArrayList<Integer> usedIds = new ArrayList<Integer>(); 
      int numComponents = getNumComponents(); 
      final int MAX_COLOR = 0xffffff; 
      Color colors[] = new Color[numComponents]; 
 
      colors[0] = new Color (0, 0, 100); 
      for (int c = 1; c < numComponents; c++) 
         colors[c] = new Color ((int)(Math.random() * MAX_COLOR)); 
 
      for (int x = 0; x < image.width(); x++) 
         for (int y = 0; y < image.height(); y++) { 
            int componentId = uf.find (pixelToId (x, y)); 
            // Check if this id already exists (inefficient for a large number of components). 
            int index = -1; 
            for (int i = 0; i < usedIds.size(); i++) 
               if (usedIds.get (i) == componentId) 
                  index = i; 
            if (index == -1) { 
               usedIds.add (componentId); 
               index = usedIds.size() - 1; 
            } 
            compImg.set (x, y, colors[index]); 
         } 
 
      return compImg; 
   } 
 
   /** 
    * Various tests for the segmentation functionality. 
    * 
    * @param args command line arguments - name of file to process. 
    */ 
   public static void main (String[] args) { 
 
	  Maze maze = new Maze (args[0], Color.white); // Set the color of the path of the maze.
 
      System.out.println (maze.mazeHasSolution());
 
      System.out.println ("Number of components: " + maze.getNumComponents()); 
 
      DisplayImage compImg = maze.getComponentImage(); 
      compImg.show(); 
   } 
} 

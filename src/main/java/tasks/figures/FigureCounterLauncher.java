package tasks.figures;

import java.io.IOException;

/**
 * Provides entry point to the figure counter program
 * 
 * @author sgerenski
 */
public class FigureCounterLauncher {
   /**
    * Program entry point method
    * 
    * @param arguments
    *           arguments from the command line
    */
   public static void main(String[] arguments) {
      if (arguments.length != 2) {
         printUsage();
         return;
      }

      FigureCounter counter = null;
      try {
         counter = initCounter(arguments[0], arguments[1]);
      } catch (FigureCounterInitializationException | IllegalArgumentException e) {
         System.out.println("Could not initialize program due to invalid "
               + "input data.");
         System.out.println("Error: " + e.getMessage());
         return;
      }

      int count = counter.count();
      System.out.println("Number of recognized figures: " + count);
   }

   static FigureCounter initCounter(String filePath, String columnCountString) {
      boolean[] data = loadFile(filePath);
      int columnCount = resolveColumnCount(columnCountString);
      FigureCounter counter = new FigureCounter(data, columnCount);
      return counter;
   }

   private static boolean[] loadFile(String path) {
      boolean data[] = null;
      try {
         data = FileUtils.readFigureData(path);
      } catch (IOException e) {
         throw new FigureCounterInitializationException(
               "An error occured while loading file: " + path, e);
      }

      return data;
   }

   private static int resolveColumnCount(String columnCountString) {
      final String errorMessage = "The specified number of columns is invalid: "
            + columnCountString;
      int columnCount = 0;
      try {
         columnCount = Integer.parseInt(columnCountString);
      } catch (NumberFormatException e) {
         throw new FigureCounterInitializationException(errorMessage, e);
      }

      if (columnCount <= 0) {
         throw new FigureCounterInitializationException(errorMessage);
      }

      return columnCount;
   }

   private static void printUsage() {
      System.out.println("Usage: java " + FigureCounterLauncher.class.getName()
            + " <path to matrix data file> <number of columns in matrix>");
   }
}

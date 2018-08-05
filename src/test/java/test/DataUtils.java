package test;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

/**
 * Provides abilities to generate or manipulate data relevant to code that
 * performs testing
 * 
 * @author sgerenski
 */
public class DataUtils {
   /**
    * Converts raw matrix data to its string representation so it can be
    * visualized
    * 
    * @param data
    *           raw matrix data
    * @param columns
    *           the number of columns of the matrix
    * @return string representation of the provided matrix data
    */
   public static String matrixToString(boolean data[], int columns) {
      StringBuilder stringBuilder = new StringBuilder();
      for (int i = 0; i < data.length; i++) {
         stringBuilder.append(data[i] ? "1 " : "0 ");
         if ((i + 1) % columns == 0) {
            stringBuilder.append(Constants.LINE_SEPARATOR);
         }
      }

      return stringBuilder.toString();
   }

   /**
    * Generates matrix data per specified criteria
    * 
    * @param rows
    *           the number of rows that the matrix will have
    * @param columns
    *           the number of columns that the matrix will have
    * @return matrix data per specified criteria
    */
   public static boolean[] buildRandomData(int rows, int columns) {
      return buildRandomData(rows, columns, 50);
   }

   /**
    * Generates matrix data per specified criteria
    * 
    * @param rows
    *           the number of rows that the matrix will have
    * @param columns
    *           the number of columns that the matrix will have
    * @param pixelDensityFactor
    *           a number from 0 to 100 specifying how dense the "colored" pixels
    *           will be - it will be silently normalized if a value out of the
    *           expected range is provided
    * @return matrix data per specified criteria
    */
   public static boolean[] buildRandomData(int rows, int columns,
         int pixelDensityFactor) {
      int length = rows * columns;
      boolean[] data = new boolean[length];

      if (length > 0 && pixelDensityFactor > 0) {
         if (pixelDensityFactor > 100) {
            pixelDensityFactor = 100;
         }

         Set<Integer> indexes = null;
         if (pixelDensityFactor < 100) {
            int indexesCount = pixelDensityFactor * length / 100;
            indexes = new HashSet<>(indexesCount);
            Random random = new Random();
            for (int i = 0; i < indexesCount; i++) {
               indexes.add(random.nextInt(length));
            }
         }

         for (int i = 0; i < length; i++) {
            if (indexes == null || indexes.contains(i)) {
               data[i] = true;
            }
         }
      }

      return data;
   }
}

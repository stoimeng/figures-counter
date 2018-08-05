package tasks.figures;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

/**
 * Provides the ability to differentiate and count figures represented by data
 * provided in a matrix-like structure
 * <p>
 * The input data represents a matrix like structure with each cell's value
 * representing a pixel from an image. Neighboring cells with value indicating a
 * colored pixel form a single image.
 * 
 * @author sgerenski
 */
public class FigureCounter {
   private final boolean[] data;
   private final int columnCount;
   private final int rowCount;

   /**
    * Class constructor
    * 
    * @param data
    *           matrix-like structure to be analyzed, must be non-null
    * @param columnCount
    *           a number indicating how many columns are in the raw data, must
    *           be positive and divide the number of cells in the data without
    *           reminder, so that valid matrix can be formed
    */
   public FigureCounter(boolean data[], int columnCount) {
      if (data == null || data.length == 0) {
         throw new IllegalArgumentException("Matrix Data is invalid");
      }
      if (columnCount <= 0 || columnCount > data.length
            || data.length % columnCount != 0) {
         throw new IllegalArgumentException("Column Count is invalid");
      }

      this.data = data;
      this.columnCount = columnCount;
      this.rowCount = data.length / columnCount;
   }

   /**
    * Performs analysis of the underlying data and returns the number of
    * recognized figures
    * 
    * @return the number of recognized figures
    */
   public int count() {
      DisjointSets sets = buildDisjointSets();

      Set<Integer> representatives = new HashSet<>();
      for (int i = 0; i < data.length; i++) {
         if (data[i]) {
            int representative = sets.find(i);
            representatives.add(representative);
         }
      }

      return representatives.size();
   }

   private DisjointSets buildDisjointSets() {
      DisjointSets sets = new DisjointSets(data.length);

      for (int i = 0; i < rowCount; i++) {
         for (int j = 0; j < columnCount; j++) {
            int index = getUnidimentionalIndex(i, j);
            if (data[index]) {
               for (int neighborIndex : getNeighborIndexes(i, j)) {
                  sets.union(index, neighborIndex);
               }
            }
         }
      }

      return sets;
   }

   private List<Integer> getNeighborIndexes(int row, int column) {
      List<Integer> indexes = new LinkedList<>();
      int index = getNeighborIndex(row - 1, column);
      if (index != -1) {
         indexes.add(index);
      }

      index = getNeighborIndex(row + 1, column);
      if (index != -1) {
         indexes.add(index);
      }

      index = getNeighborIndex(row, column - 1);
      if (index != -1) {
         indexes.add(index);
      }

      index = getNeighborIndex(row, column + 1);
      if (index != -1) {
         indexes.add(index);
      }

      return indexes;
   }

   private int getNeighborIndex(int row, int column) {
      int index = -1;
      if (row >= 0 && row < rowCount && column >= 0 && column < columnCount) {
         index = getUnidimentionalIndex(row, column);
         if (!data[index]) {
            index = -1;
         }
      }

      return index;
   }

   private int getUnidimentionalIndex(int row, int column) {
      return row * columnCount + column;
   }

   /**
    * Provides the ability to maintain disjoint sets of a range of numeric
    * elements starting from 0
    * 
    * @author sgerenski
    */
   private static class DisjointSets {
      private final int upperBound;
      private final int[] ranks;
      private final int[] parents;

      /**
       * Class constructor
       * 
       * @param upperBound
       *           specifies the upper bound of the range of elements starting
       *           from 0
       */
      public DisjointSets(int upperBound) {
         if (upperBound < 0) {
            throw new IllegalArgumentException("Upper Bound is invalid");
         }

         this.upperBound = upperBound;
         ranks = new int[upperBound];
         parents = new int[upperBound];
         for (int i = 0; i < upperBound; i++) {
            parents[i] = i;
         }
      }

      /**
       * Performs a search for the set containing the specified element
       * 
       * @param element
       *           the element whose set is to be found
       * @return the set containing the specified element as identified by the
       *         returned representative
       */
      public int find(int element) {
         if (element < 0 || element >= upperBound) {
            throw new IllegalArgumentException("Element is invalid");
         }

         int parent = element;
         while (parents[parent] != parent) {
            parent = parents[parent];
         }

         return parent;
      }

      /**
       * Performs union of the sets containing the two elements specified
       * 
       * @param element1
       *           first element a member of a set
       * @param element2
       *           second element a member of a set
       */
      public void union(int element1, int element2) {
         if (element1 < 0 || element1 >= upperBound) {
            throw new IllegalArgumentException("Element1 is invalid");
         }
         if (element2 < 0 || element2 >= upperBound) {
            throw new IllegalArgumentException("Element2 is invalid");
         }

         int representative1 = find(element1);
         int representative2 = find(element2);

         if (representative1 != representative2) {
            if (ranks[representative1] < ranks[representative2]) {
               parents[representative1] = representative2;
            } else {
               parents[representative2] = representative1;
               if (ranks[representative1] == ranks[representative2]) {
                  ranks[representative1]++;
               }
            }
         }
      }
   }
}

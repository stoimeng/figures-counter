package tasks.figures;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Provides capabilities for working with files
 * 
 * @author sgerenski
 *
 */
public class FileUtils {
   /**
    * Read figure data from file at the specified path
    * 
    * @param pathString
    *           the path where the file is located
    * @return an array representing the data as read from the file
    * @throws IOException
    *            thrown to indicate any error reading the file
    */
   public static boolean[] readFigureData(String pathString) throws IOException {
      boolean data[] = null;

      Path path = Paths.get(pathString);
      File file = path.toFile();
      if (!file.exists()) {
         throw new IOException("Cannot read file - file does not exist");
      }

      long length = file.length();
      if (length > Integer.MAX_VALUE) {
         throw new IOException(
               "Cannot read file - the specified file is bigger than "
                     + "the maximum number of bytes: " + Integer.MAX_VALUE);
      }

      int dataSize = (int) length;
      data = new boolean[dataSize];
      if (dataSize > 0) {
         FileInputStream stream = new FileInputStream(file);
         try (BufferedReader reader = new BufferedReader(new InputStreamReader(
               stream))) {
            for (int i = 0; i < dataSize; i++) {
               int character = reader.read();
               if (character == '0') {
                  data[i] = false;
               } else if (character == '1') {
                  data[i] = true;
               } else {
                  throw new IOException(
                        "Invalid data in file - it must be a sequence of 0 and "
                              + "1 characters");
               }
            }
         }
      }

      return data;
   }
}

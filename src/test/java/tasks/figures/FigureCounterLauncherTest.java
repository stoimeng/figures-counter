package tasks.figures;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.util.UUID;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import test.CauseMatcher;

public class FigureCounterLauncherTest {
   private static final String VALID_DATA = "1011";

   @Rule
   public ExpectedException expectedExceptionRule = ExpectedException.none();

   private Path validDataFilePath;
   private Path invalidDataFilePath;

   @Before
   public void createTestData() throws IOException {
      validDataFilePath = createTempDataFile(VALID_DATA);
      invalidDataFilePath = createTempDataFile("abc");
   }

   @Test
   public void testNonExistingFile() {
      expectedExceptionRule.expect(FigureCounterInitializationException.class);
      expectedExceptionRule
            .expectMessage("An error occured while loading file");
      expectedExceptionRule.expectCause(new CauseMatcher(IOException.class,
            "Cannot read file - file does not exist"));
      FigureCounterLauncher.initCounter(UUID.randomUUID().toString(), "2");
   }

   @Test
   public void testFileInvalidData() {
      expectedExceptionRule.expect(FigureCounterInitializationException.class);
      expectedExceptionRule
            .expectMessage("An error occured while loading file");
      expectedExceptionRule.expectCause(new CauseMatcher(IOException.class,
            "Invalid data in file - it must be a sequence of 0 and "
                  + "1 characters"));
      FigureCounterLauncher.initCounter(invalidDataFilePath.toString(), "2");
   }

   @Test
   public void testColumnCountOff() {
      expectedExceptionRule.expect(IllegalArgumentException.class);
      expectedExceptionRule.expectMessage("Column Count is invalid");
      FigureCounterLauncher.initCounter(validDataFilePath.toString(), "3");
   }

   @Test
   public void testColumnCountStringIllegal() {
      final String columnCount = "abc";

      expectedExceptionRule.expect(FigureCounterInitializationException.class);
      expectedExceptionRule
            .expectMessage("The specified number of columns is invalid: "
                  + columnCount);
      FigureCounterLauncher.initCounter(validDataFilePath.toString(),
            columnCount);
   }

   @Test
   public void testColumnCountNegative() {
      final String columnCount = "-1";

      expectedExceptionRule.expect(FigureCounterInitializationException.class);
      expectedExceptionRule
            .expectMessage("The specified number of columns is invalid: "
                  + columnCount);
      FigureCounterLauncher.initCounter(validDataFilePath.toString(),
            columnCount);
   }

   @Test
   public void testValidParameters() {
      FigureCounterLauncher.initCounter(validDataFilePath.toString(),
            String.valueOf(VALID_DATA.length() / 2));
   }

   private Path createTempDataFile(String data) throws IOException {
      File file = File.createTempFile("figures", ".data");
      file.deleteOnExit();
      try (DataOutputStream stream = new DataOutputStream(new FileOutputStream(
            file))) {
         stream.write(data.getBytes());
      }

      return file.toPath();
   }
}

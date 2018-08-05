package tasks.figures;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import tasks.figures.FigureCounter;

public class FigureCounterTest {
   private static final boolean[] VALID_DATA = new boolean[] {// @formatter:off
      true, false, true, false, true, true
      };// @formatter:on
   private static final int VALID_COLUMN_COUNT = 3;

   @Rule
   public ExpectedException expectedExceptionRule = ExpectedException.none();

   @Test
   public void testDataNull() {
      expectedExceptionRule.expect(IllegalArgumentException.class);
      expectedExceptionRule.expectMessage("Matrix Data is invalid");
      new FigureCounter(null, VALID_COLUMN_COUNT);
   }

   @Test
   public void testDataEmpty() {
      expectedExceptionRule.expect(IllegalArgumentException.class);
      expectedExceptionRule.expectMessage("Matrix Data is invalid");
      new FigureCounter(new boolean[0], VALID_COLUMN_COUNT);
   }

   @Test
   public void testColumnCountNegative() {
      expectedExceptionRule.expect(IllegalArgumentException.class);
      expectedExceptionRule.expectMessage("Column Count is invalid");
      new FigureCounter(VALID_DATA, -1);
   }

   @Test
   public void testColumnCountZero() {
      expectedExceptionRule.expect(IllegalArgumentException.class);
      expectedExceptionRule.expectMessage("Column Count is invalid");
      new FigureCounter(VALID_DATA, 0);
   }

   @Test
   public void testColumnCountOff() {
      expectedExceptionRule.expect(IllegalArgumentException.class);
      expectedExceptionRule.expectMessage("Column Count is invalid");
      new FigureCounter(VALID_DATA, 4);
   }
}

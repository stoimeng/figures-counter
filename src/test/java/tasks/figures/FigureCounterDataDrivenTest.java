package tasks.figures;

import java.util.Arrays;
import java.util.Collection;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import tasks.figures.FigureCounter;
import test.Constants;
import test.DataUtils;

@RunWith(Parameterized.class)
public class FigureCounterDataDrivenTest {
   private static final boolean[] MATRIX1 = {// @formatter:off
		false,false,false,false,false,false,false,false,false,false,
		true,false,false,true,false,true,false,false,false,false,
		false,false,false,true,true,false,false,true,false,false,
		false,false,false,false,false,false,false,false,false,false,
		false,true,false,false,true,false,false,true,false,false,
		true,false,false,false,false,false,false,false,false,false,
		true,false,true,false,false,true,true,false,false,true,
		true,false,false,false,false,true,false,false,true,true,
		false,false,false,false,true,true,true,true,false,false,
		false,false,false,true,false,true,true,false,false,false
		};// @formatter:on
   private static final int COLUMNS1 = 10;
   private static final int COUNT1 = 12;

   private static final boolean[] MATRIX2 = {// @formatter:off
		false, false, false, false, false, false, false, false, false, false,
		true, false, false, true, false, true, false, false, false, false,
		false, false, false, true, true, false, false, true, false, false,
		false, false, false, false, false, false, false, false, false, false,
		false, true, false, false, true, false, false, true, false, false,
		true, false, false, false, false, false, false, false, false, false,
		true, false, true, false, false, true, true, false, false, true,
		true, false, false, false, false, true, false, false, true, true,
		false, false, false, false, true, true, true, true, false, false,
		false, false, false, true, false, true, true, false, false, false
		}; // @formatter:on
   private static final int COLUMNS2 = 10;
   private static final int COUNT2 = 12;

   private static final boolean[] MATRIX3 = {// @formatter:off
		true, true, false, true, true, true, true, false, false, false,
		false, false, false, false, true, true, true, false, false, false,
		true, true, true, false, true, false, true, true, true, false,
		false, true, true, true, true, true, false, true, true, false,
		true, false, true, true, false, true, true, true, true, true,
		true, true, true, true, false, false, false, false, true, true,
		true, true, true, true, false, false, true, false, false, false,
		true, false, false, false, false, false, true, true, true, false,
		false, true, true, false, false, false, false, false, false, true,
		true, true, true, false, false, false, true, true, true, false
		}; // @formatter:on
   private static final int COLUMNS3 = 10;
   private static final int COUNT3 = 6;

   private static final boolean[] MATRIX4 = {// @formatter:off
		false, false, false, true, false, true, true, true, false, true,
		false, true, true, true, false, false, true, false, false, false,
		true, true, false, false, false, true, true, true, true, true,
		true, true, false, true, false, true, false, false, true, true,
		false, true, true, true, false, false, true, true, false, false,
		false, true, false, true, false, false, true, true, true, false,
		true, true, true, true, true, true, true, true, false, false,
		true, false, false, false, true, true, false, true, false, true,
		false, true, true, true, true, true, false, true, true, true,
		true, true, false, false, false, true, true, true, true, false
		}; // @formatter:on
   private static final int COLUMNS4 = 10;
   private static final int COUNT4 = 3;

   private static final boolean[] MATRIX5 = {// @formatter:off
		true, false, true, true, true, true, true, true, false, false
		}; // @formatter:on
   private static final int COLUMNS5 = 10;
   private static final int COUNT5 = 2;

   private static final boolean[] MATRIX6 = {// @formatter:off
		true,
		true,
		false,
		false,
		false,
		false,
		false,
		false,
		true,
		false
		}; // @formatter:on
   private static final int COLUMNS6 = 1;
   private static final int COUNT6 = 2;

   private static final boolean[] MATRIX7 = {// @formatter:off
		true
		}; // @formatter:on
   private static final int COLUMNS7 = 1;
   private static final int COUNT7 = 1;

   private static final boolean[] MATRIX8 = {// @formatter:off
		false
		}; // @formatter:on
   private static final int COLUMNS8 = 1;
   private static final int COUNT8 = 0;

   private static final boolean[] MATRIX9 = {// @formatter:off
      true,
      true
      }; // @formatter:on
   private static final int COLUMNS9 = 1;
   private static final int COUNT9 = 1;

   private static final boolean[] MATRIX10 = {// @formatter:off
      false,
      false
      }; // @formatter:on
   private static final int COLUMNS10 = 1;
   private static final int COUNT10 = 0;

   private static final boolean[] MATRIX11 = {// @formatter:off
      true,
      false
      }; // @formatter:on
   private static final int COLUMNS11 = 1;
   private static final int COUNT11 = 1;

   private static final boolean[] MATRIX12 = {// @formatter:off
      false,
      true
      }; // @formatter:on
   private static final int COLUMNS12 = 1;
   private static final int COUNT12 = 1;

   private static final boolean[] MATRIX13 = {// @formatter:off
      true, true
      }; // @formatter:on
   private static final int COLUMNS13 = 2;
   private static final int COUNT13 = 1;

   private static final boolean[] MATRIX14 = {// @formatter:off
      false, false
      }; // @formatter:on
   private static final int COLUMNS14 = 2;
   private static final int COUNT14 = 0;

   private static final boolean[] MATRIX15 = {// @formatter:off
      true, false
      }; // @formatter:on
   private static final int COLUMNS15 = 2;
   private static final int COUNT15 = 1;

   private static final boolean[] MATRIX16 = {// @formatter:off
      false, true
      }; // @formatter:on
   private static final int COLUMNS16 = 2;
   private static final int COUNT16 = 1;

   @Parameters
   public static Collection<Object[]> provideData() {
      return Arrays.asList(new Object[][] { { MATRIX1, COLUMNS1, COUNT1 },
            { MATRIX2, COLUMNS2, COUNT2 }, { MATRIX3, COLUMNS3, COUNT3 },
            { MATRIX4, COLUMNS4, COUNT4 }, { MATRIX5, COLUMNS5, COUNT5 },
            { MATRIX6, COLUMNS6, COUNT6 }, { MATRIX7, COLUMNS7, COUNT7 },
            { MATRIX8, COLUMNS8, COUNT8 }, { MATRIX9, COLUMNS9, COUNT9 },
            { MATRIX10, COLUMNS10, COUNT10 }, { MATRIX11, COLUMNS11, COUNT11 },
            { MATRIX12, COLUMNS12, COUNT12 }, { MATRIX13, COLUMNS13, COUNT13 },
            { MATRIX14, COLUMNS14, COUNT14 }, { MATRIX15, COLUMNS15, COUNT15 },
            { MATRIX16, COLUMNS16, COUNT16 } });
   }

   private final boolean[] data;
   private final int columns;
   private final int expectedCount;

   public FigureCounterDataDrivenTest(boolean data[], int columns,
         int expectedCount) {
      this.data = data;
      this.columns = columns;
      this.expectedCount = expectedCount;
   }

   @Test
   public void testCounter() {
      String matrixAsString = DataUtils.matrixToString(data, columns);
      FigureCounter counter = new FigureCounter(data, columns);
      Assert.assertEquals("Count of figures in matrix: "
            + Constants.LINE_SEPARATOR + matrixAsString, expectedCount,
            counter.count());
   }
}

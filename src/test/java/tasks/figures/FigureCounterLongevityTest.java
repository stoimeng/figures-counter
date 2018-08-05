package tasks.figures;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.junit.Assume;
import org.junit.Before;
import org.junit.Test;

import tasks.figures.FigureCounter;
import test.DataUtils;
import test.PerformanceUtils;
import test.PerformanceUtils.Timer;

public class FigureCounterLongevityTest {
   private static final long MINIMUM_EXECUTION_TIME = TimeUnit.MINUTES
         .toNanos(1);

   private final ScheduledExecutorService scheduler = Executors
         .newScheduledThreadPool(1);

   @Before
   public void evaluateRunConditions() {
      boolean includePerformanceTests = Boolean
            .getBoolean("includePerformanceTests");
      Assume.assumeTrue(includePerformanceTests);
   }

   @Test
   public void testCounter() {
      int columns = 1000;
      boolean[] data = DataUtils.buildRandomData(100, columns);
      FigureCounter counter = new FigureCounter(data, columns);

      scheduler.scheduleWithFixedDelay(PerformanceUtils.STATISTICS_PRINT_TASK,
            0, 1, TimeUnit.SECONDS);

      Timer timer = PerformanceUtils.initTimer();

      do {
         counter.count();
      } while (timer.getMilliseconds() < MINIMUM_EXECUTION_TIME);

      timer.printMilliseconds("Total milliseconds counting has been "
            + "continuously performed: %s");
   }
}

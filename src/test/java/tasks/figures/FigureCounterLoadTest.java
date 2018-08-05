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

public class FigureCounterLoadTest {
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
      // 100 million data points in total
      final int rows = 1000, columns = 100000;
      boolean[] data = DataUtils.buildRandomData(rows, columns, 90);

      scheduler.scheduleWithFixedDelay(PerformanceUtils.STATISTICS_PRINT_TASK,
            0, 1, TimeUnit.SECONDS);

      Timer timer = PerformanceUtils.initTimer();

      FigureCounter counter = new FigureCounter(data, columns);
      counter.count();

      timer.printMilliseconds("Total milliseconds to initialize counter "
            + "and complete counting: %s");
   }
}
package test;

import java.lang.management.ManagementFactory;
import java.lang.management.OperatingSystemMXBean;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Provides utilities to be used in performance tests
 * 
 * @author sgerenski
 */
public class PerformanceUtils {
   public static final StatisticsPrinter STATISTICS_PRINTER = StatisticsPrinter
         .getInstance();
   public static final StatisticsPrintTask STATISTICS_PRINT_TASK = new StatisticsPrintTask();

   /**
    * Initializes and returns a timer to be used for measuring time
    * <p>
    * The returned instance is thread-safe and can be used across threads.
    * 
    * @return timer to be used for measuring time
    */
   public static Timer initTimer() {
      Timer timer = new Timer();
      timer.start();
      return timer;
   }

   /**
    * Provides simple capabilities for measuring time
    * <p>
    * Any instance of this class is thread-safe and can be used across threads.
    * 
    * @author sgerenski
    */
   public static class Timer {
      private Long startTime = null;
      private Long stopTime = null;

      private Timer() {
      }

      /**
       * Start the timer
       */
      public synchronized void start() {
         if (startTime != null) {
            throw new IllegalStateException("Timer is already runnig");
         }
         startTime = System.nanoTime();
      }

      /**
       * Stop the timer
       */
      public synchronized void stop() {
         if (stopTime != null) {
            throw new IllegalStateException("Timer has already been stopped");
         }
         stopTime = System.nanoTime();
      }

      /**
       * Get the elapsed time in milliseconds
       * 
       * @return the elapsed time in milliseconds
       */
      public synchronized long getMilliseconds() {
         if (startTime == null) {
            throw new IllegalStateException("Timer has not been started");
         }

         long stopTime;
         if (this.stopTime == null) {
            stopTime = System.nanoTime();
         } else {
            stopTime = this.stopTime;
         }

         long milliseconds = TimeUnit.NANOSECONDS
               .toMillis(stopTime - startTime);

         return milliseconds;
      }

      /**
       * Prints the elapsed time in milliseconds
       */
      public void printMilliseconds() {
         printMilliseconds("Timer has been running for %d millisecods");
      }

      /**
       * Prints the elapsed time in milliseconds
       * 
       * @param messageFormatString
       *           a format string with single format specifier to be replaced
       *           with the milliseconds value
       */
      public synchronized void printMilliseconds(String messageFormatString) {
         long milliseconds = getMilliseconds();
         System.out.println(String.format(messageFormatString, milliseconds));
      }
   }

   /**
    * Provides capabilities to print to console various performance-related
    * statistics whenever the {@link #run()} method is invoked
    * 
    * @author sgerenski
    */
   public static class StatisticsPrintTask implements Runnable {
      private StatisticsPrintTask() {
      }

      @Override
      public void run() {
         PerformanceUtils.STATISTICS_PRINTER.print();
      }
   }

   /**
    * Provides capabilities to print to console various performance-related
    * statistics whenever the {@link #print()} method is invoked
    * 
    * @author sgerenski
    */
   public static class StatisticsPrinter {
      private final OperatingSystemMXBean osMxBean;
      private final List<Method> methods;

      private StatisticsPrinter(OperatingSystemMXBean osMxBean,
            List<Method> methods) {
         this.osMxBean = osMxBean;
         this.methods = methods;
      }

      private static StatisticsPrinter getInstance() {
         OperatingSystemMXBean osMxBean = ManagementFactory
               .getOperatingSystemMXBean();
         List<Method> methods = new LinkedList<Method>();
         for (Method method : osMxBean.getClass().getDeclaredMethods()) {
            if (method.getName().startsWith("get")) {
               methods.add(method);
               if (Modifier.isPublic(method.getModifiers())) {
                  method.setAccessible(true);
               }
            }
         }

         return new StatisticsPrinter(osMxBean, methods);
      }

      /**
       * Performs collection of statistics, printing each on the console
       */
      public void print() {
         for (Method method : methods) {
            Object value;
            try {
               value = method.invoke(osMxBean);
            } catch (Exception e) {
               value = e;
            }
            System.out.print(method.getName() + "=" + value + ", ");
         }
         System.out.println();
      }
   }
}

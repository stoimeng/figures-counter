package tasks.figures;

/**
 * Represents an error occurring while trying to initialize
 * {@link FigureCounter}
 * 
 * @author sgerenski
 *
 */
public class FigureCounterInitializationException extends RuntimeException {
   public FigureCounterInitializationException(String message) {
      super(message);
   }

   public FigureCounterInitializationException(String message, Exception e) {
      super(message, e);
   }
}

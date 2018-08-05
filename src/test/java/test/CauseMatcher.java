package test;

import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;

/**
 * Provides capabilities to describe {@link Exception} cause in order to be used
 * in matching exception (e.g. expected exceptions)
 * 
 * @author sgerenski
 */
public class CauseMatcher extends TypeSafeMatcher<Throwable> {
   private final Class<? extends Throwable> type;
   private final String expectedMessage;

   /**
    * Class constructor
    * 
    * @param type
    *           the type of the expected cause
    * @param expectedMessage
    *           the message expected in the cause
    */
   public CauseMatcher(Class<? extends Throwable> type, String expectedMessage) {
      this.type = type;
      this.expectedMessage = expectedMessage;
   }

   @Override
   protected boolean matchesSafely(Throwable item) {
      return item.getClass().isAssignableFrom(type)
            && item.getMessage().contains(expectedMessage);
   }

   @Override
   public void describeTo(Description description) {
      description.appendText("expects type ").appendValue(type)
            .appendText(" and a message ").appendValue(expectedMessage);
   }
}
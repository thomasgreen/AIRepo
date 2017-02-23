//Exception for 2 Checkers on the same square
public class AlreadyOccupiedException extends RuntimeException
{
   /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

public AlreadyOccupiedException(String msg)
   {
      super(msg);
   }
}
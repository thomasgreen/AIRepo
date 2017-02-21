import java.awt.EventQueue;

import javax.swing.JFrame;

public class CheckersGame extends JFrame
{
   /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

public CheckersGame(String title)
   {
      super(title);
      setDefaultCloseOperation(EXIT_ON_CLOSE);

      Board board = new Board();
      
      //RED CHECKERS
      for(int r = 1; r <= 3;r+=2) //for the first and third rows
      {
    	  for(int c = 2; c <= 8; c += 2)//for every other column
    	  {
    		  board.add(new Checker(CheckerType.RED_REGULAR, r, c), r, c);
    	  }
      }
      for (int c = 1; c<8;c+=2)
      {
    	  board.add(new Checker(CheckerType.RED_REGULAR, 2, c), 2, c);
      }
      
      //BLACK CHECKERS
      for(int r = 6; r <= 8;r+=2) //for the first and third rows
      {
    	  for(int c = 1; c <= 8; c += 2)//for every other collumn
    	  {
    		  board.add(new Checker(CheckerType.BLACK_REGULAR, r, c), r, c);
    	  }
      }
      for (int c = 2; c<=8;c+=2)
      {
    	  board.add(new Checker(CheckerType.BLACK_REGULAR, 7, c), 7, c);
      }
      
    
      setContentPane(board);

      pack();
      setVisible(true);
   }






   public static void main(String[] args)
   {
      Runnable r = new Runnable()
                   {
                      @Override
                      public void run()
                      {
                         new CheckersGame("Checkers");
                      }
                   };
      EventQueue.invokeLater(r);
   }
}
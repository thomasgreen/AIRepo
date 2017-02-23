import java.awt.EventQueue;

import javax.swing.JFrame;

public class CheckersGame extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	//https://www.itsyourturn.com/t_helptopic2030.html <--- Rules of checkers for logic ref
	public CheckersGame(String title) {
		super(title);
		setDefaultCloseOperation(EXIT_ON_CLOSE);

		Board board = new Board();
		
		

		
		board.setCurrentPlayer(board.getHumanRED()); //sets the red player as the first player
		// RED CHECKERS
		for (int r = 1; r <= 3; r += 2) // for the first and third rows
		{
			for (int c = 2; c <= 8; c += 2)// for every other column
			{
				Checker checker = new Checker(CheckerType.RED_REGULAR, r, c);
				board.add(checker, r, c); //add the checker to the board and...
				board.getHumanRED().getPlayerCheckers().add(checker); //add the checker to the players list

			}
		}
		for (int c = 1; c < 8; c += 2) // for middle row
		{
			Checker checker = new Checker(CheckerType.RED_REGULAR, 2, c);
			board.add(checker, 2, c);
			board.getHumanRED().getPlayerCheckers().add(checker);

		}

		
		// BLACK CHECKERS
		for (int r = 6; r <= 8; r += 2) // for the first and third rows
		{
			for (int c = 1; c <= 8; c += 2)// for every other collumn
			{
				Checker checker = new Checker(CheckerType.BLACK_REGULAR, r, c);
				board.add(checker, r, c);
				board.getHumanBLACK().getPlayerCheckers().add(checker);
			}
		}
		for (int c = 2; c <= 8; c += 2) // for middle row
		{
			Checker checker = new Checker(CheckerType.BLACK_REGULAR, 7, c);
			board.add(checker, 7, c);
			board.getHumanBLACK().getPlayerCheckers().add(checker);

		}

		setContentPane(board);

		pack();
		setVisible(true);
	}

	public static void main(String[] args) // main
	{
		Runnable r = new Runnable() {
			@Override
			public void run() {
				new CheckersGame("Checkers");
			}
		};
		EventQueue.invokeLater(r);
	}
}
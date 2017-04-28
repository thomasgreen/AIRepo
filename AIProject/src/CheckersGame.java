import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

public class CheckersGame extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private AILog log = new AILog();
	//https://www.itsyourturn.com/t_helptopic2030.html <--- Rules of checkers for logic ref
	public CheckersGame(String title) {
		super(title);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setResizable(false);
		Board board = new Board(log);
		log.show();
		

		
		
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

		makeMenu();
		
	    
		pack();
	    setLocationRelativeTo(null);
		setVisible(true);
		
		
		
	}

	private void makeMenu() {
		// TODO Auto-generated method stub
		JMenuBar menuBar = new JMenuBar();
		JMenu newGameMenu = new JMenu("New Game");
		newGameMenu.setMnemonic(KeyEvent.VK_N);
	    menuBar.add(newGameMenu);
	    
	    
	    JMenuItem hhMenuItem = new JMenuItem("HUMAN vs HUMAN");
	    newGameMenu.add(hhMenuItem);
	    setJMenuBar(menuBar);
	    
	    JMenu haMenuItem = new JMenu("HUMAN vs AI");
	    newGameMenu.add(haMenuItem);
	    setJMenuBar(menuBar);
	    
	    JMenu aaMenuItem = new JMenu("AI vs AI");
	    newGameMenu.add(aaMenuItem);
	    setJMenuBar(menuBar);
	    
	    JMenuItem easy = new JMenuItem("EASY");
	    haMenuItem.add(easy);
	    
	    JMenuItem medium = new JMenuItem("MEDIUM");
	    haMenuItem.add(medium);
	    
	    JMenuItem hard = new JMenuItem("HARD");
	    haMenuItem.add(hard);
	    
	    setJMenuBar(menuBar);
	    
	    //HUMAN V HUMAN MENU CLICK
		hhMenuItem.addActionListener(new ActionListener() {

		    @Override
		    public void actionPerformed(ActionEvent arg0) {

		    	
		        new CheckersGame("Checkers");
		        setVisible(false); //you can't see me!
		        dispose(); //Destroy the JFrame object
		    }

		});
		
		//EASY HUMAN V AI
		easy.addActionListener(new ActionListener() {

		    @Override
		    public void actionPerformed(ActionEvent arg0) {

		    	
		        new CheckersGame("Checkers EASY VS AI");
		        setVisible(false); //you can't see me!
		        dispose(); //Destroy the JFrame object
		    }

		});
		
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
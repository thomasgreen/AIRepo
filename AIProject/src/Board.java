import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import java.awt.event.MouseEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseMotionAdapter;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JComponent;

public class Board extends JComponent {
	// dimension of checkerboard square (25% bigger than checker)

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L; // needs to be in, dont
														// know why

	private final static int SQUAREDIM = (int) (Checker.getDimension() * 1.25);

	// dimension of checkerboard (width of 8 squares)

	private final int BOARDDIM = 8 * SQUAREDIM;

	// preferred size of Board component

	private Dimension dimPrefSize;

	// dragging flag -- set to true when user presses mouse button over checker
	// and cleared to false when user releases mouse button

	private boolean inDrag = false;

	// Checker object currently being modified
	private Checker currentChecker;
	public String player = "BLACK";
	// displacement between drag start coordinates and checker center
	// coordinates

	private int deltax, deltay;

	// center location of checker at start of drag

	private int oldcx, oldcy;

	// row and col of checker about to move.

	public int oldrow;
	public int oldcol;

	// list of Checkers on the board - each object inside contains row and col,
	// and coordonites of center;
	
	//win check
	public boolean redwin = false;
	public boolean blackwin = false;

	public List<Checker> checkerslist;
	
	
	//TWO PLAYER OBJECTS
	private Human currentPlayer;
	
	
	private Human humanRED;
	private Human humanBLACK;
	
	
	//TAKE PIECE FLAG - checks if move is currently being made
	private boolean takePieceFlag;
	
	//checker to delete from board
	
	private Checker checkerDELETE;
	public AILog log;
	
	
	
	public Board(AILog log) {
		this.log = log;
		checkerslist = new ArrayList<>();
		dimPrefSize = new Dimension(BOARDDIM, BOARDDIM);
		
		humanRED = new Human("RED");
		humanBLACK = new Human("BLACK");
		setCurrentPlayer(humanBLACK); //sets the red player as the first player
		takePieceFlag = false;
		addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent me) {

				// Obtain mouse coordinates at time of press.
				int x = me.getX(); // col
				int y = me.getY(); // row

				// Locate positioned checker under mouse press.
				boolean found = false;
				for (int i = 0; i <= checkerslist.size() - 1; i++) {

					if (checkerslist.get(i).contains(x, y, checkerslist.get(i).cx, checkerslist.get(i).cy)) {
						// If a checker has been clicked on...
						// (Code might move to own method when checker taking is
						// implemented
						currentChecker = checkerslist.get(i);// make
																// currentChecker
																// the checker
																// found under
																// mouse
						oldcx = checkerslist.get(i).cx; // should probably write
														// getter methods for
														// this
						oldcy = checkerslist.get(i).cy; // should probably write
														// getter methods for
														// this
						deltax = x - checkerslist.get(i).cx;
						deltay = y - checkerslist.get(i).cy;
						inDrag = true; // set in drag to true
						oldcol = (SQUAREDIM + (2 * oldcx)) / (2 * SQUAREDIM); // outdated
																				// calculation
																				// -
																				// get(i).getRow()
																				// might
																				// do
																				// same
																				// trick
																				// now
						oldrow = (SQUAREDIM + (2 * oldcy)) / (2 * SQUAREDIM); // outdated
																				// calculation
																				// -
																				// get(i).getRow()
																				// might
																				// do
																				// same
																				// trick
																				// now
						found = true; // set found to true
					}

				}
				if (!found) // if the user did not click on a checker
				{ // might need to be modified when dealing with only clicking
					// on red/black checkers
					throw new NoCheckerSelectedException("No Checker Selected");
				}

			}

			@Override
			public void mouseReleased(MouseEvent me) {

				int x = me.getX();
				int y = me.getY();

				// snap to centre of square
				currentChecker.cx = (x - deltax) / SQUAREDIM * SQUAREDIM + SQUAREDIM / 2; // should
																							// be
																							// replaced
																							// with
																							// setter
																							// methods
				currentChecker.cy = (y - deltay) / SQUAREDIM * SQUAREDIM + SQUAREDIM / 2; // should
																							// be
																							// replaced
																							// with
																							// setter
																							// methods
				

				int newcol = (SQUAREDIM + (2 * currentChecker.cx)) / (2 * SQUAREDIM);
				int newrow = (SQUAREDIM + (2 * currentChecker.cy)) / (2 * SQUAREDIM);

				// check if valid move
				boolean valid = false;
				if (valid = validMove(newrow, newcol)) {
					makeMove();
					oldrow = currentChecker.getRow();
					oldcol = currentChecker.getCol();
					currentChecker.setCol(newcol);
					currentChecker.setRow(newrow);
					
					promotionCheck(newrow);
					
					
				} 
				else {
					currentChecker.cx = oldcx;
					currentChecker.cy = oldcy;
				}
				
				pieceTaken();
				//REMOVE OTHER PEICE
				
				//change current player
				if(valid)
				{
					if(currentPlayer.equals(humanBLACK))
					{
						/*
						System.out.println("hr checkers"+humanRED.getPlayerCheckers());
						AI2 srbAI = new AI2("Red");
						setCurrentPlayer(humanRED);
						player = "RED";
						Move aiMove = srbAI.simpleRule(Board.this);
						System.out.println("Chosen move: "+aiMove.toString());
						setCurrentChecker(findChecker(aiMove.getChecker()));
						oldrow = aiMove.getChecker().getRow();
						oldcol = aiMove.getChecker().getCol();
						
						currentChecker.setCol(aiMove.getNCol());
						currentChecker.setRow(aiMove.getNRow());
						validMove(aiMove.getChecker().getRow(),aiMove.getChecker().getCol() );
						pieceTaken();
						currentChecker.cx = (aiMove.getNCol() - 1) * SQUAREDIM + SQUAREDIM / 2;
						currentChecker.cy = (aiMove.getNRow() - 1) * SQUAREDIM + SQUAREDIM / 2;
						
						promotionCheck(aiMove.getNRow());
						log.appendLog("Move Made: " + aiMove);
						*/
						
						AIMM mmAI = new AIMM("RED");
						setCurrentPlayer(humanRED);
						player = "RED";
						Move aiMove = mmAI.getAIMove(Board.this);
						System.out.println("Chosen move: "+aiMove.toString());
						setCurrentChecker(matchChecker(aiMove));
						oldrow = aiMove.getRow();
					
						oldcol = aiMove.getCol();
						
						System.out.println("AIMOVE CHECK: " + validMove(aiMove.getNRow(),aiMove.getNCol()));
						if(validMoves(aiMove.getNRow(),aiMove.getNCol())){
							currentChecker.setCol(aiMove.getNCol());
							currentChecker.setRow(aiMove.getNRow());
							currentChecker.cx = (aiMove.getNCol() - 1) * SQUAREDIM + SQUAREDIM / 2;
							currentChecker.cy = (aiMove.getNRow() - 1) * SQUAREDIM + SQUAREDIM / 2;
						}
						pieceTaken();
						
						
						promotionCheck(aiMove.getNRow());
						log.appendLog("Move Made: " + aiMove);
						setCurrentPlayer(humanBLACK);
						player = "BLACK";
					}
					else if(currentPlayer.equals(humanRED))
					{
						setCurrentPlayer(humanBLACK);
						player = "BLACK";
					}

				}
				
				if(humanRED.getPlayerCheckers().isEmpty())
				{
					blackwin = true;
				}
				if(humanBLACK.getPlayerCheckers().isEmpty())
				{
					redwin = true;
				}
				
				System.out.println("Total Checkeers: " + checkerslist.size());
				System.out.println("RED Checkeers: " + humanRED.getPlayerCheckers().size());
				System.out.println("BLACK Checkeers: " + humanBLACK.getPlayerCheckers().size());
				currentChecker = null;
				
				repaint();

			}

		});
		
		// Attach a mouse motion listener to the applet. That listener listens
		// for mouse drag events.

		addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseDragged(MouseEvent me) {
				if (inDrag) {
					// Update location of checker center.

					currentChecker.cx = me.getX() - deltax;
					currentChecker.cy = me.getY() - deltay;
					repaint();
				}
			}
		});

	}
	public Board cloneBoard (){
		Board cB = new Board(log);
		List<Checker> cList = new ArrayList<>();
		List<Checker> rList = new ArrayList<>();
		List<Checker> bList = new ArrayList<>();
		for(Checker c : checkerslist){
			cList.add(new Checker(c));
		}
		for(Checker c : getHumanRED().getPlayerCheckers()){
			rList.add(new Checker(c));
		}
		for(Checker c : getHumanBLACK().getPlayerCheckers()){
			bList.add(new Checker(c));
		}
		cB.checkerslist = cList;
		cB.getHumanRED().setPlayerCheckers(rList);
		cB.getHumanBLACK().setPlayerCheckers(bList);
		cB.setCurrentChecker(new Checker(currentChecker));
		cB.oldcol = new Integer(oldcol);
		cB.oldrow = new Integer(oldrow);
		return cB;
		
	}
public void pieceTaken(){
	System.out.println("Piece Taken check : " + takePieceFlag);
	if(takePieceFlag)
	{
		checkerslist.remove(checkerDELETE); //remove checker from list
		if(currentPlayer.equals(humanBLACK))
		{
			humanRED.getPlayerCheckers().remove(checkerDELETE);
		}
		else if(currentPlayer.equals(humanRED))
		{
			humanBLACK.getPlayerCheckers().remove(checkerDELETE);
		}
		
	}
}
	public void add(Checker checker, int row, int col) { // adds checker to the
															// board
		if (row < 1 || row > 8)
			throw new IllegalArgumentException("row out of range: " + row);
		if (col < 1 || col > 8)
			throw new IllegalArgumentException("col out of range: " + col);
		currentChecker = checker;
		currentChecker.cx = (col - 1) * SQUAREDIM + SQUAREDIM / 2;
		currentChecker.cy = (row - 1) * SQUAREDIM + SQUAREDIM / 2;
		for (Checker _checker : checkerslist) {
			if (checker.cx == _checker.cx && checker.cy == _checker.cy) {
				throw new AlreadyOccupiedException("square at (" + row + "," + col + ") is occupied");
			}

		}
		checkerslist.add(checker);
		currentChecker = null;
	}

	@Override
	public Dimension getPreferredSize() {
		return dimPrefSize;
	}

	@Override
	protected void paintComponent(Graphics g) {
		paintCheckerBoard(g);
		for (Checker posCheck : checkerslist)
			if (posCheck != Board.this.currentChecker)
				posCheck.draw(g, posCheck.cx, posCheck.cy);

		// Draw dragged checker last so that it appears over any underlying
		// checker.

		if (currentChecker != null)
			currentChecker.draw(g, currentChecker.cx, currentChecker.cy);
			
					
		Font font = new Font("Arial", Font.BOLD, 40);
		g.setFont(font);
		
		if(redwin)
		{
			String string = "RED PLAYER WINS";
			FontMetrics fm = g.getFontMetrics();
			Rectangle2D r = fm.getStringBounds(string, g);
			int x = (this.getWidth() - (int) r.getWidth()) / 2;
		    int y = (this.getHeight() - (int) r.getHeight()) / 2 + fm.getAscent();
			g.drawString(string, x, y);
		}
		else if(blackwin)
		{
			String string = "BLACK PLAYER WINS";
			FontMetrics fm = g.getFontMetrics();
			Rectangle2D r = fm.getStringBounds(string, g);
			int x = (this.getWidth() - (int) r.getWidth()) / 2;
		    int y = (this.getHeight() - (int) r.getHeight()) / 2 + fm.getAscent();
			g.drawString(string, x, y);
		}
		
	} 

	private void paintCheckerBoard(Graphics g) {
		((Graphics2D) g).setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		// Paint checkerboard.

		for (int row = 0; row < 8; row++) {
			g.setColor(((row & 1) != 0) ? Color.GRAY : Color.LIGHT_GRAY);
			for (int col = 0; col < 8; col++) {
				g.fillRect(col * SQUAREDIM, row * SQUAREDIM, SQUAREDIM, SQUAREDIM);
				g.setColor((g.getColor() == Color.GRAY) ? Color.LIGHT_GRAY : Color.GRAY);
			}
		}
	}
	public void promotionCheck(int row)
	{
		CheckerType checkertype = currentChecker.getCheckerType();
		if(checkertype == CheckerType.RED_REGULAR && row == 8)
		{
			currentChecker.promote();
		}
		else if(checkertype == CheckerType.BLACK_REGULAR && row==1)
		{
			currentChecker.promote();
		}
	}

	public boolean validMove(int newrow, int newcol) {
		// normal move
	if(newrow>8 || newrow<1 || newcol>8 || newcol<1){
		return false;
	}
		CheckerType checkertype = currentChecker.getCheckerType();
		
		boolean valid = false;
		 if(checkertype == CheckerType.RED_REGULAR && player.equals("RED"))
		{
			//can only move down the board
			if ((oldrow + 1) == newrow)
			{
				if ((oldcol + 1) == newcol || (oldcol - 1) == newcol) // if col move
																	  // is valid
				{
					return true;

				}
			}
			//test if peice is being taken
			if ((oldrow + 2) == newrow)
			{
				if ((oldcol + 2) == newcol || (oldcol - 2) == newcol) // if col move
																	  // is valid
				{
					if(validTake(checkertype, newrow, newcol))
					{
						takePieceFlag = true;
						return true;
					}

				}
			}
		}
		else if(checkertype == CheckerType.BLACK_REGULAR && player.equals("BLACK"))
		{
			//can only move up the board
			if ((oldrow - 1) == newrow)
			{
				if ((oldcol + 1) == newcol || (oldcol - 1) == newcol) // if col move
																	  // is valid
				{
					valid = true;

				}
			}
			if ((oldrow - 2) == newrow)
			{
				if ((oldcol + 2) == newcol || (oldcol - 2) == newcol) // if col move
																	  // is valid
				{
					if(validTake(checkertype, newrow, newcol))
					{
						takePieceFlag = true;
						valid = true;
					}

				}
			}
			//
		}
		else if((checkertype == CheckerType.BLACK_KING && player.equals("BLACK")) || (checkertype == CheckerType.RED_KING && player.equals("RED")))
		{
			//can move up and down
			if ((oldrow + 1) == newrow || ((oldrow - 1) == newrow)){
				if ((oldcol + 1) == newcol || (oldcol - 1) == newcol) // if col move
																		// is valid
				{
					valid = true;

				}
			}
			if ((oldrow + 2) == newrow || ((oldrow - 2) == newrow)){
				if ((oldcol + 2) == newcol || (oldcol - 2) == newcol) // if col move
																		// is valid
				{if(validTake(checkertype, newrow, newcol))
					{
					takePieceFlag = true;
					valid = true;
					}

				}
			}
		}
		
		//check if piece is being taken

		for (Checker checker : checkerslist)
		{
			if ((checker != Board.this.currentChecker && checker.cx == Board.this.currentChecker.cx
					&& checker.cy == Board.this.currentChecker.cy) ||(checker != Board.this.currentChecker && checker.getCol() == Board.this.currentChecker.getCol()
					&& checker.getRow() == Board.this.currentChecker.getRow())) {

				Board.this.currentChecker.cx = oldcx;
				Board.this.currentChecker.cy = oldcy;
				valid = false;
				takePieceFlag = false;
			}
		}
			
		
		return valid;
		//
	}
	public boolean validMoves(int newrow, int newcol) {
		// normal move
	if(newrow>8 || newrow<1 || newcol>8 || newcol<1){
		return false;
	}
		CheckerType checkertype = currentChecker.getCheckerType();
		
		boolean valid = false;
		 if(checkertype == CheckerType.RED_REGULAR && player.equals("RED"))
		{
			//can only move down the board
			 System.out.println("Type and player check: PASSED");
			if ((oldrow + 1) == newrow)
			{
				System.out.println("row check: PASSED");
				if ((oldcol + 1) == newcol || (oldcol - 1) == newcol) // if col move
					System.out.println("col check: PASSED");											  // is valid
				{
					return true;

				}
			}
			//test if peice is being taken
			if ((oldrow + 2) == newrow)
			{
				if ((oldcol + 2) == newcol || (oldcol - 2) == newcol) // if col move
																	  // is valid
				{
					if(validTake(checkertype, newrow, newcol))
					{
						takePieceFlag = true;
						return true;
					}

				}
			}
		}
		else if(checkertype == CheckerType.BLACK_REGULAR && player.equals("BLACK"))
		{
			//can only move up the board
			if ((oldrow - 1) == newrow)
			{
				if ((oldcol + 1) == newcol || (oldcol - 1) == newcol) // if col move
																	  // is valid
				{
					valid = true;

				}
			}
			if ((oldrow - 2) == newrow)
			{
				if ((oldcol + 2) == newcol || (oldcol - 2) == newcol) // if col move
																	  // is valid
				{
					if(validTake(checkertype, newrow, newcol))
					{
						takePieceFlag = true;
						valid = true;
					}

				}
			}
			//
		}
		else if((checkertype == CheckerType.BLACK_KING && player.equals("BLACK")) || (checkertype == CheckerType.RED_KING && player.equals("RED")))
		{
			//can move up and down
			if ((oldrow + 1) == newrow || ((oldrow - 1) == newrow)){
				if ((oldcol + 1) == newcol || (oldcol - 1) == newcol) // if col move
																		// is valid
				{
					valid = true;

				}
			}
			if ((oldrow + 2) == newrow || ((oldrow - 2) == newrow)){
				if ((oldcol + 2) == newcol || (oldcol - 2) == newcol) // if col move
																		// is valid
				{if(validTake(checkertype, newrow, newcol))
					{
					takePieceFlag = true;
					valid = true;
					}

				}
			}
		}
		
		//check if piece is being taken

		for (Checker checker : checkerslist)
		{
			if ((checker != Board.this.currentChecker && checker.cx == Board.this.currentChecker.cx
					&& checker.cy == Board.this.currentChecker.cy) ||(checker != Board.this.currentChecker && checker.getCol() == Board.this.currentChecker.getCol()
					&& checker.getRow() == Board.this.currentChecker.getRow())) {

				Board.this.currentChecker.cx = oldcx;
				Board.this.currentChecker.cy = oldcy;
				valid = false;
				takePieceFlag = false;
			}
		}
			
		
		return valid;
		//
	}
	public boolean validTake(CheckerType c, int newrow, int newcol) { //checks if there is a peice between the move
		
		//find row/col between the move
		//check if oppoiste checker is on it
		
		int avgRow = (oldrow + newrow)/2;
		int avgCol = (oldcol + newcol)/2;
		
		if(player.equals("RED"))
		{
			//search black checkers
			for(Checker checker: checkerslist)
			{
				if(checker.getRow() == avgRow && checker.getCol() == avgCol && !(c.equals(checker.getCheckerType())))
				{
					checkerDELETE = checker;
					
					return true;
				}
			}
		}
		else if(player.equals("BLACK"))
		{
			//search red checkers
			for(Checker checker: checkerslist)
			{
				if(checker.getRow() == avgRow && checker.getCol() == avgCol && !(c.equals(checker.getCheckerType())))
				{
					checkerDELETE = checker;
					
					return true;
				}
			}
		}
		
		return false;
	}

	private void makeMove()
	{
		// valid move
		if (inDrag)
			inDrag = false; // turn drag off
		else
			return;

		// Do not move checker onto an occupied square.

		
		
		

	}

	public Human getCurrentPlayer() {
		return currentPlayer;
	}

	public void setCurrentPlayer(Human currentPlayer) {
		this.currentPlayer = currentPlayer;
	}
	public void setCurrentChecker(Checker checker){
		currentChecker = checker;
	}

	public Human getHumanRED() {
		return humanRED;
	}

	public Human getHumanBLACK() {
		return humanBLACK;
	}
	public boolean getTPFlag(){
		return takePieceFlag;
	}
	public void setTPFlag(boolean set){
		takePieceFlag = set;
	}

	public void setHumanRED(Human humanRED) {
		this.humanRED = humanRED;
	}

	public void setHumanBLACK(Human humanBLACK) {
		this.humanBLACK = humanBLACK;
	}
	public Checker findChecker(Checker checker){
		for(Checker c : checkerslist){
			if(checker.equals(c)){
				return c;
			}
			
			
		}
		return checker;
	}
	public void movePiece(Move move){
		//update current checker
		
		//move the checker
		//check it needs to be king 
		move.getChecker().setCol(move.getNCol());
		move.getChecker().setRow(move.getNRow());
		move.getChecker().cx = (move.getNCol() - 1) * SQUAREDIM + SQUAREDIM / 2;
		move.getChecker().cy = (move.getNRow() - 1) * SQUAREDIM + SQUAREDIM / 2;
		promotionCheck(move.getNRow());
		
		
	}
	public Checker matchChecker(Move m){
		for(Checker c : checkerslist){
			if(m.getCol() == c.getCol() && m.getRow() == c.getRow()){
				return c;
			}
		}
		System.out.println("checker not found");
		return null;
	}
	
	public void takePiece(Move move){
		checkerslist.remove(checkerDELETE); //remove checker from list
		if(currentPlayer.equals("humanBLACK"))
		{
			humanRED.getPlayerCheckers().remove(checkerDELETE);
		}
		else if(currentPlayer.equals("aiRED"))
		{
			humanBLACK.getPlayerCheckers().remove(checkerDELETE);
		}
		
		
		
	}
	public int getOldrow() {
		return oldrow;
	}

	public int getOldcol() {
		return oldcol;
	}

	public void setOldrow(int oldrow) {
		this.oldrow = oldrow;
	}

	public void setOldcol(int oldcol) {
		this.oldcol = oldcol;
	}

}
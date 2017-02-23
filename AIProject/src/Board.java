import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import java.awt.event.MouseEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseMotionAdapter;

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

	// displacement between drag start coordinates and checker center
	// coordinates

	private int deltax, deltay;

	// center location of checker at start of drag

	private int oldcx, oldcy;

	// row and col of checker about to move.

	private int oldrow;
	private int oldcol;

	// list of Checkers on the board - each object inside contains row and col,
	// and coordonites of center;

	public List<Checker> checkerslist;

	public Board() {

		checkerslist = new ArrayList<>();
		dimPrefSize = new Dimension(BOARDDIM, BOARDDIM);

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
						oldrow = (SQUAREDIM + (2 * oldcx)) / (2 * SQUAREDIM); // outdated
																				// calculation
																				// -
																				// get(i).getRow()
																				// might
																				// do
																				// same
																				// trick
																				// now
						oldcol = (SQUAREDIM + (2 * oldcy)) / (2 * SQUAREDIM); // outdated
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

				int newrow = (SQUAREDIM + (2 * currentChecker.cx)) / (2 * SQUAREDIM);
				int newcol = (SQUAREDIM + (2 * currentChecker.cy)) / (2 * SQUAREDIM);

				// check if valid move
				if (validMove(oldrow, newrow, oldcol, newcol)) {

					makeMove();
				} 
				else {
					System.out.println("INVALID");
					currentChecker.cx = oldcx;
					currentChecker.cy = oldcy;
				}

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

	private boolean validMove(int oldrow, int newrow, int oldcol, int newcol) {
		// normal move
	
		CheckerType checkertype = currentChecker.getCheckerType();
		
		if(checkertype == CheckerType.RED_REGULAR)
		{
			//can only move down the board
			if ((oldcol + 1) == newcol)
			{
				if ((oldrow + 1) == newrow || (oldrow - 1) == newrow) // if col move
																	  // is valid
				{
					return true;

				}
			}
		}
		else if(checkertype == CheckerType.BLACK_REGULAR)
		{
			//can only move up the board
			if ((oldcol - 1) == newcol)
			{
				if ((oldrow + 1) == newrow || (oldrow - 1) == newrow) // if col move
																	  // is valid
				{
					return true;

				}
			}
			
		}
		else if(checkertype == CheckerType.BLACK_KING || checkertype == CheckerType.RED_KING)
		{
			//can move up and down
			if ((oldrow + 1) == newrow || ((oldrow - 1) == newrow)){
				if ((oldcol + 1) == newcol || (oldcol - 1) == newcol) // if col move
																		// is valid
				{
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

		for (Checker checker : checkerslist)
			if (checker != Board.this.currentChecker && checker.cx == Board.this.currentChecker.cx
					&& checker.cy == Board.this.currentChecker.cy) {

				Board.this.currentChecker.cx = oldcx;
				Board.this.currentChecker.cy = oldcy;
			}

	}

}
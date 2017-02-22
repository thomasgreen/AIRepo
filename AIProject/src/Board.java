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
	private static final long serialVersionUID = 1L;

	private final static int SQUAREDIM = (int) (Checker.getDimension() * 1.25);

	// dimension of checkerboard (width of 8 squares)

	private final int BOARDDIM = 8 * SQUAREDIM;

	// preferred size of Board component

	private Dimension dimPrefSize;

	// dragging flag -- set to true when user presses mouse button over checker
	// and cleared to false when user releases mouse button

	private boolean inDrag = false;
	

	// displacement between drag start coordinates and checker center
	// coordinates

	private int deltax, deltay;

	// reference to positioned checker at start of drag

	private PosCheck posCheck;

	// center location of checker at start of drag

	private int oldcx, oldcy;

	// list of Checker objects and their initial positions

	private List<PosCheck> posChecks;

	// row and col of checker about to move.

	private int oldrow;
	private int oldcol;

	public Board() {
		posChecks = new ArrayList<>();
		dimPrefSize = new Dimension(BOARDDIM, BOARDDIM);

		addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent me) {
				// Obtain mouse coordinates at time of press.

				int x = me.getX(); // col
				int y = me.getY(); // row

				// Locate positioned checker under mouse press.

				for (PosCheck posCheck : posChecks)
					if (Checker.contains(x, y, posCheck.cx, posCheck.cy)) {
						Board.this.posCheck = posCheck;
						oldcx = posCheck.cx;
						oldcy = posCheck.cy;
						deltax = x - posCheck.cx;
						deltay = y - posCheck.cy;
						inDrag = true;
						oldrow = (SQUAREDIM + (2 * oldcx)) / (2 * SQUAREDIM);
						oldcol = (SQUAREDIM + (2 * oldcy)) / (2 * SQUAREDIM);
						return;
					}
			}

			@Override
			public void mouseReleased(MouseEvent me) {

				int x = me.getX();
				int y = me.getY();

				// snap to centre of square
				posCheck.cx = (x - deltax) / SQUAREDIM * SQUAREDIM + SQUAREDIM / 2;
				posCheck.cy = (y - deltay) / SQUAREDIM * SQUAREDIM + SQUAREDIM / 2;

				int newrow = (SQUAREDIM + (2 * posCheck.cx)) / (2 * SQUAREDIM);
				int newcol = (SQUAREDIM + (2 * posCheck.cy)) / (2 * SQUAREDIM);

				System.out.println("Old Row:" + oldrow + " OldCol:" + oldcol);
				System.out.println("New Row:" + newrow + " NewCol:" + newcol);
				System.out.println();
				// check if valid move
				if (validMove(oldrow, newrow, oldcol, newcol)) {

					// valid move
					if (inDrag)
						inDrag = false;
					else
						return;

					// Snap checker to center of square.

					// Do not move checker onto an occupied square.

					for (PosCheck posCheck : posChecks)
						if (posCheck != Board.this.posCheck && posCheck.cx == Board.this.posCheck.cx
								&& posCheck.cy == Board.this.posCheck.cy) {

							Board.this.posCheck.cx = oldcx;
							Board.this.posCheck.cy = oldcy;
						}

				} else {
					System.out.println("INVALID");
					Board.this.posCheck.cx = oldcx;
					Board.this.posCheck.cy = oldcy;
				}

				posCheck = null;
				repaint();

				// When mouse released, clear inDrag (to
				// indicate no drag in progress) if inDrag is
				// already set.

			}

		});

		// Attach a mouse motion listener to the applet. That listener listens
		// for mouse drag events.

		addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseDragged(MouseEvent me) {
				if (inDrag) {
					// Update location of checker center.

					posCheck.cx = me.getX() - deltax;
					posCheck.cy = me.getY() - deltay;
					repaint();
				}
			}
		});

	}

	public void add(Checker checker, int row, int col) {
		if (row < 1 || row > 8)
			throw new IllegalArgumentException("row out of range: " + row);
		if (col < 1 || col > 8)
			throw new IllegalArgumentException("col out of range: " + col);
		PosCheck posCheck = new PosCheck();
		posCheck.checker = checker;
		posCheck.cx = (col - 1) * SQUAREDIM + SQUAREDIM / 2;
		posCheck.cy = (row - 1) * SQUAREDIM + SQUAREDIM / 2;
		for (PosCheck _posCheck : posChecks)
			if (posCheck.cx == _posCheck.cx && posCheck.cy == _posCheck.cy)
				throw new AlreadyOccupiedException("square at (" + row + "," + col + ") is occupied");
		posChecks.add(posCheck);
	}

	@Override
	public Dimension getPreferredSize() {
		return dimPrefSize;
	}

	@Override
	protected void paintComponent(Graphics g) {
		paintCheckerBoard(g);
		for (PosCheck posCheck : posChecks)
			if (posCheck != Board.this.posCheck)
				posCheck.checker.draw(g, posCheck.cx, posCheck.cy);

		// Draw dragged checker last so that it appears over any underlying
		// checker.

		if (posCheck != null)
			posCheck.checker.draw(g, posCheck.cx, posCheck.cy);
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
		if ((oldrow + 1) == newrow || (oldrow - 1) == newrow) {
			if ((oldcol + 1) == newcol || (oldcol - 1) == newcol) // if col move
																	// is valid
			{
				return true;

			}
		}
		if ((oldrow + 1) == newrow || (oldrow - 1) == newrow) {
			if ((oldcol + 1) == newcol || (oldcol - 1) == newcol) // if col move
																	// is valid
			{
				return true;

			}
		}

		// test for taking move
		
		return false;
	}

	// positioned checker helper class

	private class PosCheck {
		public Checker checker;
		public int cx;
		public int cy;
	}
}

public class Move {
	private Checker checker;
	private int row;
	private int col;
	private int newRow;
	private int newCol;
	private boolean capture;
	public Move(Checker c, int nRow, int nCol, boolean cap)
	{
		checker = c;
		row = c.getRow();
		col = c.getCol();
		newRow = nRow;
		newCol = nCol;
		capture = cap;
		
	}
	public Checker getChecker(){ return checker;}
	public int getRow(){ return row;}
	public int getNRow(){ return newRow;}
	public int getCol(){ return col;}
	public int getNCol(){return newCol;}
	public boolean getCap(){return capture;}
	public void setCap(boolean capture){this.capture = capture;}
	
	
}

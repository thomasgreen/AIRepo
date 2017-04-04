
public class Move {
	private Checker checker;
	private int row;
	private int col;
	private int newRow;
	private int newCol;
	public Move(Checker c, int nRow, int nCol)
	{
		checker = c;
		row = c.getRow();
		col = c.getCol();
		newRow = nRow;
		newCol = nCol;
		
	}
	public Checker getChecker(){ return checker;}
	public int getRow(){ return row;}
	public int getNRow(){ return newRow;}
	public int getCol(){ return col;}
	public int getNCol(){return newCol;}
	
	
}

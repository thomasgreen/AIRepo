
public class Move {
	private Checker checker;
	private int row;
	private int col;
	private int newRow;
	private int newCol;
	private boolean capture;
	public Move(Checker c, int nRow, int nCol, boolean capture)
	{
		checker = c;
		row = c.getRow();
		col = c.getCol();
		newRow = nRow;
		newCol = nCol;
		this.capture = capture;
		
	}
	public Checker getChecker(){ return checker;}
	public int getRow(){ return row;}
	public int getNRow(){ return newRow;}
	public int getCol(){ return col;}
	public int getNCol(){return newCol;}
	public boolean getCap(){return capture;}
	public String toString(){
		String capString = "";
		if(capture) capString = "Piece Taken";
		return "From : " + col + ", " + row + " to : " + newCol + ", " + newRow + " " + capString;}
	
}

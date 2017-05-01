
public class Move {
	private Checker checker;
	private int row;
	private int col;
	private int newRow;
	private int newCol;
	private boolean capture;
	private boolean threatened;
	public Move(Checker c, int nRow, int nCol, boolean capture, boolean threatened)
	{
		checker = c;
		row = c.getRow();
		col = c.getCol();
		newRow = nRow;
		newCol = nCol;
		this.capture = capture;
		this.threatened = threatened;
		
	}
	public Checker getChecker(){ return checker;}
	public int getRow(){ return row;}
	public int getNRow(){ return newRow;}
	public int getCol(){ return col;}
	public int getNCol(){return newCol;}
	public boolean getCap(){return capture;}
	public boolean getThreat(){return threatened;}
	public void setThreat(boolean set){threatened = set;}
	public String toString(){
		String capString = "";
		if(capture) capString = "Piece Taken";
		return "From : " + col + ", " + row + " to : " + newCol + ", " + newRow + " " + capString;}
	
}

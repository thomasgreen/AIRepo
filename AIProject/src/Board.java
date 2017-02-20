
public class Board {


    int blackPieces; //ammount of black pieces in play, construct to 8
    int whitePieces; //ammount of white pieces in play, construct to 8
    
    static final int rows = 8;
    static final int cols = 8;
    
    CellContent cells [][];
    
    public Board(){
    	//constructor
    }
    
    public Board(CellContent[][] board){
    	//contructor when board exists to update
    	
    }
    
    public void drawboard(){
    	//draw board graphically, probably using awt or swing
    }
	
	public boolean checkWin(){
		//checks if the player has won, could be given player to check as param
		//might be better placed in CheckersGame or Player classes
		boolean haswon = false;
		
		return haswon;
	}
	
}

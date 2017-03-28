//Class for generic AI, to be worked on later
import java.util.ArrayList;
import java.util.List;
public class AI extends Player {

	public AI(String checkerColour) {
		super(checkerColour);
		// TODO Auto-generated constructor stub
		
	}
	public int minimax(Board board,List<Checker> state, int depth, Player currentPlayer){
		int bestVal =0;
		return bestVal;
	}
	public List<Checker> generateMoves(Board board)
	{
		 List<Checker> nextMoves = new ArrayList<Checker>();
		 
		 if(board.redwin == true || board.blackwin == true) //check if player has won
		 {
			 return nextMoves; //return empty move list
		 }	
		 
		 for(Checker checker : board.checkerslist)
		 {
			 //check each space and add each possible move
			 return nextMoves;
			 
		 }
		return nextMoves;
	}
	public int evaluation (Board board){
		int score = 0;
		
		//set a score for actions i.e taking a piece
		//add up the score and return it
		
	return score;
}
}

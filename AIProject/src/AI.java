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
	public List<Move> generateMoves(Board board)
	{
		Board b = board;
		 List<Move> nextMoves = new ArrayList<Move>(); //placeholder list for moves
		 
		 if(board.redwin == true || board.blackwin == true) //check if player has won
		 {
			 return nextMoves; //return empty move list
		 }	
		 
		 for(Checker checker : board.checkerslist)
		 {
			 if(board.getCurrentPlayer().getCheckerColour().equals("Black"))
			 {
				if(checker.getCheckerType().equals(CheckerType.BLACK_REGULAR))
				{
					b.setCurrentChecker(checker);
					for(int i=-1;i>-3;i--){  // the same rules from the valid move section used here
						for(int k=-2; k<3;k++){
					if(b.validMove(checker.getRow()+i, checker.getCol()+k)){ //if the move is valid add it to the moves list
						nextMoves.add(new Move(checker, checker.getRow()+i, checker.getCol()+k));
						
					}
					}
					}
					
				}
				 
			 }
			
			 else{
				 if(board.getCurrentPlayer().getCheckerColour().equals("Red"))
				 {
					if(checker.getCheckerType().equals(CheckerType.RED_REGULAR))
						
					{
						b.setCurrentChecker(checker);
						for(int i=1;i>3;i++){  // the same rules from the valid move section used here
							for(int k=-2; k<3;k++){
						if(b.validMove(checker.getRow()+i, checker.getCol()+k)){ //if the move is valid add it to the moves list
							nextMoves.add(new Move(checker, checker.getRow()+i, checker.getCol()+k));
							
						}
						}
						}
						
					}
					 
				 }
			 }
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

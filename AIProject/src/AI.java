//Class for generic AI, to be worked on later
import java.util.ArrayList;
import java.util.List;
public class AI extends Player {

	Tree decisionTree;
    Move lastMove;
	Board boardCopy;
    
	public AI(String checkerColour) {
		super(checkerColour);
	}

	
	 public Move getAIMove(Board board) {
		
		 boardCopy = board;
		 
		 decisionTree = makeDescisionTree(boardCopy);
		 lastMove = pickMove();
	     return lastMove;
	 }


	private Move pickMove() {
		int max = -13;
		int index = 0;
		for (int i = 0; i < decisionTree.getNumChildren(); i++) {
			Tree child = decisionTree.getChild(i);
			int smin = 13;
			// Find the max leaf
			for (Tree sChild : child.getChildren()) {
				int tMax = -13;
				for (Tree tchild : sChild.getChildren()) {
					if (tchild.getScore() >= tMax) {
						tMax = tchild.getScore();
					}
				}
				sChild.setScore(tMax);
				// Find the min on the third level
				if (sChild.getScore() <= smin) {
					smin = sChild.getScore();
				}
			}
			child.setScore(smin);
			// Find the max on the second layer and save the index
			if (child.getScore() >= max) {
				max = child.getScore();
				index = i;
			}
		}
		return decisionTree.getChild(index).getMove();
	}


	private Tree makeDescisionTree(Board board) {
		// TODO Auto-generated method stub
		 Tree mainTree = new Tree(board, null, score(board));
		
		 ArrayList<Move> moves;
		 moves = generateMoves(board, this.getCheckerColour());
		 
		 for (Move move : moves) {
			  // Make second row
	          Board temp = board;
	          temp.movePiece(move);
	          temp.takePiece(move);
	          Tree firstLayer = new Tree(temp, move, score(temp));
	          ArrayList<Move> secondMoves = generateMoves(board, board.getAiRED().getCheckerColour());
	          
	          for (Move sMove : secondMoves) {
	        	  // Make third row
	                Board temp2 = temp;
	                temp2.movePiece(sMove);
	                temp2.takePiece(sMove);
	                Tree secondLayer = new Tree(temp2, sMove, score(temp2));
	                ArrayList<Move> thirdMoves = generateMoves(board, this.getCheckerColour());

	                for (Move tMove : thirdMoves) {
	                    // Make fourth row
	                    Board temp3 = temp2;
	                    temp3.movePiece(tMove);
	                    temp3.takePiece(tMove);

	                    secondLayer.addChild(new Tree(temp3, tMove, score(temp3)));
	                }

	                firstLayer.addChild(secondLayer);
	            }
	            mainTree.addChild(firstLayer);
	        }

	        return mainTree;
	        	  
	        	  
	        	  
	}
	
	private int score(Board board) {
        return board.getHumanBLACK().getPlayerCheckers().size() - board.getAiRED().getPlayerCheckers().size();
        
    }
	
	public ArrayList<Move> generateMoves(Board board, String colour)
	{
		Board copy = board;
		CheckerType normal = null;
		CheckerType promote = null;
		
		if(colour.equals("RED")){
			normal = CheckerType.RED_REGULAR;
			promote = CheckerType.RED_KING;
		}
		else if(colour.equals("BLACK"))
		{
			normal = CheckerType.BLACK_REGULAR;
			promote = CheckerType.BLACK_KING;
		}
		 ArrayList<Move> nextMoves = new ArrayList<Move>(); //placeholder list for moves
		 
		 if(copy.redwin == true || copy.blackwin == true) //check if player has won
		 {
			 return nextMoves; //return empty move list
		 }	
		 
		 for(Checker checker : copy.checkerslist)
		 {
			if(checker.getCheckerType().equals(normal))
			{
				copy.setCurrentChecker(checker);
				
				for(int i = 1; i < 3; i++)
				{
					// the same rules from the valid move section used here
					for(int k = -2; k < 3; k++)
					{
						if(checker.getRow() == 3)
						{
							System.out.println("DEGBUG");
						}
						boolean check = copy.validMove(checker, checker.getRow() + i, checker.getCol() +k);
						if(check)
						{//if the move is valid add it to the moves list
							if(copy.validTake(checker.getRow() + i, checker.getCol() +k))
							{
								nextMoves.add(new Move(checker, checker.getRow()+i, checker.getCol()+k, true));
							}
							else
							{
								nextMoves.add(new Move(checker, checker.getRow()+i, checker.getCol()+k, false));
								
							}			
						}
					}
				}		
			}
		 }
		 return nextMoves;
	}
	
	
	
}

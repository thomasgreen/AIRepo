
//Class for generic AI, to be worked on later
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class AI2 extends Player {
	
	public AI2(String checkerColour) {
		super(checkerColour);
		// TODO Auto-generated constructor stub
		
	}

	public int minimax(Board board, List<Checker> state, int depth, Player currentPlayer) {
		int bestVal = 0;
		return bestVal;
	}

	public Move simpleRule(Board board) {
		
		System.out.println("AI board");
		System.out.println(board.checkerslist);
		List<Move> gMoves = new ArrayList<Move>(); //list of valid moves from generate moves
		List<Move> nonThreatMoves = new ArrayList<Move>(); //list of non threatening moves
		gMoves = generateMoves(board); //populate gMoves
		System.out.println(gMoves);
		for (Move m : gMoves) {
			if (m.getCap()) { //for eahc move in gmoves, check if a enemy piece can be taken
				return m;
			}
		}
		for (Move m : gMoves){ // if not pieces can be taken, find a move that would not result in a piece being threatened
			if(!m.getThreat()){
				nonThreatMoves.add(m);
			}
		}
		Random rand = new Random();
		
		if(nonThreatMoves.size()!=0){
			return nonThreatMoves.get(rand.nextInt(nonThreatMoves.size())+0); //return a random move that is non threatening
		}
		try{
			gMoves.get(0);
		}catch (IndexOutOfBoundsException ex){
			System.out.println("Red play is out of Moves Black Wins");
			return null;
			
		}
		
		return gMoves.get(rand.nextInt(gMoves.size())+0); //if there are no other moves, return a random move from the valid moves list
	}
	
	public int areaCheck(Checker checker, int i, int k, Board board) {
		
		int count = 0;
		  if(((checker.getRow()+i)<=8) && ((checker.getCol()+k)<=8) && ((checker.getCol()+k)>=1)){ //stops a move that would exit the board from being valid
			  for(Checker c : board.checkerslist){
				  
				  if(!checker.equals(c)){ 
						 if((checker.getRow()+i)==c.getRow() && ((checker.getCol()+k) == c.getCol())){ //checks the space is not already occupied
							
								 
								 count++;
							 
							 
				  }else{
					  
				  }
				  }
				  
				  
			  }
		  }
			 
		  return count;
		 
	}

	public List<Move> generateMoves(Board board) {
		List<Move> nextMoves = new ArrayList<Move>(); //the list of possible moves to be returned to the ai


		for (Checker checker : board.checkerslist) {
			System.out.println(checker.getRow());
			System.out.println(checker.getCol());
			board.oldrow = checker.getRow(); 
			board.oldcol = checker.getCol();
			if (board.player.equals("BLACK")) {
				if (checker.getCheckerType().equals(CheckerType.BLACK_REGULAR)) {
					board.setCurrentChecker(checker);
					for (int i = -1; i > -3; i--) { // the same rules from the
													// valid move section used
													// here
						for (int k = -2; k < 3; k++) {
							board.setTPFlag(false);
							if (board.validMove(checker.getRow() + i, checker.getCol() + k) && areaCheck(checker, i, k, board) == 0) { // if
																								// the
																								// move
																								// is
																								// valid
																								// add
																								// it
																								// to
																								// the
																								// moves
																								// list
								if (board.getTPFlag()) {
									nextMoves.add(new Move(checker, checker.getRow() + i, checker.getCol() + k, true, false));
									
								} else {
									nextMoves.add(new Move(checker, checker.getRow() + i, checker.getCol() + k, false, false));
								}

							} else {
								System.out.println("invalid move " + i + k);
							}
						}
					}

				}

			}

			else {
				if (board.player.equals("RED")) {
					System.out.println("red player");
					if (checker.getCheckerType().equals(CheckerType.RED_REGULAR))

					{
						System.out.println("red moves");
						board.setCurrentChecker(checker);
						 // the same rules from the
						// valid move section
						// used here
						for (int i = 1; i < 3; i++) {
							for (int k = -2; k < 3; k++) {
								board.setTPFlag(false);
								// if the move is valid add it to the moves
								// list
								
								
								if (board.validMove(checker.getRow() + i, checker.getCol() + k) && areaCheck(checker, i, k, board) == 0) {
									System.out.println("----------------valid move");
									System.out.println("oldCol: " + checker.getCol() +"\noldRow: " + checker.getRow()  );
									System.out.println("Col: " + k +"\nRow: " + i );
									if (board.getTPFlag()) {
										nextMoves.add(
												new Move(checker, checker.getRow() + i, checker.getCol() + k, true, false));
										board.setTPFlag(false);
									} else {
										nextMoves.add(
												new Move(checker, checker.getRow() + i, checker.getCol() + k, false, false));
									}
								} else {
									
									
								}
							}
						}

					}else if(checker.getCheckerType().equals(CheckerType.RED_KING) || checker.getCheckerType().equals(CheckerType.BLACK_KING)){
						System.out.println("red moves king");
						board.setCurrentChecker(checker);
						 // the same rules from the
						// valid move section
						// used here
						for (int i = -2; i < 3; i++) {
							for (int k = -2; k < 3; k++) {
								board.setTPFlag(false);
								// if the move is valid add it to the moves
								// list
								
								if (board.validMove(checker.getRow() + i, checker.getCol() + k) && areaCheck(checker, i, k, board) == 0) {
									System.out.println("----------------valid move king");
									System.out.println("oldCol: " + checker.getCol() +"\noldRow: " + checker.getRow()  );
									System.out.println("Col: " + k +"\nRow: " + i );
									if (board.getTPFlag()) {
										nextMoves.add(
												new Move(checker, checker.getRow() + i, checker.getCol() + k, true, false));
										board.setTPFlag(false);
									} else {
										nextMoves.add(
												new Move(checker, checker.getRow() + i, checker.getCol() + k, false, false));
									}
								} else {
									
									
								}
							}
					}
						}
					}

				
			}
			// check each space and add each possible move

		}
		String colourM ="";
		String colourM1="";
		
		for(Move m : nextMoves){
			for(Checker c : board.checkerslist){
				if(m.getChecker().getCheckerType().equals(CheckerType.BLACK_KING) || 
					m.getChecker().getCheckerType().equals(CheckerType.BLACK_REGULAR)){
					colourM = "BLACK";
				}
				else{
					colourM = "RED";
				}
				if(c.getCheckerType().equals(CheckerType.BLACK_KING) || 
						c.getCheckerType().equals(CheckerType.BLACK_REGULAR)){
						colourM1 = "BLACK";
					}
					else{
						colourM1 = "RED";
					}
				if(!m.getChecker().equals(c) && !colourM.equals(colourM1)  && ((m.getNCol()+1==c.getCol())||m.getNCol()-1==c.getCol()) && ((m.getNRow()+1==c.getRow())||m.getNCol()-1==c.getRow())){
					if(m.getChecker().getCheckerType().equals(CheckerType.BLACK_REGULAR) && !(m.getNRow()==c.getRow()+1)){
						m.setThreat(true);
						System.out.println("threatened checker : m @ " + m.getNCol() + ", " + m.getNRow() +"\n" + 
								"enemy checker : c@ " + c.getCol() + ", " + c.getRow());
					}
					else if(m.getChecker().getCheckerType().equals(CheckerType.RED_REGULAR) && (m.getNRow()-1!=(c.getRow()))){
						m.setThreat(true);
						System.out.println("threatened checker : m @ " + m.getNCol() + ", " + m.getNRow() +"\n" + 
								"enemy checker : c@ " + c.getCol() + ", " + c.getRow());
								System.out.println(board.checkerslist);
					}
					else if(m.getChecker().getCheckerType().equals(CheckerType.BLACK_KING) || m.getChecker().getCheckerType().equals(CheckerType.RED_KING)){
					m.setThreat(true);
					System.out.println("threatened checker : m @ " + m.getNCol() + ", " + m.getNRow() +"\n" + 
							"enemy checker : c@ " + c.getCol() + ", " + c.getRow());
					}
					
				}
			}
		}
		if(nextMoves.size()==0){
			if(board.player.equals("BLACK")){
				board.redwin = true;
			}
			else{
				board.blackwin = true;
			}
			
		}
		if (board.redwin == true || board.blackwin == true) // check if player
															// has won
		{
			System.out.print("game ended");
			return nextMoves; // return empty move list
		}
		System.out.println("MY STUPID TEST: " + nextMoves);
		return nextMoves;
	}

	public int evaluation(Board board) {
		int score = 0;

		// set a score for actions i.e taking a piece
		// add up the score and return it

		return score;
	}
}


//Class for generic AI, to be worked on later
import java.util.ArrayList;
import java.util.List;

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
		List<Move> gMoves = new ArrayList<Move>();
		gMoves = generateMoves(board);
		System.out.println(gMoves);
		for (Move m : gMoves) {
			if (m.getCap()) {
				return m;
			}
		}
		System.out.println("AND THE ARRAY VALUE @ 0 IS: " + gMoves.get(0));
		
		return gMoves.get(0);
	}
	
	public int areaCheck(Checker checker, int i, int k, Board board) {
		 //&& !(checker.getRow()+i>9) && !(checker.getCol()+i>9) && (checker.getCol()+i>0)
		int count = 0;
		  if(((checker.getRow()+i)<=8) && ((checker.getCol()+k)<=8) && ((checker.getCol()+k)>=1)){
			  for(Checker c : board.checkerslist){
				  
				  if(!checker.equals(c)){ 
						 if((checker.getRow()+i)==c.getRow() && ((checker.getCol()+k) == c.getCol())){
							
								 
								 count++;
							 
							 
				  }else{
					  
				  }
				  }
				  
				  
			  }
		  }
			 
		  return count;
		 
	}

	public List<Move> generateMoves(Board board) {
		List<Move> nextMoves = new ArrayList<Move>(); // placeholder list for
														// moves

		if (board.redwin == true || board.blackwin == true) // check if player
															// has won
		{
			System.out.print("game ended");
			return nextMoves; // return empty move list
		}

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
									nextMoves.add(new Move(checker, checker.getRow() + i, checker.getCol() + k, true));
									
								} else {
									nextMoves.add(new Move(checker, checker.getRow() + i, checker.getCol() + k, false));
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
												new Move(checker, checker.getRow() + i, checker.getCol() + k, true));
										board.setTPFlag(false);
									} else {
										nextMoves.add(
												new Move(checker, checker.getRow() + i, checker.getCol() + k, false));
									}
								} else {
									
									
								}
							}
						}

					}else if(checker.getCheckerType().equals(CheckerType.RED_KING)){
						System.out.println("red moves");
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
									System.out.println("----------------valid move");
									System.out.println("oldCol: " + checker.getCol() +"\noldRow: " + checker.getRow()  );
									System.out.println("Col: " + k +"\nRow: " + i );
									if (board.getTPFlag()) {
										nextMoves.add(
												new Move(checker, checker.getRow() + i, checker.getCol() + k, true));
										board.setTPFlag(false);
									} else {
										nextMoves.add(
												new Move(checker, checker.getRow() + i, checker.getCol() + k, false));
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

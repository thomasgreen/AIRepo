
//Class for generic AI, to be worked on later
import java.util.ArrayList;
import java.util.List;

public class AIMM extends Player {

	Tree decisionTree;
	Move lastMove;
	Board b;
	

	public AIMM(String checkerColour) {
		super(checkerColour);
	}
	public Board copyBoard(Board b){
		Board cb = new Board(b.log);
		cb = b.cloneBoard();
		return cb;
	}

	public Move getAIMove(Board board) {
		b = board;
		Board boardCopy = board.cloneBoard();

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

		List<Move> moves;
		moves = generateMoves(board, "RED");

		for (Move move : moves) {
			// Make second row
			System.out.println("a1ddingmoves");
			Board temp = board;
			temp.movePieceAI(move);
			temp.takePiece(move);
			Tree firstLayer = new Tree(temp, move, score(temp));
			List<Move> secondMoves = generateMoves(board, "RED");

			for (Move sMove : secondMoves) {
				// Make third row
				System.out.println("a2ddingmoves");
				Board temp2 = temp;
				temp2.movePieceAI(sMove);
				temp2.takePiece(sMove);
				Tree secondLayer = new Tree(temp2, sMove, score(temp2));
				List<Move> thirdMoves = generateMoves(board, "RED");

				for (Move tMove : thirdMoves) {
					// Make fourth row
					Board temp3 = temp2;
					temp3.movePieceAI(tMove);
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
		return board.getHumanBLACK().getPlayerCheckers().size() - board.getHumanRED().getPlayerCheckers().size();

	}

	public int areaCheck(Checker checker, int i, int k, Board board) {
		// && !(checker.getRow()+i>9) && !(checker.getCol()+i>9) &&
		// (checker.getCol()+i>0)
		int count = 0;
			for (Checker c : board.checkerslist) {

				if (!checker.equals(c)) {
					if ((checker.getRow() + i) == c.getRow() && ((checker.getCol() + k) == c.getCol())) {

						count++;

					} else {

					}
				}

			}
		

		return count;

	}

	public List<Move> generateMoves(Board board, String colour) {
		List<Move> nextMoves = new ArrayList<Move>(); // placeholder list for
		// moves

		if (b.redwin == true || b.blackwin == true) // check if player
		// has won
		{
			System.out.print("game ended");
			return nextMoves; // return empty move list
		}

		for (Checker checker : board.checkerslist) {
			board.player = "RED";
			
			
			
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
							if (board.validMove(checker.getRow() + i, checker.getCol() + k)
									&& areaCheck(checker, i, k, board) == 0) { // if
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
								System.out.println("b invalid move " + i + k + ", " + board.oldrow + board.oldcol );
							}
						}
					}

				}

			}

			else {
				if (board.player.equals("RED")) {
					
					if (checker.getCheckerType().equals(CheckerType.RED_REGULAR))

					{
						
						board.setCurrentChecker(checker);
						// the same rules from the
						// valid move section
						// used here
						for (int i = 1; i < 3; i++) {
							for (int k = -2; k < 3; k++) {
								board.setTPFlag(false);
								// if the move is valid add it to the moves
								// list

								if (board.validMove(checker.getRow() + i, checker.getCol() + k) && areaCheck(checker, i, k, board) == 0)
										 {
									System.out.println("----------------valid move");
									System.out.println("oldCol: " + checker.getCol() + "\noldRow: " + checker.getRow());
									System.out.println("Col: " + k + "\nRow: " + i);
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

					} else if (checker.getCheckerType().equals(CheckerType.RED_KING)) {
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

								if (board.validMove(checker.getRow() + i, checker.getCol() + k)
										&& areaCheck(checker, i, k, board) == 0) {
									System.out.println("----------------valid move");
									System.out.println("oldCol: " + checker.getCol() + "\noldRow: " + checker.getRow());
									System.out.println("Col: " + k + "\nRow: " + i);
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
		System.out.println("MY STUPID TEST: " + nextMoves);
		return nextMoves;
	}

}
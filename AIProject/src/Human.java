import java.util.ArrayList;
import java.util.List;

//controls to be added here, not sure how to do that yet
public class Human extends Player {
	//human class used for controlling the game 
	
	private List<Checker> playerCheckers;
	private String CheckerColour;
	
	public Human(String checkerColour){
		
		playerCheckers = new ArrayList<>();
		this.CheckerColour = checkerColour;
	}

	public List<Checker> getPlayerCheckers() {
		return playerCheckers;
	}

	public void setPlayerCheckers(List<Checker> playerCheckers) {
		this.playerCheckers = playerCheckers;
	}

	public String getCheckerColour() {
		return CheckerColour;
	}

	public void setCheckerColour(String checkerColour) {
		CheckerColour = checkerColour;
	}
	
	
}

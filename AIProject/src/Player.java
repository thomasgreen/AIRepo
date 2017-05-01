import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public abstract class Player {
	//class to implement basic player of checkers
	
	private List<Checker> playerCheckers;
	private String CheckerColour;
	
	public Player(String checkerColour)
	{
		playerCheckers = Collections.synchronizedList(new ArrayList<>());
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

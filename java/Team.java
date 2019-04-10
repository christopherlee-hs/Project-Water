import java.util.ArrayList;

public class Team implements Comparable<Team> {
	protected ArrayList<Game> gamesPlayed; // an arraylist of type Game that stores all the games "this" team has played
	protected ArrayList<Team> opponents; // an arraylist of type Team that stores the opponent "this" team faced in each Game
	private int wins; // number of wins
	private int losses; // number of losses
	private int pm; // number of points for a team minus number of points against
	private String teamName; // the name of the team - the only unchanging, identifying characteristic of each team
	
	public Team(String name) { // initializes a Team with the parameter of the team name; everything else default
		teamName = name;
		wins = 0;
		losses = 0;
		pm = 0;
		gamesPlayed = new ArrayList<Game>();
		opponents = new ArrayList<Team>();
	}
	
	public int getWins() {
		return wins;
	}
	public int getLosses() {
		return losses;
	}
	public void setWins(int w) {
		wins = w;
	}
	public void setLosses(int l) {
		losses = l;
	}
	public double getWinPct() { // the number of wins vs. the total number of games a team has played
		if (losses + wins > 0)
			return wins * 1.0 / (losses+wins);
		else
			return 0;
	}
	public int getPM() {
		return pm;
	}
	public void setPM(int x) {
		pm = x;
	}
	public String getName() {
		return teamName;
	}
	
	public double strengthOfSchedule() { // the average win percentage of the teams "this" team has faced
		double sum = 0;
		for (Team t: opponents) 
			sum += t.getWinPct();
		return sum / opponents.size();
	}
	
	public int compareTo(Team t) { // #1 team is "least" valued (aka #1.compareTo(#2) = -1)
		if (this.getWinPct() > t.getWinPct()) return -1; //first comparison: win percentage
		else if (this.getWinPct() < t.getWinPct()) return 1;
		else if (played(t) > -1) { // second comparison: head to head
			if (this.equals(gamesPlayed.get(played(t)).getLoser()))
				return 1;
			else return -1;
		}
		else if (this.pm > t.pm) return -1; // third comparison: plus/minus
		else if (this.pm < t.pm) return 1;
		else if (this.strengthOfSchedule() > t.strengthOfSchedule()) return -1; // final comparison: strength of schedule
		else if (this.strengthOfSchedule() < t.strengthOfSchedule()) return 1;
		else return 0; // if teams are still tied - will deal with this in the client
	}

	public String toString() {
		return "Team: " + teamName + "\nWins: " + wins + "\nLosses: " + losses + "\n" + getWinPct()+"\t"+strengthOfSchedule();
	}
	
	public int played(Team other) { // returns the game number where the two teams played, or -1 if they didn't
		return opponents.indexOf(other);
	}
	
	public boolean equals(Team t) { // equality solely determined by team name
		return this.teamName.equals(t.teamName);
	}
}

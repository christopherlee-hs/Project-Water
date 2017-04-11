public class Game {
	private Team team1;
	private Team team2;
	
	private int score1;
	private int score2;
	
	public int tiebreaker;

	public Game(Team a, Team b, int x, int y) { // initializes based on two teams playing in the game and each team's score
		team1 = a;
		team2 = b;
		score1 = x;
		score2 = y;
		team1.opponents.add(team2);
		team2.opponents.add(team1);
		team1.gamesPlayed.add(this);
		team2.gamesPlayed.add(this);
	}
	public Game(Team a, Team b, int x, int y, int z) { // same as first but with potential tiebreaker
		team1 = a;
		team2 = b;
		score1 = x;
		score2 = y;
		tiebreaker = z;
		team1.opponents.add(team2);
		team2.opponents.add(team1);
		team1.gamesPlayed.add(this);
		team2.gamesPlayed.add(this);
	}
	
	public String toString() {
		return team1 + ": " + score1 + "\n" + team2 + ": " + score2;
	}
	
	public Team getWinner() { // returns the winner of a game based on score and tiebreaker
		if (score1 > score2) return team1;
		else if (score2 > score1) return team2;
		else if (tiebreaker > 0) return team1;
		else return team2;
	}
	public Team getLoser() { // returns the loser of a game based on score and tiebreaker
		if (score1 > score2) return team2;
		else if (score2 > score1) return team1;
		else if (tiebreaker > 0) return team2;
		else return team1;
	}
	
	public void playGame() { // gives the winner a win, the loser a loss, and changes plus/minus appropriately
		Team winner = getWinner();
		Team loser = getLoser();
		winner.setWins(winner.getWins() + 1);
		loser.setLosses(loser.getLosses() + 1);
		winner.setPM(winner.getPM() + winMargin());
		loser.setPM(loser.getPM() - winMargin());
	}
	
	public int winMargin() { // to determine plus/minus of each team
		return Math.abs(score1 - score2);
	}
}

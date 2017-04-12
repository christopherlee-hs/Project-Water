import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class Tourney2 {
	private static String file;
	private static ArrayList<String> teamNames; // an arraylist of type String to store all the team names
	private static ArrayList<Team> teams; // an arraylist of type Team to store all the teams
	private static ArrayList<Game> gameList = new ArrayList<Game>(); // an arraylist of type Game to store all the games played
	private static DecimalFormat df = new DecimalFormat(".000");
	public static final int NUM_POOLS = 4;
	/*
	public static void main(String[] args) throws InvalidFormatException, IOException {
		new Tourney2("C://Users/217863/Dropbox/Programs/NAPW/teamNames.xlsx");
		for (Object x: teamsArray) System.out.println(x);
	}
	*/
	public Tourney2(String fileName) throws InvalidFormatException, IOException {
		file = fileName;
		teams = new ArrayList<Team>(); // an arraylist of all the teams in the tournament
		teamNames = new ArrayList<String>(); // an arraylist of all the teams' names
		XSSFWorkbook wb = new XSSFWorkbook(new File(file));
		setTeams(wb.getSheet("Team Names"));
		parseData(wb.getSheet("Game Results"));
		wb.close();
		Collections.sort(teams); // sorts the teams based on w%, head to head, plus/minus, strength of schedule
		writeToExcelFile(file);
	}
	
	public void parseData(XSSFSheet worksheet) throws IOException, InvalidFormatException {
		Iterator<Row> iterator = worksheet.iterator();
		Row currentRow = iterator.next(); // initializes currentRow, skips the first row
		while (iterator.hasNext()) {
			currentRow = iterator.next();
			Iterator<Cell> cellIterator = currentRow.iterator();
			ArrayList<String> temp = new ArrayList<String>(); // temporary arraylist to store the data in a given column
			while (cellIterator.hasNext()) {
				temp.add(cellIterator.next().toString());
			}
			int team1index = teamNames.indexOf(temp.get(0)); // finds the team's index
			int team2index = teamNames.indexOf(temp.get(1));
			Game currGame = null;
			
			if (temp.size() > 2) {
				if (temp.size() < 5 || temp.get(4).equals(""))
					currGame = new Game(teams.get(team1index), teams.get(team2index), (int) Double.parseDouble(temp.get(2)), (int) Double.parseDouble(temp.get(3)), 0);
				else
					currGame = new Game(teams.get(team1index), teams.get(team2index), (int) Double.parseDouble(temp.get(2)), (int) Double.parseDouble(temp.get(3)), (int) Double.parseDouble(temp.get(4)));
				gameList.add(currGame); // add each game to the gameList
				currGame.playGame(); // "plays game": adjusts each team's data (w,l,etc.)
			}
		}
	}
	
	public static void setTeams(XSSFSheet worksheet) throws InvalidFormatException {
		Iterator<Row> iterator = worksheet.iterator();
		Row currentRow = iterator.next();
		while (iterator.hasNext()) {
			currentRow = iterator.next();
			Iterator<Cell> cellIterator = currentRow.iterator();
			Cell c = cellIterator.next();
			teamNames.add(c.toString().trim()); // adds each team name to the ArrayList teamNames
		}
		for (String s: teamNames) // initializes all teams in the arraylist teams
			teams.add(new Team(s));
	}
	
	public static void writeToExcelFile(String fileName) throws IOException {
		// creates Excel file with the final ranking after prelim rounds
		XSSFWorkbook wb = new XSSFWorkbook(fileName);
		XSSFSheet sheet1;
		CreationHelper createHelper;
		Row row;
		try {
			sheet1 = wb.createSheet("Rankings");
		}
		catch (IllegalArgumentException e) {
			int temp = wb.getSheetIndex(wb.getSheet("Rankings"));
			wb.removeSheetAt(temp);
			sheet1 = wb.createSheet("Rankings");
		}
		createHelper = wb.getCreationHelper();
		row = sheet1.createRow(0); // create column headers
		row.createCell(0).setCellValue(createHelper.createRichTextString("Rank"));
		row.createCell(1).setCellValue(createHelper.createRichTextString("Team Name"));
		row.createCell(2).setCellValue(createHelper.createRichTextString("Wins"));
		row.createCell(3).setCellValue(createHelper.createRichTextString("Losses"));
		row.createCell(4).setCellValue(createHelper.createRichTextString("Win Percentage"));
		row.createCell(5).setCellValue(createHelper.createRichTextString("Plus/Minus"));
		for (int i = 0; i < teams.size(); i++) { // fill each row with team and data
			row = sheet1.createRow(i+1);
			row.createCell(0).setCellValue(i+1);
			row.createCell(1).setCellValue(createHelper.createRichTextString(teams.get(i).getName()));
			row.createCell(2).setCellValue(teams.get(i).getWins());
			row.createCell(3).setCellValue(teams.get(i).getLosses());
			row.createCell(4).setCellValue(Double.parseDouble(df.format(teams.get(i).getWinPct())));
			row.createCell(5).setCellValue(teams.get(i).getPM());
		}
		
		FileOutputStream fileOut = new FileOutputStream(fileName, true);
		wb.write(fileOut);
		fileOut.close();
		wb.close();
	}
}


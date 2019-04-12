function rankTeams(teamList, team1, team2, min, sec, winner) {
  // ranks the teams based on game results
  // higher score wins
  // if tie, tiebreaker determines: 1 = team 1 wins, 2 = team 2 wins
  
  /*
  Recording times:
  Record time of first win.
  If team who won first game wins overall, record that time.
  Otherwise, record 0:00.
  
  */
  
  /*
  Ranking order of priority
  1. win percentage
  2. head to head
  3. average time to win
  4. strength of schedule
  */
  
  
  // TODO: fix this section
  var label = teamList.shift(); // see if the first element of the list is a label
  
  if (String(label).toLowerCase() == "teams") { // if it is, remove it
    team1.shift();
    team2.shift();
    min.shift();
    sec.shift();
    winner.shift();
  }
  else { // if the first row of the column isn't a label, just add that thing back there
    teamList.unshift(label);
  }
  
  var numTeams = countf(teamList);
  var numGames = countf(team1);
  
  var inProgress = (! (countf(team1) == countf(team2) 
                    && countf(team2) == countf(winner)));
  
  /*
  if (! (countf(team1) == countf(team2) && countf(team2) == countf(score1)
         && countf(score1) == countf(score2) && countf(score2) == countf(tiebreaker))) {
    throw new Error("Please make sure each of the games is filled out correctly, with every cell in each of the team, score, and tiebreaker columns filled out");
  }
  */
  
  teamList = teamList.map(String);
  team1 = team1.map(String);
  team2 = team2.map(String);
  min = min.map(Number);
  sec = sec.map(Number);
  winner = winner.map(Number);
  
  // remove whitespace from team names
  for (var i = 0; i < numTeams; i++) {
    teamList[i] = teamList[i].trim();
    // also make these lowercase to find indices more easily later
  }
  for (var i = 0; i < numGames; i++) {
    team1[i] = team1[i].trim();
    team2[i] = team2[i].trim();
  }
  
  var teamStats = new Array(numTeams);
  fillWithNumArray(teamStats, 9);
  
  // 2d array, x = team #, y = stat
  /*
  0: team name
  1: games played
  2: games won
  3: win percentage
  4: total time
  5: list of opponents
  6: list of teams defeatei
  8: strength of schedule
  9: original (pre-sort) team index
  */
  
  // a bunch of zeroes so far
  
  // put in team names and
  // make the element at index 5 (of each array) an array to include team numbers
  
  var dum = "";
  for (var i = 0; i < numTeams; i++) {
    teamStats[i][0] = teamList[i];
    teamStats[i][5] = [];
    teamStats[i][6] = [];
    teamStats[i][8] = i;
  }
  
  // will make a second array of stats which will actually be displayed
  
  /* won't be needing these anymore
  var teamGames = new Array(numTeams);
  fill(teamGames, 0);
  var teamWins = new Array(numTeams);
  fill(teamWins, 0);
  */
  
  var t1, t2;
  
  for (var i = 0; i < numGames; i++) {
    // iterate through each game
    
    // if any of team/winner boxes are blank, skip over the game
    if ((min[i] == "" && sec[i] == "") || winner == null) {
      continue;
    }
    
    // if no winner recorded, throw an error
    /* TODO: delete this
    if (winner[i] == "") {
      throw new Error("Make sure to fill out the winner (row " + (i+2) + "). Enter a 1 if team 1 won or a 2 if team 2 won.");
    }*/
    
    // find team in the list of teams; if not there, throw an error
    t1 = teamList.map(function(v) {return v.toLowerCase();}).indexOf(team1[i].toLowerCase());
    if (t1 == -1) {
      throw new Error(team1[i] + " (row " + (i+2) + ") is not a valid team name!");
    }
    t2 = teamList.map(function(v) {return v.toLowerCase();}).indexOf(team2[i].toLowerCase());
    if (t2 == -1) {
      throw new Error(team2[i] + " (row " + (i+2) + ") is not a valid team name!");
    }
    
    // increase number of games played
    teamStats[t1][1]++;
    teamStats[t2][1]++;
    
    // add the opponent they just played to the list of teams
    teamStats[t1][5].unshift(t2);
    teamStats[t2][5].unshift(t1);
    
    if (winner[i] == 1) { 
      // team 1 wins
      teamStats[t1][2]++;
      teamStats[t1][4] += 60 * min[i] + sec[i]; // add time it took to win
      teamStats[t1][6].unshift(t2); // add t2 to teams t1 beat
    }
    else if (winner[i] == 2) { 
      // team 2 wins
      teamStats[t2][2]++;
      teamStats[t2][4] += 60 * min[i] + sec[i]; // add time it took to win
      teamStats[t2][6].unshift(t1); // add t1 to teams t2 beat
    }
    
    /*
    // add the points for
    teamStats[t1][4] += score1[i];
    teamStats[t2][4] += score2[i];
    
    // and points against
    teamStats[t1][5] += score2[i];
    teamStats[t2][5] += score1[i];
    */
  }
  
  // calculate win percentage
  
  for (var i = 0; i < numTeams; i++) {
    if (teamStats[i][1] > 0) {
      teamStats[i][3] = teamStats[i][2] / teamStats[i][1];
    }
    else {
      teamStats[i][3] = 0;
    }
    // teamStats[i][6] = teamStats[i][4] - teamStats[i][5]; (plus/minus)
  }
  
  // strength of schedule
  var percentages, count;
  for (var i = 0; i < numTeams; i++) {
    percentages = 0;
    count = 0;
    for (var j = 0; j < teamStats[i][5].length; j++) {
      percentages += teamStats[teamStats[i][5][j]][3]; // adds win % of each opponent
      count++;
    }
    if (count > 0) {
      teamStats[i][8] = percentages / count; // averages win % of opponents
    }
    else {
      teamStats[i][8] = 0;
    }
  }
  
  
  // TODO: finish actual rankings
  for (var i = 0; i < numTeams; i++) {
    if (teamStats[i][2] > 0) {
      teamStats[i][4] /= teamStats[i][2];
    }
    if (teamStats[i][2] > 0) {
      teamStats[i][4] = 240 - teamStats[i][4];
    }
  }
  
  qSort(teamStats, 0, numTeams - 1);
  
  //return teamStats;
  
  // stats to be displayed
  var num_cats = 8;
  var displayStats = new Array(numTeams);
  fillWithNumArray(displayStats, num_cats);
  
  /*
  0: rank
  1: team name
  2: games played
  3: wins
  4: losses
  5: win percentage
  6: average time
  7: strength of schedule
  */
  
  var m, s;
  var count = 0;
  for (var i = 0; i < numTeams; i++) { // fill with the stats of teams who have played at least one game
    if (teamStats[i][1] > 0) {
      displayStats[count][0] = count+1;
      displayStats[count][1] = teamStats[i][0];
      displayStats[count][2] = teamStats[i][1];
      displayStats[count][3] = teamStats[i][2];
      displayStats[count][4] = teamStats[i][1] - teamStats[i][2];
      displayStats[count][5] = teamStats[i][3].toFixed(3);
      m = (teamStats[i][4] / 60) | 0;
      s = (teamStats[i][4] % 60).toFixed(0);
      if (s < 10) {
        displayStats[count][6] = m + ":0" + s;
      }
      else {
        displayStats[count][6] = m + ":" + s;
      }
      displayStats[count][7] = teamStats[i][8].toFixed(3);
      
      count++;
    }
  }
  
  
  // add the labels
  displayStats.unshift(["Rank", "Team Name", "Games Played", "Wins", "Losses", 
                       "Win Percentage", "Average Time of Victory",
                       "Strength of Schedule"]);
  count++; // accounts for row of headers
  if (inProgress) {
    displayStats.unshift("Game results are still being updated, so these rankings are NOT final.")
    count++; // accounts for row with update message
  }
  return displayStats.slice(0, count);
}

function countf(array) {
  // counts the number of unempty cells in the given array
  var curr = 0;
  var i = 0;
  while (array[i] != "" && i < array.length) {
    curr++; 
    i++;
  }
  return curr;
}

function fill(array, value) {
  // fills the array with the value
  for (var i = 0; i < array.length; i++) {
    array[i] = value;
  }
  return array;
}

function fillWithNumArray(array, newLength) {
  // fills the array with empty arrays of length newLength
  var temp;
  for (var i = 0; i < array.length; i++) {
    temp = [];
    for (var j = 0; j < newLength; j++) {
      temp.unshift(0);
    }
    array[i] = temp;
  }
}

function isBetterThan(team1, team2) { 
  // returns true of team1 should outrank team2
  if (team1[3] > team2[3]) { // first tiebreak: win percentage
    return true;
  }
  else if (team2[3] > team1[3]) {
    return false;
  }
  else if (team1[6].indexOf(team2[9]) > -1) { // second tiebreak: head-to-head
    return true;
  }
  else if (team2[6].indexOf(team1[9]) > -1) {
    return false;
  }
  else if (team1[2] > team2[2]) { // fourth tiebreak: number of wins
    return true;
  }
  else if (team2[2] > team1[2]) {
    return false;
  }
  else if (team1[1] < team2[1]) { // fifth tiebreak: number of losses
    return true;
  }
  else if (team2[1] < team1[1]) {
    return false;
  }
  else if (team1[4] < team2[4]) { // third tiebreak: time
    return true;
  }
  else if (team2[4] < team1[4]) {
    return false;
  }
  else if (team1[6] > team2[6]) { // sixth tiebreak: strength of schedule
    return true;
  }
  else if (team2[6] > team1[6]) {
    return false;
  }
  return false;
}

function qSort(A, lo, hi) { 
  // uses quicksort to rank the teams
  var p;
  if (lo < hi) {
    p = qPartition(A, lo, hi);
    qSort(A, lo, p);
    qSort(A, p + 1, hi);
  }
}

function qPartition(A, lo, hi) { 
  // part of quicksort recursion
  var pivot = A[(lo + hi) / 2 | 0];
  var i = lo - 1;
  var j = hi + 1;
  var temp;
  while(true) {
    do {
      i++;
    } while (isBetterThan(A[i], pivot));
    do {
      j--
    } while (isBetterThan(pivot, A[j]));
    if (i >= j) {
      return j;
    }
    temp = A[i];
    A[i] = A[j];
    A[j] = temp;
  }
}

function myFunction() {
  var sheet = SpreadsheetApp.getActiveSheet();
  var data = sheet.getDataRange().getValues();
  for (var i = 0; i < data.length; i++) {
    Logger.log('Product name: ' + data[i][0]);
    Logger.log('Product number: ' + data[i][0]);
  }
}

function addProduct() {
  var sheet = SpreadsheetApp.getActiveSheet();
  return [['Cotton Sweatshirt XL', 'css004']];
}

# Project-Water

Google Script to be used with a Google Sheet. java folder contains deprecated method of ranking teams -- you can ignore this.
Defines a function rankTeams which takes six columns of cells (e.g. A:A) as its arguments.

General note: an equals sign at the beginning of a cell lets Google Sheets know that it has to evaluate an expression. For example, if you type in =1+2, when you hit enter it will return 3, but if you type in 1+2, it will return 1+2.

Using the function:
To run the function, enter in a Google Sheets cell something like:
=rankTeams(A:A, B:B, C:C, D:D, E:E, F:F)
where:
A:A is a column containing the list of teams
B:B is a column containing the first team of each match
C:C is a column containing the second team of each match
D:D is a column containing the number of minutes remaining in each match when victory is decided
E:E is a column containing the number of seconds remaining in each match when victory is decided
F:F is a column denoting the winner of each match; each cell is 1 if the first team wins or 2 if the second team wins

Data input:
Each cell in A:A corresponds to a team, while each cell in B:B, C:C, D:D, E:E, and F:F correspond to a match, so it is useful to keep the data separated into separate sheets files. It is also nice to have the rankings in a completely separate file to the rest of the data. To do this, use Google Sheets' built in importRange function, which allows you to access data from another sheet. For example, if the column A:A is from the spreadsheet at the link https://docs.google.com/spreadsheets/d/abcdefg/edit#gid=0 on a sheet titled Names, you can type:
=importRange("https://docs.google.com/spreadsheets/d/abcdefg/edit#gid=0", "Names!A:A")
to get the desired range. Don't forget the quotation marks!

TODO: Finish this file

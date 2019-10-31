/* Grace Waylen
 * TextExcel TEALS 
 * 12/21/18
 */
package textExcel;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Spreadsheet implements Grid{
	private int rows = 20;
	private int cols = 12; 
	Cell [][] cellArray; 
	
	// constructs a spreadsheet with 20 rows and 12 columns 
	// generates a 12 x 20 array of cell arrays that are all empty cells
	public Spreadsheet () {
		this.rows = 20;
		this.cols = 12; 		
	        cellArray = new Cell[rows][cols];
	    for (int i = 0; i < getRows(); i++) {
	        for (int j = 0; j < getCols(); j++) {
	           cellArray[i][j] = new EmptyCell(i, j);
	         }
	    }
	 }
	
	@Override
	// returns the number of rows
	public int getRows(){
		return rows;
	}

	@Override
	//return the number of columns
	public int getCols(){
		return cols;
	}

	@Override
	//returns a cell given a specific location in the array
	public Cell getCell(Location loc) {
		return cellArray[loc.getRow()][loc.getCol()]; 
	}
	
	@Override
	// process the input to make changes, save, and open the grid
	public String processCommand(String command){
		
		if(command.equals("")) {
			return ""; 
		}
	
		if(usableInput(command).size() == 1) { // if the command only contains one word
			if(command.toLowerCase().equals("clear")) {
				return clearAll();
				} 
			if(validLocation(command.toUpperCase())) {
					return cellInspection(command); 
				}
			return "ERROR: That is not a valid command"; 
			}
		
		if(usableInput(command).size() == 2) { // if the command contains two words
			if(usableInput(command).get(0).toLowerCase().equals("save")) {
				try {
					saveToFile(usableInput(command).get(1));
					return "File Saved"; 
				} catch (IOException e) {
					return "ERROR: that is not a vaild file name"; 
				}
				
			} if(usableInput(command).get(0).toLowerCase().equals("open")) {
				try {
					readFromFile(usableInput(command).get(1));
					return getGridText();
				} catch (FileNotFoundException e) {
					return "ERROR: that is not a vaild file name";
				}
			}
				
			if(usableInput(command).get(0).toLowerCase().equals("sorta")) {
				return sorta(usableInput(command).get(1)); 
			}
			if(usableInput(command).get(0).toLowerCase().equals("sortd")) {
				return sortd(usableInput(command).get(1)); 
			} else {
				return clearCell(usableInput(command).get(1)); 	
				} 
		}
			
		if (usableInput(command).get(2).indexOf("\"") != usableInput(command).get(2).lastIndexOf("\"")){// if there are quotes its a text command 
			return setText(usableInput(command).get(0), usableInput(command).get(2)); 
		}
		
		if(usableInput(command).get(2).indexOf('(') != -1 &&  usableInput(command).get(2).indexOf(')') != -1) {// if there are parentheses its a formula command
			String text = usableInput(command).get(2).trim();
			if(validLocation(text.substring(1,text.length() - 1))){
				return setFormula(usableInput(command).get(0), usableInput(command).get(2), cellArray);
			}
			if(text.contains("+")||text.contains("/")||text.contains("*")) {
				return setFormula(usableInput(command).get(0), usableInput(command).get(2), cellArray); 
			}
			if(text.contains("-") && text.charAt(text.indexOf('-')-1) == ' ') { 
				return setFormula(usableInput(command).get(0), usableInput(command).get(2), cellArray);
			}
			if(text.toLowerCase().contains("sum")||text.toLowerCase().contains("avg")) {
				String location1 = text.substring(6, text.indexOf('-')).toUpperCase();
				String location2 = text.substring(text.indexOf('-') + 1, text.lastIndexOf(' ')).toUpperCase();
				if(text.indexOf('-') != -1) {
						if(validLocation(location1) && validLocation(location2)){
							return setFormula(usableInput(command).get(0), usableInput(command).get(2), cellArray);
						} else { 
							return "ERROR: That is not a valid command"; 
						}
					}
				if(validLocation(location1)){
					return setFormula(usableInput(command).get(0), usableInput(command).get(2), cellArray);
				}
			} 
			try {
				Double.parseDouble(text.substring(1, text.length()-1).trim()); // is it just an number?
				return setFormula(usableInput(command).get(0), usableInput(command).get(2), cellArray);// yes, make a new formula cell
				} catch (Exception e) {// if its got () and none of those things, it is a mistake. 
					return "ERROR: That is not a valid command"; 
				}
		
			}
		
		if (usableInput(command).get(2).indexOf('%') != -1){// if there is a percentage, its a percentage cell
			return setPercent(usableInput(command).get(0), usableInput(command).get(2)); 
			} else {
				return setValueDouble(usableInput(command).get(0),(usableInput(command).get(2))); // if none of those things are true, its a value command 
				}
	}
	
	@Override
	//returns the grid as a string
	public String getGridText() { 
		String grid = String.format("%3s", "");
		char letter = 'A';
		for(int i = 0; i < cols; i++) {
			grid = grid + String.format("|%-10s", "" + (char)(letter + i) ); 
		}
		grid = grid + "|\n"; 
		for(int i = 0; i < rows; i++) {
			grid = grid + String.format("%-3s", i + 1);
				for (int j = 0; j < cols; j++) {
					grid += String.format("|%-10s", cellArray[i][j].abbreviatedCellText());
				}
			grid += "|\n"; 
		}	 
	return grid; 
	}
	
	// breaks a string into an Array List by token
	private ArrayList<String>  usableInput(String text) {
		ArrayList<String> input = new ArrayList<String>(); 
		Scanner sc = new Scanner(text); 
		for(int i = 0; i < 3; i++) {
			if(sc.hasNext()) {
				if(i == 2) {
					input.add(sc.nextLine()); 
					} else {
						input.add(sc.next());		
					}
				}	
			}
		sc.close(); 
		return input;
	}
	
	// saves the grid to a file 
	//contains information on type, location, and value
	private void saveToFile(String fileName) throws IOException {
		File myData = new File(fileName);
		FileWriter fileWriter = new FileWriter(myData);
		for(int i = 0; i < rows; i++) {
			for(int j = 0; j < cols; j++) {
				String cellIdentifier = "";	
				if(!(cellArray[i][j] instanceof EmptyCell)) {
					SpreadsheetLocation loc = new SpreadsheetLocation(i,j); 
					if(cellArray[i][j] instanceof TextCell) {
						cellIdentifier = "TextCell"; 
					}
					if(cellArray[i][j] instanceof ValueCell) {
						cellIdentifier = "ValueCell"; 
					}
					if(cellArray[i][j] instanceof FormulaCell) {
						cellIdentifier = "FormulaCell"; 
					}
					if(cellArray[i][j] instanceof PercentCell) {
						cellIdentifier = "PercentCell"; 
					}
					fileWriter.write(loc.toString() + "," + cellIdentifier + ","  + cellArray[i][j].fullCellText() + "\n");  
				}
			}
		}
		fileWriter.close(); 
	}
	
	// creates a grid from a csv file
	public void readFromFile(String fileName) throws FileNotFoundException {
		Scanner fileScanner = new Scanner(new File(fileName)); 
		while (fileScanner.hasNextLine()) {
			String line = fileScanner.nextLine(); 
			String name = line.substring(0, line.indexOf(',')); 
			String type = line.substring(line.indexOf(',') + 1, line.lastIndexOf(',')); 
			String content = line.substring(line.lastIndexOf(',') + 1, line.length()); 
			if(type.equals("TextCell")) {
				setText(name, content); 
			}if(type.equals("PercentCell")) {
				setPercent(name, content);
			}if(type.equals("FormulaCell")) {
				setFormula(name, content, cellArray);
			}if(type.equals("ValueCell")) {
				setValueDouble(name, content); 
			}
		}
		fileScanner.close(); 
	}
	
	// displays text from a cell given a location
	private String cellInspection(String location)  {
		SpreadsheetLocation temp = new SpreadsheetLocation(location.toUpperCase()); 
		return getCell(temp).fullCellText(); 	
	}
	
	//sets a cell at a given location to a text cell
	private String setText(String location, String text)  {
		SpreadsheetLocation loc = new SpreadsheetLocation(location.toUpperCase());
		TextCell cell = new TextCell(loc, text); 
		cellArray[loc.getRow()][loc.getCol()] = cell; 
		return getGridText(); 
	}
	
	//sets a cell at a given location to a value cell
	private String setValueDouble (String location, String value) {
		try {
		SpreadsheetLocation loc = new SpreadsheetLocation(location.toUpperCase());
		ValueCell cell = new ValueCell(loc, value); 
		cellArray[loc.getRow()][loc.getCol()] = cell;
		return getGridText(); 
		} catch (Exception e) {
			return "ERROR: That is not a valid command"; 
		}
	}
	
	//sets a cell at a given location to a percent cell
	private String setPercent(String location, String percent) {
		try { 
		SpreadsheetLocation loc = new SpreadsheetLocation(location.toUpperCase());
		PercentCell cell = new PercentCell(loc, percent); 
		cellArray[loc.getRow()][loc.getCol()] = cell;
		return getGridText();
		} catch (Exception e) {
			return "ERROR: That is not a valid command"; 
		}
	}
	
	//sets a cell at a given location to a formula cell
	private String setFormula(String location, String formula, Cell[][] cellArray)  {
		try {
			SpreadsheetLocation loc = new SpreadsheetLocation(location.toUpperCase());
			FormulaCell cell = new FormulaCell(loc, formula, cellArray); 
			cellArray[loc.getRow()][loc.getCol()] = cell;
		} catch (Exception e) {
			return "ERROR: That is not a valid command"; 
		}
		return getGridText();
	}
	
	//sets a cell at a given location to an empty cell
	private String clearCell(String location) {
		try {
			SpreadsheetLocation loc = new SpreadsheetLocation(location.toUpperCase());
			EmptyCell cell = new EmptyCell(loc); 
			cellArray[loc.getRow()][loc.getCol()] = cell; 
			return getGridText(); 
		} catch (Exception e) {
			return "ERROR: That is not a valid command"; 
		}
	}
	
	// sets all the cells in the array to empty cells
	private String clearAll() { 
		try {
			for(int i = 0; i < rows ; i++ ) {
				for (int j = 0; j < cols; j++) {
					cellArray[i][j] = new EmptyCell(i,j); 
				}
			}
			return getGridText(); 
		} catch (Exception e) {
			return "ERROR: That is not a valid command "; 
		}
	}
	
	// sorts text or real cells in ascending order given a range
	public String sorta(String cellRange)  {
		boolean startAgain = true;  
		String [] locationArray = cellRange.split("-"); 
		SpreadsheetLocation loc1 = new SpreadsheetLocation(locationArray[0].trim().toUpperCase()); 
		SpreadsheetLocation loc2 = new SpreadsheetLocation(locationArray[1].trim().toUpperCase());
		
		if(getCell(loc1) instanceof ValueCell) {
			ArrayList<Double> tempArrayDouble = new ArrayList<Double>(); 
			double lesserValueD; 
			double greaterValueD; 
				int k = 0; 
				for (int i = loc1.getRow(); i <= loc2.getRow(); i++) {
					for (int j = loc1.getCol(); j <= loc2.getCol();  j++) {
						tempArrayDouble.add(k, Double.parseDouble(cellArray[i][j].fullCellText())); 
						k++; 	 
					}
				}
				do {
					for(int i = 0; i < tempArrayDouble.size() -1 ; i ++) {
						if(tempArrayDouble.get(i).compareTo(tempArrayDouble.get(i+1)) > 0){
							lesserValueD = tempArrayDouble.get(i); 
							greaterValueD = tempArrayDouble.get(i +1); 
							tempArrayDouble.set(i, greaterValueD); 
							tempArrayDouble.set(i + 1, lesserValueD); 
							startAgain = true;
							break; 
						} else {
							startAgain = false;  
						}
					}
				} while(startAgain);
				String cellName;
				int m = 0; 
				for (int i = loc1.getRow(); i <= loc2.getRow(); i++) {
					for (int j = loc1.getCol(); j <= loc2.getCol();  j++) {
						cellName = "" + (char) (j + 65) + (i + 1);
						setValueDouble(cellName, tempArrayDouble.get(m).toString()); 
						m++; 
					}
				}
				return getGridText(); 
			} else {
				ArrayList<String> tempArray = new ArrayList<String>();
				String lesserValue = "";
				String greaterValue =""; 
				int k = 0; 
				for (int i = loc1.getRow(); i <= loc2.getRow(); i++) {
					for (int j = loc1.getCol(); j <= loc2.getCol();  j++) {
						tempArray.add(k, cellArray[i][j].fullCellText()); 
						k++; 	 
					}
				} 
			do {
				for(int i = 0; i < tempArray.size() -1 ; i ++) {
					if(tempArray.get(i).compareToIgnoreCase(tempArray.get(i+1)) > 0){
						lesserValue = tempArray.get(i); 
						greaterValue = tempArray.get(i +1); 
						tempArray.set(i, greaterValue); 
						tempArray.set(i + 1, lesserValue); 
						startAgain = true;
						break; 
					} else {
						startAgain = false;  
					}
				}
			} while(startAgain);	
		String cellName;
		int m = 0; 
		for (int i = loc1.getRow(); i <= loc2.getRow(); i++) {
			for (int j = loc1.getCol(); j <= loc2.getCol();  j++) {
				cellName = "" + (char) (j + 65) + (i + 1);
				if(cellArray[i][j] instanceof TextCell) {
					setText(cellName, tempArray.get(m)); 
					m++;
				} 
			}
		}
	}
		return getGridText(); 
	}
	
	// sorts text or real cells in descending order given a range
	public String sortd(String cellRange)  {
		boolean startAgain = true; 
		
		String [] locationArray = cellRange.split("-"); 
		SpreadsheetLocation loc1 = new SpreadsheetLocation(locationArray[0].trim().toUpperCase()); 
		SpreadsheetLocation loc2 = new SpreadsheetLocation(locationArray[1].trim().toUpperCase());
		
		if(getCell(loc1) instanceof ValueCell) {
			ArrayList<Double> tempArrayDouble = new ArrayList<Double>(); 
			double lesserValueD; 
			double greaterValueD; 
			int k = 0; 
			for (int i = loc1.getRow(); i <= loc2.getRow(); i++) {
				for (int j = loc1.getCol(); j <= loc2.getCol();  j++) {
					tempArrayDouble.add(k, Double.parseDouble(cellArray[i][j].fullCellText())); 
					k++; 	 
				}// j
			}// i 
			do {
				for(int i = 0; i < tempArrayDouble.size() -1 ; i ++) {
					if(tempArrayDouble.get(i) < (tempArrayDouble.get(i+1))){
						lesserValueD = tempArrayDouble.get(i); 
						greaterValueD = tempArrayDouble.get(i +1); 
						tempArrayDouble.set(i, greaterValueD); 
						tempArrayDouble.set(i + 1, lesserValueD);
						startAgain = true;
						break; 
					} else {
						startAgain = false;  
					}
				}
			} while(startAgain);
			String cellName;
			int m = 0; 
			for (int i = loc1.getRow(); i <= loc2.getRow(); i++) {
				for (int j = loc1.getCol(); j <= loc2.getCol();  j++) {
					cellName = "" + (char) (j + 65) + (i + 1);
					setValueDouble(cellName, tempArrayDouble.get(m).toString()); 
					m++; 
				}//j
			}//i
			return getGridText(); 
		} else {// creates an Array with an alpha list of strings 
			ArrayList<String> tempArray = new ArrayList<String>();
			String lesserValue = "";
			String greaterValue ="";
			int k = 0; 
			for (int i = loc1.getRow(); i <= loc2.getRow(); i++) {
				for (int j = loc1.getCol(); j <= loc2.getCol();  j++) {
					tempArray.add(k, cellArray[i][j].fullCellText()); 
					k++; 	 
				}
			}
			do {
			for(int i = 0; i < tempArray.size() -1 ; i ++) {
				if(tempArray.get(i).compareToIgnoreCase(tempArray.get(i+1)) < 0){
					lesserValue = tempArray.get(i + 1); 
					greaterValue = tempArray.get(i); 
					tempArray.set(i + 1, greaterValue); 
					tempArray.set(i, lesserValue); 
					startAgain = true;
					break; 
					} else {
						startAgain = false;  
					}
				}
			} while(startAgain); 
		String cellName;
		int m = 0; 
		for (int i = loc1.getRow(); i <= loc2.getRow(); i++) {
			for (int j = loc1.getCol(); j <= loc2.getCol();  j++) {
				cellName = "" + (char) (j + 65) + (i + 1);
					if(cellArray[i][j] instanceof TextCell) {
						setText(cellName, tempArray.get(m)); 
						m++;
					} else {
						setValueDouble(cellName, tempArray.get(m)); 
						m++;
					}
				}
			}
			return getGridText(); 
		}
	}
	
	// checks to see if a given string is a valid location
	public  boolean validLocation(String location) {
		location = location.toUpperCase().trim(); 
		if(location.length() < 2 || location.length() > 4) {
			return false;
		}
		if((location.charAt(0)) >  'L'){
			return false; 
		}
		if(location.charAt(0)  < 'A'){
			return false; 
		}
		if(Integer.parseInt(location.substring(1, location.length())) < 1){		
			return false; 
		}
		if(Integer.parseInt(location.substring(1, location.length())) > 20){
			return false; 
		}
		return true; 
	}
	
	
	

	


}// end of class 

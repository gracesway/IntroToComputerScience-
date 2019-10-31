/* Grace Waylen
 * TextExcel TEALS 
 * 12/21/18
 */

package textExcel;

import java.util.ArrayList;
import java.util.Scanner;

// class constructs and calculates the values for a formula cell
class FormulaCell extends RealCell {
	
	Cell [][] cellArray;
	Spreadsheet spreadsheet; 
	String formula; 
	boolean error = false; 
	
	// constructor that takes a location and a formula 
	public FormulaCell(Location location, String formula) {
		text = formula;  
		//this.formula = formula; 
		this.location = location; 
	}
	
	//constructor that takes a location, formula, and an array of Cell arrays
	public FormulaCell(Location location, String formula, Cell [][] cellArray) {
		text = formula; 
		this.cellArray = cellArray; 
		this.location = location; 
		//this.formula = formula;
	}
	
	@Override
	// returns the full formula as it was entered
	public String fullCellText() {
		return text.trim(); 
	}
	
	@Override 
	// returns a double value from entered formula
	public double getDoubleValue()  {
		return calculateFormula(text); 	
	}
	
	@Override 
	// returns the 10 character text to appear in grid
	// returns #ERROR if error is true
	public String abbreviatedCellText() {
		
		String abText = null;
		abText ="" + getDoubleValue();
		
		if(error) {
			abText= "#ERROR"; 
		}
	
		if(abText.length() >= 12) {
			return  abText.substring(0, 10);
		}
		while(abText.length() != 10) {
			abText += " "; 
		}
		return abText;
	}
	
	// returns a double given a valid formula, including references to other cells
	// follows order of operations, * and / left to right, then + and - left to right
	// sets error to true when invalid or circular commands are given. 
	double calculateFormula(String text) {
		error = false; 
		double total = 0;
		String [] stringArray = text.substring(2,text.length() - 1).trim().split(" ");
		if(stringArray[0].toLowerCase().equals("sum") || stringArray[0].toLowerCase().equals("avg")) {
			String [] locationArray = stringArray[1].split("-"); 
			SpreadsheetLocation loc1 = new SpreadsheetLocation(locationArray[0].trim().toUpperCase()); 
			SpreadsheetLocation loc2 = new SpreadsheetLocation(locationArray[1].trim().toUpperCase());
			if(stringArray[0].trim().toLowerCase().equals("sum")) {
				return calSum(loc1, loc2, false);
			}
			if(stringArray[0].trim().toLowerCase().equals("avg")){
				return calSum(loc1, loc2, true); 
			}	
		} else {
			ArrayList<String> array = new ArrayList<String>(); 
			Scanner sc = new Scanner(text.substring(2,text.length() - 1).trim());
			
			try {
				while (sc.hasNext()) {
					String scannerText = sc.next().trim(); 
					if(validLocation(scannerText)) {
						SpreadsheetLocation location = new SpreadsheetLocation(scannerText.toUpperCase());
						if(cellArray[location.getRow()][location.getCol()] instanceof PercentCell) {
							array.add(cellArray[location.getRow()][location.getCol()].fullCellText());
						} else { 
							if(!(cellArray[location.getRow()][location.getCol()] instanceof RealCell) || cellArray[location.getRow()][location.getCol()].abbreviatedCellText().trim().equals("#ERROR")) { 
								error = true;
								sc.close();
								return 0;  
								} else {
									array.add(cellArray[location.getRow()][location.getCol()].abbreviatedCellText());
								}
							}
						} else {
							array.add(scannerText);
						}
					}
				sc.close();
			} catch(StackOverflowError e) { 
				error = true; 
				return 0;
			}
			int countOfAdditionAndSubtraction = countSpecificOperator(array, "+" , "-");   
			int countOfMultiplicationAndDivision = countSpecificOperator(array, "*", "/");  
			for (int m = 0; m < countOfMultiplicationAndDivision; m++) {
				for(int i = 0; i < array.size(); i++) {
					if(array.get(i).equals("*")) {
						String value ="" + (Double.parseDouble(array.get(i-1)) * Double.parseDouble(array.get(i +1))); 
						array.set(i,value);
						array.remove(i-1); 
						array.remove(i); 
						break; 
					}
					if(array.get(i).equals("/")) {
						String value ="" + (Double.parseDouble(array.get(i-1)) / Double.parseDouble(array.get(i +1)));
						if( Double.parseDouble(array.get(i +1)) == 0){
							error = true; 
							return 0;
						}
						array.set(i,value);
						array.remove(i-1); 
						array.remove(i); 
						break; 
					}
				}
			}
			for (int a = 0; a < countOfAdditionAndSubtraction; a++) {
				for(int i = 0; i < array.size(); i++) {
					if(array.get(i).equals("+")) {
						String value ="" + (Double.parseDouble(array.get(i-1)) + Double.parseDouble(array.get(i +1))); 
						array.set(i,value);
						array.remove(i-1); 
						array.remove(i); 
						break; 
					}
					if(array.get(i).equals("-")) {
						String value ="" + (Double.parseDouble(array.get(i-1)) - Double.parseDouble(array.get(i +1))); 
						array.set(i,value);
						array.remove(i-1); 
						array.remove(i); 
						break; 
					}
				}
			}
			value = Double.parseDouble(array.get(0)); 
			}	
		return value;
	}
	
	// tests to see if a location is valid
	public  boolean validLocation(String location) {
		location = location.toUpperCase(); 
		if(location.length() < 2) {
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
	
	// counts the number of strings in an array that are equal to two other strings
	public  int countSpecificOperator(ArrayList<String> array, String sign1, String sign2){
		int countOfSign = 0; 
		for (int i = 0; i < array.size(); i++) {
			if (array.get(i).equals(sign1)||array.get(i).equals(sign2)) {
				countOfSign ++; 
			}
		}
		return countOfSign; 
	}
	
	// calculates the sum or average of a set of cells between loc1 and loc2 
	private double calSum(SpreadsheetLocation loc1, SpreadsheetLocation loc2, boolean average) { 
		double total = 0;  
		int count = 0; 
		for (int i = loc1.getRow(); i <= loc2.getRow(); i++) {
			for (int j = loc1.getCol(); j <= loc2.getCol();  j++) { 
				if(cellArray[i][j].abbreviatedCellText().trim().equals("#ERROR")|| !(cellArray[i][j] instanceof RealCell)) {
					error = true; 
					return 0; 
				} else {		
					try {
						total += Double.parseDouble(cellArray[i][j].fullCellText()); 
						count ++;
					} catch (Exception e) {
						total +=  Double.parseDouble(cellArray[i][j].abbreviatedCellText()); 
						count ++;
						}
				} 
			}
		}
		if(average) { 
			return total / count; 
		} else {
			return total;
		}
	}
	

}// end of class
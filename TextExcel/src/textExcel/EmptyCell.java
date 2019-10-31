/* Grace Waylen
 * TextExcel TEALS 
 * 12/21/18
 */


package textExcel;

public class EmptyCell implements Cell{
	SpreadsheetLocation location;
	
	// constructor for an empty cell that takes a row and col int
	public EmptyCell(int row, int col) { 
		char letter = (char) (col + 64); 
		SpreadsheetLocation location = new SpreadsheetLocation("" + letter + "" +  row);
		this.location = location;	 
	}
	
	// constructor for an empty cell that takes a location
	public EmptyCell(SpreadsheetLocation location) {
		this.location = location; 
	}
	
	@Override
	//returns a string of 10 empty spaces to format the grid and pass one of the tests
	public String abbreviatedCellText() {
		// TODO Auto-generated method stub
		return "          ";
	}

	@Override
	// returns a string of nothing 
	public String fullCellText() {
		// TODO Auto-generated method stub
		return "";
	}
}

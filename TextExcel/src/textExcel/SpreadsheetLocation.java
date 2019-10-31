/* Grace Waylen
 * TextExcel TEALS 
 * 12/21/18
 */
package textExcel;

//class builds a location object that holds information about a cell's row and col
public class SpreadsheetLocation implements Location {
	private int row; 
	private int col; 
	
    // constructor taking the name of the cell as a string including a number and a letter
    public SpreadsheetLocation(String cellName) {
    	col = (int) (cellName.charAt(0) - 65); 
    	row = Integer.parseInt(cellName.substring(1, cellName.length())) - 1; 	
    }
    
    @Override
    // returns the cell's row
    public int getRow() {
        return row;
    }

    @Override
    // returns the cell column as a number
    public int getCol() {
        return col;
    }
    
    // constructor taking the row and column information
    public SpreadsheetLocation(int row, int col){
        this.row = row; 
        this.col = col; 
    }
    
    // returns this location as a letter and a number
    public String toString() {
    	return "" + (char) (col + 65) + (row + 1); 
    }
    
}

   
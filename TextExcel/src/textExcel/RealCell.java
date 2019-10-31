/* Grace Waylen
 * TextExcel TEALS 
 * 12/21/18
 */

package textExcel;

public class RealCell implements Comparable<RealCell>, Cell  {
	protected Location location; 
	protected String text;
	protected double value;
	
	// RealCell Constructor, cell is empty when constructed
	public RealCell() {
		}

	@Override
	// sets 10 character limit for text to be displayed by the grid
	public String abbreviatedCellText() {
		String abText ="" + getDoubleValue(); 
	
		if(abText.length() >= 12) {
			return  abText.substring(0, 10);
		}
		while(abText.length() != 10) {
			abText += " "; 
		}
		return abText;
	}

	// returns the double value
	public double getDoubleValue() {
		return value; 
	}

	@Override
	// returns the original text 
	public String fullCellText() {
		return text;
	}
	
	@Override
	// compares the value of this cell to the value of another real cell 
	// follows compareTo convention
	public int compareTo(RealCell cell) { 
		if(getDoubleValue() > cell.getDoubleValue()) { 
			return 1;
			}
		if(getDoubleValue() < cell.getDoubleValue()) { 
			return -1;
			}
		return 0;
	}  
	
	
}




/* Grace Waylen
 * TextExcel TEALS 
 * 12/21/18
 */

package textExcel;

// class for cell that holds text information 
public class TextCell implements Comparable<TextCell>, Cell{
	Location location;
	String name;
	String text; 

	// constructor that takes a location and the text
	public TextCell(Location location, String text) { 
		this.text = text.trim(); 
		this.location = location; 
	}

	@Override
	// returns the text for the grid, limits it to 10 characters
	public String abbreviatedCellText() {
		String abText = text; 
		if(abText.length() >= 12) {
			return  abText.substring(abText.indexOf("\"") + 1, abText.indexOf("\"") + 11);
		}
		abText = abText.substring(abText.indexOf("\"") + 1, abText.length() - 1);
		while(abText.length() != 10) {
			abText += " "; 
		}
		return abText;
	}

	@Override
	// returns the full text that was entered
	public String fullCellText() {
		return text;
	}
	
	@Override
	// compares this text cell to another
	// follows compareTo() convention
	public int compareTo(TextCell cell) { 
		return fullCellText().compareToIgnoreCase(cell.fullCellText()) ;
	}


}

/* Grace Waylen
 * TextExcel TEALS 
 * 12/21/18
 */

package textExcel;

class PercentCell extends RealCell {
	
	
	// constructs a Percent cell given a string with or without a percent sign
	// sets value to the percent as a decimal 
	public PercentCell(Location location, String percent) { 
		if(percent.indexOf('%') != -1) { // entered with a % sign (55%)
			value = Double.parseDouble(percent.substring(0, percent.indexOf('%')))/100;
		} else { // entered as a decimal
			value = Double.parseDouble(percent.trim());
		}
		this.location = location; 
	}
	
	
	public String fullCellText() {
		return "" + value; 
	}
		
	
	public String abbreviatedCellText() {
		String abText = "" + ((int) (value *100)) + "%";    
		if(abText.length() >= 12) {
			return  abText.substring(0, 10);
		}
		while(abText.length() != 10) {
			abText += " "; 
		}
		return abText;
	}
	
	public double getDoubleValue() {
		return value; 
	}
	

	
	

}
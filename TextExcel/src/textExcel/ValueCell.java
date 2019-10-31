/* Grace Waylen
 * TextExcel TEALS 
 * 12/21/18
 */

package textExcel;

import java.math.BigDecimal;

class ValueCell extends RealCell {
	 
	public ValueCell(Location location, String value) {
		this.value = Double.parseDouble(value); 
		this.location = location; 
		text =  value;
	}
	
	@Override
	public String fullCellText() {
		if(Math.abs(value) > 10000000) {
			BigDecimal value = new BigDecimal(this.value); 
			return value.toString(); 
		}
		return text.trim(); 
	}

}


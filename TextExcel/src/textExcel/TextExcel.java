/* Grace Waylen
 * TextExcel TEALS 
 * 12/21/18
 */

package textExcel;

import java.util.Scanner;

// class contains the main method and code for asking for input 
public class TextExcel
{
	public static void main(String[] args) throws Exception{
	
		Spreadsheet spreadsheet = new Spreadsheet(); 
		System.out.print(spreadsheet.getGridText());
		System.out.println("\nCommand: "); 
		Scanner sc = new Scanner(System.in); 
		while (sc.hasNext()) { 
			String command = sc.nextLine();
			if(command.toLowerCase().equals("quit")) {
				System.out.println("Thank you");
				sc.close();
				break; 
			}
			System.out.print(spreadsheet.processCommand(command));
			System.out.println("\nCommand: "); 	
		}
		sc.close(); 
	}

}

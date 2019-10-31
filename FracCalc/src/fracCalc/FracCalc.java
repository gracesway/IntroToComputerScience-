/*Grace Waylen 
 * FracCalc TEALS
 * 12/21/18
 */ 

package fracCalc;

import java.util.Scanner;

public class FracCalc {
	static int wholeString; 
	static int wholeAnswer =0; 
	static int denominatorString; 
	static int denominatorAnswer = 1; 
	static int numeratorString; 
	static int numeratorAnswer = 0; 
	static boolean keepGoing = true;
	static String operation; 

    public static void main(String[] args) {	
    	Scanner sc = new Scanner(System.in);
    	while (keepGoing) {
    		System.out.print("Please enter your equation:");
    		while (sc.hasNext()) {
    			String str = sc.nextLine(); 
    			if(str.toLowerCase().equals("quit")) {
    				keepGoing = false; 
    				sc.close();
    				continue; 
    			}
    			System.out.println(produceAnswer(str)); 
    			System.out.print("Please enter your equation:");
    			}
    		}
    	sc.close();
    }
    
// given an equation in string format, parses string into numbers and operators, makes those numbers improper fractions with common 
// denominators, performs the operation, reduces the fractions, turns it into a mixed number and formats that number correctly 
// handles errors when denominator is 0 and when multiple operators are included 
    public static String produceAnswer(String input) { 
        Scanner stringScanner = new Scanner(input); 
        String number = stringScanner.next(); 
        
        wholeAnswer = findWhole(number); 
        numeratorAnswer =findNumerator(number);
        denominatorAnswer = findDenominator(number); 
   
        if(denominatorAnswer == 0) {
        	stringScanner.close();
        	return ("ERROR: Cannot divide by zero");
         }
        reduceFraction(numeratorAnswer, denominatorAnswer);
        numeratorAnswer = makeImproperFractionNumerator(wholeAnswer, numeratorAnswer, denominatorAnswer);
        wholeAnswer =0;
        
        while (stringScanner.hasNext()) {
        	operation = stringScanner.next();
        	number = stringScanner.next(); 
        	
        	wholeString = findWhole(number); 
        	numeratorString =findNumerator(number);
        	denominatorString = findDenominator(number); 
 
        
        	if(denominatorString == 0) {
        		stringScanner.close();
        		return ("ERROR: Cannot divide by zero");
        		}
        
        	numeratorString = makeImproperFractionNumerator(wholeString, numeratorString, denominatorString); 
        	numeratorAnswer = makeImproperFractionNumerator(wholeAnswer, numeratorAnswer, denominatorAnswer);
        
        	findEquivelentFractionsWithCommonDenominator (numeratorString, numeratorAnswer, denominatorString, denominatorAnswer); 
        
        	if(operation.equals("+")) {
        		addFractions(numeratorAnswer, numeratorString, denominatorAnswer, denominatorString); 	
        		} else if(operation.equals("-")) {
        			subFractions(numeratorAnswer, numeratorString, denominatorAnswer, denominatorString); 
        			} else if(operation.equals("*")) {
        				multiplyFractions(numeratorAnswer, numeratorString, denominatorAnswer, denominatorString); 
        				} else if(operation.equals("/")) {
        					divideFractions(numeratorAnswer, numeratorString, denominatorAnswer, denominatorString);
        					} else {
        						stringScanner.close();
        						return "ERROR: Input is in an invalid formatt"; 
        					}	
        
        	reduceFraction(numeratorAnswer, denominatorAnswer);
        	wholeAnswer =0; 
        }
       makeMixedFraction( numeratorAnswer, denominatorAnswer);
       stringScanner.close();
       return formattNumber(wholeAnswer, numeratorAnswer, denominatorAnswer); 
    }
    
    
//given a string formatted whole_numerator/denominator, returns the whole as an int. if there is no whole, returns the value of 0.
    public static int findWhole (String fraction) {
    	if(fraction.indexOf('_')!= -1) {
    		return Integer.parseInt(fraction.substring(0,fraction.indexOf('_'))); 
    	}
    	if(fraction.indexOf('/')==-1) {
    		return Integer.parseInt(fraction); 
    	}
    	return 0; 
    }
 
 //given a string formatted whole_numerator/denominator, returns the numerator as an int. If no fraction returns the value of 0
    public static int findNumerator (String fraction) { 
    	if(fraction.indexOf('_') != -1 && fraction.indexOf('/') != -1) {
    		return Integer.parseInt(fraction.substring(fraction.indexOf('_') + 1, fraction.indexOf('/'))); 
    	}
    	if((fraction.indexOf('_') == -1 && fraction.indexOf('/') != -1)) {
    		return Integer.parseInt(fraction.substring(0, fraction.indexOf('/')));
    	}
    	return 0; 
    }
    	
  //given a string formatted whole_numerator/denominator, returns the denominator as an int. If no fraction, returns a value of 1
    public static int findDenominator(String fraction) { 
    	if(fraction.indexOf('/') != -1) {
    			return Integer.parseInt(fraction.substring(fraction.indexOf('/') + 1, fraction.length())); 
    	}
    	return 1; 
    }
   
    // returns a numerator for an improper fraction
    public static int makeImproperFractionNumerator (int whole, int num, int dem) {
    	if (whole >= 0 && num <0 || whole < 0 && num >= 0) {
    		return 0-(Math.abs(dem) * Math.abs(whole) + Math.abs(num));
    	}
    	return dem * Math.abs(whole) + Math.abs(num);  	
    }
    
    //chances the numerator and denominator of two fractions so the are equivalent fraction that share a common denominator
    public static void findEquivelentFractionsWithCommonDenominator (int num1, int num2, int den1, int den2) {
    	if(den1 == den2) {
    		return; 
    	} else {
    		numeratorString = num1 * Math.abs(den2); 
    		denominatorString = den1 * Math.abs(den2); 
    		numeratorAnswer = num2 * Math.abs(den1); 
    		denominatorAnswer = den2 * Math.abs(den1); 
    	} 	
    }
    
    // substitutes two fractions with common denominators. Fractions must be less than one or improper.
    public static void subFractions(int num1, int num2, int den1, int den2) {
    	numeratorAnswer = num1 - num2; 
    	denominatorAnswer = den1;
    }
    
    // adds two fraction with common denominators.Fractions must be less than one or improper.
    public static void addFractions(int num1, int num2, int den1, int den2) {
    	numeratorAnswer = num1 + num2; 
    	denominatorAnswer = den1; 
    }
    
    // multiplies two fractions. Fractions must be less than one or improper. 
    public static void multiplyFractions(int num1, int num2, int den1, int den2) {
    	numeratorAnswer = num1 * num2; 
    	denominatorAnswer = den1 *den2; 
    }
    
    //divides two fractions. Fractions must be less than one or improper.
    public static void divideFractions(int num1, int num2, int den1, int den2) {
    	if(num1 < 0 && num2 >= 0 || num1 >= 0 && num2 <0) { 
    	numeratorAnswer = 0- (Math.abs(num1) * den2); 
    	denominatorAnswer = den1 * Math.abs(num2); 
    	} else {
    		numeratorAnswer = Math.abs(num1 * den2); 
        	denominatorAnswer = Math.abs(den1 * num2); 
    	}
    }
    
    // reduces a fraction answer. Fraction must be less than one or improper.
    public static void reduceFraction (int num, int den) { 	
    	for(int i = Math.abs(num); i > 1; i--) { 
    		if(num % i == 0 && den % i == 0) { 
    			numeratorAnswer = num/i; 
    			denominatorAnswer = den/i; 
    			return; 
    		}
    	}
    }
    
    // turns an improper fraction answer into a mixed number.
    public static void makeMixedFraction (int num, int den) {  
    	wholeAnswer = num/den; 
    	numeratorAnswer = (num % den); 
    	denominatorAnswer = den; 
    	if(wholeAnswer < 0 && num < 0) {
    		numeratorAnswer = Math.abs(numeratorAnswer);
    	} 
    	if(wholeAnswer > 0 && num <0) {
    		wholeAnswer = 0 - wholeAnswer; 
    		numeratorAnswer = Math.abs(numeratorAnswer); 
    	}
    }
   
    // formats the number so that unnecessary 0's do not show.
    public static String formattNumber (int whole, int num, int den) { 
    	if(whole == 0 && num == 0) {
    		return "0";
    	}
    	if(whole == 0 && num != 0) {
    		return num + "/" + den; 
    	}
    	if(whole != 0 && num == 0) {
    		return "" + whole; 
    	}
    	return whole + "_" + num + "/" + den; 
    }
    
}

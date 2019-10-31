//Grace Waylen 
//CS211(c)
//Project 2

//Builds an array of locations, checks to see if if those locations have 'children', and prints a map 
public class LocationArray {
	private Location [] elementData;
	private int size; 
	
	// constructs a location array with the given number of locations
	public LocationArray(int numOfLocations){
		elementData = new Location[numOfLocations];
		size = 0;
	}

	// adds a location to the array
    public void add(Location location) {
    	elementData[size] = location; 
    	size++;
    }
    
    // returns the locations at the given index
    public Location getLocationAt(int index) {
    	return elementData[index];
    }
    
    // returns true if the location at a given index has a parent
    public boolean hasParent(int index) { 
    	if(index == 0) {
    		return false;
    	}
    	return true; 
    }
    
    // returns the parent index for the location at a given index
    public int getParent(int index) {
    	if(index != 0) {
    	return (index - 1) / 3;
    	}
    	return 0; 
    }
    
    // returns true if the given location has a left child
    public boolean hasLeftChild(int index) {
    	
    	if(index * 3 + 1 >= elementData.length) {
    		return false;
    	}
    	return true; 
    }
    
    //returns true if the given location has a center child
    public boolean hasCenterChild(int index) {
    	if(index * 3 + 2 > size) {
    		return false;
    	}
    	return true; 
    }
    
    // returns true if the given location has a right child
    public boolean hasRightChild(int index) {
    	if(index * 3 + 3 > size) {
    		return false;
    	}
    	return true; 
    }
    
    // returns the index of the given location's right child
    public int getLeftChild(int index) {
    	return index * 3 + 1;
    }
    
    // returns the index of the given location's right child
    public int getRightChild(int index) { 
    	return index * 3 + 3;
    }
    
    //returns the index of the given location's center child
    public int getCenterChild(int index) {
    	return index * 3 + 2;
    } 
    
    //counts the number of locations in the array without children
	private int countLeaves() {
		int count = 0; 
		for(int i = 0; i < size ; i++) {
			if(!hasCenterChild(i) && !hasRightChild(i) && !hasLeftChild(i)) {
				count ++; 
			}
		}
		return count; 
	}
	
	// draws a map of the locations with the root location at the bottom of the map
	public void drawMap(Player user) {
		System.out.println("Drawing a map for " + user.getName());
		int i = size -1 ;
		int x = 0;
		int y = 0;
		String [][] mapArray = new String [(int) (Math.log(countLeaves()/Math.log(3)) + 1 )][countLeaves() + 2];
		for (y =0; y < (int) (Math.log(countLeaves())/Math.log(3))  ; y++) {
			int gapSize = y*2;
			for(x = 0; x <= countLeaves() -1; x++) {
				if(y == 0) {
					mapArray[y][x] = String.format("%-20s", elementData[i].getFormatName(user));
					i--;
					System.out.print( mapArray[y][x]);
					continue;
				}
				if(x < gapSize/2) {
					gapSize /= 2; 
				}
				if(x > countLeaves() - 3) {
					gapSize /= 2; 
				}
				for(int j = 0; j < gapSize; j++){
					mapArray[y][x] = String.format("%-20s", "");
					System.out.print( mapArray[y][x]);
					x++;	
				} 
				if(x <= countLeaves() - 1) {
					mapArray[y][x] = mapArray[y][x] = String.format("%-20s", elementData[i].getFormatName(user));
					i--;
					System.out.print( mapArray[y][x]);
					gapSize = y*2;
				}
				
			}
			
				if(y == 0) {
				System.out.println("\n\n      |   - - - - - - - -   | - - - - - - - -   |                   |   - - - - - - - -   | - - - - - - - -   |                   |   - - - - - - - - | - - - - - - - -   | \n");
			} else {
				System.out.println("\n\n                            |  - - - - - - - - - - - - - - - - - - - - - - - - - - - - -  | - - - - - - - - - - - - - - - - - - - - - - - - - - - - - |  \n");
			}
		}
		
			x = 0;
			while(x < countLeaves()/2) {
				
				mapArray[y][x] = String.format("%-20s", "");
				System.out.print( mapArray[y][x]);
				x++;
			}
			mapArray[y][x] = String.format("%-20s", elementData[i].getFormatName(user));
			i--;
			System.out.print( mapArray[y][x]);
			while(x < countLeaves()) {
				mapArray[y][x] = String.format("%-20s", "");
				System.out.print( mapArray[y][x]);
				x++;
			}
			System.out.println();
	}
	
	
    
}
	


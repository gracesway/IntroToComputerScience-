// Grace Waylen 
// CS211(c)
// Project 2

import java.util.Random;

// class contains information about how the computer navigates the map
public class ComputerNavigation extends Navigation{
	LocationArray locations;
	Player player;
	
	// constructor for computer navigation
	public ComputerNavigation(LocationArray map, Player player, LinkedAssasinList assasins) {
		super(map, player, assasins);
		this.map = super.map;
		this.player = super.player;
	
		
	}
	// the computer navigates randomly in on the map, never staying in the same room for two turns
	public void startNavigation() {
		int origionalIndex = player.getCurrentLocationIndex();
		Random rand = new Random(); 
		while(player.getCurrentLocationIndex() == origionalIndex) {
			//System.out.println(player.getName() + " is at index " + player.getCurrentLocationIndex());
			int choice = rand.nextInt(4) + 1 ;
			super.move(choice); 
		}
	}
	
}

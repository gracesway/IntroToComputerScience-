// Grace Waylen 
// CS 211(c) 
// Project 2

import java.util.Scanner;

// builds out the the mechanic for the user to navigate the castle
public class PlayerNavigation extends Navigation {
	LocationArray map;
	Player player;
	
	// Constructor for PlayerNavigation
	public PlayerNavigation(LocationArray map, Player player, LinkedAssasinList assasins) {
		super(map, player, assasins);
		this.map = super.map;
		this.player = super.player; 
	}
	
	// returns a string with the player' current location 
	public String locationStats() { 
		int playerLocation = player.getCurrentLocationIndex();
		String stats = "\nYou are currently in the " + map.getLocationAt(playerLocation) + "\n" ;
	
		return stats;
	}
	

	// displays a menu of options for the user
	public int selectAction() {
		int playerLocation = player.getCurrentLocationIndex();
		Scanner sc = new Scanner(System.in); 
		if(map.hasLeftChild(playerLocation)){
			System.out.println ("Press 1 to go to the " + map.getLocationAt(map.getLeftChild(playerLocation)).getName());
		}
		if(map.hasCenterChild(playerLocation)){
			System.out.println ("Press 2 to go to the " + map.getLocationAt(map.getCenterChild(playerLocation)).getName());
		}
		if(map.hasRightChild(playerLocation)){
			System.out.println ("Press 3 to go to the " + map.getLocationAt(map.getRightChild(playerLocation)).getName());
		}
		if(map.hasParent(playerLocation)){
			System.out.println("Press 4 to go back to the " + map.getLocationAt(map.getParent(playerLocation)).getName());
		}
		System.out.println("Press 5 to stay in the " + map.getLocationAt(playerLocation).getName());
		System.out.println("Press 6 for a map and current player status");
		int choice;
		try {
			choice = sc.nextInt();
		} catch( Exception e) {
			choice = 5;
		}
		return choice;
	}
	
	// starts the navigation for the user
	public void startNavigation() {
		System.out.print(locationStats());
		super.move(selectAction());
	}
	
}

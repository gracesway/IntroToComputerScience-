// Grace Waylen 
// CS 211(c)
// Project 2 

import java.text.SimpleDateFormat;
import java.util.Date;

// this abstract class contains information on how a Player navigates during its turn. playerNavigation and Computer Navigation extend Navigation
public abstract class Navigation {
	protected LocationArray map;
	protected Player player;
	private LinkedAssasinList assasins; 
	
	
	// given a  map, a player, and a linked list of assassins, constructs a Navigation object
	public Navigation(LocationArray map, Player player, LinkedAssasinList assasins) {
		this.map = map; 
		this.player = player; 
		this.assasins = assasins; 
	}
	
	// user and computer startNavigation differently
	public abstract void startNavigation();
	
	// returns the Player associated with this Navigation object
	public Player getPlayer() {
		return player;
	}
	
	// moves the player into a location given their choice
	public void move(int choice) {
		int playerLocation = player.getCurrentLocationIndex();
		if(map.hasLeftChild(playerLocation) && choice == 1 ){
			map.getLocationAt(playerLocation).removePlayer(player);
			map.getLocationAt(map.getLeftChild(playerLocation)).addPlayer(player);
			player.setCurrentLocationIndex(map.getLeftChild(playerLocation));
			} else {
				if(map.hasCenterChild(playerLocation) && choice == 2 ){
					map.getLocationAt(playerLocation).removePlayer(player);
					map.getLocationAt(map.getCenterChild(playerLocation)).addPlayer(player);
					player.setCurrentLocationIndex(map.getCenterChild(playerLocation));
				} else {
					if(map.hasRightChild(playerLocation) && choice == 3 ){
						map.getLocationAt(playerLocation).removePlayer(player);
						map.getLocationAt(map.getRightChild(playerLocation)).addPlayer(player);
						player.setCurrentLocationIndex(map.getRightChild(playerLocation));
					} else {
						if(map.hasParent(playerLocation) && choice == 4 ){
							map.getLocationAt(playerLocation).removePlayer(player);
							map.getLocationAt(map.getParent(playerLocation)).addPlayer(player);
							player.setCurrentLocationIndex(map.getParent(playerLocation));
						} else {
							if(choice == 6) {
								getStatus();
								return; // if you select this option, you do not get the chance to check for assassination
							}
						}
					}
				}
			}
		checkForAssasination();
		
	}
	
	// prints the current status of the player in the game and a map 
	private void getStatus() {
		System.out.println(String.format("%100s", "STATUS\n"));
		map.drawMap(player);
		System.out.println("You are currently looking for " + assasins.assasinAssignment(player).getName() + "\n");
		for (int i = 1; i < GamePlay.getPlayers().size(); i++) {
			System.out.print("\n" + GamePlay.getPlayers().get(i).getName() + ": " + GamePlay.getPlayers().get(i).getStatus());
			if(GamePlay.getPlayers().get(i).getStatus().equals("Dead")) {
				System.out.print(": Killed by " + GamePlay.getPlayers().get(i).getKiller().getName() + " in the " + GamePlay.getPlayers().get(i).getLocationOfDeath().getName() + " at " +GamePlay.getPlayers().get(i).getTimeOfDeath());
			} else {
				System.out.print(": In the " + map.getLocationAt(GamePlay.getPlayers().get(i).getCurrentLocationIndex()).getName()); 
			}
		}
		System.out.println();
	}
	
	// checks the players current room for the player the are looking for, if found that player is removed from play and its location of death,
	//killer, and time of death are updated. This players number of kills are incremented.
	private void checkForAssasination() {
		if(map.getLocationAt(player.getCurrentLocationIndex()).getPlayers().contains(assasins.assasinAssignment(player)) ){
			System.out.println(player.getName() + " just assasinated " + assasins.assasinAssignment(player).getName());
			player.addKill();
			assasins.assasinAssignment(player).setStatus("Dead");
			assasins.assasinAssignment(player).setKiller(player);
			SimpleDateFormat format = new SimpleDateFormat("HH:mm");
			Date date = new Date();  
			assasins.assasinAssignment(player).setTimeOfDeath(format.format(date));  
			assasins.assasinAssignment(player).setLocationOfDeath(map.getLocationAt(player.getCurrentLocationIndex()));
			assasins.remove(assasins.assasinAssignment(player));
		}
		if(assasins.size() == 1) {
			System.out.println(player.getName() + " is the last one standing!");
			GamePlay.setWinner(true);
		}
	}

}

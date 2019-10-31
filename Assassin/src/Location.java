// Grace Waylen 
// CS 211 
// project 2

import java.util.ArrayList;
// Class creates a new location with a description, name, and an array of Players currently in the room 
public class Location {
	private String description; 
	private String name;
	private ArrayList<Player> players = new ArrayList<Player>(); 
	Player user; 
	
	
	// constructor for a new location
	public Location (String name, String description) {
		this.name = name.trim(); 
		this.description = description; 
	}
	// returns the name of the room formatted so it is in the middle of a 20 character string
	public String getFormatName(Player user) {
		String formattedName = name; 
		if(players.contains(user)) {
			 formattedName ="**" + formattedName.toUpperCase() + "**";
		} 
		int extraChar = 20 - name.length();
			for (int i = 0; i < extraChar/2; i++) {
				formattedName = " " + formattedName; 
			}
				while(formattedName.length() < 20 ) {
					formattedName += " "; 
		}
				return formattedName;
	}
	// returns the name of the Location
	public String getName() {
	
		return name;}
	
	// returns the name of the room as well as its description
	public String toString() { return name + ": " + description; }

	// adds a player to the room's array
	public void addPlayer(Player player) {players.add(player); }
	
	// removes a player from the room's array
	public void removePlayer(Player player) { players.remove(player); }
	
	// returns a list of all they players in the room
	public ArrayList<Player> getPlayers(){ return players; }
	

}

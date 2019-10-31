// Grace Waylen 
// cs211(c) 
// Project 2

//This class builds a player and includes information on their current location, how many kills they have made, and who, where, at what time they were killed.
public class Player {
	private int currentLocationIndex = 0;
	private int kills; 
	private Location locationOfDeath; 
	private Player killedBy; 
	private String name;
	private String status = "Alive"; 
	private String timeOfDeath = "" ;
	
	// Constructs a player given a name
	public Player(String name) {
		this.name = name;
	}
	
	// setter and getter for time of death
	public String getTimeOfDeath() { return timeOfDeath;}
	public void setTimeOfDeath(String timeOfDeath) { this.timeOfDeath = timeOfDeath;}
	
	// setter and getter for current location
	public int getCurrentLocationIndex() { return currentLocationIndex;}
	public void setCurrentLocationIndex(int index) { currentLocationIndex = index; } 
	
	// modifier and getter for number of kills made by the player
	public int getKills() { return kills;}
	public void addKill() { kills ++;}
	
	// getter for name 
	public String getName() {return name;}
	
	//setter and getter for killer
	public void setKiller(Player killedBy) { this.killedBy = killedBy;}
	public Player getKiller() { return killedBy; }
	
	// setter and getter for status (Alive or Dead)
	public void setStatus(String status) {this.status = status;}
	public String getStatus() { return status;}
	
	// setter and getter for location of death
	public void setLocationOfDeath(Location room) { locationOfDeath = room;}
	public Location getLocationOfDeath() { return locationOfDeath;}

}

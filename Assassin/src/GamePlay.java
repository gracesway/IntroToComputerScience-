//Grace Waylen 
//CS211(c)
//Project 2 

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

// This class creates the game play mechanics including creating player, location, and Navigation lists.
public class GamePlay {
	private static boolean winner= false;
	private int numberOfPlayers;
	private LocationArray map  = new LocationArray(13); 
	private static LinkedAssasinList assasins = new LinkedAssasinList(); 
	ArrayList<Navigation> navs = new ArrayList<Navigation>();
	static ArrayList<Player>players;
	public static ArrayList<Player> getPlayers() { return players;}
	private Player user;
	
	// setter and getter 
	public static void setWinner(boolean winners) { winner = winners; }
	public static boolean getWinner() { return winner;}
	public LinkedAssasinList getAssasinList() {return assasins;}
	
	// constructor 
	//set and Array List of players, and uses that list to set an LinkedAssasinList
	public GamePlay(int numberOfPlayers) {
		this.numberOfPlayers = numberOfPlayers;
		players = new ArrayList<Player>(); 
		winner = false;
		user = new Player(getPlayerName());
		players.add(user); 
		players.add(new Player("Jenny")); 
		players.add(new Player("Mike")); 
		players.add(new Player("Noah")); 
		players.add(new Player("Bob"));
		players.add(new Player("Frank")); 
		players.add(new Player("Mary")); 
		players.add(new Player("Julie")); 
		players.add(new Player("Mark")); 
		players.add(new Player("Sue"));
		if(numberOfPlayers < 10) {
			for (int i = 9; players.size() > (numberOfPlayers); i--) {
				players.remove(i);
			}	
		}
		assasins = createAssasinList(); 
	}
		
	// creates a Location Array with the 13 Locations listed below
	public LocationArray createMap() {
		map.add(new Location("Great Room", "A large, warm room, the light from the fire flickers against the wall."));
		map.add(new Location("Solar", "Uncomfortable looking chairs surround a small fire place."));
		map.add(new Location("Kitchen", "Strange smells fill the room, a mix of raw meat sour vegetables."));
		map.add(new Location("Master Chamber", "There is an beautifully made bed in the middle of the room, the moon shines though the window"));
		map.add(new Location("Cabinet", "A room where men gather after dinner, it smells of smoke."));
		map.add(new Location("Boudoir", "A room where women gather after dinner, the chairs look far more comfortable in here."));
		map.add(new Location("Pavilion","Its a dark night, you can hear the wind blowing through the trees"));
		map.add(new Location("Buttery", "Cases of wine and cheese line the basement room."));
		map.add(new Location("Pantry", "Bags of dry goods line the walls of this small space."));
		map.add(new Location("Cellar", "A cold room, far below the castle"));
		map.add(new Location("GardenRobe", "Its a fancy bathroom, a large empty tub sits in the corner"));
		map.add(new Location("Servant Quarter", "A much smaller room, bright."));
		map.add(new Location("Wardrobe", "Bright colored cloth lines the walls of this small room."));
		return map;
	}
	
	//ask the player for their name and returns the answer
	public String getPlayerName() { 
		Scanner sc = new Scanner(System.in); 
		System.out.println("What is your name? ");
		return sc.nextLine();
	}
	
	// returns a LinkedAssasinList from the array list of players 
	public LinkedAssasinList createAssasinList() {
		Random rand = new Random();
		ArrayList<Player> temp = new ArrayList<Player>(players);
		while(players.size() != assasins.size()) {
			int random = rand.nextInt(temp.size());
			assasins.add(temp.get(random));
			temp.remove(random);
		}
		return assasins; 
	}
	
	// set the array of navigation systems using the array list of players
	public void createNavigation() {
		navs.add(new PlayerNavigation(map, players.get(0), assasins));
		for(int i = 1; i < players.size(); i++) {
			navs.add(new ComputerNavigation(map, players.get(i), assasins));
		}
	}
	
	// start the game play, including instructions at the start and turn taking
	public void gamePlay() {
	System.out.println("\n\nYou and " + (numberOfPlayers - 1) + " other deadly assassins have been dropped into the Great Castle at Portchester.\n" + "Your goal is to kill " 
	+ assasins.assasinAssignment(user).getName() + ". Once you have killed " + assasins.assasinAssignment(user).getName() +" you will needed to use the status report (#6) from the menu to find out who you're after next."
				+ "\nYou can navigate the castle by using the number keys. You will be alerted when another assassin is killed, but you will not know who is after you!" 
				+ "\nThe last person to make it out alive is the winner!");
		while(!winner) {
			for(int i = 0; i < navs.size(); i++) {
				if(assasins.contains(navs.get(i).getPlayer()) && !winner) {
					navs.get(i).startNavigation();
				}	
			}
		}
	}
	
	// returns a string that gives the end of game stats for each player, including where they were killed and who killed them, and the number of kills they had 
	public String endOfGameStats() {// format these strings 
		String string = "";
		for(int i = 0; i < players.size(); i++) {
			string += players.get(i).getName() + ": # of Kills - " + players.get(i).getKills();
			
			if(players.get(i).getStatus().equals("Dead")) {
				string +=  " | Killed in: " + map.getLocationAt(players.get(i).getCurrentLocationIndex()).getName() + "\n";
			} else {
				string +=  " | Made it out alive.\n";
			}
					 
		}
		return string;
	}

}

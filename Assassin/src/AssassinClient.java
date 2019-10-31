// Grace Waylen 
// CS211(c) 
// Project 2
import java.util.Scanner;

// runs the solution for 16.4 and the text based game
public class AssassinClient{
	public static boolean keepPlaying = true;
	

	// asks user if they would like to see the solution for 16.4 or the text based game
	public static void main(String[] args)  {
		Scanner sc = new Scanner(System.in);
		String cont;
		while(keepPlaying) {
			int answer  = askWhatDemo(); // ask the user what they would like to see, the solution or the game
			if(answer == 1) {
				justAssignment(); 
			} else if (answer == 2) {
				playGame();
			}
			do {
				System.out.println("Would you like to play again? Y/N"); // ask if they would like to play again
				cont = sc.nextLine().trim().toUpperCase();
				if(cont.equals("N")){
				keepPlaying = false;
				}
				if(cont.equals("Y")){
					keepPlaying = true;
				}	
			} while(!cont.equals("N") && !cont.equals("Y")); // keep asking until the user types y or n
		}
		sc.close();
	}
	
	//asks if the user wants to see the solution to 16.4 or to play the text based game
	private static int askWhatDemo() {
		Scanner sc = new Scanner(System.in);
		System.out.println("Press 1 to see just the solution for question 16.4"); 
		System.out.println("Press 2 to see the text based game the question inspired!"); 
		return sc.nextInt();
	}
		
	// solution to the assignment 
	private static void justAssignment() {
		Scanner sc = new Scanner(System.in);
		LinkedAssasinList players = new LinkedAssasinList(); 
		players.add(new Player("Jenny")); // adds new Players to the list
		players.add(new Player("Mike")); 
		players.add(new Player("Noah")); 
		players.add(new Player("Bob"));
		players.add(new Player("Frank")); 
		players.add(new Player("Mary")); 
		players.add(new Player("Julie")); 
		players.add(new Player("Mark")); 
		players.add(new Player("Sue"));
		while(players.size() > 1) { // while the number of players is greater than 1
			PlayerNode current = players.getFront();
			while(current.next != null) { 
				System.out.println(current.player.getName() + " is looking for " + current.next.player.getName()); // print out the name of the players and who they are looking for 
				current = current.next;
			}
			System.out.print(current.player.getName() + " is looking for " + players.getFront().player.getName());
			System.out.println("\n Who would you like to 'take out'? ");// ask the user who they would like to remove from the list
			String name = sc.nextLine();
			while(!players.contains(name)) {// if the user enters something that isnt contained in the list, ask again
				System.out.println("Who? "); 
				name = sc.nextLine();
			}
			players.remove(name);
		}
		System.out.println(players.toString() + " is the last one standing!"); // announce the winner
	}
		
	// starts the text based game 
	private static void playGame()  {
		GamePlay game;
		Scanner sc = new Scanner(System.in);
			System.out.println("How many players? (Max 10)"); // asks the user how many players they want to play with 
			try {
				game = new GamePlay(sc.nextInt()); 
			} catch (Exception e) { // if they enter an invalid number (greater than 10, less than 2) or something that is not an integer, assume 10 player
				game = new GamePlay(10); 
			}
			LocationArray map = game.createMap(); // create a new map 
			map.drawMap(GamePlay.getPlayers().get(0)); // draw the map for the user
			game.createNavigation(); // create an array of navigation objects for the players
			game.gamePlay(); // play the game
			System.out.print(game.endOfGameStats()); // show the end of game stats
			sc.nextLine();
	}
}



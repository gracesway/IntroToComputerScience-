// Grace Waylen 
//CS211 )c) 
// Project 2 

// creates a player node containing a player and another node
public class PlayerNode {

	public PlayerNode next; 
	public Player player;
		
	//constructs player node given a player and a player node
	    public PlayerNode(Player player, PlayerNode next) {
	        this.player = player;
	        this.next = next;
	    }

	    //constucts a node given a player
	    public PlayerNode(Player player) {
	        this(player, null);
	    }

	    //constructs an empty node
	    public PlayerNode() {
	        this(null, null);
	    }
}


// Grace Waylen 
// CS 211 (c) 
// project 2

// Creates a linked list of Player Nodes to create an assassin list 
public class LinkedAssasinList {

	 private PlayerNode front; 
	 private int size = 0;
	
	
	 // constructor, creates an empty list with one null node at the front
	 public LinkedAssasinList() {
		front = null; 
	 }
	 
	 // returns the front node
	 public PlayerNode getFront() { return front; }
	 
	 // returns the size of the List
	 public int size() { return size;}
	
	 // adds a player to the list
	  public void add(Player player) {
	       if (front == null) {
	           front = new PlayerNode(player);
	           size ++;
	       } else {
	           PlayerNode current = front;
	           while (current.next != null) {
	               current = current.next;
	           }
	           current.next = new PlayerNode(player);
	           size++;
	        }
	    }
	  
	  // removes a player from the the list and reorganizes the list
	  public void remove(Player player) { 
		  PlayerNode current = front;
		  if(front.player.equals(player)) {
			  front = current.next; 
			  size--;
			  return;
		  }
		  while(!current.next.player.equals(player)) {
				  current = current.next; 
				  if(current.next == null) {
					  return;
				  }
			  }
		  current.next = current.next.next;
		  size--;
	  }
	  
	  // removes a player from the list given a players name 
	  public void remove(String playerName) { 
		  PlayerNode current = front;
		  if(front.player.getName().equals(playerName)) {
			  front = current.next; 
			  size--;
			  return;
		  }
		  while(!current.next.player.getName().equals(playerName)) {
				  current = current.next; 
				  if(current.next == null) {
					  return;
				  }
			  }
		  current.next = current.next.next;
		  size--;
	  }
	  
	  //returns true if this LinkedAssassinList contains a given player
		public boolean contains(Player player){
			 PlayerNode current = front;
			  while(current != null) {
				  if(current.player.equals(player)) {
					  return true; 
				  }
				  if(current.next == null) {
					  return false;
				  }
			current = current.next;
			  }
			 return false;
		 }
		 
		// returns true if this LinkedAssassinList contains a player with the given name
		 public boolean contains(String playerName){
			 PlayerNode current = front;
			  while(current != null) {
				  if(current.player.getName().equals(playerName)) {
					  return true; 
				  }
				  if(current.next == null) {
					  return false;
				  }
			current = current.next;
			  }
			 return false;
		 }
	  
	// toString override for LinkedAssassinList
	public String toString() {
		  String toString = ""; 
		  PlayerNode current = front; 
		  
		  while(current != null) {
			  toString += current.player.getName() + " ";
			  current = current.next;
		  }
		  return toString; 
	  }
	  
		// returns the player that the given player is looking to assassinate
	  public Player assasinAssignment(Player player) {
		  PlayerNode current = front;
		  while(!current.player.equals(player)) {
			 
			  current = current.next; 
		  }
		  if(current.next == null) {
			  return front.player;
		  } 
		  return current.next.player;
	  }
	
}

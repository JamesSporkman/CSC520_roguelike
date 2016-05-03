import java.util.ArrayList;


public class AOORogueLike
{
	//Static stuff? Specifically tile symbols.
	//Only one enemy for now may be a good idea.
	static final char __WALL__ = '|';
	static final char __PLAYER__ = '$';
	static final char __ENEMY__ = 'M';
	static final char __ITEM__ = 'I';
	static final char __SPACE__ = ' ';
	static final char __STAIRWELLDOWN__ = 'D';
	static final char __STAIRWELLUP__ = 'U';
	
	private Dungeon theDungeon;
	private Player thePlayer;
	private Room testRoom;
	private Floor testFloor;
	
	public AOORogueLike(){
		//CREATE: Dungeon.
		//CREATE: Floors.
		//CREATE: Rooms.
		//CREATE: Tiles.
		theDungeon = new Dungeon();
		thePlayer = new Player();
		thePlayer.setLocation(theDungeon.setPlayerStart());
		
		//testRoom = new Room();
		//testFloor = new Floor();
	}
	//This should be just part of the constructors, no?
	/*
	public void buildDungeon(){
		Dungeon theDungeon = new Dungeon; //Need construction!
		buildFloors();
	}
	public void buildFloors(){
		buildRooms();
	}*/
	
	//Thinking that, at this point, we might as well make each floor a room or two at most.
	public void buildRooms(){
		loadTiles();
		loadActors();
		loadItems();
	}
	public void loadTiles(){
		
	}
	public void loadActors(){
		
	}
	public void loadItems(){
		
	}
	public void display()
	{
		//System.out.println("UPDATE DISPLAY");
		Floor currentFloor = theDungeon.getCurrFloor();
		ArrayList<Room> roomList = currentFloor.getRoomGrid();
		for(int i = 0; i<roomList.size();i++)
		{
			Room currRoom = roomList.get(i);
			System.out.print(" ");
			for(int j = 0; j<currRoom.tileMap.length;j++)
				System.out.print("_");
			for(int j = 0; j<currRoom.tileMap.length;j++)
			{	
				System.out.println();
				System.out.print("|");
				for(int k = 0; k<currRoom.tileMap[j].length;k++)
					System.out.print(currRoom.tileMap[j][k].displayTile());
				System.out.print("|");	
			}
			System.out.println();
			System.out.print(" ");
			for(int j = 0; j<currRoom.tileMap.length;j++)
				System.out.print("-");
			System.out.println();
		}
	}
	
	private class Tile
	{
		private State tileState;
		private Actor actorOnTile;
		private ArrayList<AbstractItem> itemsOnTile;
		
		//tile index may be important for judging distances. tileIndex {x, y}
		//Okay, I'm not sure exactly where you're going with this...
		//Tile indexes seem like they should be seperate x and y values.
		//Returning them as an array could work though...
		private int[] tileIndex;
		public int[] getIndex(){return tileIndex;}
		
		Tile( State stateToAssign, int[] tileIndexIn ){
			itemsOnTile = new ArrayList<AbstractItem>();
			this.tileState = stateToAssign;
			this.tileIndex = tileIndexIn;
			actorOnTile = null;
		}
		
		public State getState(){return tileState;}
		public void useFeature()
		{
			if( this.tileState == State.STAIRWELLDOWN ){
				theDungeon.descendLevel();
			}
			else if( this.tileState == State.STAIRWELLUP ){
				theDungeon.ascendLevel();
			}
			else{
				//What to do?
				//Handle checks if you can move to the tile?
				if(tileState == State.SPACE)
				{
					if(actorOnTile == null)
					{}	
					//move corresponding actor somehow? Maybe return a true?
				}
				if(tileState == State.TRAP)
				{
					//Take damage?
					//I would think after activating trap, it becomes an empty space
					tileState = State.SPACE;
				}
			}
		}
		
		public String displayTile(){
			//System.out.println(actorOnTile);
			String toReturn = "";
			if( actorOnTile != null ){//Note: Need to be sure it'd be null. Possibly add 'nothing' case?
				if( actorOnTile == thePlayer ){
					toReturn = Character.toString(__PLAYER__);
				}
			}
			else if( itemsOnTile.size() > 0 ){
				toReturn = Character.toString(__ITEM__);
			}
			else{
				if( tileState == State.WALL ){
					toReturn = Character.toString(__WALL__);
				}
				else if( tileState == State.STAIRWELLDOWN ){
					toReturn = Character.toString(__STAIRWELLDOWN__);
				}
				else if( tileState == State.STAIRWELLUP ){
					toReturn = Character.toString(__STAIRWELLUP__);
				}
				else{
					toReturn = Character.toString(__SPACE__);
				}
			}
			return toReturn;
		}
		
		public ArrayList<AbstractItem> getItemsOnTile() {return itemsOnTile;}
		
		public Actor getActorOnTile(){return actorOnTile;}
		public void setActorOnTile(Actor a)
		{
			actorOnTile = a;
		}
		public void removeActorOnTile()
		{
			actorOnTile = null;
		}
		
		public boolean isVacant()
		{
			if(actorOnTile == null && tileState != State.WALL)
				return true;
			return false;
			
		}
	}
	
	private class Room
	{
		//public ArrayList<Tile> tileMap; //Switch to using 2d arrays? Unless you can think of a quick way to search a 2d arraylist.
		public Tile[][] tileMap = new Tile[11][11]; //Max room size
		
		public Room(){ //Default constructor - an empty room.
			for( int i = 0; i < this.tileMap.length; i++ ){
				for( int j = 0; j < this.tileMap[i].length; j++ ){
					int[] tileIndexIn = {i, j}; //Feels weird doing this.
					//tileIndexIn[i] = j;
					if(i==5 && j ==5)
						this.tileMap[i][j] = new Tile( State.WALL, tileIndexIn );
					else
						this.tileMap[i][j] = new Tile( State.SPACE, tileIndexIn );
				}
			}
		}
		Room(State roomDesign[][])
		{
			
		}
		public int[] getTileOfActor( Actor target )
		{
			int[] returnable = {-1, -1};
			for( int i = 0; i < tileMap.length; i++ ){
				for( int j = 0; j < tileMap[i].length; j++ ){
					if( tileMap[i][j].getActorOnTile() == target ){
						returnable[0] = i;
						returnable[1] = j;
						return returnable;
					}
				}
			}
			return returnable;
		}
		public String displayRoom(){
			String finishedDisplay = "";
			for( int i = 0; i < this.tileMap.length; i++ ){
				for( int j = 0; j < this.tileMap[i].length; j++ ){
					finishedDisplay = finishedDisplay + tileMap[i][j].displayTile();
				}
				finishedDisplay = finishedDisplay + "\n"; //Next line.
			}
			return finishedDisplay;
		}
		public void setPlayerStart()
		{
			tileMap[2][2].setActorOnTile(thePlayer);
		}
	}
	
	private class Floor
	{
		private ArrayList<Room> roomGrid;
		private ArrayList<Actor> actorList;
		
		public Floor(){ //Default constructor. Adds a single room and the player.
			Room tempRoom =  new Room();
			roomGrid = new ArrayList<Room>();
			actorList = new ArrayList<Actor>();
			this.roomGrid.add(tempRoom );
			actorList.add( thePlayer );
			roomGrid.get(0).setPlayerStart();
		}
		Floor( Room roomToAdd ){//Build with a single room.
			this.roomGrid.add( roomToAdd );
			actorList.add( thePlayer );
		}
		Floor( ArrayList<Room> roomsToAdd, ArrayList<Actor> actorsToAdd ){ //Now we add a whole set of things.
			this.roomGrid.addAll(roomsToAdd);
			this.actorList.addAll(actorsToAdd);
		}
		
		public ArrayList<Room> getRoomGrid(){return roomGrid;}
		public ArrayList<Actor> getActorList(){return actorList;}
	}
	
	private class Dungeon
	{
		public Dungeon()
		{
			Floor newFloor = new Floor();
			currentLevel = 0;
			levelMap = new ArrayList<Floor>();
			levelMap.add(newFloor);
		}
		public void descendLevel()
		{
			currentLevel++;
		}
		public void ascendLevel()
		{
			currentLevel--;
		}
		public Floor getCurrFloor()
		{
			return levelMap.get(currentLevel);
		}
		public Tile setPlayerStart()
		{
			levelMap.get(0).getRoomGrid().get(0).setPlayerStart();
			return levelMap.get(0).getRoomGrid().get(0).tileMap[2][2];
		}
		private ArrayList<Floor> levelMap;
		private int currentLevel;
		
	}
	
	public enum State
	{
		WALL, SPACE, TRAP,
		STAIRWELLDOWN, STAIRWELLUP
	}
	
	public interface Item
	{
		public void use();
	}
	
	public abstract class AbstractItem implements Item
	{
		public String description;
	}
	public class Consumable extends AbstractItem
	{
		public void use()
		{
			
		}
		private int effect;
		private int duration;
		private boolean isFood;
		private int statEffected;
		private int maxUses;
		private int timesUsed;
	}
	public enum EquipLocation
	{
		HEAD, BODY, GLOVES, BOOTS, WEAPON, AMULET, LEFTRING, RIGHTRING
	}
	public class Equippable extends AbstractItem
	{
		public void use()
		{
			thePlayer.equip( this );
		}
		private EquipLocation equipSlot;
		private int armorProvided;
		private boolean isEquipped;
		private int range;	
	}
	
	public interface Actor
	{
		public int getMaxHealth();
		public int getCurrentHealth();
		public ArrayList<AbstractItem> getInventory();
		public ArrayList<AbstractItem> equipItem();
		public AbstractItem getInventoryItem(int i);
		public Tile getLocation();
		public void attack(Actor Target);
		public void beAttacked(int Damage);
		public void move(char d);
		public String examine();
		public ArrayList<Actor> targetsInRange(int r);
	}
	
	public abstract class AbstractActor implements Actor
	{
		//These methods from the interface can be defined here for all subclasses
		public int getMaxHealth(){return Stats.get(0);}
		public int getCurrentHealth(){return Stats.get(1);}
		public ArrayList<AbstractItem> getInventory(){return Inventory;}
		public ArrayList<AbstractItem> equipItem(){return EquippedItems;}
		public AbstractItem getInventoryItem(int i){return Inventory.get(i);}
		public Tile getLocation(){return Location;}
		
		//Needs to be defined still!
		public void attack(Actor Target){
			//Error checking?
		}
		public void beAttacked(int Damage){
			//I added the damage argument
			//Let attack() handle damage amount and whether it hits?
			Stats.set(1, Stats.get(1)-Damage);
		}
		public void move(char d)
		{
			int[] tileIndex = Location.getIndex();
			switch(d)
			{
				case 'W':
					//System.out.println("MovedNorth!");
					
					if(tileIndex[0]>0)
					{
						Room tempRoom = theDungeon.getCurrFloor().getRoomGrid().get(0);
						if (tempRoom.tileMap[tileIndex[0]-1][tileIndex[1]].isVacant())
						{
							Location.removeActorOnTile();
							Location = tempRoom.tileMap[tileIndex[0]-1][tileIndex[1]];
							Location.setActorOnTile(this);
						}
					}
					break;
				case 'S':
					//System.out.println("MovedSouth!");
					//int[] tileIndex = Location.getIndex();
					if(tileIndex[0]<11)
					{
						Room tempRoom = theDungeon.getCurrFloor().getRoomGrid().get(0);
						if (tempRoom.tileMap[tileIndex[0]+1][tileIndex[1]].isVacant())
						{
							Location.removeActorOnTile();
							Location = tempRoom.tileMap[tileIndex[0]+1][tileIndex[1]];
							Location.setActorOnTile(this);
						}
					}
					break;
				case 'D':
					//System.out.println("MovedEast!");
					//int[] tileIndex = Location.getIndex();
					if(tileIndex[1]<11)
					{
						Room tempRoom = theDungeon.getCurrFloor().getRoomGrid().get(0);
						if (tempRoom.tileMap[tileIndex[0]][tileIndex[1]+1].isVacant())
						{
							Location.removeActorOnTile();
							Location = tempRoom.tileMap[tileIndex[0]][tileIndex[1]+1];
							Location.setActorOnTile(this);
						}
					}
					break;
				case 'A':
					System.out.println("MovedWest!");
					//int[] tileIndex = Location.getIndex();
					if(tileIndex[1]>0)
					{
						Room tempRoom = theDungeon.getCurrFloor().getRoomGrid().get(0);
						if (tempRoom.tileMap[tileIndex[0]][tileIndex[1]-1].isVacant())
						{
							Location.removeActorOnTile();
							Location = tempRoom.tileMap[tileIndex[0]][tileIndex[1]-1];
							Location.setActorOnTile(this);
						}
					}
			}
		}
		public String examine()
		{
			return "Hello!";
		}
		public ArrayList<Actor> targetsInRange(int r){return new ArrayList<Actor>();}
		//End of methods needing definitions
		//Fields
		public ArrayList<Integer> Stats;
		public ArrayList<AbstractItem> Inventory;
		public ArrayList<AbstractItem> EquippedItems;
		public Tile Location;
		
		
	}
	
	public class Player extends AbstractActor
	{
		Player()
		{
			
		}
		public void setLocation(Tile myLocation)
		{
			Location = myLocation;
		}
		public void equip(Equippable e){
			
		}
		public int Score;
	}
	
	public class Enemy extends AbstractActor
	{
		public void runAll(){
			if( Awake ){
				if ( Alert ){
					//Search target.
					if(target == null)
					{
						int[] playerLocation = thePlayer.getLocation().getIndex();
						int[] thisLocation = Location.getIndex();
						if(((playerLocation[0]-thisLocation[0])<5) && ((playerLocation[0]-thisLocation[0])>-5))
						{
							if(((playerLocation[1]-thisLocation[1])<5) && ((playerLocation[1]-thisLocation[1])>-5))
								target = thePlayer;
						}
					}
					Tile TargetLocation = target.getLocation();
					//Get target's location.
					//Compare to current location.
					//Reduce the difference between locations.
				}
			}
		}
		public void updateEnergy(){
			energyLevel = energyGen;
		}
		
		public int energyLevel;
		public int energyGen;
		public Actor target;
		public Actor lastAttacker;
		public int infightThreshold;
		public boolean Alert;
		public boolean Awake;	
	}
	
	public static void main(String[] args)
	{
		//buildDungeon();
		AOORogueLike myGame = new AOORogueLike();
		boolean gameOver = false;
		//Display logic goes here.
		myGame.display();
		while(gameOver == false)
		{
			//Run player logic.
			boolean playerHasTurn = true;
			System.out.print(">");
			while( playerHasTurn ){
			//Player command logic.
				char command;
				try
				{
					command = (char)System.in.read(); 
				}catch(Exception e)
				{
					command = '0';
				}
				//char command = String.valueOf(commandFull.charAt(0));
				if( command=='W'||command=='w' ){
					//Move North.
					myGame.thePlayer.move('W');
					//End turn.
					playerHasTurn = false;
				}
				else if( command=='S'||command=='s' ){
					//Move south.
					//End turn.
					myGame.thePlayer.move('S');
					playerHasTurn = false;
				}
				else if(command=='A'||command=='a'){
					//Move west.
					//End turn.
					myGame.thePlayer.move('A');
					playerHasTurn = false;
				}
				else if(command=='D'||command=='d'){
					//Move east.
					//End turn.
					myGame.thePlayer.move('D');
					playerHasTurn = false;
				}
				else if(command=='I'||command=='i'){
					//Display inventory to use/equip.
					//DO NOT end turn!
				}
				else if(command=='E'||command=='e'){
					//Equip or use.
					//End turn.
					playerHasTurn = false;
				}
				else if(command=='Q'||command=='q'){
					//Use local feature.
					//End turn.
					playerHasTurn = false;
				}
				else if(command=='G'||command=='g'){
					//Grab item on tile.
					//End turn.
					playerHasTurn = false;
				}
				else if(command=='F'||command=='f'){
					//Attack.
					//End turn.
					playerHasTurn = false;
				}
				else if(command=='X'||command=='x'){
					System.out.print("Examine? ");
					//System.in.read();
					//playerHasTurn = false;
					//DO NOT end turn!
				}
				else if(command=='?'||command=='.')
				{
					System.out.println("QUITTING...");
					gameOver = true;
					playerHasTurn = false;
				}
				else
				{
				//	System.out.println("Unknown command");
					//Let the user know somehow there was an error?
				}
			}
			
		//end Player Turn
		
		for (int i = 0; i < 50; ++i) System.out.println();
		myGame.display();
		//Display and move enemies
		}
		//Gameover
	}
}
import java.util.ArrayList;


public class AOORogueLike
{
	//Static stuff? Specifically tile symbols.
	//Only one enemy for now may be a good idea.
	static final String __WALL__ = "|";
	static final String __PLAYER__ = "$";
	static final String __ENEMY__ = "M";
	static final String __ITEM__ = "I";
	static final String __SPACE__ = " ";
	static final String __STAIRWELLDOWN__ = "D";
	static final String __STAIRWELLUP__ = "U";
	
	private Dungeon theDungeon;
	private Player thePlayer;
	
	public AOORogueLike(){
		//CREATE: Dungeon.
		//CREATE: Floors.
		//CREATE: Rooms.
		//CREATE: Tiles.
		//theDungeon = new Dungeon();
		//thePlayer = new Player();
		System.out.println("WE MADE IT HERE!");
		thePlayer = new Player();
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
		System.out.println("UPDATE DISPLAY");
		/*Floor currentFloor = theDungeon.getCurrFloor();
		ArrayList<Room> roomList = currentFloor.getRoomGrid();
		for(int i = 0; i<roomList.size();i++)
		{
			Room currRoom = roomList.get(i);
			for(int j = 0; j<currRoom.tileMap.length;j++)
				for(int k = 0; k<currRoom.tileMap[j].length;k++)
					System.out.print(currRoom.tileMap[j][k]);
		}*/
	}
	
	private class Tile
	{
		
		private State tileState;
		private Actor actorOnTile;
		private ArrayList<AbstractItem> itemsOnTile;
		
		//tile index may be important for judging distances. tileIndex {x, y}
		private int[] tileIndex;
		public int[] getIndex(){return tileIndex;}
		
		public Tile(State newState)
		{
			tileState = newState;
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
		
		public ArrayList<AbstractItem> getItemsOnTile() {return itemsOnTile;}
		
		public Actor getActorOnTile(){return actorOnTile;}
		public void setActorOnTile(Actor a)
		{
			actorOnTile = a;
		}
		
		//This is useful for outputting to the screen
		public String toString()
		{
			switch(tileState)
			{
				case SPACE:
					if(actorOnTile == thePlayer)
						return __PLAYER__;
					else if(actorOnTile != null)
						return __ENEMY__;
					else
						return __SPACE__;
				case WALL:
					return __WALL__;
				case STAIRWELLDOWN:
					return __STAIRWELLDOWN__;
				case STAIRWELLUP:
					return __STAIRWELLUP__;
			}
			return " ";
		}
	}
	
	private class Room
	{
		//public ArrayList<Tile> tileMap; //Switch to using 2d arrays? Unless you can think of a quick way to search a 2d arraylist.
		public Tile[][] tileMap = new Tile[11][11]; //Max room size
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
	}
	
	private class Floor
	{
		private ArrayList<Room> roomGrid;
		private ArrayList<Actor> actorList;
		
		public ArrayList<Room> getRoomGrid(){return roomGrid;}
		public ArrayList<Actor> getActorList(){return actorList;}
	}
	
	private class Dungeon
	{
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
			switch(d)
			{
				case 'W':
					System.out.println("MovedNorth!");
					break;
				case 'S':
					System.out.println("MovedSouth!");
					break;
				case 'D':
					System.out.println("MovedEast!");
					break;
				case 'A':
					System.out.println("MovedWest!");
					
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
		public Player(){}
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
					playerHasTurn = false;
				}
				else if(command=='A'||command=='a'){
					//Move west.
					//End turn.
					playerHasTurn = false;
				}
				else if(command=='D'||command=='d'){
					//Move east.
					//End turn.
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
					//Examine.
					//DO NOT end turn!
				}
				else
				{
					System.out.println("Unknown command");
					//Let the user know somehow there was an error?
				}
			}
			
		//end Player Turn
		myGame.display();
		//Display and move enemies
		}
		//Gameover
	}
}
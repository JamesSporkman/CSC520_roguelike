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
	
	public void AOORogueLike(){
		//CREATE: Dungeon.
		//CREATE: Floors.
		//CREATE: Rooms.
		//CREATE: Tiles.
		theDungeon = new Dungeon();
		thePlayer = new Player();
		testRoom = new Room();
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
			this.State = stateToAssign;
			this.tileIndex = tileIndexIn;
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
	}
	
	private class Room
	{
		//public ArrayList<Tile> tileMap; //Switch to using 2d arrays? Unless you can think of a quick way to search a 2d arraylist.
		public Tile[][] tileMap = new Tile[11][11]; //Max room size
		
		Room(){ //Default constructor - an empty room.
			for( int i = 0; i < this.tileMap.length; i++ ){
				for( int j = 0; j < this.tileMap[i].length; j++ ){
					int[] tileIndexIn = new int[11]; //Feels weird doing this.
					tileIndexIn[i] = j;
					this.tileMap[i][j] = new Tile( State.SPACE, tileIndexIn );
				}
			}
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
		boolean gameOver = false;
		//Display logic goes here.
		while(gameOver == false)
		{
			//Run player logic.
			boolean playerHasTurn = true;
			while( playerHasTurn ){
			//Player command logic.
				String commandFull = "W"; //Placeholder, move north.
				String command = String.valueOf(commandFull.charAt(0));
				if( command.equalsIgnoreCase("W") ){
					//Move North.
					//End turn.
					playerHasTurn = false;
				}
				else if( command.equalsIgnoreCase("S") ){
					//Move south.
					//End turn.
					playerHasTurn = false;
				}
				else if(command.equalsIgnoreCase("A")){
					//Move west.
					//End turn.
					playerHasTurn = false;
				}
				else if(command.equalsIgnoreCase("D")){
					//Move east.
					//End turn.
					playerHasTurn = false;
				}
				else if(command.equalsIgnoreCase("I")){
					//Display inventory to use/equip.
					//DO NOT end turn!
				}
				else if(command.equalsIgnoreCase("E")){
					//Equip or use.
					//End turn.
					playerHasTurn = false;
				}
				else if(command.equalsIgnoreCase("Q")){
					//Use local feature.
					//End turn.
					playerHasTurn = false;
				}
				else if(command.equalsIgnoreCase("G")){
					//Grab item on tile.
					//End turn.
					playerHasTurn = false;
				}
				else if(command.equalsIgnoreCase("F")){
					//Attack.
					//End turn.
					playerHasTurn = false;
				}
				else if(command.equalsIgnoreCase("X")){
					//Examine.
					//DO NOT end turn!
				}
				else
				{
					//Let the user know somehow there was an error?
				}
			}
			
		//end Player Turn
		
		//Display and move enemies
		}
		//Gameover
	}
}
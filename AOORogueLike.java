import java.util.ArrayList;


public class AOORogueLike
{
	//Static stuff? Specifically tile symbols.
	public void Roguelike(){
		//CREATE: Dungeon.
		//CREATE: Floors.
		//CREATE: Rooms.
		//CREATE: Tiles.
	}
	public void buildDungeon(){
		Dungeon theDungeon = new Dungeon; //Need construction!
		buildFloors();
	}
	public void buildFloors(){
		buildRooms();
	}
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
		
		public State getState(){return tileState;}
		public void useFeature()
		{
			if( this.tileState == STAIRWELLDOWN ){
				theDungeon.descendLevel();
			}
			else if( this.tileState == STAIRWELLUP ){
				theDungeon.ascendLevel();
			}
			else{
				//What to do?
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
		public int[] getTileOfActor( Actor target )
		{
			for( int i = 0; i < tileMap.length(); i++ ){
				for( int j = 0; j < tileMap[i].length; j++ ){
					if( tileMap[i][j].getActorOnTile == target ){
						int[] returnable = { i,j }
						return returnable;
					}
				}
			}
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
			Player.equip( this );
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
		public void beAttacked();
		public void move(char d);
		public String examine();
		public ArrayList<Actor> targetsInRange(int r);
	}
	
	public abstract class AbstractActor implements Actor
	{
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
					tile TargetLocation = target.getLocation();
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
		buildDungeon();
		
		//Display logic goes here.
		
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
		}
	}
}
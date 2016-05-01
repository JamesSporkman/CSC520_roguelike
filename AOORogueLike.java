import java.util.ArrayList;


public class AOORogueLike
{
	public void Roguelike(){
		
	}
	public void buildDungeon(){
		
	}
	public void buildFloors(){
		
	}
	public void buildRooms(){
		
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
			
		}
		
		public ArrayList<AbstractItem> getItemsOnTile() {return itemsOnTile;}
		
		public Actor getActorOnTile(){return actorOnTile;}
		public void setActorOnTile(Actor a)
		{
			
			
		}
	}
	
	private class Room
	{
		public ArrayList<Tile> tileMap;
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
			
		}
		public void ascendLevel()
		{
			
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
			
		}
		public void updateEnergy(){
			
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
		
	}
}
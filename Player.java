import java.util.ArrayList;
import java.util.Arrays;
/**
 * Class Player - a player in an adventure game.
 * 
 * This class is part of the "World of Zuul" application. 
 * "World of Zuul" is a very simple, text based adventure game. 
 *
 * The "Player" object it used to keep track of a user's items, health, and other similar information.
 * 
 * @author  Nicholas Trilone
 * @version 2021.11.01
 */
public class Player
{
    private ArrayList<Item> items; //items within the players inventory
    private int health;
    /**
     * Create a player with an empty inventory, that can be filled with items.
     * @param health represents the amount of health the player has, can be manipulated through the use of other methods
     */
    public Player(int health) 
    {
         this.health=health;
         items = new ArrayList<>();
    }
    
    /**
     * Returns the health value of the player
     * @return int representing player health
     */
    public int getHealth()
    {
        return health;
    }
    
    /**
     * Increases/decreases player health by provided value
     * Positive numbers heal, and negative numbers hurt
     * @param int representing player heal/hurt amount
     */
    public void changeHealthValue(int num)
    {
        if(health+num<=10){
            health=health+num;
        }
        else{
            health=10;
        }
    }
    
    /**
     * Adds an item into a the players inventory.
     * @param item is the item object that is added
     */
    public void addItem(Item item)
    {
        items.add(item);
    }

    /**
     * Removes an item from a players inventory.
     * Searches by name, prints based on the results.
     * @param name The items's name.
     */
    public void dropItem(String name)
    {
        int i=items.size()-1;
        while(i>=0){
           if(items.get(i).getName().equals(name))
           {
               if(items.get(i).getKey()==true){
                   System.out.println("This item seems too important to simply drop.");
                   return;
               }
               System.out.println("You dropped the "+items.get(i).getName()+".");
               items.remove(i);
               return;
           }
           i--;
        }
        System.out.println("Action failed. Review your input and try again!");
    }
    
    /**
     * Returns the players inventory ArrayList
     * @return items ArrayList
     */
    public ArrayList<Item> getInventory()
    {
        return items;
    }
    
    /**
     * Returns whether or not the player has a certain item.
     * @return true if has item, false if not
     */
    public boolean containsItem(String name)
    {
        int i=0;
        while(i<getInventory().size()){
                if(items.get(i).getName().equals(name)){
                    return true;
                }
                i++;
            }
        return false;
    }
    
    /** 
     * Prints out which items are the players inventory, and their details.
     * No return or parameter values.
     */
    public void printInventory()
    {
        if (getInventory().size()==0){
            System.out.println("There are no items in your inventory.");
        }
        else{
            System.out.println("The item(s) in your inventory are: ");
            int i=0;
            while(i<getInventory().size()){
                System.out.print("Name: "+getInventory().get(i).getName()+", ");
                System.out.print("Description: "+getInventory().get(i).getDescription()+", ");
                System.out.println("Weight: "+getInventory().get(i).getWeight());
                i++;
            }
        }
    }
}

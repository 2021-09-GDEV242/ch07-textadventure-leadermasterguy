import java.util.Set;
import java.util.HashMap;
import java.util.Iterator;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Class Room - a room in an adventure game.
 *
 * This class is part of the "World of Zuul" application. 
 * "World of Zuul" is a very simple, text based adventure game.  
 *
 * A "Room" represents one location in the scenery of the game.  It is 
 * connected to other rooms via exits.  For each existing exit, the room 
 * stores a reference to the neighboring room.
 * It is also able to manage of an ArrayList of Items it contains.
 * 
 * @author  Nicholas Trilone
 * @version 2021.11.01
 */

public class Room 
{
    private String description;
    private HashMap<String, Room> exits;        // stores exits of this room.
    private ArrayList<Item> items; //items within the room
    /**
     * Create a room described "description". Initially, it has
     * no exits. "description" is something like "a kitchen" or
     * "an open court yard".
     * @param description The room's description.
     */
    public Room(String description) 
    {
        this.description = description;
        items = new ArrayList<>();
        exits = new HashMap<>();
    }
    
    /**
     * Adds an item into a room's ArrayList of items.
     * @param description The items's name.
     * @param description The items's description.
     * @param description The items's weight.
     */
    public void addItem(String name,String description, int weight, boolean key)
    {
        items.add(new Item(name,description,weight,key));
    }

    /**
     * Removes an item from a room's ArrayList of items.
     * @param description The items's index.
     */
    public void removeItem(int n)
    {
        items.remove(n);
    }
    
    /**
     * Removes an item into a room's ArrayList of items, and places it into the players inventory
     * Searches by name, prints based on the results.
     * @param name The items's name.
     */
    public void takeItem(String name, Player player)
    {
        int i=items.size()-1;
        while(i>=0){
           if(items.get(i).getName().equals(name))
           {
               player.addItem(items.get(i));
               System.out.println("You take the "+items.get(i).getName()+"!");
               items.remove(i);
               return;
           }
           i--;
        }
        System.out.println("Action failed. Review your input and try again!");
    }
    
    /**
     * Returns a room's item ArrayList
     * @return items ArrayList
     */
    public ArrayList<Item> getItems()
    {
        return items;
    }
    
    /**
     * Define an exit from this room.
     * @param direction The direction of the exit.
     * @param neighbor  The room to which the exit leads.
     */
    public void setExit(String direction, Room neighbor) 
    {
        exits.put(direction, neighbor);
    }

    /**
     * @return The short description of the room
     * (the one that was defined in the constructor).
     */
    public String getShortDescription()
    {
        return description;
    }

    /**
     * Return a description of the room in the form:
     *     You are in the kitchen.
     *     Exits: north west
     * @return A long description of this room
     */
    public String getLongDescription()
    {
        return "You are " + description + ".\n" + getExitString();
    }

    /**
     * Return a string describing the room's exits, for example
     * "Exits: north west".
     * @return Details of the room's exits.
     */
    private String getExitString()
    {
        String returnString = "Exits:";
        Set<String> keys = exits.keySet();
        for(String exit : keys) {
            returnString += " " + exit;
        }
        return returnString;
    }

    /**
     * Return the room's description
     * @return The room's description.
     */
    public Room getExit(String direction) 
    {
        return exits.get(direction);
    }
    
    /** 
     * Prints out which items are within a room.
     */
    public void printItems()
    {
        if (getItems().size()==0){
            System.out.println("There are no items in this room.");
        }
        else{
            System.out.print("The item(s) in this room are: ");
            int i=0;
            while(i<getItems().size()){
                if(i<getItems().size()-1){
                    System.out.print(getItems().get(i).getName()+", ");
                }
                else{
                    System.out.println(getItems().get(i).getName()+".");
                }
                i++;
            }
        }
    }
}


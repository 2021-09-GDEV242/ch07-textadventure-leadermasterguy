
/**
 * Class Item - an item in an adventure game.
 * 
 * This class is part of the "World of Zuul" application. 
 * "World of Zuul" is a very simple, text based adventure game. 
 *
 * An "Item" is an object or piece of equipment that is found within a Room.
 * It stores its name, description, and a weight value.
 * 
 * @author  Nicholas Trilone
 * @version 2021.11.01
 */
public class Item
{
    private String name;
    private String description;
    private int weight;
    private boolean key;
    /**
     * Create an item described "description", with a given weight value, and name.
     * @param name The item's name
     * @param description The item's description.
     * @param weight The item's weight.
     *
     */
    public Item(String name,String description,int weight, boolean key) 
    {
        this.name = name;
        this.description = description;
        this.weight = weight;
        this.key = key;
    }

    /**
     * @return The name of the item
     */
    public String getName()
    {
        return name;
    }
    
    /**
     * @return The description of the item
     */
    public String getDescription()
    {
        return description;
    }
    
    /**
     * @return The weight of the item
     */
    public int getWeight()
    {
        return weight;
    }
    
    /**
     * important items are marked as "key", and cannot be dropped by the player
     * true means "key", while most items are false
     * @return The key value of the item
     */
    public boolean getKey()
    {
        return key;
    }
}

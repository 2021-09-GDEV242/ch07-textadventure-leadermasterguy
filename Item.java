
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
    /**
     * Create an item described "description", with a given weight value, and name.
     * @param name The item's name
     * @param description The item's description.
     * @param weight The item's weight.
     *
     */
    public Item(String name,String description,int weight) 
    {
        this.name = name;
        this.description = description;
        this.weight = weight;
    }

    /**
     * @return The name of the item
     */
    public String getName()
    {
        return name;
    }
}

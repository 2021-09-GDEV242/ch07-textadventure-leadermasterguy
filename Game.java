import java.util.ArrayList;
import java.util.Arrays;
/**
 *  This class is the main class of the "World of Zuul" application. 
 *  "World of Zuul" is a very simple, text based adventure game.  Users 
 *  can walk around some scenery. That's all. It should really be extended 
 *  to make it more interesting!
 * 
 *  To play this game, create an instance of this class and call the "play"
 *  method.
 * 
 *  This main class creates and initialises all the others: it creates all
 *  rooms, creates the parser and starts the game.  It also evaluates and
 *  executes the commands that the parser returns.
 * 
 * 
 * 
 * Passing Requirements:
 * Implemented look command
 * 
 * C Requirements:
 * Added eat command, with a simple text response
 * Streamlined printing of available commands
 * 
 * B Requirements:
 * Added items to the game. Items have description and weight, and items are printed with the room descriptions.
 * Made it so rooms could hold multiple items(via ArrayList).
 * Made a player object that is capable of holding multiple items, and taking items from rooms.
 * 
 * 
 * A Requirements:
 * 
 * @author  Nicholas Trilone
 * @version 2021.11.01
 */

public class Game 
{
    private Parser parser;
    private Room currentRoom;
    private Player player;
    /**
     * Main Method.
     * Creates a game object, and runs the play method, starting the game.
     */
    public static void main (String args[])
    {
        Game game1 = new Game();
        game1.play();
    }
    
    /**
     * Create the game and initialise its internal map.
     */
    public Game() 
    {
        createRooms();
        parser = new Parser();
        player = new Player();
    }

    /**
     * Create all the rooms and link their exits together.
     */
    private void createRooms()
    {
        Room outside, theater, pub, lab, office;

        // create the rooms
        outside = new Room("outside the main entrance of the university");
        theater = new Room("in a lecture theater");
        pub = new Room("in the campus pub");
        lab = new Room("in a computing lab");
        office = new Room("in the computing admin office");

        // initialise room exits
        outside.setExit("east", theater);
        outside.setExit("south", lab);
        outside.setExit("west", pub);

        theater.setExit("west", outside);

        pub.setExit("east", outside);

        lab.setExit("north", outside);
        lab.setExit("east", office);

        office.setExit("west", lab);
        
        // add items to rooms
        outside.addItem("book","A book idk", 2);
        outside.addItem("key","An important looking key", 1);
        outside.addItem("rock","Just a rock", 3);

        currentRoom = outside;  // start game outside
    }

    /**
     *  Main play routine.  Loops until end of play.
     */
    public void play() 
    {            
        printWelcome();

        // Enter the main command loop.  Here we repeatedly read commands and
        // execute them until the game is over.

        boolean finished = false;
        while (! finished) {
            Command command = parser.getCommand();
            finished = processCommand(command);
        }
        System.out.println("Thank you for playing.  Good bye.");
    }

    /**
     * Print out the opening message for the player.
     */
    private void printWelcome()
    {
        System.out.println();
        System.out.println("Welcome to the World of Zuul!");
        System.out.println("World of Zuul is a new, incredibly boring adventure game.");
        System.out.println("Type '" + CommandWord.HELP + "' if you need help.");
        System.out.println();
        System.out.println(currentRoom.getLongDescription());
        currentRoom.printItems();
    }

    /**
     * Given a command, process (that is: execute) the command.
     * @param command The command to be processed.
     * @return true If the command ends the game, false otherwise.
     */
    private boolean processCommand(Command command) 
    {
        boolean wantToQuit = false;

        CommandWord commandWord = command.getCommandWord();

        switch (commandWord) {
            case UNKNOWN:
                System.out.println("I don't know what you mean...");
                break;

            case HELP:
                printHelp();
                break;

            case GO:
                goRoom(command);
                break;

            case QUIT:
                wantToQuit = quit(command);
                break;
                
            case LOOK:
                look();
                break;
                
            case EAT:
                eat();
                break;
                
            case TAKE:
                take(command.getSecondWord());
                break;
                
            case INVENTORY:
                inventory();
                break;
        }
        return wantToQuit;
    }

    // implementations of user commands:

    /**
     * Print out some help information.
     * Here we print some stupid, cryptic message and a list of the 
     * command words.
     */
    private void printHelp() 
    {
        System.out.println("You are lost. You are alone. You wander");
        System.out.println("around at the university.");
        System.out.println();
        System.out.println("Your command words are:");
        parser.showCommands();
    }

    /** 
     * Try to go in one direction. If there is an exit, enter the new
     * room, otherwise print an error message.
     */
    private void goRoom(Command command) 
    {
        if(!command.hasSecondWord()) {
            // if there is no second word, we don't know where to go...
            System.out.println("Go where?");
            return;
        }

        String direction = command.getSecondWord();

        // Try to leave current room.
        Room nextRoom = currentRoom.getExit(direction);

        if (nextRoom == null) {
            System.out.println("There is no door!");
        }
        else {
            currentRoom = nextRoom;
            System.out.println(currentRoom.getLongDescription());
        }
        
        //prints the objects that are inside of the room
        currentRoom.printItems();
    }

    /** 
     * "Quit" was entered. Check the rest of the command to see
     * whether we really quit the game.
     * @return true, if this command quits the game, false otherwise.
     */
    private boolean quit(Command command) 
    {
        if(command.hasSecondWord()) {
            System.out.println("Quit what?");
            return false;
        }
        else {
            return true;  // signal that we want to quit
        }
    }

    /** 
     * Examines the room that the player is currently in.
     * Prints the room's long description.
     * Prints out any items that are in the room.
     * Has no parameters or return values.
     */
    private void look()
    {
        System.out.println(currentRoom.getLongDescription());
        currentRoom.printItems();
    }
    
    /** 
     * Causes the player character to eat.
     * Currently does nothing other than print a message.
     * Has no parameters or return values.
     */
    private void eat()
    {
        System.out.println("You have eaten now and you are not hungry any more.");
    }
    
    /** 
     * Causes the player to take an item out of a room.
     * Has no return values.
     * @param name of item attempting to remove
     */
    private void take(String name)
    {
        currentRoom.takeItem(name,player);
    }
    
    /** 
     * Prints out information about the player's inventory
     */
    private void inventory()
    {
        player.printInventory();
    }
}

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
 * 15+ rooms
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
 * Added a locked door and a key that can be used to open it.
 * Added a basic health system, that will drain over time.
 * Made it so eating will regenerate health, up to a maximum of 10.
 * 
 * Additional features:
 * checkable inventory
 * ability to take items from rooms(into the inventory)
 * ability to drop items(not key items)
 * 
 * @author  Nicholas Trilone
 * @version 2021.11.01
 */

public class Game 
{
    private Parser parser;
    private Room currentRoom;
    private Player player;
    private Room door;
    private Room freedom;
    private int counter=1;
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
        player = new Player(10);
    }

    /**
     * Create all the rooms and link their exits together.
     */
    private void createRooms()
    {
        Room outside, theater, pub, lab, office, stage, backstage, pub2, table, closet, stairs, basement, storage, security;

        // create the rooms
        outside = new Room("outside the main entrance of the university");
        theater = new Room("in a lecture theater");
        pub = new Room("in the campus pub");
        lab = new Room("in a computing lab");
        office = new Room("in the computing admin office");
        freedom = new Room("you have escaped! The End");
        stage = new Room("on a stage");
        backstage = new Room("behind the stage");
        pub2 = new Room("at the very back of the campus pub");
        door = new Room("by a mysterious door(locked)");
        table = new Room("under a table, for some reason");
        closet = new Room("inside of a storage closet");
        stairs = new Room("on a set of creaky stairs");
        basement = new Room("in a dark and creepy basement");
        storage = new Room("in a room used for storage");
        security = new Room("in a room used for security");

        // initialise room exits
        outside.setExit("east", theater);
        outside.setExit("south", lab);
        outside.setExit("west", pub);
        outside.setExit("north", door);

        theater.setExit("west", outside);
        theater.setExit("east", stage);

        pub.setExit("east", outside);
        pub.setExit("west", pub2);

        lab.setExit("north", outside);
        lab.setExit("east", office);
        lab.setExit("south", stairs);

        office.setExit("west", lab);
        office.setExit("east", table);
        office.setExit("south", security);
        
        table.setExit("west", office);
        
        security.setExit("north", office);
        
        stage.setExit("west", theater);
        stage.setExit("east", backstage);
        
        backstage.setExit("west", stage);
        
        pub2.setExit("east", pub);
        
        freedom.setExit("south", door);
        
        stairs.setExit("south", basement);
        stairs.setExit("north", lab);
        
        basement.setExit("east", storage);
        basement.setExit("west", closet);
        basement.setExit("north", stairs);
        
        closet.setExit("east", basement);
        
        storage.setExit("west", basement);
        
        door.setExit("south", outside);
        //door.setExit("north", freedom);
        
        
        
        
        // add items to rooms
        outside.addItem("rock2","Who would've guessed that there are rocks outside?", 4, false);
        outside.addItem("rock","Just a rock.", 3, false);
        
        table.addItem("gum","Pre-chewed.", 1, false);
        
        closet.addItem("key","Seems slightly important.", 0, true);
        
        storage.addItem("folder","A folder full of useless papers.", 2, false);
        storage.addItem("flashlight","No batteries included.", 3, false);
        
        stairs.addItem("spider","Your very first friend.", 0, false);
        
        pub2.addItem("bottles","All empty", 5, false);
        
        security.addItem("equipment","All the security equipment you can carry. Will sell for a fortune.", 35, false);
        
        backstage.addItem("banjo", "Finders keepers.", 5, false);
        
        theater.addItem("note", "A crude drawing", 0, false);
        
        
        
        

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
                
            case DROP:
                drop(command.getSecondWord());
                break;
                
            case UNLOCK:
                unlock();
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
        
        counter++;
        if (counter==4){
            counter=0;
            player.changeHealthValue(-1);
            System.out.println("You feel a sharp pain. You now have "+player.getHealth()+" Health");
        }
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
     * Will heal them for 2 health, up to a maximum of 10.
     * Has no parameters or return values.
     */
    private void eat()
    {
        if(player.getHealth()==10){
            System.out.println("You have eaten, although it doesn't seem to have done much.");
        }
        else{
            player.changeHealthValue(2);
            System.out.println("You heal and feel a bit better. You now have "+player.getHealth()+" health.");
        }
        
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
     * Causes the player to drop an item from their inventory.
     * Does not work on important "key" items.
     * @param name of item attempting to drop
     */
    private void drop(String name)
    {
        player.dropItem(name);
    }
    
    /** 
     * Player attempts to unlock a door
     * @param name of item attempting to drop
     */
    private void unlock()
    {
        if(currentRoom.getShortDescription().equals("by a mysterious door(locked)")&&player.containsItem("key")){
            System.out.println("You unlock the door, and can now escape by going north.");
            door.setExit("north", freedom);
            return;
        }
        if(player.containsItem("key")){
            System.out.println("You have a key, but there's nothing to unlock here.");
            return;
        }
        if(currentRoom.getShortDescription().equals("by a mysterious door(locked)")){
            System.out.println("It seems like you'll need a key to unlock this.");
            return;
        }
        System.out.println("Unlock what with what?");
    }
    
    /** 
     * Prints out information about the player's inventory
     */
    private void inventory()
    {
        player.printInventory();
    }
}

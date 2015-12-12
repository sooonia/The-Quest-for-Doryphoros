package sample;

/**
 * Created by Sonia on 10/6/2015.
 */
import javafx.scene.control.TextArea;
import sun.plugin.javascript.navig.Array;

import java.util.ArrayList;
public class Character
{
    String name;
    private boolean alive = true;
    int robStat = 1;
    ArrayList<Item> charItem;

    /**
     * Creates a new character
     * @param name the name of the character
     * @param robability 1/robability is the probability of successfully robbing the character
     * @throws IllegalArgumentException if name is null or robability less than 1
     */
    public Character (String name, int robability )
    {
        if (name == null || robability <1)
        {
            throw new IllegalArgumentException("Must provide a name and a robability");
        }
        this.name = name;
        robStat = robability;
        charItem = new ArrayList<Item>();

    }

    /**
     * Basic constructor
     */
    public Character()
    {
    }

    /**
     * removes item from character's inventory and adds it to the player's
     * @param item the item the character is giving away
     * @param player the player to whom the character gives the item
     * @throws IllegalArgumentException if item is null or player is null
     */
    public void give(Item item, Player player)
    {
        if (item == null || player == null)
        {
            throw new IllegalArgumentException("Neither item or player can be null.");
        }
        charItem.remove(item);
        player.addItem(item);
    }

    /**
     * changes alive to false
     */
    public void die()
    {
        alive = false;
    }

    /**
     * adds item to character's inventory
     * @param item the item to be added
     */
    public void addItem(Item item)
    {
        charItem.add(item);
    }


    /**
     * prints out a line of dialogue
     * @param line what the character says
     */
    public void say(TextArea outscreen, String line)
    {
        outscreen.appendText(this.name + ": " + line+"\n");
    }

    /**
     *
     * @return true if the character is still alive
     */
    public boolean isAlive()
    {
        return alive;
    }

}

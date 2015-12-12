package sample;

import javafx.scene.control.TextArea;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

/**
 * Created by Sonia on 10/1/2015.
 */
public class Player
{

    TextArea outScreen;
        public String name;
        private int strength;
        private int healthMax;
        private int health;
        private boolean alive;
        private Location place;

        private ArrayList<Item> items;
        private ArrayList<Weapon> weaponList;
        private boolean forestQuest;
        private boolean agoraQuest;
        private boolean polQuest;
        private boolean completed;

    /**
     * Constructs the player
     * @param name the name of the player
     */

        public Player(TextArea outScreen, String name)
        {
            this.outScreen = outScreen;
            this.name=name;
            strength=2;
            healthMax=25;
            health = 10;
            alive = true;

            items = new ArrayList<Item>();
            weaponList = new ArrayList<Weapon>();

            forestQuest = false;
            agoraQuest = false;
            polQuest = false;
            completed = false;
        }

    /**
     *
     * @param itemName the name of the item you want to give. Case Sensitive
     * @param character the character to whom you give the item
     * @return true if you successfully give the item to the character
     * @throws IllegalAccessException if item is null or character is null
     */

        public boolean give(String itemName, Character character)
        {
            if (itemName == null || character == null)
            {
                throw new IllegalArgumentException("Need to specify both the name of the item and who you give it to.");
            }

            boolean success = false;
            Item item = new Item("",0,0);
            for (Item thing : items)
            {
                if (itemName.equals(thing.itemName))
                {
                    item = thing;
                }
            }
            if (items.contains(item))
            {
                items.remove(item);
                character.charItem.add(item);
                outScreen.appendText("You have given " + character.name + " " + item.itemName +"\n");
                success = true;
            }
            else
            {
                outScreen.appendText("You do not have this item\n");
            }
            return success;
        }

    /**
     *
     * @param monster the monster you are fighting
     * @return true if you beat the monster
     * @throws IllegalArgumentException if monster is unspecified
     */
        public boolean fight(Monster monster)
        {
            if (monster == null)
            {
                throw new IllegalArgumentException("Need to specify a monster to fight.");
            }
            boolean victory = false;
            int damage = attack();
            monster.setHealth(-1* damage);

            if (monster.getHealth() <= 0)
            {
                victory = true;
                monster.die();
                outScreen.appendText("You have slain " + monster.name + ".\n");
                for (Item item : monster.charItem)
                {
                    items.add(item);
                    outScreen.appendText("You have received " + item.itemName + "\n");
                }
            }

            else
            {
                outScreen.appendText(monster.name + " has " + monster.getHealth() + " health points remaining.\n");
                damage = monster.attack();
                health = health - damage;
                if (health <= 0)
                {
                    alive = false;
                    outScreen.appendText("You died.\n");
                    //figure out how to end the program here
                }
                else
                {
                    outScreen.appendText("You have " + health + " health points remaining.\n");
                }
            }
            return victory;
        }

        /**
        *random amount less than the player's strength
        * @return how much the player damages the monster
        */
        public int attack()
        {
            Random rand = new Random();
            int val= rand.nextInt(this.strength);
            assert val >=0;
            return val;
        }

    /**
     * changes player's health. Muct be greater than 0.
     * @param healthDif the amount the health will increase by. Can be negative.
     * @throws ArithmeticException if health is less than 0.
     */
        public void setHealth(int healthDif)
        {
            health = health + healthDif;
            if (health > healthMax)
            {
                health = healthMax;
            }
            if(health <= 0)
            {
             throw new ArithmeticException();
            }

        }

    /**
     *
     * @param monster the monster from whom you are trying to flee
     * @return true if successfully fled
     * @ throws illegal argument exception if monster is null.
     */
        public boolean flee(Monster monster)
        {
            if (monster == null)
            {
                throw new IllegalArgumentException("You must specify a monster to run away from");
            }
            Random rand= new Random();
            int num = rand.nextInt(5);
            int damage;
            boolean success = true;
            if (num == 1)
            {
                outScreen.appendText("You have successfully fled\n");
            }
            else
            {
                success = false;
                outScreen.appendText("You fail to flee\n");
                damage = monster.attack();
                health = health - damage;
                if (health <= 0)
                {
                    alive = false;
                    outScreen.appendText("You died.\n");
                    //figure out how to end the program here
                }
                else
                {
                    outScreen.appendText("You have " + health + " health points remaining.\n");
                }
            }
            return success;
        }

    /**
     *
     * @return player's health
     * health must be greater than 0
     */

        public int getHealth()
        {
            assert health >0;
            return health;
        }

    /**
     *
     * @return maximum possible health points a player can have
     * Must be greater than 0.
     */
        public int getHealthMax()
        {
            assert healthMax >0;
            return healthMax;
        }

    /**
     *
     * @return the list of items a player has
     */

        public ArrayList<Item> getItems()
        {
            return items;
        }

    /**
     * the length of the weapon list may not exceed 1
     * @return the weapon currently equipped by a player
     */

        public ArrayList<Weapon> getWeaponList()
        {
            assert weaponList.size() <= 1;
            return weaponList;
        }

    /**
     * removes the selected weapon from the equipment list. You then drop it forever
     * @param weapon
     * @throws IllegalArgumentException if weapon is null
     */

        public void removeWeapon(Weapon weapon)
        {
            if (weapon == null)
            {
                throw new IllegalArgumentException("You must select a weapon to remove");
            }
            int bonus = weapon.getStrengthBonus();
            strength = strength - bonus;
            weaponList.remove(0);

        }

    /**
     * kills the selected character
     * @param character
     * @throws IllegalArgumentException if character is null
     */

        public void kill(Character character)
        {
            if (character == null)
            {
                throw new IllegalArgumentException("You must select a character");
            }
            character.die();
            outScreen.appendText("You have killed " + character.name + ".\n");
        }

    /**
     * equips the selected weapon if the player has it in his inventory
     * does not need a pre condition because it searches through the inventory
     * @param weapon
     */

        public void equip(Weapon weapon)
        {
            Scanner in = new Scanner(System.in);
            if (weaponList.size() == 0)
            {
                weaponList.add(weapon);
                items.remove(weapon);
                strength = strength + weapon.getStrengthBonus();
                outScreen.appendText("You have equipped " + weapon.getItemName() + ".\n");
                outScreen.appendText("Your strength is now " + strength + ".\n");
            }
            else
            {
                outScreen.appendText("You already have a weapon equipped.\n");
                outScreen.appendText("You will need to unequip your current weapon before you can equip this one.\n");

            }

            assert(strength >= 1);

        }

    public void unequip()
    {
        if (weaponList.size() == 1) {
            Weapon current = weaponList.get(0);
            removeWeapon(current);
            items.add(current);
            outScreen.appendText("You have unequiped " + current.itemName + ".\n");
            outScreen.appendText("Your strength is now " + strength + ".\n");
        }

        else
        {
            outScreen.appendText("You do not currently have a weapon equipped.\n");
        }
    }

    /**
     *
     * @param character the character whom the player wishes to rob
     * @return true if player sucessfully robs the character
     *@throws IllegalArgumentException if character is null
     */
    public boolean rob(Character character)
    {
        if (character == null)
        {
            throw new IllegalArgumentException("Must select a character to rob");
        }
        boolean success = false;
        Random rand = new Random();
        int val = rand.nextInt(character.robStat);
        if (val == 0)
        {
            success = true;
            for (int i = 0; i<=character.charItem.size(); i++)
            {
                Item item=character.charItem.get(0);
                character.charItem.remove(0);
                items.add(item);
                outScreen.appendText("You have received " + item.itemName + ".\n");
            }
        }
        else
        {
            outScreen.appendText("You've been caught trying to steal!\n");
        }
        return success;
    }

    /**
     * no precondition needed because it will just return false if the item is not there.
     * @param itemName the name of the item you wish to see if the player has in his inventory. Case Sensitive.
     * @return true if player has the item is in player's inventory
     */
    public boolean has(String itemName)
    {
        boolean output = false;
        for (Item thing : items)
        {
            if (itemName.equals(thing.itemName))
            {
                output = true;
            }
        }
        return output;
    }

    /**
     *
     * @return true if the player is alive
     */
    public boolean isAlive()
    {
        return alive;
    }

    /**
     * adds item to player's inventory
     * @param item the item to add to players inventory
     * @throws IllegalArgumentException if no item is specified
     */
    public void addItem(Item item)
    {
        if (item == null)
        {
            throw new IllegalArgumentException("Must specify an item");
        }
        items.add(item);
        outScreen.appendText(item.getItemName() + " has been added to your inventory\n");
    }

    /**
     * removes the selected item from a players inventory
     * @param item the item to be removed from the inventory
     * @throws IllegalArgumentException if player does not have the item
     */
    public void removeItem(Item item)
    {
        if (!has(item.getItemName()))
        {
            throw new IllegalArgumentException("Player does not have this item");
        }
        items.remove(item);
    }

    /**
     * removes the selected item from a players inventory
     * @param itemName the name of item to be removed from the inventory
     * @throws IllegalArgumentException if player does not have the item
     */
    public void removeItem(String itemName)
    {
        if (!has(itemName))
        {
            throw new IllegalArgumentException("Player does not have this item");
        }

        Item found = null;
        for (Item item : items)
        {
            if (item.getItemName().equals(itemName))
            {
                found = item;
            }
        }
            items.remove(found);
    }

    /**
     *
     * @param strengthDif the amount by which the player's strength changes. Can be negative
     * player's strength can never be less than or equal to 0
     */
    public void setStrength(int strengthDif)
    {
        strength = strength + strengthDif;
        assert strength >=0;
    }

    /**
     *
     * @return player's strength
     */
    public int getStrength()
    {
        return strength;
    }

    /**
     *
     * @return player's name
     */
    public String getName()
    {
        return name;
    }

    /**
     * kills the player
     */
    public void die()
    {
      alive = false;
      outScreen.appendText("You are dead.\n");
      outScreen.appendText("Game over\n");
    }

    /**
     *
     * @return true if completed polykleitos quest
     */
    public boolean getPolQuest()
    {
        return polQuest;
    }

    /**
     * Changes polQuest to true
     */
    public void completePolQuest()
    {
        polQuest = true;
    }

    /**
     *
     * @return true if completed agora quest
     */
    public boolean getAgoraQuest()
    {
        return agoraQuest;
    }

    /**
     * Changes agoraQuest to true
     */
    public void completeAgoraQuest()
    {
        agoraQuest = true;
    }

    /**
     *
     * @return true if completed forest quest
     */
    public boolean getForestQuest()
    {
        return forestQuest;
    }

    /**
     * Changes forestQuest to true
     */
    public void completeForestQuest()
    {
        forestQuest = true;
    }

    /**
     *
     * @return true if player won the game
     */
    public boolean isCompleted()
    {
        return completed;
    }

    /**
     * Changes completed to true
     */
    public void complete()
    {
        completed = true;
    }
}

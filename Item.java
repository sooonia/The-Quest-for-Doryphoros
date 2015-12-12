package sample;

import javafx.scene.control.TextArea;

/**
 * Created by Sonia on 10/6/2015.
 */
public class Item
{
    String itemName;
    protected int healthBonus;
    protected int strengthBonus;

    public Item()
    {
        itemName="";
        healthBonus=0;
        strengthBonus=0;
    }

    public Item(String itemName,int healthBonus, int strengthBonus)
    {
        this.itemName = itemName;
        this.healthBonus = healthBonus;
        this.strengthBonus = strengthBonus;
    }

    public void use(TextArea outScreen, Monster mons)
    {
        mons.setHealth(healthBonus);
        mons.setStrength(strengthBonus);
        outScreen.appendText(mons.name + "'s health is now " +mons.getHealth()+"\n");
        outScreen.appendText(mons.name + "'s strength is now "+ mons.getStrength()+"\n\n");
        mons.charItem.remove(this);
    }

    public int getHealthBonus()
    {
        return healthBonus;
    }

    public int getStrengthBonus()
    {
        return strengthBonus;
    }

    public String getItemName()
    {
        return itemName;
    }

    public String toString()
    {
        return itemName;
    }
}
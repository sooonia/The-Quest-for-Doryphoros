package sample;

import javafx.scene.control.TextArea;

/**
 * Created by Sonia on 10/6/2015.
 */
public class Elixir extends Item
{
    TextArea outScreen;
    int numUses;
    public Elixir(TextArea outScreen, String itemName, int healthBonus, int strengthBonus, int numUses)
    {
        super(itemName, healthBonus, strengthBonus);
        this.numUses = numUses;
        this.outScreen = outScreen;
    }

    public void use(Player player)
    {

            player.setHealth(healthBonus);
            if(player.getHealth() > player.getHealthMax())
            {
                player.setHealth(player.getHealthMax());
            }
            player.setStrength(strengthBonus);
            outScreen.appendText("Health: " + player.getHealth() + "/" + player.getHealthMax()+"\n");
            outScreen.appendText("Strength: " + player.getStrength()+"\n");
            numUses --;
            if (numUses == 0)
            {
                outScreen.appendText(itemName +" is empty. You throw it into the appropriate recycling bin.\n");
                player.removeItem(this);
            }
            else if (numUses == 1)
            {
                outScreen.appendText("You have one use left.\n");
            }
            else
            {
                outScreen.appendText("You have " + numUses + " uses left.\n");
            }
    }

    public void listEffects()
    {
        outScreen.appendText("This is the " + itemName + ".\n");
        outScreen.appendText("  It adds " + healthBonus + " points to your health.\n");
        outScreen.appendText("  It adds " + strengthBonus + " points to your strength.\n");
        outScreen.appendText("  You can use it  " + numUses + " times\n");
    }


}

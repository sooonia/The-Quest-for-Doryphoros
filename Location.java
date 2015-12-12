package sample;


/**
 * Created by Sonia on 10/14/2015.
 */

import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;


public abstract class Location  implements ProcessInput
{
    protected javafx.scene.control.TextArea outScreen;
    protected String command;

    public Location(TextArea outScreen, String command)
    {
        this.outScreen = outScreen;
        this.command = command;
    }

    public void changeCommand(String newCom)
    {
        command = newCom;
    }

    public boolean processInput(String command, String keyword)
    {
        if (!command.contains("use")&& !command.contains("equip"))
        {
            command = command.toLowerCase();
        }
        return command.contains(keyword);

    }

    public boolean processInput(String command, Player player)
    {
        boolean success=true;
        if (command.contains("use"))
        {
            {
                boolean found = false;
                int i =0;
                while (!found && i<player.getItems().size())
                {
                    Item item = player.getItems().get(i);
                    if (command.contains(item.itemName) && item.getClass() == Elixir.class)
                    {
                        Elixir elix = (Elixir) item;
                        elix.use(player);
                        found = true;
                    }
                    i++;
                }
            }
        }

        else if (command.contains("unequip"))
        {
            player.unequip();
        }

        else if (command.contains("equip"))
        {
            boolean found = false;
            int i =0;
            while (!found && i<player.getItems().size())
            {
                Item item = player.getItems().get(i);
                if (command.contains(item.itemName) && (item.getClass() == Weapon.class))
                {
                    player.equip((Weapon)item);
                    found = true;
                }
                i++;
            }

        }
        else
        {
            success = false;
        }
        return success;

    }

    public void write(String line)
    {
        outScreen.appendText(line+"\n");
    }

}

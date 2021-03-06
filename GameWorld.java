package sample;

import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.control.TextArea;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import java.io.*;

public class GameWorld
{
    private TextArea outScreen;
    private String command;
    private Limbo limbo;
    Player player;
    private boolean nameTime;
    private boolean setup;

    public GameWorld(Limbo limbo, TextArea outScreen, String command)
    {
        this.outScreen = outScreen;
        this.command = command;
        this.limbo = limbo;
        nameTime=false;
        setup = false;

    }

    public boolean isSetup(){return setup;}

    public void changeCommand(String newCom)
    {
        command = newCom;
    }

    public Player getPlayer(){return  player;}

    public void main()

    {
        // Instructions

       if (!nameTime)
        {
        outScreen.appendText("\nWelcome to The Quest for Doryphoros. This is a choose your own adventure style game. \n" +
                "The goal is to find the Spear Bearer sculpture. Most of the characters you can kill, rob, or persuade. \n" +
                "Elixirs may be used at any point in the game with the use command followed by the name of the elixir. \n" +
                "To equip a weapon, just type equip followed by the name of the weapon. " +
                "\nYou can leave the location you are in by typing \"leave\" followed by your current location." +
                "\nType inventory to see all of your items. Type stats to see your health and strength. \n" +
                "Type help if you forget some of these commands. \nTry not to die.\n" +
                "Good Luck! \n \n");
        outScreen.appendText("Athena: What is your name, adventurer?\n");
        nameTime = true;
        }

        else {
            String name = command;

            player = new Player(outScreen, name);
            setup = true;
            limbo.changeInLimbo();
            limbo.runLimbo(player);

        }
    }
}

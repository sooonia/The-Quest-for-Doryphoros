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
                "Elixirs may be used during battle with the use command. \nTo equip a weapon, just type equip followed by the name of the weapon. \nBe warned, these features are case sensative. Try not to die.\n" +
                "Good Luck! \n \n");
        outScreen.appendText("Athena: What is your name, adventurer?\n");
        nameTime = true;
        }

        else {
            String name = command;

            player = new Player(outScreen, name);
            FileWriter myWriter;
            try {
                myWriter = new FileWriter("characters.csv");
                myWriter.write(name + "\n");

                myWriter.close();
            } catch (IOException e) {
                outScreen.appendText("Whoops. Something went wrong.");
            }
            setup = true;
            limbo.changeInLimbo();
            limbo.runLimbo(player);

        }
    }
}

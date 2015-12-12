package sample;

import javafx.scene.control.*;
import javafx.scene.control.TextArea;

import java.awt.*;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;

/**
 * Created by Sonia on 10/14/2015.
 */


public class Limbo extends Location
{

    private Forest forest;
    private Agora agora;
    private PolHouse house;
    boolean started;
    boolean in;
    private boolean inLimbo;
    private boolean answered;

    public Limbo(TextArea outScreen, String command, Forest forest, Agora agora, PolHouse house)
    {
        super(outScreen, command);
        this.forest = forest;
        this.agora = agora;
        this.house = house;

        in = false;
        started = false;
        answered=false;
    }

    public void changeInLimbo()
    {
        inLimbo=!inLimbo;
    }

    public boolean isInLimbo()
    {
        return inLimbo;
    }

    public void setForest(Forest newForest)
    {
        forest = newForest;
    }

    public void setAgora(Agora newAgora)
    {
        agora = newAgora;
    }

    public void setHouse(PolHouse newHouse)
    {
        house = newHouse;
    }


    public boolean in()
    {
        return in;
    }

    public void runLimbo(Player player)
    {
        in = true;
        if (player.isAlive()) {
            if (!started) {
                started = true;
                outScreen.appendText("Athena: Hello, " + player.name + "\n");
                outScreen.appendText("Athena: I'd like to welcome to you to Athens. I'm afraid you've come upon dark times here.\n");
                outScreen.appendText("Athena: Would you like to know the cause of all this trouble? \n");
                outScreen.appendText("Answer yes or no\n");

            } else if (!answered) {

                if (processInput(command, "yes")) {
                    outScreen.appendText("Athena: There is a terrible cyclops rampaging through the forest; \n" +
                            " some lunatic is running around the agora, \n");
                    outScreen.appendText(" and worst of all: someone has stolen the great sculpture: Spear Bearer. \n" +
                            " Polykleitos has not left his home since! \n \n" +
                            "Go either to the forest, the agora, or the residence of Polykleitos.\n");
                    answered = true;
                } else if (processInput(command, "no")) {
                    outScreen.appendText("You have angered Athena. Good luck figuring out your next move.\n");
                    answered = true;
                } else {
                    outScreen.appendText("Not a valid command. Answer yes or no\n");
                }
            } else if (processInput(command, "forest")) {
                inLimbo = false;
                forest.changeInForest();
                forest.runForest(player);
            } else if (processInput(command, "pol") || processInput(command, "house") || processInput(command, "home") || processInput(command, "residence")) {
                inLimbo = false;
                house.changeAtPolHouse();
                house.runPolHouse(player);
            } else if (processInput(command, "agora")) {
                inLimbo = false;
                agora.changeInAgora();
                agora.runAgora(player);
            } else {
                outScreen.appendText("Not a valid command. Please go somewhere.");
            }

        }
    }
}

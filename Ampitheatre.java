package sample;

import javafx.scene.control.TextArea;

import java.util.Scanner;

/**
 * Created by Sonia on 11/11/2015.
 */
public class Ampitheatre extends Location
{
    //Constructors
    private Character pte;
    private Item dory;

    private Monster zeus;
    private Monster roach;

    private boolean beatMonster;
    private boolean hasGodKey;
    private boolean started;
    private boolean started1;
    private boolean zeusFight;
    private boolean roachFight;

    public Ampitheatre(TextArea outScreen, String command)
    {
        super(outScreen,command);
        pte = new Character("Polykleitos the Younger",1000000);
        dory = new Item("Spear Bearer", 0, 0);
        pte.addItem(dory);

        zeus = new Monster("Zeus",15,50);
        roach = new Monster("Cucaracha", 3, 30);

        beatMonster = false;
        hasGodKey = false;
        started = false;
        started1 = false;
        zeusFight = false;
        roachFight = false;
    }

    public void runAmpitheatre(Player player)
    {
        if (player.isAlive()) {
            if (!started1) {
                hasGodKey = player.has("Gods' Key");
                write("\nYou make your way over to the ampitheatre on the outskirts of town.");
                write("You put the three keys through the main gate, walk through, and it shuts behind you!");
                write("Not cliche at all... I'm sure getting the sculpture back will open it.");

                pte.say(outScreen, "So you think you're very clever, huh? Well let's see how clever you are against my monster!");
                write("Are you ready?");
                started1 = true;
            } else {
                if (processInput(command, "no") && !started) {
                    write("Tough cookies. Get your sword out.");
                } else if (processInput(command, "kill") && !started) {
                    write("Come on, he's just a troubled kid.");
                } else if ((processInput(command, "yes") || processInput(command, "draw") || processInput(command, "sword")||processInput(command, "fine")) && !started && hasGodKey) {
                    started = true;
                    write("A big door opens and Zeus comes flying out swinging a flaming sword. You may attack, or use an elixir. Fleeing is not an option.");
                    zeusFight = true;
                } else if ((processInput(command, "yes") || processInput(command, "draw") || processInput(command, "sword")||processInput(command, "fine")) && !started) {
                    started = true;
                    write("A big door opens and a cockroach the size of your face flies at you. You may attack, or use an elixir. Fleeing is not an option.");
                    roachFight = true;
                } else if (processInput(command, "attack") && roachFight && roach.isAlive()) {
                    player.fight(roach);
                    write("");
                    if (!roach.isAlive()) {
                        roachFight = false;
                        pte.say(outScreen, "Oh no! What happened?! Where was...\nJust take it! Having it has only making my self esteem that much worse.");
                        pte.give(dory, player);
                        write("You win!!\nI didn't think you could do it, but look at that!");
                    }

                } else if (processInput(command, "attack") && zeusFight && zeus.isAlive()) {
                    player.fight(zeus);
                    write("");
                    if (!zeus.isAlive()) {
                        zeusFight = false;
                        pte.say(outScreen, "YOU KILLED ZEUS?! OH MY GODS! Take the damn thing!");
                        pte.give(dory, player);
                        write("You win!!\nI didn't think you could do it, but look at that!");
                    }
                } else if (processInput(command, "flee") && (roachFight || zeusFight)) {
                    write("There is nowhere to flee to.");
                } else if (processInput(command, "inventory") || processInput(command, "items")) {
                    write(player.getItems().toString());
                } else if (processInput(command, player)) {
                    write("");
                } else {
                    write("Not a valid command");
                }


            }
        }
    }
}

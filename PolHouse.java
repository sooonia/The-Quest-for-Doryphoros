package sample;

import javafx.scene.control.TextArea;

import java.util.Scanner;
/**
 * Created by Sonia on 11/2/2015.
 */
public class PolHouse extends Location
{

    private boolean atPolHouse;
    private Limbo limbo;

    //Constructors
    private Character poly;
    private Item houseKey;
    private Character athena;
    private Item godKey;
    Scanner in;

    private boolean inHouse;
    private boolean inBack;
    private boolean angryPoly;
    private boolean finished;
    private boolean started;
    private boolean ans1;

    /**
     * Creates Polykleitos' house
     */
    public PolHouse(TextArea outScreen, String command, Limbo limbo)
    {
        super(outScreen,command);
        this.limbo = limbo;
        atPolHouse=false;

        poly = new Character("Polykleitos", 50);
        houseKey = new Item("House Key", 0,0);
        poly.addItem(houseKey);

        athena = new Character("Athena",10000);
        godKey = new Item("Gods' Key", 0, 0);
        athena.addItem(godKey);

        started=false;
        inHouse = false;
        inBack = false;
        angryPoly = false;
        finished = false;
        ans1 = false;

        in = new Scanner(System.in);
    }

    public void changeAtPolHouse()
    {
        atPolHouse=!atPolHouse;
    }

    public boolean isAtPolHouse()
    {
        return atPolHouse;
    }

    /**
     * Runs the house quest
     * Uses try/catch
     * @param player the player playing the game
     * @throws IllegalArgumentException if player is null
     */
    public void runPolHouse(Player player)
    {
        if (player == null)
        {
            throw new IllegalArgumentException("Cannot run without a player");
        }

        if (player.isAlive()) {
            if (!started) {
                write("You go through the city to Polykleitos' home.");
                write("");
                write("You stand in front of a cute little house.");
                started = true;
            } else {

                if (finished) {
                    write("There isn't a whole lot here for you. Try going to the agora or forest instead");
                    atPolHouse = false;
                    started = false;
                    limbo.changeInLimbo();

                }
                //Getting inside the house without bread
                else if ((processInput(command, "path") || (processInput(command, "go") && (processInput(command, "back") || processInput(command, "behind")))) && !inBack && !inHouse) {
                    write("You go behind the house");
                    inBack = true;
                } else if ((processInput(command, "knock") || processInput(command, "enter") || processInput(command, "in ")) && !inHouse && !inBack && !player.has("Bread")) {
                    poly.say(outScreen, "No! No one is allowed inside until I have my Dory back!");
                } else if ((processInput(command, "look around") || processInput(command, "check")) && !inHouse && !inBack) {
                    write("The door seems to be firmly locked. I thought this was a culture of hospitality. \nThen again, what sort of guest does not even bring something for his host's table.");
                    write("You also notice a little path that leads around the house and into the backyard.");

                } else if ((processInput(command, "back") || processInput(command, "front")) && inBack) {
                    inBack = false;
                    write("You go back to the front.");
                } else if ((processInput(command, "look around") || processInput(command, "check")) && !inHouse && inBack) {
                    write("There is a window that may be juuuust big enough to climb through.");
                } else if ((processInput(command, "climb") || processInput(command, "window")) && inBack && !inHouse) {
                    inBack = false;
                    inHouse = true;
                    angryPoly = true;
                    write("Ever so gracefully, you try to climb through the window, but you get stuck. Nice going, tubs.");
                    poly.say(outScreen, "What is going on in here? Come to rob me again??");
                    try {
                        player.setHealth(-9);
                        write("Polykleitos hits you with a wooden spoon.");
                        write("You have " + player.getHealth() + " health points remaining.");
                        write("Polykleitos then bursts to tears, wailing about his lost SON.");
                    } catch (ArithmeticException exc) {
                        write("Polykleitos hits you with a wooden spoon.");
                        player.die();
                        write("That wasn't very glamorous.");
                    }
                } else if ((processInput(command, "ask") || processInput(command, "lost") || processInput(command, "missing") ||processInput(command, "son")) && angryPoly && inHouse && !ans1) {
                    ans1=true;
                    poly.say(outScreen, "He's gone! My beautiful Spear Bearer, my perfect son. Now who will sit beside me at dinner? My NEPHEW? Oh leave me!");
                } else if (processInput(command, "nephew") && angryPoly && inHouse) {
                    poly.say(outScreen, "Yes, Polykleitos the Younger, my good-for-nothing nephew. Not nearly as good as my real son. Never will be.");
                    poly.say(outScreen, "He thinks he can earn my love by building that ampitheatre, HA!");
                    write("");
                    write("Maybe it's time to leave Polykleitos to his grief.");

                } else if ((processInput(command, "leave") || processInput(command, "exit")) && inHouse && angryPoly) {
                    inHouse = false;
                    athena.say(outScreen, "Hello, " + player.getName() + ". Good to see you once again.");
                    athena.say(outScreen, "Polykleitos is not himself. You must right this wrong. Go to the ampitheatre once you have the 3 Great Keys. Here is the Gods' Key.");
                    athena.give(godKey, player);
                    finished = true;
                    player.completePolQuest();
                    if (player.getForestQuest() && player.getAgoraQuest()) {
                        write("You have completed the last quest. Congratulations. Now go get that sculpture!!");
                    } else if (player.getForestQuest()) {
                        write("You have completed the quest in the house. Congratulations. All that's left to do is go to the agora!");
                        limbo.changeInLimbo();
                    } else if (player.getAgoraQuest()) {
                        write("You have completed the quest in the house. Congratulations. All that's left to do is to go the forest!");
                        limbo.changeInLimbo();
                    } else {
                        write("You have completed the quest in the house. Congratulations. Go now to either the forest or the agora!");
                        limbo.changeInLimbo();
                    }
                } else if ((processInput(command, "knock") || processInput(command, "enter") || processInput(command, "in")) && !inHouse && !inBack && player.has("Bread")) {
                    inHouse = true;
                    poly.say(outScreen, "Noooo! Not guests at a time like this!");
                    poly.say(outScreen, "... You brought bread and oil? Well, come sit down at least. Let us break bread and talk.");
                    player.give("Bread", poly);
                    player.give("Olive Oil", poly);
                    poly.say(outScreen, "Thank you, but this cannot make up for my lost SON.");

                } else if ((processInput(command, "son") || processInput(command, "lost") || processInput(command, "missing")) && inHouse &&!ans1) {
                    ans1 = true;
                    poly.say(outScreen, "He's gone! My beautiful Spear Bearer, my perfect son. Now who will sit beside me at dinner? My NEPHEW?");
                } else if (processInput(command, "nephew") && inHouse) {
                    poly.say(outScreen, "Yes, Polykleitos the Younger, my good-for-nothing nephew. Not nearly as good as my real son. Never will be.");
                    poly.say(outScreen, "He thinks he can earn my love by building that ampitheatre, HA!");
                    poly.say(outScreen, "I wouldn't be surprised if he was the one who stole my REAL son. \nCould you go check it out for me? You'll need 3 keys. Here's one.");
                    poly.give(houseKey, player);
                    finished = true;
                    atPolHouse = false;
                    player.completePolQuest();
                    if (player.getForestQuest() && player.getAgoraQuest()) {
                        write("You have completed the last quest. Congratulations. Now go get that sculpture!!");
                    } else if (player.getForestQuest()) {
                        write("You have completed the quest in the house. Congratulations. All that's left to do is go to the agora!");
                        limbo.changeInLimbo();
                    } else if (player.getAgoraQuest()) {
                        write("You have completed the quest in the house. Congratulations. All that's left to do is to go the forest!");
                        limbo.changeInLimbo();
                    } else {
                        write("You have completed the quest in the house. Congratulations. Go now to either the forest or the agora!");
                        limbo.changeInLimbo();
                    }
                    started = false;


                } else if (processInput(command, "inventory") || processInput(command, "items")) {
                    write(player.getItems().toString());
                } else if (processInput(command, player)) {
                    write("");
                } else if (processInput(command, "leave") && (processInput(command, "home") || processInput(command, "polykleitos")) || processInput(command, "house")) {
                    atPolHouse = false;

                    write("Go to either the forest or to the agora. Or you can go back to the house.");
                    started = false;
                    limbo.changeInLimbo();
                } else if (processInput(command, "yay")) {
                    write("I'm glad you're happy.");
                } else if (processInput(command, "thank you")) {
                    write("You're welcome.");
                } else if (processInput(command, "kill") && processInput(command,"self")) {
                    write ("1 (800) 273-8255");
                }else if (processInput(command, "help") || processInput(command, "instructions")) {
                    write("Remember, when you're talking to a character in the game, you can rob, persuade, or kill them. Looking around may sometimes help.");
                } else {
                    write("Not a valid command");
                }
            }
        }
    }
}

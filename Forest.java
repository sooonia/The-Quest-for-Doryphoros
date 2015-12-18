package sample;

/**
 * Created by Sonia on 10/13/2015.
 */
import javafx.scene.control.TextArea;


public class Forest extends Location
{
    private Limbo limbo;
    private boolean inForest;

    // CONSTRUCTORS
    Character pan;

    Weapon oldSword;
    Weapon club;
    Elixir superElixir;
    Item wine;
    Item flute;
    String tempName;
    Item forestKey;



    //Making Cyclopses
    Monster cyc;
    Monster cyc2;

    boolean inForest2;
    boolean withPan;
    boolean nearOliveTree;
    boolean hasSword;
    boolean hasElixir;
    boolean panQuest;
    boolean nearCave;
    boolean inCave;
    boolean cyc1Fight;
    boolean cyc2Fight;
    boolean wasClever;
    boolean gettingName;


    public Forest(TextArea outScreen, String command, Limbo limbo)
    {
        super(outScreen,command);
        this.limbo = limbo;
        inForest=false;

        // CONSTRUCTORS
        // Forest constructors
        //Making Pan
        pan = new Character("Pan", 1);
        flute = new Item("Pan's Flute", 0, 0);
        wine = new Item("Magic Wine", 0, -10);
        pan.addItem(flute);
        pan.addItem(wine);

        //Making items in forest
        forestKey = new Item("The Forest Key", 0, 0);
        superElixir = new Elixir(outScreen,"Super Elixir", 10, 2, 3);
        oldSword = new Weapon("Abandoned Sword", 3);
        club = new Weapon("Cyclops' Club", 10);


        //Making Cyclopses
        cyc = new Monster("Cyclops", 22, 10);
        cyc.addItem(forestKey);
        cyc.addItem(club);
        cyc2 = new Monster("Giant Cyclops", 15, 45);

        inForest2 = false;
        nearOliveTree = false;
        hasSword = false;
        hasElixir = false;
        panQuest = false;
        withPan=false;
        nearCave=false;
        inCave=false;
        cyc1Fight=false;
        cyc2Fight=false;
        wasClever = false;
        gettingName = false;

    }

    public boolean isInForest(){return inForest;}

    public void changeInForest()
    {
        inForest=!inForest;
    }

    public void runForest(Player player)
    {
        if (player.isAlive()) {
            if (!inForest2) {
                gettingName = false;
                withPan=false;
                nearCave=false;
                inCave=false;
                cyc1Fight=false;
                cyc2Fight=false;
                write("\nWelcome to the forest. Feel free to look around.");
                inForest2 = true;
            } else {
                if (processInput(command, "look around") && pan.isAlive() && !panQuest) {
                    write("You are currently in a little clearing. \nThere is a pretty olive tree and some flowers. \n" +
                            "There are some big footprints going west. \n" +
                            "There is also a little man with goat legs playing the fiddle");
                } else if (processInput(command, "look around")) {
                    write("You are currently in a little clearing. \n" +
                            "There is a pretty olive tree and some flowers. \n" +
                            "There are some big footprints going west.");
                } else if (gettingName) {
                    tempName = command;
                    gettingName = false;
                    if (tempName.toLowerCase().equals("no one") || tempName.toLowerCase().equals("nobody")) {
                        wasClever = true;
                    }
                    cyc.say(outScreen, "Have you brought me a gift of wine, " + tempName + "? I really like wine. If it is good wine I may not kill you.");
                }

                //Pan Quest
                else if ((processInput(command, "man") || processInput(command, "goat")||processInput(command,"pan")) && !panQuest && pan.isAlive() && !withPan) {
                    withPan = true;
                    pan.say(outScreen, "You look funny. Go away.");
                } else if ((processInput(command, "man") || processInput(command, "goat")||processInput(command,"pan")) && panQuest && pan.isAlive() && !withPan) {
                    write("\nYou try to approach Pan, but he pulls a knife out and growls at you.\nYou decide to just stay in the clearing.");
                } else if (processInput(command, "kill") && withPan) {
                    withPan = false;
                    player.kill(pan);
                } else if (processInput(command, "rob") && withPan) {
                    if (player.rob(pan)) ;
                    {
                        pan.say(outScreen, "Hey! I saw that. Get out of here before I really get angry");
                        write("");
                        write("You hurry back to the clearing.");
                        panQuest = true;
                        withPan = false;
                        withPan = false;
                    }
                } else if (processInput(command, "persuade") && withPan) {
                    pan.say(outScreen, "Aw, shucks. You're making me blush, " + player.name + ". \n Look, if you're here to fight the cyclops check for a sword under the olive tree");
                } else if ((processInput(command, "away") || processInput(command, "go") || processInput(command, "leave") || processInput(command, "back")) && withPan) {
                    write("You have left Pan and gone back to the clearing. Feel free to come back");
                    withPan = false;
                }

                //Getting Items
                else if ((processInput(command, "tree") || processInput(command, "olive")) && !hasSword) {
                    write("");
                    write("There is an old sword underneath the tree. It may be useful. Try picking it up.");
                    nearOliveTree = true;
                } else if ((processInput(command, "pick up") || processInput(command, "take") || processInput(command, "pick it up")) && nearOliveTree) {
                    hasSword = true;
                    player.addItem(oldSword);
                    write("Its power is " + oldSword.getStrengthBonus() + ". You'll have to equip it if you want its bonus.");
                    nearOliveTree = false;
                } else if ((processInput(command, "leave") || processInput(command, "no") || processInput(command, "back")) && nearOliveTree) {
                    write("You go back to the clearing");
                    nearOliveTree = false;
                } else if ((processInput(command, "olive") || processInput(command, "tree")) && hasSword) {
                    write("Just some olives.");
                }  else if ((processInput(command, "olive") && processInput(command, "eat")) && hasSword) {
                    write("Yum.");
                    player.setHealth(2);
                    write("Your health has gone up 2 points!");

                }else if (processInput(command, "flowers") && !hasElixir) {
                    write("There seems to be an elixir hidden among the flowers. You pick it up");
                    write("");
                    hasElixir = true;
                    player.addItem(superElixir);
                    superElixir.listEffects();
                } else if (processInput(command, "flowers") && hasElixir) {
                    write("Pretty flowers.");
                }

                //Following the Cyclops

                else if ((processInput(command, "west") || processInput(command, "footprints")) && cyc.isAlive()) {
                    write("");
                    nearCave = true;
                    write("You go west after the footprints. \n");

                    write("You happen upon a cave.");
                } else if ((processInput(command, "west") || processInput(command, "footprints")) && !cyc.isAlive()) {
                    write("Eh. Let's not go back there. That cyclops probably smells by now.");

                } else if ((processInput(command, "check") || processInput(command, "look")||processInput(command,"examine")) && nearCave && cyc.isAlive()) {
                    write("There is a rumbling coming from inside the cave.");
                } else if ((processInput(command, "check") || processInput(command, "look")||processInput(command,"examine")) && nearCave && !cyc.isAlive()) {
                    write("The cave has collapsed in on itself.");
                } else if ((processInput(command, "enter") || processInput(command, "in")) && nearCave && cyc.isAlive()) {
                    write("You enter the cave and see a giant cyclops. He wields a club and looks angry. \nYou size him up and estimate his health to be " + cyc.getHealth() + " and his strength to be " + cyc.getStrength() + ".");
                    cyc.say(outScreen, "You who enter my cave. Who are you?");
                    if (player.has("Wisdom")) {
                        write("Your wisdom speaks to you: perhaps you should say that you are \"no one\".");
                    }
                    gettingName = true;
                    nearCave = false;
                    inCave = true;
                } else if ((processInput(command, "enter") || processInput(command, "in")) && nearCave && !cyc.isAlive()) {
                    write("The cave has collapsed in on itself.");
                } else if (processInput(command, "back") && nearCave) {
                    nearCave = false;
                    write("You go back to the clearing.");
                } else if ((processInput(command, "wine") || processInput(command, "give") || processInput(command, "yes")) && inCave) {
                    if (player.give("Magic Wine", cyc)) {
                        wine.use(outScreen, cyc);
                        cyc.say(outScreen, "That tasted funny. " + tempName + " is trying to kill me!!");
                        cyc1Fight = true;
                        write("The Cyclops yelled this pretty loudly. Hopefully no one heard.");
                        write("");
                        write("The Cyclops rushes toward you. attack, flee or use an elixir.");
                    }
                } else if ((processInput(command, "back") || processInput(command, "leave") || processInput(command, "exit")) && inCave && !cyc1Fight) {
                    inCave = false;
                    nearCave = true;
                    write("You are outside the cave.");

                    if (!cyc.isAlive() && nearCave && !wasClever) {
                        write("There is an even bigger cyclops waiting for you outside of the cave! You just cannot catch a break today. He looks pretty unhappy.");
                        cyc2.say(outScreen, "You killed my brother, mortal!");
                        write("He rushes toward you. Attack, use an elixir, or flee.");
                        cyc2Fight = true;
                    }
                } else if (inCave && !cyc1Fight && cyc.isAlive()) {
                    cyc1Fight = true;
                    cyc.say(outScreen, tempName + " has snuck into my cave and wishes to attack me!!! \n");
                    write("The Cyclops yelled this pretty loudly. Hopefully no one heard.");
                    write("");
                    write("The Cyclops rushes toward you. attack, flee or use an elixir.");
                } else if (processInput(command, "attack") && cyc1Fight && cyc.isAlive()) {
                    player.fight(cyc);
                    write("");
                    if (!cyc.isAlive()) {
                        cyc1Fight = false;
                        write("The club's power is " + club.strengthBonus + ".");
                        if (!wasClever) {
                            write("Please exit the cave.");
                        }

                        else {
                            player.completeForestQuest();
                            inForest = false;
                            inForest2 = false;
                            if (player.getAgoraQuest() && player.getPolQuest()) {
                                write("\nYou have completed the last quest. Congratulations. Now go get that sculpture!!");
                            } else if (player.getAgoraQuest()) {
                                write("\nYou have completed the quest in the forest. Congratulations. All that's left to do is visit Polykleitos!");
                                limbo.changeInLimbo();
                            } else if (player.getPolQuest()) {
                                write("\nYou have completed the quest in the forest. Congratulations. All that's left to do is to go the agora!");
                                limbo.changeInLimbo();
                            } else {
                                write("You have completed the quest in the forest. Congratulations. Go now to either the agora or to Polykleitos");
                                limbo.changeInLimbo();
                            }

                        }

                    }

                } else if (processInput(command, "flee") && cyc1Fight) {
                    if (player.flee(cyc)) {
                        inCave = false;
                        nearCave = true;
                        write("You are no back outside of the cave.");
                        cyc1Fight = false;
                    }
                } else if (processInput(command, "attack") && cyc2Fight && cyc2.isAlive()) {
                    player.fight(cyc2);
                    write("");
                    if (!cyc2.isAlive()) {
                        player.completeForestQuest();
                        inForest = false;
                        inForest2 = false;
                        if (player.getAgoraQuest() && player.getPolQuest()) {
                            write("\nYou have completed the last quest. Congratulations. Now go get that sculpture!!");
                        } else if (player.getAgoraQuest()) {
                            write("\nYou have completed the quest in the forest. Congratulations. All that's left to do is visit Polykleitos!");
                            limbo.changeInLimbo();
                        } else if (player.getPolQuest()) {
                            write("\nYou have completed the quest in the forest. Congratulations. All that's left to do is to go the agora!");
                            limbo.changeInLimbo();
                        } else {
                            write("You have completed the quest in the forest. Congratulations. Go now to either the agora or to Polykleitos");
                            limbo.changeInLimbo();
                        }
                    }

                } else if (processInput(command, "flee") && cyc2Fight) {
                    if (player.flee(cyc)) {
                        inForest = false;
                        inForest2 = false;
                        cyc2Fight = false;
                        limbo.changeInLimbo();
                        limbo.runLimbo(player);

                    }
                } else if (processInput(command, "inventory") || processInput(command, "items")) {
                    write(player.getItems().toString());
                } else if (processInput(command, "stats") || processInput(command, "health")||processInput(command, "strength")) {
                    write("Your health is " +player.getHealth()+"/"+player.getHealthMax());
                    write("Your strength is "+player.getStrength());
                } else if (processInput(command, player)) {
                    write("");
                } else if (processInput(command, "leave") && processInput(command, "forest")) {
                    inForest = false;
                    inForest2 = false;
                    limbo.changeInLimbo();
                    write("Go to either the agora or to Polykleitos. Or you can go back to the forest.");
                } else if (processInput(command, "yay")) {
                    write("I'm glad you're happy.");
                } else if (processInput(command, "thank you")) {
                    write("You're welcome.");
                } else if (processInput(command, "help") || processInput(command, "instructions")) {
                    write("Remember, when you're talking to a character in the game, you can rob, persuade, or kill them. Looking around may sometimes help.");
                } else if (processInput(command, "kill") && processInput(command,"self")) {
                    write ("1 (800) 273-8255");
                } else if (processInput(command,"learn")&&(processInput(command, "kung fu")||processInput(command,"karate"))){
                    write("You bust out some sick new moves.");
                    player.setStrength(3);
                    write("Your strength has increased to " +player.getStrength()+"!");
                } else {
                    write("Not a valid command");
                }

            }
        }

    }


}

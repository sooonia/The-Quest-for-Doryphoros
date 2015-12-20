package sample;

import javafx.scene.control.TextArea;

import java.util.Scanner;

/**
 * Created by Sonia on 10/23/2015.
 */
public class Agora extends Location
{
    private Limbo limbo;
    //Constructors
    private Character soc;
    private Item wisdom;
    private Character omw;
    private Elixir weakElixir;
    private Elixir blueElixir;
    private Character guard;
    private Item agoraKey;
    private Character merchant;
    private Item oil;
    private Item bread;
    private Item powder;
    private Character priest;
    private Weapon heroSword;


    private boolean inAgora;
    private boolean inCenter;
    private boolean nearStalls;
    private boolean inCircle;
    private boolean robAttemptOmw;
    private boolean nearMerchant;
    private boolean robAttemptMerch;
    private boolean helped;
    private boolean inTemple;
    private boolean robAttemptPriest;
    private boolean gavePowder;
    private boolean talking;
    private boolean talking1;
    private boolean talking2;
    private boolean talked;
    private boolean hasSword;
    private boolean started;


    /**
     * Creates the agora
     */
    public Agora(TextArea outScreen, String command, Limbo limbo)
    {
        super(outScreen,command);
        this.limbo = limbo;
        inAgora = false;

        soc = new Character("Socrates", 5);
        wisdom = new Item("Wisdom",0,0);
        soc.addItem(wisdom);

        omw = new Character("Old Market Woman", 4);
        weakElixir = new Elixir(outScreen,"Weak Elixir", 3, 0, 1);
        blueElixir = new Elixir(outScreen,"Blue Elixir", 5, 1, 2);
        omw.addItem(weakElixir);
        omw.addItem(blueElixir);

        guard = new Character("Market Guard", 100);
        agoraKey = new Item("Agora Key", 0 , 0);
        guard.addItem(agoraKey);

        merchant = new Character("Merchant", 5);
        oil = new Item("Olive Oil", 0, 2);
        bread = new Item("Bread", 2, 0);
        powder = new Item("Mysterious Powder", 0,0);
        merchant.addItem(bread);
        merchant.addItem(oil);
        merchant.addItem(powder);

        priest = new Character("Priest", 3);
        heroSword = new Weapon("Sword of Heroes", 25);
        priest.addItem(heroSword);

        inCenter = true;
        nearStalls = false;
        inCircle = false;
        robAttemptOmw = false;
        nearMerchant = false;
        robAttemptMerch = false;
        helped =false;
        inTemple = false;
        robAttemptPriest = false;
        gavePowder = false;
        talking= false;
        talking1=false;
        talking2=false;
        talked = false;
        hasSword = false;
        started = false;

    }

    public void changeInAgora()
    {
        inAgora=!inAgora;
    }

    public boolean isInAgora()
    {
        return inAgora;
    }

    /**
     * Runs the agora
     * Does not need pre or post conditions because of the setup of the if loops
     * @param player the player playing the game
     */
    public void runAgora(Player player)
    {
        if (player.isAlive()) {
            if (!started) {
                inCenter = true;
                nearStalls = false;
                inCircle = false;
                nearMerchant = false;
                inTemple = false;
                talking= false;
                talking1=false;
                talking2=false;
                write("\n Welcome to the agora. This is the hustling and bustling center of the city. Check it out.\n");
                started = true;
            } else {
                if ((processInput(command, "check") || processInput(command, "look")) && inCenter && soc.isAlive()) {
                    write("You stand in the center of the agora. Market stalls line the streets. \nA great big temple stands tall in the center. What incredible columns it has. \nThere seems to be a big circle of people crowded around a robed man.\n");
                } else if ((processInput(command, "check") || processInput(command, "look")) && inCenter && !soc.isAlive()) {
                    write("You stand in the center of the agora. Market stalls line the streets. \nA great big temple stands tall in the center. What incredible columns it has.\n");
                } else if ((processInput(command, "stalls") || processInput(command, "market")) && inCenter && !helped && merchant.isAlive()) {
                    inCenter = false;
                    nearStalls = true;
                    write("You go toward the market stalls. \n There is a cute old little lady struggling to get her groceries into a cart. \n A colorfully dressed merchant is selling olive oil and bread.\n");
                } else if ((processInput(command, "stalls") || processInput(command, "market")) && inCenter && merchant.isAlive() && helped) {
                    inCenter = false;
                    nearStalls = true;
                    write("You go toward the market stalls. \n A colorfully dressed merchant is selling olive oil and bread. \n The old woman smiles at you and waves.\n");
                } else if ((processInput(command, "stalls") || processInput(command, "market")) && inCenter && !merchant.isAlive() && helped) {
                    inCenter = false;
                    nearStalls = true;
                    write("You go toward the market stalls. \n The old woman smiles at you and waves.\n");
                } else if ((processInput(command, "stalls") || processInput(command, "market")) && inCenter && !helped && !merchant.isAlive()) {
                    inCenter = false;
                    nearStalls = true;
                    write("You go toward the market stalls. \nThere is a cute old little lady struggling to get her groceries into a cart.\n");
                } else if ((processInput(command, "look") || processInput(command, "check")) && nearStalls && helped && merchant.isAlive()) {
                    write("You stand near the thankful old woman. The merchant is a little further up selling his goods.\n");
                } else if ((processInput(command, "look") || processInput(command, "check")) && nearStalls && !helped && merchant.isAlive()) {
                    write("The old woman struggles with her groceries next to you. The merchant is a little further up selling his goods.\n");
                } else if ((processInput(command, "look") || processInput(command, "check")) && nearStalls && helped && !merchant.isAlive()) {
                    write("You stand near the thankful old woman. The merchant's empty stall is a little furhter up. It looks so sad.\n");
                } else if ((processInput(command, "look") || processInput(command, "check")) && nearStalls && !helped && !merchant.isAlive()) {
                    write("The old woman struggles with her groceries next to you. The merchant's empty stall is a little further up. It looks so sad.\n");
                }

                //Dealing with Old Market Woman
                else if (((processInput(command, "help") || processInput(command, "assist") || processInput(command, "aid")) && (processInput(command, "lady") || processInput(command, "woman"))) && nearStalls && !robAttemptOmw && !helped) {
                    omw.say(outScreen, "Thank you, dear! Take this elixir! \nAnd here's some information: that merchant sells some... secret goods. They may be useful.");
                    omw.give(blueElixir, player);
                    blueElixir.listEffects();
                    helped = true;
                } else if ((processInput(command, "kill") && (processInput(command, "lady") || processInput(command, "woman"))) && nearStalls) {
                    write("No. That's terrible. She's a cute little old lady. Do something else.\n");
                } else if ((processInput(command, "rob") && (processInput(command, "lady") || processInput(command, "woman"))) && nearStalls && !robAttemptOmw) {
                    robAttemptOmw = true;
                    if (player.rob(omw)) {
                        omw.say(outScreen, "You're standing awfully close to me.");
                        weakElixir.listEffects();
                    } else {
                        omw.say(outScreen, "Thief!! Guards! Someone! Help!");
                        write("Now you've done it. Here comes a guard.");
                        if (player.getWeaponList().size() == 1) {
                            guard.say(outScreen, "I'm confiscating your weapon, criminal. And stay away from the Old Woman.");
                            Weapon current = player.getWeaponList().get(0);
                            player.removeWeapon(current);
                            player.setStrength(-1 * current.getStrengthBonus());
                        } else {
                            guard.say(outScreen, "I've got my eye on you. You'd better stay away from the Old Woman.\n");
                        }
                    }
                } else if ((processInput(command, "persuade") && (processInput(command, "lady") || processInput(command, "woman"))) && nearStalls) {
                    omw.say(outScreen, "Ok friend, so here's the gossip: That Socrates is a total nightmare, real douchebag. Everyone kind of wants him dead.\n");
                }


                //Dealing with merchant
                else if (processInput(command, "merchant") && nearStalls && !nearMerchant && merchant.isAlive()) {
                    merchant.say(outScreen, "Greetings, citizen! I have available today the finest bread and oil in the city!\n");
                    nearMerchant = true;
                } else if ((processInput(command, "bread") || processInput(command, "oil") || processInput(command, "buy")) && nearMerchant && merchant.isAlive()) {
                    merchant.say(outScreen, "The bread is 1 copper. 2 for the oil. I'll make you a deal though. Buy both, and I'll sell 'em to ya for only 3 coppers.");
                    merchant.say(outScreen, "... Well... I guess I can trade em to you too, but it's gotta be good. I did want to get my daughter a flute for her birthday.\n");
                } else if ((processInput(command, "trade")||processInput(command,"give")||processInput(command,"exchange")||processInput(command,"flute"))&& nearMerchant && merchant.isAlive()) {
                    if (player.has("Pan's Flute")) {
                        player.give("Pan's Flute", merchant);
                        merchant.say(outScreen, "Oooh! This will do very nicely.");
                        merchant.give(bread, player);
                        merchant.give(oil, player);
                    } else {
                        merchant.say(outScreen, "No, that's not a fair deal.\n");
                    }
                } else if (processInput(command, "rob") && nearMerchant && !robAttemptMerch && merchant.isAlive()) {
                    if (player.rob(merchant)) {
                        write("");
                        merchant.say(outScreen, "That felt funny.\n");
                    } else {
                        merchant.say(outScreen, "You do that again, and I'll have the guards cut off your hands.\n");
                    }
                    robAttemptMerch=true;
                } else if (processInput(command, "rob") && nearMerchant && robAttemptMerch && merchant.isAlive()) {
                    write("That doesn't seem like a very good idea.\n");
                } else if (processInput(command, "kill") && nearMerchant && merchant.isAlive()) {
                    player.kill(merchant);
                    nearMerchant = false;
                    write("Damn it. I bet he had a family that relied on him for both financial and emotional support. Nice going.\n");
                } else if (processInput(command, "persuade") && nearMerchant && merchant.isAlive()&& !player.has("Mysterious Powder")) {
                    merchant.say(outScreen, "How do you know about that? Look, I don't want any trouble. Just take it.");
                    merchant.give(powder, player);
                    write("");
                } else if (processInput(command, "persuade") && nearMerchant && merchant.isAlive()) {
                    merchant.say(outScreen, "I ain't got any left.\n");
                } else if ((processInput(command, "back") || processInput(command, "center")) && nearStalls) {
                    nearStalls = false;
                    nearMerchant = false;
                    inCenter = true;
                    write("You go back to the center of the agora.\n");
                }

                //Temple
                else if (processInput(command, "temple") && inCenter && !gavePowder && priest.isAlive()) {
                    inCenter = false;
                    inTemple = true;
                    write("You go up to the temple. The heavy doors swing open and you enter. \nA priest is praying to a great big scultpure of Athena. He comes over to you");
                    priest.say(outScreen, "At long last, the gods show me favor. You have come to redeem me. Please give me that which will bring back my visions\n");

                } else if (processInput(command, "temple") && inCenter) {
                    write("You try to open the doors, but they will not budge.\n");
                } else if (processInput(command, "temple") && !inCenter) {
                    write("You need to go back to the center of town before you can go to the temple.\n");
                } else if (processInput(command, "persuade") && inTemple) {
                    priest.say(outScreen, "It is not flattery that I need, but for the mysteries of the gods to reveal themselves to me once more.\n");
                } else if (processInput(command, "kill") && inTemple) {
                    priest.say(outScreen, "Fine! Take me out of this cruel world. There is no place for me here anyway.");
                    priest.die();
                    inTemple = false;
                    write("You nonchalantly leave the temple. Just killed a man. No big deal.\n");
                } else if (processInput(command, "rob") && inTemple) {
                    if (!robAttemptPriest) {
                        robAttemptPriest = true;
                        if (player.rob(priest)) {
                            write("Its strength is " + heroSword.getStrengthBonus());
                            priest.say(outScreen, "Take it, cruel gods! I don't even care.\n");
                        } else {
                            priest.say(outScreen, "Hey, man, don't be stealing my stuff.\n");
                        }
                    } else {
                        priest.say(outScreen, "Get away from here. Just leave if you do not wish to aid me!\n");
                    }
                } else if (processInput(command, "give") && inTemple && !hasSword) {
                    if (processInput(command, "powder") && player.has("Mysterious Powder")) {
                        player.give("Mysterious Powder", priest);
                        priest.say(outScreen, "What is this? ... Yes! This is what I needed! The gods are speaking to me once again! \n Here, take this. May it serve you well in your travels.");
                        priest.give(heroSword, player);
                        hasSword = true;

                        gavePowder = true;
                        inTemple = false;
                        inCenter = true;
                        write("You exit the temple and go back to the center of the agora.\n");
                    } else {
                        priest.say(outScreen, "NO, NO, NO! Why do you mock me so?!\n");
                    }
                } else if (processInput(command, "give") && inTemple && hasSword) {
                    if (processInput(command, "powder") && player.has("Mysterious Powder")) {
                        player.give("Mysterious Powder", priest);
                        priest.say(outScreen, "May the gods show you endless favor, this exactly what I needed!");
                        gavePowder = true;
                        inTemple = false;
                        inCenter = true;
                        write("You exit the temple and go back to the center of the agora.\n");
                    } else {
                        priest.say(outScreen, "NO, NO, NO! Why do you mock me so?!\n");
                    }
                } else if ((processInput(command, "leave") || processInput(command, "exit") || processInput(command, "back")) && inTemple) {
                    inTemple = false;
                    inCenter = true;
                    write("You go back to the center of the agora.\n");
                }

                //Socrates
                else if ((processInput(command, "crowd") || processInput(command, "man")) && !talked && inCenter && soc.isAlive()) {
                    write("You go up to the crowd. The old man who seems to be the focus of attention goes up to you.");
                    inCenter = false;
                    inCircle = true;
                    soc.say(outScreen, "Hello there, " + player.getName() + ". I am the great philosopher Socrates. And who are you?");
                    talking1 = true;
                    talking = true;
                } else if ((processInput(command, "crowd") || processInput(command, "man")) && talked && inCenter && soc.isAlive()) {
                    soc.say(outScreen, "Hello fellow philosopher.\n");
                    inCircle=true;
                } else if ((processInput(command, "persuade")) && talked && inCircle && soc.isAlive()) {
                    soc.say(outScreen, "True knowledge exists in knowing that you know nothing. I'm profound.\n");
                } else if ((processInput(command, "persuade")) && !talked && inCircle && soc.isAlive()) {
                    soc.say(outScreen,"Teach me something... If you dare!\n");

                } else if ((processInput(command, "rob")) && inCircle && soc.isAlive()) {
                    soc.say(outScreen, "Such actions are not wise.\n");
                } else if (processInput(command, "kill") && inCircle) {
                    player.kill(soc);
                    write("Oh no! You've killed Socrates, one of the best minds this world has ever known. Here comes a guard.");
                    guard.say(outScreen, "Thank you for your service, adventurer! We cannot thank you enough, that guy was a dick. \nHere, take this key as a form of payment from the city.");
                    guard.give(agoraKey, player);
                    player.completeAgoraQuest();
                    inAgora = false;
                    started = false;
                    talking1=false;
                    talking=false;
                    talking2=false;
                    write("Well look at that.");

                    if (player.getForestQuest() && player.getPolQuest()) {
                        write("You have completed the last quest. Congratulations. Now go get that sculpture!!");
                    } else if (player.getForestQuest()) {
                        write("You have completed the quest in the agora. Congratulations. All that's left to do is visit Polykleitos!");
                        limbo.changeInLimbo();
                    } else if (player.getPolQuest()) {
                        write("You have completed the quest in the agora. Congratulations. All that's left to do is to go the forest!");
                        limbo.changeInLimbo();
                    } else {
                        write("You have completed the quest in the agora. Congratulations. Go now to either the forest or to Polykleitos");
                        limbo.changeInLimbo();
                    }
                } else if ((processInput(command, "dust in the wind") || processInput(command, "pointless") || processInput(command, "everything") || processInput(command, "nothing") || processInput(command, "know")) && talking) {
                    talking = false;
                    talking1 = false;
                    talking2 = false;
                    talked = true;
                    soc.say(outScreen, "Why yes, I see that. This is a whole new level of depth. You are wise beyond your years.\n");
                    soc.give(wisdom, player);

                } else if ((processInput(command, "back")||processInput(command,"center")) && inCircle) {
                    inCircle = false;
                    talking = false;
                    talking1=false;
                    inCenter=true;
                    write("You go back to the center of the agora.\n");
                } else if (inCircle && talking1) {
                    soc.say(outScreen, "But what does that even mean? What does it mean to be anything?");
                    talking1 = false;
                    talking2 = true;
                } else if (inCircle && talking2) {
                    soc.say(outScreen, "There is no shepherd without sheep. The horse trainer knows what is best for the horses. But who can know?");
                    talking1 = true;
                    talking2 = false;
                } else if (processInput(command, "inventory") || processInput(command, "items")) {
                    write(player.getItems().toString());
                } else if (processInput(command, "stats") || processInput(command, "health")||processInput(command, "strength")) {
                    write("Your health is " +player.getHealth()+"/"+player.getHealthMax());
                    write("Your strength is "+player.getStrength());
                } else if (processInput(command, player)) {
                    write("");
                } else if (processInput(command, "leave") && processInput(command, "agora")) {
                    inAgora = false;
                    started = false;
                    write("Go to either the forest or to Polykleitos. Or you can go back to the agora.");
                    limbo.changeInLimbo();

                } else if (processInput(command, "yay")) {
                    write("I'm glad you're happy.\n");
                } else if (processInput(command, "thank you")) {
                    write("You're welcome.\n");
                } else if (processInput(command, "help") || processInput(command, "instructions")) {
                    write("Remember, when you're talking to a character in the game, you can rob, persuade, or kill them. \nLooking around may sometimes help.\n" +
                    "To equip a weapon, just type \"equip\" followed by the name of the weapon.\n" +
                            "You can leave the location you are in by typing \"leave\" followed by your current location.\n" +
                            "Type inventory to see all of your items. Type stats to see your health and strength. \n\n");
                } else if (processInput(command, "kill") && processInput(command,"self")) {
                    write ("1 (800) 273-8255\n");
                }else {
                    write("Not a valid command\n");
                }
            }
        }
    }
}

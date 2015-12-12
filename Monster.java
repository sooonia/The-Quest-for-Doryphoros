package sample;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Sonia on 10/1/2015.
 */
public class Monster extends Character
    {

        private int strength;
        private int health;

        public Monster(String name, int strength, int health)
        {
            this.name = name;
            robStat = 1000000;
            this.strength = strength;
            this.health = health;
            charItem= new ArrayList<Item>();
        }

        public int attack()
        {
            Random rand = new Random();
            return rand.nextInt(this.strength);
        }

        public int getStrength()
        {
            return strength;
        }

        public int getHealth()
        {
            return health;
        }

        public void setHealth(int healthDif)
        {
            health = health + healthDif;
        }

        public void setStrength(int strengthDif)
        {
            strength = strength + strengthDif;
        }

    }


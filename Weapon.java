package sample;

/**
 * Created by Sonia on 10/6/2015.
 */
public class Weapon extends Item
{
    /**
     * Creates a new weapon
     *
     * @param itemName the name of the item
     * @param strengthBonus how much strength increases when weapon is equipped
     * @throws ArithmeticException if strengthBonus <= 0
     */

    public Weapon(String itemName, int strengthBonus)
    {
        if (strengthBonus <=0)
        {
            throw new ArithmeticException();
        }
        this.itemName = itemName;
        this.strengthBonus = strengthBonus;
        this.healthBonus =0;
    }

}

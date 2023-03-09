package reto_Ivan_Kevin_Jia.models.Characters;

import reto_Ivan_Kevin_Jia.interfaces.IHealerChar;

public class HealerCharacter extends Character implements IHealerChar {

    // El poder, tendrá cantidad de usos para la habilidad
    private int mana = getPower();

    public HealerCharacter(String name, int basicDamage, int maxLife, int power, int extra) {
        super(name, basicDamage, maxLife, power, extra);
    }

    @Override
    public int heal() {
        int countHeal = 0;
        if (mana < 40) {
            System.out.println("No tienes más maná!");
            return 0;
        }
        if (getActualLife() < getMaxLife()) {
            for (int i = 1; i <= 7; i++) {
                countHeal++;
                if (getActualLife() == getMaxLife()) {
                    break;
                }
                setActualLife(getActualLife() + 1);
            }
            System.out.println(this.getName() + " se ha curado " + countHeal + " de vida");
            mana = mana - 40;
            return countHeal;
        } else {
            System.out.println("Tienes la vida al máximo!");
            return -1;
        }
    }

    @Override
    public String toString() {
        return getName() +
                "(basicDamage=" + getBasicDamage() +
                ", life=" + getActualLife() +
                ", mana=" + mana +
                ")" + "\n" +
                "HABILIDAD: Se regenera 7 de vida (-40 maná)";
    }
}

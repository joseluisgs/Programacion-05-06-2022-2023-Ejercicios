package reto_Ivan_Kevin_Jia.models.Characters;

import reto_Ivan_Kevin_Jia.interfaces.ICounterCharacter;

public class CounterCharacter extends Character implements ICounterCharacter {

    // El extra será el escudo que acumula para reducir el daño
    public int accumulateShield = getExtra();
    // El poder será el maná
    private int mana = getPower();

    public CounterCharacter(String name, int basicDamage, int maxLife, int power, int extra) {
        super(name, basicDamage, maxLife, power, extra);
    }

    @Override
    public int fullCounter(Character enemy) {
        if (mana >= 50) {
            if (accumulateShield < 4) {
                accumulateShield = 4;
            }
            enemy.setActualLife(enemy.getActualLife() - this.getBasicDamage());
            this.mana = this.mana - 50;
            System.out.println(this.getName() + " ha activado Full counter! Tiene un escudo de " + accumulateShield + " y hace " + getBasicDamage() + " de daño");
            return 1;
        }
        System.out.println("No tienes maná!");
        return 0;
    }

    @Override
    public String toString() {
        return getName() +
                "(basicDamage=" + getBasicDamage() +
                ", life=" + getActualLife() +
                ", mana=" + mana +
                ", escudo=" + accumulateShield + ")" + "\n" +
                "HABILIDAD: Reduce 4 de daño y hace un daño básico (-50 maná)";
    }
}

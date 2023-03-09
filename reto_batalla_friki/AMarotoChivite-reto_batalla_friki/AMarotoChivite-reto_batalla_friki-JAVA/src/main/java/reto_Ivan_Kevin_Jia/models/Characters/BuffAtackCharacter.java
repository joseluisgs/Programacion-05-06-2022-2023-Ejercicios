package reto_Ivan_Kevin_Jia.models.Characters;

import reto_Ivan_Kevin_Jia.interfaces.IBuffAtackCharacter;

public class BuffAtackCharacter extends Character implements IBuffAtackCharacter {

    // El poder, tendrá cantidad de usos para la habilidad
    private int mana = getPower();

    public BuffAtackCharacter(String name, int basicDamage, int maxLife, int power, int extra) {
        super(name, basicDamage, maxLife, power, extra);
    }

    @Override
    public int gear3(Character enemy) {
        if (mana > 0) {
            int newLife = enemy.getActualLife() - getBasicDamage() * 2;
            enemy.setActualLife(newLife);
            mana = mana - 50;
            System.out.println(this.getName() + " ha hecho " + getBasicDamage() * 2 + " de daño");
            return getBasicDamage() * 2;
        }
        return 0;
    }

    @Override
    public String toString() {
        return getName() +
                "(basicDamage=" + getBasicDamage() +
                ", life=" + getActualLife() +
                ", mana=" + mana +
                ")" + "\n" +
                "HABILIDAD: Hace el doble de daño (-50 maná)";
    }

}

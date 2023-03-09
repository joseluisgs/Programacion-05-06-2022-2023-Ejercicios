package reto_Ivan_Kevin_Jia.models.Characters;

import reto_Ivan_Kevin_Jia.interfaces.IInstaKillChar;

public class InstaKillCharacter extends Character implements IInstaKillChar {

    // El extra será el porcentaje para insta-matar
    private final double porcentInstaKill = getExtra();
    // El poder, tendra cantidad de usos para la habilidad
    private int mana = getPower();

    public InstaKillCharacter(String name, int basicDamage, int maxLife, int mana, int porcentInstaKill) {
        super(name, basicDamage, maxLife, mana, porcentInstaKill);
    }

    @Override
    public boolean instaKill(Character enemy) {
        int lifeMaxEnemy = enemy.getMaxLife();
        int actualLifeEnemy = enemy.getActualLife();

        if (mana > 0 && actualLifeEnemy < (lifeMaxEnemy * (porcentInstaKill / 100.0))) {
            enemy.setActualLife(0);
            mana = 0;
            System.out.println(this.getName() + " ha hecho insta-kill al enemigo");
            return true;
        } else {
            System.out.println("No tiene menos del " + porcentInstaKill + "% de vida");
            // Para que pierda el turno, si no queremos que pierda el turno entonces (false)
            return true;
        }
    }

    @Override
    public String toString() {
        return getName() +
                "(basicDamage=" + getBasicDamage() +
                ", life=" + getActualLife() +
                ", mana =" + mana +
                ")" + "\n" +
                "HABILIDAD: Elimina al enemigo si tiene " + porcentInstaKill + "% de vida o menos (-100 maná)";
    }

}

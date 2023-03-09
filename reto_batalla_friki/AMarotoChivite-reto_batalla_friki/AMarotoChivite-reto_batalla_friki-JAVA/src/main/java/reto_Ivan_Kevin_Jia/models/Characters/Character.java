package reto_Ivan_Kevin_Jia.models.Characters;

import reto_Ivan_Kevin_Jia.interfaces.ICharacter;

public abstract class Character implements ICharacter {
    private final String name;

    private final int basicDamage;
    private final int maxLife;
    private final int power;
    private final int extra;
    private int actualLife;
    private boolean hasMoved = false;

    public Character(String name, int basicDamage, int maxLife, int power, int extra) {
        this.name = name;
        this.basicDamage = basicDamage;
        this.maxLife = maxLife;
        this.power = power;
        this.extra = extra;

        // Inicia con la maxima vida
        actualLife = maxLife;
    }

    public boolean getHasMoved() {
        return hasMoved;
    }

    public void setHasMoved(boolean hasMoved) {
        this.hasMoved = hasMoved;
    }

    public int getBasicDamage() {
        return basicDamage;
    }

    public int getMaxLife() {
        return maxLife;
    }

    public int getActualLife() {
        return actualLife;
    }

    public void setActualLife(int newLife) {
        this.actualLife = newLife;
    }

    public int getPower() {
        return power;
    }

    public String getName() {
        return name;
    }

    public int getExtra() {
        return extra;
    }

    public boolean isDead() {
        return this.actualLife <= 0;
    }

    @Override
    public int basicAttack(Character enemy) {
        int newLife = enemy.actualLife - basicDamage;
        enemy.actualLife = newLife;
        System.out.println(this.name + " ha hecho " + basicDamage + " de daño");
        return basicDamage;
    }

}
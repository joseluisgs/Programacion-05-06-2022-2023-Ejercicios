package reto_Ivan_Kevin_Jia.models;

import reto_Ivan_Kevin_Jia.models.Characters.Character;

public class Team {

    private final Character char1;
    private final Character char2;
    private final Character[] allCharacter;
    private String owner;

    public Team(String owner, Character char1, Character char2) {
        this.owner = owner;
        this.char1 = char1;
        this.char2 = char2;
        allCharacter = new Character[]{char1, char2};
    }

    public Character[] getAllChar() {
        return allCharacter;
    }

    public Character getChar1() {
        return char1;
    }

    public Character getChar2() {
        return char2;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }


}

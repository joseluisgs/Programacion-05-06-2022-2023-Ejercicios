package reto_Ivan_Kevin_Jia.models;

import reto_Ivan_Kevin_Jia.models.Characters.Character;
import reto_Ivan_Kevin_Jia.models.Characters.*;

public class Map {

    private final Character[][] matrix;
    private final Team[] teams;

    public Map(int dimension, Team[] teams) {
        this.teams = teams;
        matrix = new Character[dimension][dimension];
        setFirstCharacters();
    }

    public Character[][] getMatrix() {
        return matrix;
    }

    public void setFirstCharacters() {
        matrix[0][0] = teams[0].getChar1();
        matrix[matrix.length - 1][matrix[0].length - 1] = teams[1].getChar1();
    }

    public void print() {
        for (Character[] characters : matrix) {
            for (int columnas = 0; columnas < characters.length; columnas++) {
                if (columnas == characters.length - 1) {
                    if (characters[columnas] instanceof CounterCharacter) {
                        System.out.println("M");
                    } else if (characters[columnas] instanceof HealerCharacter) {
                        System.out.println("R");
                    } else if (characters[columnas] instanceof InstaKillCharacter) {
                        System.out.println("F");
                    } else if (characters[columnas] instanceof BuffAtackCharacter) {
                        System.out.println("J");
                    } else {
                        System.out.println("-");
                    }
                } else {
                    if (characters[columnas] instanceof CounterCharacter) {
                        System.out.print("M" + " ");
                    } else if (characters[columnas] instanceof HealerCharacter) {
                        System.out.print("R" + " ");
                    } else if (characters[columnas] instanceof InstaKillCharacter) {
                        System.out.print("F" + " ");
                    } else if (characters[columnas] instanceof BuffAtackCharacter) {
                        System.out.print("J" + " ");
                    } else {
                        System.out.print("-" + " ");
                    }
                }
            }
        }

        System.out.println("----------------------------------");
        System.out.println("""
                LEYENDA:\s
                M -> (Mordekai)\s
                R -> (Rigby)\s
                F -> (Finn)\s
                J -> (Jake)\s
                """
        );
    }
}

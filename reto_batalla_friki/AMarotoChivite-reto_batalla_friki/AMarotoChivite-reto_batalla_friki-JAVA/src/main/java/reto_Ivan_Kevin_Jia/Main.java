package reto_Ivan_Kevin_Jia;

import reto_Ivan_Kevin_Jia.models.Characters.BuffAtackCharacter;
import reto_Ivan_Kevin_Jia.models.Characters.CounterCharacter;
import reto_Ivan_Kevin_Jia.models.Characters.HealerCharacter;
import reto_Ivan_Kevin_Jia.models.Characters.InstaKillCharacter;
import reto_Ivan_Kevin_Jia.models.Map;
import reto_Ivan_Kevin_Jia.models.Team;

import java.util.Scanner;

import static reto_Ivan_Kevin_Jia.utils.simulationBattle.simulationBattle;

public class Main {
    public static void main(String[] args) {
        // Instancio almacén de personajes disponibles
        Team team1 = new Team(
                null, new CounterCharacter("Mordekai", 3, 30, 100, 0),
                new HealerCharacter("Rigby", 3, 30, 100, 0));
        Team team2 = new Team(
                null, new InstaKillCharacter("Finn", 5, 21, 100, 40),
                new BuffAtackCharacter("Jake", 4, 25, 100, 0));

        Team[] teams = {team1, team2};


        // Selección de Team: 1 = team1, 2 = team2
        selectTeam(teams);

        // Instancio mapa con personajes seleccionados
        int dimension = 5;
        Map map = new Map(dimension, teams);

        // Inicio batalla
        simulationBattle(map, teams);
    }

    private static void selectTeam(Team[] teams) {
        while (true) {
            System.out.println("===================================");
            System.out.println("SELECCIÓN DE EQUIPOS");
            System.out.println("===================================");
            for (int i = 0; i < teams.length; i++) {
                System.out.println("EQUIPO: " + (i + 1));
                for (int j = 0; j < teams[i].getAllChar().length; j++) {
                    System.out.println(teams[i].getAllChar()[j]);
                }
                System.out.println();
            }
            System.out.println("===================================");
            System.out.println("JUGADOR: elija su equipo por el NUMERO");

            Scanner sc = new Scanner(System.in);
            String selection = sc.nextLine();

            if (filterNumber(selection)) {
                if (selection.equals("1")) {
                    teams[0].setOwner("Human");
                    teams[1].setOwner("IA");
                } else if (selection.equals("2")) {
                    teams[1].setOwner("Human");
                    teams[0].setOwner("IA");
                }
                break;
            }
        }
    }

    public static Boolean filterNumber(String selection) {
        String regex = "[1-2]";
        if (!selection.matches(regex)) {
            System.out.println("Debe ser 1 o 2!");
            System.out.println();
            return false;
        }
        return true;
    }
}

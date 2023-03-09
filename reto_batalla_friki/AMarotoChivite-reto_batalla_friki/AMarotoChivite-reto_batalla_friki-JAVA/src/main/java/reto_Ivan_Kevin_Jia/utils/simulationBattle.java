package reto_Ivan_Kevin_Jia.utils;

import reto_Ivan_Kevin_Jia.models.Characters.Character;
import reto_Ivan_Kevin_Jia.models.Characters.*;
import reto_Ivan_Kevin_Jia.models.Map;
import reto_Ivan_Kevin_Jia.models.Team;

import java.util.Scanner;

import static reto_Ivan_Kevin_Jia.Main.filterNumber;

public class simulationBattle {
    public static void simulationBattle(Map map, Team[] teams) {
        boolean exit = false;

        do {
            if (encounter(map)) {
                // Impresión para info actual
                System.out.println("============== COMIENZA BATALLA =================");
                map.print();

                int round = 1;
                while (true) {
                    // Acciones de todos los personajes...
                    System.out.println("============= RONDA " + (round++) + " =============");
                    battle(map, teams);

                    // Checkear si hay que cambiar de personaje
                    checkChangeCharacter(map, teams);

                    // Filtro para salir cuando todos de un equipo estén muertos
                    if (allDead(teams)) {
                        exit = true;
                        break;
                    }
                }
            } else {
                // Si no se encuentran, se mueven los personajes
                map.print();
                moveAllCharacter(map);
                // Reiniciamos la propiedad de haberse movido
                initMovement(map);
            }
        } while (!exit);
        finalInform(teams);
    }

    private static void finalInform(Team[] teams) {
        int countDeads = 0;
        System.out.println("==== INFORME EQUIPOS ====");
        for (int i = 0; i < teams.length; i++) {
            System.out.println("EQUIPO " + (i + 1));
            for (int j = 0; j < teams[i].getAllChar().length; j++) {
                System.out.println(teams[i].getAllChar()[j]);
                if (teams[i].getOwner().equals("IA") && teams[i].getAllChar()[j].getActualLife() <= 0) {
                    countDeads++;
                }
            }
        }
        if (countDeads == 2) {
            System.out.println("HAS GANADO, Jugador 1!");
        } else {
            System.out.println("HAS GANADO, Jugador 2!");
        }
    }

    private static void battle(Map map, Team[] teams) {

        Character myCharacter = null;
        Character enemyCharacter = null;

        for (int i = 0; i < map.getMatrix().length; i++) {
            for (int j = 0; j < map.getMatrix()[i].length; j++) {
                if (map.getMatrix()[i][j] != null) {
                    boolean isEnemy = false;
                    // Recorremos los equipos para saber cual es del enemigo
                    for (int k = 0; k < teams.length; k++) {
                        if (teams[k].getOwner().equals("IA")) {
                            for (int n = 0; n < teams[k].getAllChar().length; n++) {
                                if (teams[k].getAllChar()[n].getName().equals(map.getMatrix()[i][j].getName())) {
                                    enemyCharacter = map.getMatrix()[i][j];
                                    isEnemy = true;
                                }
                            }
                        }
                    }
                    if (!isEnemy) {
                        myCharacter = map.getMatrix()[i][j];
                    }
                }
            }
        }

        while (true) {
            System.out.println("==== ENEMIGO ====");
            System.out.println(enemyCharacter);
            System.out.println();
            System.out.println("==== TU TURNO: Jugador 1 ====");
            System.out.println("Selecciona la habilidad de tu personaje:");
            System.out.println(myCharacter);
            System.out.println("1 -> Ataque normal");
            System.out.println("2 -> Habilidad especial");

            Scanner sc = new Scanner(System.in);
            String selection = sc.nextLine();

            if (filterNumber(selection)) {
                if (selection.equals("1")) {
                    myCharacter.basicAttack(enemyCharacter);
                    exceptionMordekaiShield(enemyCharacter);
                    break;
                } else if (selection.equals("2")) {
                    if (abilities(enemyCharacter, myCharacter)) break;
                }
            }
        }

        // SI QUEREMOS PELEAR CONTRA LA IA, en vez de Jugador 2
        while (true) {
            System.out.println("==== TURNO DEL ENEMIGO ====");
            int selectionIA = (int) (Math.random() * 2 + 1);

            if (filterNumber(String.valueOf(selectionIA))) {
                if (selectionIA == 1) {
                    enemyCharacter.basicAttack(myCharacter);
                    exceptionMordekaiShield(myCharacter);
                    break;
                } else if (selectionIA == 2) {
                    if (abilities(myCharacter, enemyCharacter)) break;
                }
            }
        }

        // SI QUEREMOS PELEAR CONTRA JUGADOR 2, en vez de IA
 /*       while (true) {
            System.out.println("==== ENEMIGO ====");
            System.out.println(myCharacter);
            System.out.println();
            System.out.println("==== TU TURNO: Jugador 2 ====");
            System.out.println("Selecciona la habilidad de tu personaje:");
            System.out.println(enemyCharacter);
            System.out.println("1 -> Ataque normal");
            System.out.println("2 -> Habilidad especial");

            Scanner sc = new Scanner(System.in);
            String selection = sc.nextLine();

            if (filterNumber(selection)) {
                if (selection.equals("1")) {
                    enemyCharacter.basicAttack(myCharacter);
                    exceptionMordekaiShield(myCharacter);
                    break;
                } else if (selection.equals("2")) {
                    if (abilities(myCharacter, enemyCharacter)) break;
                }
            }
        }*/

    }

    private static void exceptionMordekaiShield(Character defensor) {
        if (defensor instanceof CounterCharacter) {
            if (((CounterCharacter) defensor).accumulateShield >= 1) {
                System.out.println("El enemigo tiene un escudo de " + defensor.getExtra() + " y mitiga ese daño...");
                // Único caso donde sobrepasaré la vida máxima debido al escudo
                defensor.setActualLife(defensor.getActualLife() + ((CounterCharacter) defensor).accumulateShield);
                ((CounterCharacter) defensor).accumulateShield = 0;
            }
        }
    }

    private static boolean abilities(Character defensor, Character atacker) {
        if (atacker instanceof BuffAtackCharacter) {
            if (((BuffAtackCharacter) atacker).gear3(defensor) != 0) {
                exceptionMordekaiShield(defensor);
                return true;
            }
        }
        if (atacker instanceof CounterCharacter) {
            if (((CounterCharacter) atacker).fullCounter(defensor) != 0) {
                return true;
            }
        }
        if (atacker instanceof HealerCharacter) {
            if ((((HealerCharacter) atacker).heal() > 0)) {
                return true;
            }
        }
        if (atacker instanceof InstaKillCharacter) {
            return ((InstaKillCharacter) atacker).instaKill(defensor);
        }
        return false;
    }

    private static void checkChangeCharacter(Map map, Team[] teams) {
        for (int i = 0; i < map.getMatrix().length; i++) {
            for (int j = 0; j < map.getMatrix()[i].length; j++) {
                if (map.getMatrix()[i][j] != null && map.getMatrix()[i][j].isDead()) {
                    // Buscamos que personaje es el que ha muerto para intercambiar por su compañero
                    for (int k = 0; k < teams.length; k++) {
                        if (map.getMatrix()[i][j].getName().equals(teams[k].getChar1().getName())) {
                            map.getMatrix()[i][j] = teams[k].getChar2();
                        }
                    }
                }
            }
        }
    }

    private static void initMovement(Map map) {
        for (int i = 0; i < map.getMatrix().length; i++) {
            for (int j = 0; j < map.getMatrix()[i].length; j++) {
                if (map.getMatrix()[i][j] != null) {
                    map.getMatrix()[i][j].setHasMoved(false);
                }
            }
        }
    }

    private static void moveAllCharacter(Map map) {
        for (int i = 0; i < map.getMatrix().length; i++) {
            for (int j = 0; j < map.getMatrix()[i].length; j++) {
                if (map.getMatrix()[i][j] != null && !map.getMatrix()[i][j].getHasMoved()) {
                    int[] actualPosition = {i, j};
                    Character characterToMove = map.getMatrix()[i][j];

                    // Establecemos que se ha movido para no moverlo más
                    map.getMatrix()[i][j].setHasMoved(true);
                    // Movemos
                    move(map, actualPosition, characterToMove);
                }
            }
        }
    }

    // Encuentro personajes
    private static boolean encounter(Map map) {
        for (int i = 0; i < map.getMatrix().length; i++) {
            for (int j = 0; j < map.getMatrix()[i].length; j++) {
                if (map.getMatrix()[i][j] != null) {
                    int[] positionCharacterToCheck = {i, j};
                    if (checkAroundCharacter(map, positionCharacterToCheck)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private static boolean checkAroundCharacter(Map map, int[] positionCharacterToCheck) {

        // Sin salirnos de las filas abajo
        // Cruz abajo
        if (positionCharacterToCheck[0] + 1 <= (map.getMatrix().length - 1)) {
            if (map.getMatrix()[positionCharacterToCheck[0] + 1][positionCharacterToCheck[1]] != null) {
                return true;
            }
            // Sin salirnos de las columnas derechas
            // En diagonal abajo derecha
            if (positionCharacterToCheck[1] + 1 <= (map.getMatrix()[0].length - 1)) {
                if (map.getMatrix()[positionCharacterToCheck[0] + 1][positionCharacterToCheck[1] + 1] != null) {
                    return true;
                }
            }
            // Sin salirnos de las columnas izquierdas
            // En diagonal abajo izquierda
            if (positionCharacterToCheck[1] - 1 >= 0) {
                if (map.getMatrix()[positionCharacterToCheck[0] + 1][positionCharacterToCheck[1] - 1] != null) {
                    return true;
                }
            }
        }

        // Sin salirnos de las filas arriba
        // Cruz arriba
        if (positionCharacterToCheck[0] - 1 >= 0) {
            if (map.getMatrix()[positionCharacterToCheck[0] - 1][positionCharacterToCheck[1]] != null) {
                return true;
            }
            // Sin salirnos de las columnas derechas
            // En diagonal arriba derecha
            if (positionCharacterToCheck[1] + 1 <= (map.getMatrix()[0].length - 1)) {
                if (map.getMatrix()[positionCharacterToCheck[0] - 1][positionCharacterToCheck[1] + 1] != null) {
                    return true;
                }
            }
            // Sin salirnos de las columnas izquierdas
            // En diagonal arriba izquierda
            if (positionCharacterToCheck[1] - 1 >= 0) {
                if (map.getMatrix()[positionCharacterToCheck[0] - 1][positionCharacterToCheck[1] - 1] != null) {
                    return true;
                }
            }
        }

        // Sin salirnos de las columnas derecha
        // Cruz derecha
        if (positionCharacterToCheck[1] + 1 <= (map.getMatrix()[0].length - 1)) {
            if (map.getMatrix()[positionCharacterToCheck[0]][positionCharacterToCheck[1] + 1] != null) {
                return true;
            }
        }

        // Sin salirnos de las columnas izquierda
        // Cruz izquierda
        if (positionCharacterToCheck[1] - 1 >= 0) {
            return map.getMatrix()[positionCharacterToCheck[0]][positionCharacterToCheck[1] - 1] != null;
        }
        return false;
    }

    // Movimiento por cada personaje
    private static void move(Map map, int[] characterPosition, Character character) {
        // Movimiento en 8 direcciones de forma aleatoria:
        // newPosicion[0] = posicion de la fila
        // newPosicion[1] = posicion de la columna
        int[] newPosition = new int[2];

        do {
            // 0 = neutral
            // 1 = down (rows), left (columns)
            // 2 = up (rows), right (columns)
            int directionRow = (int) (Math.random() * 3);
            int directionColumn = (int) (Math.random() * 3);

            //Movimiento en cruz
            if (directionRow == 1 && directionColumn == 0) {
                // Cruz abajo
                newPosition[0] = characterPosition[0] + 1;
                newPosition[1] = characterPosition[1];
            } else if (directionRow == 2 && directionColumn == 0) {
                // Cruz arriba
                newPosition[0] = characterPosition[0] - 1;
                newPosition[1] = characterPosition[1];
            } else if (directionRow == 0 && directionColumn == 1) {
                // Cruz izquierda
                newPosition[0] = characterPosition[0];
                newPosition[1] = characterPosition[1] - 1;
            } else if (directionRow == 0 && directionColumn == 2) {
                // Cruz derecha
                newPosition[0] = characterPosition[0];
                newPosition[1] = characterPosition[1] + 1;
            }

            //Movimiento diagonal
            else if (directionRow == 1 && directionColumn == 1) {
                // Abajo e izquierda
                newPosition[0] = characterPosition[0] + 1;
                newPosition[1] = characterPosition[1] - 1;
            } else if (directionRow == 2 && directionColumn == 1) {
                // Arriba e izquierda
                newPosition[0] = characterPosition[0] - 1;
                newPosition[1] = characterPosition[1] - 1;
            } else if (directionRow == 1 && directionColumn == 2) {
                // Abajo y derecha
                newPosition[0] = characterPosition[0] + 1;
                newPosition[1] = characterPosition[1] + 1;
            } else if (directionRow == 2 && directionColumn == 2) {
                // Arriba y derecha
                newPosition[0] = characterPosition[0] - 1;
                newPosition[1] = characterPosition[1] + 1;
            }

            // Si la nueva posición a la que se cambiará está vacía, es correcto
            // Si la nueva posición favorece a nuestro checkIndexOut, es correcto
        } while (!checkIndexOutBound(map, newPosition) || !checkBoxIsEmpty(map, newPosition));

        //Superamos todos los filtros y cambiamos posición
        map.getMatrix()[newPosition[0]][newPosition[1]] = character;
        // Y vaciamos la anterior posición
        map.getMatrix()[characterPosition[0]][characterPosition[1]] = null;
    }

    // Cuando movemos un personaje vemos, si la nueva posición está vacía
    private static boolean checkBoxIsEmpty(Map map, int[] newPosition) {
        return map.getMatrix()[newPosition[0]][newPosition[1]] == null;
    }

    // Filtrar no salirse de matriz
    private static boolean checkIndexOutBound(Map map, int[] newPosition) {
        return newPosition[0] >= 0
                && newPosition[0] < map.getMatrix().length
                && newPosition[1] >= 0
                && newPosition[1] < map.getMatrix()[0].length;
    }

    private static boolean allDead(Team[] teams) {
        for (Team team : teams) {
            int count = 0;
            for (int j = 0; j < team.getAllChar().length; j++) {
                if (team.getAllChar()[j].isDead()) {
                    count++;
                }
                if (count == 2) {
                    return true;
                }
            }
        }
        return false;
    }

}

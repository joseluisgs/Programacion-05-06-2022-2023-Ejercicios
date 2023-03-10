package models;

import factory.EnemigoFactory;

import java.util.ArrayList;
import java.util.Scanner;

import static general.General.introducirNumero;

public class Mapa {
    private final static Scanner sc = new Scanner(System.in);
    private final String BATALLA = "✊";
    private final String HOGUERA = "\uD83D\uDD25";
    private final String JEFE = "\uD83D\uDC80";
    private final String PROTA = "😀";
    private final String VACIO = "  ";
    private final int filas = 8;
    private final int columnas = 4;
    private final String[][] mapa = new String[filas][columnas];

    final Protagonista prota = new Protagonista();

    public void simulacion(){
        iniciarMapa();
        prota.iniciarMazoYMano();
        final String[][] mapaBase = new String[filas][columnas];
        clonarMapa(mapaBase);
        System.out.println("El mapa es el siguiente:");
        mostrarMapa();
        System.out.println();
        System.out.println("Empezando por la fila de arriba y sindo las posibles opciones: 1, 2, 3 y 4.");
        System.out.println("Introduzca la primera posición en la que vaya a estar el protagonista:");
        colocarAlProtagonista(introducirNumero(1,4));
        do{
            int[] coordenadas = moverse();
            System.out.println("El mapa es el siguiente:");
            mostrarMapa();
            System.out.println();
            if(mapaBase[coordenadas[0]][coordenadas[1]].equals(BATALLA)){
                entablarBatalla();
                System.out.println();
            }else{
                if(mapaBase[coordenadas[0]][coordenadas[1]].equals(HOGUERA)){
                    hoguera();
                    System.out.println();
                }else{
                    entablarPeleaJefe();
                    System.out.println();
                }
            }

        }while(buscarPosicionProta()[1] < filas-1 && prota.getVida() > 0);
        if(prota.getVida() <= 0){
            System.out.println("Una pena, estuviste tan cerca, sin embargo, los enemigos consiguieron derrotarte, sin embargo aun no debes darte por vencido, RISE TARNISHED.");
        }else{
            System.out.println("Felicidades, al final con algo de esfuerzo y dedicación las cosas se consigúen.");
        }
    }

    private void entablarPeleaJefe() {
        final Enemigo.Jefe jefe = new Enemigo.Jefe();
        do {
            accionProtagonista(new ArrayList<Enemigo>(), jefe);
            System.out.println();
            recivirDañoVeneno();
            System.out.println();
            accionJefe(jefe);
            System.out.println();
            jefeReciveVeneno(jefe);
            System.out.println();
        }while(jefe.getVida()>0 && prota.getVida()>0);
    }

    private void accionJefe(Enemigo.Jefe jefe) {
        if(jefe.getVida() > 0){
            int chance = (int)(Math.random()*100+1);
            if(chance <= 20){
                jefe.setVida(jefe.getVida()+ jefe.curar());
                System.out.println("El jefe decidio curarse "+jefe.curar()+" puntos de vida, ahora tiene "+jefe.getVida()+" puntos de vida.");
            }else{
                if(chance <= 60){
                    jefe.setDaño(jefe.atacar()+ jefe.potenciar());
                    System.out.println("El jefe decidio aumentarse "+jefe.potenciar()+" puntos de daño, ahora tiene "+jefe.atacar()+" puntos de daño.");
                }else{
                    System.out.println("El jefe decidio atacar al protagonista, con un total de "+jefe.atacar()+" puntos de daño.");
                    prota.setVida(prota.getVida()-jefe.atacar());
                }
            }
        }
    }

    private void jefeReciveVeneno(Enemigo.Jefe jefe) {
        if(jefe.getVida() > 0 && jefe.getVeneno()>0){
            jefe.setVida(jefe.getVida()-jefe.getVeneno());
            System.out.println("El jefe ha recivido un total de "+jefe.getVeneno()+" puntos de daño de veneno, ahora tiene "+jefe.getVida()+" puntos de vida.");
        }else{
            System.out.println("El jefe no recivio daño de veneno.");
        }
    }

    private void clonarMapa(String[][] mapaBase) {
        for(int i=0;i<filas;i++){
            for(int j=0;j<columnas;j++){
                mapaBase[i][j] = mapa[i][j];
            }
        }
    }

    private void hoguera() {
        System.out.println("Jugador, has llegado a una hoguera, felicidades.");
        System.out.println("Seleccione la opción que desea:");
        if(eleccionHoguera() == 1){
            prota.setVida(prota.getVida()+35);
            System.out.println("Has decidio curarte 35 puntos, por lo que ahora tú vida es de: "+prota.getVida());
        }else{
            Carta carta = seleccionarCarta();
            if (carta instanceof Carta.CartaDaño || carta instanceof Carta.CartaDañoPlus) {
                if (carta instanceof Carta.CartaDaño) {
                    ((Carta.CartaDaño) carta).setDaño(((Carta.CartaDaño) carta).getDaño()+3);
                    System.out.println("Se mejoro el daño de la carta: "+(Carta.CartaDaño)carta+" en +3.");
                } else {
                    ((Carta.CartaDañoPlus) carta).setDañoPlus(((Carta.CartaDañoPlus) carta).getDañoPlus()+3);
                    System.out.println("Se mejoro el daño de la carta: "+(Carta.CartaDañoPlus)carta+" en +3.");
                }
            } else {
                if (carta instanceof Carta.CartaCuracion || carta instanceof Carta.CartaCuracionPlus) {
                    if (carta instanceof Carta.CartaCuracion) {
                        ((Carta.CartaCuracion) carta).setCuracion(((Carta.CartaCuracion) carta).getCuracion()+4);
                        System.out.println("Se mejoro la curación de la carta: "+(Carta.CartaCuracion)carta+" en +4.");
                    } else {
                        ((Carta.CartaCuracionPlus) carta).setCuracionPlus(((Carta.CartaCuracionPlus) carta).getCuracionPlus()+4);
                        System.out.println("Se mejoro la curación de la carta: "+(Carta.CartaCuracionPlus)carta+" en +4.");
                    }
                } else {
                    if (carta instanceof Carta.CartaVeneno) {
                        ((Carta.CartaVeneno) carta).setDañoVeneno(((Carta.CartaVeneno) carta).getDañoVeneno()+1);
                        System.out.println("Se el veneno de la carta: "+(Carta.CartaVeneno)carta+" en +1.");
                    }
                }
            }
        }
    }

    private int eleccionHoguera() {
        System.out.println("******************************");
        System.out.println("*            Menú            *");
        System.out.println("******************************");
        System.out.println("* 1. Curarte 35 de vida      *");
        System.out.println("* 2. Mejorar una carta       *");
        System.out.println("******************************");
        return introducirNumero(1,2);
    }

    public void colocarAlProtagonista(int posicion){
        mapa[0][posicion-1] = PROTA;
        entablarBatalla();
    }

    public int[] moverse(){
        System.out.println("El mapa es el siguiente:");
        mostrarMapa();
        System.out.println();
        int[] coordenadas = buscarPosicionProta();
        System.out.println("Jugador, tienes tres opciones de movimiento posible: SE, S o SO, elija:");
        String direccion = introducirDireccion(coordenadas);
        if(direccion.equals("s")){
            mapa[coordenadas[0]][coordenadas[1]] = VACIO;
            mapa[coordenadas[0]+1][coordenadas[1]] = PROTA;
            coordenadas[0] = coordenadas[0]+1;
        }else{
            if(direccion.equals("se")){
                mapa[coordenadas[0]][coordenadas[1]] = VACIO;
                mapa[coordenadas[0]+1][coordenadas[1]+1] = PROTA;
                coordenadas[0] = coordenadas[0]+1;
                coordenadas[1] = coordenadas[1]+1;
            }else{
                mapa[coordenadas[0]][coordenadas[1]] = VACIO;
                mapa[coordenadas[0]+1][coordenadas[1]-1] = PROTA;
                coordenadas[0] = coordenadas[0]+1;
                coordenadas[1] = coordenadas[1]-1;
            }
        }
        return coordenadas;
    }

    private String introducirDireccion(int[] coordenadas) {
        String direccion = "";
        do{
            try{
                direccion = sc.nextLine().trim().toLowerCase();
                validarDireccion(direccion, coordenadas);
            }catch (Exception e){
                System.out.println(e.getMessage());
                direccion = "";
            }
        }while(direccion.equals(""));
        return direccion;
    }

    private void validarDireccion(String direccion, int[] coordenadas) {
        if(!direccion.equals("se") && !direccion.equals("s") && !direccion.equals("so")){
            throw new IllegalArgumentException("Error al introducir direccion: La direccion debe ser SR, S o SO, vuelve a probar:");
        }
        if(direccion.equals("se") && coordenadas[1] == 3){
            throw new IllegalArgumentException("Error al introducir direccion: No te puedes mover hacia hay, vuelve a probar:");
        }
        if(direccion.equals("so") && coordenadas[1] == 0){
            throw new IllegalArgumentException("Error al introducir direccion: No te puedes mover hacia hay, vuelve a probar:");
        }
    }

    private int[] buscarPosicionProta() {
        int[] coordenadas = new int[2];
        for(int i=0;i<filas;i++){
            for(int j=0;j<columnas;j++){
                if(mapa[i][j].equals(PROTA)){
                    coordenadas[0] = i;
                    coordenadas[1] = j;
                    break;
                }
            }
        }
        return coordenadas;
    }

    private void entablarBatalla() {
        ArrayList<Enemigo> enemigos = new ArrayList<>();
        int numeroEnemigos = ((int)(Math.random()*3+1));
        for(int i =0; i<numeroEnemigos;i++){
            enemigos.add(EnemigoFactory.getInstance().enemigoRandom());
        }
        do {
            accionProtagonista(enemigos, null);
            System.out.println();
            recivirDañoVeneno();
            System.out.println();
            accionEnemigos(enemigos);
            System.out.println();
            enemigosRecivenVeneno(enemigos);
            System.out.println();
        }while(!enemigos.isEmpty() && prota.getVida()>0);
        prota.setVeneno(0);
    }

    private void eliminarEnemigosMuertos(ArrayList<Enemigo> enemigos) {
        int size = enemigos.size();
        for(int i=size-1; i>=0; i--){
            if(enemigos.get(i).getVida() <= 0){
                enemigos.remove(i);
            }
        }
    }

    private void enemigosRecivenVeneno(ArrayList<Enemigo> enemigos) {
        if(!enemigos.isEmpty()) {
            if (enemigos.get(0).getVeneno() > 0) {
                for (int i = 0; i < enemigos.size(); i++) {
                    Enemigo enemigo = enemigos.get(i);
                    enemigo.setVida(enemigo.getVida() - enemigo.getVeneno());
                    System.out.println("El enemigo: " + enemigo + " a recibido " + enemigo.getVeneno() + " puntos de daño por el veneno.");
                }
            } else {
                System.out.println("Los enemigos no tenián ningún veneno en su cuerpo, por lo que no recibieron daño de veneno.");
            }
            eliminarEnemigosMuertos(enemigos);
            System.out.println();
        }
    }

    private void recivirDañoVeneno() {
        if(prota.getVeneno() > 0){
            prota.setVida(prota.getVida()- prota.getVeneno());
            System.out.println("El prota ha recibido, un total de "+prota.getVeneno()+" puntos de veneno, su vida actual es: "+prota.getVida());
        }else{
            System.out.println("El prota no tenia veneno en su cuerpo, por lo que no recibio daño de veneno.");
        }
    }

    private void accionEnemigos(ArrayList<Enemigo> enemigos) {
        for(int i=0;i<enemigos.size();i++){
            Enemigo enemigo = enemigos.get(i);
            if(enemigo instanceof Enemigo.Curador){
                if((int) (Math.random()*100+1) <= 75) {
                    if (enemigos.size() > 1) {
                        if ((int) (Math.random() * 100 + 1 )<= 50) {
                            System.out.println("El enemigo: " + enemigo + ", se a curado a si mismo, " + ((Enemigo.Curador) enemigo).curar() + " puntos de vida.");
                            enemigo.setVida(enemigo.getVida() + ((Enemigo.Curador) enemigo).curar());
                        } else {
                            Enemigo enemigo1;
                            do {
                                enemigo1 = enemigos.get((int) Math.random() * enemigos.size());
                            } while (enemigo1 == enemigo);
                            System.out.println("El enemigo: " + enemigo + ", ha curado al enemigo: " + enemigo1 + ", " + ((Enemigo.Curador) enemigo).curar() + " puntos de vida.");
                            enemigo1.setVida(enemigo1.getVida() + ((Enemigo.Curador) enemigo).curar());
                        }
                    } else {
                        System.out.println("El enemigo: " + enemigo + ", se a curado a si mismo, " + ((Enemigo.Curador) enemigo).curar() + " puntos de vida.");
                        enemigo.setVida(enemigo.getVida() + ((Enemigo.Curador) enemigo).curar());
                    }
                }else{
                    int ataque = enemigo.atacar();
                    System.out.println("El enemigo: "+enemigo+", ataco al protagonista con "+ataque+" puntos de daño.");
                    prota.setVida(prota.getVida()-ataque);
                }
            }else{
                if(enemigo instanceof Enemigo.Potenciador){
                    if((int) (Math.random()*100+1) <= 75) {
                        if (enemigos.size() > 1) {
                            if ((int) (Math.random() * 100 + 1) <= 50) {
                                System.out.println("El enemigo: " + enemigo + ", se a potenciado a si mismo, " + ((Enemigo.Potenciador) enemigo).potenciar() + " puntos de daño.");
                                enemigo.setDaño(enemigo.atacar() + ((Enemigo.Potenciador) enemigo).potenciar());
                            } else {
                                Enemigo enemigo1;
                                do {
                                    enemigo1 = enemigos.get((int) (Math.random() * enemigos.size()));
                                } while (enemigo1 == enemigo);
                                System.out.println("El enemigo: " + enemigo + ", ha potenciado al enemigo: " + enemigo1 + ", " + ((Enemigo.Potenciador) enemigo).potenciar() + " puntos de daño.");
                                enemigo1.setDaño(enemigo1.atacar() + ((Enemigo.Potenciador) enemigo).potenciar());
                            }
                        } else {
                            System.out.println("El enemigo: " + enemigo + ", se a potenciado a si mismo, " + ((Enemigo.Potenciador) enemigo).potenciar() + " puntos de daño.");
                            enemigo.setDaño(enemigo.atacar() + ((Enemigo.Potenciador) enemigo).potenciar());
                        }
                    }else{
                        int ataque = enemigo.atacar();
                        System.out.println("El enemigo: "+enemigo+", ataco al protagonista con "+ataque+" puntos de daño.");
                        prota.setVida(prota.getVida()-ataque);
                    }
                }else{
                    int ataque = enemigo.atacar();
                    System.out.println("El enemigo: "+enemigo+", ataco al protagonista con "+ataque+" puntos de daño.");
                    prota.setVida(prota.getVida()-ataque);
                }
            }
        }
        System.out.println();
        System.out.println("Termina el turno de los enemigos.");
    }

    private void accionProtagonista(ArrayList<Enemigo> enemigos, Enemigo.Jefe jefe) {
        Carta carta;
        do {
            if(jefe == null) {
                System.out.println("Te enfrentas a los siguientes enemigos:");
                representarEnemigos(enemigos);
                System.out.println();
            }else{
                System.out.println("Te enfrentas al jefe:");
                System.out.println(jefe);
                System.out.println();
            }
            carta = seleccionarCarta();
            System.out.println();
            if(carta != null) {
                prota.setMana(prota.getMana() - carta.getCosteDeMana());
                if (carta instanceof Carta.CartaDaño || carta instanceof Carta.CartaDañoPlus) {
                    if (carta instanceof Carta.CartaDaño) {
                        int daño = ((Carta.CartaDaño) carta).getDaño();
                        if(jefe == null) {
                            System.out.println("El protagonista ataco a todos los enemigos con " + daño + " puntos de daño.");
                            enemigos.forEach(
                                    enemigo -> enemigo.setVida(enemigo.getVida() - daño
                                    ));
                        }else{
                            System.out.println("El protagonista ataco al jefe con " + daño + " puntos de daño.");
                            jefe.setVida(jefe.getVida()-daño);
                        }
                    } else {
                        int daño = ((Carta.CartaDañoPlus) carta).getDañoPlus();
                        if (jefe == null) {
                            System.out.println("El protagonista ataco a todos los enemigos con " + daño + " puntos de daño.");
                            enemigos.forEach(
                                    enemigo -> enemigo.setVida(enemigo.getVida() - daño)
                            );
                        } else {
                            System.out.println("El protagonista ataco al jefe con " + daño + " puntos de daño.");
                            jefe.setVida(jefe.getVida() - daño);
                        }
                    }
                } else {
                    if (carta instanceof Carta.CartaCuracion || carta instanceof Carta.CartaCuracionPlus) {
                        if (carta instanceof Carta.CartaCuracion) {
                            int curacion = ((Carta.CartaCuracion) carta).getCuracion();
                            prota.setVida(prota.getVida() + curacion);
                            System.out.println("El protagonista se curo con " + curacion + " puntos de sanación, ahora tiene " + prota.getVida() + ".");
                        } else {
                            int curacion = ((Carta.CartaCuracionPlus) carta).getCuracionPlus();
                            prota.setVida(prota.getVida() + curacion);
                            System.out.println("El protagonista se curo con " + curacion + " puntos de sanación, ahora tiene " + prota.getVida() + ".");
                        }
                    } else {
                        if (carta instanceof Carta.CartaVeneno) {
                            if (jefe == null) {
                                System.out.println("El protagonista ataco con " + ((Carta.CartaVeneno) carta).getDañoVeneno() + " punto de veneno a todos los enemigos.");
                                Carta finalCarta = carta;
                                enemigos.forEach(
                                        enemigo -> enemigo.setVeneno(enemigo.getVeneno() + ((Carta.CartaVeneno) finalCarta).getDañoVeneno())
                                );
                            } else {
                                System.out.println("El protagonista ataco con " + ((Carta.CartaVeneno) carta).getDañoVeneno() + " punto de veneno al jefe.");
                                jefe.setVeneno(jefe.getVeneno() + ((Carta.CartaVeneno) carta).getDañoVeneno());
                            }
                        }
                    }
                }
            }else{
                System.out.println("Decidiste no elegir ninguna carta más, por lo que:");
            }
            if(jefe == null) {
                eliminarEnemigosMuertos(enemigos);
                System.out.println();
            }
        }while(puedeSeleccionarCarta() && carta != null && !enemigos.isEmpty());
        prota.setMana(3);
        prota.iniciarMano();
        System.out.println("Termina el turno del protagonista.");
    }

    private void representarEnemigos(ArrayList<Enemigo> enemigos) {
        for(int i=0;i<enemigos.size();i++){
            if(enemigos.get(i) instanceof Enemigo.Curador){
                System.out.println((Enemigo.Curador)(enemigos.get(i)));
            }else{
                if(enemigos.get(i) instanceof Enemigo.Potenciador){
                    System.out.println((Enemigo.Potenciador)(enemigos.get(i)));
                }else{
                    System.out.println(enemigos.get(i));
                }
            }
        }
    }

    private boolean puedeSeleccionarCarta() {
        boolean res = false;
        ArrayList<Carta> lista = prota.getMano();
        for(int i=0;i<lista.size();i++){
            if (lista.get(i).getCosteDeMana() <= prota.getMana()) {
                res =  true;
            }
        }
        return res;
    }

    private Carta seleccionarCarta() {
        System.out.println("Jugador ("+prota+"), tu mano actual es la siguiente:");
        prota.getMano().forEach(System.out::println);
        System.out.println("Seleccione la carta que desea(las opciones son de 1 al número de cartas disponibles, selecciona el valor del número de cartas + 1, para no elegir carta):");
        final int pos = introducirNumero(1,prota.getMano().size()+1);
        if(pos != prota.getMano().size()+1) {
            return prota.sacarCartaDeMano(pos, prota);
        }else{
            return null;
        }
    }

    public void iniciarMapa(){
        for(int i=1;i<filas-1;i++){
            int columna = -1;
            if((int)(Math.random()*100+1) >= 50){
                columna = (int) (Math.random()*4);
            }
            for(int j=0; j<columnas; j++){
                if(j == columna){
                    mapa[i][j] = HOGUERA;
                }else{
                    mapa[i][j] = BATALLA;
                }
            }
        }
        for(int j=0; j<columnas; j++){
            mapa[0][j] = BATALLA;
            mapa[filas-1][j] = JEFE;
        }
    }

    public void mostrarMapa(){
        String mensaje = "";
        for(int i=0;i<filas;i++){
            for(int j=0; j<columnas; j++){
                mensaje += " "+mapa[i][j];
            }
            System.out.println(mensaje);
            mensaje = "";
        }
    }
}
package models;

import cola.colaCartas;

import java.util.ArrayList;

final public class Protagonista {
    private int veneno = 0;
    private int vida = 50;
    private int mana = 3;
    private ArrayList<Carta> mano = new ArrayList<Carta>();
    private static final colaCartas mazo = new colaCartas();

    public Protagonista(){}

    public int getVida() {
        return vida;
    }

    public void setVida(int vida) {
        this.vida = vida;
    }

    public int getMana() {
        return mana;
    }

    public void setMana(int mana) {
        this.mana = mana;
    }

    public ArrayList<Carta> getMano() {
        return mano;
    }

    public void setMano(ArrayList<Carta> mano) {
        this.mano = mano;
    }

    public colaCartas getMazo() {
        return mazo;
    }

    public int getVeneno() {
        return veneno;
    }

    public void setVeneno(int veneno) {
        this.veneno = veneno;
    }

    public Carta sacarCartaDeMano(int pos, Protagonista prota) {
        Carta carta = mano.remove(pos - 1);
        if (prota.getMana() >= carta.getCosteDeMana()){
            mazo.enqueue(carta);
        return carta;
        }else{
            System.out.println("El protagonista no tiene suficiente mana para cojer esa carta.");
        }
        mano.add(carta);
        return null;
    }

    public void iniciarMazoYMano(){
        mazo.enqueue(new Carta.CartaDaño());
        mazo.enqueue(new Carta.CartaDaño());
        mazo.enqueue(new Carta.CartaDaño());
        mazo.enqueue(new Carta.CartaDañoPlus());
        mazo.enqueue(new Carta.CartaCuracion());
        mazo.enqueue(new Carta.CartaCuracion());
        mazo.enqueue(new Carta.CartaCuracion());
        mazo.enqueue(new Carta.CartaCuracionPlus());
        mazo.enqueue(new Carta.CartaVeneno());
        mazo.enqueue(new Carta.CartaVeneno());
        mazo.shuffle();
        iniciarMano();
    }

    public void iniciarMano(){
        final int maximo = mano.size();
        for(int i = 0;i<4-maximo;i++){
            mano.add(mazo.dequeue());
        }
    }

    @Override
    public String toString() {
        return "Protagonista{" +
                "veneno=" + veneno +
                ", vida=" + vida +
                ", mana=" + mana +
                '}';
    }
}
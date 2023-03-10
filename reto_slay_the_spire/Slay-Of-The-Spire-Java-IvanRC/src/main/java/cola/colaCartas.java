package cola;

import models.Carta;

import java.util.ArrayList;
import java.util.Collections;

public class colaCartas implements Cola<Carta> {
    public colaCartas() {}

    private final ArrayList cola = new ArrayList();

    @Override
    public void enqueue(Carta item) {
        cola.add(item);
    }

    @Override
    public Carta dequeue() {
        return (Carta) cola.remove(0);
    }

    @Override
    public Carta peek() {
        return (Carta) cola.get(0);
    }

    @Override
    public boolean estaVacia() {
        return cola.isEmpty();
    }

    @Override
    public int tamaño() {
        return cola.size();
    }

    public void shuffle(){
        Collections.shuffle(cola);
    }
}

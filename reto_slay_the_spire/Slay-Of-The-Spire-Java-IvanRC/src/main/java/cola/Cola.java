package cola;

public interface Cola<T> {
    void enqueue(T item);
    T dequeue();
    T peek();
    boolean estaVacia();
    int tamaño();
}

// Clase base que representa cada eslabón (nodo) doblemente enlazado en memoria
public class Nodo {

    private Object dato;      // Información almacenada dentro del nodo (ej. una ReservaCancha)
    private Nodo izquierda;   // Referencia (puntero) al nodo anterior
    private Nodo derecha;     // Referencia (puntero) al nodo siguiente

    // Constructor: crea un nodo aislado con su dato y sin enlaces iniciales
    public Nodo(Object dato) {
        this.dato = dato;
        this.izquierda = null;
        this.derecha = null;
    }

    public Object getDato() { return dato; }
    public void setDato(Object dato) { this.dato = dato; }

    public Nodo getIzquierda() { return izquierda; }
    public void setIzquierda(Nodo izquierda) { this.izquierda = izquierda; }

    public Nodo getDerecha() { return derecha; }
    public void setDerecha(Nodo derecha) { this.derecha = derecha; }
}
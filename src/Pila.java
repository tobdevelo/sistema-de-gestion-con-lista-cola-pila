// Pila dinámica LIFO (Last In, First Out) implementada manualmente con nodos enlazados
// Guarda el historial de reservas procesadas y permite deshacer la última operación
public class Pila {

    private Nodo cima;       // Apunta al último elemento apilado (el tope de la pila)
    private int cantidad;    // Número de elementos almacenados en la pila

    public Pila() {
        this.cima = null;
        this.cantidad = 0;
    }

    // Coloca un elemento en la cima de la pila (O(1))
    public void apilar(Object dato) {
        Nodo nuevo = new Nodo(dato);
        if (!esVacia()) {
            // El nuevo nodo apunta hacia abajo (derecha) a la cima anterior
            nuevo.setDerecha(cima);
            cima.setIzquierda(nuevo);
        }
        cima = nuevo; // El nuevo nodo se convierte en la nueva cima
        cantidad++;
    }

    // Retira y retorna el elemento que está en la cima de la pila (O(1))
    public Object desapilar() {
        if (esVacia()) {
            return null;
        }
        Object datoRetirado = cima.getDato();
        cima = cima.getDerecha(); // Bajamos la cima al siguiente nodo
        if (cima != null) {
            cima.setIzquierda(null); // Desconectamos el nodo retirado
        }
        cantidad--;
        return datoRetirado;
    }

    // Consulta el elemento de la cima sin retirarlo
    public Object peek() {
        return esVacia() ? null : cima.getDato();
    }

    // Retorna la cantidad de elementos apilados
    public int tamanio() {
        return cantidad;
    }

    // Verifica si la pila está vacía
    public boolean esVacia() {
        return cima == null;
    }

    // Verifica si un objeto se encuentra dentro de la pila
    public boolean contiene(Object dato) {
        return buscar(dato) != -1;
    }

    // Vacía toda la pila
    public void limpiar() {
        cima = null;
        cantidad = 0;
    }

    // Muestra los elementos desde la cima (más reciente) hacia el fondo
    public void mostrar() {
        if (esVacia()) {
            System.out.println("  [El historial de procesados (Pila) está vacío]");
            return;
        }
        Nodo actual = cima;
        int pos = 1;
        while (actual != null) {
            String etiqueta = (pos == 1) ? " [CIMA]" : "";
            System.out.println("  " + pos + "." + etiqueta + " " + actual.getDato());
            actual = actual.getDerecha();
            pos++;
        }
    }

    // Busca un elemento desde la cima y retorna su posición (1 = cima), o -1 si no existe
    public int buscar(Object dato) {
        Nodo actual = cima;
        int posicion = 1;
        while (actual != null) {
            if (actual.getDato() != null && actual.getDato().equals(dato)) {
                return posicion;
            }
            actual = actual.getDerecha();
            posicion++;
        }
        return -1;
    }
}

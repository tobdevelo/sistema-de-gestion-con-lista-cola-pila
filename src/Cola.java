// Cola dinámica FIFO (First In, First Out) implementada manualmente con nodos enlazados
// Gestiona las reservas de cancha pendientes por atender en orden de llegada
public class Cola {

    private Nodo frente;     // Apunta al primer elemento en llegar (el próximo en salir)
    private Nodo fin;        // Apunta al último elemento en llegar (por donde se encola)
    private int cantidad;    // Cantidad actual de elementos en la cola

    public Cola() {
        this.frente = null;
        this.fin = null;
        this.cantidad = 0;
    }

    // Inserta un nuevo elemento al final de la cola (O(1))
    public void encolar(Object dato) {
        Nodo nuevo = new Nodo(dato);
        if (esVacia()) {
            // Si la cola estaba vacía, frente y fin apuntan al mismo nodo
            frente = nuevo;
            fin = nuevo;
        } else {
            // Enlazamos el último nodo actual hacia la derecha con el nuevo y movemos 'fin'
            fin.setDerecha(nuevo);
            nuevo.setIzquierda(fin);
            fin = nuevo;
        }
        cantidad++;
    }

    // Extrae y retorna el elemento ubicado al frente de la cola (O(1))
    public Object desencolar() {
        if (esVacia()) {
            return null;
        }
        Object datoAtendido = frente.getDato();
        if (frente == fin) {
            // Era el único elemento en la cola
            frente = null;
            fin = null;
        } else {
            // Avanzamos el puntero 'frente' al siguiente nodo y cortamos la referencia izquierda
            frente = frente.getDerecha();
            frente.setIzquierda(null);
        }
        cantidad--;
        return datoAtendido;
    }

    // Consulta el elemento del frente sin retirarlo de la cola
    public Object peek() {
        return esVacia() ? null : frente.getDato();
    }

    // Retorna el número de elementos esperando en la cola
    public int tamanio() {
        return cantidad;
    }

    // Verifica si la cola no tiene elementos
    public boolean esVacia() {
        return frente == null;
    }

    // Recorre desde el frente hasta el fin para verificar si existe un dato
    public boolean contiene(Object dato) {
        Nodo actual = frente;
        while (actual != null) {
            if (actual.getDato() != null && actual.getDato().equals(dato)) {
                return true;
            }
            actual = actual.getDerecha();
        }
        return false;
    }

    // Vacía la cola por completo
    public void limpiar() {
        frente = null;
        fin = null;
        cantidad = 0;
    }

    // Muestra todos los elementos pendientes desde el frente hacia el fin
    public void mostrar() {
        if (esVacia()) {
            System.out.println("  [No hay reservas pendientes en la cola]");
            return;
        }
        Nodo actual = frente;
        int turno = 1;
        while (actual != null) {
            System.out.println("  Turno " + turno + " -> " + actual.getDato());
            actual = actual.getDerecha();
            turno++;
        }
    }
}
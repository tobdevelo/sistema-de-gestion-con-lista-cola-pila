// Lista doblemente enlazada que almacena el registro histórico completo del sistema
public class Lista {

    private Nodo cabeza;     // Apunta al primer nodo de la lista
    private Nodo cola;       // Apunta al último nodo de la lista
    private int cantidad;    // Lleva el conteo exacto de elementos almacenados

    public Lista() {
        this.cabeza = null;
        this.cola = null;
        this.cantidad = 0;
    }

    // 1. Agrega un elemento al final de la lista (operación O(1) gracias al puntero 'cola')
    public void agregar(Object dato) {
        Nodo nuevo = new Nodo(dato);
        if (cabeza == null) {
            // Si la lista está vacía, el nuevo nodo es tanto cabeza como cola
            cabeza = nuevo;
            cola = nuevo;
        } else {
            // Conectamos el último actual con el nuevo hacia la derecha, y el nuevo hacia la izquierda
            cola.setDerecha(nuevo);
            nuevo.setIzquierda(cola);
            cola = nuevo; // Actualizamos 'cola' para que apunte al nuevo último nodo
        }
        cantidad++;
    }

    // 2. Agrega un elemento al inicio de la lista (actualiza 'cabeza')
    public void agregarAlInicio(Object dato) {
        Nodo nuevo = new Nodo(dato);
        if (cabeza == null) {
            cabeza = nuevo;
            cola = nuevo;
        } else {
            nuevo.setDerecha(cabeza);
            cabeza.setIzquierda(nuevo);
            cabeza = nuevo; // El nuevo nodo pasa a ser la cabeza
        }
        cantidad++;
    }

    // 3. Inserta un elemento en una posición específica (índice 0 basado)
    public void agregarEnPosicion(int indice, Object dato) {
        if (indice < 0 || indice > cantidad) {
            throw new IndexOutOfBoundsException("Índice fuera de rango: " + indice);
        }
        if (indice == 0) {
            agregarAlInicio(dato);
            return;
        }
        if (indice == cantidad) {
            agregar(dato);
            return;
        }

        // Avanzamos hasta el nodo que ocupa actualmente 'indice'
        Nodo actual = obtenerNodo(indice);
        Nodo anterior = actual.getIzquierda();
        Nodo nuevo = new Nodo(dato);

        // Reenlazamos los 4 punteros para insertar 'nuevo' entre 'anterior' y 'actual'
        nuevo.setIzquierda(anterior);
        nuevo.setDerecha(actual);
        anterior.setDerecha(nuevo);
        actual.setIzquierda(nuevo);
        cantidad++;
    }

    // 4. Elimina y retorna el primer elemento de la lista
    public Object eliminarPrimero() {
        if (cabeza == null) return null;

        Object datoEliminado = cabeza.getDato();
        if (cabeza == cola) {
            // Solo había un elemento en la lista
            cabeza = null;
            cola = null;
        } else {
            cabeza = cabeza.getDerecha();
            cabeza.setIzquierda(null); // Desconectamos el nodo eliminado
        }
        cantidad--;
        return datoEliminado;
    }

    // 5. Elimina y retorna el último elemento de la lista
    public Object eliminarUltimo() {
        if (cola == null) return null;

        Object datoEliminado = cola.getDato();
        if (cabeza == cola) {
            cabeza = null;
            cola = null;
        } else {
            cola = cola.getIzquierda();
            cola.setDerecha(null); // Desconectamos el antiguo último nodo
        }
        cantidad--;
        return datoEliminado;
    }

    // 6. Elimina y retorna el elemento ubicado en una posición específica
    public Object eliminarEnPosicion(int indice) {
        if (indice < 0 || indice >= cantidad) {
            throw new IndexOutOfBoundsException("Índice fuera de rango: " + indice);
        }
        if (indice == 0) return eliminarPrimero();
        if (indice == cantidad - 1) return eliminarUltimo();

        Nodo objetivo = obtenerNodo(indice);
        Nodo anterior = objetivo.getIzquierda();
        Nodo siguiente = objetivo.getDerecha();

        // Saltamos el nodo objetivo uniendo directamente 'anterior' con 'siguiente'
        anterior.setDerecha(siguiente);
        siguiente.setIzquierda(anterior);
        cantidad--;
        return objetivo.getDato();
    }

    // 7. Busca y retorna el dato almacenado en una posición numérica
    public Object buscarDato(int indice) {
        if (indice < 0 || indice >= cantidad) {
            throw new IndexOutOfBoundsException("Índice fuera de rango: " + indice);
        }
        return obtenerNodo(indice).getDato();
    }

    // 8. Busca un objeto en la lista usando su método equals() y retorna el objeto encontrado (o null)
    public Object buscarDato(Object dato) {
        Nodo actual = cabeza;
        while (actual != null) {
            if (actual.getDato() != null && actual.getDato().equals(dato)) {
                return actual.getDato();
            }
            actual = actual.getDerecha(); // Avanzamos al siguiente nodo
        }
        return null;
    }

    // 9. Verifica si un objeto existe dentro de la lista
    public boolean contiene(Object dato) {
        return buscarDato(dato) != null;
    }

    // 10. Retorna la cantidad total de elementos en la lista
    public int cuentaElementos() {
        return cantidad;
    }

    // 11. Vacía completamente la lista liberando las referencias
    public void limpiar() {
        cabeza = null;
        cola = null;
        cantidad = 0;
    }

    // 12. Recorre e imprime los elementos desde 'cabeza' hacia 'cola' (izquierda a derecha)
    public void mostrarAdelante() {
        if (cabeza == null) {
            System.out.println("  [La lista general está vacía]");
            return;
        }
        Nodo actual = cabeza;
        int i = 1;
        while (actual != null) {
            System.out.println("  " + i + ". " + actual.getDato());
            actual = actual.getDerecha();
            i++;
        }
    }

    // 13. Recorre e imprime los elementos desde 'cola' hacia 'cabeza' (derecha a izquierda)
    public void mostrarAtras() {
        if (cola == null) {
            System.out.println("  [La lista general está vacía]");
            return;
        }
        Nodo actual = cola;
        int i = cantidad;
        while (actual != null) {
            System.out.println("  " + i + ". " + actual.getDato());
            actual = actual.getIzquierda();
            i--;
        }
    }

    // Método auxiliar interno para localizar el nodo en un índice dado
    private Nodo obtenerNodo(int indice) {
        Nodo actual = cabeza;
        for (int i = 0; i < indice; i++) {
            actual = actual.getDerecha();
        }
        return actual;
    }
}
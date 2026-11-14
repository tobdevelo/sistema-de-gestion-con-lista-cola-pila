# sistema-de-gestion-con-lista-cola-pila
# Sistema de Gestión de Reservas - Centro Deportivo (Caso 24)

- **Actividad:** Unidad 2 — Actividad de Aprendizaje (Lista, Cola y Pila manuales)
- **Asignatura:** Estructuras de Datos
- **Programa:** Ingeniería de Software — Universidad de Cartagena
- **Estudiante:** Jorge Taján Acevedo
- **Tutor:** John Carlos Arrieta Arrieta
- **Versión de Java:** OpenJDK 17+

## Descripción del sistema
Aplicación de consola en Java que administra las reservas de escenarios deportivos del **Centro Deportivo (Caso 24)** utilizando tres estructuras de datos implementadas manualmente desde cero mediante nodos doblemente enlazados (`izquierda` y `derecha`):
- **`Lista` (Lista doblemente enlazada):** Mantiene el registro histórico general completo de todas las reservas ingresadas.
- **`Cola` (FIFO):** Administra las reservas pendientes de atención en estricto orden de llegada.
- **`Pila` (LIFO):** Almacena el historial de reservas procesadas y permite deshacer el último procesamiento devolviéndolo a la cola de pendientes.

## Clases desarrolladas (`src/`)
- `Nodo.java`: Eslabón doblemente enlazado (`izquierda`, `dato`, `derecha`).
- `Lista.java`: Lista doblemente enlazada con sus 13 métodos de inserción, eliminación, búsqueda y recorrido bidireccional.
- `Cola.java`: Cola dinámica FIFO (`encolar`, `desencolar`, `peek`, `contiene`, `mostrar`).
- `Pila.java`: Pila dinámica LIFO (`apilar`, `desapilar`, `peek`, `buscar`, `mostrar`).
- `ReservaCancha.java`: Entidad principal (`codigoReserva`, `nombreCliente`, `tipoCancha`, `horario`, `estado`) con `equals()` basado en `codigoReserva`.
- `SistemaCentroDeportivo.java`: Menú interactivo de 10 opciones, validaciones y cancelación mediante cola auxiliar.

## Instrucciones de ejecución
1. Clonar el repositorio público desde GitHub.
2. Abrir la carpeta del proyecto en **IntelliJ IDEA** (con `src` marcado como *Sources Root*).
3. Ejecutar el método `main` de `src/SistemaCentroDeportivo.java`.
import java.util.Scanner;

// Clase principal de ejecución y control del Caso 24: Centro Deportivo
// Conecta Lista (registro total), Cola (atención FIFO) y Pila (historial LIFO y deshacer)
public class SistemaCentroDeportivo {

    private final Lista listaGeneral;          // Registro completo de todas las reservas del sistema
    private final Cola colaPendientes;         // Cola FIFO de reservas pendientes por confirmar/atender
    private final Pila historialProcesados;    // Pila LIFO de reservas ya procesadas (permite deshacer)

    public SistemaCentroDeportivo() {
        this.listaGeneral = new Lista();
        this.colaPendientes = new Cola();
        this.historialProcesados = new Pila();
        cargarDatosIniciales();
    }

    // Carga 6 reservas de ejemplo para que el sistema inicie con datos reales listos para probar
    private void cargarDatosIniciales() {
        registrarInterno(new ReservaCancha("RES-101", "Carlos Pérez", "Fútbol 5", "17:00 - 18:00", "PENDIENTE"));
        registrarInterno(new ReservaCancha("RES-102", "Laura Gómez", "Tenis", "18:00 - 19:00", "PENDIENTE"));
        registrarInterno(new ReservaCancha("RES-103", "Andrés Ríos", "Baloncesto", "19:00 - 20:00", "PENDIENTE"));
        registrarInterno(new ReservaCancha("RES-104", "Sofía Herrera", "Fútbol 5", "20:00 - 21:00", "PENDIENTE"));
        registrarInterno(new ReservaCancha("RES-105", "Mateo Silva", "Vóley", "21:00 - 22:00", "PENDIENTE"));
    }

    // Regla obligatoria de registro: guarda en Lista general y encola en Cola de pendientes
    private void registrarInterno(ReservaCancha reserva) {
        listaGeneral.agregar(reserva);
        colaPendientes.encolar(reserva);
    }

    // Opción 1: Registrar una nueva reserva con validaciones
    public void registrarReserva(Scanner scanner) {
        System.out.println("\n--- REGISTRAR NUEVA RESERVA DE CANCHA ---");
        System.out.print("Código de reserva (ej. RES-106): ");
        String codigo = scanner.nextLine().trim().toUpperCase();

        if (codigo.isEmpty()) {
            System.out.println("Error: El código de reserva es obligatorio.");
            return;
        }
        // Validamos unicidad usando listaGeneral.contiene() (que invoca equals() de ReservaCancha)
        if (listaGeneral.contiene(new ReservaCancha(codigo))) {
            System.out.println("Error: Ya existe una reserva registrada con el código " + codigo + ".");
            return;
        }

        System.out.print("Nombre del cliente: ");
        String cliente = scanner.nextLine().trim();
        System.out.print("Tipo de cancha (Fútbol 5 / Tenis / Baloncesto / Vóley): ");
        String cancha = scanner.nextLine().trim();
        System.out.print("Horario (ej. 16:00 - 17:00): ");
        String horario = scanner.nextLine().trim();

        if (cliente.isEmpty() || cancha.isEmpty() || horario.isEmpty()) {
            System.out.println("Error: Ningún campo puede quedar vacío.");
            return;
        }

        ReservaCancha nueva = new ReservaCancha(codigo, cliente, cancha, horario, "PENDIENTE");
        registrarInterno(nueva);
        System.out.println("Reserva " + codigo + " agregada a Lista General y enviada a Cola de Pendientes.");
    }

    // Opción 2: Mostrar todas las reservas registradas en la Lista doblemente enlazada
    public void verTodosRegistrados() {
        System.out.println("\n--- REGISTRO GENERAL DE RESERVAS (LISTA DOBLEMENTE ENLAZADA) ---");
        listaGeneral.mostrarAdelante();
    }

    // Opción 3: Mostrar las reservas en espera dentro de la Cola FIFO
    public void verPendientes() {
        System.out.println("\n--- RESERVAS PENDIENTES POR ATENDER (COLA FIFO) ---");
        colaPendientes.mostrar();
    }

    // Opción 4: Procesar la siguiente reserva pendiente (desencolar -> cambiar estado -> apilar)
    public void procesarSiguiente() {
        System.out.println("\n--- PROCESAR SIGUIENTE RESERVA ---");
        if (colaPendientes.esVacia()) {
            System.out.println("No hay reservas pendientes en la cola para procesar.");
            return;
        }

        // Regla obligatoria de procesamiento
        ReservaCancha procesada = (ReservaCancha) colaPendientes.desencolar();
        procesada.setEstado("PROCESADA");
        historialProcesados.apilar(procesada);

        System.out.println("Reserva atendida y apilada en historial: " + procesada);
    }

    // Opción 5: Mostrar el historial de procesados desde la cima hacia el fondo (LIFO)
    public void verHistorialProcesados() {
        System.out.println("\n--- HISTORIAL DE RESERVAS PROCESADAS (PILA LIFO) ---");
        historialProcesados.mostrar();
    }

    // Opción 6: Buscar una reserva en la Lista general por su código
    public void buscarPorCodigo(Scanner scanner) {
        System.out.println("\n--- BUSCAR RESERVA POR CÓDIGO ---");
        System.out.print("Ingrese el código de reserva a buscar: ");
        String codigo = scanner.nextLine().trim().toUpperCase();

        if (codigo.isEmpty()) {
            System.out.println("Error: Debe ingresar un código válido.");
            return;
        }

        // Creamos objeto molde con el código para que buscarDato() compare mediante equals()
        ReservaCancha encontrada = (ReservaCancha) listaGeneral.buscarDato(new ReservaCancha(codigo));
        if (encontrada != null) {
            System.out.println("Reserva encontrada: " + encontrada);
        } else {
            System.out.println("No se encontró ninguna reserva con el código '" + codigo + "'.");
        }
    }

    // Opción 7: Cancelar una reserva pendiente usando una COLA AUXILIAR (regla obligatoria)
    public void cancelarPendiente(Scanner scanner) {
        System.out.println("\n--- CANCELAR RESERVA PENDIENTE (MEDIANTE COLA AUXILIAR) ---");
        if (colaPendientes.esVacia()) {
            System.out.println("La cola de pendientes está vacía; no hay reservas para cancelar.");
            return;
        }

        System.out.print("Ingrese el código de la reserva pendiente a cancelar: ");
        String codigo = scanner.nextLine().trim().toUpperCase();
        ReservaCancha objetivo = new ReservaCancha(codigo);

        if (!colaPendientes.contiene(objetivo)) {
            System.out.println("La reserva '" + codigo + "' no está en la cola de pendientes (puede estar ya procesada, cancelada o no existir).");
            return;
        }

        // No eliminamos directamente en medio de los nodos: trasvasamos a una cola auxiliar
        Cola colaAuxiliar = new Cola();
        ReservaCancha cancelada = null;

        while (!colaPendientes.esVacia()) {
            ReservaCancha actual = (ReservaCancha) colaPendientes.desencolar();
            if (actual.equals(objetivo)) {
                cancelada = actual; // Se omite de la cola auxiliar
            } else {
                colaAuxiliar.encolar(actual); // Se conserva el orden de los demás turnos
            }
        }

        // Devolvemos los elementos restantes a la cola principal en su orden original
        while (!colaAuxiliar.esVacia()) {
            colaPendientes.encolar(colaAuxiliar.desencolar());
        }

        if (cancelada != null) {
            cancelada.setEstado("CANCELADA"); // Actualiza su estado reflejado en la Lista general
            System.out.println("Reserva cancelada correctamente usando cola auxiliar: " + cancelada);
        }
    }

    // Opción 8: Deshacer el último procesamiento (desapilar -> restaurar estado -> reencolar)
    public void deshacerUltimoProcesamiento() {
        System.out.println("\n--- DESHACER ÚLTIMO PROCESAMIENTO (PILA -> COLA) ---");
        if (historialProcesados.esVacia()) {
            System.out.println("No hay operaciones en la pila de historial para deshacer.");
            return;
        }

        // Regla obligatoria para deshacer
        ReservaCancha ultimo = (ReservaCancha) historialProcesados.desapilar();
        ultimo.setEstado("PENDIENTE");
        colaPendientes.encolar(ultimo);

        System.out.println("Operación deshecha. La reserva volvió a estado PENDIENTE y fue reencolada: " + ultimo);
    }

    // Opción 9: Mostrar reporte cuantitativo de las tres estructuras
    public void verCantidadElementos() {
        System.out.println("\n--- REPORTE DE CANTIDAD DE ELEMENTOS POR ESTRUCTURA ---");
        System.out.println("1. Lista General (Total histórico registrado): " + listaGeneral.cuentaElementos());
        System.out.println("2. Cola de Pendientes (En espera FIFO):       " + colaPendientes.tamanio());
        System.out.println("3. Pila de Historial (Procesados LIFO):      " + historialProcesados.tamanio());
    }

    // Menú principal de 10 opciones exigido por la guía
    public void iniciarMenu() {
        Scanner scanner = new Scanner(System.in);
        int opcion = -1;

        while (opcion != 10) {
            System.out.println("\n=============================================================");
            System.out.println("     SISTEMA DE RESERVAS - CENTRO DEPORTIVO (CASO 24)");
            System.out.println("=============================================================");
            System.out.println("1. Registrar elemento (Nueva reserva)");
            System.out.println("2. Ver todos los elementos registrados (Lista)");
            System.out.println("3. Ver elementos pendientes (Cola)");
            System.out.println("4. Procesar siguiente elemento (Cola -> Pila)");
            System.out.println("5. Ver historial de elementos procesados (Pila)");
            System.out.println("6. Buscar elemento por código (Lista)");
            System.out.println("7. Cancelar elemento pendiente (Cola auxiliar)");
            System.out.println("8. Deshacer último procesamiento (Pila -> Cola)");
            System.out.println("9. Ver cantidad de elementos");
            System.out.println("10. Salir");
            System.out.print("Seleccione una opción: ");

            try {
                opcion = Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Error: Ingrese un número válido entre 1 y 10.");
                continue;
            }

            switch (opcion) {
                case 1 -> registrarReserva(scanner);
                case 2 -> verTodosRegistrados();
                case 3 -> verPendientes();
                case 4 -> procesarSiguiente();
                case 5 -> verHistorialProcesados();
                case 6 -> buscarPorCodigo(scanner);
                case 7 -> cancelarPendiente(scanner);
                case 8 -> deshacerUltimoProcesamiento();
                case 9 -> verCantidadElementos();
                case 10 -> System.out.println("Saliendo del Sistema de Centro Deportivo...");
                default -> System.out.println("Opción fuera de rango (1-10).");
            }
        }
        scanner.close();
    }

    public static void main(String[] args) {
        SistemaCentroDeportivo sistema = new SistemaCentroDeportivo();
        sistema.iniciarMenu();
    }
}
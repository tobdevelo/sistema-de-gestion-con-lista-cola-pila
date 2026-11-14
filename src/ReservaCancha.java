import java.util.Objects;

// Entidad principal del Caso 24: Centro Deportivo
// Representa una reserva de escenario deportivo identificada por su código único
public class ReservaCancha {

    private String codigoReserva;   // Identificador principal único (ej. RES-101)
    private String nombreCliente;   // Nombre del usuario que solicita la cancha
    private String tipoCancha;      // Escenario reservado (Fútbol 5, Tenis, Baloncesto, Vóley)
    private String horario;         // Franja horaria de la reserva (ej. 18:00 - 19:00)
    private String estado;          // Estado actual: PENDIENTE, PROCESADA o CANCELADA

    // Constructor completo
    public ReservaCancha(String codigoReserva, String nombreCliente, String tipoCancha,
                         String horario, String estado) {
        this.codigoReserva = codigoReserva;
        this.nombreCliente = nombreCliente;
        this.tipoCancha = tipoCancha;
        this.horario = horario;
        this.estado = estado;
    }

    // Constructor auxiliar de búsqueda: permite crear un objeto temporal solo con el código
    // para que lista.buscarDato() o cola.contiene() lo encuentren mediante equals()
    public ReservaCancha(String codigoReserva) {
        this.codigoReserva = codigoReserva;
    }

    public String getCodigoReserva() { return codigoReserva; }
    public void setCodigoReserva(String codigoReserva) { this.codigoReserva = codigoReserva; }

    public String getNombreCliente() { return nombreCliente; }
    public void setNombreCliente(String nombreCliente) { this.nombreCliente = nombreCliente; }

    public String getTipoCancha() { return tipoCancha; }
    public void setTipoCancha(String tipoCancha) { this.tipoCancha = tipoCancha; }

    public String getHorario() { return horario; }
    public void setHorario(String horario) { this.horario = horario; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }

    // Dos reservas son iguales si tienen el mismo código de reserva (sin importar mayúsculas)
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof ReservaCancha)) return false;
        ReservaCancha otra = (ReservaCancha) obj;
        if (this.codigoReserva == null || otra.codigoReserva == null) return false;
        return this.codigoReserva.equalsIgnoreCase(otra.codigoReserva);
    }

    @Override
    public int hashCode() {
        return Objects.hash(codigoReserva == null ? "" : codigoReserva.toUpperCase());
    }

    @Override
    public String toString() {
        return String.format("[%s] Cliente: %-16s | Cancha: %-11s | Horario: %-13s | Estado: %s",
                codigoReserva, nombreCliente, tipoCancha, horario, estado);
    }
}
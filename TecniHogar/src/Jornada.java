import java.util.ArrayList;

public class Jornada
{
    private Cuadrilla cuadrilla;
    private String estado;

    private ArrayList<OrdenServicio> ordenes;
    private ArrayList<ResultadoVisita> resultados;

    public Jornada(Cuadrilla cuadrilla)
    {
        if (cuadrilla == null)
        {
            throw new IllegalArgumentException(
                "Debes seleccionar una cuadrilla."
            );
        }

        this.cuadrilla = cuadrilla;
        this.estado = "PREPARACION";

        this.ordenes = new ArrayList<>();
        this.resultados = new ArrayList<>();

        cuadrilla.asociarJornada(this);
    }

    public String getEstado()
    {
        return this.estado;
    }

    public Cuadrilla getCuadrilla()
    {
        return this.cuadrilla;
    }

    public ArrayList<OrdenServicio> getOrdenes()
    {
        return new ArrayList<>(this.ordenes);
    }

    public ArrayList<ResultadoVisita> getResultados()
    {
        return new ArrayList<>(this.resultados);
    }

    private void validarPreparacion()
    {
        if (!"PREPARACION".equals(this.estado))
        {
            throw new IllegalStateException(
                "La jornada debe estar en preparación."
            );
        }
    }

    public double calcularHorasAsignadas()
    {
        double total = 0;

        for (OrdenServicio orden : this.ordenes)
        {
            total += orden.getHorasTrabajo();
        }

        return total;
    }

    public void asignarOrden(OrdenServicio orden)
    {
        validarPreparacion();

        if (orden == null)
        {
            throw new IllegalArgumentException(
                "Debes seleccionar una orden."
            );
        }

        orden.validarInicioVisita();

        if (orden.getJornada() != null)
        {
            throw new IllegalStateException(
                "La orden ya pertenece a una jornada."
            );
        }

        if (orden instanceof MantenimientoAC
                && !this.cuadrilla.estaCertificadaRefrigeracion())
        {
            throw new IllegalStateException(
                "La cuadrilla necesita certificación en refrigeración."
            );
        }

        double total =
            calcularHorasAsignadas() + orden.getHorasTrabajo();

        if (total > this.cuadrilla.getCapacidadHoras())
        {
            throw new IllegalStateException(
                "Se supera la capacidad de horas de la cuadrilla."
            );
        }

        this.ordenes.add(orden);
        orden.asignarJornada(this);
    }

    public void retirarOrden(OrdenServicio orden)
    {
        validarPreparacion();

        if (!this.ordenes.remove(orden))
        {
            throw new IllegalArgumentException(
                "La orden no pertenece a esta jornada."
            );
        }

        orden.asignarJornada(null);
    }

    public void iniciar()
    {
        validarPreparacion();

        if (this.ordenes.isEmpty())
        {
            throw new IllegalStateException(
                "No se puede iniciar una jornada vacía."
            );
        }

        // Comprobar todas antes de modificar alguna.
        for (OrdenServicio orden : this.ordenes)
        {
            orden.validarInicioVisita();
        }

        for (OrdenServicio orden : this.ordenes)
        {
            orden.iniciarVisita();
        }

        this.estado = "EN_CURSO";
    }

    private boolean tieneResultado(OrdenServicio orden)
    {
        for (ResultadoVisita resultado : this.resultados)
        {
            if (resultado.getOrden() == orden)
            {
                return true;
            }
        }

        return false;
    }

    public void registrarResultado(
        OrdenServicio orden, boolean completada)
    {
        if (!"EN_CURSO".equals(this.estado))
        {
            throw new IllegalStateException(
                "La jornada debe estar en curso."
            );
        }

        if (!this.ordenes.contains(orden))
        {
            throw new IllegalArgumentException(
                "La orden no pertenece a esta jornada."
            );
        }

        if (tieneResultado(orden))
        {
            throw new IllegalStateException(
                "La orden ya tiene un resultado registrado."
            );
        }

        orden.registrarResultado(completada);

        ResultadoVisita resultado =
            new ResultadoVisita(orden, completada);

        this.resultados.add(resultado);

        if (this.resultados.size() == this.ordenes.size())
        {
            this.estado = "CERRADA";
            this.cuadrilla.liberarJornada(this);
        }
    }
}
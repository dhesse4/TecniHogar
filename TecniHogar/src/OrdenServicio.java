public abstract class OrdenServicio
{
    private String id;
    private String nombreCliente;
    private String direccion;
    private double horasTrabajo;
    private String zona;

    private String estado;
    private int cantidadVisitas;
    private Jornada jornada;

    private boolean registrada;
    private double montoCobrado;
    private double cargosAdicionales;

    public OrdenServicio(String id, String nombreCliente, String direccion, double horasTrabajo, String zona)
    {
        if (id == null || id.trim().isEmpty())
        {
            throw new IllegalArgumentException("El ID es obligatorio.");
        }
        if (nombreCliente == null || nombreCliente.trim().isEmpty())
        {
            throw new IllegalArgumentException("El nombre del cliente es obligatorio.");
        }
        if (direccion == null || direccion.trim().isEmpty())
        {
            throw new IllegalArgumentException("La direccion es obligatoria.");
        }

        this.id = id;
        this.nombreCliente = nombreCliente;
        this.direccion = direccion;

        if (!Double.isFinite(horasTrabajo) || horasTrabajo <= 0)
        {
            throw new IllegalArgumentException(
                "Las horas estimadas deben ser positivas."
            );
        }

        this.horasTrabajo = horasTrabajo;
        setZona(zona);

        this.estado = "PENDIENTE";
        this.cantidadVisitas = 0;
        this.jornada = null;

        this.registrada = false;
        this.montoCobrado = 0;
        this.cargosAdicionales = 0;
    }

    void registrarSinCobro()
    {
        if (this.registrada)
        {
            throw new IllegalStateException(
                "La orden ya está registrada."
            );
        }

        this.registrada = true;
        this.estado = "PENDIENTE";
        this.montoCobrado = 0;
        this.cargosAdicionales = 0;
    }

    public String getId()
    {
        return this.id;
    }

    public String getNombreCliente()
    {
        return this.nombreCliente;
    }

    public String getDireccion()
    {
        return this.direccion;
    }

    public double getHorasTrabajo()
    {
        return this.horasTrabajo;
    }

    public String getZona()
    {
        return this.zona;
    }

    public void setZona(String zona)
    {
        if (zona == null)
        {
            throw new IllegalArgumentException("Zona inválida.");
        }

        zona = zona.trim();

        if ("metropolitana".equalsIgnoreCase(zona))
        {
            this.zona = "metropolitana";
        }
        else if ("periferica".equalsIgnoreCase(zona))
        {
            this.zona = "periferica";
        }
        else
        {
            throw new IllegalArgumentException("Zona inválida.");
        }
    }

    public String getEstado()
    {
        return this.estado;
    }

    public int getCantidadVisitas()
    {
        return this.cantidadVisitas;
    }

    public Jornada getJornada()
    {
        return this.jornada;
    }

    public boolean estaRegistrada()
    {
        return this.registrada;
    }

    public double getMontoCobrado()
    {
        return this.montoCobrado;
    }

    public double getCargosAdicionales()
    {
        return this.cargosAdicionales;
    }

    void registrar()
    {
        if (this.registrada)
        {
            throw new IllegalStateException(
                "La orden ya está registrada."
            );
        }

        this.montoCobrado = calculoServicio();
        this.registrada = true;
    }

    void asignarJornada(Jornada jornada)
    {
        this.jornada = jornada;
    }

    void validarInicioVisita()
    {
        if (!this.registrada)
        {
            throw new IllegalStateException(
                "La orden debe estar registrada."
            );
        }

        if (!"PENDIENTE".equals(this.estado))
        {
            throw new IllegalStateException(
                "La orden debe estar pendiente."
            );
        }

        if (this.cantidadVisitas >= getMaximoVisitas())
        {
            throw new IllegalStateException(
                "La orden agotó sus visitas."
            );
        }
    }

    void iniciarVisita()
    {
        validarInicioVisita();

        this.cantidadVisitas++;

        if (this.cantidadVisitas > 1)
        {
            this.cargosAdicionales += 40;
        }

        this.estado = "EN_ATENCION";
    }

    void registrarResultado(boolean completada)
    {
        if (!"EN_ATENCION".equals(this.estado))
        {
            throw new IllegalStateException(
                "La orden no está en atención."
            );
        }

        if (completada)
        {
            this.estado = "COMPLETADA";
        }
        else if (this.cantidadVisitas >= getMaximoVisitas())
        {
            this.estado = "CANCELADA";
        }
        else
        {
            this.estado = "PENDIENTE";
        }

        this.jornada = null;
    }

    public abstract double calculoServicio();

    public abstract int getMaximoVisitas();

    public abstract int calculoTiempo();
}
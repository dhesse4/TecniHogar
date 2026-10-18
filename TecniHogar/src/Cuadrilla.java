public class Cuadrilla
{
    private String codigo;
    private double capacidadHoras;
    private boolean certificadaRefrigeracion;
    private Jornada jornadaAbierta;

    public Cuadrilla(
        String codigo, double capacidadHoras,
        boolean certificadaRefrigeracion)
    {
        if (codigo == null || codigo.trim().isEmpty())
        {
            throw new IllegalArgumentException(
                "El código es obligatorio."
            );
        }

        if (!Double.isFinite(capacidadHoras) || capacidadHoras <= 0)
        {
            throw new IllegalArgumentException(
                "La capacidad de horas debe ser positiva."
            );
        }

        this.codigo = codigo.trim();
        this.capacidadHoras = capacidadHoras;
        this.certificadaRefrigeracion = certificadaRefrigeracion;
        this.jornadaAbierta = null;
    }

    public String getCodigo()
    {
        return this.codigo;
    }

    public double getCapacidadHoras()
    {
        return this.capacidadHoras;
    }

    public boolean estaCertificadaRefrigeracion()
    {
        return this.certificadaRefrigeracion;
    }

    public Jornada getJornadaAbierta()
    {
        return this.jornadaAbierta;
    }

    void asociarJornada(Jornada jornada)
    {
        if (this.jornadaAbierta != null)
        {
            throw new IllegalStateException(
                "La cuadrilla ya tiene una jornada abierta."
            );
        }

        this.jornadaAbierta = jornada;
    }

    void liberarJornada(Jornada jornada)
    {
        if (this.jornadaAbierta == jornada)
        {
            this.jornadaAbierta = null;
        }
    }
}
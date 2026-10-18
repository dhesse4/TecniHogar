public class ResultadoVisita
{
    private OrdenServicio orden;
    private boolean completada;

    public ResultadoVisita(
        OrdenServicio orden, boolean completada)
    {
        this.orden = orden;
        this.completada = completada;
    }

    public OrdenServicio getOrden()
    {
        return this.orden;
    }

    public boolean estaCompletada()
    {
        return this.completada;
    }

    public String getDescripcion()
    {
        if (this.completada)
        {
            return "COMPLETADA";
        }

        return "VISITA_FALLIDA";
    }
}
public class InstalacionPanelesSolares extends Instalacion
{

    private int cantidadPaneles;

    public InstalacionPanelesSolares(String id, String nombreCliente, String direccion,
    double horasTrabajo, String zona, Integer puntosInstalacion, Integer cantidadPaneles)
    {
        super(id, nombreCliente, direccion, horasTrabajo, zona, puntosInstalacion);

        setPaneles(cantidadPaneles);
    }

   public void setPaneles(int cantidad)
    {
        if (cantidad <= 0)
        {
            throw new IllegalArgumentException(
                "La cantidad de paneles debe ser mayor que cero"
            );
        }

        this.cantidadPaneles = cantidad;
    }

    @Override 
    public double horasFacturables()
    {
        double horasPuntos = super.getPuntos() * 0.8;
        return Math.ceil(Math.max(super.getHorasTrabajo(), horasPuntos));
    }

    @Override
    public double calculoServicio()
    {
        return super.calcularTarifaBase() + (90 * horasFacturables()) + (200 * cantidadPaneles);
    }

    @Override 
    public int calculoTiempo()
    {
        return super.calculoTiempo() + 5;
    }

    @Override
    public int getMaximoVisitas()
    {
        return 3;
    }

}
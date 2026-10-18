public class InstalacionElectrica extends Instalacion
{

    private double valorMateriales;

    public InstalacionElectrica
    (
        String id, String nombreCliente, String direccion,
        double horasTrabajo, String zona, Integer puntosInstalacion, double valorMateriales) 
    {

        super(id, nombreCliente, direccion, horasTrabajo, zona, puntosInstalacion);
        setMateriales(valorMateriales);

    }

    public double getMateriales()
    {
        return this.valorMateriales;
    }
    public void setMateriales(double materiales) 
    {
        if (!Double.isFinite(materiales) || materiales < 0) 
        {
            throw new IllegalArgumentException
            (
                "El valor de los materiales debe ser un número válido y no negativo."
            );
        }

        this.valorMateriales = materiales;
    }

   @Override
    public double calculoServicio() 
    {
        return super.calculoServicio() + 120 + (this.valorMateriales * 0.05);
    }
    
    @Override 
    public int calculoTiempo()
    {
        return super.calculoTiempo() + 2;
    }

    @Override
    public int getMaximoVisitas()
    {
        return 2;
    }
}

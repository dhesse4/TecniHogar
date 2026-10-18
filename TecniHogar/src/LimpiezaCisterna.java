public class LimpiezaCisterna extends OrdenServicio
{

    private double capacidadLitros;

    public LimpiezaCisterna(String id, String nombreCliente, String direccion, double horasTrabajo, String zona, double capacidadCisterna) 
    {
        super(id, nombreCliente, direccion, horasTrabajo, zona);
        setCapacidad(capacidadCisterna);
       
    }

   public void setCapacidad(double capacidad)
    {
        if (!Double.isFinite(capacidad) || capacidad <= 0)
        {
            throw new IllegalArgumentException(
                "La capacidad debe ser un número finito mayor que cero."
            );
        }

        this.capacidadLitros = capacidad;
    }

    public double getCapacidad()
    {
        return this.capacidadLitros;
    }

    public double horasFacturables()
    {
        double horasCapacidad = this.capacidadLitros / 1000.0;

        return Math.max(super.getHorasTrabajo(), horasCapacidad);
    }

    public int calcularTarifaBase()
    {
        if ("metropolitana".equalsIgnoreCase(super.getZona()))
        {
            return 150;
        }
        else if ("periferica".equalsIgnoreCase(super.getZona()))
        {
            return 225;
        }
        else
        {
            throw new IllegalArgumentException("Zona inválida.");
        }
    }

   @Override
    public int calculoTiempo()
    {
        return (int) Math.ceil(this.capacidadLitros / 1000.0);
    }

    @Override
    public double calculoServicio()
    {
        return calcularTarifaBase() + (horasFacturables() * 80) + 50;
    }

    @Override
    public int getMaximoVisitas()
    {
        return 2;
    }
    
}

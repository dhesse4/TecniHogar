public class Instalacion extends OrdenServicio
{
    private Integer puntosInstalacion;

    public Instalacion
    (String id, String nombreCliente, String direccion, double horasTrabajo, String zona, Integer puntosInstalacion) 
    {
        super(id, nombreCliente, direccion, horasTrabajo, zona);
        setPuntos(puntosInstalacion);
    }

    public Integer getPuntos()
    {
        return puntosInstalacion;
    }
    public void setPuntos(Integer puntos) 
    {
        if (puntos == null || puntos <= 0) 
        {
            throw new IllegalArgumentException
            (
                "La cantidad de puntos debe ser un entero positivo."
            );
        }

        this.puntosInstalacion = puntos;
    }
    
    public double horasFacturables()
    {

        double horasPuntos = this.puntosInstalacion * 0.75;
        return Math.ceil(Math.max(super.getHorasTrabajo(), horasPuntos));
    }

    public int calcularTarifaBase()
    {
        if("metropolitana".equalsIgnoreCase(super.getZona()))
        {
            return 100;
        }
        else if("periferica".equalsIgnoreCase(super.getZona()))
        {
            return 175;
        }
        else 
        {
            throw new IllegalArgumentException("Zona inválida.");
        }
    }

    @Override 
    public double calculoServicio()
    {
        return calcularTarifaBase() + (horasFacturables() * 90);
    }

    @Override 
    public int calculoTiempo()
    {
        if("metropolitana".equalsIgnoreCase(super.getZona()))
        {
            return 3;
        }
        else if("periferica".equalsIgnoreCase(super.getZona()))
        {
            return 5;
        }
        else 
        {
            throw new IllegalArgumentException("Zona inválida.");
        }
    }
 
    @Override
    public int getMaximoVisitas()
    {
        return 3;
    }
}
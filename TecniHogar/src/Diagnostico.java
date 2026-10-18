public class Diagnostico extends OrdenServicio 
{

    public Diagnostico(String id, String nombreCliente, String direccion,
    double horasTrabajo, String zona)
    {

        super(id, nombreCliente, direccion, validarHoras(horasTrabajo),
        zona);
    }

    private static double validarHoras(double horasTrabajo) 
    {
        if (horasTrabajo > 1.5) 
        {
            throw new IllegalArgumentException
            (
                "Los diagnósticos no pueden superar las 1.5 horas estimadas."
            );
        }

        return horasTrabajo;
    }

    @Override 
    public double calculoServicio()
    {
        if("metropolitana".equalsIgnoreCase(super.getZona()))
        {
            return 150.00;
        }
        else if("periferica".equalsIgnoreCase(super.getZona()))
        {
            return 225.00;
        }
        else 
        {
            throw new IllegalArgumentException("Zona inválida.");
        }
    }

    @Override 
    public int calculoTiempo()
    {
        if("metropolitana".equalsIgnoreCase(super.getZona()))
        {
            return 1;
        }
        else if("periferica".equalsIgnoreCase(super.getZona()))
        {
            return 2;
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
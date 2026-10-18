public class MantenimientoAC extends OrdenServicio
{
    private Integer capacidadBTU;

    public MantenimientoAC
    (String id, String nombreCliente, String direccion, double horasTrabajo, String zona, int capacidadBTU) 
    {
        super(id, nombreCliente, direccion, horasTrabajo, validarZona(zona));
        setBTU(capacidadBTU);
    }

    public void setBTU(int capacidad) 
    {
        if (capacidad < 9000 || capacidad > 60000) 
        {
            throw new IllegalArgumentException
            (
                "La capacidad debe estar entre 9,000 y 60,000."
            );
        }

        this.capacidadBTU = capacidad;
    }

    private static String validarZona(String zona) 
    {
    if (zona == null || !"metropolitana".equalsIgnoreCase(zona.trim())) 
    {
        throw new IllegalArgumentException
        (
            "Solo se acepta la zona metropolitana para mantenimiento de AC."
        );
    }

        return "metropolitana";
    }

    @Override
    public void setZona(String zona) 
    {
        super.setZona(validarZona(zona));
    }

    @Override
    public double calculoServicio() 
    {
        return 100 + 80 + (super.getHorasTrabajo() * 110);
    }
    
    @Override 
    public int calculoTiempo()
    {
        return 2;
    }

    @Override
    public int getMaximoVisitas()
    {
        return 1;
    }
}
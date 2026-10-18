import java.util.ArrayList;

public class GestorServicios
{
    private ArrayList<OrdenServicio> ordenes = new ArrayList<>();
    private ArrayList<Cuadrilla> cuadrillas = new ArrayList<>();
    private ArrayList<Jornada> jornadas = new ArrayList<>();

    public void cotizar(OrdenServicio servicio)
    {
        System.out.println("Costo: Q" + servicio.calculoServicio());
        System.out.println( "Tiempo estimado: " + servicio.calculoTiempo() + " dias");
    }

    public OrdenServicio buscarOrden(String id)
    {
        for (OrdenServicio orden : this.ordenes)
        {
            if (orden.getId().equalsIgnoreCase(id))
            {
                return orden;
            }
        }

        throw new IllegalArgumentException("No existe esa orden.");
    }

    private void validarIdDisponible(String id)
    {
        for (OrdenServicio orden : this.ordenes)
        {
            if (orden.getId().equalsIgnoreCase(id))
            {
                throw new IllegalArgumentException("Ya existe una orden con ese ID.");
            }
        }
    }

    public void registrarOrden(OrdenServicio orden)
    {
        validarIdDisponible(orden.getId());

        orden.registrar();
        this.ordenes.add(orden);

        System.out.println("Orden registrada. Cobro: Q" + orden.getMontoCobrado());
    }

    public void registrarOrdenInicial(OrdenServicio orden)
    {
        validarIdDisponible(orden.getId());

        orden.registrarSinCobro();
        this.ordenes.add(orden);
    }

    public Cuadrilla buscarCuadrilla(String codigo)
    {
        for (Cuadrilla cuadrilla : this.cuadrillas)
        {
            if (cuadrilla.getCodigo().equalsIgnoreCase(codigo))
            {
                return cuadrilla;
            }
        }

        throw new IllegalArgumentException("No existe esa cuadrilla.");
    }

    public void registrarCuadrilla(Cuadrilla nueva)
    {
        for (Cuadrilla cuadrilla : this.cuadrillas)
        {
            if (cuadrilla.getCodigo().equalsIgnoreCase(nueva.getCodigo()))
            {
                throw new IllegalArgumentException("Ya existe una cuadrilla con ese codigo.");
            }
        }

        this.cuadrillas.add(nueva);
    }

    public int crearJornada(String codigoCuadrilla)
    {
        Cuadrilla cuadrilla = buscarCuadrilla(codigoCuadrilla);
        Jornada jornada = new Jornada(cuadrilla);

        this.jornadas.add(jornada);

        return this.jornadas.size();
    }

    public Jornada buscarJornada(int numero)
    {
        if (numero < 1 || numero > this.jornadas.size())
        {
            throw new IllegalArgumentException("No existe esa jornada.");
        }

        return this.jornadas.get(numero - 1);
    }

    public void consultarOrdenes()
    {
        for (OrdenServicio orden : this.ordenes)
        {
            System.out.println("\nID: " + orden.getId() + "\nCategoria: " + orden.getClass().getSimpleName()
            + "\nCliente: " + orden.getNombreCliente() + "\nEstado: " + orden.getEstado()
            + "\nHoras estimadas: " + orden.getHorasTrabajo() + "\nVisitas: " + orden.getCantidadVisitas() + "\nDias habiles: " + orden.calculoTiempo()
            + "\nCobro inicial: Q" + orden.getMontoCobrado() + "\nCargos adicionales: Q" + orden.getCargosAdicionales());
        }
    }

    public void consultarCuadrillas()
    {
        for (Cuadrilla cuadrilla : this.cuadrillas)
        {
            System.out.println("\nCodigo: " + cuadrilla.getCodigo() + "\nCapacidad: " + cuadrilla.getCapacidadHoras()
            + "\nCertificada: "+ cuadrilla.estaCertificadaRefrigeracion()
            + "\nTiene jornada abierta: "+ (cuadrilla.getJornadaAbierta() != null));
        }
    }

    public void consultarJornadas()
    {
        for (int i = 0; i < this.jornadas.size(); i++)
        {
            Jornada jornada = this.jornadas.get(i);

            System.out.println("\nJornada: " + (i + 1) + "\nCuadrilla: " + jornada.getCuadrilla().getCodigo()
                + "\nEstado: " + jornada.getEstado() + "\nOrdenes: " + jornada.getOrdenes().size()
                + "\nHoras: " + jornada.calcularHorasAsignadas());

        }
    }

    public void reporteOrdenes()
    {
        String[] categorias = {
            "Diagnostico",
            "Instalacion",
            "InstalacionElectrica",
            "MantenimientoAC",
            "InstalacionPanelesSolares",
            "LimpiezaCisterna"
        };

        String[] estados = {
            "PENDIENTE",
            "EN_ATENCION",
            "COMPLETADA",
            "CANCELADA"
        };

        System.out.println("\nOrdenes por categoria y estado:");

        for (String categoria : categorias)
        {
            int totalCategoria = 0;

            System.out.println("\nCategoria: " + categoria);

            for (String estado : estados)
            {
                int cantidad = 0;

                for (OrdenServicio orden : this.ordenes)
                {
                    String categoriaOrden =
                        orden.getClass().getSimpleName();

                    if (categoria.equals(categoriaOrden) && estado.equals(orden.getEstado()))
                    {
                        cantidad++;
                    }
                }

                System.out.println(estado + ": " + cantidad);
                totalCategoria += cantidad;
            }

            System.out.println("Total de la categoria: " + totalCategoria);
        }

        System.out.println("\nTotal de ordenes: " + this.ordenes.size());
    }

    public void reporteIngresos()
    {
        double ingresosRegistro = 0;
        double ingresosVisitas = 0;

        for (OrdenServicio orden : this.ordenes)
        {
            ingresosRegistro += orden.getMontoCobrado();
            ingresosVisitas += orden.getCargosAdicionales();
        }

        System.out.println("\nReporte de ingresos:");

        System.out.println("Ingresos por registro: " + ingresosRegistro);

        System.out.println("Ingresos por visitas adicionales: " + ingresosVisitas);

        System.out.println("Ingreso total: " + (ingresosRegistro + ingresosVisitas));

        String[] categorias = {
            "Diagnostico",
            "Instalacion",
            "InstalacionElectrica",
            "MantenimientoAC",
            "InstalacionPanelesSolares",
            "LimpiezaCisterna"
        };

        System.out.println("\nIngresos por categoria:");

        for (String categoria : categorias)
        {
            double registroCategoria = 0;
            double visitasCategoria = 0;

            for (OrdenServicio orden : this.ordenes)
            {
                if (categoria.equals(orden.getClass().getSimpleName()))
                {
                    registroCategoria += orden.getMontoCobrado();
                    visitasCategoria += orden.getCargosAdicionales();
                }
            }

            System.out.println("\nCategoría: " + categoria);

            System.out.println("Por registro: " + registroCategoria);

            System.out.println("Por visitas adicionales: " + visitasCategoria);

            System.out.println("Total: " + (registroCategoria + visitasCategoria));
        }
    }

    public void reporteJornadasAbiertas()
    {
        boolean hayJornadas = false;

        System.out.println("\nJornadas sin cerrar:");

        for (int i = 0; i < this.jornadas.size(); i++)
        {
            Jornada jornada = this.jornadas.get(i);

            if (!"CERRADA".equals(jornada.getEstado()))
            {
                hayJornadas = true;

                Cuadrilla cuadrilla = jornada.getCuadrilla();

                double horasAsignadas = jornada.calcularHorasAsignadas();

                double capacidadDisponible = cuadrilla.getCapacidadHoras() - horasAsignadas;

                System.out.println("\nJornada: " + (i + 1));
                System.out.println("Estado: " + jornada.getEstado());

                System.out.println("Cuadrilla: " + cuadrilla.getCodigo());

                System.out.println("Capacidad total: " + cuadrilla.getCapacidadHoras() + " horas");

                System.out.println("Horas asignadas: " + horasAsignadas);

                System.out.println("Capacidad disponible: " + capacidadDisponible + " horas");
            }
        }

        if (!hayJornadas)
        {
            System.out.println("No hay jornadas abiertas.");
        }
    }

}
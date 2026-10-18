import java.util.Scanner;

public class App 
{
    GestorServicios gestor = new GestorServicios();
    private static Scanner entrada = new Scanner(System.in);
    public void main(String[] args) throws Exception 
    {
        DatosIniciales();

        int eleccion = 0;
        while(eleccion != 12)
        {
            try // try para manejar las posibles excepciones que tenga el usuario.
            {

                System.out.println("\n----- Ordenes TecniHogar -----");
                System.out.println("1. Cotizar y registrar orden");
                System.out.println("2. Registrar cuadrilla");
                System.out.println("3. Crear jornada");
                System.out.println("4. Asignar orden");
                System.out.println("5. Retirar orden");
                System.out.println("6. Iniciar jornada");
                System.out.println("7. Registrar resultado de visita");
                System.out.println("8. Consultar órdenes");
                System.out.println("9. Consultar cuadrillas");
                System.out.println("10. Consultar jornadas");
                System.out.println("11. Reportes");
                System.out.println("12. Salir");
                System.out.print("Elige la opcion que deseas realizar: ");
                eleccion = entrada.nextInt();
                entrada.nextLine();

                if(eleccion == 1)
                {
                    crearOrden();      
                }

                else if(eleccion == 2)
                {
                    System.out.print("Codigo de cuadrilla: ");
                    String codigo = entrada.nextLine();

                    System.out.print("Capacidad de horas: ");
                    double horas = entrada.nextDouble();
                    entrada.nextLine();

                    System.out.print("¿Certificada en refrigeracion? (s/n): ");
                    String respuesta = entrada.nextLine();

                    if (!"s".equalsIgnoreCase(respuesta) && !"n".equalsIgnoreCase(respuesta))
                    {
                        System.out.println("Debe responder s o n.");
                        continue;
                    }

                    boolean certificada = "s".equalsIgnoreCase(respuesta);

                    Cuadrilla cuadrilla = new Cuadrilla(codigo, horas, certificada);

                    gestor.registrarCuadrilla(cuadrilla);
                    System.out.println("Cuadrilla registrada.");
                }

                else if(eleccion == 3)
                {
                    gestor.consultarCuadrillas();

                    System.out.print("Código de cuadrilla: ");
                    String codigo = entrada.nextLine();

                    int numero = gestor.crearJornada(codigo);

                    System.out.println("Jornada creada. Número: " + numero);
                }

                else if(eleccion == 4)
                {
                    gestor.consultarJornadas();

                    System.out.print("Número de jornada: ");
                    int numero = entrada.nextInt();
                    entrada.nextLine();

                    Jornada jornada = gestor.buscarJornada(numero);

                    gestor.consultarOrdenes();

                    System.out.print("ID de la orden: ");
                    String id = entrada.nextLine();

                    OrdenServicio orden = gestor.buscarOrden(id);

                    jornada.asignarOrden(orden);
                    System.out.println("Orden asignada.");
                }

                else if(eleccion == 5)
                {
                    gestor.consultarJornadas();

                    System.out.print("Número de jornada: ");
                    int numero = entrada.nextInt();
                    entrada.nextLine();

                    Jornada jornada = gestor.buscarJornada(numero);

                    System.out.print("ID de la orden a retirar: ");
                    String id = entrada.nextLine();

                    OrdenServicio orden = gestor.buscarOrden(id);

                    jornada.retirarOrden(orden);
                    System.out.println("Orden retirada.");
                }

                else if(eleccion == 6)
                {
                    gestor.consultarJornadas();

                    System.out.print("Número de jornada: ");
                    int numero = entrada.nextInt();
                    entrada.nextLine();

                    Jornada jornada = gestor.buscarJornada(numero);

                    jornada.iniciar();
                    System.out.println("Jornada iniciada.");
                }

                else if(eleccion == 7)
                {
                    gestor.consultarJornadas();

                    System.out.print("Número de jornada: ");
                    int numero = entrada.nextInt();
                    entrada.nextLine();

                    Jornada jornada = gestor.buscarJornada(numero);

                    for (OrdenServicio orden : jornada.getOrdenes())
                    {
                        System.out.println("Orden: " + orden.getId());
                    }

                    System.out.print("ID de la orden: ");
                    String id = entrada.nextLine();

                    OrdenServicio orden = gestor.buscarOrden(id);

                    System.out.println("1. Completada");
                    System.out.println("2. Visita fallida");
                    System.out.print("Resultado: ");

                    int resultado = entrada.nextInt();
                    entrada.nextLine();

                    if (resultado != 1 && resultado != 2)
                    {
                        System.out.println("Resultado inválido.");
                        continue;
                    }

                    jornada.registrarResultado(orden, resultado == 1);

                    System.out.println("Estado de la orden: " + orden.getEstado());

                    System.out.println("Estado de la jornada: " + jornada.getEstado());
                }

                else if(eleccion == 8)
                {
                    gestor.consultarOrdenes();
                }

                else if(eleccion == 9)
                {
                    gestor.consultarCuadrillas();
                }

                else if(eleccion == 10)
                {
                    gestor.consultarJornadas();
                }

                else if(eleccion == 11)
                {
                    System.out.println("\nReportes:");
                    System.out.println("1. Órdenes por categoria y estado");
                    System.out.println("2. Ingresos");
                    System.out.println("3. Jornadas sin cerrar");
                    System.out.print("Seleccione un reporte: ");

                    int reporte = entrada.nextInt();
                    entrada.nextLine();

                    switch (reporte)
                    {
                        case 1:
                            gestor.reporteOrdenes();
                            break;

                        case 2:
                            gestor.reporteIngresos();
                            break;

                        case 3:
                            gestor.reporteJornadasAbiertas();
                            break;

                        default:
                            System.out.println("Reporte invalido.");
                    }
                }
                else if (eleccion != 12)
                {
                    System.out.println("Opción inválida. Elige un número del 1 al 12.");
                }
            }
            catch (java.util.InputMismatchException e)
            {
                System.out.println("Debes ingresar un número válido.");
                entrada.nextLine();
            }
            catch (IllegalArgumentException | IllegalStateException e)
            {
                System.out.println("Error: " + e.getMessage());
            }

        }
    
        entrada.close();

    }

    public void DatosIniciales()
    {
        gestor.registrarCuadrilla(new Cuadrilla("C01", 8, true));

        gestor.registrarCuadrilla(new Cuadrilla("C02", 8, false));

        gestor.registrarCuadrilla(new Cuadrilla("C03", 10, false));

        gestor.registrarOrdenInicial(new Diagnostico("D01", "Ana", "Zona 1", 1, "metropolitana"));

        gestor.registrarOrdenInicial(new Diagnostico("D02", "Luis", "Villa Nueva",1.5, "periferica"));

        gestor.registrarOrdenInicial(new Instalacion("I01", "Marta", "Zona 7",2, "metropolitana", 3));

        gestor.registrarOrdenInicial(new Instalacion("I02", "Pedro", "Mixco",3, "periferica", 4));

        gestor.registrarOrdenInicial(new InstalacionElectrica("E01", "Sofía", "Zona 10",2.5, "metropolitana", 5, 2000));

        gestor.registrarOrdenInicial(new InstalacionElectrica("E02", "Carlos", "Villa Nueva",3, "periferica", 4, 1500));

        gestor.registrarOrdenInicial(new MantenimientoAC("A01", "Laura", "Zona 4",2, "metropolitana", 12000));

        gestor.registrarOrdenInicial(new MantenimientoAC("A02", "Diego", "Zona 14",3, "metropolitana", 24000));

        gestor.registrarOrdenInicial(new InstalacionPanelesSolares("P01", "Elena", "Zona 16",3, "metropolitana", 2, 15));

        gestor.registrarOrdenInicial(new InstalacionPanelesSolares("P02", "Jorge", "Mixco",4, "periferica", 6, 12));

        gestor.registrarOrdenInicial(new LimpiezaCisterna("L01", "Rosa", "Zona 11", 2, "metropolitana", 2000));

        gestor.registrarOrdenInicial(new LimpiezaCisterna("L02", "Mario", "Villa Nueva", 3, "periferica", 3000));

    }

    public void crearOrden()
    {
        System.out.println("1. Diagnóstico");
        System.out.println("2. Instalación");
        System.out.println("3. Instalación eléctrica");
        System.out.println("4. Mantenimiento de AC");
        System.out.println("5. Paneles solares");
        System.out.println("6. Limpieza de cisternas");
        System.out.print("Categoría: ");

        int categoria = entrada.nextInt();
        entrada.nextLine();

        if (categoria < 1 || categoria > 6)
        {
            System.out.println("Categoría inválida.");
            return;
        }

        System.out.print("ID: ");
        String id = entrada.nextLine();

        System.out.print("Nombre del cliente: ");
        String cliente = entrada.nextLine();

        System.out.print("Dirección: ");
        String direccion = entrada.nextLine();

        System.out.print("Horas estimadas: ");
        double horas = entrada.nextDouble();
        entrada.nextLine();

        System.out.print("Zona: ");
        String zona = entrada.nextLine();

        OrdenServicio orden = null;

        switch (categoria)
        {
            case 1:
                orden = new Diagnostico(id, cliente, direccion, horas, zona);
                break;

            case 2:
                System.out.print("Puntos de instalación: ");
                int puntos = entrada.nextInt();
                entrada.nextLine();

                orden = new Instalacion(id, cliente, direccion,horas, zona, puntos);
                break;

            case 3:
                System.out.print("Puntos de instalación: ");
                int puntosElectricos = entrada.nextInt();
                entrada.nextLine();

                System.out.print("Valor de materiales: ");
                double materiales = entrada.nextDouble();
                entrada.nextLine();

                orden = new InstalacionElectrica(id, cliente, direccion,horas, zona,puntosElectricos, materiales);
                break;

            case 4:
                System.out.print("Capacidad en BTU: ");
                int btu = entrada.nextInt();
                entrada.nextLine();

                orden = new MantenimientoAC(id, cliente, direccion,horas, zona, btu);
                break;

            case 5:
                System.out.print("Puntos de instalación: ");
                int puntosSolares = entrada.nextInt();
                entrada.nextLine();

                System.out.print("Cantidad de paneles: ");
                int paneles = entrada.nextInt();
                entrada.nextLine();

                orden = new InstalacionPanelesSolares(id, cliente, direccion,horas, zona, puntosSolares, paneles);
                break;

            case 6:
            System.out.print("Capacidad en litros: ");
            int litros = entrada.nextInt();
            entrada.nextLine();

            orden = new LimpiezaCisterna(id, cliente, direccion,horas, zona, litros);
            break;

        }

            gestor.cotizar(orden);

            System.out.print("¿Desea registrar la orden? (s/n): ");
            String respuesta = entrada.nextLine();

            if ("s".equalsIgnoreCase(respuesta))
            {
                gestor.registrarOrden(orden);
            }

            else
            {
                System.out.println("Orden no registrada.");
            }
    }
    
    
}

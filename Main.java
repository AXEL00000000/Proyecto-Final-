import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws Exception {
        // Crear una instancia del árbol de series
        ArbolSeries arbolSeries = new ArbolSeries();
        // Crear una instancia del grafo
        Grafo grafo = new Grafo();

        // Ruta estática del archivo CSV
        String rutaArchivo ="C:\\Users\\yonot\\Downloads\\series_2000.csv"; // Cambia esto por la ruta de tu archivo CSV

        // Intentar cargar los datos del archivo CSV antes de mostrar el menú
        try {
            arbolSeries.cargarDesdeCsvPorNombre(rutaArchivo);
            System.out.println("Datos cargados correctamente desde el archivo CSV.");
        } catch (IOException e) {
            System.out.println("Error al cargar el archivo CSV: " + e.getMessage());
            return; // Si hay un error, salir del programa
        }

        // Mostrar el menú después de cargar los datos
        Scanner scanner = new Scanner(System.in);

        while (true) {
            // Mostrar menú
            System.out.println("\nMenú:");
            System.out.println("1. Mostrar todas las series");
            System.out.println("2. Buscar serie por nombre");
            System.out.println("3. Agregar nueva serie");
            System.out.println("4. Agregar episodio a serie");
            System.out.println("5. Eliminar serie por nombre");
            System.out.println("6. Calcular calificación promedio de una temporada");
            System.out.println("7. Insertar series en cola e imprimir");
            System.out.println("8. Obtener temporadas de una serie");
            System.out.println("9. Obtener episodios de una temporada");
            System.out.println("10. Agregar serie para relacionarlas");
            System.out.println("11. Ver series relacionadas entre series");
            System.out.println("12. Agregar relación entre series(series agregadas para relacionar)");
            System.out.println("13. Salir");
            System.out.print("Selecciona una opción: ");

            int opcion = scanner.nextInt();
            scanner.nextLine();  // Consumir el salto de línea

            switch (opcion) {
                case 1:
                    // Mostrar todas las series
                    arbolSeries.imprimirEnOrden();
                    break;

                case 2:
                    // Buscar serie por nombre
                    System.out.print("Ingresa el nombre de la serie a buscar: ");
                    String nombreBusqueda = scanner.nextLine();
                    try {
                        Serie serie = arbolSeries.buscarPorNombre(nombreBusqueda);
                        System.out.println("Serie encontrada: " + serie);
                    } catch (Exception e) {
                        System.out.println("Error: " + e.getMessage());
                    }
                    break;

                case 3:
                    // Agregar nueva serie
                    System.out.print("Ingresa el nombre de la serie: ");
                    String nombreSerie = scanner.nextLine();
                    System.out.print("Ingresa el género de la serie: ");
                    String generoSerie = scanner.nextLine();
                    arbolSeries.agregarSerieYGenero(nombreSerie, generoSerie);
                    System.out.println("Serie agregada correctamente.");
                    break;

                case 4:
                    // Agregar episodio a una serie
                    System.out.print("Ingresa el nombre de la serie: ");
                    String nombreSerieEpisodio = scanner.nextLine();
                    System.out.print("Ingresa el número de temporada: ");
                    int numeroTemporada = scanner.nextInt();
                    System.out.print("Ingresa el número de episodio: ");
                    int numeroEpisodio = scanner.nextInt();
                    scanner.nextLine();  // Consumir el salto de línea
                    System.out.print("Ingresa el título del episodio: ");
                    String tituloEpisodio = scanner.nextLine();
                    System.out.print("Ingresa la duración del episodio (en minutos): ");
                    int duracionEpisodio = scanner.nextInt();
                    System.out.print("Ingresa el rating del episodio: ");
                    double ratingEpisodio = scanner.nextDouble();
                    try {
                        arbolSeries.agregarEpisodioACorrespondiente(nombreSerieEpisodio, numeroTemporada, numeroEpisodio, tituloEpisodio, duracionEpisodio, ratingEpisodio);
                        System.out.println("Episodio agregado correctamente.");
                    } catch (Exception e) {
                        System.out.println("Error al agregar el episodio: " + e.getMessage());
                    }
                    break;

                case 5:
                    // Eliminar serie por nombre
                    System.out.print("Ingresa el nombre de la serie a eliminar: ");
                    String nombreEliminar = scanner.nextLine();
                    arbolSeries.eliminarPorNombre(nombreEliminar);
                    System.out.println("Serie eliminada correctamente.");
                    break;

                case 6:
                    // Calcular calificación promedio de una temporada
                    System.out.print("Ingresa el nombre de la serie: ");
                    String nombreSerieCalificacion = scanner.nextLine();
                    System.out.print("Ingresa el número de temporada: ");
                    int temporadaCalificacion = scanner.nextInt();
                    try {
                        Serie serieCalificacion = arbolSeries.buscarPorNombre(nombreSerieCalificacion);
                        Temporada temporada = serieCalificacion.obtenerTemporada(temporadaCalificacion);
                        double calificacionPromedio = arbolSeries.calcularCalificacionPromedio(temporada);
                        System.out.println("Calificación promedio de la temporada: " + calificacionPromedio);
                    } catch (Exception e) {
                        System.out.println("Error: " + e.getMessage());
                    }
                    break;

                case 7:
                    // Insertar series en cola e imprimir
                    System.out.print("Ingresa los nombres de las series separados por coma: ");
                    String seriesInput = scanner.nextLine();
                    String[] nombresSeries = seriesInput.split(",");
                    try {
                        arbolSeries.insertarSeriesEnColaYImprimir(nombresSeries);
                    } catch (Exception e) {
                        System.out.println("Error: " + e.getMessage());
                    }
                    break;

                case 8:
                    // Obtener temporadas de una serie
                    System.out.print("Ingresa el nombre de la serie: ");
                    String nombreSerieTemporadas = scanner.nextLine();
                    try {
                        Temporada[] temporadas = arbolSeries.obtenerTemporadasPorNombreSerie(nombreSerieTemporadas);
                        System.out.println("Temporadas de la serie: ");
                        for (Temporada temporada : temporadas) {
                            System.out.println(temporada);
                        }
                    } catch (Exception e) {
                        System.out.println("Error: " + e.getMessage());
                    }
                    break;

                case 9:
                    // Obtener episodios de una temporada
                    System.out.print("Ingresa el nombre de la serie: ");
                    String nombreSerieEpisodios = scanner.nextLine();
                    System.out.print("Ingresa el número de temporada: ");
                    int numeroTemporadaEpisodios = scanner.nextInt();
                    try {
                        Episodio[] episodios = arbolSeries.obtenerEpisodiosPorNombreSerieYTemporada(nombreSerieEpisodios, numeroTemporadaEpisodios);
                        System.out.println("Episodios de la temporada: ");
                        for (Episodio episodio : episodios) {
                            System.out.println(episodio);
                        }
                    } catch (Exception e) {
                        System.out.println("Error: " + e.getMessage());
                    }
                    break;

                               case 10:
                    // Agregar serie al grafo
                    System.out.print("Ingresa el nombre de la serie para agregar al grafo: ");
                    String nombreSerieGrafo = scanner.nextLine();
                    try {
                        Serie serieGrafo = arbolSeries.buscarPorNombre(nombreSerieGrafo);
                        if (serieGrafo != null) {
                            if (grafo.agregarNodo(serieGrafo)) {
                                System.out.println("Serie agregada al grafo.");
                            } else {
                                System.out.println("La serie ya está en el grafo.");
                            }
                        } else {
                            System.out.println("La serie no fue encontrada en el árbol.");
                        }
                    } catch (Exception e) {
                        System.out.println("Error al agregar la serie al grafo: " + e.getMessage());
                    }
                    break;

                case 11:
                    // Ver series relacionadas en el grafo
                    System.out.print("Ingresa el nombre de la serie para ver las relacionadas: ");
                    String nombreSerieRelacionadas = scanner.nextLine();
                    grafo.obtenerSeriesRelacionadas(nombreSerieRelacionadas);
                    break;

                case 12:
                    // Agregar relación entre series
                    System.out.print("Ingresa el nombre de la primera serie para la relación: ");
                    String nombreSerie1 = scanner.nextLine();
                    System.out.print("Ingresa el nombre de la segunda serie para la relación: ");
                    String nombreSerie2 = scanner.nextLine();
                    if (grafo.agregarRelacion(nombreSerie1, nombreSerie2)) {
                        System.out.println("Relación agregada entre las dos series.");
                    } else {
                        System.out.println("No se pudo agregar la relación. Asegúrate de que ambas series existen en el grafo.");
                    }
                    break;

                case 13:
                    // Salir
                    System.out.println("¡Hasta luego!");
                    scanner.close();
                    return;

                default:
                    System.out.println("Opción no válida. Intenta de nuevo.");
            }
        }
    }
}


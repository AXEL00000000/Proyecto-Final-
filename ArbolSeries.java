import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class ArbolSeries {
    private NodoAvlObjeto<Serie> raiz;

    public ArbolSeries() {
        this.raiz = null;
    }

    public void insertarPorNombre(Serie serie) {
        this.raiz = generarNodoPorNombre(serie, raiz);
    }
    
    private NodoAvlObjeto<Serie> generarNodoPorNombre(Serie serie, NodoAvlObjeto<Serie> nodo) {
        if (nodo == null) {
            return new NodoAvlObjeto<>(serie);
        }
        
        if (serie.getNombre().compareTo(nodo.getDato().getNombre()) < 0) {
            nodo.setIzq(generarNodoPorNombre(serie, nodo.getIzq()));
        } else if (serie.getNombre().compareTo(nodo.getDato().getNombre()) > 0) {
            nodo.setDer(generarNodoPorNombre(serie, nodo.getDer()));
        }
        
        int nuevaAltura = 1 + Math.max(determinarAltura(nodo.getIzq()), determinarAltura(nodo.getDer()));
        nodo.setAltura(nuevaAltura);
        int balance = determinarAltura(nodo.getDer()) - determinarAltura(nodo.getIzq());
        
        if (balance >= 2 && serie.getNombre().compareTo(nodo.getDer().getDato().getNombre()) > 0) {
            nodo = rotacionIzq(nodo); 
        } else if (balance <= -2 && serie.getNombre().compareTo(nodo.getIzq().getDato().getNombre()) < 0) {
            nodo = rotacionDer(nodo); 
        } else if (balance >= 2 && serie.getNombre().compareTo(nodo.getDer().getDato().getNombre()) < 0) {
            nodo.setDer(rotacionDer(nodo.getDer())); 
            nodo = rotacionIzq(nodo); 
        } else if (balance <= -2 && serie.getNombre().compareTo(nodo.getIzq().getDato().getNombre()) > 0) {
            nodo.setIzq(rotacionIzq(nodo.getIzq()));
            nodo = rotacionDer(nodo); 
        }
        
        return nodo;
    }

    private int determinarAltura(NodoAvlObjeto<Serie> nodo) {
        if (nodo == null) return 0;
        return nodo.getAltura();
    }

    private NodoAvlObjeto<Serie> rotacionIzq(NodoAvlObjeto<Serie> x) {
        NodoAvlObjeto<Serie> y = x.getDer();
        NodoAvlObjeto<Serie> z = y.getIzq();

        y.setIzq(x);
        x.setDer(z);

        x.setAltura(1 + Math.max(determinarAltura(x.getIzq()), determinarAltura(x.getDer())));
        y.setAltura(1 + Math.max(determinarAltura(y.getIzq()), determinarAltura(y.getDer())));

        return y;
    }

    private NodoAvlObjeto<Serie> rotacionDer(NodoAvlObjeto<Serie> y) {
        NodoAvlObjeto<Serie> x = y.getIzq();
        NodoAvlObjeto<Serie> z = x.getDer();

        x.setDer(y);
        y.setIzq(z);

        y.setAltura(1 + Math.max(determinarAltura(y.getIzq()), determinarAltura(y.getDer())));
        x.setAltura(1 + Math.max(determinarAltura(x.getIzq()), determinarAltura(x.getDer())));

        return x;
    }
    
public void cargarDesdeCsvPorNombre(String rutaArchivo) throws Exception {
    try (BufferedReader br = new BufferedReader(new FileReader(rutaArchivo))) {
        String linea;
        Serie serieActual = null; // Almacenamos la serie actual
        Temporada temporadaActual = null; // Almacenamos la temporada actual

        while ((linea = br.readLine()) != null) {
            String[] datos = linea.split(",");

            // Asegúrate de que los datos del CSV sean correctos
            String nombreSerie = datos[0].trim();
            String generoSerie = datos[1].trim();
            int numeroTemporada = Integer.parseInt(datos[2].trim());
            int numeroEpisodio = Integer.parseInt(datos[3].trim()); // Número del episodio
            String tituloEpisodio = datos[4].trim();
            int duracionEpisodio = Integer.parseInt(datos[5].trim());
            double ratingEpisodio = Double.parseDouble(datos[6].trim());

            // Verifica si la serie ya existe, si no la crea
            if (serieActual == null || !serieActual.getNombre().equals(nombreSerie)) {
                // Crear nueva serie y añadirla al árbol
                serieActual = new Serie(nombreSerie, generoSerie);
                insertarPorNombre(serieActual); // Inserta en el árbol por nombre
            }

            // Verifica si la temporada ya existe, si no la crea
            if (temporadaActual == null || temporadaActual.getNumero() != numeroTemporada) {
                // Crear nueva temporada y añadirla a la serie
                temporadaActual = new Temporada(numeroTemporada);
                serieActual.agregarTemporada(temporadaActual); // Añadir la temporada a la serie
            }

            // Crear el episodio y añadirlo a la temporada
            Episodio episodio = new Episodio(numeroEpisodio, tituloEpisodio, duracionEpisodio, ratingEpisodio);
            try {
                temporadaActual.agregarEpisodio(episodio);// Añadir el episodio a la temporada
            } catch (Exception e) {
                System.err.println("Error al agregar el episodio " + episodio.getNumero() + ": " + e.getMessage());
            }
           
        }
    }
}


    
    public void imprimirEnOrden() {
        imprimirEnOrden(raiz);
    }
    
    private void imprimirEnOrden(NodoAvlObjeto<Serie> nodo) {
        if (nodo != null) {
            imprimirEnOrden(nodo.getIzq());
            System.out.println(nodo.getDato());
            imprimirEnOrden(nodo.getDer());
        }
    }
    
    public Serie buscarPorNombre(String nombreSerie) throws Exception {
        int[] contador = {0};  // Usamos un arreglo para poder pasar la referencia en las llamadas recursivas
        Serie resultado = buscarPorNombreRecursivo(raiz, nombreSerie, contador);
        System.out.println("Nodos recorridos: " + contador[0]);  // Imprimir el número de nodos recorridos
        return resultado;
    }
    
    private Serie buscarPorNombreRecursivo(NodoAvlObjeto<Serie> nodo, String nombreSerie, int[] contador) throws Exception {
        if (nodo == null) {
            throw new Exception("La serie con el nombre " + nombreSerie + " no se encontró.");
        }
        contador[0]++;  // Incrementamos el contador cada vez que se recorre un nodo
        int comparacion = nombreSerie.compareTo(nodo.getDato().getNombre());
        
        if (comparacion == 0) {
            return nodo.getDato();  // Se encontró la serie
        }
        
        if (comparacion < 0) {
            return buscarPorNombreRecursivo(nodo.getIzq(), nombreSerie, contador);  // Buscar en el subárbol izquierdo
        }
        return buscarPorNombreRecursivo(nodo.getDer(), nombreSerie, contador);  // Buscar en el subárbol derecho
    }
    
    public Temporada[] obtenerTemporadasPorNombreSerie(String nombreSerie) throws Exception {
    Serie serie = buscarPorNombre(nombreSerie);  // Método para buscar la serie por nombre
    if (serie == null) {
        throw new Exception("Serie no encontrada");
    }

    // Obtener la lista de temporadas de la serie
    ListaLigada temporadasLigada = serie.getTemporadas();
    
    // Crear un arreglo para guardar las temporadas
    Temporada[] temporadasArreglo = new Temporada[temporadasLigada.tamaño()];
    
    // Transferir las temporadas de la lista ligada al arreglo
    for (int i = 0; i < temporadasLigada.tamaño(); i++) {
        temporadasArreglo[i] = (Temporada) temporadasLigada.obtener(i);
    }

    // Ordenar el arreglo de temporadas
    temporadasArreglo = quick(temporadasArreglo);  // Ordena el arreglo de temporadas
    
    return temporadasArreglo;
    }


public Temporada[] quick(Temporada[] temporadas) {
    quicksort(temporadas, 0, temporadas.length - 1); // Llama al método recursivo de Quicksort
    return temporadas; // Retorna el arreglo ordenado
}

public void quicksort(Temporada[] temporadas, int inicio, int fin) {
    int posI = inicio;
    int posD = fin;
    Temporada pivote = temporadas[(posI + posD) / 2]; // Elegir el pivote como el elemento medio

    do {
        // Encuentra un elemento mayor que el pivote desde la izquierda
        while (temporadas[posI].getNumero() < pivote.getNumero()) {
            posI++;
        }
        // Encuentra un elemento menor que el pivote desde la derecha
        while (temporadas[posD].getNumero() > pivote.getNumero()) {
            posD--;
        }
        // Intercambia los elementos si las posiciones no se cruzan
        if (posI <= posD) {
            intercambiar(temporadas, posI, posD);
            posI++;
            posD--;
        }
    } while (posI <= posD);

    // Llamadas recursivas para las particiones izquierda y derecha
    if (inicio < posD) {
        quicksort(temporadas, inicio, posD);
    }
    if (fin > posI) {
        quicksort(temporadas, posI, fin);
    }
}

public void intercambiar(Temporada[] temporadas, int i, int j) {
    Temporada temp = temporadas[i];
    temporadas[i] = temporadas[j];
    temporadas[j] = temp;
}

public Episodio[] obtenerEpisodiosPorNombreSerieYTemporada(String nombreSerie, int numeroTemporada) throws Exception {
    Serie serie = buscarPorNombre(nombreSerie);  // Método para buscar la serie por nombre
    if (serie == null) {
        throw new Exception("Serie no encontrada");
    }

    // Buscar la temporada por número usando el método obtenerTemporada de la clase Serie
    Temporada temporada = serie.obtenerTemporada(numeroTemporada);
    if (temporada == null) {
        throw new Exception("Temporada no encontrada");
    }

    // Obtener la lista de episodios de la temporada
    ListaLigada episodiosLigada = temporada.getEpisodios();
    
    // Crear un arreglo para guardar los episodios
    Episodio[] episodiosArreglo = new Episodio[episodiosLigada.tamaño()];
    
    // Transferir los episodios de la lista ligada al arreglo
    for (int i = 0; i < episodiosLigada.tamaño(); i++) {
        episodiosArreglo[i] = (Episodio) episodiosLigada.obtener(i);
    }

    // Ordenar el arreglo de episodios
    episodiosArreglo = ordenarEpisodiosPorNumero(episodiosArreglo);  // Ordena el arreglo de episodios
    
    return episodiosArreglo;
}

public Episodio[] ordenarEpisodiosPorNumero(Episodio[] episodios) {
    ordenarPorQuick(episodios, 0, episodios.length - 1); // Llama al método recursivo para ordenar
    return episodios; // Retorna el arreglo ordenado
}

public void ordenarPorQuick(Episodio[] episodios, int inicio, int fin) {
    int posI = inicio;
    int posD = fin;
    Episodio pivote = episodios[(posI + posD) / 2]; // Elegir el pivote como el elemento medio

    do {
        // Encuentra un episodio con un número mayor que el pivote desde la izquierda
        while (episodios[posI].getNumero() < pivote.getNumero()) {
            posI++;
        }
        // Encuentra un episodio con un número menor que el pivote desde la derecha
        while (episodios[posD].getNumero() > pivote.getNumero()) {
            posD--;
        }
        // Intercambia los elementos si las posiciones no se cruzan
        if (posI <= posD) {
            intercambiarEpisodios(episodios, posI, posD);
            posI++;
            posD--;
        }
    } while (posI <= posD);

    // Llamadas recursivas para las particiones izquierda y derecha
    if (inicio < posD) {
        ordenarPorQuick(episodios, inicio, posD);
    }
    if (fin > posI) {
        ordenarPorQuick(episodios, posI, fin);
    }
}

public void intercambiarEpisodios(Episodio[] episodios, int i, int j) {
    Episodio temp = episodios[i];
    episodios[i] = episodios[j];
    episodios[j] = temp;
}
    
    public void agregarSerieYGenero(String nombreSerie, String generoSerie) {
        Serie nuevaSerie = new Serie(nombreSerie, generoSerie);
        insertarPorNombre(nuevaSerie); // Inserta la serie por nombre
    }

    // Método para agregar un episodio a la temporada correspondiente dentro de la serie correspondiente
    public void agregarEpisodioACorrespondiente(String nombreSerie, int numeroTemporada, int numeroEpisodio, String tituloEpisodio, int duracionEpisodio, double ratingEpisodio) throws Exception {
        Serie serie = buscarPorNombre(nombreSerie); // Buscar la serie por nombre
        Temporada temporada = serie.obtenerTemporada(numeroTemporada); // Obtener la temporada correspondiente

        if (temporada == null) {
            // Si la temporada no existe, creamos una nueva
            temporada = new Temporada(numeroTemporada);
            serie.agregarTemporada(temporada);
        }

        // Crear el episodio y añadirlo a la temporada
        Episodio episodio = new Episodio(numeroEpisodio, tituloEpisodio, duracionEpisodio, ratingEpisodio);
        temporada.agregarEpisodio(episodio); // Añadir el episodio a la temporada
    }
    
    public void eliminarPorNombre(String nombreSerie) {
    this.raiz = eliminarPorNombreRecursivo(raiz, nombreSerie);
}

private NodoAvlObjeto<Serie> eliminarPorNombreRecursivo(NodoAvlObjeto<Serie> nodo, String nombreSerie) {
    if (nodo == null) {
        return nodo;  // Si el nodo es nulo, simplemente regresamos.
    }

    int comparacion = nombreSerie.compareTo(nodo.getDato().getNombre());

    // Si el nombre es menor, buscar en el subárbol izquierdo.
    if (comparacion < 0) {
        nodo.setIzq(eliminarPorNombreRecursivo(nodo.getIzq(), nombreSerie));
    }
    // Si el nombre es mayor, buscar en el subárbol derecho.
    else if (comparacion > 0) {
        nodo.setDer(eliminarPorNombreRecursivo(nodo.getDer(), nombreSerie));
    }
    // Si encontramos el nodo a eliminar.
    else {
        // Caso 1: El nodo tiene un solo hijo o no tiene hijos.
        if (nodo.getIzq() == null) {
            return nodo.getDer();  // Si no tiene hijo izquierdo, lo reemplazamos con su hijo derecho.
        } else if (nodo.getDer() == null) {
            return nodo.getIzq();  // Si no tiene hijo derecho, lo reemplazamos con su hijo izquierdo.
        }

        // Caso 2: El nodo tiene dos hijos.
        // Encontrar el sucesor inorden (el mínimo nodo del subárbol derecho).
        NodoAvlObjeto<Serie> sucesor = obtenerMinimoNodo(nodo.getDer());

        // Reemplazar el nodo por el sucesor.
        nodo.setDato(sucesor.getDato());

        // Eliminar el sucesor en el subárbol derecho.
        nodo.setDer(eliminarPorNombreRecursivo(nodo.getDer(), sucesor.getDato().getNombre()));
    }

    // Actualizar la altura del nodo actual y balancear el árbol.
    nodo.setAltura(1 + Math.max(determinarAltura(nodo.getIzq()), determinarAltura(nodo.getDer())));
    int balance = determinarAltura(nodo.getDer()) - determinarAltura(nodo.getIzq());

    // Rotaciones para balancear el árbol después de la eliminación.
    if (balance >= 2 && determinarAltura(nodo.getDer().getDer()) >= determinarAltura(nodo.getDer().getIzq())) {
        return rotacionIzq(nodo);  // Rotación simple izquierda.
    }
    if (balance <= -2 && determinarAltura(nodo.getIzq().getIzq()) >= determinarAltura(nodo.getIzq().getDer())) {
        return rotacionDer(nodo);  // Rotación simple derecha.
    }
    if (balance >= 2) {
        nodo.setDer(rotacionDer(nodo.getDer()));  // Rotación doble derecha-izquierda.
        return rotacionIzq(nodo);
    }
    if (balance <= -2) {
        nodo.setIzq(rotacionIzq(nodo.getIzq()));  // Rotación doble izquierda-derecha.
        return rotacionDer(nodo);
    }

    return nodo;  // El árbol está balanceado.
}

// Método para obtener el nodo con el valor mínimo en el subárbol derecho (sucesor inorden).
private NodoAvlObjeto<Serie> obtenerMinimoNodo(NodoAvlObjeto<Serie> nodo) {
    NodoAvlObjeto<Serie> actual = nodo;
    while (actual.getIzq() != null) {
        actual = actual.getIzq();  // Ir a la izquierda hasta encontrar el nodo más pequeño.
    }
    return actual;
}

public Temporada obtenerMejorTemporadaPorCalificacion(String nombreSerie) throws Exception {
    Serie serie = buscarPorNombre(nombreSerie);
    if (serie == null) {
        throw new Exception("Serie no encontrada");
    }

    ListaLigada temporadasLigada = serie.getTemporadas();
    if (temporadasLigada == null || temporadasLigada.tamaño() == 0) {
        throw new Exception("No hay temporadas disponibles para la serie");
    }

    Temporada mejorTemporada = null;
    double mejorCalificacionPromedio = 0;

    // Itera sobre las temporadas y calcula la calificación promedio
    for (int i = 0; i < temporadasLigada.tamaño(); i++) {
        Temporada temporada = (Temporada) temporadasLigada.obtener(i);
        double calificacionPromedio = calcularCalificacionPromedio(temporada);

        if (calificacionPromedio > mejorCalificacionPromedio) {
            mejorCalificacionPromedio = calificacionPromedio;
            mejorTemporada = temporada;
        }
    }

    return mejorTemporada;
}

public double calcularCalificacionPromedio(Temporada temporada) throws Exception{
    ListaLigada episodios = temporada.getEpisodios();
    if (episodios == null || episodios.tamaño() == 0) {
        return 0;
    }

    double sumaCalificaciones = 0;
    for (int i = 0; i < episodios.tamaño(); i++) {
        Episodio episodio = (Episodio) episodios.obtener(i);
        sumaCalificaciones += episodio.getRating();
    }

    return sumaCalificaciones / episodios.tamaño();
}

 public void insertarSeriesEnColaYImprimir(String[] nombresSeries) throws Exception {
        ColaLigada<Serie> cola = new ColaLigada<>();  // Creamos una nueva ColaLigada para series

        // Buscar cada serie por nombre e insertarla en la cola
        for (String nombreSerie : nombresSeries) {
            Serie serie = buscarPorNombre(nombreSerie);  // Buscar la serie en el árbol AVL
            if (serie != null) {
                System.out.println("Serie encontrada: " + serie.getNombre());
                cola.agregar(serie);  // Insertar la serie en la cola
            } else {
                System.out.println("Serie no encontrada: " + nombreSerie);
            }
        }

        // Imprimir las series de la cola
        System.out.println("Series en la cola:");
        while (!cola.estaVacia()) {
            try {
                Serie serie = cola.eliminar();  // Extraer la serie de la cola
                System.out.println(serie);  // Imprimir la serie
            } catch (Exception e) {
                System.out.println("Error al eliminar de la cola: " + e.getMessage());
            }
        }
    }


    
}


public class Grafo {
    private NodoGrafo primero;
    private NodoGrafo ultimo;

    public Grafo() {
        this.primero = null;
        this.ultimo = null;
    }

    public boolean estaVacio() {
        return this.primero == null && this.ultimo == null;
    }

    public boolean existeVertice(Serie serie) {
        if (estaVacio())
            return false;
        NodoGrafo actual = primero;
        while (actual != null) {
            if (actual.getDato().equals(serie))
                return true;
            actual = actual.getSiguiente();
        }
        return false;
    }

    public boolean agregarArista(Serie origen, Serie destino) {
        if (!existeVertice(origen) || !existeVertice(destino)) {
            System.out.println("No se puede agregar la arista");
            return false;
        }

        NodoGrafo actual = primero;
        while (!actual.getDato().equals(origen)) {
            actual = actual.getSiguiente();
        }
        actual.getListaAdyacencia().agregarUltimo(destino); // Usamos el método agregarUltimo de ListaLigada
        return true;
    }

    public boolean agregarNodo(Serie serie) {
        if (existeVertice(serie))
            return false;
        NodoGrafo nodo = new NodoGrafo(serie);
        if (estaVacio()) {
            this.primero = nodo;
            this.ultimo = nodo;
            return true;
        }
        this.ultimo.setSiguiente(nodo);
        this.ultimo = nodo;
        return true;
    }

    // Método para obtener las series relacionadas con una serie dada
    public void obtenerSeriesRelacionadas(String nombreSerie) {
        // Buscar el nodo que contiene la serie en el grafo
        NodoGrafo nodoSerie = buscarNodoEnGrafo(nombreSerie);
        if (nodoSerie != null) {
            // Imprimir las series relacionadas (adyacentes)
            System.out.println("Series relacionadas con " + nombreSerie + ": ");
            System.out.println(imprimirAdyacencias(nodoSerie.getListaAdyacencia()));
        } else {
            System.out.println("La serie no está en el grafo.");
        }
    }

    // Método para buscar un nodo por nombre de serie en el grafo
    private NodoGrafo buscarNodoEnGrafo(String nombreSerie) {
        NodoGrafo actual = primero;
        while (actual != null) {
            if (actual.getDato().getNombre().equals(nombreSerie)) {
                return actual; // Nodo encontrado
            }
            actual = actual.getSiguiente();
        }
        return null; // Nodo no encontrado
    }

    // Método para agregar una relación entre dos series (agregar arista)
    public boolean agregarRelacion(String nombreSerie1, String nombreSerie2) {
        NodoGrafo nodoSerie1 = buscarNodoEnGrafo(nombreSerie1);
        NodoGrafo nodoSerie2 = buscarNodoEnGrafo(nombreSerie2);

        if (nodoSerie1 != null && nodoSerie2 != null) {
            // Si ambas series existen, agregar la relación (arista)
            nodoSerie1.getListaAdyacencia().agregarUltimo(nodoSerie2.getDato());
            return true;
        } else {
            System.out.println("Una o ambas series no existen en el grafo.");
            return false;
        }
    }

    @Override
    public String toString() {
        StringBuilder cadena = new StringBuilder();
        NodoGrafo actual = primero;
        while (actual != null) {
            cadena.append(actual.getDato().toString()).append("->").append(imprimirAdyacencias(actual.getListaAdyacencia())).append("\n");
            actual = actual.getSiguiente();
        }
        return cadena.toString();
    }

    private String imprimirAdyacencias(ListaLigada<Serie> lista) {
        StringBuilder adyacencias = new StringBuilder();
        try {
            // Verificar si la lista está vacía antes de intentar obtener el primer nodo
            if (lista.estaVacia()) {
                return "No hay adyacencias"; // Devuelve un mensaje si la lista está vacía
            }
            
            Nodo<Serie> actual = lista.obtenerNodo(0);  // Obtiene el primer nodo
            while (actual != null) {
                adyacencias.append(actual.getValor().toString()).append(", ");
                actual = actual.getSiguiente();
            }
            // Eliminar la última coma y espacio
            if (adyacencias.length() > 0) {
                adyacencias.setLength(adyacencias.length() - 2);
            }
        } catch (Exception e) {
            // Si se lanza una excepción (por ejemplo, índice fuera de rango), se captura y muestra un mensaje de error
            adyacencias.append("Error al obtener adyacencias: ").append(e.getMessage());
        }
        return adyacencias.toString();
    }
}


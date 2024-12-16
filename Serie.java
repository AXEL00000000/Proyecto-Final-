public class Serie {
    private String nombre;
    private String genero;
    private ListaLigada temporadas; // Usamos ListaLigada en lugar de LinkedList

    // Constructor
    public Serie(String nombre, String genero) {
        this.nombre = nombre;
        this.genero = genero;
        this.temporadas = new ListaLigada(); // Inicializamos con ListaLigada
    }

    // Getters y Setters
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public ListaLigada getTemporadas() {
        return temporadas;
    }
    
        public void agregarTemporada(Temporada temporada)  {
        temporadas.agregarUltimo(temporada); // Agrega la temporada 
    }

    public Temporada obtenerTemporada(int numero) throws Exception {
        for (int i = 0; i < temporadas.tamaño(); i++) {
            Temporada temporada = (Temporada) temporadas.obtener(i); // Cast necesario
            if (temporada.getNumero() == numero) {
                return temporada;
            }
        }
        return null; // Si no se encuentra la temporada
    }

    public int totalEpisodios() throws Exception {
        int total = 0;
        for (int i = 0; i < temporadas.tamaño(); i++) {
            Temporada temporada = (Temporada) temporadas.obtener(i);
            total += temporada.getEpisodios().tamaño();
        }
        return total;
    }

    public double ratingPromedio() throws Exception {
        double suma = 0;
        int contador = 0;
        for (int i = 0; i < temporadas.tamaño(); i++) {
            Temporada temporada = (Temporada) temporadas.obtener(i);
            for (int j = 0; j < temporada.getEpisodios().tamaño(); j++) {
                Episodio episodio = (Episodio) temporada.getEpisodios().obtener(j);
                suma += episodio.getRating();
                contador++;
            }
        }
        return contador == 0 ? 0 : suma / contador;
    }

   @Override
public String toString() {
    try {
        // Llamamos a los métodos que calculan el total de episodios y el rating promedio
        int totalEpisodios = totalEpisodios();  // Llama al método totalEpisodios()
        double ratingPromedio = ratingPromedio();  // Llama al método ratingPromedio()

        // Formateamos el resultado para incluir estos valores en el toString
        return "Serie: " + nombre + " (" + genero + "), Temporadas: " + temporadas.tamaño() +
               ", Total Episodios: " + totalEpisodios +
               ", Rating Promedio: " + String.format("%.2f", ratingPromedio);
    } catch (Exception e) {
        return "Error al obtener la información de la serie: " + e.getMessage();
    }
}

}

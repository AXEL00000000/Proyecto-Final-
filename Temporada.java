public class Temporada {
    private int numero;
    private ListaLigada episodios; // Usamos ListaLigada en lugar de LinkedList

    // Constructor
    public Temporada(int numero) {
        this.numero = numero;
        this.episodios = new ListaLigada(); // Inicializamos con ListaLigada
    }

    // Getters y Setters
    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public ListaLigada getEpisodios() {
        return episodios;
    }

    // Métodos
    public void agregarEpisodio(Episodio episodio) throws Exception  {
        // Verifica si el episodio ya existe utilizando obtenerEpisodio
        if (obtenerEpisodio(episodio.getNumero()) != null) {
            System.out.println("El episodio " + episodio.getNumero() + " ya existe en la temporada " + this.numero);
            return; // No agrega el episodio
        }
        episodios.agregarUltimo(episodio); // Método de ListaLigada
    }

    public Episodio obtenerEpisodio(int numero) throws Exception {
        for (int i = 0; i < episodios.tamaño(); i++) {
            Episodio episodio = (Episodio) episodios.obtener(i); // Cast necesario
            if (episodio.getNumero() == numero) {
                return episodio;
            }
        }
        return null; // Si no se encuentra el episodio
    }

    @Override
    public String toString() {
        return "Temporada " + numero + " con " + episodios.tamaño() + " episodios.";
    }
}

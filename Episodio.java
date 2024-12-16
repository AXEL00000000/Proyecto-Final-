public class Episodio {
    private int numero;
    private String titulo;
    private int duracion; // Duración en minutos
    private double rating;

    // Constructor
    public Episodio(int numero, String titulo, int duracion, double rating) {
        this.numero = numero;
        this.titulo = titulo;
        this.duracion = duracion;
        this.rating = rating;
    }

    // Getters y Setters
    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public int getDuracion() {
        return duracion;
    }

    public void setDuracion(int duracion) {
        this.duracion = duracion;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    @Override
    public String toString() {
        return "E" + numero + ": " + titulo + " (" + duracion + " min, Rating: " + rating + ")";
    }
}

public class NodoGrafo {
    private Serie dato; // Nodo ahora contiene un objeto Serie
    private ListaLigada<Serie> listaAdyacencia; // Usamos la lista ligada para las adyacencias
    private NodoGrafo siguiente;

    public NodoGrafo(Serie dato) {
        this.dato = dato;
        this.listaAdyacencia = new ListaLigada<>(); // Usamos ListaLigada en lugar de ArrayList
        this.siguiente = null;
    }

    public Serie getDato() {
        return dato;
    }

    public void setDato(Serie dato) {
        this.dato = dato;
    }

    public ListaLigada<Serie> getListaAdyacencia() {
        return listaAdyacencia;
    }

    public void setListaAdyacencia(ListaLigada<Serie> listaAdyacencia) {
        this.listaAdyacencia = listaAdyacencia;
    }

    public NodoGrafo getSiguiente() {
        return siguiente;
    }

    public void setSiguiente(NodoGrafo siguiente) {
        this.siguiente = siguiente;
    }
}

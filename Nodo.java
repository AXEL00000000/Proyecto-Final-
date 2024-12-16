public class Nodo<E> {
    private E valor; // Ahora es genérico
    private Nodo<E> siguiente;

    public Nodo(E valor) {
        this.valor = valor;
        this.siguiente = null;
    }

    public E getValor() {
        return this.valor;
    }

    public void setValor(E valor) {
        this.valor = valor;
    }

    public Nodo<E> getSiguiente() {
        return this.siguiente;
    }

    public void setSiguiente(Nodo<E> siguiente) {
        this.siguiente = siguiente;
    }
}
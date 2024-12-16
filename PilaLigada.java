public class PilaLigada<E> {
    private Nodo<E> tope;

    public PilaLigada() {
        this.tope = null;
    }

    public boolean estaVacia() {
        return tope == null;
    }

    public boolean push(E valor) {
        Nodo<E> nuevoNodo = new Nodo<>(valor);
        nuevoNodo.setSiguiente(tope);
        tope = nuevoNodo;
        return true;
    }

    public E pop() throws Exception {
        if (estaVacia()) {
            throw new Exception("Pila vacía");
        }
        E valor = tope.getValor();
        tope = tope.getSiguiente();
        return valor;
    }

    public E verTope() throws Exception {
        if (estaVacia()) {
            throw new Exception("Pila vacía");
        }
        return tope.getValor();
    }

    public int size() {
        int contador = 0;
        Nodo<E> actual = tope;
        while (actual != null) {
            contador++;
            actual = actual.getSiguiente();
        }
        return contador;
    }
}

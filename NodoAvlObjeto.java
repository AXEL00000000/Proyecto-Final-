public class NodoAvlObjeto<T> {
    private T dato;               // El dato que almacena el nodo (en tu caso, el objeto Cancion o Serie)
    private NodoAvlObjeto<T> izq; // Referencia al subárbol izquierdo
    private NodoAvlObjeto<T> der; // Referencia al subárbol derecho
    private int altura;           // Altura del nodo en el árbol para mantener el balance

    // Constructor para crear un nodo con el dato
    public NodoAvlObjeto(T dato) {
        this.dato = dato;
        this.izq = null;
        this.der = null;
        this.altura = 1;  // Un nodo recién creado tiene altura 1
    }

    // Getters y setters para el dato y los hijos
    public T getDato() {
        return dato;
    }
    
    public void setDato(T dato) {
        this.dato = dato;
    }

    public NodoAvlObjeto<T> getIzq() {
        return izq;
    }

    public void setIzq(NodoAvlObjeto<T> izq) {
        this.izq = izq;
    }

    public NodoAvlObjeto<T> getDer() {
        return der;
    }

    public void setDer(NodoAvlObjeto<T> der) {
        this.der = der;
    }

    public int getAltura() {
        return altura;
    }

    public void setAltura(int altura) {
        this.altura = altura;
    }
}
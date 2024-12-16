public class ColaLigada<E> {
    private Nodo<E> primero;
    private Nodo<E> ultimo;

    // Constructor para inicializar la cola vacía.
    public ColaLigada() {
        this.primero = null;
        this.ultimo = null;
    }

    // Método que verifica si la cola está vacía.
    public boolean estaVacia() {
        return this.primero == null;
    }

    // Método para agregar un nuevo valor al final de la cola.
    public boolean agregar(E valor) {
        Nodo<E> nuevo = new Nodo<>(valor);  // Crear un nuevo nodo con el valor.
        if (estaVacia()) {
            // Si la cola está vacía, el nuevo nodo es tanto el primero como el último.
            this.primero = nuevo;
            this.ultimo = nuevo;
            return true;
        }
        // Si no está vacía, añadimos el nuevo nodo al final.
        this.ultimo.setSiguiente(nuevo);
        this.ultimo = nuevo;  // Actualizamos el último nodo.
        return true;
    }

    // Método para eliminar y devolver el primer valor de la cola.
    public E eliminar() throws Exception {
        if (estaVacia()) {
            throw new Exception("La cola está vacía, no se puede eliminar.");
        }
        E auxValor = primero.getValor();  // Guardamos el valor del primer nodo.
        this.primero = primero.getSiguiente();  // Actualizamos el primer nodo al siguiente.
        // Si la cola queda vacía después de eliminar, el último nodo también debe ser null.
        if (this.primero == null) {
            this.ultimo = null;
        }
        return auxValor;  // Retornamos el valor eliminado.
    }

    // Método para ver el primer valor sin eliminarlo.
    public E verPrimero() throws Exception {
        if (estaVacia()) {
            throw new Exception("La cola está vacía, no hay primer valor.");
        }
        return primero.getValor();  // Devolvemos el valor del primer nodo.
    }

    // Método para ver el último valor sin eliminarlo.
    public E verUltimo() throws Exception {
        if (estaVacia()) {
            throw new Exception("La cola está vacía, no hay último valor.");
        }
        return ultimo.getValor();  // Devolvemos el valor del último nodo.
    }
}

// Nodo genérico para almacenar cualquier tipo de objeto.
class Nodo<E> {
    private E valor;
    private Nodo<E> siguiente;

    // Constructor del nodo que toma un valor.
    public Nodo(E valor) {
        this.valor = valor;
        this.siguiente = null;
    }

    // Getters y setters
    public E getValor() {
        return valor;
    }

    public void setValor(E valor) {
        this.valor = valor;
    }

    public Nodo<E> getSiguiente() {
        return siguiente;
    }

    public void setSiguiente(Nodo<E> siguiente) {
        this.siguiente = siguiente;
    }
}

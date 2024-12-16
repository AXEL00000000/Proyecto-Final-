public class ListaLigada<E> {
    private Nodo<E> primero;
    private Nodo<E> ultimo;

    public ListaLigada() {
        this.primero = null;
        this.ultimo = null;
    }

    public boolean estaVacia() {
        return primero == null && ultimo == null;
    }

    public boolean agregarInicio(E valor) {
        Nodo<E> nuevo = new Nodo<>(valor);
        if (estaVacia()) {
            primero = nuevo;
            ultimo = nuevo;
            return true;
        }
        nuevo.setSiguiente(primero);
        primero = nuevo;
        return true;
    }

    public boolean agregarUltimo(E valor) {
        Nodo<E> nuevo = new Nodo<>(valor);
        if (estaVacia()) {
            primero = nuevo;
            ultimo = nuevo;
            return true;
        }
        ultimo.setSiguiente(nuevo);
        ultimo = nuevo;
        return true;
    }

    public E eliminarInicio() throws Exception {
        if (estaVacia()) {
            throw new Exception("La lista está vacía");
        }
        E auxValor = primero.getValor();
        primero = primero.getSiguiente();
        if (primero == null) {
            ultimo = null; // Si se eliminó el único elemento
        }
        return auxValor;
    }

    public E eliminarFin() throws Exception {
        if (estaVacia()) {
            throw new Exception("La lista está vacía");
        }

        if (primero == ultimo) { // Caso especial: solo hay un elemento
            E auxValor = primero.getValor();
            primero = null;
            ultimo = null;
            return auxValor;
        }

        Nodo<E> nodoAux = primero;
        while (nodoAux.getSiguiente() != null && nodoAux.getSiguiente().getSiguiente() != null) {
            nodoAux = nodoAux.getSiguiente();
        }

        E auxValor = ultimo.getValor();
        ultimo = nodoAux;
        ultimo.setSiguiente(null);
        return auxValor;
    }

    public void eliminar(E valor) throws Exception {
        if (estaVacia()) {
            throw new Exception("La lista está vacía");
        }

        Nodo<E> actual = primero;
        Nodo<E> previo = null;

        // Eliminar nodos al inicio con el valor especificado
        while (actual != null && actual.getValor().equals(valor)) {
            primero = actual.getSiguiente();
            actual = primero;
        }

        // Si la lista quedó vacía
        if (primero == null) {
            ultimo = null;
            return;
        }

        // Eliminar nodos intermedios y al final con el valor especificado
        actual = primero;
        while (actual != null) {
            if (actual.getValor().equals(valor)) {
                previo.setSiguiente(actual.getSiguiente());
                if (actual.getSiguiente() == null) {
                    ultimo = previo; // Actualizamos el último nodo
                }
            } else {
                previo = actual;
            }
            actual = actual.getSiguiente();
        }
    }
    
    public Nodo obtenerNodo(int indice) throws Exception {
        if (indice < 0 || estaVacia()) {
            throw new Exception("Índice fuera de rango o lista vacía");
        }
        Nodo actual = primero;
        int contador = 0;
        while (actual != null) {
            if (contador == indice) {
                return actual;
            }
            actual = actual.getSiguiente();
            contador++;
        }
        throw new Exception("Índice fuera de rango");
    }
    
    public Object obtener(int indice) throws Exception {
        return obtenerNodo(indice).getValor();
    }
    
    public int tamaño() {
        int contador = 0;
        Nodo actual = primero;
        while (actual != null) {
            contador++;
            actual = actual.getSiguiente();
        }
        return contador;
    }

    // Método para imprimir la lista (útil para depuración)
    public void imprimir() {
        Nodo<E> actual = primero;
        while (actual != null) {
            System.out.println(actual.getValor());
            actual = actual.getSiguiente();
        }
    }
}
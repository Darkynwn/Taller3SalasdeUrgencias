public class ListNode {
    Object dato;
    ListNode siguiente;

    public ListNode(Object dato) {
        this.dato = dato;
        this.siguiente = null;
    }

    public Object getDato() {
        return dato;
    }

    public ListNode getSiguiente() {
        return siguiente;
    }

    @Override
    public String toString() {
        return String.valueOf(dato);
    }
}

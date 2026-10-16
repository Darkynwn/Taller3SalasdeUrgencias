import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Random;

public class MiLista implements ListInterface {

    ListNode cabeza = null;

    @Override
    public boolean isEmpty() { return cabeza == null; }

    @Override
    public int getSize() {
        int contador = 0;
        ListNode actual = cabeza;
        while (actual != null) {
            contador++;
            actual = actual.siguiente;
        }
        return contador;
    }

    @Override
    public void clear() { cabeza = null; }

    @Override
    public Object getHead() {
        return cabeza == null ? null : cabeza.dato;
    }

    @Override
    public Object getTail() {
        if (cabeza == null) return null;
        ListNode actual = cabeza;
        while (actual.siguiente != null) actual = actual.siguiente;
        return actual.dato;
    }

    @Override
    public Object get(ListNode node) {
        if (node == null) return null;
        ListNode actual = cabeza;
        while (actual != null) {
            if (actual == node) return actual.dato;
            actual = actual.siguiente;
        }
        return null;
    }

    @Override
    public Object search(Object object) {
        ListNode actual = cabeza;
        while (actual != null) {
            if (Objects.equals(actual.dato, object)) return actual.dato;
            actual = actual.siguiente;
        }
        return null;
    }

    @Override
    public boolean add(Object object) { return insertTail(object); }

    @Override
    public boolean insert(ListNode node, Object object) {
        if (node == null) return false;
        ListNode actual = cabeza;
        while (actual != null) {
            if (actual == node) {
                ListNode nuevo = new ListNode(object);
                nuevo.siguiente = actual.siguiente;
                actual.siguiente = nuevo;
                return true;
            }
            actual = actual.siguiente;
        }
        return false;
    }

    @Override
    public boolean insert(Object ob, Object object) {
        ListNode actual = cabeza;
        while (actual != null) {
            if (Objects.equals(actual.dato, ob)) return insert(actual, object);
            actual = actual.siguiente;
        }
        return false;
    }

    @Override
    public boolean insertHead(Object object) {
        ListNode nuevo = new ListNode(object);
        nuevo.siguiente = cabeza;
        cabeza = nuevo;
        return true;
    }

    @Override
    public boolean insertTail(Object object) {
        ListNode nuevo = new ListNode(object);
        if (cabeza == null) {
            cabeza = nuevo;
            return true;
        }
        ListNode actual = cabeza;
        while (actual.siguiente != null) actual = actual.siguiente;
        actual.siguiente = nuevo;
        return true;
    }

    @Override
    public boolean set(ListNode node, Object object) {
        if (node == null) return false;
        ListNode actual = cabeza;
        while (actual != null) {
            if (actual == node) {
                actual.dato = object;
                return true;
            }
            actual = actual.siguiente;
        }
        return false;
    }

    @Override
    public boolean remove(ListNode node) {
        if (cabeza == null || node == null) return false;
        if (cabeza == node) {
            cabeza = cabeza.siguiente;
            return true;
        }
        ListNode anterior = cabeza;
        while (anterior.siguiente != null) {
            if (anterior.siguiente == node) {
                anterior.siguiente = anterior.siguiente.siguiente;
                return true;
            }
            anterior = anterior.siguiente;
        }
        return false;
    }

    @Override
    public boolean contains(Object object) { return search(object) != null; }

    @Override
    public Iterator<ListNode> iterator() {
        return new Iterator<ListNode>() {
            private ListNode actual = cabeza;

            @Override
            public boolean hasNext() { return actual != null; }

            @Override
            public ListNode next() {
                if (!hasNext()) throw new NoSuchElementException();
                ListNode resultado = actual;
                actual = actual.siguiente;
                return resultado;
            }

            @Override
            public void remove() {
                throw new UnsupportedOperationException();
            }
        };
    }

    @Override
    public Object[] toArray() {
        Object[] arreglo = new Object[getSize()];
        ListNode actual = cabeza;
        int i = 0;
        while (actual != null) {
            arreglo[i++] = actual.dato;
            actual = actual.siguiente;
        }
        return arreglo;
    }

    @Override
    public Object[] toArray(Object[] object) {
        int tamano = getSize();
        Object[] resultado = object.length >= tamano ? object : new Object[tamano];
        ListNode actual = cabeza;
        int i = 0;
        while (actual != null) {
            resultado[i++] = actual.dato;
            actual = actual.siguiente;
        }
        if (resultado.length > tamano) resultado[tamano] = null;
        return resultado;
    }

    @Override
    public Object getBeforeTo() {
        return cabeza == null ? null : getBeforeTo(cabeza);
    }

    @Override
    public Object getBeforeTo(ListNode node) {
        if (node == null || cabeza == null || cabeza == node) return null;
        ListNode anterior = cabeza;
        while (anterior.siguiente != null) {
            if (anterior.siguiente == node) return anterior.dato;
            anterior = anterior.siguiente;
        }
        return null;
    }

    @Override
    public Object getNextTo() {
        return cabeza == null ? null : getNextTo(cabeza);
    }

    @Override
    public Object getNextTo(ListNode node) {
        if (node == null) return null;
        ListNode actual = cabeza;
        while (actual != null) {
            if (actual == node) return actual.siguiente == null ? null : actual.siguiente.dato;
            actual = actual.siguiente;
        }
        return null;
    }

    @Override
    public MiLista subList(ListNode from, ListNode to) {
        MiLista resultado = new MiLista();
        if (from == null || to == null) return resultado;

        ListNode actual = cabeza;
        boolean inicio = false;

        while (actual != null) {
            if (actual == from) inicio = true;
            if (inicio) resultado.insertTail(actual.dato);
            if (actual == to) return resultado;
            actual = actual.siguiente;
        }
        return new MiLista();
    }

    @Override
    public MiLista sortList() {
        MiLista resultado = new MiLista();
        ListNode actual = cabeza;
        while (actual != null) {
            insertarOrdenado(resultado, actual.dato);
            actual = actual.siguiente;
        }
        return resultado;
    }

    private void insertarOrdenado(MiLista lista, Object objeto) {
        if (lista.cabeza == null) {
            lista.insertHead(objeto);
            return;
        }

        ListNode anterior = null;
        ListNode actual = lista.cabeza;

        while (actual != null && comparar(actual.dato, objeto) <= 0) {
            anterior = actual;
            actual = actual.siguiente;
        }

        ListNode nuevo = new ListNode(objeto);
        nuevo.siguiente = actual;

        if (anterior == null) lista.cabeza = nuevo;
        else anterior.siguiente = nuevo;
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    private int comparar(Object a, Object b) {
        if (a == b) return 0;
        if (a == null) return -1;
        if (b == null) return 1;

        if (a instanceof Comparable && a.getClass().isInstance(b)) {
            return ((Comparable) a).compareTo(b);
        }

        return a.toString().compareToIgnoreCase(b.toString());
    }

    @Override
    public void shuffle() {
        int tamano = getSize();
        if (tamano < 2) return;

        Random random = new Random();

        for (int i = tamano - 1; i > 0; i--) {
            int j = random.nextInt(i + 1);
            ListNode nodoI = getNodeAt(i);
            ListNode nodoJ = getNodeAt(j);

            Object temporal = nodoI.dato;
            nodoI.dato = nodoJ.dato;
            nodoJ.dato = temporal;
        }
    }

    private ListNode getNodeAt(int indice) {
        if (indice < 0) return null;

        ListNode actual = cabeza;
        int posicion = 0;

        while (actual != null) {
            if (posicion == indice) return actual;
            posicion++;
            actual = actual.siguiente;
        }
        return null;
    }

    @Override
    public String toString() {
        StringBuilder texto = new StringBuilder("[");
        ListNode actual = cabeza;

        while (actual != null) {
            texto.append(actual.dato);
            if (actual.siguiente != null) texto.append(", ");
            actual = actual.siguiente;
        }

        texto.append("]");
        return texto.toString();
    }
}

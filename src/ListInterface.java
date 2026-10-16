import java.util.Iterator;

public interface ListInterface {
    public boolean isEmpty();
    public int getSize();
    public void clear();
    public Object getHead();
    public Object getTail();
    public Object get(ListNode node);
    public Object search(Object object);
    public boolean add(Object object);
    public boolean insert(ListNode node, Object object);
    public boolean insert(Object ob, Object object);
    public boolean insertHead(Object object);
    public boolean insertTail(Object object);
    public boolean set(ListNode node, Object object);
    public boolean remove(ListNode node);

    public boolean contains(Object object);
    public Iterator<ListNode> iterator();
    public Object[] toArray();
    public Object[] toArray(Object[] object);
    public Object getBeforeTo();
    public Object getBeforeTo(ListNode node);
    public Object getNextTo();
    public Object getNextTo(ListNode node);
    public MiLista subList(ListNode from, ListNode to);
    public MiLista sortList();
    public void shuffle();
}

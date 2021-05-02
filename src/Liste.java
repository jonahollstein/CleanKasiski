

public class Liste<E> {
    public class Node {
        E item;
        Node next;
    }

    private Node first;
    private Node last;
    public int N;

    public Liste() {
        first = last = new Node();
        N = 0;
    }

    public int size() {
        return N;
    }

    public boolean isEmpty() {
        return N == 0;
    }

    public E getItem(int index) {
        if (index < 1 || index > N) {
            throw new IndexOutOfBoundsException();
        }
        return prednode(index).next.item;
    }

    public void append(E item) {
        insertAt(N + 1, item);
    }

    public void insertAt(int index, E item) {
        if (index < 1 || index > N + 1) {
            throw new IndexOutOfBoundsException();
        }

        Node newNode = new Node();
        newNode.item = item;

        if (index == N + 1) {
            last.next = newNode;
            last = newNode;
        } else {
            Node pred = prednode(index);
            newNode.next = pred.next;
            pred.next = newNode;
        }

        N++;
    }

    public void delete(int index) {
        if (index < 1 || index > N) {
            throw new IndexOutOfBoundsException();
        }
        Node pred = prednode(index);
        pred.next = pred.next.next;
        N--;
    }

    public Node prednode(int index) {
        Node p = first;
        for (int i = 0; i < index - 1; i++) {
            p = p.next;
        }
        return p;
    }

    public String makeString() {
        StringBuilder s = new StringBuilder();
        for (Node p = first.next; p != null; p = p.next) {
            s.append(p.item).append(" ");
        }
        return s.toString();
    }

    public void concat(Liste<E> append) {
        if (append.N >= 1) {
            for (int i = 1; i <= append.size(); i++) {
                insertAt(N + 1, append.getItem(i));
            }
        }
    }
}


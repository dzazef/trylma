package models.client_server;

import java.io.Serializable;
import java.util.Iterator;
import java.util.List;

/**
 * Iterator po polach dla danego ruchu.
 */
public class MovePathIterator implements Iterator, Serializable {
    private List<Field> movePath;
    private int pos = 0;
    MovePathIterator(List<Field> movePath) {
        this.movePath=movePath;
    }
    @Override
    public boolean hasNext() {
        return (pos < movePath.size());
    }
    @Override
    public Object next() {
        return this.movePath.get(pos++);
    }
}

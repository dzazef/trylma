package serializable;



import java.io.Serializable;
import java.util.Iterator;
import java.util.List;

/**
 * Iterator class, an Iterator design patter embodiment, used in FieldsSet class.
 */
public class MovePathIterator implements Iterator, Serializable {
    private static final long serialVersionUID = -670142841088144675L;
    private List<Field> movePath;
    private int pos = 0;
    MovePathIterator(List<Field> movePath) {
        this.movePath=movePath;
    }
    public boolean hasNext() {
        return (pos < movePath.size());
    }
    public Object next() {
        return this.movePath.get(pos++);
    }
    public void remove() {

    }
}

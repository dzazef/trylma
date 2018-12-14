package models.client_server;

import serializable.Field;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Klasa obsługująca pojedynczy ruch.
 */
public class MovePath implements Serializable {
    private int index = 0;
    private List<Field> movePath = new ArrayList<>();
    public void addField(Field field) {
        this.movePath.add(field);
    }
    public void removeLast() {
        this.movePath.remove(this.movePath.size()-1);
    }
    public Iterator createIterator() {
        return new MovePathIterator(movePath);
    }
}

package models.client_server;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Klasa określa strukture możliwych ruchów dla danego pionka w formie drzewa.
 * Korzeniem drzewa jest pionek dla którego ruchy rozpatrujemy.
 * Węzłami drzewa są pola na które pionek może się poruszyć po danej ścieżce.
 */
@Deprecated
public class TreeMoves implements Serializable {
    private List<TreeMoves> children = new ArrayList<TreeMoves>();
    private TreeMoves parent = null;
    private Field field;

    public TreeMoves(Field field) {
        this.field=field;
    }
    public TreeMoves(Field field, TreeMoves parent) {
        this.field=field;
        this.parent=parent;
    }
    public void setParent(TreeMoves parent) {
        this.parent=parent;
    }
    public void addChild(TreeMoves child) {
        child.setParent(this);
        this.children.add(child);
    }
    public List<TreeMoves> getChildren() {
        return children;
    }
    public Field getField() {
        return this.field;
    }
    public void setField(Field field) {
        this.field=field;
    }
}

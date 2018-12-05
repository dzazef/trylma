package models.client_server;

import java.util.ArrayList;
import java.util.List;

/**
 * Klasa obsługująca wszystkie mozliwe ścieżki w danym ruchu.
 */
public class AllMovePaths {
    private List<MovePath> allMovePaths;
    public AllMovePaths() {
        this.allMovePaths=new ArrayList<>();
    }
    public void addMovePath(MovePath movePath) {
        this.allMovePaths.add(movePath);
    }
    public void removeLastMovePath() {
        this.allMovePaths.remove(this.allMovePaths.size()-1);
    }
}

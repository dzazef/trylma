package models.client.board;

import models.client.players.Player;
import models.client.players.PlayerFactory;
import org.junit.Before;
import org.junit.Test;
import views.BoardViewManager;

import static org.junit.Assert.*;

public class BoardTest {
    @Before
    public void init() {
        BoardViewManager.initializeFields();
    }

    @Test
    public void generateFields() {
        assertTrue(BoardViewManager.getCircleFields().size()>0);
    }

    private void addNewPlayer() {
        int id = BoardViewManager.getPlayerList().size()+1;
        Player player = PlayerFactory.getPlayer(id, false, 4, 4, 4, 4);
        if (player==null) System.err.println("Wrong user id. Couldn't create user.");
        else {
            BoardViewManager.getPlayerList().add(player);
        }
    }

    @Test
    public void testAddNewPlayer() {
        addNewPlayer();
        assertEquals(1, BoardViewManager.getPlayerList().size());
        addNewPlayer();
        assertEquals(2, BoardViewManager.getPlayerList().size());
        addNewPlayer();
        assertEquals(3, BoardViewManager.getPlayerList().size());
        addNewPlayer();
        assertEquals(4, BoardViewManager.getPlayerList().size());
        addNewPlayer();
        assertEquals(5, BoardViewManager.getPlayerList().size());
        addNewPlayer();
        assertEquals(6, BoardViewManager.getPlayerList().size());
        addNewPlayer();
        assertEquals(6, BoardViewManager.getPlayerList().size());
    }
}

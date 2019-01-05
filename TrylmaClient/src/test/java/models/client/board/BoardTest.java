package models.client.board;

import models.client.players.Player;
import models.client.players.PlayerFactory;
import org.junit.Test;

import static org.junit.Assert.*;

public class BoardTest {
    @Test
    public void generateFields() {
        Board.generateFields();
        assertTrue(Board.getCircleFields().size()>0);
    }

    private void addNewPlayer() {
        Player player = PlayerFactory.getPlayer(Board.getPlayerList().size()+1, false, 4, 4, 4, 4);
        if (player==null) System.err.println("Wrong user id. Couldn't create user.");
        else {
            Board.getPlayerList().add(player);
        }
    }

    @Test
    public void testAddNewPlayer() {
        addNewPlayer();
        assertEquals(1, Board.getPlayerList().size());
        addNewPlayer();
        assertEquals(2, Board.getPlayerList().size());
        addNewPlayer();
        assertEquals(3, Board.getPlayerList().size());
        addNewPlayer();
        assertEquals(4, Board.getPlayerList().size());
        addNewPlayer();
        assertEquals(5, Board.getPlayerList().size());
        addNewPlayer();
        assertEquals(6, Board.getPlayerList().size());
        addNewPlayer();
        assertEquals(6, Board.getPlayerList().size());
    }
}

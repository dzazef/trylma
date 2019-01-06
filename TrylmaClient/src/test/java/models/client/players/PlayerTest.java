package models.client.players;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class PlayerTest {

    @Test
    public void getID() {
        assertEquals(1, new Player1(false, 0, 0, 0, 0).getID());
        assertEquals(2, new Player2(false, 0, 0, 0, 0).getID());
        assertEquals(3, new Player3(false, 0, 0, 0, 0).getID());
        assertEquals(4, new Player4(false, 0, 0, 0, 0).getID());
        assertEquals(5, new Player5(false, 0, 0, 0, 0).getID());
        assertEquals(6, new Player6(false, 0, 0, 0, 0).getID());
    }

    @Test
    public void generateFields() {
        Player player = new Player1(false, 1, 4, 0, 0);
        assertTrue(player.getCircleFields().size()>0);
        player = new Player2(false, 1, 4, 0, 0);
        assertTrue(player.getCircleFields().size()>0);
        player = new Player3(false, 1, 4, 0, 0);
        assertTrue(player.getCircleFields().size()>0);
        player = new Player4(false, 1, 4, 0, 0);
        assertTrue(player.getCircleFields().size()>0);
        player = new Player5(false, 1, 4, 0, 0);
        assertTrue(player.getCircleFields().size()>0);
        player = new Player6(false, 1, 4, 0, 0);
        assertTrue(player.getCircleFields().size()>0);
    }
}
package models.client.players;

import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class PlayerFactoryTest {

    @Test
    public void getPlayer() {
        Player player = PlayerFactory.getPlayer(1, true, 1, 1, 1, 1);
        assertTrue(player instanceof Player1);
        player = PlayerFactory.getPlayer(2, true, 1, 1, 1, 1);
        assertTrue(player instanceof Player2);
        player = PlayerFactory.getPlayer(3, true, 1, 1, 1, 1);
        assertTrue(player instanceof Player3);
        player = PlayerFactory.getPlayer(4, true, 1, 1, 1, 1);
        assertTrue(player instanceof Player4);
        player = PlayerFactory.getPlayer(5, true, 1, 1, 1, 1);
        assertTrue(player instanceof Player5);
        player = PlayerFactory.getPlayer(6, true, 1, 1, 1, 1);
        assertTrue(player instanceof Player6);
    }
}
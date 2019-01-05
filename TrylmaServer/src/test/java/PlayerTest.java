import gamemanager.GameManager;
import gamemanager.MoveManager;
import org.junit.Before;
import org.junit.Test;
import player.*;
import serializable.Field;
import serializable.FieldsSet;

import static org.junit.Assert.*;
public class PlayerTest {
    Player1 player1;
    Player2 player2;
    Player3 player3;
    Player4 player4;
    Player5 player5;
    Player6 player6;
    @Before
    public void before()
    {
        GameManager.initialize();
        GameManager.getBoard().build(4);
        player1 = (Player1) GameManager.getPlayerCreators().get(0).createPlayer(4,false);
        player2 = (Player2) GameManager.getPlayerCreators().get(1).createPlayer(4,false);
        player3 = (Player3) GameManager.getPlayerCreators().get(2).createPlayer(4,false);
        player4 = (Player4) GameManager.getPlayerCreators().get(3).createPlayer(4,false);
        player5 = (Player5) GameManager.getPlayerCreators().get(4).createPlayer(4,false);
        player6 = (Player6) GameManager.getPlayerCreators().get(5).createPlayer(4,false);
    }
    @Test
    public void testPlayers()
    {
        assertEquals(player1.getId(),"1");
        assertEquals(player2.getId(),"2");
        assertEquals(player3.getId(),"3");
        assertEquals(player4.getId(),"4");
        assertEquals(player5.getId(),"5");
        assertEquals(player6.getId(),"6");

        player1.clear();
        player2.clear();
        player3.clear();
        player4.clear();
        player5.clear();
        player6.clear();

        assertNull(player1.getBotchoosenpawn());
        assertNull(player2.getBotchoosenpawn());
        assertNull(player3.getBotchoosenpawn());
        assertNull(player4.getBotchoosenpawn());
        assertNull(player5.getBotchoosenpawn());
        assertNull(player6.getBotchoosenpawn());

        assertNull(player1.getBotchoosenpath());
        assertNull(player2.getBotchoosenpath());
        assertNull(player3.getBotchoosenpath());
        assertNull(player4.getBotchoosenpath());
        assertNull(player5.getBotchoosenpath());
        assertNull(player6.getBotchoosenpath());

        player1.setBotchoosenpawn(player1.getPawnById("-6,-3,-3"));
        player2.setBotchoosenpawn(player2.getPawnById("6,3,3"));
        player3.setBotchoosenpawn(player3.getPawnById("3,-3,6"));
        player4.setBotchoosenpawn(player4.getPawnById("-3,3,-6"));
        player5.setBotchoosenpawn(player5.getPawnById("-3,-6,3"));
        player6.setBotchoosenpawn(player6.getPawnById("3,6,-3"));

        assertEquals(player1.getBotchoosenpawn().getId(),"-6,-3,-3");
        assertEquals(player2.getBotchoosenpawn().getId(),"6,3,3");
        assertEquals(player3.getBotchoosenpawn().getId(),"3,-3,6");
        assertEquals(player4.getBotchoosenpawn().getId(),"-3,3,-6");
        assertEquals(player5.getBotchoosenpawn().getId(),"-3,-6,3");
        assertEquals(player6.getBotchoosenpawn().getId(),"3,6,-3");

        player1.setBotchoosendestination(GameManager.getBoard().getFieldById("2,3,-1"));
        player2.setBotchoosendestination(GameManager.getBoard().getFieldById("1,-3,4"));
        player3.setBotchoosendestination(GameManager.getBoard().getFieldById("0,-1,1"));
        player4.setBotchoosendestination(GameManager.getBoard().getFieldById("-2,-4,2"));
        player5.setBotchoosendestination(GameManager.getBoard().getFieldById("-3,-3,0"));
        player6.setBotchoosendestination(GameManager.getBoard().getFieldById("-4,-3,-1"));

        assertEquals(player1.getBotchoosendestination().getId(),"2,3,-1");
        assertEquals(player2.getBotchoosendestination().getId(),"1,-3,4");
        assertEquals(player3.getBotchoosendestination().getId(),"0,-1,1");
        assertEquals(player4.getBotchoosendestination().getId(),"-2,-4,2");
        assertEquals(player5.getBotchoosendestination().getId(),"-3,-3,0");
        assertEquals(player6.getBotchoosendestination().getId(),"-4,-3,-1");

        FieldsSet dummyfieldset1 = new FieldsSet();
        FieldsSet dummyfieldset2 = new FieldsSet();
        FieldsSet dummyfieldset3 = new FieldsSet();
        FieldsSet dummyfieldset4 = new FieldsSet();
        FieldsSet dummyfieldset5 = new FieldsSet();
        FieldsSet dummyfieldset6 = new FieldsSet();


        dummyfieldset1.addField(GameManager.getBoard().getFieldById("0,-1,1"));
        dummyfieldset1.addField(GameManager.getBoard().getFieldById("0,0,0"));

        dummyfieldset2.addField(GameManager.getBoard().getFieldById("0,-2,2"));
        dummyfieldset2.addField(GameManager.getBoard().getFieldById("2,-1,3"));

        dummyfieldset3.addField(GameManager.getBoard().getFieldById("-1,-4,3"));
        dummyfieldset3.addField(GameManager.getBoard().getFieldById("-2,-3,1"));

        dummyfieldset4.addField(GameManager.getBoard().getFieldById("1,2,-1"));
        dummyfieldset4.addField(GameManager.getBoard().getFieldById("2,3,-1"));

        dummyfieldset5.addField(GameManager.getBoard().getFieldById("3,2,1"));
        dummyfieldset5.addField(GameManager.getBoard().getFieldById("4,0,4"));

        dummyfieldset6.addField(GameManager.getBoard().getFieldById("0,0,0"));
        dummyfieldset6.addField(GameManager.getBoard().getFieldById("-3,0,-3"));
        dummyfieldset6.addField(GameManager.getBoard().getFieldById("1,-2,3"));


        player1.setBotchoosenpath(dummyfieldset1);
        player2.setBotchoosenpath(dummyfieldset2);
        player3.setBotchoosenpath(dummyfieldset3);
        player4.setBotchoosenpath(dummyfieldset4);
        player5.setBotchoosenpath(dummyfieldset5);
        player6.setBotchoosenpath(dummyfieldset6);

        assertEquals(player1.getBotchoosenpath().getEnd().getId(),"0,0,0");
        assertEquals(player2.getBotchoosenpath().getEnd().getId(),"2,-1,3");
        assertEquals(player3.getBotchoosenpath().getEnd().getId(),"-2,-3,1");
        assertEquals(player4.getBotchoosenpath().getEnd().getId(),"2,3,-1");
        assertEquals(player5.getBotchoosenpath().getEnd().getId(),"4,0,4");
        assertEquals(player6.getBotchoosenpath().getEnd().getId(),"1,-2,3");


        assertFalse(player1.isBot());
        assertFalse(player2.isBot());
        assertFalse(player3.isBot());
        assertFalse(player4.isBot());
        assertFalse(player5.isBot());
        assertFalse(player6.isBot());

        assertNull(player1.getPawnById("0,0,0"));
        assertNull(player2.getPawnById("0,0,0"));
        assertNull(player3.getPawnById("0,0,0"));
        assertNull(player4.getPawnById("0,0,0"));
        assertNull(player5.getPawnById("0,0,0"));
        assertNull(player6.getPawnById("0,0,0"));

        assertFalse(player1.checkWin());
        assertFalse(player2.checkWin());
        assertFalse(player3.checkWin());
        assertFalse(player4.checkWin());
        assertFalse(player5.checkWin());
        assertFalse(player6.checkWin());

        assertNotNull(player1.getPawns());
        assertNotNull(player2.getPawns());
        assertNotNull(player3.getPawns());
        assertNotNull(player4.getPawns());
        assertNotNull(player5.getPawns());
        assertNotNull(player6.getPawns());

        assertEquals(player1.getPawns().size() , 10);
        assertEquals(player2.getPawns().size() , 10);
        assertEquals(player3.getPawns().size() , 10);
        assertEquals(player4.getPawns().size() , 10);
        assertEquals(player5.getPawns().size() , 10);
        assertEquals(player6.getPawns().size() , 10);

        Pawn testpawn = player1.getPawnById("-5,-4,-1");

        assertEquals(testpawn.getId(),"-5,-4,-1");
        GameManager.getBoard().move(testpawn,GameManager.getBoard().getFieldById("-1,-2,1"));
        player1.movePawn(testpawn,GameManager.getBoard().getFieldById("-1,-2,1"));
        assertEquals(testpawn.getId(),"-1,-2,1");

        testpawn = player2.getPawnById("5,2,3");

        assertEquals(testpawn.getId(),"5,2,3");
        GameManager.getBoard().move(testpawn,GameManager.getBoard().getFieldById("-1,-3,2"));
        player2.movePawn(testpawn,GameManager.getBoard().getFieldById("-1,-3,2"));
        assertEquals(testpawn.getId(),"-1,-3,2");

        testpawn = player3.getPawnById("1,-4,5");

        assertEquals(testpawn.getId(),"1,-4,5");
        GameManager.getBoard().move(testpawn,GameManager.getBoard().getFieldById("3,0,3"));
        player3.movePawn(testpawn,GameManager.getBoard().getFieldById("3,0,3"));
        assertEquals(testpawn.getId(),"3,0,3");

        testpawn = player4.getPawnById("-3,3,-6");

        assertEquals(testpawn.getId(),"-3,3,-6");
        GameManager.getBoard().move(testpawn,GameManager.getBoard().getFieldById("0,3,-3"));
        player4.movePawn(testpawn,GameManager.getBoard().getFieldById("0,3,-3"));
        assertEquals(testpawn.getId(),"0,3,-3");

        testpawn = player5.getPawnById("-4,-7,3");

        assertEquals(testpawn.getId(),"-4,-7,3");
        GameManager.getBoard().move(testpawn,GameManager.getBoard().getFieldById("-2,0,-2"));
        player5.movePawn(testpawn,GameManager.getBoard().getFieldById("-2,0,-2"));
        assertEquals(testpawn.getId(),"-2,0,-2");

        testpawn = player6.getPawnById("3,7,-4");

        assertEquals(testpawn.getId(),"3,7,-4");
        GameManager.getBoard().move(testpawn,GameManager.getBoard().getFieldById("4,1,3"));
        player6.movePawn(testpawn,GameManager.getBoard().getFieldById("4,1,3"));
        assertEquals(testpawn.getId(),"4,1,3");
    }

    @Test
    public void testBot()
    {
        Bot.botMove(player1);

        GameManager.getBoard().move(player1.getBotchoosenpawn(),player1.getBotchoosendestination());
        player1.movePawn(player1.getBotchoosenpawn(),player1.getBotchoosendestination());

        Pawn previousBotChoosenPawn = player1.getBotchoosenpawn();
        FieldsSet previousBotChoosenPath = player1.getBotchoosenpath();
        Field previousBotChoosenDestination = player1.getBotchoosendestination();

        Bot.botMove(player1);
        GameManager.getBoard().move(player1.getBotchoosenpawn(),player1.getBotchoosendestination());
        player1.movePawn(player1.getBotchoosenpawn(),player1.getBotchoosendestination());

        assertNotEquals(player1.getBotchoosenpath().getEnd().getId(),previousBotChoosenPath.getEnd().getId());
        assertNotEquals(player1.getBotchoosendestination().getId(),previousBotChoosenDestination.getId());
        assertNotEquals(player1.getBotchoosenpawn().getId(),previousBotChoosenPawn.getId());

        Bot.botMove(player2);
        GameManager.getBoard().move(player2.getBotchoosenpawn(),player2.getBotchoosendestination());
        player2.movePawn(player2.getBotchoosenpawn(),player2.getBotchoosendestination());

         previousBotChoosenPawn = player2.getBotchoosenpawn();
         previousBotChoosenPath = player2.getBotchoosenpath();
         previousBotChoosenDestination = player2.getBotchoosendestination();

        Bot.botMove(player2);
        GameManager.getBoard().move(player2.getBotchoosenpawn(),player2.getBotchoosendestination());
        player2.movePawn(player2.getBotchoosenpawn(),player2.getBotchoosendestination());

        assertNotEquals(player2.getBotchoosenpath().getEnd().getId(),previousBotChoosenPath.getEnd().getId());
        assertNotEquals(player2.getBotchoosendestination().getId(),previousBotChoosenDestination.getId());
        assertNotEquals(player2.getBotchoosenpawn().getId(),previousBotChoosenPawn.getId());

        Bot.botMove(player3);
        GameManager.getBoard().move(player3.getBotchoosenpawn(),player3.getBotchoosendestination());
        player3.movePawn(player3.getBotchoosenpawn(),player3.getBotchoosendestination());

         previousBotChoosenPawn = player3.getBotchoosenpawn();
         previousBotChoosenPath = player3.getBotchoosenpath();
         previousBotChoosenDestination = player3.getBotchoosendestination();

        Bot.botMove(player3);
        GameManager.getBoard().move(player3.getBotchoosenpawn(),player3.getBotchoosendestination());
        player3.movePawn(player3.getBotchoosenpawn(),player3.getBotchoosendestination());

        assertNotEquals(player3.getBotchoosenpath().getEnd().getId(),previousBotChoosenPath.getEnd().getId());
        assertNotEquals(player3.getBotchoosendestination().getId(),previousBotChoosenDestination.getId());
        assertNotEquals(player3.getBotchoosenpawn().getId(),previousBotChoosenPawn.getId());

        Bot.botMove(player4);
        GameManager.getBoard().move(player4.getBotchoosenpawn(),player4.getBotchoosendestination());
        player4.movePawn(player4.getBotchoosenpawn(),player4.getBotchoosendestination());

         previousBotChoosenPawn = player4.getBotchoosenpawn();
         previousBotChoosenPath = player4.getBotchoosenpath();
         previousBotChoosenDestination = player4.getBotchoosendestination();

        Bot.botMove(player4);
        GameManager.getBoard().move(player4.getBotchoosenpawn(),player4.getBotchoosendestination());
        player4.movePawn(player4.getBotchoosenpawn(),player4.getBotchoosendestination());

        assertNotEquals(player4.getBotchoosenpath().getEnd().getId(),previousBotChoosenPath.getEnd().getId());
        assertNotEquals(player4.getBotchoosendestination().getId(),previousBotChoosenDestination.getId());
        assertNotEquals(player4.getBotchoosenpawn().getId(),previousBotChoosenPawn.getId());

        Bot.botMove(player5);
        GameManager.getBoard().move(player5.getBotchoosenpawn(),player5.getBotchoosendestination());
        player5.movePawn(player5.getBotchoosenpawn(),player5.getBotchoosendestination());

         previousBotChoosenPawn = player5.getBotchoosenpawn();
         previousBotChoosenPath = player5.getBotchoosenpath();
         previousBotChoosenDestination = player5.getBotchoosendestination();

        Bot.botMove(player5);
        GameManager.getBoard().move(player5.getBotchoosenpawn(),player5.getBotchoosendestination());
        player5.movePawn(player5.getBotchoosenpawn(),player5.getBotchoosendestination());

        assertNotEquals(player5.getBotchoosenpath().getEnd().getId(),previousBotChoosenPath.getEnd().getId());
        assertNotEquals(player5.getBotchoosendestination().getId(),previousBotChoosenDestination.getId());
        assertNotEquals(player5.getBotchoosenpawn().getId(),previousBotChoosenPawn.getId());

        Bot.botMove(player6);
        GameManager.getBoard().move(player6.getBotchoosenpawn(),player6.getBotchoosendestination());
        player6.movePawn(player6.getBotchoosenpawn(),player6.getBotchoosendestination());

         previousBotChoosenPawn = player6.getBotchoosenpawn();
         previousBotChoosenPath = player6.getBotchoosenpath();
         previousBotChoosenDestination = player6.getBotchoosendestination();

        Bot.botMove(player6);
        GameManager.getBoard().move(player6.getBotchoosenpawn(),player6.getBotchoosendestination());
        player6.movePawn(player6.getBotchoosenpawn(),player6.getBotchoosendestination());

        assertNotEquals(player6.getBotchoosenpath().getEnd().getId(),previousBotChoosenPath.getEnd().getId());
        assertNotEquals(player6.getBotchoosendestination().getId(),previousBotChoosenDestination.getId());
        assertNotEquals(player6.getBotchoosenpawn().getId(),previousBotChoosenPawn.getId());

    }
}

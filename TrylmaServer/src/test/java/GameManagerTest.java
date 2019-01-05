import gamemanager.GameManager;
import gamemanager.MoveManager;
import org.junit.Before;
import org.junit.Test;
import player.Pawn;
import player.Player;
import player.Player1;
import player.Player2;
import serializable.Field;
import serializable.FieldsSet;


import java.util.ArrayList;

import static org.junit.Assert.*;


public class GameManagerTest {
    private class DummyPlayer implements Player
    {

        @Override
        public boolean isBot() {
            return false;
        }

        @Override
        public Pawn getBotchoosenpawn() {
            return null;
        }

        @Override
        public Field getBotchoosendestination() {
            return null;
        }

        @Override
        public FieldsSet getBotchoosenpath() {
            return null;
        }

        @Override
        public ArrayList<Pawn> getPawns() {
            return null;
        }

        @Override
        public void clear() {

        }

        @Override
        public void setBotchoosenpawn(Pawn pawn) {

        }

        @Override
        public void setBotchoosendestination(Field desination) {

        }

        @Override
        public void setBotchoosenpath(FieldsSet path) {

        }

        @Override
        public Pawn getPawnById(String id) {
            return null;
        }

        @Override
        public String getId() {
            return "404 not found";
        }

        @Override
        public boolean checkWin() {
            return true;
        }

        @Override
        public void movePawn(Pawn pawn, Field destination) {

        }
    }
    @Before
    public void before()
    {
        GameManager.initialize();
        GameManager.getBoard().build(4);
        GameManager.getPlayers().add(new Player1(4,true));
        GameManager.getPlayers().add(new DummyPlayer());

    }
    @Test
    public void testGameManager()
    {
        assertNotNull(GameManager.getBoard());
        assertNull(GameManager.getActualplayer());
        assertNotNull(GameManager.getPlayers());
        assertNotNull(GameManager.getChainOfResponsibility());
        assertNotNull(GameManager.getPlayerCreators());
        assertEquals(GameManager.getPlayerCreators().size(),6);


        GameManager.setActualplayer(new Player1(4,true));
        assertEquals(GameManager.getActualplayer().getId(),"1");

        assertFalse(GameManager.isGameInProgerss());
        GameManager.setGameInProgerss(true);
        assertTrue(GameManager.isGameInProgerss());

        GameManager.setNumberOfPawns(4);
        GameManager.setNumberOfPlayers(2);
        GameManager.setFreePlacesForGame(2);

        assertEquals(GameManager.getFreePlacesForGame(),2);
        assertEquals(GameManager.getNumberOfPlayers(),2);
        assertEquals(GameManager.getNumberOfPawns(),4);

        assertEquals(GameManager.getPlayers().size(), 2);
        assertEquals(GameManager.getPlayers().get(0).getId(),"1");

        assertEquals(GameManager.getPlayersobjout().size(), 0);

        assertEquals(GameManager.getPlayers().get(1).getId(),"404 not found");

        assertEquals(GameManager.checkWin().getId(),"404 not found");

        GameManager.nextPlayer();

        assertEquals(GameManager.getActualplayer().getId(),"404 not found");

    }

    @Test
    public void testMoveManager()
    {

        MoveManager.generateMovePaths(GameManager.getPlayers().get(0).getPawnById("-5,-4,-1"));
        assertEquals(MoveManager.getMoveDestinations().getPath().size(),2);

        MoveManager.setChoosenPawn(GameManager.getPlayers().get(0).getPawnById("-5,-3,-2"));
        assertEquals(MoveManager.getChoosenPawn().getId(),"-5,-3,-2");

        assertEquals(MoveManager.getPaths().size(),2);

        FieldsSet dummyset = new FieldsSet();
        dummyset.addField(GameManager.getBoard().getFieldById("0,0,0"));
        dummyset.addField(GameManager.getBoard().getFieldById("-1,-1,0"));

        MoveManager.setMoveDestinations(dummyset);
        assertEquals(MoveManager.getMoveDestinations().getPath().size(),2);
        assertEquals(MoveManager.getMoveDestinations().getStart().getId(),"0,0,0");
        assertEquals(MoveManager.getMoveDestinations().getEnd().getId(),"-1,-1,0");

        ArrayList<FieldsSet> dummyarray = new ArrayList<>();
        dummyarray.add(dummyset);
        MoveManager.setPaths(dummyarray);
        assertEquals(MoveManager.getPaths().size(),1);
    }


    @Test
    public void testBoard()
    {
        Player2 player2 = new Player2(4,false);

        assertEquals(GameManager.getBoard().getFieldById("5,1,4").getState() , Field.State.TAKEN);
        assertEquals(GameManager.getBoard().getFieldById("4,1,3").getState() , Field.State.FREE);


        GameManager.getBoard().move(player2.getPawnById("5,1,4"),GameManager.getBoard().getFieldById("4,1,3"));
        player2.movePawn(player2.getPawnById("5,1,4"),GameManager.getBoard().getFieldById("4,1,3"));

        assertEquals(GameManager.getBoard().getFieldById("5,1,4").getState() , Field.State.FREE);
        assertEquals(GameManager.getBoard().getFieldById("4,1,3").getState() , Field.State.TAKEN);

    }

}

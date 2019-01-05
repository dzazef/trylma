package player;

import gamemanager.GameManager;
import gamemanager.MoveManager;

import serializable.Field;
import serializable.FieldsSet;

import java.util.ArrayList;
import java.util.Random;

public class Player3 implements Player {
    private String id;
    private ArrayList<Pawn> pawns;
    private int numOfPawns;
    private boolean bot;
    private Pawn botchoosenpawn=null;
    private FieldsSet botchoosenpath = null;
    private Field botchoosendestination = null;

    public Player3(int numOfPawns, boolean bot)
    {
        this.id = "3";
        this.bot = bot;
        this.numOfPawns= numOfPawns;
        this.pawns = new ArrayList<>();
        int initialy = -numOfPawns;
        int initialz = 2*numOfPawns;
        int x = numOfPawns;
        for(int k = numOfPawns; x>0;x--,k--) {
            int y = initialy;
            int z = initialz;
            for (int i = 0; i < k; i++) {
                    Pawn pawn = new Pawn(x,y,z);
                    this.pawns.add(pawn);
                GameManager.getBoard().getFieldById(pawn.getId()).changeState();
                y++;
                z--;
            }
            initialz--;
        }
    }
    public Pawn getPawnById(String id)
    {
        for (Pawn pawn : pawns) {
            if (pawn.getId().equals(id)) {
                return pawn;
            }
        }
        return null;
    }

    public boolean isBot() {
        return bot;
    }

    public Pawn getBotchoosenpawn() {
        return botchoosenpawn;
    }

    public Field getBotchoosendestination() {
        return botchoosendestination;
    }

    public FieldsSet getBotchoosenpath() {
        return botchoosenpath;
    }

    public String getId() {
        return id;
    }

    public boolean checkWin() {
        for (Pawn pawn : pawns) {
            if (pawn.getZ() >= -numOfPawns) {
                return false;
            }
        }
        return true;
    }
    @Override
    public void clear()
    {
        this.botchoosenpath = null;
        this.botchoosenpawn = null;
    }
    @Override
    public ArrayList<Pawn> getPawns() {
        return pawns;
    }

    @Override
    public void setBotchoosenpawn(Pawn pawn) {
        this.botchoosenpawn = pawn;
    }

    @Override
    public void setBotchoosendestination(Field desination) {
        this.botchoosendestination = desination;
    }

    @Override
    public void setBotchoosenpath(FieldsSet path) {
        this.botchoosenpath = path;
    }


    public void movePawn(Pawn pawn, Field destination) {
        for (Pawn pawn1 : pawns) {
            if (pawn1.getId().equals(pawn.getId()))
            {
                pawn1.setXYZ(destination.getX(), destination.getY(), destination.getZ());
            }
        }
    }
}

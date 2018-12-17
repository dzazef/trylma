package player;

import gamemanager.GameManager;
import gamemanager.MoveManager;
import gamemanager.Path;
import serializable.Field;

import java.util.ArrayList;

public class Player5 implements Player {
    String id="player5";
    ArrayList<Pawn> pawns;
    int numOfPawns;
    boolean bot;
    Pawn botchoosenpawn=null;
    Path botchoosenpath = null;
    Field botchoosendestination = null;

    public Player5(int numOfPawns, boolean bot)
    {
        this.bot = bot;
        this.numOfPawns = numOfPawns;
        this.pawns = new ArrayList<Pawn>();
        int initialy = -2*numOfPawns;
        int initialz = numOfPawns;
        int x = -numOfPawns;
        for(int k = numOfPawns; x<0 ;x++,k--) {
            int y = initialy;
            int z = initialz;
            for (int i = 0; i < k; i++) {
                Pawn pawn = new Pawn(x,y,z);
                this.pawns.add(pawn);
                GameManager.board.getFieldById(pawn.getId()).changeState();
                y++;
                z--;
            }
            initialy++;
        }
    }
    public Pawn getPawnById(String id)
    {
        for(int i = 0; i < pawns.size();i++)
        {
            if(pawns.get(i).getId()==id)
            {
                return pawns.get(i);
            }
        }
        return null;
    }
    public ArrayList<Pawn> getPawns()
    {
        return pawns;
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

    public Path getBotchoosenpath() {
        return botchoosenpath;
    }

    public String getId() {
        return id;
    }

    public boolean checkWin() {
        for(int i = 0;i<pawns.size();i++)
        {
            if(pawns.get(i).getY() <= numOfPawns)
            {
                return false;
            }
        }
        return true;
    }

    public void botMove() {
        int y = -2*numOfPawns;
        for(int j = 0; j < pawns.size();j++) {
            MoveManager.generateMovePaths(pawns.get(j));

            for (int i = 0; i < MoveManager.paths.size(); i++) {

                if (MoveManager.paths.get(i).end.getY() > y) {
                    this.botchoosenpawn = pawns.get(j);
                    y = MoveManager.paths.get(i).end.getY();
                    this.botchoosenpath = MoveManager.paths.get(i);

                }
            }
        }
        this.botchoosendestination = this.botchoosenpath.end;
    }

    public void movePawn(Pawn pawn, Field destination) {
        for(int i = 0; i < pawns.size();i++)
        {
            if(pawns.get(i).getId()== pawn.getId())
            {
                pawns.get(i).setXYZ(destination.getX(),destination.getY(),destination.getZ());
            }
        }
    }
}

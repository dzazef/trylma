package player;

import gamemanager.GameManager;
import gamemanager.Path;
import gamemanager.board.Field;

import java.util.ArrayList;

public class Player4 implements Player {
    String id="player4";
    ArrayList<Pawn> pawns;
    int numOfPawns;
    boolean bot;
    Pawn botchoosenpawn=null;

    public Player4(int numOfPawns, boolean bot)
    {
        this.bot = bot;
        this.numOfPawns = numOfPawns;
        this.pawns = new ArrayList<Pawn>();
        int initialy = 1;
        int initialz = -(numOfPawns+1);
        int x = -numOfPawns;
        for(int k = numOfPawns; x<0 ;x++,k--) {
            int y = initialy;
            int z = initialz;
            for (int i = 0; i < k; i++) {
                Pawn pawn = new Pawn(x,y,z);
                this.pawns.add(pawn);

                y++;
                z--;
            }
            initialy++;
        }
    }
    public String getId() {
        return id;
    }

    public boolean checkWin() {
        for(int i = 0;i<pawns.size();i++)
        {
            if(pawns.get(i).getZ() >= numOfPawns)
            {
                return false;
            }
        }
        return true;
    }

    public void botMove(Pawn pawn, Field destination, Path path) {

        int z = -2*numOfPawns;
        for(int j = 0; j < pawns.size();j++) {
            GameManager.generateMovePaths(pawns.get(j));

            for (int i = 0; i < GameManager.paths.size(); i++) {

                if (GameManager.paths.get(i).end.getZ() > z) {
                    z = GameManager.paths.get(i).end.getY();
                    path = GameManager.paths.get(i);
                }
            }
        }
        destination = path.end;
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

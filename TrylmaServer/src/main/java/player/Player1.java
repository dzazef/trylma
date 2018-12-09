package player;

import gamemanager.GameManager;
import gamemanager.Path;
import gamemanager.board.Field;

import java.util.ArrayList;

public class Player1 implements Player {
    String id="player1";
    ArrayList<Pawn> pawns;
    int numOfPawns;
    boolean bot;
    Pawn botchoosenpawn=null;

    public Player1(int numOfPawns,boolean bot)
    {
        this.bot = bot;
        this.numOfPawns = numOfPawns;
        this.pawns = new ArrayList<Pawn>();
        int initialy = -numOfPawns;
        int initialz = -numOfPawns;
        int x = -(numOfPawns+1);
        for(int k = numOfPawns; x>=-2*numOfPawns ;x--,k--) {
            int y = initialy;
            int z = initialz;
            for (int i = 0; i < k; i++) {
                Pawn pawn = new Pawn(x,y,z);
                this.pawns.add(pawn);
                y++;
                z--;
            }
            initialz--;
        }
    }

    public String getId() {
        return id;
    }

    public boolean checkWin() {
        for(int i = 0;i<pawns.size();i++)
        {
            if(pawns.get(i).getX() >= numOfPawns)
            {
                return false;
            }
        }
        return true;
    }

    public void botMove(Pawn pawn, Field destination, Path path) {
        int x = -2*numOfPawns;
        for(int j = 0; j < pawns.size();j++) {
            GameManager.generateMovePaths(pawns.get(j));

            for (int i = 0; i < GameManager.paths.size(); i++) {

                if (GameManager.paths.get(i).end.getX() > x) {
                    x = GameManager.paths.get(i).end.getY();
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

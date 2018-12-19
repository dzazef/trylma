package player;

import gamemanager.GameManager;
import gamemanager.MoveManager;

import serializable.Field;
import serializable.FieldsSet;

import java.util.ArrayList;

public class Player1 implements Player {
    private String id;
    private ArrayList<Pawn> pawns;
    private int numOfPawns;
    private boolean bot;
    private Pawn botchoosenpawn;
    private FieldsSet botchoosenpath ;
    private Field botchoosendestination;

    public Player1(int numOfPawns,boolean bot)
    {
        this.id = "1";
        this.bot = bot;
        this.numOfPawns = numOfPawns;
        this.pawns = new ArrayList<Pawn>();
        int initialy = -1;
        int initialz = -numOfPawns;
        int x = -(numOfPawns+1);
        for(int k = numOfPawns; x>=-2*numOfPawns ;x--,k--) {
            int y = initialy;
            int z = initialz;
            for (int i = 0; i < k; i++) {
                Pawn pawn = new Pawn(x,y,z);
                this.pawns.add(pawn);
                GameManager.board.getFieldById(pawn.getId()).changeState();
                y--;
                z++;
            }
            initialy--;
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

    public FieldsSet getBotchoosenpath() {
        return botchoosenpath;
    }

    public String getId() {
        return id;
    }

    public boolean checkWin() {
        for (Pawn pawn : pawns) {
            if (pawn.getX() >= numOfPawns) {
                return false;
            }
        }
        return true;
    }

    public void botMove() {

        int x = 0;
        int oś = 8;
        for (Pawn pawn : pawns) {
            MoveManager.generateMovePaths(pawn);

            if(botchoosenpawn != null)
            {
                if(botchoosenpawn.getId().equals(pawn.getId()))
                {
                    continue;
                }
            }

            for (int i = 0; i < MoveManager.paths.size(); i++) {
                if (MoveManager.paths != null) {
                    int delta = MoveManager.paths.get(i).end.getX()-pawn.getX();
                    if (delta> x) {
                        this.botchoosenpawn = pawn;
                        x = delta;
                        this.botchoosenpath = MoveManager.paths.get(i);
                        System.out.println("Bot 1 -----------------");

                        for(Field field : this.botchoosenpath.getPath())
                        {
                            System.out.println(field.getId());
                        }
                        System.out.println("----------------------------");
                    }
                    else if(delta == x)
                    {
                        int newoś = Math.abs(MoveManager.paths.get(i).end.getZ()) + MoveManager.paths.get(i).end.getY();
                        if( newoś < oś)
                        {
                            oś = newoś;
                        }
                    }
                }
            }
        }
        this.botchoosendestination = this.botchoosenpath.end;
    }

    public void movePawn(Pawn pawn, Field destination) {
        for (Pawn pawn1 : pawns) {
            if (pawn1.getId().equals(pawn.getId())) {
                pawn1.setXYZ(destination.getX(), destination.getY(), destination.getZ());
            }
        }
    }
}

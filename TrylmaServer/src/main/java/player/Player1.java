package player;

import gamemanager.GameManager;
import gamemanager.MoveManager;

import serializable.Field;
import serializable.FieldsSet;

import java.util.ArrayList;
import java.util.Random;

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
            if (pawn.getX() <= numOfPawns) {
                return false;
            }
        }
        return true;
    }

    public void botMove() {
        this.botchoosenpath = null;
        this.botchoosenpawn = null;
        System.out.println("Wywołuję funkcję ruchu bota");
        int points = 0;
        for (Pawn pawn : pawns) {
            MoveManager.generateMovePaths(pawn);
            if(this.botchoosenpath == null)
            {
                if(MoveManager.paths.size() >0)
                {
                    this.botchoosenpath = MoveManager.paths.get(0);
                    this.botchoosenpawn = pawn;
                }
            }
            if (MoveManager.paths != null) {
                for (int i = 0; i < MoveManager.paths.size(); i++) {
                    int pointstemp = calculatePoints(MoveManager.paths.get(i).start,MoveManager.paths.get(i).end);
                    if(pointstemp>points)
                    {
                        this.botchoosenpath = MoveManager.paths.get(i);
                        this.botchoosenpawn = pawn;
                        points = pointstemp;
                    }
                    else if (pointstemp == points)
                    {
                        int rand = (new Random(System.currentTimeMillis())).nextInt();
                        if(rand%2 == 0)
                        {
                            this.botchoosenpath = MoveManager.paths.get(i);
                            this.botchoosenpawn = pawn;
                            points = pointstemp;
                        }
                    }
                }
            }
        }
        this.botchoosendestination = this.botchoosenpath.end;
    }

    private int calculatePoints(Field start, Field end)
    {
        int points = 0;
        for(Pawn pawn:pawns)
        {
            if(pawn.getId().equals(start.getId()))
            {
                points += calculateFieldPoints(end);
            }
            else
            {
                points += calculateFieldPoints(GameManager.board.getFieldById(pawn.getId()));
            }
        }
        return points;
    }
    private int calculateFieldPoints(Field end)
    {
        int points = 0;
        if(end.getY()>=1 && end.getY()<=4)
        {
            points += 10;
        }
        else
        {
            points += 8 - Math.abs(end.getY() - 4);
        }

        if(end.getZ()>= 1 && end.getZ() <= 4)
        {
            points += 10;
        }
        else
        {
            points += 8 - Math.abs(end.getZ() - 4);
        }

        points += 16 - Math.abs(end.getX() - 8);
        return points;
    }
    public void movePawn(Pawn pawn, Field destination) {
        for (Pawn pawn1 : pawns) {
            if (pawn1.getId().equals(pawn.getId())) {
                pawn1.setXYZ(destination.getX(), destination.getY(), destination.getZ());
            }
        }
    }
}

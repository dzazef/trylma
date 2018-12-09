package gamemanager;

import gamemanager.board.Board;
import gamemanager.board.Field;
import player.Pawn;
import player.Player;

import java.util.ArrayList;

public abstract class GameManager {
    public static Board board;
    public static ArrayList<Player> players;
    public static ArrayList<Path> paths;

    public Player checkWin()
    {
        return null;
    }
    public void initialize(int numOfPawns, ArrayList<Player> players )
    {
        this.board = new Board();
        this.board.build(numOfPawns);
    }
    public static void  generateMovePaths(Pawn pawn)
    {
        paths = new ArrayList<Path>();
        step(board.getFieldById(pawn.getId()));


        Path path = new Path();
        path.path.add(board.getFieldById(pawn.getId()));
        jump(path);

    }
    public static String getIdFromCoordinates(int x,int y,int z)
    {
        return x+","+y+","+z;
    }
    private static void addJump(Field jumpEnd,Path previousPath)
    {
        if(jumpEnd != null) {
            for(int i = 0;i<paths.size();i++)
            {
                if(paths.get(i).end == jumpEnd)
                {
                    return ;
                }
            }
            if (jumpEnd.getState() == Field.State.FREE)
            {
                Path path = new Path(previousPath.getPath());
                path.addStepToPath(jumpEnd);
                paths.add(path);
                jump(path);
            }
        }
    }
    private static void jump(Path previousPath)
    {
        Field whereWeAre = previousPath.end;
        // Sprawdza czy jest możliwy skok w prawo i jak tak to go dodaje do liste ścieżek
        Field jumpEnd = board.getFieldById(getIdFromCoordinates(whereWeAre.getX(),whereWeAre.getY()+2,whereWeAre.getZ()-2));
        addJump(jumpEnd,previousPath);

        // Sprawdza czy jest możliwy skok w lewo i jak tak to go dodaje do listy ścieżek
        jumpEnd = board.getFieldById(getIdFromCoordinates(whereWeAre.getX(),whereWeAre.getY()-2,whereWeAre.getZ()+2));
        addJump(jumpEnd,previousPath);

        // Sprawdza czy jest możliwy skok w prawy górny róg i jak tak to go dodaje do listy ścieżek
        jumpEnd = board.getFieldById(getIdFromCoordinates(whereWeAre.getX()-2,whereWeAre.getY(),whereWeAre.getZ()-2));
        addJump(jumpEnd,previousPath);

        // Sprawdza czy jest możliwy skok w lewy górny róg i jak tak to go dodaje do listy ścieżek
        jumpEnd = board.getFieldById(getIdFromCoordinates(whereWeAre.getX() - 2,whereWeAre.getY()-2,whereWeAre.getZ()));
        addJump(jumpEnd,previousPath);

        // Sprawdza czy jest możliwy skok w lewy dolny róg i jak tak to go dodaje do listy ścieżek
        jumpEnd = board.getFieldById(getIdFromCoordinates(whereWeAre.getX()+2,whereWeAre.getY(),whereWeAre.getZ()+2));
        addJump(jumpEnd,previousPath);

        // Sprawdza czy jest możliwy skok w prawy dolny róg i jak tak to go dodaje do listy ścieżek
        jumpEnd = board.getFieldById(getIdFromCoordinates(whereWeAre.getX()+2,whereWeAre.getY()+2,whereWeAre.getZ()));
        addJump(jumpEnd,previousPath);
    }
    private static void addStep(Field stepEnd)
    {
        if(stepEnd != null)
        {
            if(stepEnd.getState() == Field.State.FREE)
            {
                Path path = new Path();
                path.addStepToPath(stepEnd);
                paths.add(path);
            }
        }


    }
    private static void step(Field field)
    {
        // Sprawdza, czy jest możliwy krok w prawo i dodaje do listy ścieżek
        Field stepEnd = board.getFieldById(getIdFromCoordinates(field.getX(),field.getY()+1,field.getZ()-1));
        addStep(stepEnd);

        // Sprawdza, czy jest możliwy krok w lewo i dodaje do listy ścieżek
        stepEnd = board.getFieldById(getIdFromCoordinates(field.getX(),field.getY()-1,field.getZ()+1));
        addStep(stepEnd);

        // Sprawdza, czy jest możliwy krok w prawy górny róg i dodaje do listy ścieżek
        stepEnd = board.getFieldById(getIdFromCoordinates(field.getX()-1,field.getY(),field.getZ()-1));
        addStep(stepEnd);

        // Sprawdza, czy jest możliwy krok w lewy górny róg i dodaje do listy ścieżek
        stepEnd = board.getFieldById(getIdFromCoordinates(field.getX()-1,field.getY()-1,field.getZ()));
        addStep(stepEnd);

        // Sprawdza, czy jest możliwy krok w prawy dolny róg i dodaje do listy ścieżek
        stepEnd = board.getFieldById(getIdFromCoordinates(field.getX()+1,field.getY()+1,field.getZ()));
        addStep(stepEnd);

        // Sprawdza, czy jest możliwy krok w lewy dolny róg i dodaje do listy ścieżek
        stepEnd = board.getFieldById(getIdFromCoordinates(field.getX()+1,field.getY(),field.getZ()+1));
        addStep(stepEnd);
    }
}

package gamemanager;
import gamemanager.board.Board;
import gamemanager.board.Field;
import player.Pawn;
import player.Pawn;

import java.util.ArrayList;

public class MoveManager {
    public static MoveDestinations moveDestinations;
    public static ArrayList<Path> paths;
    public static Pawn choosenPawn;
    public static void  generateMovePaths(Pawn pawn)
    {
        moveDestinations = new MoveDestinations();
        paths = new ArrayList<Path>();
        step(GameManager.board.getFieldById(pawn.getId()));


        Path path = new Path();
        path.end = GameManager.board.getFieldById(pawn.getId());
        jump(path);

    }
    public static String getIdFromCoordinates(int x,int y,int z)
    {
        return x+","+y+","+z;
    }

    private static void addJump(Field jumpEnd, Field jumpOver, Path previousPath)
    {
        if(jumpEnd != null) {
            if(jumpOver.getState() == Field.State.TAKEN) {
                for (int i = 0; i < paths.size(); i++) {
                    if (paths.get(i).end == jumpEnd) {
                        return;
                    }
                }
                if (jumpEnd.getState() == Field.State.FREE) {
                    Path path = new Path(previousPath.getPath());
                    path.addStepToPath(jumpEnd);
                    moveDestinations.addMoveDestination(jumpEnd);
                    paths.add(path);
                    jump(path);
                }
            }
            else
            {
                return ;
            }
        }
    }
    private static void jump(Path previousPath)
    {
        Field whereWeAre = previousPath.end;
        // Sprawdza czy jest możliwy skok w prawo i jak tak to go dodaje do liste ścieżek
        Field jumpOver = GameManager.board.getFieldById(getIdFromCoordinates(whereWeAre.getX(),whereWeAre.getY()+1,whereWeAre.getZ()-1));
        Field jumpEnd = GameManager.board.getFieldById(getIdFromCoordinates(whereWeAre.getX(),whereWeAre.getY()+2,whereWeAre.getZ()-2));
        addJump(jumpEnd,jumpOver,previousPath);


        // Sprawdza czy jest możliwy skok w lewo i jak tak to go dodaje do listy ścieżek
        jumpOver = GameManager.board.getFieldById(getIdFromCoordinates(whereWeAre.getX(),whereWeAre.getY()-1,whereWeAre.getZ()+1));
        jumpEnd = GameManager.board.getFieldById(getIdFromCoordinates(whereWeAre.getX(),whereWeAre.getY()-2,whereWeAre.getZ()+2));
        addJump(jumpEnd,jumpOver,previousPath);

        // Sprawdza czy jest możliwy skok w prawy górny róg i jak tak to go dodaje do listy ścieżek
        jumpOver = GameManager.board.getFieldById(getIdFromCoordinates(whereWeAre.getX()-1,whereWeAre.getY(),whereWeAre.getZ()-1));
        jumpEnd = GameManager.board.getFieldById(getIdFromCoordinates(whereWeAre.getX()-2,whereWeAre.getY(),whereWeAre.getZ()-2));
        addJump(jumpEnd,jumpOver,previousPath);

        // Sprawdza czy jest możliwy skok w lewy górny róg i jak tak to go dodaje do listy ścieżek
        jumpOver = GameManager.board.getFieldById(getIdFromCoordinates(whereWeAre.getX()-1,whereWeAre.getY()-1,whereWeAre.getZ()));
        jumpEnd = GameManager.board.getFieldById(getIdFromCoordinates(whereWeAre.getX() - 2,whereWeAre.getY()-2,whereWeAre.getZ()));
        addJump(jumpEnd,jumpOver,previousPath);

        // Sprawdza czy jest możliwy skok w lewy dolny róg i jak tak to go dodaje do listy ścieżek
        jumpOver = GameManager.board.getFieldById(getIdFromCoordinates(whereWeAre.getX()+1,whereWeAre.getY(),whereWeAre.getZ()+1));
        jumpEnd = GameManager.board.getFieldById(getIdFromCoordinates(whereWeAre.getX()+2,whereWeAre.getY(),whereWeAre.getZ()+2));
        addJump(jumpEnd,jumpOver,previousPath);

        // Sprawdza czy jest możliwy skok w prawy dolny róg i jak tak to go dodaje do listy ścieżek
        jumpOver = GameManager.board.getFieldById(getIdFromCoordinates(whereWeAre.getX()+1,whereWeAre.getY()+1,whereWeAre.getZ()));
        jumpEnd = GameManager.board.getFieldById(getIdFromCoordinates(whereWeAre.getX()+2,whereWeAre.getY()+2,whereWeAre.getZ()));
        addJump(jumpEnd,jumpOver,previousPath);
    }
    private static void addStep(Field stepEnd)
    {
        if(stepEnd != null)
        {
            if(stepEnd.getState() == Field.State.FREE)
            {
                Path path = new Path();
                path.addStepToPath(stepEnd);
                moveDestinations.addMoveDestination(stepEnd);
                paths.add(path);
            }
        }


    }
    private static void step(Field field)
    {
        // Sprawdza, czy jest możliwy krok w prawo i dodaje do listy ścieżek
        Field stepEnd = GameManager.board.getFieldById(getIdFromCoordinates(field.getX(),field.getY()+1,field.getZ()-1));
        addStep(stepEnd);

        // Sprawdza, czy jest możliwy krok w lewo i dodaje do listy ścieżek
        stepEnd = GameManager.board.getFieldById(getIdFromCoordinates(field.getX(),field.getY()-1,field.getZ()+1));
        addStep(stepEnd);

        // Sprawdza, czy jest możliwy krok w prawy górny róg i dodaje do listy ścieżek
        stepEnd = GameManager.board.getFieldById(getIdFromCoordinates(field.getX()-1,field.getY(),field.getZ()-1));
        addStep(stepEnd);

        // Sprawdza, czy jest możliwy krok w lewy górny róg i dodaje do listy ścieżek
        stepEnd = GameManager.board.getFieldById(getIdFromCoordinates(field.getX()-1,field.getY()-1,field.getZ()));
        addStep(stepEnd);

        // Sprawdza, czy jest możliwy krok w prawy dolny róg i dodaje do listy ścieżek
        stepEnd = GameManager.board.getFieldById(getIdFromCoordinates(field.getX()+1,field.getY()+1,field.getZ()));
        addStep(stepEnd);

        // Sprawdza, czy jest możliwy krok w lewy dolny róg i dodaje do listy ścieżek
        stepEnd = GameManager.board.getFieldById(getIdFromCoordinates(field.getX()+1,field.getY(),field.getZ()+1));
        addStep(stepEnd);
    }
}

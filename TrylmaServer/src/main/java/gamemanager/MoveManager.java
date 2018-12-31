package gamemanager;
import serializable.Field;
import player.Pawn;
import serializable.FieldsSet;

import java.util.ArrayList;

public class MoveManager {
    public static FieldsSet moveDestinations;
    public static ArrayList<FieldsSet> paths;
    public static Pawn choosenPawn;
    public static void  generateMovePaths(Pawn pawn)
    {
        moveDestinations = new FieldsSet();
        paths = new ArrayList<>();
        if(pawn == null)
        {
            System.out.println("Pawn jest nullem");
        }
        else {
            FieldsSet path = new FieldsSet();
            path.addField(GameManager.board.getFieldById(pawn.getId()));
            step(path);

            FieldsSet path2 = new FieldsSet();
            path2.addField(GameManager.board.getFieldById(pawn.getId()));
            jump(path2);

        }
    }
    private static String getIdFromCoordinates(int x, int y, int z)
    {
        return x+","+y+","+z;
    }

    private static boolean alreadyInSet(Field field)
    {
        for(FieldsSet set : MoveManager.paths)
        {
            if (field.getId().equals(set.end.getId()))
            {
                return false;
            }
        }
        return true;
    }
    private static FieldsSet addJump(Field jumpEnd, Field jumpOver, FieldsSet previousPath)
    {
        if(jumpEnd != null) {
            if(alreadyInSet(jumpEnd)) {
                if (jumpOver != null) {
                    if (jumpOver.getState() == Field.State.TAKEN) {
                        for (FieldsSet path1 : paths) {
                            if (path1.end.getId().equals(jumpEnd.getId())) {
                                return null;
                            }
                        }
                        if (jumpEnd.getState() == Field.State.FREE) {
                            FieldsSet path = new FieldsSet(previousPath.getPath());
                            path.addField(jumpEnd);
                            moveDestinations.addField(jumpEnd);
                            paths.add(path);
                            return path;
                        }
                    }
                }
            }
        }
        return null;
    }
    private static void jump(FieldsSet previousPath)
    {

        FieldsSet set1 = null ,set2 = null ,set3 = null,set4 = null,set5 = null,set6 = null;
        Field whereWeAre = previousPath.end;
        Field jumpOver;
        Field jumpEnd;

        // Sprawdza czy jest możliwy skok w prawo i jak tak to go dodaje do liste ścieżek
        if(GameManager.board.getFieldById(getIdFromCoordinates(whereWeAre.getX(),whereWeAre.getY()+1,whereWeAre.getZ()-1))!=null &&
                GameManager.board.getFieldById(getIdFromCoordinates(whereWeAre.getX(),whereWeAre.getY()+2,whereWeAre.getZ()-2))!=null  ) {
             jumpOver = GameManager.board.getFieldById(getIdFromCoordinates(whereWeAre.getX(), whereWeAre.getY() + 1, whereWeAre.getZ() - 1));
             jumpEnd = GameManager.board.getFieldById(getIdFromCoordinates(whereWeAre.getX(), whereWeAre.getY() + 2, whereWeAre.getZ() - 2));
            set1  = addJump(jumpEnd, jumpOver, previousPath);
        }



        // Sprawdza czy jest możliwy skok w lewo i jak tak to go dodaje do listy ścieżek
        if(GameManager.board.getFieldById(getIdFromCoordinates(whereWeAre.getX(), whereWeAre.getY() - 1, whereWeAre.getZ() + 1))!=null &&
                GameManager.board.getFieldById(getIdFromCoordinates(whereWeAre.getX(), whereWeAre.getY() - 2, whereWeAre.getZ() + 2))!=null  ) {
            jumpOver = GameManager.board.getFieldById(getIdFromCoordinates(whereWeAre.getX(), whereWeAre.getY() - 1, whereWeAre.getZ() + 1));
            jumpEnd = GameManager.board.getFieldById(getIdFromCoordinates(whereWeAre.getX(), whereWeAre.getY() - 2, whereWeAre.getZ() + 2));
            set2 = addJump(jumpEnd, jumpOver, previousPath);
        }


        // Sprawdza czy jest możliwy skok w prawy górny róg i jak tak to go dodaje do listy ścieżek
        if(GameManager.board.getFieldById(getIdFromCoordinates(whereWeAre.getX() - 1, whereWeAre.getY(), whereWeAre.getZ() - 1))!=null &&
                GameManager.board.getFieldById(getIdFromCoordinates(whereWeAre.getX() - 2, whereWeAre.getY(), whereWeAre.getZ() - 2))!=null  ) {
            jumpOver = GameManager.board.getFieldById(getIdFromCoordinates(whereWeAre.getX() - 1, whereWeAre.getY(), whereWeAre.getZ() - 1));
            jumpEnd = GameManager.board.getFieldById(getIdFromCoordinates(whereWeAre.getX() - 2, whereWeAre.getY(), whereWeAre.getZ() - 2));
            set3 = addJump(jumpEnd, jumpOver, previousPath);

        }


        // Sprawdza czy jest możliwy skok w lewy górny róg i jak tak to go dodaje do listy ścieżek
        if(GameManager.board.getFieldById(getIdFromCoordinates(whereWeAre.getX() - 1, whereWeAre.getY() - 1, whereWeAre.getZ()))!=null &&
                GameManager.board.getFieldById(getIdFromCoordinates(whereWeAre.getX() - 2, whereWeAre.getY() - 2, whereWeAre.getZ()))!=null  ) {
            jumpOver = GameManager.board.getFieldById(getIdFromCoordinates(whereWeAre.getX() - 1, whereWeAre.getY() - 1, whereWeAre.getZ()));
            jumpEnd = GameManager.board.getFieldById(getIdFromCoordinates(whereWeAre.getX() - 2, whereWeAre.getY() - 2, whereWeAre.getZ()));
            set4= addJump(jumpEnd, jumpOver, previousPath);
        }



        // Sprawdza czy jest możliwy skok w lewy dolny róg i jak tak to go dodaje do listy ścieżek
        if(GameManager.board.getFieldById(getIdFromCoordinates(whereWeAre.getX() + 1, whereWeAre.getY(), whereWeAre.getZ() + 1))!=null &&
                GameManager.board.getFieldById(getIdFromCoordinates(whereWeAre.getX() + 2, whereWeAre.getY(), whereWeAre.getZ() + 2))!=null  ) {
            jumpOver = GameManager.board.getFieldById(getIdFromCoordinates(whereWeAre.getX() + 1, whereWeAre.getY(), whereWeAre.getZ() + 1));
            jumpEnd = GameManager.board.getFieldById(getIdFromCoordinates(whereWeAre.getX() + 2, whereWeAre.getY(), whereWeAre.getZ() + 2));
            set5 = addJump(jumpEnd, jumpOver, previousPath);
        }



        // Sprawdza czy jest możliwy skok w prawy dolny róg i jak tak to go dodaje do listy ścieżek
        if(GameManager.board.getFieldById(getIdFromCoordinates(whereWeAre.getX() + 1, whereWeAre.getY() + 1, whereWeAre.getZ()))!=null &&
                GameManager.board.getFieldById(getIdFromCoordinates(whereWeAre.getX() + 2, whereWeAre.getY() + 2, whereWeAre.getZ()))!=null  ) {
            jumpOver = GameManager.board.getFieldById(getIdFromCoordinates(whereWeAre.getX() + 1, whereWeAre.getY() + 1, whereWeAre.getZ()));
            jumpEnd = GameManager.board.getFieldById(getIdFromCoordinates(whereWeAre.getX() + 2, whereWeAre.getY() + 2, whereWeAre.getZ()));
            set6 = addJump(jumpEnd, jumpOver, previousPath);
        }
        if(set1 != null)
        {
            jump(set1);
        }

        if(set2!= null)
        {
            jump(set2);
        }

        if(set3 != null)
        {
            jump(set3);
        }

        if(set4 != null)
        {
            jump(set4);
        }

        if(set5 != null)
        {
            jump(set5);
        }

        if(set6 != null)
        {
            jump(set6);
        }
    }
    private static void addStep(Field stepEnd, FieldsSet previousPath)
    {
        if(stepEnd != null)
        {
            if(alreadyInSet(stepEnd)) {
                if (stepEnd.getState() == Field.State.FREE) {
                    FieldsSet path = new FieldsSet(previousPath.path);
                    path.addField(stepEnd);
                    moveDestinations.addField(stepEnd);
                    paths.add(path);
                }
            }
        }
    }
    private static void step(FieldsSet previousPath)
    {

        Field stepEnd;
        // Sprawdza, czy jest możliwy krok w prawo i dodaje do listy ścieżek
        if(GameManager.board.getFieldById(getIdFromCoordinates(previousPath.start.getX(),previousPath.start.getY()+1,previousPath.start.getZ()-1))!=null) {
            stepEnd = GameManager.board.getFieldById(getIdFromCoordinates(previousPath.start.getX(), previousPath.start.getY() + 1, previousPath.start.getZ() - 1));
            addStep(stepEnd,previousPath);
        }

        // Sprawdza, czy jest możliwy krok w lewo i dodaje do listy ścieżek
        if(GameManager.board.getFieldById(getIdFromCoordinates(previousPath.start.getX(),previousPath.start.getY()-1,previousPath.start.getZ()+1))!=null) {
            stepEnd = GameManager.board.getFieldById(getIdFromCoordinates(previousPath.start.getX(), previousPath.start.getY() - 1, previousPath.start.getZ() + 1));
            addStep(stepEnd,previousPath);
        }


        // Sprawdza, czy jest możliwy krok w prawy górny róg i dodaje do listy ścieżek
        if(GameManager.board.getFieldById(getIdFromCoordinates(previousPath.start.getX()-1,previousPath.start.getY(),previousPath.start.getZ()-1))!=null) {
            stepEnd = GameManager.board.getFieldById(getIdFromCoordinates(previousPath.start.getX() - 1, previousPath.start.getY(), previousPath.start.getZ() - 1));
            addStep(stepEnd,previousPath);
        }



        // Sprawdza, czy jest możliwy krok w lewy górny róg i dodaje do listy ścieżek
        if(GameManager.board.getFieldById(getIdFromCoordinates(previousPath.start.getX()-1,previousPath.start.getY()-1,previousPath.start.getZ()))!=null) {
            stepEnd = GameManager.board.getFieldById(getIdFromCoordinates(previousPath.start.getX() - 1, previousPath.start.getY() - 1, previousPath.start.getZ()));
            addStep(stepEnd,previousPath);
        }


        // Sprawdza, czy jest możliwy krok w prawy dolny róg i dodaje do listy ścieżek
        if(GameManager.board.getFieldById(getIdFromCoordinates(previousPath.start.getX()+1,previousPath.start.getY()+1,previousPath.start.getZ()))!=null) {
            stepEnd = GameManager.board.getFieldById(getIdFromCoordinates(previousPath.start.getX() + 1, previousPath.start.getY() + 1, previousPath.start.getZ()));
            addStep(stepEnd,previousPath);
        }


        // Sprawdza, czy jest możliwy krok w lewy dolny róg i dodaje do listy ścieżek
        if(GameManager.board.getFieldById(getIdFromCoordinates(previousPath.start.getX()+1,previousPath.start.getY(),previousPath.start.getZ()+1))!=null) {
            stepEnd = GameManager.board.getFieldById(getIdFromCoordinates(previousPath.start.getX() + 1, previousPath.start.getY(), previousPath.start.getZ() + 1));
            addStep(stepEnd,previousPath);
        }
    }
}

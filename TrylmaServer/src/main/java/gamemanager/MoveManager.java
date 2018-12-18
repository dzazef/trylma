package gamemanager;
import serializable.Field;
import player.Pawn;
import serializable.MoveDestinations;

import java.util.ArrayList;

public class MoveManager {
    public static MoveDestinations moveDestinations;
    public static ArrayList<Path> paths;
    public static Pawn choosenPawn;
    public static void  generateMovePaths(Pawn pawn)
    {
        System.out.println("1");
        moveDestinations = new MoveDestinations();
        System.out.println("2");

        paths = new ArrayList<Path>();
        System.out.println("3");

        if(pawn == null)
        {
            System.out.println("Pawn jest nullem");
        }
        System.out.println(pawn.getId());
        System.out.println(GameManager.board.getFieldById(pawn.getId()).getId());
        step(GameManager.board.getFieldById(pawn.getId()));
        System.out.println("4");


        Path path = new Path();
        System.out.println("5");

        path.end = GameManager.board.getFieldById(pawn.getId());
        System.out.println("6");

        jump(path);
        System.out.println("7");


    }
    public static String getIdFromCoordinates(int x,int y,int z)
    {
        return x+","+y+","+z;
    }

    private static void addJump(Field jumpEnd, Field jumpOver, Path previousPath)
    {
        System.out.println("15");

        if(jumpEnd != null) {
            System.out.println("16");

            if(jumpOver.getState() == Field.State.TAKEN) {
                System.out.println("17");

                for (int i = 0; i < paths.size(); i++) {
                    if (paths.get(i).end == jumpEnd) {
                        return;
                    }
                }
                System.out.println("18");

                if (jumpEnd.getState() == Field.State.FREE) {
                    System.out.println("19");

                    Path path = new Path(previousPath.getPath());
                    path.addStepToPath(jumpEnd);
                    moveDestinations.addMoveDestination(jumpEnd);
                    paths.add(path);
                    jump(path);
                    System.out.println("20");

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
        System.out.println("8");

        Field whereWeAre = previousPath.end;
        Field jumpOver;
        Field jumpEnd;
        System.out.println("9");

        // Sprawdza czy jest możliwy skok w prawo i jak tak to go dodaje do liste ścieżek
        if(GameManager.board.getFieldById(getIdFromCoordinates(whereWeAre.getX(),whereWeAre.getY()+1,whereWeAre.getZ()-1))!=null &&
                GameManager.board.getFieldById(getIdFromCoordinates(whereWeAre.getX(),whereWeAre.getY()+2,whereWeAre.getZ()-2))!=null  ) {
             jumpOver = GameManager.board.getFieldById(getIdFromCoordinates(whereWeAre.getX(), whereWeAre.getY() + 1, whereWeAre.getZ() - 1));
             jumpEnd = GameManager.board.getFieldById(getIdFromCoordinates(whereWeAre.getX(), whereWeAre.getY() + 2, whereWeAre.getZ() - 2));
            addJump(jumpEnd, jumpOver, previousPath);
        }

        System.out.println("10");

        // Sprawdza czy jest możliwy skok w lewo i jak tak to go dodaje do listy ścieżek
        if(GameManager.board.getFieldById(getIdFromCoordinates(whereWeAre.getX(), whereWeAre.getY() - 1, whereWeAre.getZ() + 1))!=null &&
                GameManager.board.getFieldById(getIdFromCoordinates(whereWeAre.getX(), whereWeAre.getY() - 2, whereWeAre.getZ() + 2))!=null  ) {
            jumpOver = GameManager.board.getFieldById(getIdFromCoordinates(whereWeAre.getX(), whereWeAre.getY() - 1, whereWeAre.getZ() + 1));
            jumpEnd = GameManager.board.getFieldById(getIdFromCoordinates(whereWeAre.getX(), whereWeAre.getY() - 2, whereWeAre.getZ() + 2));
            addJump(jumpEnd, jumpOver, previousPath);
        }

        System.out.println("11");

        // Sprawdza czy jest możliwy skok w prawy górny róg i jak tak to go dodaje do listy ścieżek
        if(GameManager.board.getFieldById(getIdFromCoordinates(whereWeAre.getX() - 1, whereWeAre.getY(), whereWeAre.getZ() - 1))!=null &&
                GameManager.board.getFieldById(getIdFromCoordinates(whereWeAre.getX() - 2, whereWeAre.getY(), whereWeAre.getZ() - 2))!=null  ) {
            jumpOver = GameManager.board.getFieldById(getIdFromCoordinates(whereWeAre.getX() - 1, whereWeAre.getY(), whereWeAre.getZ() - 1));
            jumpEnd = GameManager.board.getFieldById(getIdFromCoordinates(whereWeAre.getX() - 2, whereWeAre.getY(), whereWeAre.getZ() - 2));
            addJump(jumpEnd, jumpOver, previousPath);
        }

        System.out.println("12");

        // Sprawdza czy jest możliwy skok w lewy górny róg i jak tak to go dodaje do listy ścieżek
        if(GameManager.board.getFieldById(getIdFromCoordinates(whereWeAre.getX() - 1, whereWeAre.getY() - 1, whereWeAre.getZ()))!=null &&
                GameManager.board.getFieldById(getIdFromCoordinates(whereWeAre.getX() - 2, whereWeAre.getY() - 2, whereWeAre.getZ()))!=null  ) {
            jumpOver = GameManager.board.getFieldById(getIdFromCoordinates(whereWeAre.getX() - 1, whereWeAre.getY() - 1, whereWeAre.getZ()));
            jumpEnd = GameManager.board.getFieldById(getIdFromCoordinates(whereWeAre.getX() - 2, whereWeAre.getY() - 2, whereWeAre.getZ()));
            addJump(jumpEnd, jumpOver, previousPath);
        }

        System.out.println("13");

        // Sprawdza czy jest możliwy skok w lewy dolny róg i jak tak to go dodaje do listy ścieżek
        if(GameManager.board.getFieldById(getIdFromCoordinates(whereWeAre.getX() + 1, whereWeAre.getY(), whereWeAre.getZ() + 1))!=null &&
                GameManager.board.getFieldById(getIdFromCoordinates(whereWeAre.getX() + 2, whereWeAre.getY(), whereWeAre.getZ() + 2))!=null  ) {
            jumpOver = GameManager.board.getFieldById(getIdFromCoordinates(whereWeAre.getX() + 1, whereWeAre.getY(), whereWeAre.getZ() + 1));
            jumpEnd = GameManager.board.getFieldById(getIdFromCoordinates(whereWeAre.getX() + 2, whereWeAre.getY(), whereWeAre.getZ() + 2));
            addJump(jumpEnd, jumpOver, previousPath);
        }

        System.out.println("14");

        // Sprawdza czy jest możliwy skok w prawy dolny róg i jak tak to go dodaje do listy ścieżek
        if(GameManager.board.getFieldById(getIdFromCoordinates(whereWeAre.getX() + 1, whereWeAre.getY() + 1, whereWeAre.getZ()))!=null &&
                GameManager.board.getFieldById(getIdFromCoordinates(whereWeAre.getX() + 2, whereWeAre.getY() + 2, whereWeAre.getZ()))!=null  ) {
            jumpOver = GameManager.board.getFieldById(getIdFromCoordinates(whereWeAre.getX() + 1, whereWeAre.getY() + 1, whereWeAre.getZ()));
            jumpEnd = GameManager.board.getFieldById(getIdFromCoordinates(whereWeAre.getX() + 2, whereWeAre.getY() + 2, whereWeAre.getZ()));
            addJump(jumpEnd, jumpOver, previousPath);
        }
    }
    private static void addStep(Field stepEnd)
    {
        System.out.println("27");

        if(stepEnd != null)
        {
            System.out.println("28");

            if(stepEnd.getState() == Field.State.FREE)
            {
                System.out.println("29");
                Path path = new Path();
                path.addStepToPath(stepEnd);
                moveDestinations.addMoveDestination(stepEnd);
                paths.add(path);
            }
        }


    }
    private static void step(Field field)
    {
        System.out.println("21");

        Field stepEnd;
        // Sprawdza, czy jest możliwy krok w prawo i dodaje do listy ścieżek
        if(GameManager.board.getFieldById(getIdFromCoordinates(field.getX(),field.getY()+1,field.getZ()-1))!=null) {
            stepEnd = GameManager.board.getFieldById(getIdFromCoordinates(field.getX(), field.getY() + 1, field.getZ() - 1));
            addStep(stepEnd);
        }
        System.out.println("22");

        // Sprawdza, czy jest możliwy krok w lewo i dodaje do listy ścieżek
        if(GameManager.board.getFieldById(getIdFromCoordinates(field.getX(),field.getY()-1,field.getZ()+1))!=null) {
            stepEnd = GameManager.board.getFieldById(getIdFromCoordinates(field.getX(), field.getY() - 1, field.getZ() + 1));
            addStep(stepEnd);
        }

        System.out.println("23");

        // Sprawdza, czy jest możliwy krok w prawy górny róg i dodaje do listy ścieżek
        if(GameManager.board.getFieldById(getIdFromCoordinates(field.getX()-1,field.getY(),field.getZ()-1))!=null) {
            stepEnd = GameManager.board.getFieldById(getIdFromCoordinates(field.getX() - 1, field.getY(), field.getZ() - 1));
            addStep(stepEnd);
        }

        System.out.println("24");

        // Sprawdza, czy jest możliwy krok w lewy górny róg i dodaje do listy ścieżek
        if(GameManager.board.getFieldById(getIdFromCoordinates(field.getX()-1,field.getY()-1,field.getZ()))!=null) {
            stepEnd = GameManager.board.getFieldById(getIdFromCoordinates(field.getX() - 1, field.getY() - 1, field.getZ()));
            addStep(stepEnd);
        }

        System.out.println("25");

        // Sprawdza, czy jest możliwy krok w prawy dolny róg i dodaje do listy ścieżek
        if(GameManager.board.getFieldById(getIdFromCoordinates(field.getX()+1,field.getY()+1,field.getZ()))!=null) {
            stepEnd = GameManager.board.getFieldById(getIdFromCoordinates(field.getX() + 1, field.getY() + 1, field.getZ()));
            addStep(stepEnd);
        }

        System.out.println("26");

        // Sprawdza, czy jest możliwy krok w lewy dolny róg i dodaje do listy ścieżek
        if(GameManager.board.getFieldById(getIdFromCoordinates(field.getX()+1,field.getY(),field.getZ()+1))!=null) {
            stepEnd = GameManager.board.getFieldById(getIdFromCoordinates(field.getX() + 1, field.getY(), field.getZ() + 1));
            addStep(stepEnd);
        }
    }
}

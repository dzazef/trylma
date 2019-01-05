package gamemanager;
import serializable.Field;
import player.Pawn;
import serializable.FieldsSet;

import java.util.ArrayList;

/**
 * MoveManager is an abstract class that contains methods needed for moving pawns on the board.
 */
public abstract class MoveManager {
    /**
     * Array that contains fields with destinations distinct pawn can go to.
     */
    private static FieldsSet moveDestinations;
    /**
     * Array containing paths that pawn can go through.
     */
    private static ArrayList<FieldsSet> paths;
    /**
     * The players chosen pawn.
     */
    private static Pawn choosenPawn;

    /**
     * Function that sets pawn, that player chooses.
     * @param choosenPawn Pawn that player chooses.
     */
    public static void setChoosenPawn(Pawn choosenPawn) {
        MoveManager.choosenPawn = choosenPawn;
    }

    /**
     * Function that sets pawn move destination.
     * @param moveDestinations New move destination.
     */
    public static void setMoveDestinations(FieldsSet moveDestinations) {
        MoveManager.moveDestinations = moveDestinations;
    }

    /**
     * Function that sets paths, the pawn can go.
     * @param paths New paths array.
     */
    public static void setPaths(ArrayList<FieldsSet> paths) {
        MoveManager.paths = paths;
    }

    /**
     * Function returns chosen pawn move destination.
     * @return pawn move destination.
     */
    public static FieldsSet getMoveDestinations() {
        return moveDestinations;
    }

    /**
     * Function returns paths pawn can go through.
     * @return paths pawn can go.
     */
    public static ArrayList<FieldsSet> getPaths() {
        return paths;
    }

    /**
     * Function returns actually chosen pawn.
     * @return actually chosen pawn.
     */
    public static Pawn getChoosenPawn() {
        return choosenPawn;
    }

    /**
     * Function generates paths distinct pawn can go, and saves date in adequate variables.
     * @param pawn Pawn for which paths will be generated.
     */
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
            path.addField(GameManager.getBoard().getFieldById(pawn.getId()));
            step(path);

            FieldsSet path2 = new FieldsSet();
            path2.addField(GameManager.getBoard().getFieldById(pawn.getId()));
            jump(path2);

        }
    }

    /**
     * Additional function that helps other function by converting coordinates into id.
     * @param x x - coordinate.
     * @param y y - coordinate.
     * @param z z - coordinate.
     * @return Returns id in String.
     */
    private static String getIdFromCoordinates(int x, int y, int z)
    {
        return x+","+y+","+z;
    }

    /**
     * Function checks if there is already path in paths array that leads to given field.
     * @param field Field to be checked.
     * @return boolean true/false depending if there already is such path or not.
     */
    private static boolean alreadyInSet(Field field)
    {
        for(FieldsSet set : MoveManager.paths)
        {
            if (field.getId().equals(set.getEnd().getId()))
            {
                return true;
            }
        }
        return false;
    }

    /**
     * Function, after checking if the given date is ok, adds new jump to path array and new destination to moveDestination array.
     * @param jumpEnd The destination of the jump.
     * @param jumpOver The field the pawn wants to jump over.
     * @param previousPath The previous path, that leads to the place, from where we are trying to jum now.
     * @return Returns path expanded of another jump.
     */
    private static FieldsSet addJump(Field jumpEnd, Field jumpOver, FieldsSet previousPath)
    {
        if(jumpEnd != null && !alreadyInSet(jumpEnd) && jumpOver != null &&jumpOver.getState() == Field.State.TAKEN) {
            if (jumpEnd.getState() == Field.State.FREE) {
                FieldsSet path = new FieldsSet(previousPath.getPath());
                path.addField(jumpEnd);
                moveDestinations.addField(jumpEnd);
                paths.add(path);
                return path;
            }
        }
        return null;
    }

    /**
     * Function that recursively tries to jump in every direction starting from point where pawn stands in this scope.
     * @param previousPath Previous path leading to the place where pawn is now.
     */
    private static void jump(FieldsSet previousPath)
    {

        FieldsSet set1 = null ,set2 = null ,set3 = null,set4 = null,set5 = null,set6 = null;
        Field whereWeAre = previousPath.getEnd();
        Field jumpOver;
        Field jumpEnd;

        // Sprawdza czy jest możliwy skok w prawo i jak tak to go dodaje do liste ścieżek
        if(GameManager.getBoard().getFieldById(getIdFromCoordinates(whereWeAre.getX(),whereWeAre.getY()+1,whereWeAre.getZ()-1))!=null &&
                GameManager.getBoard().getFieldById(getIdFromCoordinates(whereWeAre.getX(),whereWeAre.getY()+2,whereWeAre.getZ()-2))!=null  ) {
             jumpOver = GameManager.getBoard().getFieldById(getIdFromCoordinates(whereWeAre.getX(), whereWeAre.getY() + 1, whereWeAre.getZ() - 1));
             jumpEnd = GameManager.getBoard().getFieldById(getIdFromCoordinates(whereWeAre.getX(), whereWeAre.getY() + 2, whereWeAre.getZ() - 2));
            set1  = addJump(jumpEnd, jumpOver, previousPath);
        }



        // Sprawdza czy jest możliwy skok w lewo i jak tak to go dodaje do listy ścieżek
        if(GameManager.getBoard().getFieldById(getIdFromCoordinates(whereWeAre.getX(), whereWeAre.getY() - 1, whereWeAre.getZ() + 1))!=null &&
                GameManager.getBoard().getFieldById(getIdFromCoordinates(whereWeAre.getX(), whereWeAre.getY() - 2, whereWeAre.getZ() + 2))!=null  ) {
            jumpOver = GameManager.getBoard().getFieldById(getIdFromCoordinates(whereWeAre.getX(), whereWeAre.getY() - 1, whereWeAre.getZ() + 1));
            jumpEnd = GameManager.getBoard().getFieldById(getIdFromCoordinates(whereWeAre.getX(), whereWeAre.getY() - 2, whereWeAre.getZ() + 2));
            set2 = addJump(jumpEnd, jumpOver, previousPath);
        }


        // Sprawdza czy jest możliwy skok w prawy górny róg i jak tak to go dodaje do listy ścieżek
        if(GameManager.getBoard().getFieldById(getIdFromCoordinates(whereWeAre.getX() - 1, whereWeAre.getY(), whereWeAre.getZ() - 1))!=null &&
                GameManager.getBoard().getFieldById(getIdFromCoordinates(whereWeAre.getX() - 2, whereWeAre.getY(), whereWeAre.getZ() - 2))!=null  ) {
            jumpOver = GameManager.getBoard().getFieldById(getIdFromCoordinates(whereWeAre.getX() - 1, whereWeAre.getY(), whereWeAre.getZ() - 1));
            jumpEnd = GameManager.getBoard().getFieldById(getIdFromCoordinates(whereWeAre.getX() - 2, whereWeAre.getY(), whereWeAre.getZ() - 2));
            set3 = addJump(jumpEnd, jumpOver, previousPath);

        }


        // Sprawdza czy jest możliwy skok w lewy górny róg i jak tak to go dodaje do listy ścieżek
        if(GameManager.getBoard().getFieldById(getIdFromCoordinates(whereWeAre.getX() - 1, whereWeAre.getY() - 1, whereWeAre.getZ()))!=null &&
                GameManager.getBoard().getFieldById(getIdFromCoordinates(whereWeAre.getX() - 2, whereWeAre.getY() - 2, whereWeAre.getZ()))!=null  ) {
            jumpOver = GameManager.getBoard().getFieldById(getIdFromCoordinates(whereWeAre.getX() - 1, whereWeAre.getY() - 1, whereWeAre.getZ()));
            jumpEnd = GameManager.getBoard().getFieldById(getIdFromCoordinates(whereWeAre.getX() - 2, whereWeAre.getY() - 2, whereWeAre.getZ()));
            set4= addJump(jumpEnd, jumpOver, previousPath);
        }



        // Sprawdza czy jest możliwy skok w lewy dolny róg i jak tak to go dodaje do listy ścieżek
        if(GameManager.getBoard().getFieldById(getIdFromCoordinates(whereWeAre.getX() + 1, whereWeAre.getY(), whereWeAre.getZ() + 1))!=null &&
                GameManager.getBoard().getFieldById(getIdFromCoordinates(whereWeAre.getX() + 2, whereWeAre.getY(), whereWeAre.getZ() + 2))!=null  ) {
            jumpOver = GameManager.getBoard().getFieldById(getIdFromCoordinates(whereWeAre.getX() + 1, whereWeAre.getY(), whereWeAre.getZ() + 1));
            jumpEnd = GameManager.getBoard().getFieldById(getIdFromCoordinates(whereWeAre.getX() + 2, whereWeAre.getY(), whereWeAre.getZ() + 2));
            set5 = addJump(jumpEnd, jumpOver, previousPath);
        }



        // Sprawdza czy jest możliwy skok w prawy dolny róg i jak tak to go dodaje do listy ścieżek
        if(GameManager.getBoard().getFieldById(getIdFromCoordinates(whereWeAre.getX() + 1, whereWeAre.getY() + 1, whereWeAre.getZ()))!=null &&
                GameManager.getBoard().getFieldById(getIdFromCoordinates(whereWeAre.getX() + 2, whereWeAre.getY() + 2, whereWeAre.getZ()))!=null  ) {
            jumpOver = GameManager.getBoard().getFieldById(getIdFromCoordinates(whereWeAre.getX() + 1, whereWeAre.getY() + 1, whereWeAre.getZ()));
            jumpEnd = GameManager.getBoard().getFieldById(getIdFromCoordinates(whereWeAre.getX() + 2, whereWeAre.getY() + 2, whereWeAre.getZ()));
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

    /**
     * Function adds step to paths array and new destination to destination array if given data fulfills conditions.
     * @param stepEnd Step destination.
     * @param previousPath Previous path leading to point where the pawn is. In step case it has only start position of pawn, but client needs FieldsSet to be send.
     */
    private static void addStep(Field stepEnd,FieldsSet previousPath)
    {
        if(stepEnd != null && !alreadyInSet(stepEnd) && stepEnd.getState() == Field.State.FREE) {
            FieldsSet path = new FieldsSet(previousPath.getPath());
            path.addField(stepEnd);
            moveDestinations.addField(stepEnd);
            paths.add(path);
        }
    }

    /**
     * Function tries to add step to paths array and new destination to move destination array.
     * @param previousPath Previous path leading to point where the pawn is. In step case it has only start position of pawn, but client needs FieldsSet to be send.
     */
    private static void step(FieldsSet previousPath)
    {

        Field stepEnd;
        // Sprawdza, czy jest możliwy krok w prawo i dodaje do listy ścieżek
        if(GameManager.getBoard().getFieldById(getIdFromCoordinates(previousPath.getStart().getX(),previousPath.getStart().getY()+1,previousPath.getStart().getZ()-1))!=null) {
            stepEnd = GameManager.getBoard().getFieldById(getIdFromCoordinates(previousPath.getStart().getX(), previousPath.getStart().getY() + 1, previousPath.getStart().getZ() - 1));
            addStep(stepEnd,previousPath);
        }

        // Sprawdza, czy jest możliwy krok w lewo i dodaje do listy ścieżek
        if(GameManager.getBoard().getFieldById(getIdFromCoordinates(previousPath.getStart().getX(),previousPath.getStart().getY()-1,previousPath.getStart().getZ()+1))!=null) {
            stepEnd = GameManager.getBoard().getFieldById(getIdFromCoordinates(previousPath.getStart().getX(), previousPath.getStart().getY() - 1, previousPath.getStart().getZ() + 1));
            addStep(stepEnd,previousPath);
        }


        // Sprawdza, czy jest możliwy krok w prawy górny róg i dodaje do listy ścieżek
        if(GameManager.getBoard().getFieldById(getIdFromCoordinates(previousPath.getStart().getX()-1,previousPath.getStart().getY(),previousPath.getStart().getZ()-1))!=null) {
            stepEnd = GameManager.getBoard().getFieldById(getIdFromCoordinates(previousPath.getStart().getX() - 1, previousPath.getStart().getY(), previousPath.getStart().getZ() - 1));
            addStep(stepEnd,previousPath);
        }



        // Sprawdza, czy jest możliwy krok w lewy górny róg i dodaje do listy ścieżek
        if(GameManager.getBoard().getFieldById(getIdFromCoordinates(previousPath.getStart().getX()-1,previousPath.getStart().getY()-1,previousPath.getStart().getZ()))!=null) {
            stepEnd = GameManager.getBoard().getFieldById(getIdFromCoordinates(previousPath.getStart().getX() - 1, previousPath.getStart().getY() - 1, previousPath.getStart().getZ()));
            addStep(stepEnd,previousPath);
        }


        // Sprawdza, czy jest możliwy krok w prawy dolny róg i dodaje do listy ścieżek
        if(GameManager.getBoard().getFieldById(getIdFromCoordinates(previousPath.getStart().getX()+1,previousPath.getStart().getY()+1,previousPath.getStart().getZ()))!=null) {
            stepEnd = GameManager.getBoard().getFieldById(getIdFromCoordinates(previousPath.getStart().getX() + 1, previousPath.getStart().getY() + 1, previousPath.getStart().getZ()));
            addStep(stepEnd,previousPath);
        }


        // Sprawdza, czy jest możliwy krok w lewy dolny róg i dodaje do listy ścieżek
        if(GameManager.getBoard().getFieldById(getIdFromCoordinates(previousPath.getStart().getX()+1,previousPath.getStart().getY(),previousPath.getStart().getZ()+1))!=null) {
            stepEnd = GameManager.getBoard().getFieldById(getIdFromCoordinates(previousPath.getStart().getX() + 1, previousPath.getStart().getY(), previousPath.getStart().getZ() + 1));
            addStep(stepEnd,previousPath);
        }
    }
}

package gamemanager.board;

import player.Pawn;
import serializable.Field;

import java.util.ArrayList;
import java.util.List;

/**
 * Board class, as the name suggests, creates a Board objects, that plays role of board for chinese checkers game. It stores the fields with information if they are taken or free.
 */
public class Board {
    /**
     * List, of board fields.
     */
    private List fields;

    public Board()
    {
        this.fields = new ArrayList<>();
    }

    /**
     * Function that moves pawn on the board. Changes pawn start field state to FREE, and pawn end field state to TAKEN.
     * @param from Pawn start field.
     * @param destination Pawn end field.
     */
    public final void move(Pawn from, Field destination)
    {
        Field field = getFieldById(destination.getId());
        field.changeState();
        field = getFieldById(from.getId());
        field.changeState();
    }

    /**
     * Function that advises other ones by returning Field by its id.
     * @param id Searched Field id.
     * @return Field found by its id.
     */
    public Field getFieldById(String id)
    {
        for (Object field : fields) {
            if (((Field)field).getId().equals(id)) {
                return (Field)field;
            }
        }
        return null;
    }

    /**
     * Function that initializes the whole board, depending on numOfPawns magic number, that defines how board looks.
     * @param numOfPawns Magic number, that indicates the look of the board. It is number of pawns on side of the players home space.
     */
    public void build(int numOfPawns)
    {
        /*
         \--------------/
          \            /
           \          /
            \        /
             \      /
              \    /
               \  /
                \/
         */
        int initialy = numOfPawns;
        int initialz = -2*numOfPawns;
        int x = -numOfPawns;
        for(int k = 3*numOfPawns + 1; x<=2*numOfPawns+1;x++,k--)
        {
            int y = initialy;
            int z = initialz;
            for(int i = 0; i<k;i++)
            {
                Field field = new Field(x,y,z,false);
                fields.add(field);
                y--;
                z++;
            }

            initialz++;
        }

        /*    /\
             /  \
            /____\

          /\      /\
         /  \    /  \
        /____\  /____\
         */
        initialy = 2*numOfPawns;
        initialz = -numOfPawns;
        x = numOfPawns;
        for(int k = 3*numOfPawns + 1; x>=-2*numOfPawns - 1;x--,k--) {
            int y = initialy;
            int z = initialz;
            for (int i = 0; i < k; i++) {

                if(x <= -(numOfPawns + 1)||y >= (numOfPawns + 1) || z >= (numOfPawns + 1))
                {
                    Field field = new Field(x,y,z,false);
                    fields.add(field);
                }
                y--;
                z++;
            }
            initialy--;
        }
    }
}

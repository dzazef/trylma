package gamemanager.board;

import player.Pawn;
import serializable.Field;

import java.util.ArrayList;

public class Board {
    public ArrayList<Field> fields;

    public Board()
    {
        this.fields = new ArrayList<Field>();
    }
    public void move(Pawn from, Field destination)
    {
        Field field = getFieldById(destination.getId());
        field.changeState();
        field = getFieldById(from.getId());
        field.changeState();
    }
    public Field getFieldById(String id)
    {
        for(int i = 0; i < fields.size(); i++)
        {
            if(fields.get(i).getId().equals(id))
            {
                return fields.get(i);
            }
        }
        return null;
    }
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

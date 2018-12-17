package connector;

import gamemanager.GameManager;
import gamemanager.MoveManager;
import serializable.Field;
import player.Pawn;
import player.Player;

import java.io.*;

public class Command {
    private ObjectOutputStream objout;




    public Command(ObjectOutputStream objectout)
    {
        this.objout = objectout;

    }

    public void sendWinMessage(Player winner)
    {
        String message = "won;"+winner.getId();
        try {
            objout.writeObject(message);
        }
        catch (IOException e)
        {
            System.out.println("Failed to send won message: IOExcception" + e);
        }

    }

    public void sendMoveMessage(Pawn pawn, Field destination)
    {
        for(int i = 0; i< MoveManager.paths.size();i++)
        {
            if(MoveManager.paths.get(i).end.getId() == destination.getId())
            {
                for(int j = 0; j < GameManager.playersobjout.size();j++) {
                    String message = "moved;" + GameManager.actualplayer.getId();

                    try {
                        GameManager.playersobjout.get(j).writeObject(message);
                        GameManager.playersobjout.get(j).writeObject(MoveManager.paths.get(i));
                    } catch (IOException e) {
                        System.out.println("failed to send path object: IOException" + e);
                    }
                }
                return;
            }
        }
    }
    public void sendPossibleMovesMessage(Field field)
    {
        Pawn pawn =GameManager.actualplayer.getPawnById(field.getId());
        MoveManager.generateMovePaths(pawn);
        try {
            objout.writeObject("possiblefields");
            objout.writeObject(MoveManager.moveDestinations);
        }
        catch(IOException e)
        {
            System.out.println("Sending possible move destinations failed");
        }
    }
}

package connector;

import gamemanager.GameManager;
import gamemanager.MoveManager;
import serializable.Field;
import player.Pawn;
import player.Player;

import java.io.*;

public class Command {
    private ObjectOutputStream objout;
    private ObjectInputStream objin;



    Command(ObjectOutputStream objectout, ObjectInputStream objectin)
    {
        this.objout = objectout;
        this.objin = objectin;

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

    void sendMoveMessage(Pawn pawn, Field destination)
    {
        for(int i = 0; i< MoveManager.paths.size();i++)
        {
            if(MoveManager.paths.get(i).end.getId().equals(destination.getId()))
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
    void sendPossibleMovesMessage(Field field)
    {
        System.out.println("Jestem w metodzie co sciezki wysyle");
        Pawn pawn =GameManager.actualplayer.getPawnById(field.getId());

        System.out.println(GameManager.actualplayer.getId());
        System.out.println(GameManager.actualplayer.getPawnById(field.getId()).getId());
        if(pawn == null)
        {
            System.out.println("Juz tutaj pawn jest nullem");
        }
        MoveManager.generateMovePaths(pawn);
        System.out.println("Tworzę ścieżki i próbuję je wysłać");
        try {
            objout.writeObject("possiblefields");
                objout.writeObject(MoveManager.moveDestinations);

        }
        catch(Exception e)
        {
            System.out.println("Sending possible move destinations failed");
        }
    }
}

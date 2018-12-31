package connector;

import gamemanager.GameManager;
import gamemanager.MoveManager;
import gamemanager.Path;
import serializable.Field;
import player.Pawn;
import player.Player;
import serializable.FieldsSet;

import java.io.*;

public class Command {
    private ObjectOutputStream objout;




    Command(ObjectOutputStream objectout)
    {
        this.objout = objectout;
    }

    public void sendWinMessage(Player winner)
    {
        for(int j = 0; j < GameManager.playersobjout.size();j++) {
            String message = "won:" + winner.getId();

            try {
                GameManager.playersobjout.get(j).writeObject(message);
            } catch (IOException e) {
                System.out.println("failed to send path object: IOException" + e);
            }
        }

    }

    void move(FieldsSet path)
    {
        System.out.println("Wysyłam ścieżkę");
        for(int j = 0; j < GameManager.playersobjout.size();j++) {
            String message = "moved:" + GameManager.actualplayer.getId();

            try {
                GameManager.playersobjout.get(j).writeObject(message);
                GameManager.playersobjout.get(j).writeObject(path);
            } catch (IOException e) {
                System.out.println("failed to send path object: IOException" + e);
            }
        }
        return;
    }

    void sendMoveMessage( Field destination)
    {
        if(!(GameManager.actualplayer.isBot())) {
            for (int i = 0; i < MoveManager.paths.size(); i++) {
                System.out.println(i);
                if (MoveManager.paths.get(i).end.getId().equals(destination.getId())) {
                    move(MoveManager.paths.get(i));
                }
            }
        }else
        {
            move(GameManager.actualplayer.getBotchoosenpath());
        }
    }
    void sendPossibleMovesMessage(Field field)
    {

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
            objout.writeObject("possible_fields");
                objout.writeObject(MoveManager.moveDestinations);

        }
        catch(Exception e)
        {
            System.out.println("Sending possible move destinations failed");
        }
    }
}

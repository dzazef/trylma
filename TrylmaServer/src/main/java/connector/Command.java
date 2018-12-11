package connector;

import gamemanager.GameManager;
import gamemanager.MoveManager;
import gamemanager.Path;
import gamemanager.board.Board;
import gamemanager.board.Field;
import player.Pawn;
import player.Player;

import java.io.*;

public class Command {
    private ObjectOutputStream objout;
    private PrintWriter out;



    public Command(ObjectOutputStream objectout,PrintWriter out)
    {
        this.objout = objectout;
        this.out = out;
    }

    public void sendWinMessage(Player winner)
    {
        out.println("won;"+winner.getId());

    }

    public void sendMoveMessage(Pawn pawn, Field destination)
    {
        for(int i = 0; i< MoveManager.paths.size();i++)
        {
            if(MoveManager.paths.get(i).end.getId() == destination.getId())
            {
                for(int j = 0; j < GameManager.playersobjout.size();j++) {
                    GameManager.playersout.get(j).println("moved;" + GameManager.actualplayer.getId());
                    try {
                        GameManager.playersobjout.get(j).writeObject(MoveManager.paths.get(i));
                    } catch (IOException e) {
                        System.out.println("failed to send path object");
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
            out.println("possible_fields");
            objout.writeObject(MoveManager.moveDestinations);
        }
        catch(IOException e)
        {
            System.out.println("Sending possible move destinations failed");
        }
    }
}

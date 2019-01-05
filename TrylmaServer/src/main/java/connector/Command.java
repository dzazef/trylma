package connector;

import gamemanager.GameManager;
import gamemanager.MoveManager;
import serializable.Field;
import player.Pawn;
import player.Player;
import serializable.FieldsSet;

import java.io.*;

/**
 * Command class is responsible for generating and sending messages to clients, and sometimes indirectly checking correctness of received
 * data. Every instance of Command class has its unique ObjectOutputStream corresponding to unique client connected.
 */
class Command {
    /**
     * ObjectOutputStream of client the command will send messages to.
     */
    private ObjectOutputStream objout;

    /**
     * Constructor
     * @param objectout ObjectOutputStream of client.
     */
    Command(ObjectOutputStream objectout)
    {
        this.objout = objectout;
    }

    /**
     * Function responsible for sending win message.
     * @param winner
     */
    void sendWinMessage(Player winner) throws IOException
    {
        for(int j = 0; j < GameManager.getPlayersobjout().size();j++) {
            String message = "won:" + winner.getId();

            try {
                GameManager.getPlayersobjout().get(j).writeObject(message);
            } catch (IOException e) {
                throw new IOException("failed to send path object: IOException" + e);
            }
        }

    }

    /**
     * Additional function that supports executing sendMoveMessage() function. It simply sends given FieldsSet, with adequate message
     * to all clients connected.
     * @param path given path the pawn will go.
     */
    private void move(FieldsSet path) throws IOException
    {
        for(int j = 0; j < GameManager.getPlayersobjout().size();j++) {
            String message = "moved:" + GameManager.getActualplayer().getId();

            try {
                GameManager.getPlayersobjout().get(j).writeObject(message);
                GameManager.getPlayersobjout().get(j).writeObject(path);
            } catch (IOException e) {
                throw new IOException("failed to send path object: IOException" + e);
            }
        }
    }

    /**
     * Function responsible for sending move message to all clients. It receives the destination the pawn is going to (regardless if it is
     * destination the player or bot chooses) and finds suitable path from paths generated earlier. During executing this function, the
     * fragment of code responsible for finding thee suitable path also plays a role of checking if received destination is even possible
     * to be moved to, and therefor it checks the correctness of messages received from clients.
     * @param destination Given destination the player is moving its chosen pawn to.
     */
    void sendMoveMessage( Field destination) throws IOException
    {
        if(!(GameManager.getActualplayer().isBot())) {
            for (int i = 0; i < MoveManager.getPaths().size(); i++) {
                if (MoveManager.getPaths().get(i).getEnd().getId().equals(destination.getId())) {
                    move(MoveManager.getPaths().get(i));
                    return;
                }

            }
            System.out.println("ERROR: Client send impossible move destination");
        }else
        {
            move(GameManager.getActualplayer().getBotchoosenpath());
        }
    }

    /**
     * Function sends an array with possible fields to client, given the field that player chooses.
     * @param field Field the player chooses.
     */
    void sendPossibleMovesMessage(Field field) throws IOException
    {
        Pawn pawn =GameManager.getActualplayer().getPawnById(field.getId());
          if(pawn == null)
        {
            System.out.println("Juz tutaj pawn jest nullem");
        }
        MoveManager.generateMovePaths(pawn);
        try {
            objout.writeObject("possible_fields");
            objout.writeObject(MoveManager.getMoveDestinations());
        }
        catch(Exception e)
        {
            throw new IOException("Sending possible move destinations failed");
        }
    }

    /**
     * Function sends failure message if something goes terribly wrong during game.
     */
    void sendFailureMessage()
    {
        for(int j = 0; j < GameManager.getPlayersobjout().size();j++) {
            String message = "failure";
            try {
                GameManager.getPlayersobjout().get(j).writeObject(message);
            } catch (IOException e) {
                System.out.println("failed to send failure message" + e);
            }
        }
    }

}

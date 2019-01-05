package connector;

import gamemanager.GameManager;
import gamemanager.MoveManager;
import player.Bot;
import player.Player;
import serializable.Field;

import java.io.IOException;
import java.io.ObjectInputStream;

/**
 * Part of chain of responsibility pattern, responsible for handling endfield command.
 */
public class EndfieldMessage implements MessageChain {
    private MessageChain chain;

    /**
     * Function setting next chain link in chain of responsibility.
     * @param nextChain Next chain link.
     */
    @Override
    public void setNextChain(MessageChain nextChain) {
        this.chain = nextChain;
    }

    /**
     * Function responsible for handling endfield command.
     * @param message Message received from client.
     * @param objectInputStream Input stream, from which function reads the chosen end field.
     * @param command Command object, by which move message is send.
     * @param player Player object, whose pawn is moved.
     * @throws IOException
     */
    @Override
    public void handleMessage(String message, ObjectInputStream objectInputStream, Command command, Player player) throws IOException {
        if (message.startsWith("endfield")) {
            Field field;
            try {
                field = (Field) objectInputStream.readObject();
            } catch (Exception e) {
                throw new IOException("Failed to read object for endfield " + e + " : " + player.getId());
            }
            command.sendMoveMessage(field);
            for (int i = 0; i < MoveManager.getPaths().size(); i++) {
                if (MoveManager.getPaths().get(i).getEnd().getId().equals(field.getId())) {
                    Bot.delay = MoveManager.getPaths().get(i).getPath().size() * 300;
                }
            }
            GameManager.getBoard().move(MoveManager.getChoosenPawn(), field);
            GameManager.getActualplayer().movePawn(MoveManager.getChoosenPawn(), field);
            GameManager.nextPlayer();
        }
        else
        {
            throw new IOException("Unknown command");
        }
    }


}

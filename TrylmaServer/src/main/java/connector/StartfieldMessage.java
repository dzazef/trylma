package connector;

import gamemanager.GameManager;
import gamemanager.MoveManager;
import player.Player;
import serializable.Field;

import java.io.IOException;
import java.io.ObjectInputStream;

/**
 * Part of chain of responsibility pattern, responsible for handling startfield command.
 */
public class StartfieldMessage implements MessageChain {
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
     * Function responsible for handling startfield command.
     * @param message Message received from client.
     * @param objectInputStream Input stream, from which function reads the chosen start field.
     * @param command Command object, does't needed here.
     * @param player Player object, whose pawn is moved.
     * @throws IOException
     */
    @Override
    public void handleMessage(String message, ObjectInputStream objectInputStream, Command command, Player player) throws IOException {
        if (message.startsWith("startfield")) {
            Field field;
            try {
                field = (Field) objectInputStream.readObject();
            } catch (Exception e) {
                throw new IOException("Failed to read Field class object for startfield " + e + " : " + player.getId());
            }
            MoveManager.setChoosenPawn(player.getPawnById(field.getId()));
            command.sendPossibleMovesMessage(field);
            MoveManager.setChoosenPawn(GameManager.getActualplayer().getPawnById(field.getId()));
        }
        else
        {
            if(this.chain != null) {
                this.chain.handleMessage(message, objectInputStream, command, player);
            }
        }
    }

}

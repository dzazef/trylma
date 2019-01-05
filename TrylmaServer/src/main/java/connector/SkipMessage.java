package connector;

import gamemanager.GameManager;
import player.Player;

import java.io.IOException;
import java.io.ObjectInputStream;

/**
 * Part of chain of responsibility pattern, responsible for handling skip command.
 */
public class SkipMessage implements MessageChain {
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
     * Function responsible for handling skip command.
     * @param message Message received from client.
     * @param objectInputStream Input stream, doesn't needed here.
     * @param command Command object, doesn't needed here.
     * @param player Player object, doesn't needed here.
     * @throws IOException
     */
    @Override
    public void handleMessage(String message, ObjectInputStream objectInputStream, Command command, Player player) throws IOException {
        if (message.startsWith("skip")) {
            GameManager.nextPlayer();
        }
        else
        {
            if(this.chain != null) {
                this.chain.handleMessage(message, objectInputStream, command, player);
            }
        }
    }

}

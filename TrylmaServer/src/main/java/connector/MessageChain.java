package connector;

import player.Player;

import java.io.IOException;
import java.io.ObjectInputStream;

/**
 * Interface for objects in chain of responsibility.
 */
public interface MessageChain {
    void setNextChain(MessageChain nextChain);
    void handleMessage(String message, ObjectInputStream objectInputStream, Command command, Player player) throws IOException;
}

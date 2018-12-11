package Server;

import connector.Connector;
import gamemanager.GameManager;
import gamemanager.board.Board;

public class Server {
    public static void main(String[] args) {
        Connector connector = new Connector();
        GameManager.initialize();
        connector.communicationLoop();

    }
}

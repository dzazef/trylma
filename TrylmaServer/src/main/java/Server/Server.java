package Server;

import connector.Connector;
import gamemanager.GameManager;


public class Server {
    public static void main(String[] args) {
        Connector connector = new Connector();
        GameManager.initialize();
        connector.communicationLoop();

    }
}

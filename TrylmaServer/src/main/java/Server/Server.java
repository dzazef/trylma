package Server;

import connector.Connector;

public class Server {
    public static void main(String[] args) {
        Connector connector = new Connector();
        connector.communicationLoop();
    }
}

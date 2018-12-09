package Server;

import connector.Connector;
import gamemanager.GameManager;
import gamemanager.board.Board;

public class Server {
    public static void main(String[] args) {
        Connector connector = new Connector();
        connector.communicationLoop();
        GameManager.board = new Board();
        GameManager.board.build(2);
        System.out.println(GameManager.board.fields.size());
        for(int i = 0; i<GameManager.board.fields.size();i++)
        {
            System.out.println(GameManager.board.fields.get(i).getId());
        }
    }
}

package gamemanager;

import gamemanager.board.Board;
import player.Pawn;
import player.Player;

import java.util.ArrayList;

public abstract class GameManager {
    public Board board;
    public static ArrayList<Player> players;
    public ArrayList<Path> paths;

    public Player checkWin()
    {
        return null;
    }
    public void initialize(int numOfPawns, ArrayList<Player> players )
    {

    }
    public ArrayList<Path> generateMovePaths(Pawn pawn)
    {
        return null;
    }
    private void jump(Path previousPath)
    {

    }
}

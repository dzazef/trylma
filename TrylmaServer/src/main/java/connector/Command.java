package connector;

import gamemanager.Path;
import gamemanager.board.Board;
import gamemanager.board.Field;
import player.Pawn;
import player.Player;

public class Command {
    private String message;
    private String object;
    private enum commandType{
        PATH,WIN,ACTUALIZE_BOARD,BOT
    }

    public String getMessage()
    {
        return "Suck my dick";
    }
    public void generatePathMessage(Path path)
    {

    }
    public void generateWinMessage(boolean win, Player winner)
    {

    }
    public void generateActualizeBoardMessage(Board board)
    {

    }
    public void generateMoveMessage(Pawn pawn, Field destination,Path path)
    {

    }
}

package player;

import gamemanager.Path;
import gamemanager.board.Field;

import java.util.ArrayList;

public interface Player {


     String getId();
     boolean checkWin();
     void botMove(Pawn pawn, Field destination, Path path);
     void movePawn(Pawn pawn,Field destination);


}

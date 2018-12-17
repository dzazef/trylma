package player;

import gamemanager.Path;
import serializable.Field;

import java.util.ArrayList;


public interface Player {
      ArrayList<Pawn> getPawns();
      boolean isBot() ;
      Pawn getBotchoosenpawn() ;
      Field getBotchoosendestination() ;
      Path getBotchoosenpath() ;

      Pawn getPawnById(String id);
     String getId();
     boolean checkWin();
     void botMove();
     void movePawn(Pawn pawn,Field destination);


}

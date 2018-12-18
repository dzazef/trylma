package player;


import serializable.Field;
import serializable.FieldsSet;

import java.util.ArrayList;


public interface Player {
      ArrayList<Pawn> getPawns();
      boolean isBot() ;
      Pawn getBotchoosenpawn() ;
      Field getBotchoosendestination() ;
      FieldsSet getBotchoosenpath() ;

      Pawn getPawnById(String id);
     String getId();
     boolean checkWin();
     void botMove();
     void movePawn(Pawn pawn,Field destination);


}

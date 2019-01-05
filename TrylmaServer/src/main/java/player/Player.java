package player;


import serializable.Field;
import serializable.FieldsSet;

import java.util.ArrayList;

/**
 * Player interface, defining players properties. Part of Factory Method design pattern.
 */
public interface Player {
    /**
     * Function returns boolean variable that stores information if player is a bot player or not.
     * @return true if player is bot, false if player is not bot.
     */
    boolean isBot();

    /**
     * Function that returns pawn, that bot player has chosen.
     * @return Pawn chosen by bot.
     */
    Pawn getBotchoosenpawn();

    /**
     * Function returns destination to which bots chosen pawn is going.
     * @return Bot chosen destination.
     */
    Field getBotchoosendestination();

    /**
     * Function returns path which bot chooses. Because every bot follows its own path...
     * @return Bot chosen path.
     */
    FieldsSet getBotchoosenpath();

    /**
     * Function returns list of players pawns.
     * @return list of pawns.
     */
    ArrayList<Pawn> getPawns();

    /**
     * Function sets bot chosen path variable and bot chosen pawn variable to null.
     */
    void clear();

    /**
     * Function sets bot chosen pawn variable.
     * @param pawn New bot chosen pawn.
     */
    void setBotchoosenpawn(Pawn pawn);

    /**
     * Function sets bot chosen destination variable.
     * @param desination New bot chosen destination.
     */
    void setBotchoosendestination(Field desination);

    /**
     * Function sets bot chosen path variable.
     * @param path New bot chosen path.
     */
    void setBotchoosenpath(FieldsSet path );

    /**
     * Function returns players pawn by its id.
     * @param id Paws id.
     * @return Found pawn.
     */
    Pawn getPawnById(String id);

    /**
     * Function returns id of player.
     * @return Id of player.
     */
    String getId();

    /**
     * Function checks if player won.
     * @return True if player won, false if not.
     */
    boolean checkWin();

    /**
     * Function changes moved pawn coordinates.
     * @param pawn Moved pawn.
     * @param destination Destination to which pawn moved.
     */
    void movePawn(Pawn pawn,Field destination);


}

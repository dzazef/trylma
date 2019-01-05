package player;

/**
 * An abstract class that is a scheme for all players creators. Part of Factory Method design patter.
 */
public abstract class PlayerCreator {
    /**
     * Fixed function for creating different players.
     * @param numberOfPawns Number indicating number of players pawns.
     * @param isBot Boolean variable defining if player is bot or not.
     * @return Returns created concrete player.
     */
    public Player createPlayer(int numberOfPawns, boolean isBot) {
        return instantiatePlayer(numberOfPawns,isBot);
    }

    /**
     * Function overrode by every  concrete player creator. Instantiates concrete player. It is used in createPlayer function.
     * @param numberOfPawns Number indicating number of players pawns.
     * @param isBot Boolean variable defining if player is bot or not.
     * @return Returns instantiate concrete player.
     */
    protected abstract Player instantiatePlayer(int numberOfPawns, boolean isBot);
}

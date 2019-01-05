package player;

public class Player5ConcreteCreator extends PlayerCreator {
    @Override
    protected Player instantiatePlayer(int numberOfPawns, boolean isBot) {
        return new Player5(numberOfPawns,isBot);
    }
}

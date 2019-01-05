package player;

public class Player1ConcreteCreator extends PlayerCreator {
    @Override
    protected Player instantiatePlayer(int numberOfPawns, boolean isBot) {
        return new Player1(numberOfPawns,isBot);
    }
}

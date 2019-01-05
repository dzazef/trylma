package player;

public class Player4ConcreteCreator extends PlayerCreator {
    @Override
    protected Player instantiatePlayer(int numberOfPawns, boolean isBot) {
        return new Player4(numberOfPawns,isBot);
    }
}

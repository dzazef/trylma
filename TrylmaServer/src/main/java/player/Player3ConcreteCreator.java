package player;

public class Player3ConcreteCreator extends PlayerCreator {
    @Override
    protected Player instantiatePlayer(int numberOfPawns, boolean isBot) {
        return new Player3(numberOfPawns,isBot);
    }
}

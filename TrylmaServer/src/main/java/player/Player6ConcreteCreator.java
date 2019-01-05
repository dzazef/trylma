package player;

public class Player6ConcreteCreator extends PlayerCreator {
    @Override
    protected Player instantiatePlayer(int numberOfPawns, boolean isBot) {
        return new Player6(numberOfPawns,isBot);
    }
}

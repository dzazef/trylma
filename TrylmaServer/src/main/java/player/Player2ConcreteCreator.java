package player;

public class Player2ConcreteCreator extends PlayerCreator {
    @Override
    protected Player instantiatePlayer(int numberOfPawns, boolean isBot) {
        return new Player2(numberOfPawns,isBot);
    }
}

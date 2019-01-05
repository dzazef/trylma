package models.client.players;

public class PlayerFactory {
    public static Player getPlayer(int id, boolean isThisMe, double radius, int ch, double wGap, double hGap) {
        switch(id) {
            case 1: return new Player1(isThisMe, radius, ch, wGap, hGap);
            case 2: return new Player2(isThisMe, radius, ch, wGap, hGap);
            case 3: return new Player3(isThisMe, radius, ch, wGap, hGap);
            case 4: return new Player4(isThisMe, radius, ch, wGap, hGap);
            case 5: return new Player5(isThisMe, radius, ch, wGap, hGap);
            case 6: return new Player6(isThisMe, radius, ch, wGap, hGap);
        }
        return null;
    }
}

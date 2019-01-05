package gamemanager;

import connector.*;
import gamemanager.board.Board;
import player.*;

import java.io.ObjectOutputStream;
import java.util.ArrayList;


/**
 * GamaManager is an abstract class that stores variables and functions needed by server to conduct the gameplay.
 */
public abstract class GameManager {
    public static ArrayList<Connector.Handler> handlers;
    /**
     * Variable storing information if game is in progress.
     */
    public static Boolean gameInProgerss;
    /**
     * Variable storing number, that implies how many pawns each player has.
     */
    private static int numberOfPawns;
    /**
     * Variable storing number of players.
     */
    private static int numberOfPlayers;
    /**
     * Variable storing how many free places at the board are left.
     */
    private static int freePlacesForGame;
    /**
     * Board object, on players play. Yay.
     */
    private static Board board;
    /**
     * Array storing all players taking part in game.
     */
    private static ArrayList<Player> players;
    /**
     * Array storing each connected client output steam.
     */
    private static ArrayList<ObjectOutputStream> playersobjout;
    /**
     * Variable storing actual player.
     */
    private static Player actualplayer;
    /**
     * Chain of responsibility, responsible for handling received messages.
     */
    private static MessageChain chainOfResponsibility;
    /**
     * Array storing players creators, that are part of Factory Method design pattern.
     */
    private static ArrayList<PlayerCreator> playerCreators;

    /**
     * Function setting next in order player as actual player.
     */
    public static void nextPlayer()
    {
        for(int i = 0; i < players.size(); i++)
        {
            if(players.get(i).getId().equals(actualplayer.getId()))
            {
                actualplayer = players.get((i+1)%numberOfPlayers);
                return;
            }
        }
    }

    /**
     * Function that enables outer classes to use players creators.
     * @return array with player creators.
     */
    public static ArrayList<PlayerCreator> getPlayerCreators() {
        return playerCreators;
    }

    /**
     * Function that checks if any player wins and if so returns that player.
     * @return player that is winner or null.
     */
    public static Player checkWin()
    {
        for(Player player : players)
        {
            if(player.checkWin())
            {
                return player;
            }
        }
        return null;
    }

    /**
     * Function that sets actual player.
     * @param actualplayer New actual player.
     */
    public static void setActualplayer(Player actualplayer) {
        GameManager.actualplayer = actualplayer;
    }

    /**
     * Function that sets number of free places for game.
     * @param freePlacesForGame New number of free places at the board.
     */
    public static void setFreePlacesForGame(int freePlacesForGame) {
        GameManager.freePlacesForGame = freePlacesForGame;
    }

    /**
     * Function that sets number that indicates number of pawns each player has.
     * @param numberOfPawns New number of pawns.
     */
    public static void setNumberOfPawns(int numberOfPawns) {
        GameManager.numberOfPawns = numberOfPawns;
    }

    /**
     * Function that sets number of players in game.
     * @param numberOfPlayers New number of players.
     */
    public static void setNumberOfPlayers(int numberOfPlayers) {
        GameManager.numberOfPlayers = numberOfPlayers;
    }

    /**
     * Function that returns array of output streams to all connected clients.
     * @return array of output streams to connected clients.
     */
    public static ArrayList<ObjectOutputStream> getPlayersobjout() {
        return playersobjout;
    }

    /**
     * Function that returns array containing players.
     * @return array of players.
     */
    public static ArrayList<Player> getPlayers() {
        return players;
    }

    /**
     * Function that returns board on which the game is happening.
     * @return board.
     */
    public static Board getBoard() {
        return board;
    }

    /**
     * Returns remaining free places for game.
     * @return number of free places for game.
     */
    public static int getFreePlacesForGame() {
        return freePlacesForGame;
    }

    /**
     * Function returns number that indicates number of players pawns.
     * @return number of pawns.
     */
    public static int getNumberOfPawns() {
        return numberOfPawns;
    }

    /**
     * Function returns number of players.
     * @return number of players in game.
     */
    public static int getNumberOfPlayers() {
        return numberOfPlayers;
    }

    /**
     * Function returns chain of responsibility, that embodies the chain of responsibility design pattern, which is responsible for handling messages received from clients.
     * @return chain of responsibility.
     */
    public static MessageChain getChainOfResponsibility() {
        return chainOfResponsibility;
    }

    /**
     * Function returns actual player.
     * @return actual player.
     */
    public static Player getActualplayer() {
        return actualplayer;
    }

    /**
     * Function returns information if game is in progress or not.
     * @return boolean variable indicating if game is in progress.
     */
    public static synchronized boolean isGameInProgerss() {
        return gameInProgerss;
    }

    /**
     * Function sets gameInProgress variable.
     * @param gameInProgerss New value of game in progress variable.
     */
    public static synchronized void setGameInProgerss(boolean gameInProgerss) {
        GameManager.gameInProgerss = gameInProgerss;
    }

    /**
     * The function is invoked when fatal error during server running occurs. It resets the server.
     */
    public static synchronized void gameFailure()
    {
        setGameInProgerss(false);
        for(Connector.Handler handler : handlers)
        {
            handler.turnOff();
        }
        initialize();
    }

    /**
     * Function that initializes GameManager main ingredients.
     */
    public static void initialize()
    {
        handlers = new ArrayList<>();
        gameInProgerss = false;
        numberOfPlayers = 0;
        numberOfPawns = 0;
        freePlacesForGame = 1;
        playersobjout = new ArrayList<>();
        board = new Board();
        players = new ArrayList<>();
        chainOfResponsibility = new StartfieldMessage();
        SkipMessage skipMessage = new SkipMessage();
        EndfieldMessage endfieldMessage = new EndfieldMessage();
        skipMessage.setNextChain(endfieldMessage);
        chainOfResponsibility.setNextChain(skipMessage);
        playerCreators = new ArrayList<>();
        playerCreators.add(new Player1ConcreteCreator());
        playerCreators.add(new Player2ConcreteCreator());
        playerCreators.add(new Player3ConcreteCreator());
        playerCreators.add(new Player4ConcreteCreator());
        playerCreators.add(new Player5ConcreteCreator());
        playerCreators.add(new Player6ConcreteCreator());
    }

}

package connector;

import gamemanager.GameManager;
import player.*;
import java.io.*;
import java.lang.reflect.GenericArrayType;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Connector class is the class responsible for communication in server application. It receives messages from clients and sends answers.
 * It has the communication loop that is responsible for connecting clients with server, and than in unique Handler thread handle the communication
 * with this particular client. The inner class Handler extends Thread class. One Handler communicates with and runs adequate logic functions
 * of server for distinct player.
 */
public class Connector {

    /**
     * Handler class responsible for communicating and running server logic functions for distinct player.
     */
    public static class Handler extends Thread {
        /**
         * Boolean variable, that decides about thread life. When the thread is created it gains true value, so threads run function runs.
         * When the thread is no longer needed, GameManager can kill it by setting its value as false.
         */
        private boolean inUse;
        /**
         * Variable storing information if client is already connected or not.
         */
        private boolean connected;

        /**
         * Command class object, responsible for sending messages to client.
         */
        private Command command;
        /**
         * Handlers unique ObjectOutputStream by which every message and serialized objects will be send.
         */
        private ObjectOutputStream objout;
        /**
         * Handlers unique ObjectInputStream from which every message and serialized objects will be read.
         */
        private ObjectInputStream objin;
        /**
         * Player class object, representing distinct player the handler is handling.
         */
        private Player player_to_handle;
        /**
         * The clients socket, by which client is connected to server.
         */
        private Socket socket;

        /**
         * Turns the thread off.
         */
        public synchronized void turnOff()
        {
            this.inUse = false;
        }
        /**
         * Function responsible for creating and adding new player to game if there is place at the table.
         */
        private synchronized void addPlayer()
        {
            if (GameManager.getFreePlacesForGame() == 0) {
                GameManager.getPlayersobjout().add(objout);
                GameManager.setGameInProgerss(true);
                return;
            }
            if(GameManager.getFreePlacesForGame() >0)
            {
                this.connected = true;
                GameManager.getPlayersobjout().add(objout);
            }
            Player player = GameManager.getPlayerCreators().get(GameManager.getNumberOfPlayers() - GameManager.getFreePlacesForGame()).createPlayer(GameManager.getNumberOfPawns(),false);
            GameManager.getPlayers().add(player);
            this.player_to_handle = player;

            GameManager.setFreePlacesForGame(GameManager.getFreePlacesForGame() - 1);
            if (GameManager.getFreePlacesForGame() == 0) {
                GameManager.setGameInProgerss(true);
            }
        }

        /**
         * Handlers constructor, used to start handler for normal player.
         * @param socket Clients socket, by which client is connected.
         */
        Handler(Socket socket) {
            GameManager.handlers.add(this);
            this.inUse = true;
            this.connected = false;
            this.player_to_handle = null;
            this.socket = socket;
            try {
                this.objout = new ObjectOutputStream(socket.getOutputStream());
                this.objin = new ObjectInputStream(socket.getInputStream());
            }
            catch (Exception e)
            {
                System.out.println("Failed to create Output or Input stream. Player Handler");
            }
            command = new Command(objout);
        }

        /**
         * Handlers constructor, used to start handler for bot player.
         * @param player Player the bot is playing;
         */
        private Handler(Player player) {
            GameManager.handlers.add(this);
            this.inUse = true;
            this.connected = true;
            this.player_to_handle = player;

            command = new Command(objout);
        }

        /**
         * Function responsible for moving if the player the Handler handles is a bot player.
         */
        private void botMove() throws IOException
        {
            System.out.println("1");
            try {
                sleep(Bot.delay*3);
            }
            catch (Exception e)
            {
                System.out.println(e.getMessage());
            }
            System.out.println("2");
            Bot.botMove(this.player_to_handle);
            System.out.println("3");
            command.sendMoveMessage(GameManager.getActualplayer().getBotchoosendestination());
            System.out.println("4");
            GameManager.getBoard().move(GameManager.getActualplayer().getBotchoosenpawn(), GameManager.getActualplayer().getBotchoosendestination());
            System.out.println("5");
            GameManager.getActualplayer().movePawn(GameManager.getActualplayer().getBotchoosenpawn(), GameManager.getActualplayer().getBotchoosendestination());
            System.out.println("6");
            GameManager.nextPlayer();
        }

        /**
         * Function that checks if any of the players is a winner, executed after the handler finishes his move algorithm.
         */
        private synchronized void checkWin() throws IOException
        {
            Player winner = GameManager.checkWin();
            if(winner != null)
            {
                System.out.println("Winner " + winner.getId());
                command.sendWinMessage(winner);
                GameManager.setGameInProgerss(false);
                GameManager.initialize();
                this.connected = false;
            }
        }

        /**
         * Function that handles commands received from clients application.
         * @throws IOException
         */
        private void handleCommands() throws IOException{
            Object t;
            try {
                t = this.objin.readObject();
            } catch (Exception e) {
                throw new IOException("Failed to read startfield command from client "+ e + " : " + this.player_to_handle.getId());
            }
            String s = (String) t;
            GameManager.getChainOfResponsibility().handleMessage(s, this.objin, this.command, this.player_to_handle);
        }

        /**
         * Function that creates and adds adequate number of bots while creating the game.
         * @param numberOfBots Number of bots in game.
         * @param numberOfPawns Number indicating how board and players will look like. It doesn't exactly specifies the number of pawns the players have, but it says how big is side of the triangle,
         *                      that is the players home.
         */
        private void addBots(int numberOfBots, int numberOfPawns)
        {
            for (int i = 0; i < numberOfBots; i++) {
                Player player = GameManager.getPlayerCreators().get(i).createPlayer(numberOfPawns,true);
                GameManager.getPlayers().add(player);
                (new Handler(player)).start();
            }
        }

        /**
         * Function invoked at the beginning of game, responsible for creating the game.
         */
        private void createGame() throws Exception
        {
            String s;
            try {
                objout.writeObject("newgame");
            }
            catch (IOException e)
            {
                System.out.println("Failed to send newgame command");
            }
            try {
                s = (String) objin.readObject();
            } catch (Exception e) {
                System.out.println("failed to read line" + e);
                return;
            }
            if (s.startsWith("creategame")) {
                String[] parts = s.split(":");
                String numOfBots = parts[1];
                String numOfPlayers = parts[2];
                String numOfPawns = parts[3];
                int numOfPawnsint = 0;
                int numOfBotsint = 0;
                int numOfPlayersint = 0;
                try {
                    numOfPlayersint = Integer.parseInt(numOfPlayers);
                    numOfBotsint = Integer.parseInt(numOfBots);
                    numOfPawnsint = Integer.parseInt(numOfPawns);
                } catch (Exception e) {
                    System.out.println("Creating board error");
                }

                GameManager.setFreePlacesForGame(numOfPlayersint - numOfBotsint);
                GameManager.setNumberOfPlayers(numOfPlayersint);
                GameManager.setNumberOfPawns(numOfPawnsint);
                GameManager.getBoard().build(numOfPawnsint);
                addBots(numOfBotsint,numOfPawnsint);
                addPlayer();
                GameManager.setActualplayer(GameManager.getPlayers().get(GameManager.getNumberOfPlayers() - 1 - GameManager.getFreePlacesForGame()));
            }
            else {
                throw new Exception("Failure, game has not been created");
            }

        }

        /**
         * Function invoked when there is a connect message received from new client.
         */
        void connectMessageHandle() throws Exception
        {
            if (GameManager.getFreePlacesForGame() == 0) {
                try {
                    objout.writeObject("gamefull");
                } catch (Exception e) {
                    System.out.println("Failed to send gamefull message");
                }
            } else if (GameManager.getNumberOfPlayers() == 0) {
                createGame();
            } else if (GameManager.getFreePlacesForGame() > 0) {
                addPlayer();
                String message = "joingame" + ":" + GameManager.getPlayers().get(GameManager.getNumberOfPlayers() - 1 - GameManager.getFreePlacesForGame()).getId() + ":" + GameManager.getNumberOfPlayers() +":" + GameManager.getNumberOfPawns();
                try {
                    objout.writeObject(message);
                } catch (Exception e) {
                    System.out.println("Failed to send joingame message");
                }
            }
        }

        /**
         * Thread main function, in which everything is happening.
         */
        public void run() {
            while(this.inUse) {
                //noinspection SynchronizeOnNonFinalField
                synchronized (GameManager.gameInProgerss) {
                    if (!GameManager.isGameInProgerss() && !this.connected) {
                        if (this.player_to_handle == null) {
                            String s;
                            try {
                                s = (String) objin.readObject();
                            } catch (Exception e) {
                                System.out.println("Failed to read connect command" );
                                break;
                            }
                            if (s.startsWith("connect")) {
                                try {
                                    connectMessageHandle();
                                }
                                catch (Exception e)
                                {
                                    command.sendFailureMessage();
                                    GameManager.gameFailure();
                                    this.connected = false;
                                }
                            }
                        }
                    } else if (this.connected && GameManager.isGameInProgerss()) {
                        gameplay();
                    }
                }
            }
        }

        /**
         * Special function responsible for conducting the whole gameplay for handled player.
         */
        private synchronized void gameplay()
        {
            if (GameManager.isGameInProgerss() && GameManager.getActualplayer().getId().equals(this.player_to_handle.getId())) {
                    if (!this.player_to_handle.isBot()) {
                        try {
                            this.objout.writeObject("yourturn");
                        }
                        catch (Exception e) {
                            System.out.println("Connection failure 1 " +e +" : "+ e.getMessage());
                            command.sendFailureMessage();
                            GameManager.gameFailure();
                            this.connected = false;
                        }
                        try {


                            handleCommands();
                        }
                        catch (Exception e) {
                            System.out.println("Connection failure 2 " +e +" : "+ e.getMessage());
                            command.sendFailureMessage();
                            GameManager.gameFailure();
                            this.connected = false;
                        }
                    } else {
                        try {
                            botMove();
                        }
                        catch (Exception e) {
                            System.out.println(this.player_to_handle.getId() + "  Connection failure 3 " +e +" : "+ e.getMessage());
                            command.sendFailureMessage();
                            GameManager.gameFailure();
                            this.connected = false;
                        }
                    }
                    try {
                        checkWin();
                    }
                    catch (Exception e) {
                        System.out.println("Connection failure 4 " +e +" : "+ e.getMessage());
                        command.sendFailureMessage();
                        GameManager.gameFailure();
                        this.connected = false;
                    }


            }
        }
    }



    /**
     * Main connection loop, responsible for connecting, and running handlers for new connected sockets.
     */
    public void connectionLoop()
    {
        ServerSocket server;
        try {
            server = new ServerSocket(9090);
        }
        catch (IOException e)
        {
            System.out.println("creating server failure");
            return ;
        }
        try {
            try{
                while (true){
                    new Handler(server.accept()).start();
                }
            }
            catch (IOException e)
            {
                System.out.println("Failed to create new handler for new Socket: IOException" + e);
            }
            finally {
                server.close();
            }
        }
        catch (Exception p)
        {
            System.out.println("Failed to close the server: IOException" + p);
        }

    }

}



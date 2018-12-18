package connector;

import gamemanager.GameManager;
import gamemanager.MoveManager;
import serializable.Field;
import player.*;

import javax.rmi.ssl.SslRMIClientSocketFactory;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Klasa Connector jest główną klasą komunikacyjną serwera. Jej zaadaniem jest odbieranie informaji od klientów i, z pomocą obiektu klasy Command,
 * wysyłanie komunikatów zwrotnych. Connector posiada klasę communicationLoop, która zawiera w sobie pętlę komunikacyjną. Serwer każdemy klientowi
 * przypisuje jego obiekt klasy Handler, który rozszerza klasę Thread i działą na osobnym Sockecie na tym samym porcie.
 * W Handlarze odbywa się cała komunikacja konkretnego klienta z serwerem.
 */


public class Connector {
    /**
     * Serwer, do którego podłączać się będą klienci.
     */
    private ServerSocket server;

    /**
     * Klasa obsługująca połączenie pojedynczego klienta.
     */
    public static class Handler extends Thread {


        /**
         * Obiekt klasy Command, która posiada funkcje do generowanie i wysyłania wiadomości do klienta
         */
        private Command command;
        /**
         * Strumień wyjściowy.
         */
        private ObjectOutputStream objout;
        /**
         * Strumień wejściowy.
         */
        private ObjectInputStream objin;
        /**
         * Nazwa gracza/klienta, którego handler obsługuje.
         */
        private Player player_to_handle;
        /**
         * Socket, na którym połączony jest klient.
         */
        private Socket socket;


        /**
         * Funkcja dodająca gracza w zależności ile graczy jest już w grze.
         */
        private void addPlayer()
        {
            if((GameManager.numberOfPlayers - GameManager.freePlacesForGame +1)==1)
            {
                Player1 player = new Player1(GameManager.numberOfPawns,false);
                GameManager.players.add(player);
                this.player_to_handle = player;
            }
            else if((GameManager.numberOfPlayers - GameManager.freePlacesForGame +1)==2)
            {
                Player2 player = new Player2(GameManager.numberOfPawns,false);
                GameManager.players.add(player);
                this.player_to_handle = player;
            }
            else if((GameManager.numberOfPlayers - GameManager.freePlacesForGame +1)==3)
            {
                Player3 player = new Player3(GameManager.numberOfPawns,false);
                GameManager.players.add(player);
                this.player_to_handle = player;
            }
            else if((GameManager.numberOfPlayers - GameManager.freePlacesForGame +1)==4)
            {
                Player4 player = new Player4(GameManager.numberOfPawns,false);
                GameManager.players.add(player);
                this.player_to_handle = player;
            }
            else if((GameManager.numberOfPlayers - GameManager.freePlacesForGame +1)==5)
            {
                Player5 player = new Player5(GameManager.numberOfPawns,false);
                GameManager.players.add(player);
                this.player_to_handle = player;
            }
            else if((GameManager.numberOfPlayers - GameManager.freePlacesForGame +1)==6)
            {
                Player6 player = new Player6(GameManager.numberOfPawns,false);
                GameManager.players.add(player);
                this.player_to_handle = player;
            }

            GameManager.freePlacesForGame--;
            if (GameManager.freePlacesForGame == 0) {
                GameManager.gameInProgerss = true;
            }
        }

        /**
         * Konstruktor, używany do tworzenia handlera dla gracza.
         * @param socket socket na którym połączony zostanie klient.
         */
        public Handler(Socket socket) {
            this.socket = socket;
            try {
                this.objout = new ObjectOutputStream(socket.getOutputStream());
                this.objin = new ObjectInputStream(socket.getInputStream());
            }
            catch (Exception e)
            {
                System.out.println("Failed to create Output or Input stream. Player Handler");
            }
            command = new Command(objout,objin);
        }


        private Handler(Socket socket,Player player,ObjectInputStream in, ObjectOutputStream out) {
            this.socket = socket;
            this.player_to_handle = player;
            this.objout =out;
            this.objin = in;
            command = new Command(objout,objin);

        }
        private void handleCommands() throws Exception {
                Object t;
                if (GameManager.actualplayer.getId().equals(this.player_to_handle.getId())) {
                    if (!this.player_to_handle.isBot()) {
                        try {
                            this.objout.writeObject("yourturn");
                        } catch (Exception e) {
                            throw new Exception("Failed to send yourturn message" + e + " : " + this.player_to_handle.getId());
                        }
                        try {
                            synchronized (objin) {
                                t = this.objin.readObject();
                            }
                        } catch (Exception e) {
                            throw new Exception("Failed to read startfield command from client "+ e + " : " + this.player_to_handle.getId());
                        }
                        String s = (String) t;
                        if (s.startsWith("startfield")) {
                            System.out.println("Wszedłem do startfield");
                            Field field;
                            try {
                                synchronized (objin) {
                                    field = (Field) objin.readObject();
                                }
                            } catch (Exception e) {
                                throw new Exception("Failed to read Field class object for startfield "+ e + " : " + this.player_to_handle.getId());
                            }
                            System.out.println("Wczytałem obiekt");
                            if (field != null) {
                                System.out.println("Chce wyslac sciezki");
                                command.sendPossibleMovesMessage(field);
                                MoveManager.choosenPawn = GameManager.actualplayer.getPawnById(field.getId());
                            }
                            System.out.println("Wysłałem ścieżki");
                            try {
                                s = (String) objin.readObject();
                            } catch (Exception e) {
                                System.out.println("4");
                                throw new Exception("Failed to read endfield command from client "+ e + " : " + this.player_to_handle.getId());

                            }
                            System.out.println("Wczytałem obiekt");
                            if (s.startsWith("endfield")) {
                                System.out.println("Wsaedłwm do endfield");
                                try {
                                    field = (Field) objin.readObject();
                                } catch (Exception e) {
                                    System.out.println("5");
                                    throw new Exception("Failed to read object for endfield "+ e + " : " + this.player_to_handle.getId());
                                }
                                System.out.println("Wczytałem obiekt");
                                GameManager.actualplayer.movePawn(MoveManager.choosenPawn, field);
                                GameManager.board.move(MoveManager.choosenPawn, field);
                                command.sendMoveMessage(MoveManager.choosenPawn, field);
                                GameManager.nextPlayer();
                                System.out.println("Ruszyłem się");
                            } else if (s.startsWith("skip")) {
                                System.out.println("Tu wszedłem");
                                GameManager.nextPlayer();
                                System.out.println("Nie wyjebało");
                            }
                        } else {
                            GameManager.actualplayer.botMove();
                            command.sendMoveMessage(GameManager.actualplayer.getBotchoosenpawn(), GameManager.actualplayer.getBotchoosendestination());
                        }
                    }
                }
        }

        private void createGame()
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

                GameManager.freePlacesForGame = numOfPlayersint - numOfBotsint;
                GameManager.numberOfPlayers = numOfPlayersint;
                GameManager.numberOfPawns = numOfPawnsint;
                GameManager.board.build(numOfPawnsint);
                for (int i = 1; i <= numOfBotsint; i++) {
                    if (i == 1) {
                        Player1 player1 = new Player1(numOfPawnsint, true);
                        GameManager.players.add(player1);
                        (new Handler(this.socket,player1,objin,objout)).start();
                    } else if (i == 2) {
                        Player2 player2 = new Player2(numOfPawnsint, true);
                        GameManager.players.add(player2);
                        (new Handler(this.socket,player2,objin,objout)).start();
                    } else if (i == 3) {
                        Player3 player3 = new Player3(numOfPawnsint, true);
                        GameManager.players.add(player3);
                        (new Handler(this.socket,player3,objin,objout)).start();
                    } else if (i == 4) {
                        Player4 player4 = new Player4(numOfPawnsint, true);
                        GameManager.players.add(player4);
                        (new Handler(this.socket,player4,objin,objout)).start();
                    } else if (i == 5) {
                        Player5 player5 = new Player5(numOfPawnsint, true);
                        GameManager.players.add(player5);
                        new Handler(this.socket,player5,objin,objout);
                    } else if (i == 6) {
                        Player6 player6 = new Player6(numOfPawnsint, true);
                        GameManager.players.add(player6);
                        (new Handler(this.socket,player6,objin, objout)).start();
                    }
                }
                addPlayer();
                GameManager.playersobjout.add(objout);
                GameManager.actualplayer = GameManager.players.get(GameManager.numberOfPlayers - 1 - GameManager.freePlacesForGame);
            }

            else {
                System.out.println("Failure, game has not been created");
            }

        }
        public void run() {

                while(true) {
                    if(!GameManager.gameInProgerss)
                    {
                        String s;
                        try {
                            s = (String) objin.readObject();
                        }
                        catch (Exception e)
                        {
                            System.out.println("Failedo to read connect command" + this.player_to_handle.getId());
                            break;
                        }
                        if (s.startsWith("connect"))
                        {
                            if(GameManager.freePlacesForGame == 0)
                            {
                                try {
                                    objout.writeObject("gamefull");
                                }
                                catch (Exception e)
                                {
                                    System.out.println("Failed to send gamefull message");
                                }
                            }
                            else if(GameManager.numberOfPlayers == 0)
                            {
                                createGame();
                            }
                            else if(GameManager.freePlacesForGame>0)
                            {
                                addPlayer();
                                GameManager.playersobjout.add(objout);
                                String message = "joingame" + ":" + GameManager.players.get(GameManager.numberOfPlayers - 1 - GameManager.freePlacesForGame).getId() + ":" + GameManager.numberOfPlayers;
                                try {
                                    objout.writeObject(message);
                                }
                                catch (Exception e)
                                {
                                    System.out.println("Failed to send joingame message");
                                }
                            }
                        }
                    }
                    else
                    {
                        try {
                            handleCommands();
                        }
                        catch (Exception e)
                        {
                            System.out.println("Wywaliło serwer");
                            System.out.println(e.getMessage());
                            break;
                        }
                    }
                   /* if (!GameManager.gameInProgerss && !this.connected) {
                        String s;
                        if (GameManager.freePlacesForGame == 0) {
                            GameManager.gameInProgerss = true;
                        }
                        try {
                            s = (String) objin.readObject();
                        }
                        catch (Exception e)
                        {
                            System.out.println("Failedo to read connect command");
                            break;
                        }
                        if (s.startsWith("connect") && !GameManager.gameInProgerss) {
                            if (GameManager.numberOfPlayers == 0) {
                                createGame();
                                this.connected = true;
                            }
                        } else if (GameManager.freePlacesForGame > 0) {
                            addPlayer();
                            GameManager.playersobjout.add(objout);
                            String message = "joingame" + ":" + GameManager.players.get(GameManager.numberOfPlayers - 1 - GameManager.freePlacesForGame).getId() + ":" + GameManager.numberOfPlayers;
                            try {
                                objout.writeObject(message);
                            }
                            catch (Exception e)
                            {
                                System.out.println("Failed to send joingame message");
                            }
                        }
                    } else if (GameManager.gameInProgerss && this.connected) {
                        handleCommands();
                    } else if (GameManager.gameInProgerss && !this.connected) {
                        String s;


                        if (s.startsWith("connect")) {
                            objout.writeObject("gamefull");
                        }
                    }*/
                }

        }
    }


    /**
     * Funkcja połączenia, która odpowiada za całe połączenie serwera.
     */
    public void communicationLoop()
    {
        try {
            this.server = new ServerSocket(9090);
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



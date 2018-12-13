package connector;

import gamemanager.GameManager;
import gamemanager.MoveManager;
import gamemanager.board.Field;
import player.*;

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
         * Zmienna sprawdzająca czy dany gracz jest już połączony.
         */
        private boolean connected;
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
        private String name;
        /**
         * Socket, na którym połączony jest klient.
         */
        private Socket socket;

        /**
         * Funkcja dodająca gracza w zależności ile graczy jest już w grze.
         */
        private void addPlayer()
        {
            if((GameManager.numberOfPlayers - GameManager.freePlacesForGame + 1)==1)
            {
                GameManager.players.add(new Player1(GameManager.numberOfPawns,false));
                this.name = "player1";
            }
            else if((GameManager.numberOfPlayers - GameManager.freePlacesForGame + 1)==2)
            {
                GameManager.players.add(new Player2(GameManager.numberOfPawns,false));
                this.name = "player2";
            }
            else if((GameManager.numberOfPlayers - GameManager.freePlacesForGame + 1)==3)
            {
                GameManager.players.add(new Player3(GameManager.numberOfPawns,false));
                this.name = "player3";
            }
            else if((GameManager.numberOfPlayers - GameManager.freePlacesForGame + 1)==4)
            {
                GameManager.players.add(new Player4(GameManager.numberOfPawns,false));
                this.name = "player4";
            }
            else if((GameManager.numberOfPlayers - GameManager.freePlacesForGame + 1)==5)
            {
                GameManager.players.add(new Player5(GameManager.numberOfPawns,false));
                this.name = "player5";
            }
            else if((GameManager.numberOfPlayers - GameManager.freePlacesForGame + 1)==6)
            {
                GameManager.players.add(new Player6(GameManager.numberOfPawns,false));
                this.name = "player6";
            }
            this.connected = true;
        }

        /**
         * Konstruktor, używany do tworzenia handlera dla gracza.
         * @param socket socket na którym połączony zostanie klient.
         */
        public Handler(Socket socket) {
            this.connected =false;
            this.socket = socket;
        }

        /**
         * Konstruktor, używany do tworzenia handlera dla botów.
         * @param socket na który odpowiedzi będzie wysyłał bot.
         * @param name Nazwa gracza, którą przyjmie bot.
         */
        public Handler(Socket socket,String name)
        {
            this.connected = true;
            this.socket = socket;
            this.name = name;
        }


        public void run() {
            try {
                this.objout = new ObjectOutputStream(socket.getOutputStream());
                this.objin = new ObjectInputStream(socket.getInputStream());
                GameManager.playersobjout.add(objout);
                command = new Command(objout);
                while(true) {
                    if(!GameManager.gameInProgerss && !this.connected)
                    {
                        String s;
                        if (GameManager.freePlacesForGame == 0) {
                            GameManager.gameInProgerss = true;
                        }

                        s = (String) objin.readObject();
                        System.out.println(s);
                        if (s.startsWith("connect") && !GameManager.gameInProgerss) {
                            if (GameManager.numberOfPlayers == 0) {
                                objout.writeObject("newgame");
                                try {
                                    s = (String) objin.readObject();
                                } catch (IOException e) {
                                    System.out.println("failed to read line");
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
                                            GameManager.players.add(new Player1(numOfPawnsint, true));
                                            new Handler(socket, "player1").start();
                                        } else if (i == 2) {
                                            GameManager.players.add(new Player2(numOfPawnsint, true));
                                            new Handler(socket, "player2").start();
                                        } else if (i == 3) {
                                            GameManager.players.add(new Player3(numOfPawnsint, true));
                                            new Handler(socket, "player3").start();
                                        } else if (i == 4) {
                                            GameManager.players.add(new Player4(numOfPawnsint, true));
                                            new Handler(socket, "player4").start();
                                        } else if (i == 5) {
                                            GameManager.players.add(new Player5(numOfPawnsint, true));
                                            new Handler(socket, "player5").start();
                                        } else if (i == 6) {
                                            GameManager.players.add(new Player6(numOfPawnsint, true));
                                            new Handler(socket, "player6").start();
                                        }
                                    }
                                    addPlayer();
                                    GameManager.actualplayer = GameManager.players.get(GameManager.numberOfPlayers - GameManager.freePlacesForGame);
                                    System.out.println(GameManager.board.getFieldById("0,0,0").getState());
                                    if (GameManager.freePlacesForGame == 0) {
                                        GameManager.gameInProgerss = true;
                                    }
                                } else {
                                    System.out.println("Failure, game has not been created");
                                }
                            }
                            else if (GameManager.freePlacesForGame >0){
                                addPlayer();
                                String message = "joingame" + ":"+GameManager.players.get(GameManager.numberOfPlayers - GameManager.freePlacesForGame + 1).getId() + ":" + GameManager.numberOfPlayers;
                                objout.writeObject(message);
                                GameManager.freePlacesForGame--;
                                if (GameManager.freePlacesForGame == 0) {
                                    GameManager.gameInProgerss = true;
                                }
                            }
                        }

                    }
                    else if(GameManager.gameInProgerss && this.connected)
                    {
                        String s;
                        s = (String) objin.readObject();

                        if(GameManager.actualplayer.getId().equals(name))
                        {
                            objout.writeObject("yourturn");
                            if(s.startsWith("startfield"))
                            {
                                Field field = null;
                                try {
                                    field = (Field) objin.readObject();
                                }
                                catch (ClassNotFoundException e)
                                {
                                    System.out.println("Failed to read object");
                                }
                                if(field != null) {
                                    command.sendPossibleMovesMessage(field);
                                    MoveManager.choosenPawn = GameManager.actualplayer.getPawnById(field.getId());
                                }
                            }
                            else if(s.startsWith("endfield"))
                            {
                                Field field = null;
                                try {
                                    field = (Field) objin.readObject();
                                }
                                catch (ClassNotFoundException e)
                                {
                                    System.out.println("Failed to read object");
                                }
                                GameManager.actualplayer.movePawn(MoveManager.choosenPawn,field);
                                GameManager.board.move(MoveManager.choosenPawn,field);
                                command.sendMoveMessage(MoveManager.choosenPawn,field);
                                GameManager.nextPlayer();
                            }
                            else if(s.startsWith("skip"))
                            {
                                GameManager.nextPlayer();
                            }
                        }
                        else if(GameManager.gameInProgerss && !this.connected)
                        {
                            if (s.startsWith("connect")) {
                                objout.writeObject("gamefull");
                            }
                        }
                    }
                }

            } catch (Exception e) {

                System.out.println(e);

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



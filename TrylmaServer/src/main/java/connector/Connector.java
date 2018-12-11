package connector;

import gamemanager.GameManager;
import gamemanager.MoveManager;
import gamemanager.Path;
import gamemanager.board.Field;
import player.*;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;

public class Connector {

    private ServerSocket server;

    private static class Handler extends Thread {
        private Command command;
        private ObjectOutputStream objout;
        private ObjectInputStream objin;
        private String name;
        private Socket socket;
        private BufferedReader in;
        private PrintWriter out;

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
        }
        public Handler(Socket socket) {
            this.socket = socket;
        }
        public Handler(Socket socket,String name)
        {
            this.socket = socket;
            this.name = name;
        }

        public void run() {
            try {
                in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                out = new PrintWriter(socket.getOutputStream(), true);
                objout = new ObjectOutputStream(socket.getOutputStream());
                objin = new ObjectInputStream(socket.getInputStream());
                GameManager.playersout.add(out);
                GameManager.playersobjout.add(objout);
                command = new Command(objout,out);
                while(true) {

                    String s;
                    if(GameManager.freePlacesForGame==0)
                    {
                        GameManager.gameInProgerss = true;
                    }
                    s = in.readLine();
                    if (s.startsWith("connect") && !GameManager.gameInProgerss) {
                        if (GameManager.numberOfPlayers == 0) {
                            out.println("newgame");
                            try {
                                s = in.readLine();
                            }
                            catch (IOException e) {
                                System.out.println("failed to read line");
                            }
                            if(s.startsWith("creategame"))
                            {
                                String[] parts = s.split(";");
                                String numOfBots = parts[0];
                                String numOfPlayers = parts[1];
                                String numOfPawns = parts[2];
                                int numOfPawnsint = 0;
                                int numOfBotsint = 0;
                                int numOfPlayersint = 0;
                                try
                                {
                                    numOfPlayersint = Integer.parseInt(numOfPlayers);
                                    numOfBotsint=Integer.parseInt(numOfBots);
                                    numOfPawnsint=Integer.parseInt(numOfPawns);
                                }
                                catch (Exception e)
                                {
                                    System.out.println("Creating board error");
                                }
                                GameManager.freePlacesForGame = numOfPlayersint - numOfBotsint;
                                GameManager.numberOfPlayers = numOfPlayersint;
                                GameManager.numberOfPawns = numOfPawnsint;
                                for(int i = 1; i<=numOfBotsint;i++)
                                {
                                    if(i==1)
                                    {
                                        GameManager.players.add(new Player1(numOfPawnsint,true));
                                    }
                                    else if(i==2)
                                    {
                                        GameManager.players.add(new Player2(numOfPawnsint,true));
                                        new Handler(socket,"player2").start();
                                    }
                                    else if(i==3)
                                    {
                                        GameManager.players.add(new Player3(numOfPawnsint,true));
                                        new Handler(socket,"player3").start();
                                    }
                                    else if(i==4)
                                    {
                                        GameManager.players.add(new Player4(numOfPawnsint,true));
                                        new Handler(socket,"player4").start();
                                    }
                                    else if(i==5)
                                    {
                                        GameManager.players.add(new Player5(numOfPawnsint,true));
                                        new Handler(socket,"player5").start();
                                    }
                                    else if(i==6)
                                    {
                                        GameManager.players.add(new Player6(numOfPawnsint,true));
                                        new Handler(socket,"player6").start();
                                    }
                                }
                                addPlayer();
                                GameManager.actualplayer = GameManager.players.get(GameManager.numberOfPlayers - GameManager.freePlacesForGame);

                                if(GameManager.freePlacesForGame == 0)
                                {
                                    GameManager.gameInProgerss = true;
                                }
                            }
                            else
                            {
                                System.out.println("Failure, game has not been created");
                            }
                        }
                        else
                        {
                                addPlayer();
                                out.println("joingame"+ GameManager.players.get(GameManager.numberOfPlayers - GameManager.freePlacesForGame + 1).getId());
                                GameManager.freePlacesForGame--;
                                if(GameManager.freePlacesForGame == 0)
                                {
                                    GameManager.gameInProgerss = true;
                                }
                        }
                    }
                    else if(s.startsWith("connect")&& GameManager.gameInProgerss)
                    {
                        out.println("gamefull");
                    }
                    else if(GameManager.gameInProgerss)
                    {
                        if(GameManager.actualplayer.getId() == name)
                        {
                            out.println("yourturn");
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
                        }
                    }
                }

            } catch (IOException e) {
                System.out.println(e);
            }

        }
    }

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
            try {
                while (true){
                    new Handler(server.accept()).start();
                }
            } finally {
                server.close();
            }
        }
        catch (Exception e)
        {

        }

    }

}



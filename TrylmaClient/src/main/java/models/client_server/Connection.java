package models.client_server;

import controllers.ErrorController;
import models.client.board_players.board.Board;
import views.BoardView;
import views.ErrorView;
import views.NewGameView;

import java.io.*;
import java.net.Socket;

/**
 * Klasa obsługuje połączenie z serwerem.
 * TODO: dokończ
 */
public class Connection {
    private static Socket socket;
    private static ObjectInputStream is;
    private static ObjectOutputStream os;
    private static boolean connectionSuccess = false;
    private static boolean myTurn = false;
    private static NewGameView newGameView;

    public static boolean establishConnection()
    {
        try {
            Connection.socket = new Socket("localhost", 9090);
            Connection.connectionSuccess = true;
            Connection.is = new ObjectInputStream(socket.getInputStream());
            Connection.os = new ObjectOutputStream(socket.getOutputStream());
            return true;
        }
        catch (Exception e)
        {
            return false;
        }
    }
    private static boolean isConnectionSuccessfull() {
        return connectionSuccess;
    }

    public static void sendConnect() {
        try {
            os.writeObject("connect");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void sendCreateNewGameCommand(int players, int bots) {
        String info = "creategame" + ":" + bots + ":" + players; //TODO: size of plansza
        if (isConnectionSuccessfull()) {
            try {
                os.writeObject(info);
                Connection.newGameView.hide(); //TODO: show board
                BoardView.initialize(800, 4, 1, 0); //TODO: checkers as variable
                BoardView.show();
                BoardView.initializeFields();
                //TODO: gracz po botach
                for (int i = 1; i<=bots; i++) {
                    Board.addNewPlayer(false);
                }
                if (players>bots) Board.addNewPlayer(true);
                for (int i = bots+2; i<=players; i++) {
                    Board.addNewPlayer(false);
                }
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("Failed to write NewGameCommand to ObjectOutputStream");
            }
        }
    }

    public static void sendChosenPawn(Field field) {
        if(isitMyTurn()) {
            String command = "startfield";
            try {
                os.writeObject(command);
                os.writeObject(field);
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("Error while writing chosen pawn to OutputStream");
            }
        }
    }

    public static void sendSkip() {
        if(isitMyTurn()) {
            String command = "skip";
            try {
                os.writeObject(command);
            } catch (IOException e) {
                e.printStackTrace();
                System.err.println("Error while sending skip command");
            }
        }
    }

    public static void sendChosenField(Field field) {
        if(isitMyTurn()) {
            String command = "endfield";
            try {
                os.writeObject(command);
                os.writeObject(field);
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("Error while writing chosen pawn to OutputStream");
            }
            myTurn = false;
            Board.removePossibleFields();
        }
    }

    private static boolean commandInterpreter(String command) {
        if (command.equals("yourturn")) {
            myTurn=true;
        } else if (command.matches("moved(.*)")) {
            String[] temp = command.split(":");
            MovePath movePath;
            try {
                movePath = (MovePath) is.readObject();
                Board.makeMove(Integer.parseInt(temp[1]), movePath);
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
                System.out.println("failed to read object");
            }
        }
        else if (command.equals("newgame")) {
            try {
                newGameView = new NewGameView();
                newGameView.initialize();
                newGameView.show();
            } catch (IOException e) {
                e.printStackTrace();
                System.err.println("Unable to start new game, FXML not found");
            }
        }
        else if (command.matches("joingame(.*)")) {
            String[] temp = command.split(":");
            BoardView.initialize(800, 4, 1, 0); //TODO: checkers as variable
            BoardView.show();
            BoardView.initializeFields();
            int playerid = Integer.parseInt(temp[1]);
            for (int i = 1; i<=Integer.parseInt(temp[2]); i++) {
                if (i==playerid) Board.addNewPlayer(true);
                else Board.addNewPlayer(false);
            }
        }
        else if (command.equals("gamefull")) {
            ErrorController.message = "Gra jest pełna";
            ErrorView errorView = new ErrorView();
            try {
                errorView.initialize();
            } catch (IOException e) {
                e.printStackTrace();
                System.err.println("Error while showing error message xD");
            }
        }
        else if (command.matches("won(.*)")) {
            String[] temp = command.split(":");
            int playerid = Integer.parseInt(temp[1]);
            //end game, check who won
            return false;
        }
        else if (command.equals("possible_fields")) {
            try {
                MovePath movePath = (MovePath) is.readObject();
                Board.showPossibleFields(movePath);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        else {
            System.out.println("Failed to interprete command.");
        }
        return true;
    }

    public static void startConnectionLoop () {
        while(true) {
            try {
                String command = (String) is.readObject();
                if (commandInterpreter(command)) break;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static boolean isitMyTurn() {
        return myTurn;
    }
}

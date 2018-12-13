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
    private static BufferedReader input;
    private static PrintWriter output;
    private static ObjectInputStream is;
    private static ObjectOutputStream os;
    private static boolean connectionSuccess = false;
    private static boolean myTurn = false;

    public static boolean establishConnection()
    {
        try {
            Connection.socket = new Socket("localhost", 9090);
            Connection.input = new BufferedReader(new InputStreamReader(Connection.socket.getInputStream()));
            Connection.output = new PrintWriter(Connection.socket.getOutputStream(),true);
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

    public static void sendCreateNewGameCommand(int players, int bots) {
        String info = "create" + ":" + bots + ":" + players;
        if (isConnectionSuccessfull()) {
            try {
                os.writeObject(info);
            } catch (IOException e) {
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

    public void commandInterpreter(String command) {
        if (command.equals("yourturn")) {
            myTurn=true;
        } else if (command.matches("moved(.*)")) {
            String[] temp = command.split(":");
            MovePath movePath = null; //TODO: finish
            Board.makeMove(Integer.parseInt(temp[1]), movePath);
        }
        else if (command.equals("newgame")) {
            try {
                NewGameView newGameView = new NewGameView();
                newGameView.initialize();
                newGameView.show();
            } catch (IOException e) {
                e.printStackTrace();
                System.err.println("Unable to start new game, FXML not found");
            }
        }
        else if (command.matches("joingame(.*)")) {
            String[] temp = command.split(":");
            BoardView.initialize(800, 4, 1, 0);
            BoardView.show();
            BoardView.initializeFields();
            int playerid = Integer.parseInt(temp[1]);
            for (int i = 1; i<playerid; i++) {
                Board.addNewPlayer(false);
            }
            Board.addNewPlayer(true);
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
        }
        else if (command.equals("possible_fields")) {

        }
        else {
            System.out.println("Failed to interprete command.");
        }
    }

    public static boolean isitMyTurn() {
        return myTurn;
    }
}

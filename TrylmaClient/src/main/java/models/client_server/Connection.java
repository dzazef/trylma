package models.client_server;

import javafx.application.Platform;
import javafx.scene.control.Alert;
import models.client.board_players.board.Board;
import serializable.Field;
import serializable.FieldsSet;
import views.BoardView;
import views.NewGameView;

import java.io.*;
import java.net.Socket;

/**
 * Klasa obsługuje połączenie z serwerem.
 * TODO: dokończ
 */
@SuppressWarnings("Duplicates")
public class Connection {
    private static Socket socket;
    private static ObjectInputStream is;
    private static ObjectOutputStream os;
    private static boolean connectionSuccess = false;
    private static boolean myTurn = false;

    public static boolean establishConnection(String host)
    {
        try {
            Connection.socket = new Socket(host, 9090);
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
        if (isConnectionSuccessfull()) {
            System.out.println("connect ->");
            try {
                os.writeObject("connect");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void sendCreateNewGameCommand(int players, int bots) {
        String info = "creategame" + ":" + bots + ":" + players+":4"; //TODO: size of plansza
        if (isConnectionSuccessfull()) {
            System.out.println(info+" ->");
            try {
                os.writeObject(info);
                NewGameView.hide();
                BoardView.initialize(600, 4, 5, 0);
                BoardView.initializeFields();
                BoardView.show();
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
            System.out.println(command+" ->");
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
            System.out.println(command+" ->");
            try {
                myTurn = false;
                System.out.println("INFO: End of my turn.");
                os.writeObject(command);
                Platform.runLater(Board::removePossibleFields);
            } catch (IOException e) {
                e.printStackTrace();
                System.err.println("Error while sending skip command");
            }
        }
    }

    public static void sendChosenField(Field field) {
        if(isitMyTurn()) {
            String command = "endfield";
            System.out.println(command+" ->");
            try {
                os.writeObject(command);
                os.writeObject(field);
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("Error while writing chosen pawn to OutputStream");
            }
            myTurn = false;
            System.out.println("INFO: End of my turn. Now waiting for info about my move.");
            Board.removePossibleFields();
        }
    }

    public static boolean commandInterpreter(String command) {
        System.out.println(command+" <-");
        if (command.equals("yourturn")) {
            System.out.println("INFO: Now is my turn.");
            myTurn=true;
        } else if (command.matches("moved(.*)")) {
            String[] temp = command.split(":");
            FieldsSet movePath;
            try {
                movePath = (FieldsSet) is.readObject();
                Platform.runLater( () -> Board.makeMove(Integer.parseInt(temp[1]), movePath));
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
                System.out.println("failed to read object");
            }
        }
        else if (command.equals("newgame")) {
            Platform.runLater(() -> {
               try {
                    NewGameView.initialize();
                    NewGameView.show();
               } catch (IOException e) {
                   e.printStackTrace();
               }
            });
        }
        else if (command.matches("joingame(.*)")) {
            String[] temp = command.split(":");
            Platform.runLater( () -> {
                BoardView.initialize(600, 4, 5, 0); //TODO: checkers as variable
                BoardView.show();
                BoardView.initializeFields();
                int playerid = Integer.parseInt(temp[1]); //TODO: potrzebuję tylko id, bez 'player'
                for (int i = 1; i <= Integer.parseInt(temp[2]); i++) {
                    if (i == playerid) Board.addNewPlayer(true);
                    else Board.addNewPlayer(false);
                }
            });
        }
        else if (command.equals("gamefull")) {
            Platform.runLater( () -> {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setContentText("Game is full now. Try next time!");
                alert.showAndWait();
            });

        }
        else if (command.matches("won(.*)")) {
            String[] temp = command.split(":");
            int playerid = Integer.parseInt(temp[1]);
            Platform.runLater( () -> {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Koniec gry");
                alert.setHeaderText(null);
                alert.setContentText("Wygrał gracz nr "+playerid);
                alert.showAndWait();
            });
            return true;
        }
        else if (command.equals("possible_fields")) {
            try {
                FieldsSet movePath = (FieldsSet) is.readObject();
                Platform.runLater(() -> Board.showPossibleFields(movePath));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        else {
            System.out.println("Failed to interprete command.");
        }
        return false;
    }

    public static void startConnectionLoop () {
        if (isConnectionSuccessfull()) {
            new Thread(() -> {
                while (true) {
                    try {
                        Object object = is.readObject();
                        Connection.commandInterpreter((String) object);
                    } catch (Exception e) {
                        System.out.println("Lost connection to the server");
                        System.exit(1);
                    }
                }
            }).start();
        }
    }

    public static boolean isitMyTurn() {
        return myTurn;
    }
}

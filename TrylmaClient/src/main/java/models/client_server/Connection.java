package models.client_server;

import javafx.application.Platform;
import javafx.scene.control.Alert;
import serializable.Field;
import serializable.FieldsSet;
import views.BoardView;
import views.BoardViewManager;
import views.MenuView;
import views.NewGameView;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * Klasa obsługuje połączenie z serwerem.
 */
@SuppressWarnings("Duplicates")
public class Connection {
    private static int wGap = 3;
    private static Socket socket;
    private static ObjectInputStream is;
    private static ObjectOutputStream os;
    private static boolean connectionSuccess = false;
    private static boolean myTurn = false;

    /**
     * Funkcja umożliwiająca nawiązanie połączenia z serwerem.
     * @param host nazwa hosta
     * @return zwraca true jeśli połącznie zostało nawiązane, w przeciwnym wypadku zwraca false.
     */
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

    /**
     * Funkcja sprawdzająca czy nawiąane jest połączenie z serwerem.
     * @return zwraca prawdę jeśli połączenie jest nawiązane, w przeciwnym wypadku false.
     */
    private static boolean isConnectionSuccessfull() {
        return connectionSuccess;
    }

    /**
     * Metoda wysyła komendę do serwera informującą o próbie połączenia.
     */
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

    /**
     * Metoda wysyła komende do serwera informującą o parametrach nowej gry.
     * @param players liczba graczy
     * @param bots liczba botów
     * @param board rozmiar planszy
     */
    public static void sendCreateNewGameCommand(int players, int bots, int board) {
        String info = "creategame" + ":" + bots + ":" + players+":"+board;
        if (isConnectionSuccessfull()) {
            System.out.println(info+" ->");
            try {
                os.writeObject(info);
                NewGameView.hide();
                BoardViewManager.initialize(600, board, wGap, 0);
                BoardViewManager.initializeFields();
                BoardViewManager.draw(BoardViewManager.getCircleFields());
                BoardViewManager.show();
                for (int i = 1; i<=bots; i++) {
                    BoardViewManager.addNewPlayer(false);
                }
                if (players>bots) BoardViewManager.addNewPlayer(true);
                for (int i = bots+2; i<=players; i++) {
                    BoardViewManager.addNewPlayer(false);
                }
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("Failed to write NewGameCommand to ObjectOutputStream");
            }
        }
    }

    /**
     * Metoda wysyła pole początkowe do serwera.
     * @param field pole początkowe
     */
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

    /**
     * Metoda wysyła informację o spasowaniu do serwera.
     */
    public static void sendSkip() {
        if(isitMyTurn()) {
            String command = "skip";
            System.out.println(command+" ->");
            try {
                myTurn = false;
                Platform.runLater(() -> BoardViewManager.setMyTurn(false));
                System.out.println("INFO: End of my turn.");
                os.writeObject(command);
                Platform.runLater(BoardViewManager::removePossibleFields);
            } catch (IOException e) {
                e.printStackTrace();
                System.err.println("Error while sending skip command");
            }
        }
    }

    /**
     * Metoda wysyła pole kończące ruch do serwera.
     * @param field pole kończące ruch.
     */
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
            Platform.runLater(() -> BoardViewManager.setMyTurn(false));
            System.out.println("INFO: End of my turn. Now waiting for info about my move.");
            BoardViewManager.removePossibleFields();
        }
    }

    /**
     * Metoda umożliwiająca interpretowanie komend odebranych od serwera.
     * @param command komenda
     */
    private static void commandInterpreter(String command) {
        System.out.println(command+" <-");
        if (command.equals("yourturn")) {
            System.out.println("INFO: Now is my turn.");
            myTurn=true;
            Platform.runLater(() -> BoardViewManager.setMyTurn(true));
        } else if (command.matches("moved(.*)")) {
            String[] temp = command.split(":");
            FieldsSet movePath;
            try {
                movePath = (FieldsSet) is.readObject();
                Platform.runLater( () -> BoardViewManager.makeMove(Integer.parseInt(temp[1]), movePath));
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
                BoardView.initialize(600, Integer.parseInt(temp[3]), wGap, 0);
                BoardView.show();
                BoardViewManager.initializeFields();
                BoardViewManager.draw(BoardViewManager.getCircleFields());
                int playerid = Integer.parseInt(temp[1]);
                for (int i = 1; i <= Integer.parseInt(temp[2]); i++) {
                    if (i == playerid) BoardViewManager.addNewPlayer(true);
                    else BoardViewManager.addNewPlayer(false);
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
        }
        else if (command.equals("possible_fields")) {
            try {
                FieldsSet movePath = (FieldsSet) is.readObject();
                Platform.runLater(() -> BoardViewManager.showPossibleFields(movePath));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        else if (command.equals("failure")) {
            NewGameView.hide();
            BoardViewManager.hide();
            MenuView.show();
        }
        else {
            System.out.println("Failed to interprete command.");
        }
    }

    /**
     * Metoda tworząca nowy wątek odpowiedzialny za odczytywanie komend wysłanych przez serwer.
     */
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

    /**
     * Funkcja spradzająca czy gracz ma mozliwość ruchu.
     * @return zwraca true, jeśli gracz ma możliwość ruchu, w przeciwnym wypadku false
     */
    public static boolean isitMyTurn() {
        return myTurn;
    }
}

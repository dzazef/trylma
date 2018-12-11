package models.client_server;

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
    public static boolean isConnectionSuccessfull() {
        return connectionSuccess;
    }

    public String read()
    {
        String s;
        try {
            s = Connection.input.readLine();
        }
        catch(IOException e)
        {
            s = "Reading Error!!!";
        }
        return s;
    }
    public void write(String s)
    {
        Connection.output.println(s);
    }

    public static void sendCreateNewGameCommand(int players, int bots) {
        String info = "create"+":"+bots+":"+players;
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

    }

    public void commandInterpreter(String command) {
        if (command.equals("yourturn")) {
            myTurn=true;
        } else if (command.matches("moved(.*)")) {
            String[] temp = command.split(":");
            MovePath movePath = null; //finish
            //BoardView.makeMove(Integer.parseInt(temp[1]), movePath);
        } else if (command.equals("newgame")) {
            //createnewgame
        } else if (command.matches("joingame(.*)")) {
            String[] temp = command.split(":");
            int playerid = Integer.parseInt(temp[1]);
            //create new player for me and players for the rest
        } else if (command.equals("gamefull")) {
            //disconnect from server and shutdown
        } else if (command.matches("won(.*)")) {
            String[] temp = command.split(":");
            int playerid = Integer.parseInt(temp[1]);
            //end game, check who won
        } else {
            System.out.println("Failed to interprete command.");
        }
    }

    public static boolean isitMyTurn() {
        return myTurn;
    }
}

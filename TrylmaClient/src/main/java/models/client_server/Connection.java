package models.client_server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * Klasa obsługuje połączenie z serwerem.
 * TODO: dokończ
 */
public class Connection {
    private Socket socket;
    private BufferedReader input;
    private PrintWriter output;
    private boolean connectionsuccess = false;
    private boolean myTurn = false;

    public boolean establishConnection()
    {
        try {
            this.socket = new Socket("localhost", 9090);
            this.input = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
            this.output = new PrintWriter(this.socket.getOutputStream(),true);
            this.connectionsuccess = true;
            return true;
        }
        catch (Exception e)
        {
            return false;
        }
    }
    public boolean isConnectionSuccessfull() {
        return connectionsuccess;
    }
    public String read()
    {
        String s;
        try {
            s = this.input.readLine();
        }
        catch(IOException e)
        {
            s = "Reading Error!!!";
        }
        return s;
    }
    public void write(String s)
    {
        this.output.println(s);
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

}

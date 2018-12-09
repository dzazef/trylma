package models.client_server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Connection {
    private Socket socket;
    private BufferedReader input;
    private PrintWriter output;
    boolean connectionsuccess = false;

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
    public boolean isConnectionsuccess() {
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
}

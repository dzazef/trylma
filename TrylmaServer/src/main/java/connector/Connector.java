package connector;

import gamemanager.GameManager;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;

public class Connector {
    private Command command;
    private ServerSocket server;

    private static class Handler extends Thread {
        private String name;
        private Socket socket;
        private BufferedReader in;
        private PrintWriter out;
        public Handler(Socket socket) {
            this.socket = socket;
        }
        private String readMessage()
        {
            try {
                return in.readLine();
            }catch (IOException e)
            {
                return name + "READ FAILURE";
            }
        }
        private void SendMessage(Command command)
        {
            out.println("Elo tu Twój handler w serwerze");
        }
        private boolean getConfirmation()
        {
            try {
                String confirmation =  in.readLine();
                if(confirmation.equals(name+"confirmed"));
                {
                    return  true;
                }
            }catch (IOException e)
            {
                return false;
            }
        }
        private boolean sendConfirmation()
        {
            out.println(name + "confirmation");
            return true;
        }

        public void run() {
            try {
                in = new BufferedReader(new InputStreamReader(
                        socket.getInputStream()));
                out = new PrintWriter(socket.getOutputStream(), true);

                System.out.println("Serwer czyta");
                String s = in.readLine();

                System.out.println(s);
            } catch (IOException e) {
                System.out.println(e);
            } finally {

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
            System.out.println("connestion failure");
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



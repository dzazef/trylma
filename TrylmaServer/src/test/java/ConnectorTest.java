import connector.Connector;
import org.junit.Test;

import java.io.*;

import static org.junit.Assert.*;
import java.net.ServerSocket;
import java.net.Socket;

public class ConnectorTest {


    @Test
    public void testHandler()
    {
        Connector connector = new Connector();

        Socket socket = null;
        BufferedReader in = null;
        PrintWriter out  = null;
        ObjectOutputStream objout  = null;
        ObjectInputStream objin = null;
        try {
            socket = new Socket("localhost", 9090);
            assertTrue(socket != null);
             in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            assertTrue(in != null);
             out = new PrintWriter(socket.getOutputStream(), true);
            assertTrue(out != null);
             objout = new ObjectOutputStream(socket.getOutputStream());
            assertTrue(objout != null);
             objin = new ObjectInputStream(socket.getInputStream());
            assertTrue(objin != null);
            new Connector.Handler(socket).start();
        }
        catch (Exception e)
        {

        }
        try {
            out.println("connect");
            assertEquals(in.readLine(), "creategame");
            socket.close();
        }
        catch (Exception e)
        {

        }

    }


}

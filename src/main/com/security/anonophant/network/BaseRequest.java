/**
 * BaseSocket
 *
 * @author Aaron Weaver  (waaronl@okstate.edu)
 * @version 1.0
 * @since 4/6/15
 */

package main.com.security.anonophant.network;

import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.EventHandler;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;

/**
 * Created by weava on 4/6/15.
 *
 * Simple socketing setup complete with ability to setup
 * loading dialog if desired.
 *
 * NOTE: As of writing this initial description, this implementation is imperfect,
 * and may be changed to fit our needs.
 */
public abstract class BaseRequest extends Task<ArrayList<String>>
{
    private Socket requestSocket;
    private String URI;
    private String[] serverStrings;
    private int port;

    public BaseRequest(String URI, int port, String[] serverStrings) throws IOException
    {
        this.URI = URI;
        this.port = port;
        this.serverStrings = serverStrings;
        requestSocket = new Socket(URI, port);
    }

    public ArrayList<String> read() throws IOException { return null; }

    /**
     * Always close your sockets boys & girls :^)
     * @throws IOException
     */
    public void close() throws IOException
    {
        requestSocket.close();
    }

    /**
     * Writes array of strings through socket. You will ALWAYS want to write from
     * a socket (hence the abstract declaration)
     * @param senders
     */
    public abstract void write() throws IOException;

    public Socket getRequestSocket() {
        return requestSocket;
    }

    public void setRequestSocket(Socket requestSocket) {
        this.requestSocket = requestSocket;
    }

    public String getURI() {
        return URI;
    }

    public void setURI(String URI) {
        this.URI = URI;
    }

    public String[] getServerStrings() {
        return serverStrings;
    }

    public void setServerStrings(String[] serverStrings) {
        this.serverStrings = serverStrings;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }
}

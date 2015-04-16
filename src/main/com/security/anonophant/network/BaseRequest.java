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
    public Socket netSocket;
    public String URI;
    public Stage loadingView;
    public int port;

    public BaseRequest(String URI, int port) throws IOException
    {
        this.URI = URI;
        this.port = port;
        netSocket = new Socket(URI, port);
    }

    /**
     * Always close your sockets boys & girls :^)
     * @throws IOException
     */
    public void close() throws IOException
    {
        netSocket.close();
    }

    /**
     * Optional implementation for when wanting to read from a pipe.
     * (Not abstract so that reading remains an option for sockets, rather than a requirement)
     */
    public void read(){}

    /**
     * Writes array of strings through socket. You will ALWAYS want to write from
     * a socket (hence the abstract declaration)
     * @param senders
     */
    public abstract void write(String... senders);
}

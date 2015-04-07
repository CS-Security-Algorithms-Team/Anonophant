/**
 * BaseSocket
 *
 * @author Aaron Weaver  (waaronl@okstate.edu)
 * @version 1.0
 * @since 4/6/15
 */

package main.com.security.anonophant.network;

import javafx.stage.Stage;

import java.io.IOException;
import java.net.Socket;

/**
 * Created by weava on 4/6/15.
 *
 * Simple socketing setup complete with ability to setup
 * loading dialog if desired.
 *
 * NOTE: As of writing this initial description, this implementation is imperfect,
 * and may be changed to fit our needs.
 */
public abstract class BaseSocket implements Runnable
{
    public Socket netSocket;
    public String URI;
    public Stage loadingView;
    public int port;

    public BaseSocket(String URI, int port, Stage loadingView) throws IOException
    {
        this.URI = URI;
        this.port = port;
        this.loadingView = loadingView;
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
     * Optional abstract implementation for when wanting to read from a pipe.
     * (Not abstract so that reading remains an option for sockets, rather than a requirement)
     */
    public void read(){}

    /**
     * Writes array of strings through socket. You will ALWAYS want to write from
     * a socket (hence the abstract declaration)
     * @param senders
     */
    public abstract void write(String... senders);

    /**
     * Execute before a thread has started its logic.
     * Setup any loading screens or UI changes to allow the user to know a network request is
     * happening.
     */
    public void onPreExecute(){}

    /**
     * Execute after a thread has finished writing (and possibly reading) and no errors have occured.
     * Finish up any loading screens and load requested content.
     */
    public void onPostExecute(){}

    /**
     * Execute if an error occurs in thread
     * Make sure to let the user know an error has occured with the network.
     */
    public void onError(){}
}

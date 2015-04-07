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

    public void close() throws IOException
    {
        netSocket.close();
    }

    public void read(){}

    public void write(String... senders){}

    public void onPreExecute(){}

    public void onPostExecute(){}

    public void onError(){}
}

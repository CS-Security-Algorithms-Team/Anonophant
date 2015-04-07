/**
 * WriteBaseSocket
 *
 * @author Aaron Weaver  (waaronl@okstate.edu)
 * @version 1.0
 * @since 4/6/15
 */

package main.com.security.anonophant.network;

import javafx.stage.Stage;

import java.io.IOException;

/**
 * Created by weava on 4/6/15.
 *
 * Extension of BaseSocket. Uses socket for writes only
 */
public abstract class WriteSocket extends BaseSocket
{
    public WriteSocket(String URI, int port, Stage loadingView) throws IOException
    {
        super(URI, port, loadingView);
    }

    public void write(String... senders){}
}

/**
 * ReadBaseSocket
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
 * Extension of BaseSockets abilities, uses BaseSocket for reads only
 */
public abstract class ReadSocket extends BaseSocket
{
    public ReadSocket(String URI, int port, Stage loadingView) throws IOException
    {
        super(URI, port, loadingView);
    }

    public void read(){}
}

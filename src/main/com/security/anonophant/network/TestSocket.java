/**
 * TestSocket
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
 * This is only a test class, not really useful for anything, just making sure
 * OOP structures are working properly
 */
public class TestSocket extends ReadSocket
{
    public TestSocket(String URI, int port, Stage loadingView) throws IOException
    {
        super(URI, port, loadingView);
    }

    @Override
    public void read()
    {
        super.read();
    }

    @Override
    public void onError()
    {

    }

    @Override
    public void run()
    {

    }

}

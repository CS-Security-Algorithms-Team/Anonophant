/**
 * TestSocket
 *
 * @author Aaron Weaver  (waaronl@okstate.edu)
 * @version 1.0
 * @since 4/6/15
 */

package main.com.security.anonophant.network;

import javafx.concurrent.WorkerStateEvent;
import javafx.event.EventHandler;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by weava on 4/6/15.
 *
 * This is only a test class, not really useful for anything, just making sure
 * OOP structures are working properly
 */
public class TestRequest extends BaseRequest
{
    public TestRequest(String URI, int port, String[] serverStrings) throws IOException
    {
        super(URI, port, serverStrings);

        /*
         * Generic Fail handler only sends message to console right now,
         * If you need to add more generic functionality, add to FailHandler.
         * For specific implementations, create new failure event handler in
         * the class creating the instance of Request.
         */
        this.setOnFailed(new FailHandler("YOU HAVE FAILED"));
    }

    @Override
    public void write()
    {
        String[] toSend = getServerStrings();
    }

    @Override
    protected ArrayList<String> call() throws Exception
    {
        return null;
    }
}

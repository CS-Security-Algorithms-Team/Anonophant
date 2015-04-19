/**
 * AuthenticationRequest
 *
 * @author Aaron Weaver  (waaronl@okstate.edu)
 * @version 1.0
 * @since 4/17/15
 */

package main.com.security.anonophant.network;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;

/**
 * Created by weava on 4/17/15.
 */
public class AuthenticationRequest extends BaseRequest
{
    private ArrayList<String> fromServer;

    public AuthenticationRequest(String URI, int port, String[] serverStrings) throws IOException
    {
        super(URI, port, serverStrings);

        this.setOnFailed(new FailHandler("Could not connect to Authentication server"));
    }

    @Override
    public ArrayList<String> read() throws IOException
    {
        BufferedReader in = new BufferedReader(new InputStreamReader(getRequestSocket().getInputStream()));
        ArrayList<String> retrievedStrings = new ArrayList<>();
        String line = null;
        while((line = in.readLine()) != null)
        {
            retrievedStrings.add(line);
        }

        return retrievedStrings;
    }

    @Override
    public void write() throws IOException
    {
        String[] toSend = getServerStrings();
        PrintWriter out = new PrintWriter(getRequestSocket().getOutputStream());
        out.println(toSend[0]);
        System.out.println("TO SEND 0: " + toSend[0]);
        out.flush();
    }

    @Override
    protected ArrayList<String> call() throws Exception
    {
        write();
        fromServer = read();
        return null;
    }

    public ArrayList<String> getFromServer()
    {
        return fromServer;
    }
}

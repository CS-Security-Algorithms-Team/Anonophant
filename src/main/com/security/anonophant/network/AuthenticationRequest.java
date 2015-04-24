/**
 * AuthenticationRequest
 *
 * @author Aaron Weaver  (waaronl@okstate.edu)
 * @version 1.0
 * @since 4/17/15
 */

package main.com.security.anonophant.network;

import java.io.*;
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
        System.out.println("AUTH READING");
        DataInputStream in = new DataInputStream(getRequestSocket().getInputStream());
        ArrayList<String> retrievedStrings = new ArrayList<>();
        retrievedStrings.add(in.readUTF());

        System.out.println("AUTH FINISHED READ");

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
        //close();
        System.out.println("I AM RETURNING AUTH");
        return fromServer;
    }

    public ArrayList<String> getFromServer()
    {
        return fromServer;
    }
}

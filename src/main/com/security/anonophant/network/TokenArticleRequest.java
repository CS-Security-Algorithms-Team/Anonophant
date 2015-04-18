package main.com.security.anonophant.network;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

/**
 * Created by Max on 4/17/2015.
 */
public class TokenArticleRequest extends BaseRequest
{
    private String newToken;
    private String oldToken;

    public TokenArticleRequest(String URI, int port, String newToken, String oldToken) throws IOException {
        super(URI, port, null);
        this.newToken = newToken;
        this.oldToken = oldToken;
    }

    @Override
    public void write() throws IOException
    {

    }

    @Override
    protected ArrayList<String> call() throws Exception
    {
        Socket soc = getRequestSocket();
        BufferedWriter write = new BufferedWriter(new OutputStreamWriter(soc.getOutputStream()));
        write.write("OLD_TOKEN\n");
        write.write(newToken+"\n");
        write.write(oldToken+"\n");
        write.flush();
        BufferedReader reader = new BufferedReader(new InputStreamReader(soc.getInputStream()));
        String line = null;
        ArrayList<String> articles = new ArrayList<>();
        while ((line =reader.readLine()) != null)
        {
            articles.add(line);
        }
        return articles;
    }
}
